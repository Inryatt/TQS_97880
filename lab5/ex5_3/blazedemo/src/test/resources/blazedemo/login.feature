Feature: Login in practice site

  Scenario: Get Flight Paris - Buenos Aires
    When I navigate to "https://blazedemo.com/"
    And I select origin "Boston" and destination  "London"
    And I click Submit
    Then I should see the message "Flights from Boston to London:"
    And I select flight 2
    Then I should see the message "Your flight from TLV to SFO has been reserved."
    And I input "inputName" "V"
    And I input "address" "Some Random Street"
    And I input "city" "Night"
    And I input "state" "California"
    And I input "zipCode" "38572"
    And I input "nameOnCard" "V W"
    And I click Submit
    Then I should see the message "Thank you for your purchase today!"


  Scenario: Failure login
    When I navigate to "https://bonigarcia.dev/selenium-webdriver-java/login-form.html"
    And I login with the username "bad-user" and password "bad-password"
    And I click Submit
    Then I should be see the message "Invalid credentials"


  Scenario: Successful login
    When I navigate to "https://bonigarcia.dev/selenium-webdriver-java/login-form.html"
    And I login with the username "user" and password "user"
    And I click Submit
    Then I should be see the message "Login successful"
