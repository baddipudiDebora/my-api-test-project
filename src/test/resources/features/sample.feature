Feature: API Template Validation

  Scenario: Validate GET endpoint
    When I send GET request to "/status"
    Then the response status should be 200
