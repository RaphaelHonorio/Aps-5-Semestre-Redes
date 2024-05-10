import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CadastroInterface extends JFrame {
    private JTextField textFieldNome;
    private JTextField textFieldEmail;
    private JPasswordField passwordField;

    public CadastroInterface() {
        setTitle("Cadastro de Usuário");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(labelNome);

        textFieldNome = new JTextField();
        textFieldNome.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(textFieldNome);

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

        JButton buttonCadastrar = new JButton("Cadastrar");
        buttonCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        buttonCadastrar.setBackground(new Color(30, 144, 255));
        buttonCadastrar.setForeground(Color.WHITE);
        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String email = textFieldEmail.getText();
                String senha = new String(passwordField.getPassword());

                if (cadastrar(nome, email, senha)) {
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário. Tente novamente.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(buttonCadastrar);

        getContentPane().add(panel);
    }

    private boolean cadastrar(String nome, String email, String senha) {
        try (Connection connection = MySQLConnection.getConnection()) {
            String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, senha);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}