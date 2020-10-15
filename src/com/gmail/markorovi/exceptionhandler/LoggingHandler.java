package com.gmail.markorovi.exceptionhandler;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingHandler {

    private FileHandler manejadorDeArchivos;
    private SimpleFormatter formateador;

    public LoggingHandler(Logger LOGGER, String mensaje, String tipo) {
        try {
            FileHandler manejadorDeArchivos = new FileHandler("logger.log", true);

            LOGGER.addHandler(manejadorDeArchivos);

            SimpleFormatter formateador = new SimpleFormatter();

            manejadorDeArchivos.setFormatter(formateador);

            switch (tipo){
                case "info": 
                    LOGGER.info(mensaje);
                    break;
                    
                case "warning": 
                    LOGGER.warning(mensaje);
                    break;
    
                default: LOGGER.info(mensaje);
            }

            manejadorDeArchivos.close();

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
