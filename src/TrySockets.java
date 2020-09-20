import java.io.IOException;
import java.net.*;

public class TrySockets {
    public static class Puertos extends main {
        private int server = 50000;
        private boolean escuchando = false;

        public void setServer(int port) {
            this.server = port;
        }

        public int getServer() {
            return server;
        }

        public void setEscuchando(boolean flag) {
            this.escuchando = flag;
        }

        public Puertos() {
            estaDisponibleServerSocket();
        }

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
