#Author mwmohrma

Feature: Add an ingredient
	As a user
	I want to be able to add ingredients
	So that the CoffeeMaker can contain
	Dynamic recipes


Scenario Outline: Add valid amount
	Given I have navigated to the Add Ingredient screen and the inventory is empty
	When I add an ingredient named <iName> with amount <iAmt>
	Then the ingredient is added to the system

Examples:
	| iName  | iAmt |
	| coffee | 3    |
	| nutmeg | 2    |
	| milk   | 1    |


Scenario Outline: Add invalid amount
	Given I have navigated to the Add Ingredient screen and the inventory is empty
	When I add an ingredient named <iName> with amount <iAmt>
	Then an error message is displayed

Examples:
	| iName | iAmt |
	| sugar | -1   |
	| cream | one  |
	| clove | -99  |