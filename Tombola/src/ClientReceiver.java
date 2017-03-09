import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ClientReceiver extends Thread {
	private Socket s;
	private Client c;
	ArrayList<Integer> numeri = new ArrayList<Integer>();
	ArrayList<Integer> riceve = new ArrayList<Integer>();
	// deve essere inizializzato con il socket e il riferimento della parte grafica 
	public ClientReceiver (Socket s, Client c){
		this.s = s;
		this.c = c;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		// all'infinito resta in ascolto di nuovi messaggi nel socket
		try {
		
			while(true){
				// quando arriva un nuovo messaggio
				
				InputStreamReader isr = new InputStreamReader(s.getInputStream());
				BufferedReader in = new BufferedReader(isr);
				String num = in.readLine();
				c.addNumero(num);
				// legge il messaggio 
				// comunica alla grafica il nuovo messaggio
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
