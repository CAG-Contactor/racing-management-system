Feature: A status for the current race should be retrievable

  Scenario: if there is no active races the current race queue should be inactive
    Given a user has logged in
    Given no active races
    When the user queries about current races
    Then there is no current races


  Scenario: if there is no active races the current race queue should be inactive
    Given a user has logged in
    Given no active races
    When the user registers for a race
    When the user queries about current races
    Then there is an active races