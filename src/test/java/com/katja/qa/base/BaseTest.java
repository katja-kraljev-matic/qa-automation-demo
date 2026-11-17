package com.katja.qa.base;

import com.katja.qa.utils.Config;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Starts a fresh Chrome before every test and closes it afterwards.
 * Takes a screenshot (saved in target/screenshots) when a test fails.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        if (Config.isHeadless()) {
            options.addArguments("--headless=new");
            // Needed on CI servers (GitHub Actions)
            options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        }
        options.addArguments("--window-size=1920,1080");

        // Turn off Chrome's password pop-ups, they can block clicks
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        // Selenium Manager (built into Selenium 4) downloads the right chromedriver automatically
        driver = new ChromeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                saveScreenshot(result.getName());
            }
            driver.quit();
        }
    }

    private void saveScreenshot(String testName) {
        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path folder = Path.of("target", "screenshots");
            Files.createDirectories(folder);
            Files.copy(source.toPath(), folder.resolve(testName + ".png"),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Could not save screenshot: " + e.getMessage());
        }
    }
}
