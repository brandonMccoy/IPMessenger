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
 * 
 * @author Brandon McCoy
 * @since 05/14/2016
 */
public class Server {

	private DatagramSocket socket;

	private String strLocalIP = new String("...");
	private String strLocalPort = new String("");
	private String strSubnetMask = new String("...");

	/**
	 * 
	 */
	public Server() {
		
	}
	
	/**
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
			strLocalIP = new String(String.valueOf(InetAddress.getLocalHost()));
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
	 * 
	 */
	public void readyToReceivePacket() {
		while (true) {
			try {
				byte buffer[] = new byte[128];
				DatagramPacket packet =
						new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);

				showMsg("\nIncoming Message from " 
					+ "\nIP Address " + packet.getAddress()
					+ "\nthru Port " + packet.getPort() 
					+ "\n-->" + new String(packet.getData()));
				
			} catch (IOException ex) {
				showMsg(ex.getMessage());
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getLocalIP(){
		return strLocalIP;
	}

	/**
	 * 
	 * @return
	 */
	public String getLocalPort(){
		return strLocalPort;
	}

	/**
	 * 
	 * @return
	 */
	public String getSubnetMask(){
		return strSubnetMask;
	}
	
	/**
	 * 
	 * @return
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
				Base.window.appendIncomingMessage(msg);
			}
		};
		SwingUtilities.invokeLater(runnable);
	}
}