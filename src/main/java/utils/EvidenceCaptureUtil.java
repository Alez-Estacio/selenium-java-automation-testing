package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class EvidenceCaptureUtil {

	// Método para Serenity BDD (versión simplificada)
	public static void captureAndAddToReport(WebDriver driver, String screenshotName, String description) {
		try {
			// 1. Captura como archivo
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String fileName = screenshotName + "_" + timestamp + ".png";

			// 2. Guarda en directorio local
			FileUtils.copyFile(screenshot, new File("target/screenshots/" + fileName));

			// 3. Log para el reporte (adaptar según tu sistema de reporting)
			System.out.println("SCREENSHOT: " + description + " - " + fileName);

		} catch (Exception e) {
			System.err.println("Error al capturar evidencia: " + e.getMessage());
		}
	}

	// Mantenemos tus métodos originales sin cambios
	public static void captureScreenshotToDocument(WebDriver driver, String rutaImagen,
			String nombreDocumento, String tituloEvidencia)
			throws IOException, InvalidFormatException, InterruptedException {
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File imageFile = new File(rutaImagen);
		FileUtils.copyFile(screen, imageFile);

		File fichero = new File(nombreDocumento);
		XWPFDocument docx;

		if (!fichero.exists()) {
			docx = new XWPFDocument();
		} else {
			FileInputStream ficheroStream = new FileInputStream(fichero);
			docx = new XWPFDocument(ficheroStream);
		}

		XWPFParagraph paragraph = docx.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText(tituloEvidencia);
		run.setFontSize(13);

		InputStream pic = new FileInputStream(rutaImagen);
		run.addPicture(pic, Document.PICTURE_TYPE_PNG, rutaImagen, Units.toEMU(500), Units.toEMU(200));
		pic.close();

		FileOutputStream out = new FileOutputStream(nombreDocumento);
		docx.write(out);
		out.flush();
		out.close();
		docx.close();

		TimeUnit.SECONDS.sleep(1);
	}

	public static void writeTitleToDocument(String nombreDocumento,
			String tituloEvidencia, int fontSize)
			throws IOException, InvalidFormatException, InterruptedException {
		File fichero = new File(nombreDocumento);
		XWPFDocument docx;

		if (!fichero.exists()) {
			docx = new XWPFDocument();
		} else {
			FileInputStream ficheroStream = new FileInputStream(fichero);
			docx = new XWPFDocument(ficheroStream);
		}

		XWPFParagraph paragraph = docx.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText(tituloEvidencia);
		run.setFontSize(fontSize);

		FileOutputStream out = new FileOutputStream(nombreDocumento);
		docx.write(out);
		out.flush();
		out.close();
		docx.close();

		TimeUnit.SECONDS.sleep(1);
	}

	public static void getScreenshot(WebDriver driver, String rutaImagen, String nombreFile)
			throws IOException {
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(rutaImagen + nombreFile));
	}
}