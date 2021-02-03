#Author: mwmohrma

Feature: Add Inventory
	As an Administrator
	I want to update the Inventory of the Coffee Machine
	So that ingredients do not run out and coffee can be made

Scenario Outline: Valid inventory updates
Given the inventory update screen is open
When I add <amt> of <ingredient>
Then the inventory is successfully updated

Examples:
  | ingredient | amt |
  | coffee     | 1   |
  | milk       | 1   |
  | sugar      | 1   |
  | chocolate  | 1   |
 
 
 
Scenario Outline: Invalid Inventory updates
Given the inventory update screen is open
When I attempt to add <amt> of <ingredient>
Then the inventory is not updated

Examples:
  | ingredient | amt |
  | coffee     | -1  |
  | milk       | -1  |
  | sugar      | -1  |
  | chocolate  | -1  |