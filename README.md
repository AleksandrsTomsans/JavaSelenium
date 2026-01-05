# Selenium Automation Project – Image Validation Tool

A **Java Selenium automation project** that scans a website to identify **missing product images** across menu sections and paginated product pages. Built as a **QA portfolio project** to demonstrate web automation, test logic, and data reporting skills.

## What This Project Does

* Launches Chrome using Selenium WebDriver
* Navigates through top-level menu sections
* Iterates through paginated product listings
* Opens each product page
* Detects and counts **broken or missing images**
* Logs failures with full page details
* Exports results to a **CSV report**

## Key Skills Demonstrated

* Selenium WebDriver automation
* Java control structures (nested loops, collections)
* XPath-based element handling
* Page navigation and synchronization
* Error handling (404 / missing elements)
* Test result reporting (CSV export)
* Modular test utilities and reusable methods

## Tech Stack

* **Java**
* **Selenium WebDriver**
* **WebDriverManager**
* **ChromeDriver**
* **Maven / IntelliJ (recommended)**

## Project Structure

* `Main.java` – Test execution and navigation logic
* `Variables` – Centralized XPath selectors and test data
* `Software` – Reusable helper methods (clicks, waits, counts, CSV export)

## Output

* CSV file containing:

  * Menu section
  * Page number
  * Product number
  * Product URL
  * Product name
  * Missing image count

