package com.gmail.markorovi.exceptionhandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MuchoTexto {
    private FileWriter escritor = null;

    public void escribir (String texto){

        try {
            escritor = new FileWriter("../archivo/logging.txt", true);
            BufferedWriter escritorBuffer = new BufferedWriter(escritor);
            escritorBuffer.append(texto + "\n");
            escritorBuffer.close();

        } 
        catch (IOException exception) {
            System.out.println("Problemas, " + exception.getMessage());
        }
        finally {
            if (escritor != null) {
                try {
                    escritor.close();
                } catch (IOException e) {
                    System.out.println("No se pudo cerrar el escritor. " + e.getMessage());
                }
            }
        }
    }
}
