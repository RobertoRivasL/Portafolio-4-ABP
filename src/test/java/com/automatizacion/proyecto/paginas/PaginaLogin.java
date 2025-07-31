package com.automatizacion.proyecto.paginas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaginaLogin {
    private WebDriver driver;
    private WebDriverWait espera;

    // Localizadores de elementos de la página de Login
    @FindBy(id = "username")
    private WebElement campoUsuario;

    @FindBy(id = "password")
    private WebElement campoContrasena;

    @FindBy(css = "i.fa.fa-2x.fa-sign-in") // Selector para el botón de Iniciar Sesión
    private WebElement botonIniciarSesion;

    @FindBy(id = "flash") // Selector para mensajes de éxito/error (flash message)
    private WebElement mensajeFlash;

    @FindBy(css = "a.button.secondary") // Selector para el botón Cerrar Sesión en la página segura
    private WebElement botonCerrarSesion;

    public PaginaLogin(WebDriver driver, Duration tiempoEspera) {
        this.driver = driver;
        this.espera = new WebDriverWait(driver, tiempoEspera);
        PageFactory.initElements(driver, this);
    }

    /**
     * Navega a la URL de la página de login.
     * Espera a que la URL contenga "login" y el campo de usuario esté clickeable.
     * @param url La URL de la página de login.
     */
    public void navegarA(String url) {
        driver.get(url);
        espera.until(ExpectedConditions.urlContains("login"));
        espera.until(ExpectedConditions.elementToBeClickable(campoUsuario)); // Espera a que el campo de usuario sea clickeable
        System.out.println("Navegado a la URL: " + url); // Log de depuración
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
     * Hace clic en el botón de "Iniciar Sesión".
     * Espera a que el botón sea clickeable antes de hacer clic.
     */
    public void clickearBotonIniciarSesion() {
        espera.until(ExpectedConditions.elementToBeClickable(botonIniciarSesion));
        botonIniciarSesion.click();
        System.out.println("Click en el botón de Iniciar Sesión."); // Log de depuración
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
     * Verifica si el botón de "Cerrar Sesión" es visible.
     * @return true si el botón es visible, false en caso contrario.
     */
    public boolean esBotonCerrarSesionVisible() {
        try {
            espera.until(ExpectedConditions.visibilityOf(botonCerrarSesion));
            return botonCerrarSesion.isDisplayed();
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e) {
            System.err.println("El botón de cerrar sesión no es visible o no fue encontrado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si los elementos clave de la página de login están visibles y clickeables.
     * @return true si la página de login parece estar visible y lista para interacción, false en caso contrario.
     */
    public boolean esPaginaLoginVisible() {
        try {
            espera.until(ExpectedConditions.elementToBeClickable(campoUsuario));
            espera.until(ExpectedConditions.elementToBeClickable(campoContrasena));
            espera.until(ExpectedConditions.elementToBeClickable(botonIniciarSesion));
            System.out.println("La página de Login es visible y los elementos clave son clickeables."); // Log de depuración
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("La página de Login no es completamente visible o sus elementos no son clickeables dentro del tiempo de espera. Error: " + e.getMessage());
            return false;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.err.println("Uno de los elementos clave de la página de Login no fue encontrado. Error: " + e.getMessage());
            return false;
        }
    }
}