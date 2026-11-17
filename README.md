# QA automation demo

![Tests](../../actions/workflows/tests.yml/badge.svg)

Small test automation project built with **Java 17, Selenium WebDriver, TestNG, REST Assured and Maven**,
using the Page Object pattern.

## What is tested

| Area | Site | Tests |
|------|------|-------|
| UI (Selenium) | https://www.saucedemo.com (public practice shop) | login (valid and invalid, data-driven), cart (add, remove, contents) |
| API (REST Assured) | https://jsonplaceholder.typicode.com (public practice API) | GET, POST, status codes, response body checks |

## Structure

```
src/test/java/com/katja/qa
├── base/BaseTest.java       starts/stops Chrome, screenshot on failure
├── pages/                   Page Objects (LoginPage, InventoryPage, CartPage)
├── tests/                   UI tests (LoginTests, CartTests)
├── api/PostsApiTests.java   API tests
└── utils/Config.java        settings (base URL, headless mode)
testng.xml                   test suite
```

## How to run

Requirements: JDK 17+, Maven (or IntelliJ's bundled Maven), Google Chrome.

* **IntelliJ:** File > Open > select `pom.xml` > Open as Project. Wait for Maven to download dependencies,
  then right-click `testng.xml` > Run.
* **Command line:** `mvn test`
* **Without a visible browser:** `mvn test -Dheadless=true`

Failed UI tests save a screenshot in `target/screenshots`.

## Continuous integration

Tests run automatically on every push with GitHub Actions (see `.github/workflows/tests.yml`),
in headless Chrome. Results are visible in the **Actions** tab.
