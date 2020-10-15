package com.gmail.markorovi.exceptionhandler;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingHandler {

    public FileHandler Handler(final Logger LOGGER) {
        try {
            final FileHandler manejadorDeArchivos = new FileHandler("logging.txt", true);

            LOGGER.addHandler(manejadorDeArchivos);

            final SimpleFormatter formateador = new SimpleFormatter();

            manejadorDeArchivos.setFormatter(formateador);

            return manejadorDeArchivos;

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
