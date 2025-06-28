package com.devateleur.healthtrack.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.devateleur.healthtrack.services.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SeleniumTestBase {
    
    protected WebDriver driver;
    
    @LocalServerPort
    protected int port;
    
    protected String baseUrl;
    
    @Autowired
    protected UserService userService;
    
    @BeforeEach
    void setUp() {
        // Resetear el usuario antes de cada test para asegurar estado limpio
        if (userService != null) {
            userService.resetearUsuario();
        }
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Para ejecución sin ventana
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--remote-allow-origins=*");
        
        // Selenium 4.x tiene SeleniumManager integrado que gestiona automáticamente los drivers
        driver = new ChromeDriver(options);
        baseUrl = "http://localhost:" + port;
    }
    
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
