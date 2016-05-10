import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DReceiver {
	
	JFrame frame = new JFrame("My Window");
	JLabel lblMessage = new JLabel();
	JLabel lblMyPort = new JLabel();
	JLabel lblMyIP = new JLabel();
	Insets lblInsets = new Insets(10, 50, 10, 10);
	
	public DReceiver(){
		frame.setLayout(new GridBagLayout());
		
		addComponent(frame, lblMyIP, 0, 0,
				1, 1, GridBagConstraints.WEST, 
				GridBagConstraints.NONE, lblInsets);
		addComponent(frame, lblMyPort, 0, 1,
				1, 1, GridBagConstraints.WEST, 
				GridBagConstraints.NONE, lblInsets);
		addComponent(frame, lblMessage, 0, 2,
				1, 1, GridBagConstraints.WEST, 
				GridBagConstraints.NONE, lblInsets);
		
		try {
			this.loadHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lblMessage.setText("Incoming Message: ");
		lblMyPort.setText("My Local Port: ");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100,100,500,500); 
		frame.setVisible(true);
	}
	
	// addComponent
		// An easier way of creating GridBagRestraints, and then adding a component to the container
		private static void addComponent(Container container, Component component, int gridx, int gridy,
				int gridwidth, int gridheight, int anchor, int fill, Insets insets) {
			GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
					anchor, fill, insets, 0, 0);
			container.add(component, gbc);
		}
		
		private void loadHostAddress() throws Exception{
	    	InetAddress inet = Inet4Address.getLocalHost();
	    	lblMyIP.setText("My Local IP Address: " + inet.getHostAddress());
	    }
	
	public void listen() throws IOException{
		DatagramSocket ds = new DatagramSocket(3659);
		Integer portNumber = ds.getLocalPort();
		lblMyPort.setText("My Local Port: " + portNumber.toString());
		
		byte[] buf = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf, 1024);
		ds.receive(dp);
		String str = new String(dp.getData(), 0, dp.getLength());
		
		lblMessage.setText("Incoming Message: " + str);
		
		ds.close();
	}
	
	public static void main(String[] args) {
		DReceiver rec = new DReceiver();
		while(true){
			try {
				rec.listen();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
