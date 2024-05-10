import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginInterface extends JFrame {
    private JTextField textFieldEmail;
    private JPasswordField passwordField;

    public LoginInterface() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(labelEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(textFieldEmail);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(labelSenha);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(passwordField);

        JButton buttonLogin = new JButton("Login");
        buttonLogin.setFont(new Font("Arial", Font.BOLD, 14));
        buttonLogin.setBackground(new Color(30, 144, 255));
        buttonLogin.setForeground(Color.WHITE);
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = textFieldEmail.getText();
                String senha = new String(passwordField.getPassword());

                if (login(email, senha)) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                } else {
                    JOptionPane.showMessageDialog(null, "Login falhou. Verifique suas credenciais.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(buttonLogin);

        getContentPane().add(panel);
    }

    private boolean login(String email, String senha) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, senha);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                dispose(); // Fecha a janela de login
                iniciarServidor(); // Inicia o servidor
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Login falhou. Verifique suas credenciais.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void iniciarServidor() {
        String nome = JOptionPane.showInputDialog(null, "Digite seu nome:", "", JOptionPane.PLAIN_MESSAGE);
        int escolha = JOptionPane.showOptionDialog(null,
                "Bem-vindo: " + nome + "\n\nEscolha uma opção:\n1) Servidor\n2) Cliente:",
                "Escolha",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"Servidor", "Cliente"},
                "Servidor");

        if (escolha == JOptionPane.YES_OPTION) {
            Conexao conexao = new Conexao(nome + " (Servidor)");
        } else {
            String ip = JOptionPane.showInputDialog(null,
                    "Digite o IP de conexão (Se vazio, localhost):", "", JOptionPane.PLAIN_MESSAGE);
            if (ip.isEmpty()) {
                ip = "127.0.0.1";
            }
            Chat chat = new Chat(nome, ip);
            chat.setVisible(true);
        }
    }
}
