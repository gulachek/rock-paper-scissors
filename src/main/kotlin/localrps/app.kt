package localrps

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

data class Address(val port: Int, val host: String)

fun main(args: Array<String>) {
	val addr = parseAddress(args);

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

fun parseAddress(args: Array<String>): Address {
	var port = 8080

	val size = args.size
	if (args.size >= 1) {
		port = args[0].toInt()
	}

	return Address(port=port, host="0.0.0.0")
}
