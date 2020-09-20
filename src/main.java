public class main {
    public static void main(String[] args){
        TrySockets.Puertos P = new TrySockets.Puertos();
        P.estaDisponibleServerSocket();

        Interfaces.Ventana V = new Interfaces.Ventana(P.getServer());
        V.setVisible(true);
    }
}
