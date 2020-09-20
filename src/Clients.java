import java.io.DataInput;
import java.io.DataInputStream;
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

//        public void abrirPuerto(){
//            try {
//                socketUsado = new Socket("127.0.0.1", 50000);
//                DataOutputStream datos = new DataOutputStream(socketUsado.getOutputStream());
//                datos.writeUTF("hola");
//                datos.close();
//
//            }
//            catch (UnknownHostException e1) {
//                e1.printStackTrace();
//            }
//            catch (IOException e1) {
//                System.out.println(e1.getMessage());
//            }
//        }

        public void enviarMsj(String msj, int destinatario){
            try{
                socketUsado = new Socket("127.0.0.1", destinatario);
                DataOutputStream datos = new DataOutputStream(socketUsado.getOutputStream());
                datos.writeUTF(msj);
                datos.close();
            }
            catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
            catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }




    }
}
