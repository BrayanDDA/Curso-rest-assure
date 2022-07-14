Feature: Pruebas al servicio resources

  Scenario: Listar todos los recursos
    Given Listar recursos
    When mostrar el listado de recursos
    And validar código de respuesta "200"
    Then validar número de recursos

  Scenario: Mostrar Recurso
    Given listar recurso con id "2"
    When mostrar información del recurso
    And validar código de respuesta "200"
    Then validar informacion de la consulta del recurso
      | nombre       | año  | color   | pantone |
      | fuchsia rose | 2001 | #C74375 | 17-2031 |

  Scenario: Recurso no encontrado
    Given listar recurso con id "20"
    And validar código de respuesta "404"
