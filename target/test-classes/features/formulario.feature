Caracterista: Validación de formulario de contacto
  Como usuario del sistema
  Quiero interactuar con el formulario de contacto
  Para garantizar que valide correctamente los datos ingresados

  @CP01 @formulario @happy-path
  Escenario: CP01 - Envío de formulario con datos completos
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
  Escenario: CP02 - Llenar el formulario sin nombre
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
  Escenario: CP03 - Formulario con email inválido
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
  Escenario: CP04 - Envío de formulario sin barrio
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
  Escenario: CP05 - Envío de formulario sin asunto
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