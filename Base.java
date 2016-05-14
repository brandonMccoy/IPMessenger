import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

/**
 * 
 * @author Brandon McCoy
 * @since 05/14/2016
 */
public class Base {
	
	static Window window;
	
	/**
	 * 
	 */
	public Base(){
		window.btnSubmit.addActionListener(
				new CustomActionListener());
	}

	/**
	 * can send a message to itself
	 * @param args
	 */
	public static void main(String[] args) {
		
		Server server = new Server();
		if(server.loadServer()){
			window = new Window(
				server.getLocalIP(),
				server.getLocalPort(),
				server.getSubnetMask());

			new Base();
			server.readyToReceivePacket();
		}else{
			System.exit(1);
		}
	}

	/**
	 * If the Submit button is clicked, a Client will be loaded with 
	 * the users input, and be sent to a Server.
	 * 
	 * @author Brandon McCoy
	 *
	 */
	private class CustomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source == Base.window.btnSubmit){// Send a message
				Client client = new Client();
				if(!client.sendMessage()){
					showMsg("Error occurred while sending message.");
				}
			}
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
}