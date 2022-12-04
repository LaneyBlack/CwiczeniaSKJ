import java.io.IOException;
import java.math.BigDecimal;
import java.net.*;
import java.util.ArrayList;

public class UDPClient {

    private static int port;
    private static String ip;
    private DatagramSocket socket;
    private InetAddress address;

    public UDPClient() {
        try {
            socket = new DatagramSocket(7477); //my port to listen on
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host");
            System.exit(-1);
        } catch (SocketException e) {
            System.out.println("Socket Error");
            System.exit(-1);
        }
    }

    public void sendMsg(String msg) {
        byte[] buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("No IO");
            System.exit(-1);
        }
    }

    public String receiveMsg() {
        System.out.println("Receiving message...");
        byte[] buf = new byte[1000];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packet);
            port = packet.getPort(); //receive message and then send to this port
            address = packet.getAddress(); //save address
        } catch (IOException e) {
            System.out.println("No IO");
            System.exit(-1);
        }
        return new String(packet.getData(), 0, packet.getLength());
    }

    public void close() {
        socket.close();
    }

    public static void main(String[] args) {
        //--Receiving message example--
        ip = "10.21.12.1"; //my ip to listen on
        UDPClient client = new UDPClient();
        System.out.println("-----------------------Task2-----------------------");
        String input1 = client.receiveMsg().trim();
        System.out.println(input1);
        StringBuilder answer1 = new StringBuilder();
        for (int i = 0; i < 8; i++)
            answer1.append(input1);
        //send answer
        System.out.println("Answer1 is " + answer1);
        client.sendMsg(answer1.toString());
        System.out.println("-----------------------Task3-----------------------");
        ArrayList<Integer> inputs2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String input2 = client.receiveMsg().trim();
            System.out.println(input2);
            inputs2.add(Integer.parseInt(input2));
        }
        BigDecimal answer2 = new BigDecimal(0);
        for (int i = 0; i < 5; i++)
            answer2 = answer2.add(BigDecimal.valueOf(inputs2.get(i)));
        System.out.println(answer2);
        System.out.println("Answer2 is " + answer2);
        client.sendMsg(answer2.toString());
        System.out.println("-----------------------Task4-----------------------");
        String input3 = client.receiveMsg().trim();
        input3 = input3.replace("7", "");
        client.sendMsg(input3);
        System.out.println("Answer3 is " + input3);
        System.out.println("-----------------------Flag-----------------------");
        String flag = client.receiveMsg().trim();
        System.out.println(flag);

        //--Sending message example--
//        ip = "172.23.129.53"; //to listen on
//        port = 7477;
//        UDPClient client = new UDPClient();
//        String message = "12345";
//        client.sendMsg(message);
//        answer = client.receiveMsg();
        client.close();
    }
}
