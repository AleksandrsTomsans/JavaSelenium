package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.Duration;
import java.util.*;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

public final class Software {


     /**
      * Creates a CSV file from a 2D list of strings.
      *
      * @param filePath The full path where the CSV file will be saved
      * @param data A list of rows, where each row is a list of string values
      *
      *<br>
      *<br>
      * Example:<br>
      *
      * DATA = Arrays.asList(
      *<pre>
      *     Arrays.asList("Name", "Age", "Town"),<br>
      *     Arrays.asList("Name", "Age", "Town"),<br>
      *     Arrays.asList("Name", "Age", "Town")</pre>
      *
      * );
      *<br>
      *<br>
      * String FILE_PATH = "C:\\Users\\name\\Desktop\\output.csv"
      * <br>
      * <br>
      * createCSVFile(FILE_PATH, DATA);
      * // Creates a CSV file at the specified path
      */
     public static void createCSVFile(String filePath, List<List<String>> data) {
          File file = new File(filePath);

          try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
               for (List<String> row : data) {
                    out.write(String.join(",", row));
                    out.newLine();
               }
               System.out.println("CSV file created successfully at " + filePath);
          } catch (IOException e) {
               e.printStackTrace();
          }
     }


     /**
      * Refreshes the current page once.
      *
      * This is an overloaded version of "refreshPage(driver)" that
      * uses a default repeat count of 1.
      *
      * @return Unit
      *
      *  Example:
      *  refreshPage(driver) // reloads the current page 1 times
      *  refreshPage(3, driver) // reloads the current page 3 times
      *
      * @param driver The WebDriver instance controlling the browser
      */
     public static void refreshPage(WebDriver driver) {
          driver.getCurrentUrl(); // default repeat = 1
     }

     /**
      * Reloads the current page in the browser.
      *
      * @param repeat Number of times to refresh the page (default is 1)
      * @return Unit
      *
      * Example:
      * refreshPage(3) // reloads the current page 3 times
      */
     public static void refreshPage( WebDriver driver, int repeat) {

          String url = driver.getCurrentUrl();
          int defaultRefresh = 0;

          while (defaultRefresh < repeat)
          {
               driver.get(url);
               println("Refresh page "+( defaultRefresh+1 )+" " +url);
               defaultRefresh++;
          }
     }

     /**
      * Clicks an element safely on the page.
      * Waits for the element to be visible and retries if an exception occurs.
      *
      * @param driver The WebDriver instance controlling the browser
      * @param xpath  XPath of the element to click
      * <br>
      * <br>
      * Example:
      * <br>
      * press(driver, "//button[@id='submit']");
      */
     public static void press(WebDriver driver, String xpath) {
          try {
               WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds wait
               wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
               WebElement element = driver.findElement(By.xpath(xpath));
               element.click();
          } catch (Exception e) {
               refreshPage(driver); // assumes you have the refreshPage method
               WebElement element = driver.findElement(By.xpath(xpath));
               element.click();
          }
     }

     /**
      * Returns the count of elements matching the given XPath.
      *
      * @param xpath XPath of target elements
      * @return Int – number of elements found (0 if none)
      *<br>
      * Example:
      * <br>
      * getItemCount("//div[@class='product']");
      */
     public static int getItemCount(WebDriver driver, String xpath) {

          try {
               // Find all elements matching the XPath expression
               List<WebElement> elements = driver.findElements(By.xpath(xpath));

               // Get the count of elements
               return elements.size();
          } catch (Exception e) {
               System.out.println("getItemCount Exception ");
               return 0;
          }
     }

     /**
      * Checks if the current page is a "404" or "Page not found".
      * @param driver WebDriver instance<br>
      * @return Boolean – true if page not found, false otherwise
      *
      * Example:
      * if (pnf(driver)) refreshPage();
      */

     public static boolean pnf(WebDriver driver) {
          boolean hasH1 = !driver.findElements(By.tagName("h1")).isEmpty();
          boolean hasSubtitle = !driver.findElements(By.className("NoMatch-Subtitle")).isEmpty();

          if (hasH1 && driver.findElements(By.tagName("h1")).get(0).getText().equals("404")) {
               System.out.println("h1 = 404, ");
               return true;

          }

          if (hasSubtitle && driver.findElements(By.className("NoMatch-Subtitle")).get(0).getText().equals("Page not found")) {
               System.out.println("h2 =  Page not found");
               return true;
          }

          return false;
     }



     /**
      * Waits until all elements matching the XPath are visible and clickable.<br>
      * <br>
      * @param driver WebDriver instance<br>
      * @param xpath XPath of target elements<br>
      * @return Unit<br>
      * <br>
      * Example:<br>
      * loadAllElements(driver, "//li[@class='menu-item']");<br>
      */
     public static void loadAllElements(WebDriver driver, String xpath) {

          WebDriverWait web1 = new WebDriverWait(driver, Duration.ofSeconds(3));

          try {
               web1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
          } catch (Exception e) {
               System.out.println("loadAllElements() Exception ");
          }

          int index = getItemCount(driver,xpath);

          WebDriverWait web = new WebDriverWait(driver, Duration.ofSeconds(3));

          try {
               for (int plp = 1; plp <= index; plp++) {
                    web.until(ExpectedConditions.elementToBeClickable(
                              By.xpath(xpath + "[" + plp + "]")));
               }
          } catch (Exception e) {
               System.out.print("loadAllElements() Exception, ");
          }
     }

     /**
      * Waits until a single element becomes visible.
      *
      * @param driver WebDriver instance<br>
      * @param item XPath of the element
      * @return Unit
      * <br>
      * Example:<br>
      * loadSingleElement(driver, "//h1[@class='title']");<br>
      */
     public static void loadSingleElement(WebDriver driver, String item) {

          WebDriverWait web1 = new WebDriverWait(driver, Duration.ofSeconds(1));
          web1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(item)));
     }

     /**
      * Retrieves text from an element using XPath or CSS selector.<br>
      *
      * Selector detection rules:<br>
      * - Starts with "/" or "(" → XPath<br>
      * - Otherwise → CSS selector
      *
      * @param driver WebDriver instance<br>
      * @param selector XPath or CSS selector<br>
      * @return String – text content of the element<br>
      *
      * Example:<br>
      * String name = text(driver, "//p[@class='product-name']");<br>
      */
     public static String text(WebDriver driver, String selector) {

          if (selector == null || selector.isEmpty()) {
               return "";
          }

          try {
               if (isXpath(selector)) {
                    return driver.findElement(By.xpath(selector)).getText();
               } else {
                    return driver.findElement(By.cssSelector(selector)).getText();
               }
          } catch (NoSuchElementException e) {
               return "";
          }
     }

     private static boolean isXpath(String selector) {
          return selector.startsWith("/") || selector.startsWith("(");
     }

     /**
      * Counts elements matching the XPath and retries if the count is higher than expected.
      *
      * @param driver WebDriver instance
      * @param xpath XPath of the elements
      * @param num Expected number of items
      * @return int – final count after retries
      * <br>
      * Example:<br>
      * int count = itemCount(driver, "//div[@class='product']", 10);<br>
      */
     public static int itemCount(WebDriver driver, String xpath, int num) {

          int elementsList = 0;
          int countReloads = 1;
          String page = driver.getCurrentUrl();
          WebDriverWait web = new WebDriverWait(driver, Duration.ofSeconds(3));

          try {
               while (countReloads <= 5) {

                    web.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

                    List<WebElement> elements = driver.findElements(By.xpath(xpath));
                    int itemsCounted = elements.size();

                    if (itemsCounted > num) {
                         elementsList = itemsCounted;
                         println(itemsCounted + " items found. Reloading page...");

                         driver.get(page);
                         countReloads++;
                    } else {
                         println("xpath.itemCount " + itemsCounted + " items");
                         return itemsCounted; // exit immediately
                    }
               }
          } catch (Exception e) {
               println("string.ItemCount Exception: " + e.getMessage());
          }

          return elementsList;
     }

     /**
      * Checks whether at least one element matching the given XPath locator
      * is present in the DOM.
      *
      * <p>This method uses {@code findElements()} instead of {@code findElement()}
      * to avoid throwing {@link org.openqa.selenium.NoSuchElementException}.
      * If no matching elements are found, an empty list is returned.</p>
      *
      * <p><b>Note:</b> The locator parameter must be a valid XPath expression.</p>
      *
      * @param driver  the active {@link WebDriver} instance
      * @param xpath the XPath locator as a {@link String}
      * @return {@code true} if at least one matching element is found;
      *         {@code false} otherwise
      *
      * <pre>
      * Example usage:
      * {@code
      * String NEXT_PAGE = "//button[@aria-label='Next']";
      *
      * while (isElementPresent(driver, NEXT_PAGE)) {
      *     press(driver, NEXT_PAGE);
      * }
      * }
      * </pre>
      */
     public static boolean isElementPresent(WebDriver driver, String xpath) {
          try {
               new WebDriverWait(driver, Duration.ofSeconds(1))
                         .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
               return true;
          } catch (Exception e) {
               return false;
          }
     }
//     public static int imageNotFound(WebDriver driver, String xpath, int num){
//
//          // Load all elements first
//          loadAllElements(driver, Variables.xpath.IMAGE_NOT_FOUND);
//
//          // Get count
//          int infNum = getItemCount(driver, Variables.xpath.IMAGE_NOT_FOUND);
//
//          List<List<String>> listListData = new ArrayList<>();
//
// // Images not found will count and report all details
//          if (infNum > 0) {
//
//               List<String> pageRow = new ArrayList<>();
//
//               pageRow.add(element);
//               pageRow.add("Page " + pageNum);
//               pageRow.add("Item " + itemNum);
//               pageRow.add(driver.getCurrentUrl());
//               pageRow.add(pldProduct.getText());
//               pageRow.add("Images not found " + infNum);
//
//               System.out.println("Images not found " + infNum);
//
//               listListData.add(pageRow);
//          }
//     }





     public static void println() {
          System.out.println();
     }

     public static void println(String s) {
          System.out.println(s);
     }

     public static void println(boolean b) {
          System.out.println(b);
     }

     public static void println(char c) {
          System.out.println(c);
     }

     public static void println(byte b) {
          System.out.println(b);
     }

     public static void println(short s) {
          System.out.println(s);
     }

     public static void println(int i) {
          System.out.println(i);
     }

     public static void println(long l) {
          System.out.println(l);
     }

     public static void println(float f) {
          System.out.println(f);
     }

     public static void println(double d) {
          System.out.println(d);
     }

     public static void println(char[] a) {
          System.out.println(a);
     }

     public static void println(byte[] a) {
          System.out.println(Arrays.toString(a));
     }

     public static void println(short[] a) {
          System.out.println(Arrays.toString(a));
     }

     public static void println(int[] a) {
          System.out.println(Arrays.toString(a));
     }

     public static void println(long[] a) {
          System.out.println(Arrays.toString(a));
     }

     public static void println(float[] a) {
          System.out.println(Arrays.toString(a));
     }

     public static void println(double[] a) {
          System.out.println(Arrays.toString(a));
     }

     public static void println(boolean[] a) {
          System.out.println(Arrays.toString(a));
     }

     public static void println(Object[] a) {
          System.out.println(Arrays.deepToString(a));
     }

     public static void println(List<?> list) {
          System.out.println(list);
     }

     public static void println(Set<?> set) {
          System.out.println(set);
     }

     public static void println(Map<?, ?> map) {
          System.out.println(map);
     }

     public static void println(Object o) {
          System.out.println(o);
     }
}
