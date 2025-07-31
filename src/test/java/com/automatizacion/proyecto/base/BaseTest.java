package com.automatizacion.proyecto.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected Properties configuracion;
    protected Duration tiempoEsperaPrueba;

    @BeforeMethod
    @Parameters({"navegador", "modoSinCabeza", "tiempo.espera.prueba"})
    public void configurar(@Optional("chrome") String navegador,
                      @Optional("false") String modoSinCabeza,
                      @Optional("30") String tiempoEsperaPruebaStr) {
        cargarConfiguracion();
        this.tiempoEsperaPrueba = Duration.ofSeconds(Long.parseLong(tiempoEsperaPruebaStr));

        boolean esModoSinCabeza = Boolean.parseBoolean(modoSinCabeza);

        if (navegador.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions opciones = new ChromeOptions();
            if (esModoSinCabeza) {
                opciones.addArguments("--headless=new");
            }
            opciones.addArguments("--window-size=1920,1080");
            opciones.addArguments("--disable-gpu");
            opciones.addArguments("--no-sandbox");
            opciones.addArguments("--disable-dev-shm-usage");
            opciones.addArguments("--remote-allow-origins=*"); // Necesario para Selenium 4.x en algunos entornos
            driver = new ChromeDriver(opciones);
        } else if (navegador.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions opciones = new FirefoxOptions();
            if (esModoSinCabeza) {
                opciones.addArguments("-headless");
            }
            driver = new FirefoxDriver(opciones);
        } else {
            throw new IllegalArgumentException("Navegador no soportado: " + navegador);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Espera implícita general
    }

    @AfterMethod
    public void desconfigurar() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void cargarConfiguracion() {
        configuracion = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            configuracion.load(fis);
        } catch (IOException e) {
            System.err.println("Error al cargar config.properties: " + e.getMessage());
            e.printStackTrace();
            // Considerar lanzar una RuntimeException si la configuración es crítica
        }
    }
}