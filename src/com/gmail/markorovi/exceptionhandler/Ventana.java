package com.gmail.markorovi.exceptionhandler;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/** Clase que se utiliza para la generacion de la ventana
 * @author Marco Rodríguez
 * @version 1.0
 * @since  1.0
 */
public class Ventana extends JFrame implements ActionListener, Runnable {

    private static final Logger LOGGER = Logger.getLogger(Ventana.class.getName());

        Map<String, java.util.List<String>> diccionario = new HashMap<>();
        private Conexiones S;
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

        /**
         * Asigna el valor al puerto que la app está utilizando
         * @param disponible Puerto utilizado
         */

        public void setPuerto(final int disponible) {
            this.puerto = disponible;
            this.S = new Conexiones();
        }

        /**
         * Crea la ventana que la app utiliza
         * 
         * @param disponible Puerto que la ventana está utilizando
         */
        public Ventana(final int disponible) {
            caracteristicasVentanas();
            setPuerto(disponible);
            mostrarPuerto();
            abrirVentana();
        }

        /**
         * Agrega a la ventana la información sobre el puerto que está utilizando
         */
        public void mostrarPuerto() {
            this.port = new JLabel();
            this.port.setText("Tu puerto es el: " + puerto);
            this.port.setBounds(530, 10, 150, 25);
            this.add(port);
        }

        /**
         * Caracterizticas principales de la ventana
         */
        public void caracteristicasVentanas() {
            this.setTitle("Chat");
            this.setSize(700, 500);
            this.setLayout(null);
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        /**
         * Todos los witdgets de la ventana que se esta presentando, ademas de los
         * eventos para que los mismos se actualicen
         */
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
                for (final String str : diccionario.get(String.valueOf(lista_mensajes.getSelectedValue()))) {
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

            final Thread actualizar = new Thread(this);
            actualizar.start();
        }

        /**
         * Hilo que recibe cualquier dato que se le esté siendo enviando, y actualiza el
         * chat
         */
        @Override
        public void run() {
            try {
                final ServerSocket listening = new ServerSocket(puerto);
                while (true) {
                    final Socket recibido = listening.accept();
                    final DataInputStream datosEntrada = new DataInputStream(recibido.getInputStream());
                    final String msj = datosEntrada.readUTF();
                    final String[] separacion = msj.split(";");
                    if (diccionario.containsKey(separacion[0])) {
                        diccionario.get(separacion[0]).add(separacion[1] + ":" + separacion[2]);
                    } else {
                        final java.util.List<String> historial_chat = new ArrayList<>();
                        historial_chat.add(separacion[1] + ": " + separacion[2]);
                        diccionario.put(String.valueOf(separacion[0]), historial_chat);

                    }

                    lista_mensajes.setListData(diccionario.keySet().toArray());
                    chat.selectAll();
                    chat.replaceSelection("");
                    for (final String str : diccionario.get(String.valueOf(separacion[0]))) {
                        chat.append(str + "\n");
                    }
                    lista_mensajes.clearSelection();
                    recibido.close();
                }
            } catch (final IOException e) {
                // new LoggingHandler(LOGGER, "Hay problemas al intentar conectarse al puerto "
                // + puerto + ". Mensaje de error: " + e.getMessage(), "warning");
                final FileHandler fHandler = (new LoggingHandler().Handler(LOGGER));
                LOGGER.warning("Hay problemas al intentar conectarse al puerto " + puerto + ". Mensaje de error: "
                        + e.getMessage());
                fHandler.close();

            } catch (final ArrayIndexOutOfBoundsException e1) {
                // new LoggingHandler(LOGGER, "El mensaje que adjunto está vacío. Mensaje de
                // error: " + e1.getMessage(), "info");
                final FileHandler fHandler = (new LoggingHandler().Handler(LOGGER));
                LOGGER.info("El mensaje que adjunto está vacío. Mensaje de error: " + e1.getMessage());
                fHandler.close();
            }
        }

        /**
         * Se encarga de actualizar el chat, además de acceder a un objeto de la clase
         * Clients para que este envie el mensaje
         * 
         * @param e Evento que se activa al presionar el botón de enviar
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final String msj = caja_texto.getText();
                final int destinatario = Integer.parseInt(caja_puerto.getText());
                S.enviarMsj(msj, destinatario, caja_nombre.getText(), puerto);
                if (diccionario.containsKey(String.valueOf(destinatario))) {
                    diccionario.get(String.valueOf(destinatario)).add("TU: " + msj);
                } else {
                    final java.util.List<String> historial_chat = new ArrayList<>();
                    historial_chat.add("TU: " + msj);
                    diccionario.put(String.valueOf(destinatario), historial_chat);
                }
                lista_mensajes.setListData(diccionario.keySet().toArray());
                chat.selectAll();
                chat.replaceSelection("");
                for (final String str : diccionario.get(String.valueOf(destinatario))) {
                    chat.append(str + "\n");
                }
                lista_mensajes.clearSelection();
            } catch (final NumberFormatException exception) {
                // new LoggingHandler(LOGGER, "El puerto introducido no es válido. Mensaje de
                // error:" + exception.getMessage(), "warning");
                final FileHandler fHandler = (new LoggingHandler().Handler(LOGGER));
                LOGGER.warning("El puerto introducido no es válido. Mensaje de error:" + exception.getMessage());
                fHandler.close();
                JOptionPane.showMessageDialog(null, "Puerto desconocido");
            }
        }


}