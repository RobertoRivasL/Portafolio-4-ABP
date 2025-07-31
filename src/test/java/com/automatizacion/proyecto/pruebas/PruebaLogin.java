package com.automatizacion.proyecto.pruebas;

import com.automatizacion.proyecto.base.BaseTest; 
import com.automatizacion.proyecto.paginas.PaginaLogin;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PruebaLogin extends BaseTest {

    @Test(description = "Caso de Prueba 1: Inicio de Sesión Exitoso")
    public void testInicioSesionExitoso() {
        // Obtener URLs y credenciales desde el archivo de configuración (config.properties)
        String urlPaginaLogin = configuracion.getProperty("login.url");
        String usuario = configuracion.getProperty("login.username");
        String contrasena = configuracion.getProperty("login.password");
        String rutaPaginaSegura = configuracion.getProperty("secure.page.path");
        String mensajeExito = configuracion.getProperty("login.success.message");

        // Instanciar el Page Object de la página de Login
        PaginaLogin paginaLogin = new PaginaLogin(driver, tiempoEsperaPrueba);
        paginaLogin.navegarA(urlPaginaLogin);

        // Aserción inicial: Verificar que la página de login es visible
        Assert.assertTrue(paginaLogin.esPaginaLoginVisible(), "La página de login debería estar visible.");

        // Realizar las acciones de login
        paginaLogin.ingresarUsuario(usuario);
        paginaLogin.ingresarContrasena(contrasena);
        paginaLogin.clickearBotonIniciarSesion();

        // Aserciones post-login:
        // 1. Verificar que la URL actual contiene la ruta a la página segura
        Assert.assertTrue(driver.getCurrentUrl().contains(rutaPaginaSegura), "Debería ser redirigido a la página segura.");
        // 2. Verificar que el mensaje de éxito se muestra y contiene el texto esperado
        Assert.assertTrue(paginaLogin.obtenerTextoMensajeFlash().contains(mensajeExito), "Mensaje de éxito no mostrado o incorrecto.");
        // 3. Verificar que el botón de cerrar sesión es visible en la página segura
        Assert.assertTrue(paginaLogin.esBotonCerrarSesionVisible(), "El botón de cerrar sesión debería estar visible.");
    }

    @Test(description = "Caso de Prueba 2: Usuario Inválido")
    public void testUsuarioInvalido() {
        // Obtener URLs, credenciales y mensajes desde el archivo de configuración
        String urlPaginaLogin = configuracion.getProperty("login.url");
        String usuarioInvalido = configuracion.getProperty("login.invalid.username");
        String contrasena = configuracion.getProperty("login.password");
        String mensajeUsuarioInvalido = configuracion.getProperty("login.invalid.username.message");

        // Instanciar el Page Object y navegar a la URL
        PaginaLogin paginaLogin = new PaginaLogin(driver, tiempoEsperaPrueba);
        paginaLogin.navegarA(urlPaginaLogin);

        // Aserción inicial: Verificar que la página de login es visible
        Assert.assertTrue(paginaLogin.esPaginaLoginVisible(), "La página de login debería estar visible.");

        // Realizar las acciones con usuario inválido
        paginaLogin.ingresarUsuario(usuarioInvalido);
        paginaLogin.ingresarContrasena(contrasena);
        paginaLogin.clickearBotonIniciarSesion();

        // Aserciones post-intento:
        // 1. Verificar que el mensaje de error para usuario inválido se muestra
        Assert.assertTrue(paginaLogin.obtenerTextoMensajeFlash().contains(mensajeUsuarioInvalido), "Mensaje de error para usuario inválido no mostrado o incorrecto.");
        // 2. Verificar que se permanece en la página de login
        Assert.assertTrue(driver.getCurrentUrl().contains(urlPaginaLogin), "Debería permanecer en la página de login.");
    }

    @Test(description = "Caso de Prueba 3: Contraseña Inválida")
    public void testContrasenaInvalida() {
        // Obtener URLs, credenciales y mensajes desde el archivo de configuración
        String urlPaginaLogin = configuracion.getProperty("login.url");
        String usuario = configuracion.getProperty("login.username");
        // CORREGIDO: `configuracion.hook.getProperty` a `configuracion.getProperty`
        String contrasenaInvalida = configuracion.getProperty("login.invalid.password");
        String mensajeContrasenaInvalida = configuracion.getProperty("login.invalid.password.message");

        // Instanciar el Page Object y navegar a la URL
        PaginaLogin paginaLogin = new PaginaLogin(driver, tiempoEsperaPrueba);
        paginaLogin.navegarA(urlPaginaLogin);

        // Aserción inicial: Verificar que la página de login es visible
        Assert.assertTrue(paginaLogin.esPaginaLoginVisible(), "La página de login debería estar visible.");

        // Realizar las acciones con contraseña inválida
        paginaLogin.ingresarUsuario(usuario);
        paginaLogin.ingresarContrasena(contrasenaInvalida);
        paginaLogin.clickearBotonIniciarSesion();

        // Aserciones post-intento:
        // 1. Verificar que el mensaje de error para contraseña inválida se muestra
        Assert.assertTrue(paginaLogin.obtenerTextoMensajeFlash().contains(mensajeContrasenaInvalida), "Mensaje de error para contraseña inválida no mostrado o incorrecto.");
        // 2. Verificar que se permanece en la página de login
        Assert.assertTrue(driver.getCurrentUrl().contains(urlPaginaLogin), "Debería permanecer en la página de login.");
    }
}