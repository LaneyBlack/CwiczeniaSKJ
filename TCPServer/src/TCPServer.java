import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer {
    static Integer numberOfClients = 0;
    static ArrayList<Socket> clients = new ArrayList<>();

    static ArrayList<Integer> firstNumbers = new ArrayList<>();

    public static class ServerThread extends Thread {
        private final Socket socket;

        public ServerThread(Socket socket) {
            super();
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                System.out.println(clients);
                synchronized (numberOfClients) {
                    numberOfClients++;
                }
                System.out.println("==========Connected:" + numberOfClients);
                String input;
                System.out.println("---------------------------------Task1---------------------------------For-" + numberOfClients);
                do {
                    input = in.readLine();
                } while (input == null);
                System.out.println("From Client " + input);
                synchronized (firstNumbers) {
                    firstNumbers.add(Integer.parseInt(input));
                }
                out.println(input);
                System.out.println("---------------------------------Task2---------------------------------For-" + numberOfClients);
                int answer1 = GCDLots(firstNumbers);
                System.out.println(answer1);
                out.println(answer1);
                System.out.println("---------------------------------Task3---------------------------------For-" + numberOfClients);
                input = null;
                do {
                    input = in.readLine();
                } while (input == null);
                System.out.println("Input:" + input);
                input = input.replace("7", "");
                System.out.println(input);
                out.println(input);
                System.out.println("---------------------------------Task4---------------------------------For-" + numberOfClients);
                input = null;
                input = in.readLine();
                System.out.println(input);
                int number = Integer.parseInt(input);
                int k = 0;
                do{
                    k++;
                } while (Math.pow(k+1, 3) < number);
                System.out.println("Answer " + k);
                out.println(k);
                System.out.println("---------------------------------Task5---------------------------------For-" + numberOfClients);
                System.out.println(7477);
                out.println(7477);
                System.out.println("---------------------------------Task6---------------------------------For-" + numberOfClients);
                int answer6 = sum(firstNumbers);
                System.out.println(answer6);
                out.println(answer6);
                System.out.println("----------===----------All Tasks Ended----------===----------");
                input = null;
                do {
                    input = in.readLine();
                } while (input == null);
                System.out.println("!!! The Flag is " + input);
                synchronized (firstNumbers){
                    firstNumbers = new ArrayList<>();
                }
                synchronized (clients){
                    clients = new ArrayList<>();
                }
            } catch (IOException e1) {
                // do nothing
                System.out.println('1');
            }

            try {
                socket.close();
            } catch (IOException e) {
                // do nothing
                System.out.println('2');
            }
        }
    }

    public void listenSocket(int port) {
        ServerSocket server = null;
        Socket client = null;
        try {
            server = new ServerSocket(port);
//            server = new ServerSocket(port, 1, new InetSocketAddress("172.23.67.147", port));
        } catch (IOException e) {
            System.out.println("Could not listen");
            System.exit(-1);
        }
        System.out.println("Server IP: " + server.getInetAddress());
        System.out.println("Server listens on port: " + server.getLocalPort());
        while (true) {
            try {
                client = server.accept();
                clients.add(client);
            } catch (IOException e) {
                System.out.println("Accept failed");
                System.exit(-1);
            }
            //New Thread to every client
            (new ServerThread(client)).start();
        }

    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Too few parameters:");
            System.out.println("\tGot - " + args.length);
            System.out.println("\tExpected - 1 and more");
            return;
        }
        //Server port
        int port = Integer.parseInt(args[0]);
        TCPServer server = new TCPServer();
        server.listenSocket(port);
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

    public static int gcdByEuclidsAlgorithm(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcdByEuclidsAlgorithm(n2, n1 % n2);
    }

    public static int GCDLots(ArrayList<Integer> numbers) {
        int min = numbers.get(0);
        for (int i = 0; i < numbers.size() - 1; i++) {
            min = Math.min(min, (Math.min(numbers.get(i), numbers.get(i + 1))));
        }
        for (int i = min; i >= 1; i--) {
            boolean ret = true;
            for (int value : numbers)
                if (!(value % i == 0)) {
                    ret = false;
                    break;
                }
            if (ret) {
                return i;
            }
        }
        return -1;
    }

    public static int sum(ArrayList<Integer> list) {
        int sum = 0;
        for (Integer value : list) {
            sum += value;
        }
        return sum;
    }
}
