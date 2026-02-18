Feature: API Template Validation

  Scenario: 01 Successfully update a user by username
    Given I call "user" endpoint with http method "PUT"
    And I set path parameter "username" to "Debora"
    And I load body from file "user/createUser.json"
    When I execute the request
    Then the status code should be 200
    And the response body should contain "\"code\":200"


  Scenario: 02 Get user by username
    Given I call "user" endpoint with http method "GET"
    And I set path parameter "username" to "Debora"
    When I execute the request
    Then the status code should be 200
    And the response body should contain "Debora"