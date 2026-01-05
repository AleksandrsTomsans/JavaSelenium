package org.example;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

import org.example.Variables.xpath;
import org.example.Variables.lists;

import static org.example.Software.*;

import static org.example.Software.println;
import static org.example.Software.refreshPage;
import static org.example.Software.press;
import static org.example.Software.pnf;
import static org.example.Software.text;
import static org.example.Software.itemCount;

public class Main {
     public static void main(String[] args) throws InterruptedException {

          WebDriverManager.chromedriver().setup();

          ChromeOptions chromeOptions = new ChromeOptions();
          chromeOptions.addArguments("start-maximized");
          // chromeOptions.addArguments("--headless"); // uncomment to run headless

          WebDriver driver = new ChromeDriver(chromeOptions);
          WebDriverWait web = new WebDriverWait(driver, Duration.ofSeconds(5));
          // Implicit wait
          // www.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

          List<List<String>> listListData = new ArrayList<>();



          driver.get(xpath.MAIN_PAGE);

          println("Browser works");


          int total_MenuSections = 8;
          
          //   1st for loop over top menu sections
          for (int loop_1st = 1; loop_1st < total_MenuSections;      loop_1st++)
          {
               String menuElement = driver.getTitle();
               String URL_1stLoop = driver.getCurrentUrl();

               press(driver, xpath.TOP_MENU_ELEMENT + "[" + loop_1st + "]");
               println(menuElement + URL_1stLoop);
               Thread.sleep(1000);

               if (pnf(driver)) {refreshPage(driver);
                    if (pnf(driver)) {
                         println(driver.getTitle()+" Still 404 after refresh: " + driver.getCurrentUrl());
                         continue;
                    }
               } else {
                    println("loop_2nd");
                    
                    //   2nd loop over pages for each menu sections
                    for (int pageNumber = 1; ;pageNumber++)
                    {
                         String URL = driver.getCurrentUrl();
                         println("      PageNumber = " + pageNumber);
                         
                         println(URL);
                         int productsPerPage = itemCount(driver, xpath.PRODUCT,12);
                         
                         
                         // 3rd loop of each item in the page
                         for (int product = 1; product <=productsPerPage; product++)
                         {
                              println("Item number " + product);
                              println("3rd loop, Products per page "+productsPerPage);
                              press(driver, xpath.PRODUCT + "[" + product + "]");
                              Thread.sleep(1000);

// Load all elements first
                              loadAllElements(driver, xpath.IMAGE_NOT_FOUND);
// Get count
                              int infNum = getItemCount(driver, xpath.IMAGE_NOT_FOUND);
                              println(infNum);

// Images not found will count and report all details
                              if (infNum > 0)
                              {
                                   List<String> pageRow = new ArrayList<>();
                                   
                                   pageRow.add(menuElement);
                                   pageRow.add("Page " + pageNumber);
                                   pageRow.add("Item " + product);
                                   pageRow.add(driver.getCurrentUrl());
                                   pageRow.add(text(driver,xpath.PLD_PRODUCT));
                                   pageRow.add("Images not found " + infNum);
                                   
                                   println("Images not found " + infNum);
                                   
                                   listListData.add(pageRow);
                              }
                              
                              driver.navigate().back();
                              
                         }


                         try {
                              press(driver, xpath.NEXT_PAGE);
                         } catch (Exception e) {
                              break;  // exit loop
                         }
                    }



               }

               
               println("loop_1st full run");
          }
          
          String folder = "C:\\Users\\besty\\Desktop\\Programs";
        
          createCSVFile(folder, listListData);
          
          
          
          driver.quit();
          println("Quit browser");




          











     }
}