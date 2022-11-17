package localrps

import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.websocket.*
import io.ktor.server.websocket.*
import localrps.util.qrPng
import java.io.File
import java.net.InetAddress
import java.util.concurrent.atomic.*
import java.util.*

class Address(val port: Int, val host: String){
	fun toUrl(): String {
		return "http://${this.host}:${this.port}/"
	}
}
data class CommandLineArgs(val addr: Address, val dir: String)

fun main(args: Array<String>) {
	val config = parseArgs(args);
	val addr = config.addr
	val dir = config.dir

	System.out.println("Using directory $dir")
	System.out.println("Connect board to ${addr.toUrl()}/static/board.html")

	embeddedServer(Netty, port = addr.port, host = addr.host) {
		install(WebSockets)
		configureRouting(config)
	}.start(wait = true)
}

class Connection(val session: DefaultWebSocketSession) {
	companion object {
		val lastId = AtomicInteger(0)
	}
	val name = "users${lastId.getAndIncrement()}"
}

fun Application.configureRouting(config: CommandLineArgs) {
	routing {
		val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
		var n = 1

		suspend fun update() {
			connections.forEach {
				it.session.send(n.toString())
			}
		}

		static("/static"){
			resources("files")
		}

		get("/increment") {
			n += 1
			update()
			call.respondText("")
		}

		webSocket("/updates") {
			val thisConnection = Connection(this)
			connections += thisConnection

			try {
				update()
				for (frame in incoming) {
				}
			} catch (e: Exception) {
				println(e.localizedMessage)
			}finally {
				connections -= thisConnection
			}
		}

		get("/qr"){
			val qrFilename = "${config.dir}/hostname-qr.png"
			qrPng(qrFilename, "png", "${config.addr.toUrl()}/static/controller.html")
			val qrFile = File(qrFilename)
			call.response.header(
				HttpHeaders.ContentDisposition,
				ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, qrFilename)
					.toString()
			)
			call.respondFile(qrFile)
		}
	}
}

fun parseArgs(args: Array<String>): CommandLineArgs {
	var port = 8080
	var dir = ".rock-paper-scissors-${port}"

	if (args.size >= 1) {
		port = args[0].toInt()
	}

	if (args.size >= 2) {
		dir = args[1]
	}

	return CommandLineArgs(
		addr=Address(port=port, host=InetAddress.getLocalHost().getHostName()),
		dir=dir
	)
}
