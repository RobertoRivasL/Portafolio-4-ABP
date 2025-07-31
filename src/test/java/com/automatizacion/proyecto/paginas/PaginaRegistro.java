package com.automatizacion.proyecto.paginas;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaginaRegistro {
    private WebDriver driver;
    private WebDriverWait espera;
    private JavascriptExecutor js;

    // Localizadores de elementos de la página de Registro
    @FindBy(id = "username")
    private WebElement campoUsuario;

    @FindBy(id = "password")
    private WebElement campoContrasena;

    @FindBy(id = "password-confirm")
    private WebElement campoConfirmarContrasena;

    @FindBy(xpath = "//button[contains(text(),'Register')]") // Selector para el botón de Registrar
    private WebElement botonRegistrar;

    @FindBy(id = "flash") // Selector para mensajes de éxito/error (flash message)
    private WebElement mensajeFlash;

    public PaginaRegistro(WebDriver driver, Duration tiempoEspera) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, tiempoEspera);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Navega a la URL de la página de registro.
     * Espera a que la URL contenga "register" y el campo de usuario esté clickeable.
     * @param url La URL de la página de registro.
     */
    public void navegarA(String url) {
        driver.get(url);
        espera.until(ExpectedConditions.urlContains("register"));
        espera.until(ExpectedConditions.elementToBeClickable(campoUsuario)); // Espera a que el campo de usuario sea clickeable
        desplazarAElemento(campoUsuario); // Asegura visibilidad después de la espera inicial
        System.out.println("Navegado a la URL: " + url); // Log de depuración
    }

    /**
     * Desplaza la vista del navegador hasta que el elemento especificado sea visible en la pantalla.
     * @param elemento El WebElement al que se desea desplazar.
     */
    private void desplazarAElemento(WebElement elemento) {
        js.executeScript("arguments[0].scrollIntoView(true);", elemento);
        try {
            Thread.sleep(500); // Pequeña pausa para asegurar que el scroll se complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrumpido durante el desplazamiento", e);
        }
    }

    /**
     * Ingresa el nombre de usuario en el campo correspondiente.
     * Espera a que el campo esté clickeable antes de enviar las teclas.
     * @param usuario El nombre de usuario a ingresar.
     */
    public void ingresarUsuario(String usuario) {
        espera.until(ExpectedConditions.elementToBeClickable(campoUsuario));
        campoUsuario.sendKeys(usuario);
        System.out.println("Ingresado usuario: " + usuario); // Log de depuración
    }

    /**
     * Ingresa la contraseña en el campo correspondiente.
     * Espera a que el campo esté clickeable antes de enviar las teclas.
     * @param contrasena La contraseña a ingresar.
     */
    public void ingresarContrasena(String contrasena) {
        espera.until(ExpectedConditions.elementToBeClickable(campoContrasena));
        campoContrasena.sendKeys(contrasena);
        System.out.println("Ingresado contraseña (oculto): [*****]"); // Log de depuración
    }

    /**
     * Ingresa la confirmación de la contraseña en el campo correspondiente.
     * Espera a que el campo esté clickeable antes de enviar las teclas.
     * @param confirmarContrasena La confirmación de la contraseña a ingresar.
     */
    public void ingresarConfirmarContrasena(String confirmarContrasena) {
        espera.until(ExpectedConditions.elementToBeClickable(campoConfirmarContrasena));
        campoConfirmarContrasena.sendKeys(confirmarContrasena);
        System.out.println("Ingresada confirmación de contraseña (oculto): [*****]"); // Log de depuración
    }

    /**
     * Hace clic en el botón de "Registrar".
     * Espera a que el botón sea clickeable antes de hacer clic.
     */
    public void clickearBotonRegistrar() {
        espera.until(ExpectedConditions.elementToBeClickable(botonRegistrar));
        botonRegistrar.click();
        System.out.println("Click en el botón de Registrar."); // Log de depuración
    }

    /**
     * Obtiene el texto del mensaje flash (éxito o error) que aparece en la página.
     * Espera a que el mensaje sea visible antes de obtener su texto.
     * @return El texto del mensaje flash.
     */
    public String obtenerTextoMensajeFlash() {
        espera.until(ExpectedConditions.visibilityOf(mensajeFlash));
        String mensaje = mensajeFlash.getText().trim();
        System.out.println("Mensaje Flash obtenido: " + mensaje); // Log de depuración
        return mensaje;
    }
    
    /**
     * Verifica si los elementos clave de la página de registro están visibles y clickeables.
     * @return true si la página de registro parece estar visible y lista para interacción, false en caso contrario.
     */
    public boolean esPaginaRegistroVisible() {
        try {
            espera.until(ExpectedConditions.elementToBeClickable(campoUsuario));
            espera.until(ExpectedConditions.elementToBeClickable(campoContrasena));
            espera.until(ExpectedConditions.elementToBeClickable(campoConfirmarContrasena));
            espera.until(ExpectedConditions.elementToBeClickable(botonRegistrar)); // También esperamos por el botón de registro
            System.out.println("La página de Registro es visible y los elementos clave son clickeables."); // Log de depuración
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("La página de Registro no es completamente visible o sus elementos no son clickeables dentro del tiempo de espera. Error: " + e.getMessage());
            return false;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.err.println("Uno de los elementos clave de la página de Registro no fue encontrado. Error: " + e.getMessage());
            return false;
        }
    }
}