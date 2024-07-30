Feature: API Testing with RestAssured

  Background:
    Given I have the base URL "https://jsonplaceholder.typicode.com"

  Scenario: GET request
    When I send a GET request to "/posts/1"
    Then the status code should be 200
    And the response should contain key "userId" with value "1"
    And the response should match the JSON
      """
      {
        "userId": 1,
        "id": 1,
        "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
      }
      """
    And the response time should be less than 2000 ms

  Scenario: POST request
    Given I have the headers
      | Content-Type | application/json |
    And I have the body
      """
      {
        "title": "foo",
        "body": "bar",
        "userId": 1
      }
      """
    When I send a POST request to "/posts"
    Then the status code should be 201
    And the response should contain key "title" with value "foo"
    And the response headers should be
      | Content-Type | application/json; charset=utf-8 |
    And the response should contain "foo"
    And the response structure should match
    | id    |
    | title |
    | body  |
    | userId|

  Scenario: PUT request
    Given I have the headers
      | Content-Type | application/json |
    And I have the body
      """
      {
        "id": 1,
        "title": "foo",
        "body": "bar",
        "userId": 1
      }
      """
    When I send a PUT request to "/posts/1"
    Then the status code should be 200
    And the response should contain key "title" with value "foo"
    And the response headers should be
      | Content-Type | application/json; charset=utf-8 |
    And the response should contain "foo"

  Scenario: DELETE request
    Given I have the headers
      | Content-Type | application/json |
    When I send a DELETE request to "/posts/1"
    Then the status code should be 200

  Scenario: PATCH request
    Given I have the headers
      | Content-Type | application/json |
    And I have the body
      """
      {
        "title": "patched_title"
      }
      """
    When I send a PATCH request to "/posts/1"
    Then the status code should be 200
    And the response should contain key "title" with value "patched_title"

  Scenario: HEAD request
    Given I have the headers
      | Content-Type | application/json |
    When I send a HEAD request to "/posts/1"
    Then the status code should be 200

  Scenario: OPTIONS request
    Given I have the headers
      | Content-Type | application/json |
    When I send an OPTIONS request to "/posts/1"
    Then the status code should be 204

  Scenario: Validate response contains key
    When I send a GET request to "/posts/1"
    Then the response should contain key "title"

  Scenario: Validate response contains specific value
    When I send a GET request to "/posts/1"
    Then the response should contain "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"

  Scenario: Validate response has a maximum size
    When I send a GET request to "/posts"
    Then the response should have a maximum size of 100

  Scenario: Validate response is sorted
    When I send a GET request to "/posts"
    Then the response should be sorted by "id" in "asc" order
