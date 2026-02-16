Feature: API Template Validation

  Scenario: Create a new pet successfully
    Given a pet payload with json from path 'createPet'
    When I send POST request to "/pet"
  #  Then the response status code should be 200
  #  Then the response should contain pet name "doggie"
   # And the response should contain status "available"
