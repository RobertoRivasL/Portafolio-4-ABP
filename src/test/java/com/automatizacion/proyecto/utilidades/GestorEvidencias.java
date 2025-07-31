package com.automatizacion.proyecto.utilidades;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestorEvidencias {

    public static String tomarCapturaPantalla(WebDriver driver, String nombreScreenshot) {
        String rutaDestino = "evidencias/";
        String marcaTiempo = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destino = new File(rutaDestino + nombreScreenshot + "_" + marcaTiempo + ".png");
        try {
            FileHandler.copy(screenshotFile, destino);
            System.out.println("Captura de pantalla guardada en: " + destino.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar la captura de pantalla: " + e.getMessage());
        }
        return destino.getAbsolutePath();
    }
}