package localrps.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import localrps.CommandLineArgs
import localrps.util.qrPng
import java.io.File

fun Application.qrRouting(config: CommandLineArgs) {
    routing {
        static("/static"){
            resources("files")
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