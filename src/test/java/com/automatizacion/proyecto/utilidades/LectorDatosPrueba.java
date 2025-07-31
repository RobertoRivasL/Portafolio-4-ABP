package com.automatizacion.proyecto.utilidades;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorDatosPrueba {

    public static Object[][] leerDatosCSV(String rutaArchivo) {
        List<String[]> data = new ArrayList<>();
        String line;
        boolean isFirstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Saltar cabecera
                    continue;
                }
                String[] values = line.split(","); // Asume CSV delimitado por comas
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toArray(new Object[0][0]);
    }
}