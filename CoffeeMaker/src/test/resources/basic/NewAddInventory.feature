#Author: mwmohrma

Feature: Add Inventory
	As an Administrator
	I want to update the Inventory of the Coffee Machine
	So that ingredients do not run out and coffee can be made

Scenario Outline: Valid inventory updates
Given the inventory contains <original> of <ingredient>
When I add <amt> of <ingredient>
Then the inventiry is successfully updated

Examples:
  | original | ingredient | amt |
  | 0        | coffee     | 1   |
  | 0        | milk       | 1   |
  | 0        | sugar      | 1   |
  | 0        | chocolate  | 1   |
 
 
 
Scenario Outline: Invalid Inventory updates
Given the inventory contains <original> of <ingredient>
When I attempt to add <amt> of <ingredient>
Then an error occurs for <error>
Then the inventory is not updated

Examples:
  | original | ingredient | amt |
  | 0        | coffee     | -1   |
  | 0        | milk       | -1   |
  | 0        | sugar      | -1   |
  | 0        | chocolate  | -1   |