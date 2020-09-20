import java.io.IOException;
import java.net.*;

public class TrySockets {
    public static class Puertos extends main {
        private int server = 50000;
        private String ip = "127.0.0.1";
        private boolean enUso = true;
        private boolean escuchando = false;

        public void setServer(int port) {
            this.server = port;
        }

        public int getServer() {
            return server;
        }

        public void setEnUso(boolean flag) {
            this.enUso = flag;
        }

        public void setEscuchando(boolean flag) {
            this.escuchando = flag;
        }

        public Puertos() {
            //estaDisponibleSocket();
            estaDisponibleServerSocket();
        }

//        public void estaDisponibleSocket() {
//            while (enUso) {
//                if (getPort() < 65535){
//                    try {
//                        new Socket(ip, port).close();
//                        setEnUso(true);
//                        setPort(getPort() + 1);;
//                    }
//                    catch (IOException e) {
//                        setEnUso(false);;
//                    }
//                }
//                else{
//                    System.out.println("No hay puertos libres");
//                    break;
//                }
//            }
//        }

        public void estaDisponibleServerSocket() {
            while (escuchando == false) {
                if (getServer() < 65535){
                    try {
                        new ServerSocket(server).close();
                        setEscuchando(true);
                    }
                    catch (IOException e) {
                        setServer(getServer() + 1);
                    }
                }
                else{
                    System.out.println("No hay puertos libres");
                    break;
                }
            }
        }
    }
}
