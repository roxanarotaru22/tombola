import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;

public class Client {

	protected Shell shell;
	private Socket s;
	private PrintWriter out;
	private BufferedReader in;
	private Table table;
	ArrayList<Integer> numeri = new ArrayList<Integer>();
	ArrayList<TableItem> celle = new ArrayList<TableItem>();
	ArrayList<TableColumn> colonne = new ArrayList<TableColumn>();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Client window = new Client();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");

		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 56, 366, 83);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		for (int i = 0; i < 10; i++) {
			colonne.add(new TableColumn(table, SWT.NONE));
			colonne.get(i).setWidth(30);
		}

		/*
		 * for(int i=0; i<3; i++){ celle.add(new TableItem(table, SWT.NULL)); }
		 */

		// celle.get(5).setText(5,"1");

		Button btnConnessione = new Button(shell, SWT.NONE);
		btnConnessione.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					s = new Socket("172.16.6.1", 9999);

					 ClientReceiver cr = new ClientReceiver(s, Client.this);
					// -il socket
					// out = new PrintWriter(s.getOutputStream(), true);
					// -la classe grafica
					 cr.start();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnConnessione.setBounds(10, 10, 75, 25);
		btnConnessione.setText("INIZIA");

		Button btnCarta = new Button(shell, SWT.NONE);
		btnCarta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					PrintWriter out = new PrintWriter(s.getOutputStream(), true);
					out.println("CARTA");
					for (int j = 0; j < 3; j++) {
						TableItem item = new TableItem(table, SWT.NULL);
						for (int i = 0; i < 5; i++) {
							int ris = s.getInputStream().read();
							System.out.println("Ricevo: " + ris);
							numeri.add(ris);

							if (ris < 10) {
								/*
								 * while(true){ if(celle.get(0).getText()==""){
								 * item.setText(0, "" + ris); } }
								 */
								item.setText(0, "" + ris);
							}
							if (ris < 20 && ris >= 10) {
								item.setText(1, "" + ris);
							}
							if (ris < 30 && ris >= 20) {
								item.setText(2, "" + ris);
							}
							if (ris < 40 && ris >= 30) {
								item.setText(3, "" + ris);
							}
							if (ris < 50 && ris >= 40) {

								item.setText(4, "" + ris);
							}
							if (ris < 60 && ris >= 50) {
								item.setText(5, "" + ris);
							}
							if (ris < 70 && ris >= 60) {
								item.setText(6, "" + ris);
							}
							if (ris < 80 && ris >= 70) {
								item.setText(7, "" + ris);
							}
							if (ris < 90 && ris >= 80) {
								item.setText(8, "" + ris);
							}
						}
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnCarta.setBounds(105, 10, 85, 25);
		btnCarta.setText("Genera Scheda");

		Label lblSchedaGioco = new Label(shell, SWT.NONE);
		lblSchedaGioco.setBounds(10, 35, 85, 15);
		lblSchedaGioco.setText("Scheda gioco");

		
	}
}
