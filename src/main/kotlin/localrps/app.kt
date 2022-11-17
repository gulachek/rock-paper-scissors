package localrps

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

data class Address(val port: Int, val host: String)
data class CommandLineArgs(val addr: Address, val dir: String)

fun main(args: Array<String>) {
	val config = parseArgs(args);
	val addr = config.addr
	val dir = config.dir

	System.out.println("Using directory ${dir}")
	System.out.println("Running on http://${addr.host}:${addr.port}/")

	embeddedServer(Netty, port = addr.port, host = addr.host) {
		configureRouting()
	}.start(wait = true)
}

fun Application.configureRouting() {
	routing {
		get("/"){
			call.respondText("Hello World!")
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
		addr=Address(port=port, host="0.0.0.0"),
		dir=dir
	)
}
