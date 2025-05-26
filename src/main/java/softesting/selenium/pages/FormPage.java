package softesting.selenium.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FormPage extends PageObject {

    private final WebDriverWait wait;

    // Locators
    @FindBy(id = "nombre")
    private WebElementFacade nombreInput;

    @FindBy(name = "email")
    private WebElementFacade emailInput;

    @FindBy(css = "#grupo__barrio > div > input")
    private WebElementFacade barrioInput;

    @FindBy(name = "asunto")
    private WebElementFacade asuntoInput;

    @FindBy(css = "#formulario > div:nth-child(5) > textarea")
    private WebElementFacade mensajeTextarea;

    @FindBy(css = "#formulario > div:nth-child(7) > button")
    private WebElementFacade enviarButton;

    @FindBy(xpath = "//div[@id='grupo__nombre']//p[contains(@class, 'error')]")
    private WebElementFacade errorNombreElement;

    @FindBy(xpath = "//div[@id='grupo__email']//p[contains(@class, 'error')]")
    private WebElementFacade errorEmailElement;

    @FindBy(xpath = "//div[@id='grupo__barrio']//p[contains(@class, 'error')]")
    private WebElementFacade errorBarrioElement;

    @FindBy(xpath = "//div[@id='grupo__asunto']//p[contains(@class, 'error')]")
    private WebElementFacade errorAsuntoElement;

    public FormPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Métodos de interacción
    public void enterNombre(String nombre) {
        nombreInput.type(nombre);
    }

    public void enterEmail(String email) {
        emailInput.type(email);
    }

    public void enterBarrio(String barrio) {
        barrioInput.type(barrio);
    }

    public void enterAsunto(String asunto) {
        asuntoInput.type(asunto);
    }

    public void enterMensaje(String mensaje) {
        mensajeTextarea.type(mensaje);
    }

    public void enviarFormulario() {
        enviarButton.click();
    }

    public String enviarFormularioYCapturarAlerta() {
        enviarButton.click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String message = alert.getText();
        alert.accept();
        return message;
    }

    // Métodos de validación para el error del campo Nombre
    public String getErrorNombre() {
        return errorNombreElement.waitUntilVisible().getText();
    }

    public String getErrorForName(String fieldName) {
        WebElementFacade errorElement = find(By.xpath(
                "//p[contains(@class, 'formulario__input-error-activo') and preceding-sibling::*[@name='" + fieldName
                        + "']]"));
        return errorElement.waitUntilVisible().getText();
    }

    // Métodos de validación para el error del campo Email
    public String getErrorEmail() {
        return errorEmailElement.waitUntilVisible().getText();
    }

    public String getErrorForEmail(String fieldEmail) {
        WebElementFacade errorElement = find(By.xpath(
                "//p[contains(@class, 'formulario__input-error-activo') and preceding-sibling::*[@email='" + fieldEmail
                        + "']]"));
        return errorElement.waitUntilVisible().getText();
    }

    // Métodos de validación para el error del campo Barrio
    public String getErrorBarrio() {
        return errorBarrioElement.waitUntilVisible().getText();
    }

    public String getErrorForBarrio(String fieldBarrio) {
        WebElementFacade errorBarrioElement = find(By.xpath(
                "//p[contains(@class, 'formulario__input-error-activo') and preceding-sibling::*[@barrio='"
                        + fieldBarrio
                        + "']]"));
        return errorBarrioElement.waitUntilVisible().getText();
    }

    // Métodos de validación para el error del campo Asunto
    public String getErrorAsunto() {
        return errorAsuntoElement.waitUntilVisible().getText();
    }

    public String getErrorForAsunto(String fieldAsunto) {
        WebElementFacade errorAsuntoElement = find(By.xpath(
                "//p[contains(@class, 'formulario__input-error-activo') and preceding-sibling::*[@asunto='"
                        + fieldAsunto
                        + "']]"));
        return errorAsuntoElement.waitUntilVisible().getText();
    }

    public boolean isFormSubmitted() {
        try {
            return wait.until(ExpectedConditions.urlContains("success"));
        } catch (Exception e) {
            return false;
        }
    }
}