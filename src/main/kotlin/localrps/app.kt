package localrps

import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import localrps.util.qrPng
import java.io.File
import java.net.InetAddress

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
	System.out.println("Running on ${addr.toUrl()}")

	embeddedServer(Netty, port = addr.port, host = addr.host) {
		configureRouting(config)
	}.start(wait = true)
}

fun Application.configureRouting(config: CommandLineArgs) {
	routing {
		static("/static"){
			resources("files")
		}

		get("/"){
			call.respondRedirect("/static/index.html")
		}

		get("/qr"){
			val qrFilename = "${config.dir}/hostname-qr.png"
			qrPng(qrFilename, "png", config.addr.toUrl())
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
