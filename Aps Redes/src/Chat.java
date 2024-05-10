
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;

import static javax.swing.JOptionPane.*;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;


public class Chat extends javax.swing.JFrame {

    private String nome;
    private Socket socket;
    private BufferedReader bufreader;
    private InputStreamReader inputstreamr;
    private boolean rodar;

    public Chat(String nome, String ip) {
        initComponents();
        rodar = true;
        this.nome = nome;
        try {
            socket = new Socket(ip, 5000);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha na conexão com o Servidor " + ip, "", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        Thread();
    }

    private void Thread() {
        Thread t = new Thread(new Runnable() {
            String mensagem;

            @Override
            public void run() {
                try {
                    inputstreamr = new InputStreamReader(socket.getInputStream());
                    bufreader = new BufferedReader(inputstreamr);
                    while ((mensagem = bufreader.readLine()) != null) {
                        mensagemRecebida.setText(mensagemRecebida.getText() + mensagem + "\n");
                        if (!rodar) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erro na conexão com o servidor.", "", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        t.start();
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mensagemRecebida = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        msgEnviada = new javax.swing.JTextArea();
        jButtonEnviarTexto = new javax.swing.JButton();
        jButtonEnviarImagem = new javax.swing.JButton(); // Botão para enviar imagem

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        mensagemRecebida.setEditable(false);
        mensagemRecebida.setColumns(20);
        mensagemRecebida.setFont(new java.awt.Font("Comic Sans MS", 0, 13));
        mensagemRecebida.setForeground(new java.awt.Color(0, 153, 51));
        mensagemRecebida.setRows(5);
        mensagemRecebida.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(mensagemRecebida);
        msgEnviada.setColumns(20);
        msgEnviada.setFont(new java.awt.Font("Comic Sans MS", 0, 13));
        msgEnviada.setForeground(new java.awt.Color(0, 153, 51));
        msgEnviada.setRows(5);
        jScrollPane2.setViewportView(msgEnviada);

        jButtonEnviarTexto.setText("Enviar");
        jButtonEnviarTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnviarTextoActionPerformed(evt);
            }
        });

        jButtonEnviarImagem.setText("Enviar Imagem");
        jButtonEnviarImagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarImagem();
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jButtonEnviarTexto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jButtonEnviarImagem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jButtonEnviarTexto)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonEnviarImagem))
                                        .addComponent(jScrollPane2))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void jButtonEnviarTextoActionPerformed(java.awt.event.ActionEvent evt) {
        String mensagem = nome + " diz: \n";
        try {
            PrintStream ps = new PrintStream(socket.getOutputStream());
            mensagem += msgEnviada.getText();
            ps.println(mensagem);
            ps.flush();
            msgEnviada.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Mensagem não enviada!", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enviarImagem() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                BufferedImage image = ImageIO.read(selectedFile);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                byte[] imageData = baos.toByteArray();
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(imageData);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao enviar imagem!", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    private javax.swing.JButton jButtonEnviarTexto;
    private javax.swing.JButton jButtonEnviarImagem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea mensagemRecebida;
    private javax.swing.JTextArea msgEnviada;
}