Feature: the message can be retrieved

  Scenario: Create new player
    When call for create new player
    Then new playerId is returned

  Scenario: Create new game
    Given call for create new player
    And new playerId is returned
    When call for create new game
    Then new gameId is returned

  Scenario Outline: Winning paid round increases the balance
    Given create new player and game
    When play PAID round with <betAmount> euro and <winType> result
    Then game balance is equals to <expectedBalance>
    Examples:
      | betAmount | winType    | expectedBalance |
      | 1         | WIN_SMALL  | 5003            |
      | 2         | WIN_MEDIUM | 5020            |
      | 3         | WIN_BIG    | 5150            |

  Scenario Outline: Losing paid round decreases the balance
    Given create new player and game
    When play PAID round with <betAmount> euro and <winType> result
    Then game balance is equals to <expectedBalance>
    Examples:
      | betAmount | winType | expectedBalance |
      | 4         | LOSE    | 4996            |

  Scenario: Losing paid round which suppose to be free does not decreases the balance
    Given create new player and game
    And next round will be free
    When play PAID round with 2 euro and WIN_BIG result
    Then game balance is equals to 5100
    When play PAID round with 10 euro and LOSE result
    Then game balance is equals to 5100

  Scenario: Losing many rounds playing for free does not decrease the balance
    Given create new player and game
    When play FREE round with 5 euro and LOSE result
    When play FREE round with 6 euro and LOSE result
    When play FREE round with 7 euro and LOSE result
    Then game balance is equals to 5000

  Scenario Outline: Playing paid round with unacceptable bet amount return http error
    Given create new player and game
    When play PAID round with <betAmount> euro and <winType> result
    Then http response status code is <expectedResponseCode>
    And game balance is equals to <expectedBalance>
    Examples:
      | betAmount | winType   | expectedResponseCode | expectedBalance |
      | 10        | WIN_SMALL | 200                  | 5030            |
      | 11        | WIN_SMALL | 400                  | 5000            |
      | -1        | WIN_SMALL | 400                  | 5000            |
      | 0.5       | WIN_SMALL | 400                  | 5000            |

  Scenario: Losing all money does not allow to play anymore
    Given create new player and game
    And change game balance to 100
    When play PAID round with 10 euro and LOSE result 9 times
    And game balance is equals to 10
    When play PAID round with 10 euro and LOSE result
    And game balance is equals to 0
    When play PAID round with 10 euro and LOSE result
    Then http response status code is 400