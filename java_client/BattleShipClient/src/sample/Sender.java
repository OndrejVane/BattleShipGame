package sample;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;


public class Sender {

    private Socket socket;

    private OutputStreamWriter wr;

    public Sender(Socket socket){
        this.socket = socket;
    }

    public void sendMessage(String message){
        try {
            wr = new OutputStreamWriter(socket.getOutputStream());
            wr.write(message);
            System.out.println("Sent: " + message);
            wr.flush();
        } catch (SocketException f){
            System.out.println("ERROR: Socket is closed");
        } catch (IOException e) {
            System.out.println("ERROR: Sender");
        }

    }


    public Socket getSocket(){
        return this.socket;
    }

}