package localrps.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import localrps.CommandLineArgs
import localrps.Connection
import java.util.*

fun Application.gameUpdateRouting(config: CommandLineArgs) {
    routing {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        var n = 1

        suspend fun update() {
            connections.forEach {
                it.session.send(n.toString())
            }
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

    }
}