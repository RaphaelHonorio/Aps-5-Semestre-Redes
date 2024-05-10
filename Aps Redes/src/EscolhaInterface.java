import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EscolhaInterface extends JFrame {
    public EscolhaInterface() {
        setTitle("Escolha");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));

        JButton buttonLogin = new JButton("Login");
        buttonLogin.setFont(new Font("Arial", Font.BOLD, 14));
        buttonLogin.setBackground(new Color(30, 144, 255));
        buttonLogin.setForeground(Color.WHITE);
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela de escolha
                new LoginInterface().setVisible(true); // Abre a janela de login
            }
        });
        panel.add(buttonLogin);

        JButton buttonCadastro = new JButton("Cadastro");
        buttonCadastro.setFont(new Font("Arial", Font.BOLD, 14));
        buttonCadastro.setBackground(new Color(50, 205, 50));
        buttonCadastro.setForeground(Color.WHITE);
        buttonCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela de escolha
                new CadastroInterface().setVisible(true); // Abre a janela de cadastro
            }
        });
        panel.add(buttonCadastro);

        getContentPane().add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EscolhaInterface().setVisible(true);
            }
        });
    }
}