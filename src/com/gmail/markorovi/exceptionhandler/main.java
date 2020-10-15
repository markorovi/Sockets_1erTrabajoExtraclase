package com.gmail.markorovi.exceptionhandler;

public class main {
    public static void main(String[] args) throws InterruptedException {        

        TrySockets.Puertos P = new TrySockets.Puertos();
        P.estaDisponibleServerSocket();

        Ventana V = new Ventana(P.getServer());
        V.setVisible(true);
    }
}
