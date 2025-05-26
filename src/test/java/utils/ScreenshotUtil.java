package utils;

import listeners.JUnit5TestListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String captureScreenshotAsBase64(WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        return ts.getScreenshotAs(OutputType.BASE64);
    }

    public static void captureAndAddToReport(WebDriver driver, String screenshotName, String description) {
        String base64Screenshot = captureScreenshotAsBase64(driver);
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String screenshotTitle = screenshotName + dateName;

        // Usamos JUnit5TestListener en lugar de TestListener
        if (JUnit5TestListener.getTest() != null) {
            JUnit5TestListener.getTest().log(Status.INFO, description,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot, screenshotTitle).build());
        }
        TestLogger.log(Status.INFO, "Screenshot capturado y añadido al reporte: " + description);
    }
}