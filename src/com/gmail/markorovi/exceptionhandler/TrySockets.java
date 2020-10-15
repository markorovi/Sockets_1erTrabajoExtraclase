package com.gmail.markorovi.exceptionhandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/** Clase que verifica que el puerto para cada aplicación pueda ser abierto sin problemas
 * @author Marco Rodríguez
 * @version 1.0
 * @since  1.0
 */
public class TrySockets {

    private static final Logger LOGGER = Logger.getLogger(TrySockets.class.getName());

    public static class Puertos extends main {
        private int server = 50000;
        private boolean escuchando = false;

        /**
         * Asigna el valor del ServerSocket a utilizar
         * @param port Puerto del ServerSocket
         */
        public void setServer(final int port) {
            this.server = port;
        }

        /**
         * Obtiene el ServerSocket actual
         * 
         * @return Número de puerto
         */
        public int getServer() {
            return server;
        }

        /**
         * Asigna un valor booleano para así saber si el puerto esta a la escucha
         * 
         * @param flag Valor booleano correspondiente a la disponibilidad del puerto
         */
        public void setEscuchando(final boolean flag) {
            this.escuchando = flag;
        }

        /**
         * Constructor de la clase
         */
        public Puertos() {
            estaDisponibleServerSocket();
        }

        /**
         * Método que se encarga de recorrer todos los puertos inciando por el 50000
         * hasta encontrar uno disponible.
         */
        public void estaDisponibleServerSocket() {
            while (escuchando == false) {
                if (getServer() < 65535) {
                    try {
                        new ServerSocket(server).close();
                        setEscuchando(true);
                    } catch (final IOException e) {
                        final FileHandler fHandler = (new LoggingHandler().Handler(LOGGER));
                        LOGGER.info("El puerto " + getServer() + "está ocupado, intentando con siguiente.");
                        fHandler.close();
                        setServer(getServer() + 1);
                    }
                } else {
                    final FileHandler fHandler = (new LoggingHandler().Handler(LOGGER));
                    LOGGER.warning("No se encontró ningún puerto disponible.");
                    fHandler.close();
                    JOptionPane.showMessageDialog(null, "No se encontró ningún puerto disponible.");
                    break;
                }
            }
        }
    }
}
