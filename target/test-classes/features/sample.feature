Feature: API Template Validation

  Scenario: 01 Create a new pet successfully
  Given I pass a payload with json from path 'createPet'
    When I send POST request to "/pet"
#    Then the response status code should be 200
#    Then the response should contain pet name "doggie"
#    And the response should contain status "available"

 Scenario: 02 Create a new petstore successfully
 Given I pass a payload with json from path 'createStore'
   When I send POST request to "/store/order"



  Scenario: 03 Create a new petstore successfully
    Given I update the jsonpayload 'createStore' for field 'quantity' with value '120'
    When I send POST request to "/store/order"
