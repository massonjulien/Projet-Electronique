package projetElec;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import gnu.io.*;

@SuppressWarnings("serial")
public class JavaApplication extends JFrame implements ActionListener, SerialPortEventListener{
	
	private SerialPort serialPort;
	OutputStream outputstream;
	InputStream inputstream;
	CommPortIdentifier portId;
	private JButton button;
	private JTextField field1;
	private JTextField field2;
	int k;
	boolean virgule = false;
	ArrayList<Integer> list = new ArrayList<Integer>();
	ArrayList<Integer> decimale = new ArrayList<Integer>();
	
	public JavaApplication() {
		
		field1 = new JTextField();
		button = new JButton("envoyer");
		field2 = new JTextField();
		this.setLayout(new GridLayout(2, 2));
		this.getContentPane().add(field1);
		this.getContentPane().add(button);
		this.getContentPane().add(field2);
		
		field1.addActionListener(this);
		//button.addActionListener(this);
		this.setTitle("Ma première fenêtre");
		this.setSize(300, 200);
		this.setResizable(false);
		//this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		
		try {
			portId=CommPortIdentifier.getPortIdentifier("COM2");
			serialPort=(SerialPort)portId.open("",100);
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			
			
		}catch(Exception e) {
			System.out.println("ça ne marche pas");
			JOptionPane.showMessageDialog(null, e.toString());}
		
	}

	@Override
	public void actionPerformed(ActionEvent m) {
		if(m.getSource() == field1) {
			String x = field1.getText();
			int y = Integer.parseInt(x);
			int z = y+48;
			char c = (char)z;
			try {
				outputstream=serialPort.getOutputStream();
				outputstream.write(c);
				outputstream.close();
				field1.setText("");
			}catch(Exception e) {JOptionPane.showMessageDialog(null, e.toString());}
		}
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// TODO Auto-generated method stub
		byte[] readbuffer=new byte[10];
		float somme = 0;
		try {
			inputstream=serialPort.getInputStream();
			//BufferedReader test = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			
			inputstream.read(readbuffer);
			String y = new String(readbuffer);
			//System.out.println(y);
			char d = y.charAt(0);
			int j = (int)d;
			k = j-48;
			
			if(k == -2) {
				virgule = true;
			}else {
			
			if(k != 10) {
				if(virgule == false) {
					list.add(k);
				}else {
					decimale.add(k);
				}
			}else {
				for(int i = 0; i < list.size(); i++)
			    {
					somme += list.get(i)*Math.pow(10, list.size() - i-1);
					//System.out.println("liste : " + list.get(i));
			    }
				for(int i = 0; i < decimale.size(); i++)
			    {
					somme += decimale.get(i)*Math.pow(10, -i-1);
					//System.out.println("texte : " + decimale.get(i));
			    }
				field2.setText(somme+ "");
				virgule = false;
				list.clear();
				decimale.clear();
				inputstream.close();
			}
			}
			
			/*while(inputstream.available()>0) 
				{
					inputstream.read(readbuffer);
				}	
			String y = new String(readbuffer);
			System.out.println(y);
			char d = y.charAt(0);
			int j = (int)d;
			k = j-48;
			field2.setText(somme+ "");
			inputstream.close();*/
			
		}catch(Exception e) {JOptionPane.showMessageDialog(null, e.toString());}
	}
}


