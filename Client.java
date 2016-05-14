import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

/**
 * 
 * @author Brandon McCoy
 * @since 05/14/2016
 */
public class Client{

	private DatagramSocket socket;

	/**
	 * 
	 */
	public Client(){
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean sendMessage(){
		try{
			socket = new DatagramSocket();
		}catch(SocketException e){
			return false;
		}
		
		// Print to the clients output, where and what 
		// the user is sending
		String msg = new String("\nDestination IP Address: /"
				+ Base.window.getDestinationIP()
				+ "\nDestination Port: "
				+ Base.window.getDestinationPort()
				+ "\nOutgoing Message: "
				+ Base.window.getMsgOut());
		showMsg("\nSendng message packet: " + msg);

		msg = new String(Base.window.getMsgOut());
		byte buff[] = msg.getBytes();

		// Set socket
		Integer portNumber = Base.window.getDestinationPortInt();

		// Set InetAddress of server
		String addr = new String(Base.window.getDestinationIP());
		InetAddress serverAddress;
		try {
			serverAddress = InetAddress.getByName(addr);
			// Load DatagramPacket
			DatagramPacket packetSend = new DatagramPacket(
					buff, buff.length, serverAddress, portNumber);
			socket.send(packetSend);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		socket.close();
		showMsg("\nPacket sent");
		return true;
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