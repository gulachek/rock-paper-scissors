package localrps.util

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


fun qrPng(filename:String, type: String ,data:String) {
    val outputFile = File(filename).also { file -> file.parentFile.mkdirs() }
    ImageIO.write(generateQRCodeImage(data),type, outputFile)
}

@Throws(Exception::class)
fun generateQRCodeImage(barcodeText: String?): BufferedImage? {
    val barcodeWriter = QRCodeWriter()
    val bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200)
    return MatrixToImageWriter.toBufferedImage(bitMatrix)
}
