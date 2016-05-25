package messenger;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

// UDP Server Program

/**
 * The Server class listens for incoming messages and then 
 * displays the message to the UI incoming messages box.
 * 
 * @author Brandon McCoy
 * @since 05/17/2016
 */
public class Server {

	private DatagramSocket socket;

	private String strLocalIP = new String("...");
	private String strLocalPort = new String("");
	private String strSubnetMask = new String("...");

	/**
	 * The constructor is empty to prevent failure caused by Exceptions.
	 * loadServer method must be called to set Server values.
	 */
	public Server() {
		
	}

	/**
	 * Loads all of the important data needed to receive a DatagramPacket.
	 * 
	 * @return false if an Exception was thrown, true otherwise.
	 */
	public boolean loadServer(){
		try {
			socket = new DatagramSocket();
		} catch (SocketException ex) {
			return false;
		}

		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			strLocalIP = new String(inetAddress.getHostAddress());
		} catch (UnknownHostException e) {
			return false;
		}

		strLocalPort = new String(String.valueOf(socket.getLocalPort()));

		try {
			strSubnetMask = new String(getSubnetMaskString());
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Sets the server to a listening state. 
	 * Displays the message if a message is received.
	 * This method will run until application is closed.
	 */
	public void readyToReceivePacket() {
		while (true) {
			try {
				byte buffer[] = new byte[128];
				DatagramPacket packet =
						new DatagramPacket(buffer, buffer.length);
				// Listen for message
				socket.receive(packet);
				// Display message received
				showMsg(new String(packet.getData()));
			} catch (IOException ex) {
				showMsg(ex.getMessage());
			}
		}
	}

	/**
	 * 
	 * @return IP address of this application.
	 */
	public String getLocalIP(){
		return strLocalIP;
	}

	/**
	 * 
	 * @return Port number that this Server is listening on.
	 */
	public String getLocalPort(){
		return strLocalPort;
	}

	/**
	 * 
	 * @return The Subnet Mask associated with this application.
	 */
	public String getSubnetMask(){
		return strSubnetMask;
	}

	/**
	 * 
	 * @return The IPv4 Subnet Mask
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	private String getSubnetMaskString() throws UnknownHostException, SocketException{
		NetworkInterface thisNetworkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
		short subnetMask = thisNetworkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();
		String strSubnetMask = new String("");
		if(subnetMask == 8){
			strSubnetMask = new String(" (255.0.0.0)");
		}else if(subnetMask == 16){
			strSubnetMask = new String(" (255.255.0.0)");
		}else if(subnetMask == 24){
			strSubnetMask = new String(" (255.255.255.0)");
		}
		return "/" + String.valueOf(subnetMask) + strSubnetMask;
	}

	/**
	 * 
	 * @param msg
	 */
	private void showMsg(final String msg) {
		Runnable runnable = new Runnable(){
			public void run(){
				Controller.window.appendIncomingMessage(msg);
			}
		};
		SwingUtilities.invokeLater(runnable);
	}
}
