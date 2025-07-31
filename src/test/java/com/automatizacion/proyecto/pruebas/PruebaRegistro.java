package com.automatizacion.proyecto.pruebas;

// Importaciones necesarias para la prueba de Registro
import com.automatizacion.proyecto.base.BaseTest; // Importa la clase BaseTest
import com.automatizacion.proyecto.paginas.PaginaRegistro; // Importa el Page Object de la Página de Registro
import org.testng.Assert; // Importa la clase Assert de TestNG para las aserciones
import org.testng.annotations.Test; // Importa la anotación Test de TestNG
import com.github.javafaker.Faker; // Importa la clase Faker para la generación de datos aleatorios

public class PruebaRegistro extends BaseTest {

    private Faker faker = new Faker(); // Instancia de Faker para generar datos de prueba

    @Test(description = "Caso de Prueba: Registro Exitoso")
    public void testRegistroExitoso() {
        // Obtener URLs y mensajes desde el archivo de configuración
        String urlPaginaRegistro = configuracion.getProperty("register.url");
        String mensajeExito = configuracion.getProperty("register.success.message");

        // Generar datos aleatorios para un nuevo usuario
        String nuevoUsuario = faker.internet().emailAddress();
        String nuevaContrasena = faker.internet().password(8, 12, true, true, true); // Contraseña aleatoria segura

        // Instanciar el Page Object de la página de Registro
        PaginaRegistro paginaRegistro = new PaginaRegistro(driver, tiempoEsperaPrueba);
        paginaRegistro.navegarA(urlPaginaRegistro); // Navegar a la URL de registro

        // Aserción inicial: Verificar que la página de registro es visible
        Assert.assertTrue(paginaRegistro.esPaginaRegistroVisible(), "La página de registro debería estar visible.");

        // Realizar las acciones de registro
        paginaRegistro.ingresarUsuario(nuevoUsuario);
        paginaRegistro.ingresarContrasena(nuevaContrasena);
        paginaRegistro.ingresarConfirmarContrasena(nuevaContrasena); // Las contraseñas coinciden
        paginaRegistro.clickearBotonRegistrar();

        // Aserciones post-registro:
        // Verificar que el mensaje de éxito de registro se muestra y contiene el texto esperado
        Assert.assertTrue(paginaRegistro.obtenerTextoMensajeFlash().contains(mensajeExito), "Mensaje de éxito de registro no mostrado o incorrecto.");
        // Después de un registro exitoso, la aplicación podría redirigir a otra página o mostrar un pop-up.
        // Aquí se asume que se muestra un mensaje flash en la misma página o en una de confirmación.
    }

    @Test(description = "Caso de Prueba: Registro con Contraseñas No Coincidentes")
    public void testRegistroContrasenasNoCoincidentes() {
        // Obtener URLs y mensajes desde el archivo de configuración
        String urlPaginaRegistro = configuracion.getProperty("register.url");
        String mensajeError = configuracion.getProperty("register.password.mismatch.message");

        // Generar datos aleatorios para usuario y contraseñas que NO coinciden
        String nuevoUsuario = faker.internet().emailAddress();
        String nuevaContrasena = faker.internet().password(8, 12, true, true, true);
        String confirmarContrasena = nuevaContrasena + "noCoincide"; // Contraseña que no coincide intencionadamente

        // Instanciar el Page Object y navegar a la URL
        PaginaRegistro paginaRegistro = new PaginaRegistro(driver, tiempoEsperaPrueba);
        paginaRegistro.navegarA(urlPaginaRegistro);

        // Aserción inicial: Verificar que la página de registro es visible
        Assert.assertTrue(paginaRegistro.esPaginaRegistroVisible(), "La página de registro debería estar visible.");

        // Realizar las acciones de registro con contraseñas que no coinciden
        paginaRegistro.ingresarUsuario(nuevoUsuario);
        paginaRegistro.ingresarContrasena(nuevaContrasena);
        paginaRegistro.ingresarConfirmarContrasena(confirmarContrasena);
        paginaRegistro.clickearBotonRegistrar();

        // Aserciones post-intento:
        // 1. Verificar que el mensaje de error para contraseñas no coincidentes se muestra
        Assert.assertTrue(paginaRegistro.obtenerTextoMensajeFlash().contains(mensajeError), "Mensaje de error para contraseñas no coincidentes no mostrado o incorrecto.");
        // 2. Verificar que se permanece en la página de registro después del intento fallido
        Assert.assertTrue(driver.getCurrentUrl().contains(urlPaginaRegistro), "Debería permanecer en la página de registro después de un intento fallido.");
    }
    
    @Test(description = "Caso de Prueba: Registro con Usuario Existente (si aplica)")
    public void testRegistroUsuarioExistente() {
        // Para este test, la aplicación debe mostrar un mensaje específico si el usuario ya existe.
        // Aquí usaremos un usuario conocido (el de login) para simular un usuario existente.
        String urlPaginaRegistro = configuracion.getProperty("register.url");
        String usuarioExistente = configuracion.getProperty("login.username"); // Usuario "practice" en config.properties
        String nuevaContrasena = faker.internet().password(8, 12, true, true, true);
        String mensajeUsuarioExistente = configuracion.getProperty("register.existing.user.message"); 

        // Instanciar el Page Object y navegar a la URL
        PaginaRegistro paginaRegistro = new PaginaRegistro(driver, tiempoEsperaPrueba);
        paginaRegistro.navegarA(urlPaginaRegistro);

        // Aserción inicial: Verificar que la página de registro es visible
        Assert.assertTrue(paginaRegistro.esPaginaRegistroVisible(), "La página de registro debería estar visible.");

        // Realizar las acciones de registro con un usuario que ya existe
        paginaRegistro.ingresarUsuario(usuarioExistente);
        paginaRegistro.ingresarContrasena(nuevaContrasena);
        paginaRegistro.ingresarConfirmarContrasena(nuevaContrasena);
        paginaRegistro.clickearBotonRegistrar();

        // Aserciones post-intento:
        // 1. Verificar que el mensaje de error para usuario existente se muestra
        Assert.assertTrue(paginaRegistro.obtenerTextoMensajeFlash().contains(mensajeUsuarioExistente), "Mensaje de error para usuario existente no mostrado o incorrecto.");
        // 2. Verificar que se permanece en la página de registro después del intento fallido
        Assert.assertTrue(driver.getCurrentUrl().contains(urlPaginaRegistro), "Debería permanecer en la página de registro después de intentar registrar un usuario existente.");
    }
}