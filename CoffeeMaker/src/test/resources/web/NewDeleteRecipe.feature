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