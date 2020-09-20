import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class Clients {
    public static class Conexiones extends main{
        int server = 0;
        Socket socketUsado;

        public void setPuerto(int disponible) {
            this.server = disponible;
        }

        public Conexiones(int puerto){
            setPuerto(puerto);
        }

        public void enviarMsj(String msj, int destinatario, String usuario, int puerto){
            try{
                socketUsado = new Socket("127.0.0.1", destinatario);
                DataOutputStream datos = new DataOutputStream(socketUsado.getOutputStream());
                datos.writeUTF(usuario + " [" + puerto + "]: " + msj);
                datos.close();
            }
            catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
            catch (IOException e1) {
                System.out.println(e1.getMessage());
                System.out.println("Usuario no activo");
            }
        }




    }
}
