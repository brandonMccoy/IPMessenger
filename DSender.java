import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * <h1>DSender</h1>
 * The DSender class creates a DatagramPacket containing a String message,
 * that is then sent to an IP address (String representation of the IP address)
 * through a specific port. These three arguments are parameters 
 * of the DSender class constructor.
 * 
 * @author Brandon McCoy
 * @date 05/09/2016
 * 
 */

/* The range of port numbers from 1024 to 49151 are the registered ports. 
 * They are assigned by IANA for specific service upon application by 
 * a requesting entity.
 * 
 * 666  is the official Doom fps socket
 * 2302 is the unofficial Halo: Combat Evolved multiplayer socket
 * 3071 is the unofficial Call of Duty Black Ops socket
 * 3659	is the unofficial Battlefield 4 socket
 * 
 * https://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers
 * */

public class DSender {
	
	/**
	 * <b>DSender constructor</b> 
	 * <p>
	 * <b>Note:</b> DatagramPacket delivery is not guaranteed.
	 * 
	 * @param String mMessage - The message to be sent to the server
	 * @param String mInetAddressName - The name of the host
	 * @param int mPort - The port the DatagramPacket is sent on
	 * @throws Exception - 
	 */
	public DSender(String mMessage, String mInetAddressName, int mPort) 
			throws Exception {
		
		DatagramSocket ds = new DatagramSocket(mPort);
		
		DatagramPacket dp = new DatagramPacket(mMessage.getBytes(), 
				mMessage.length(), 
				InetAddress.getByName(mInetAddressName), 
				mPort);
		
		ds.send(dp);
		
		ds.close();
	}
	
	// TODO main function will be omitted
	public static void main(String[] args) throws Exception {
		InetAddress ip = InetAddress.getLocalHost();
		String strIPName = ip.getHostName();
		//System.out.println(ip.toString());
		new DSender("Whats up?", strIPName, 3659);
	}
}
