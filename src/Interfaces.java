import javax.swing.*;
import java.awt.event.*;
import java.awt.Window.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Interfaces {
    public static class Ventana extends JFrame implements ActionListener, Runnable {
        private JLabel texto;
        private JTextField caja_texto;
        private JButton boton;
        private JTextArea chat;
        private JScrollPane scroll;
        private JLabel port;
        private int puerto = 0;
        private Clients.Conexiones S;
        private JTextField caja_puerto;

        public void setPuerto(int disponible) {
            this.puerto = disponible;
            this.S = new Clients.Conexiones(puerto);
        }



        public String getText() {
            return caja_texto.getText();
        }

        public Ventana(int disponible) {
            caracteristicasVentanas();
            setPuerto(disponible);
            mostrarPuerto();
            abrirVentana();
        }


        public void mostrarPuerto() {
            this.port = new JLabel();
            this.port.setText(Integer.toString(puerto));
            this.port.setBounds(300, 350, 100, 25);
            this.add(port);
        }

        public void caracteristicasVentanas() {
            this.setTitle("Prueba");
            this.setSize(500, 500);
            this.setLayout(null);
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.out.println("Uncomment following to open another window!");
                    e.getWindow().dispose();
                    System.out.println("JFrame Closed!");
                }
            });
        }

        public void abrirVentana() {
            texto = new JLabel();
            caja_texto = new JTextField();
            boton = new JButton();
            chat = new JTextArea();
            scroll = new JScrollPane(chat);
            caja_puerto = new JTextField();

            texto.setText("Inserte Nombre");
            texto.setBounds(50, 350, 100, 25);
            caja_texto.setBounds(150, 350, 100, 25);
            //chat.setBounds(12, 20, 450, 300);
            chat.setEditable(false);
            boton.setText("Mostrar Mensaje");
            boton.setBounds(50, 400, 200, 30);
            boton.addActionListener(this);
            scroll.setBounds(12, 20, 450, 300);
            caja_puerto.setBounds(300, 400, 70, 30);

            this.add(texto);
            this.add(caja_texto);
            this.add(boton);
           // this.add(chat);
            //this.add(scroll);
            this.add(scroll);
            this.add(caja_puerto);

            Thread actualizar = new Thread(this);
            actualizar.start();


        }



        @Override
        public void run() {
            try {
                ServerSocket listening = new ServerSocket(puerto);
                while (true){
                    Socket recibido = listening.accept();
                    DataInputStream datosEntrada = new DataInputStream(recibido.getInputStream());
                    String msj = datosEntrada.readUTF();
                    chat.append("\n" + msj);
                    recibido.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String msj = caja_texto.getText();
            chat.append("\n" + msj);
            int puerto = Integer.parseInt(caja_puerto.getText());

            S.enviarMsj(msj, puerto);





        }

    }
}
