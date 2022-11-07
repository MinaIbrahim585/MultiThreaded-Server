import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Random;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.RecursiveAction;

// Server class
class Server {
	public static void main(String[] args)
	{
		ServerSocket server = null;

		try {

			// server is listening on port 1234
			server = new ServerSocket(6000);
			server.setReuseAddress(true);
System.out.println("Waiting on incoming connections");
			// running infinite loop for getting
			// client request
			while (true) {

				// socket object to receive incoming client
				// requests
				Socket client = server.accept();

				// Displaying that new client is connected
				// to server
				System.out.println("New client connected"
								+ client.getInetAddress()
										.getHostAddress());

				// create a new thread object
				ClientHandler clientSock
					= new ClientHandler(client);

				// This thread will handle the client
				// separately
				new Thread(clientSock).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (server != null) {
				try {
					server.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	// ClientHandler class
	private static class ClientHandler implements Runnable {
		
        private final Socket clientSocket;
    static class FindSum extends RecursiveTask<Integer> {
        int[] array;
        int low;
        int high;
        FindSum(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }
        protected Integer compute() {
            if ((high - low) < array.length/2)  {
                int number = 0;
	     for(int i=0;i < array.length;i++){
            
		number = number + array[i];
         }
		//String res=Integer.toString(fact);
                return number;
            }
            else {
                int mid = low + (high - low) / 2;
                FindSum left = new FindSum(array, low, mid);
                FindSum right = new FindSum(array, mid, high);
                left.fork();
                int rightResult = right.compute();
                int leftResult = left.join();
                return leftResult + rightResult;
            }
        }
    }

		// Constructor
		public ClientHandler(Socket socket)
		{
			this.clientSocket = socket;
		}

		public void run()
		{
			PrintWriter out = null;
			BufferedReader in = null;
			try {
	
   
		BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        String line = reader.readLine();

        
        
        System.out.println("Recv: number: " + line);
       
        int n1 = Integer.parseInt(line);
         int Data[]= new int[n1];

        for(int i=0;i<Data.length;i++){
    Data[i]= new Random().nextInt(1000);
}

	    // int fact=0;	
	  //   for(int i=0;i < Data.length;i++){
            
		//	 fact = fact + Data[i];
        // }
		//String res=Integer.toString(fact);
            int nThreads = Runtime.getRuntime().availableProcessors();
            System.out.println("Number of threads available for concurrency: " + nThreads);
            ForkJoinPool forkJoinPool = new ForkJoinPool(nThreads);

         //   FindSum findSum = new FindSum(Data);    

int sum = forkJoinPool.invoke(new FindSum(Data, 0, Data.length));
            System.out.println("Sum of Array: " + sum);
       		writer.println(sum);

            // Testing against no parallelism
            
forkJoinPool.close();


			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
						clientSocket.close();
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
