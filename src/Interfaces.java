import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;

public class Interfaces {
    public static class Ventana extends JFrame implements ActionListener, Runnable {
        Map<String, java.util.List<String>> diccionario = new HashMap<>();

        private Clients.Conexiones S;
        private JButton boton;
        private JLabel texto;
        private JLabel texto_usuario;
        private JLabel texto_destinatario;
        private JLabel port;
        private JList lista_mensajes;
        private JScrollPane scroll_chat;
        private JScrollPane scroll_lista;
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
            this.port.setBounds(530, 10, 150, 25);
            this.add(port);
        }

        public void caracteristicasVentanas() {
            this.setTitle("Chat");
            this.setSize(700, 500);
            this.setLayout(null);
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        public void abrirVentana() {
            texto = new JLabel();
            caja_texto = new JTextField();
            boton = new JButton();
            chat = new JTextArea();
            scroll_chat = new JScrollPane(chat);
            caja_puerto = new JTextField();
            caja_nombre = new JTextField();
            texto_destinatario = new JLabel();
            texto_usuario = new JLabel();
            lista_mensajes = new JList();
            scroll_lista = new JScrollPane(lista_mensajes);

            texto.setText("Mensaje: ");
            texto.setBounds(225, 350, 100, 25);
            texto_usuario.setText("De: ");
            texto_usuario.setBounds(225, 380, 100, 25);
            texto_destinatario.setText("Para (puerto): ");
            texto_destinatario.setBounds(225, 410, 100, 25);
            caja_texto.setBounds(285, 350, 365, 25);
            chat.setEditable(false);
            boton.setText("Enviar Mensaje");
            boton.setBounds(480, 395, 150, 25);
            boton.addActionListener(this);
            scroll_chat.setBounds(212, 40, 460, 300);
            caja_nombre.setBounds(285, 380, 130, 25);
            caja_puerto.setBounds(320, 410, 95, 25);
            scroll_lista.setBounds(20, 40, 160, 400);
            scroll_lista.setFont(new Font("Arial", Font.ITALIC, 30));
            lista_mensajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lista_mensajes.addListSelectionListener(e -> {
                chat.selectAll();
                chat.replaceSelection("");
                for (String str : diccionario.get(String.valueOf(lista_mensajes.getSelectedValue()))) {
                    chat.append(str + "\n");
                }
            });

            this.add(texto);
            this.add(texto_destinatario);
            this.add(texto_usuario);
            this.add(caja_texto);
            this.add(boton);
            this.add(scroll_chat);
            this.add(caja_puerto);
            this.add(caja_nombre);
            this.add(scroll_lista);

            //mensajes.add("prueba");
            //lista_mensajes.setListData(mensajes.toArray());

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
                    String[] separacion = msj.split(";");
                    if (diccionario.containsKey(separacion[0])) {
                        diccionario.get(separacion[0]).add(separacion[1] + ":" + separacion[2]);
                    }
                    else {
                        java.util.List<String> historial_chat = new ArrayList<>();
                        historial_chat.add(separacion[1] + ": " + separacion[2]);
                        diccionario.put(String.valueOf(separacion[0]), historial_chat);

                    }

                    lista_mensajes.setListData(diccionario.keySet().toArray());
                    chat.selectAll();
                    chat.replaceSelection("");
                    for (String str : diccionario.get(String.valueOf(separacion[0]))) {
                        chat.append(str + "\n");
                    }
                    lista_mensajes.clearSelection();
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
                System.out.println(destinatario);
                //chat.append("Tu: " + msj + "\n");
                S.enviarMsj(msj, destinatario, caja_nombre.getText(), puerto);
                if (diccionario.containsKey(String.valueOf(destinatario))) {
                    diccionario.get(String.valueOf(destinatario)).add("TU: " + msj);
                }
                else {
                    java.util.List<String> historial_chat = new ArrayList<>();
                    historial_chat.add("TU: " + msj);
                    diccionario.put(String.valueOf(destinatario), historial_chat);
                }
                lista_mensajes.setListData(diccionario.keySet().toArray());
                chat.selectAll();
                chat.replaceSelection("");
                for (String str : diccionario.get(String.valueOf(destinatario))) {
                    chat.append(str + "\n");
                }
                lista_mensajes.clearSelection();
            }
            catch (NumberFormatException exception) {
                System.out.println("Puerto desconocido");
            }
        }

    }
}
