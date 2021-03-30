package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{

    private Socket socket;
    public static String serverText;
    public Server(Socket socket){
       this.socket = socket;

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
            while(true){
                output.println(input.readLine());
                serverText = input.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        }

}
