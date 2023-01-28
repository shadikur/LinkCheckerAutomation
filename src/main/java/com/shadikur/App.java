package com.shadikur;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class App {

    public static void main(String[] args) {
        // Set up the web driver
		// Setup ChromeDriver
		WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        //Ask user to enter the URL
        System.out.println("Enter the URL: ");
        String getUrl = System.console().readLine();

        // Navigate to the website
        driver.get(getUrl);

        // Find all the links on the page
        List<WebElement> links = driver.findElements(By.tagName("a"));

        // Check the status of each link
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();

                if (responseCode >= 400) {
                    // Yellow for broken link
                    System.out.println(url + "\033[1;33m is a broken link with error code: \033[0m" + responseCode);
                } else {
                    // Console Colored Text Output, Green for valid link and Red for broken link
                    System.out.println(url + "\033[1;32mis a valid link\033[0m");
                }
            } catch (Exception e) {
                System.out.println(url + "\033[1;31m  is a broken link\033[0m");
            }
        }

        // Close the web driver
        driver.quit();
    }
}
