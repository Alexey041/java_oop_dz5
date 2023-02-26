import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.System.Logger.Level;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class ServerApp {
    public static void main(String[] args) {
/*         Logger logger = Logger.getAnonymousLogger();
        SimpleFormatter formatter = new SimpleFormatter();
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("log.txt");
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fileHandler); */

        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Сервер запущен, ожидаем соединение....");
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключился к серверу!");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            double res = 0;
            
            while (true) {
                String[] clientRequest = dataInputStream.readUTF().split(" ");
                //logger.log(Level.INFO, null, clientRequest);
                if (clientRequest.equals("end")) break;
                System.out.println("Мы получили строку: " + Arrays.toString(clientRequest));
                double first = Double.parseDouble(clientRequest[0]);
                double second = Double.parseDouble(clientRequest[2]);
                switch (clientRequest[1]) {
                    case "+":
                        res = first + second;
                        break;
                    case "-":
                        res = first - second;
                        break;
                    case "*":
                        res = first * second;
                        break;
                    case "/":
                        res = first / second;
                        break;
                }
                dataOutputStream.writeUTF("Результат: " + res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
