# Proyecto: softesting-selenium-junit

Automatización de pruebas funcionales para un formulario web usando **Serenity BDD**, **JUnit 5** y **Selenium WebDriver**.  
Incluye generación de reportes, captura de evidencias y utilidades para pruebas robustas y trazables.

## Estructura del Proyecto

```
.
├── pom.xml
├── run_tests.bat
├── README.md
├── src/
│   ├── main/
│   │   └── java/
│   │       └── softesting/selenium/pages/FormPage.java
│   │       └── utils/EvidenceCaptureUtil.java
│   └── test/
│       └── java/
│           └── softesting/selenium/FormTest.java
│           └── listeners/JUnit5TestListener.java
│           └── utils/ScreenshotUtil.java
│           └── utils/TestLogger.java
│           └── utils/ExtentManager.java
│       └── resources/
│           └── serenity.conf
│           └── features/
│               └── formulario.feature
├── target/
│   └── site/serenity/
│   └── screenshots/
│   └── failsafe-reports/
└── logs/
```

## Tecnologías y Librerías

- **Java 11**
- **Maven**: Manejador de dependencias y ciclo de vida del proyecto.
- **Selenium WebDriver**: Automatización de navegadores.
- **Serenity BDD**: Framework para pruebas y generación de reportes.
- **JUnit 5**: Motor de ejecución de pruebas.
- **ExtentReports**: Reportes enriquecidos con capturas.
- **Apache POI**: Manipulación de documentos Word para evidencias.
- **Apache Commons IO**: Utilidades de archivos.
- **SLF4J**: Logging.

## Ejecución de Pruebas

Puedes ejecutar las pruebas y generar reportes usando el script por lotes incluido:

```sh
run_tests.bat
```

Opciones principales:
- Ejecutar un caso de prueba específico (`FormTest`)
- Limpiar y ejecutar todas las pruebas
- Generar solo los reportes
- Abrir el último reporte generado

También puedes ejecutar desde Maven:

```sh
mvn clean verify
```

Los reportes Serenity se generan en:  
`target/site/serenity/index.html`

## Configuración

La configuración principal de Serenity y WebDriver está en [`src/test/resources/serenity.conf`](src/test/resources/serenity.conf).

- URL base: `http://64.227.54.255/Softesting/Frontend/Caso1.html`
- Navegador: Chrome (con opciones personalizadas)
- Captura de pantallas: Activada para cada acción

## Pruebas BDD con Gherkin

El proyecto incluye pruebas escritas en Gherkin en el archivo [`formulario.feature`](src/test/resources/features/formulario.feature), que describen los escenarios de validación del formulario de contacto, tanto para caminos felices como para validaciones negativas. A continuación los escenarios en Gherkin:

```Gherkin
Feature: Validación de formulario de contacto
  Como usuario del sistema
  Quiero interactuar con el formulario de contacto
  Para garantizar que valide correctamente los datos ingresados

  @CP01 @formulario @happy-path
  Scenario Outline: CP01 - Envío de formulario con datos completos
    Dado que estoy en la página del formulario de contacto
    Cuando completo todos los campos con información válida:
      | Nombre             | Email                   | Barrio   | Asunto             | Mensaje                              |
      | <Nombre>           | <Email>                 | <Barrio> | <Asunto>           | <Mensaje>                            |
    Y hago clic en el botón "Enviar"
    Entonces debo ver el mensaje del sistema: "<Mensaje Esperado>"

    Ejemplos:
      | Nombre             | Email                   | Barrio   | Asunto             | Mensaje                              | Mensaje Esperado        |
      | Juan Martin Alvarez| juan.alvarez@outlook.com| Modelia  | Petición de prueba | Este es un mensaje de prueba        | UPPPPS ALGO HA FALLADO  |
      | María López        | maria.lopez@test.com    | Suba     | Consulta técnica   | Necesito soporte urgente            | UPPPPS ALGO HA FALLADO  |
      | Pedro Ramírez      | pedro.ramirez@mail.com  | Teusaquillo | Cotización       | Solicito información sobre precios  | UPPPPS ALGO HA FALLADO  |

  @CP02 @formulario @validacion @sad-path
  Scenario Outline: CP02 - Llenar el formulario sin nombre
    Dado que estoy en la página del formulario de contacto
    Cuando dejo el campo "Nombre" vacío
    Y completo los demás campos requeridos:
      | Email             | Barrio  | Asunto   | Mensaje          |
      | <Email>           | <Barrio>| <Asunto> | <Mensaje>        |
    Y hago clic en el campo "Email"
    Entonces debo ver el mensaje de error: "El nombre debe ser mayor a 4 letras y no debe incluir caracteres especiales"

    Ejemplos:
      | Email             | Barrio  | Asunto   | Mensaje          |
      | test@test.com     | Usaquén | Reclamo  | Falta mi nombre  |
      | cliente@mail.com  | Chapinero| Queja   | Servicio defectuoso |

  @CP03 @formulario @validacion @sad-path
  Scenario Outline: CP03 - Formulario con email inválido
    Dado que estoy en la página del formulario de contacto
    Cuando ingreso un email con formato inválido:
      | Email             |
      | <Email>           |
    Y completo los demás campos correctamente:
      | Nombre      | Barrio   | Asunto     | Mensaje           |
      | <Nombre>    | <Barrio> | <Asunto>   | <Mensaje>         |
    Y hago clic en el campo "Barrio"
    Entonces debo ver el mensaje de error: "El Email debe ser mayor a 4 caracteres y debe contener @ y ."

    Ejemplos:
      | Email             | Nombre      | Barrio   | Asunto     | Mensaje           |
      | email.invalido    | Ana Gómez   | Chapinero| Sugerencia | Email mal formado |
      | sinarroba.com     | Carlos Ruiz | Kennedy  | Consulta   | Formato incorrecto|
      | usuario@sinpunto  | Luisa Pérez | Fontibón | PQR       | Correo no válido  |

  @CP04 @formulario @validacion @sad-path
  Scenario Outline: CP04 - Envío de formulario sin barrio
    Dado que estoy en la página del formulario de contacto
    Cuando dejo el campo "Barrio" vacío
    Y completo los demás campos requeridos:
      | Nombre       | Email             | Asunto        | Mensaje          |
      | <Nombre>     | <Email>           | <Asunto>      | <Mensaje>        |
    Y hago clic en el campo "Asunto" o "Mensaje"
    Entonces debo ver el mensaje de error: "El nombre debe ser mayor a 4 letras"

    Ejemplos:
      | Nombre       | Email             | Asunto        | Mensaje          |
      | Carlos Ruiz  | carlos@test.com   | Felicitaciones| Mensaje de prueba|
      | Sofía Castro | sofia@corp.com    | Solicitud     | Urgente          |

  @CP05 @formulario @validacion @sad-path
  Scenario Outline: CP05 - Envío de formulario sin asunto
    Dado que estoy en la página del formulario de contacto
    Cuando dejo el campo "Mensaje" vacío
    Y completo los demás campos requeridos:
      | Nombre       | Email             | Barrio    | Mensaje    |
      | <Nombre>     | <Email>           | <Barrio>  | <Mensaje>  |
    Y hago clic en el campo "Mensaje"
    Entonces debo ver el mensaje de error: "El nombre debe ser mayor a 4 letras"

    Ejemplos:
      | Nombre       | Email             | Barrio    | Asunto    |
      | Luisa Pérez  | luisa@test.com    | Chapinero | Consulta  |
      | Andrés Gómez | andres@gmx.com    | Salitre   | Cotización|
```

## Estructura de Código

- [`FormPage`](src/main/java/softesting/selenium/pages/FormPage.java): Page Object para el formulario web.
- [`FormTest`](src/test/java/softesting/selenium/FormTest.java): Casos de prueba funcionales.
- [`EvidenceCaptureUtil`](src/main/java/utils/EvidenceCaptureUtil.java): Utilidades para capturar y guardar evidencias.
- [`JUnit5TestListener`](src/test/java/listeners/JUnit5TestListener.java): Integración con ExtentReports.
- [`TestLogger`](src/test/java/utils/TestLogger.java): Logging estructurado para pruebas.

## Ejemplo de Caso de Prueba

```java
@Test
@Title("CP01 - Envío de formulario con datos completos")
public void testCompleteFormSubmission() throws IOException {
    formPage.enterNombre("Juan Martin Alvarez");
    formPage.enterEmail("juan.alvarez@outlook.com");
    formPage.enterBarrio("Modelia");
    formPage.enterAsunto("Petición de prueba");
    formPage.enterMensaje("Este es un mensaje de prueba");

    EvidenceCaptureUtil.captureAndAddToReport(driver, "CP01 - Envío de formulario con datos completos",
            "Formulario lleno antes de enviar");

    String mensajeAlerta = formPage.enviarFormularioYCapturarAlerta();
    Assertions.assertTrue(mensajeAlerta.contains("UPPPPS ALGO HA FALLADO"),
            "Mensaje recibido: " + mensajeAlerta);
}
```

## Notas

- Las capturas de pantalla se almacenan en `target/screenshots/` y se enlazan en los reportes.
- Puedes personalizar la URL base y opciones de navegador en el archivo de configuración.
- El proyecto está preparado para integrarse con CI/CD y generar reportes automáticos.

---

## ¿Por qué esta solución es óptima?
- Mantenibilidad: Uso del patrón Page Object Model, facilitando la actualización y escalabilidad de los tests.
- Reportes enriquecidos: Integración con Serenity BDD y ExtentReports para reportes visuales, detallados y trazables.
- Evidencia automática: Captura y adjunta evidencias visuales en cada paso relevante, mejorando la auditoría y el análisis de fallos.
- Flexibilidad: Configuración centralizada y soporte para ejecución local o en CI/CD.
- Cobertura: Escenarios BDD claros y reutilizables, que validan tanto caminos felices como negativos.
- Facilidad de uso: Ejecución simple vía script o Maven, sin requerir conocimientos avanzados para operar la suite.

**Autor:**  
Alezander Estacio Rodríguez