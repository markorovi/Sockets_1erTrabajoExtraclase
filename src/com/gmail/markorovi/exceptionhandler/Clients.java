package com.gmail.markorovi.exceptionhandler;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

/** Clase que se utiliza para el envio de datos a través de los sockets
 * @author Marco Rodríguez
 * @version 1.0
 * @since  1.0
 */
public class Clients {
    public static class Conexiones extends main{
        Socket socketUsado;

        
        /**
         * Método que se encarga de trabajar con los sockets para así enviar el mensaje
         * @param msj El mensaje a enviar
         * @param destinatario El puerto al cual se le debe de enviar el mensaje
         * @param usuario El nombre con el que el emisor del mensaje se identificó
         * @param puerto El puerto que corresponde a la dirección desde la cual se envía el mensaje
         */

        Logger LOGGER = Logger.getLogger(Clients.class.getName());

        public void enviarMsj(String msj, int destinatario, String usuario, int puerto){
            try{
                socketUsado = new Socket("127.0.0.1", destinatario);
                DataOutputStream datos = new DataOutputStream(socketUsado.getOutputStream());
                datos.writeUTF(puerto + ";" + usuario + ";" + msj);
                datos.close();
            }
            catch (IOException e) {
                LOGGER.warning("El puerto de destino pertenece a un usuario que no se encuentra activo. Mensaje de error: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Has creado un chat con un usuario no activo.");
            }
            catch (IllegalArgumentException exception) {
                LOGGER.warning("Puerto introducido fuera del rango (0<puerto<65535). Mensaje de error: " + exception.getMessage());
                JOptionPane.showMessageDialog(null, "Puerto introducido fuera del rango (0<puerto<65535).");
            }
        }




    }
}
