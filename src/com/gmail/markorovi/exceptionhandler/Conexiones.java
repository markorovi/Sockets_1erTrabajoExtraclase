package com.gmail.markorovi.exceptionhandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/** Clase que se utiliza para el envio de datos a través de los sockets
 * @author Marco Rodríguez
 * @version 1.0
 * @since  1.0
 */
public class Conexiones extends main{
        Socket socketUsado;

        Logger LOGGER = Logger.getLogger(Conexiones.class.getName());
        
        /**
         * Método que se encarga de trabajar con los sockets para así enviar el mensaje
         * @param msj El mensaje a enviar
         * @param destinatario El puerto al cual se le debe de enviar el mensaje
         * @param usuario El nombre con el que el emisor del mensaje se identificó
         * @param puerto El puerto que corresponde a la dirección desde la cual se envía el mensaje
         */

        public void enviarMsj(final String msj, final int destinatario, final String usuario, final int puerto) {
            try {
                socketUsado = new Socket("127.0.0.1", destinatario);
                final DataOutputStream datos = new DataOutputStream(socketUsado.getOutputStream());
                datos.writeUTF(puerto + ";" + usuario + ";" + msj);
                datos.close();
            } catch (final IOException e) {
                // new LoggingHandler(LOGGER, "El puerto de destino pertenece a un usuario que
                // no se encuentra activo. Mensaje de error: " + e.getMessage(), "info");
                final FileHandler fHandler = (new LoggingHandler().Handler(LOGGER));
                LOGGER.info("El puerto de destino pertenece a un usuario que no se encuentra activo. Mensaje de error: "
                        + e.getMessage());
                fHandler.close();
                JOptionPane.showMessageDialog(null, "Has creado un chat con un usuario no activo.");
            } catch (final IllegalArgumentException exception) {
                // new LoggingHandler(LOGGER, "Puerto introducido fuera del rango
                // (0<puerto<65535). Mensaje de error: " + exception.getMessage(), "warning");
                final FileHandler fHandler = (new LoggingHandler().Handler(LOGGER));
                LOGGER.warning("Puerto introducido fuera del rango (0<puerto<65535). Mensaje de error: " + exception.getMessage());
                fHandler.close();
                JOptionPane.showMessageDialog(null, "Puerto introducido fuera del rango (0<puerto<65535).");
            }
        }

}
