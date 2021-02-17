#Author: mwmohrma

Feature: Delete a recipe
	As a system user
	I want to be able to delete recipes
	To remove invalid and unecessary ones
	





Scenario Outline: Delete recipe by name
	Given there is a recipe called <recipeName>
	When I delete the recipe with name <recipeName>
	Then the recipe is deleted from the system
	
Examples:
	| recipeName |
	| Latte      |
	| Mocha      |
	


Scenario Outline: Delete all recipes by name
	Given there are recipes called <name1> and <name2>
	When I delete the recipes with delete all option
	Then they are deleted
	
Examples:
	| name1 | name2 |
	| Latte | Mocha |
	| Cap   | Cup   |