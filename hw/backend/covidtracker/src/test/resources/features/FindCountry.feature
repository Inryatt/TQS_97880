
Feature: Filter Country by Name

Scenario: Find Data for Finland Two Days Ago
  When I navigate to "http://localhost:5000/country"
  
  Then I should see the text "Country Data"
  And I write country "Finland"
  And I press Enter
  Then I should see the text "Finland"
