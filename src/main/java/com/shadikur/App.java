package com.shadikur;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

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
        options.addArguments("--whitelisted-ips=");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        WebDriver driver = new ChromeDriver(options);

        String url = null;
        // Check if a command line argument was passed for URL
        if (args.length > 0 && args[0].startsWith("-url=")) {
            url = args[0].substring(5);
        }

        // If URL was not passed as a command line argument, prompt the user to enter it
        if (url == null) {
            System.out.print("Enter the URL: ");
            Scanner scanner = new Scanner(System.in);
            url = scanner.nextLine();
        }

        // Navigate to the website
        driver.get(url);

        // Find all the links on the page
        List<WebElement> links = driver.findElements(By.tagName("a"));

        // Check the status of each link
        for (WebElement link : links) {
            String url1 = link.getAttribute("href");
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url1).openConnection();
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();

                if (responseCode >= 400) {
                    // Yellow for broken link
                    System.out.println(url1 + "\033[1;33m is a broken link with error code: \033[0m" + responseCode);
                } else {
                    // Console Colored Text Output, Green for valid link and Red for broken link
                    System.out.println(url1 + "\033[1;32mis a valid link\033[0m");
                }
            } catch (Exception e) {
                System.out.println(url1 + "\033[1;31m  is a broken link\033[0m");
            }
        }

        // Close the web driver
        driver.quit();
        System.out.println("Chrome has been closed!");
    }
}
