import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class ImageChat {

    // Método para enviar uma imagem selecionada pelo usuário
    public static void enviarImagem(Socket socket) {
        try {
            // Criar um seletor de arquivos
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                // Carregar a imagem selecionada pelo usuário
                BufferedImage image = ImageIO.read(selectedFile);

                // Converter a imagem para bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                byte[] imageData = baos.toByteArray();

                // Enviar os bytes da imagem
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(imageData);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para receber uma imagem
    public static BufferedImage receberImagem(Socket socket) {
        try {
            // Receber os bytes da imagem
            InputStream inputStream = socket.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] imageData = baos.toByteArray();

            // Converter os bytes de volta para uma imagem
            ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}