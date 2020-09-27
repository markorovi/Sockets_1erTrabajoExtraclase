import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

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
        public void enviarMsj(String msj, int destinatario, String usuario, int puerto){
            try{
                socketUsado = new Socket("127.0.0.1", destinatario);
                DataOutputStream datos = new DataOutputStream(socketUsado.getOutputStream());
                datos.writeUTF(puerto + ";" + usuario + ";" + msj);
                datos.close();
            }
            catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
            catch (IOException e1) {
                System.out.println(e1.getMessage());
                System.out.println("Usuario no activo");
                JOptionPane.showMessageDialog(null, "Haz creado un chat con un usuario no activo.");
            }
        }




    }
}
