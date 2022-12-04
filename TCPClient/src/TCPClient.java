import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class TCPClient {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String address = "172.21.48.48"; //here you should enter IP address to connect with prowadzacy
        //"192.125.123.LiczbaMOD64
        int port = 13165; //port to connect with

        try {
//            socket = new Socket(address, port, InetAddress.getByName("192.168.0.66"), 5007);
            socket = new Socket();
            socket.connect(new InetSocketAddress(address, port), 500);
            out = new PrintWriter(socket.getOutputStream(), true); //to server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //from server
        } catch (UnknownHostException e) {
            System.out.println("Unknown host");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("No I/O");
            System.exit(-1);
        }

        System.out.println("My Address: " + socket.getLocalAddress() + ":" + socket.getLocalPort());
        System.out.println("Server Address: " + address + ":" + port);
        out.println("198817"); //flaga
        out.println("172.23.67.57:7477"); //my IP:Port

//        out.println("Connection established");
//        String flag;
//        do {
//            flag = in.readLine();
//        } while (flag == null);
//        if (flag.equals("23432")) {
//            if (readFromBase(in).equals(socket.getLocalAddress().toString().replace("/", ""))) {
//                out.println("240");
//                out.println("33");
//                out.println("81");
//                if (GCDThree(240, 33, 81) == Integer.parseInt(readFromBase(in))) {
//                    out.println("29138420220");
//                    if (readFromBase(in).equals("9138400")) {
//                        out.println("Good Job");
//                    } else
//                        out.println("Task5 bad input");
//                } else
//                    out.println("Task4 bad input");
//            } else
//                out.println("Task3 bad input");
//        } else
//            out.println("Task2 bad input");
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Cannot close the socket");
            System.exit(-1);
        }
    }

    public static int greatestCommonDivisor(int val1, int val2) {
        while (val1 != val2) {
            if (val1 > val2)
                val1 = val1 - val2;
            else
                val2 = val2 - val1;
        }
        return 1;
    }

    public static int GCDThree(int num1, int num2, int num3) {
        int min = Math.min(num1, Math.min(num2, num3));
        for (int i = min; i >= 1; i--)
            if ((num1 % i == 0) && (num2 % i == 0) && (num3 % i == 0))
                return i;
        return -1;
    }

    public static String readFromBase(BufferedReader input) throws IOException {
        String answer;
        do {
            answer = input.readLine();
        } while (answer == null);
        return answer;
    }
}
