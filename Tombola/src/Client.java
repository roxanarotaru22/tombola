import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class Client {

	protected Shell shlTombola;
	private Socket s;
	private PrintWriter out;
	private BufferedReader in;
	private Table table;
	private Text numero1;
	ArrayList<Integer> numeri = new ArrayList<Integer>();
	ArrayList<TableItem> celle = new ArrayList<TableItem>();
	ArrayList<TableColumn> colonne = new ArrayList<TableColumn>();
	private Text esiste;

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
		shlTombola.open();
		shlTombola.layout();
		while (!shlTombola.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlTombola = new Shell();
		shlTombola.setBackground(SWTResourceManager.getColor(169, 169, 169));
		shlTombola.setSize(533, 361);
		shlTombola.setText("Tombola");

		table = new Table(shlTombola, SWT.BORDER | SWT.FULL_SELECTION);
		table.setForeground(SWTResourceManager.getColor(255, 0, 0));
		table.setBackground(SWTResourceManager.getColor(222, 184, 135));
		table.setBounds(81, 92, 366, 83);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		numero1 = new Text(shlTombola, SWT.BORDER);
		numero1.setFont(SWTResourceManager.getFont("Tekton Pro Ext", 11, SWT.BOLD));
		numero1.setForeground(SWTResourceManager.getColor(0, 0, 0));
		numero1.setBackground(SWTResourceManager.getColor(169, 169, 169));
		numero1.setBounds(9, 227, 270, 21);

		for (int i = 0; i < 10; i++) {
			colonne.add(new TableColumn(table, SWT.NONE));
			colonne.get(i).setWidth(30);
		}

		/*
		 * for(int i=0; i<3; i++){ celle.add(new TableItem(table, SWT.NULL)); }
		 */

		// celle.get(5).setText(5,"1");

		Button btnConnessione = new Button(shlTombola, SWT.NONE);
		btnConnessione.setForeground(SWTResourceManager.getColor(255, 0, 0));
		btnConnessione.setFont(SWTResourceManager.getFont("Tekton Pro Ext", 11, SWT.BOLD));
		btnConnessione.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					s = new Socket("172.16.6.1", 9999);

				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnConnessione.setBounds(9, 10, 68, 60);

		Button btnCarta = new Button(shlTombola, SWT.NONE);
		btnCarta.setFont(SWTResourceManager.getFont("Tekton Pro Ext", 11, SWT.BOLD));
		btnCarta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					PrintWriter out = new PrintWriter(s.getOutputStream(), true);
					out.println("CARTA");
					for (int j = 0; j < 3; j++) {
						TableItem item = new TableItem(table, SWT.NULL);
						celle.add(item);
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
					ClientReceiver cr = new ClientReceiver(s, Client.this);
					// -il socket
					// out = new PrintWriter(s.getOutputStream(), true);
					// -la classe grafica
					cr.start();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnCarta.setBounds(192, 181, 143, 25);
		btnCarta.setText("Genera Scheda");

		Label lblSchedaGioco = new Label(shlTombola, SWT.NONE);
		lblSchedaGioco.setFont(SWTResourceManager.getFont("Tekton Pro Ext", 15, SWT.BOLD));
		lblSchedaGioco.setForeground(SWTResourceManager.getColor(255, 0, 255));
		lblSchedaGioco.setBackground(SWTResourceManager.getColor(169, 169, 169));
		lblSchedaGioco.setBounds(160, 61, 175, 25);
		lblSchedaGioco.setText("Scheda di gioco:");

		Button btnNumero = new Button(shlTombola, SWT.NONE);
		btnNumero.setFont(SWTResourceManager.getFont("Tekton Pro Ext", 11, SWT.NORMAL));
		btnNumero.setText("NUMERO");
		btnNumero.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					PrintWriter out = new PrintWriter(s.getOutputStream(), true);
					out.println("NUMERO");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNumero.setBounds(285, 227, 106, 25);

		esiste = new Text(shlTombola, SWT.BORDER);
		esiste.setFont(SWTResourceManager.getFont("Tekton Pro Ext", 15, SWT.BOLD));
		esiste.setForeground(SWTResourceManager.getColor(255, 0, 0));
		esiste.setBackground(SWTResourceManager.getColor(169, 169, 169));
		esiste.setBounds(70, 270, 377, 43);
		
		Label label = new Label(shlTombola, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setForeground(SWTResourceManager.getColor(255, 0, 0));
		label.setBackground(SWTResourceManager.getColor(169, 169, 169));
		label.setBounds(0, 254, 517, 21);

	}

	public void addNumero(String numero) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				int n1 = 0;
				System.out.println("Ricevo: " + numero);
				numero1.setText("Il numero e': " + numero);
				for (int i = 0; i < celle.size(); i++) {
					for (int j = 0; j < 10; j++) {

						if (numero.equals(celle.get(i).getText(j))) {
							n1 = 1;
							
						}
					}
				}
				if (n1 == 1) {
					System.out.println("NUMERO ESISTENTE");
					esiste.setText("NUMERO ESISTENTE");
				} else {
					System.out.println("NUMERO NON ESISTENTE");
					esiste.setText("NUMERO NON ESISTENTE");
				}
			}
		});

	}
}
