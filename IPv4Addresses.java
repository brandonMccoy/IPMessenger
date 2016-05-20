package messenger;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IPv4Addresses {
	
	private List<String> ipAddresses;
	
	public IPv4Addresses(){
		ipAddresses = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @return This machines active IPv4 addresses
	 */
	public List<String> getIPv4Addresses(){
		return ipAddresses;
	}
	
	/**
	 * Prints valid IPv4 addresses of this machine
	 */
	public void loadIPAddresses(){
		
		try {
			Enumeration<NetworkInterface> interfaces = 
					NetworkInterface.getNetworkInterfaces();
			loadPartTwo(interfaces);
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param networkInterfaces
	 * @throws SocketException
	 */
	private void loadPartTwo(Enumeration<NetworkInterface> networkInterfaces)
			throws SocketException{
		
		while (networkInterfaces.hasMoreElements()) {
			NetworkInterface iface = networkInterfaces.nextElement();
			// filters out 127.0.0.1 and inactive interfaces
			if (iface.isLoopback() || !iface.isUp())
				continue;

			loadPartThree(iface);
		}
	}
	
	/**
	 * 
	 * @param networkInterface
	 */
	private void loadPartThree(NetworkInterface networkInterface){
		
		String ip;
		Enumeration<InetAddress> addresses = 
				networkInterface.getInetAddresses();
		
		while(addresses.hasMoreElements()) {
			InetAddress addr = addresses.nextElement();
			// TODO more research into why this is returning only IPv4 addresses
			if(!addr.isLinkLocalAddress()){
				ip = new String(addr.getHostAddress());
				/* [Output Example]
				 * Display Name: VMWare Virtual Ethernet Adapter for VMnet1
				 * Address:    198.198.198.198
				 * */
				try{
					ipAddresses.add("Display Name: "
							+ networkInterface.getDisplayName() + "\n"
							+ "Address:\t" + ip);
				}catch(NullPointerException e){
					e.printStackTrace();
				}
			}
		}
	}
}
