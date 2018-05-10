import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public PrintWriter out;
    public BufferedReader in;

    public Server(){
        try{
            ServerSocket serverSocket = new ServerSocket(2222);
            Socket client = serverSocket.accept();

            out = new PrintWriter(client.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        }catch(IOException e){
            System.out.println("Unable to create socket");
        }


    }
}
