import java.net.*;
import java.io.*;
import java.util.*;


class Client {
  public static final int SERVER_PORT = 6000;

  public static void main(String... args) {
    System.out.println("Client On");
    try {
      InetAddress local = InetAddress.getLocalHost();  // Can use InetAddress.getByName().
      System.out.println("LocalHost: " + local);
      Socket clientSocket = new Socket(local, SERVER_PORT);
      PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      System.out.println("Connected to server " + clientSocket.getInetAddress()
          + " port " + clientSocket.getPort());
      Scanner scanner = new Scanner(System.in);
     
        System.out.print("Enter a how many elements in Array: ");
        String line = scanner.nextLine();
     
        writer.println(line);
		

		String response1 = reader.readLine();
        System.out.println("Sum of Array is:- " + response1);
      
      if(line == "0"){
      clientSocket.close();
      }
    } catch (IOException e) {
      
    }
  }
}