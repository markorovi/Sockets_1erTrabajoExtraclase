import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Interfaces {
    public static class Ventana extends JFrame implements ActionListener, Runnable {
        private Clients.Conexiones S;
        private JButton boton;
        private JLabel texto;
        private JLabel texto_usuario;
        private JLabel texto_destinatario;
        private JLabel port;
        private JScrollPane scroll;
        private JTextArea chat;
        private JTextField caja_puerto;
        private JTextField caja_texto;
        private JTextField caja_nombre;
        private int puerto = 0;



        public void setPuerto(int disponible) {
            this.puerto = disponible;
            this.S = new Clients.Conexiones(puerto);
        }

        public Ventana(int disponible) {
            caracteristicasVentanas();
            setPuerto(disponible);
            mostrarPuerto();
            abrirVentana();
        }

        public void mostrarPuerto() {
            this.port = new JLabel();
            this.port.setText("Tu puerto es el: " + puerto);
            this.port.setBounds(330, 10, 150, 25);
            this.add(port);
        }

        public void caracteristicasVentanas() {
            this.setTitle("Chat");
            this.setSize(500, 500);
            this.setLayout(null);
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        public void abrirVentana() {
            texto = new JLabel();
            caja_texto = new JTextField();
            boton = new JButton();
            chat = new JTextArea();
            scroll = new JScrollPane(chat);
            caja_puerto = new JTextField();
            caja_nombre = new JTextField();
            texto_destinatario = new JLabel();
            texto_usuario = new JLabel();

            texto.setText("Mensaje: ");
            texto.setBounds(25, 350, 100, 25);
            texto_usuario.setText("De: ");
            texto_usuario.setBounds(25, 380, 100, 25);
            texto_destinatario.setText("Para (puerto): ");
            texto_destinatario.setBounds(25, 410, 100, 25);
            caja_texto.setBounds(85, 350, 365, 25);
            chat.setEditable(false);
            boton.setText("Enviar Mensaje");
            boton.setBounds(280, 395, 150, 25);
            boton.addActionListener(this);
            scroll.setBounds(12, 40, 460, 300);
            caja_nombre.setBounds(85, 380, 130, 25);
            caja_puerto.setBounds(120, 410, 95, 25);

            this.add(texto);
            this.add(texto_destinatario);
            this.add(texto_usuario);
            this.add(caja_texto);
            this.add(boton);
            this.add(scroll);
            this.add(caja_puerto);
            this.add(caja_nombre);

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
                    chat.append(msj + "\n");
                    recibido.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String msj = caja_texto.getText();
                int destinatario = Integer.parseInt(caja_puerto.getText());
                chat.append("De ti para [" + destinatario + "]: " + msj + "\n");
                S.enviarMsj(msj, destinatario, caja_nombre.getText(), puerto);
            }
            catch (NumberFormatException exception) {
                System.out.println("Puerto desconocido");
            }
        }

    }
}
