package softesting.selenium;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import softesting.selenium.pages.FormPage;
import utils.EvidenceCaptureUtil;
import utils.TestLogger;

import java.io.IOException;
import java.time.Duration;

@ExtendWith(SerenityJUnit5Extension.class)
public class FormTest {
    private static final Logger logger = LoggerFactory.getLogger(FormTest.class);
    private static final String URL = "http://64.227.54.255/Softesting/Frontend/Caso1.html";
    private static final boolean debugMode = true;

    @Managed(driver = "chrome", options = "start-maximized,disable-notifications")
    private WebDriver driver;

    private FormPage formPage;

    @BeforeEach
    public void setUp() {
        formPage = new FormPage(driver);
        driver.get(URL);
        logger.info("Navegado a URL: {}", URL);
        TestLogger.logInfo("Browser abierto y navegado a la URL");
    }

    @Test
    @Title("CP01 - Envío de formulario con datos completos")
    public void testCompleteFormSubmission() throws IOException {
        formPage.enterNombre("Juan Martin Alvarez");
        formPage.enterEmail("juan.alvarez@outlook.com");
        formPage.enterBarrio("Modelia");
        formPage.enterAsunto("Petición de prueba");
        formPage.enterMensaje("Este es un mensaje de prueba");

        EvidenceCaptureUtil.captureAndAddToReport(driver, "CP01 - Envío de formulario con datos completos",
                "Formulario lleno antes de enviar"); // Captura de screenshot

        String mensajeAlerta = formPage.enviarFormularioYCapturarAlerta(); // Captura del error y método usado para
                                                                           // obtenerlo
        Assertions.assertTrue(mensajeAlerta.contains("UPPPPS ALGO HA FALLADO"),
                "Mensaje recibido: " + mensajeAlerta);
    }

    @Test
    @Title("CP02 - Llenar el formulario sin nombre")
    public void testFormSubmissionWithoutName() {
        driver.findElement(By.id("nombre")).click(); // Enfocar el campo nombre
        formPage.enterEmail("test@test.com");
        formPage.enterBarrio("Usaquén");
        EvidenceCaptureUtil.captureAndAddToReport(driver, "CP02 - Llenar el formulario sin nombre",
                "Formulario sin nombre"); // Captura de screenshot
        formPage.enterAsunto("Reclamo");
        formPage.enterMensaje("Falta mi nombre");

        String error = formPage.getErrorNombre(); // Captura del error y método usado para obtenerlo
        Assertions.assertEquals("El nombre debe ser mayor a 4 letras y no debe incluir caracteres especiales", error);
    }

    @Test
    @Title("CP03 - Formulario con email inválido")
    public void testFormSubmissionWithInvalidEmail() {
        formPage.enterNombre("Ana Gómez");
        formPage.enterEmail("emailinvalido");
        formPage.enterBarrio("Chapinero");
        formPage.enterAsunto("Sugerencia");
        formPage.enterMensaje("Email mal formado");

        EvidenceCaptureUtil.captureAndAddToReport(driver, "CP03 - Formulario con email inválido",
                "Formulario sin correo"); // Captura de screenshot

        String error = formPage.getErrorEmail(); // Captura del error y método usado para obtenerlo
        Assertions.assertEquals(
                "El Email debe ser mayor a 4 caracteres y debe llevar @ y . no debe incluir caracteres especiales",
                error);

    }

    @Test
    @Title("CP04 - Envío de formulario sin barrio")
    public void testFormSubmissionWithoutBarrio() {
        formPage.enterNombre("Carlos Ruiz");
        formPage.enterEmail("carlos@test.com");
        driver.findElement(By.name("barrio")).click();
        formPage.enterAsunto("Felicitaciones");
        formPage.enterMensaje("Mensaje de prueba");

        EvidenceCaptureUtil.captureAndAddToReport(driver, "CP04 - Envío de formulario sin barrio",
                "Formulario sin barrio"); // Captura de screenshot

        String error = formPage.getErrorBarrio(); // Captura del error y método usado para obtenerlo
        Assertions.assertEquals("El nombre debe ser mayor a 4 letras", error);
    }

    @Test
    @Title("CP05 - Envío de formulario sin asunto")
    public void testFormSubmissionWithoutSubject() {
        formPage.enterNombre("Luisa Pérez");
        formPage.enterEmail("luisa@test.com");
        formPage.enterBarrio("Chapinero");
        driver.findElement(By.name("asunto")).click();
        formPage.enterMensaje("Mensaje mínimo");

        EvidenceCaptureUtil.captureAndAddToReport(driver, "CP05 - Envío de formulario sin asunto",
                "Formulario sin asunto"); // Captura de screenshot

        String error = formPage.getErrorAsunto(); // Captura del error y método usado para obtenerlo
        Assertions.assertEquals("El nombre debe ser mayor a 4 letras", error);
    }

    @AfterEach
    public void tearDown() {
        logger.info("========== Test finalizado ==========");
        if (debugMode) {
            new WebDriverWait(driver, Duration.ofSeconds(8))
                    .until(ExpectedConditions.titleIs("Simulacro Funcional")); // o cualquier título actual de la página
        }
    }
}
