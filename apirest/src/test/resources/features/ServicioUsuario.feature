@todoslosservicios
Feature: Pruebas al servicio usuario

  @metodoget
  Scenario: Listar todos los usuarios
    Given Listar usuario
    When mostrar el listado de usuarios
    And validar códigos de respuesta "200"
    Then validar número de registros

  @metodoget
  Scenario: Mostrar usuario
    Given listar usuario con id "3"
    When mostrar información del usuario
    And validar códigos de respuesta "200"
    Then validar informacion de la consulta
      | email               | nombre | apellido |
      | emma.wong@reqres.in | Emma   | Wong     |

  @metodoget
  Scenario: Usuario no registrado
    Given listar usuario con id "23"
    Then validar códigos de respuesta "404"

  @metodopost
  Scenario: Crear usuario
    Given que no existe usuario registrado
    When  registrar datos del usuario
      | nombre    | puesto | codigo |
      | Alexander | QA     | 201    |
    Then validar códigos de respuesta "201"
    And  mostrar los datos del registro

  @metodoput
  Scenario: Actualizar usuarios
    Given que el usuario esté registrado
    When  actualizar datos del usuario
      | id | nombre    | puesto | codigo |
      | 2  | Alexander | QA     | 200    |
    Then validar códigos de respuesta "200"
    And  mostrar los datos del registro