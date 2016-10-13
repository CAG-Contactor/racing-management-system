Feature: A status for the current race should be retrievable

  Scenario: if no race is active the race status should be empty
    Given no active races
    When the client queries the current status
    Then the current status should be empty

  Scenario: if a race has been started a status
    Given a started race
    When the client queries the current status
    Then the current status should have state "ACTIVE"