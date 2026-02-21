Feature: API Template Validation

  Scenario: 01 POST with Simple primitive Json payload
    Given I call "user" endpoint with http method "PUT"
    And I set path parameter "username" to "Debora"
    And I load body from file "user/createUser"
    When I execute the request
    Then the status code should be 200
    And the response body should contain "\"code\":200"


  Scenario: 02 GET call passing Query Params
    Given I call "user" endpoint with http method "GET"
    And I set path parameter "username" to "Debora"
    When I execute the request
    Then the status code should be 200
    And the response body should contain "Debora"


  Scenario: 03 DELETE call passing Query Params
    Given I call "user" endpoint with http method "DELETE"
    And I set path parameter "username" to "Debora"
    When I execute the request
    Then the status code should be 200
    And the response body should contain "\"code\":200"


  Scenario: 04 PUT call with supports primitive values, nested objects, arrays of primitives, and arrays of objects
    Given I call "pet" endpoint with http method "POST"
    And I load body from file "pet/postPet"
    And I update the payload 'pet/postPet' for field 'category.name' with value 'mydoggie'
    When I execute the request
    Then the status code should be 200
    And the response body should contain "id"










