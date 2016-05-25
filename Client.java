package messenger;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

/**
 * This class opens a DatagramSocket, and sends messages 
 * loaded with user input to an IP and port specified by the user.
 * The loadMessage method must be called before sending a message.
 * The close method must also be called to close the DatagramSocket.
 * @author Brandon McCoy
 * @since 05/17/2016
 */
public class Client{

	private DatagramSocket socket;
	private DatagramPacket packetSend;
	private Integer portNumber;
	private String addr;
	private InetAddress serverAddress;
	private String outgoingMessage;
	private String msg;

	/**
	 * The constructor is empty to prevent failure caused by Exceptions.
	 * loadMessage method must be called to set Client values.
	 */
	public Client(){
		
	}
	
	/**
	 * Loads all of the important data from the open window (JFrame) 
	 * needed to send a message.
	 * 
	 * @return false if an exception occurs, true if successful.
	 */
	public boolean loadMessage(){
		try{
			socket = new DatagramSocket();
		}catch(SocketException e){
			return false;
		}
		
		// Set Message
		outgoingMessage = new String(Controller.window.getMsgOut());
		
		// Set socket
		portNumber = Controller.window.getDestinationPortInt();
		
		// Set InetAddress of server
		addr = new String(Controller.window.getDestinationIP());
		
		try {
			serverAddress = InetAddress.getByName(addr);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Closes this DatagramSocket.
	 */
	public void close(){
		socket.close();
	}

	/**
	 * Prepares the DatagramPacket to be sent, with the users UI input.
	 * Must be called before the DatagramPacket is sent.
	 */
	private void loadPacket(){
		byte buff[] = msg.getBytes();
		packetSend = new DatagramPacket(
					buff, buff.length, serverAddress, portNumber);
	}

	/**
	 * 
	 * @return false if an exception occurs, true if successful.
	 */
	public boolean sendMessage(){

		msg = new String("From IP Address " + Controller.window.getMyIP()
			+ "\nServer is listening on port " + Controller.window.getMyPort()
			+ "\n--> " + outgoingMessage);

		// Load DatagramPacket
		loadPacket();

		// Send Message
		if(!send(packetSend)){
			showMsg("Failed to send message.");
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param dp A preloaded DatagramPacket to be sent to the assigned host.
	 * @return false if an exception occurs, true if successful.
	 */
	private boolean send(DatagramPacket dp){
		try {
			socket.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param msg message to display in the Incoming Messages box.
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
