package messenger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.SwingUtilities;


/**
 * Contains main(string[] args).
 * 
 * Sets up the Server and prepares the window. 
 * Then the ActionListener is set up for the submit button.
 * If the submit button is clicked, a Client is created, 
 * the client datapacket is loaded with user input values 
 * for IP destination, port destination, and String message.
 * Then the datapacket is sent to the IP address. 
 * The Server is set to recieve messages.
 * 
 * @author Brandon McCoy
 * @since 05/17/2016
 */
public class Controller {
	
	static Window window;
	
	/**
	 * Constructor is empty
	 */
	public Controller(){
		
	}
	
	/**
	 * Adds a custom action listener and KeyListener to the submit button.
	 */
	public void addListeners(){
		window.btnSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				SendMessage();
			}
		});

		window.btnSubmit.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					SendMessage();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
	}

	/**
	 * Because the Server, Client, and Window all run on different threads,
	 * it is possible for this application to send a message to itself.
	 * The application will close if any of the information needed to run
	 * this application is not loaded correctly. (Server.loadServer fails)
	 * @param args
	 */
	public static void main(String[] args) {
		Controller controller = new Controller();
		final Server server = new Server();
		if(server.loadServer()){
			window = new Window(
					server.getLocalIP(),
					server.getLocalPort(),
					server.getSubnetMask());

			// Add actionListener and keyListener to the submit button
			controller.addListeners();
			
			// The server is set to receive on its own thread
			// because the receive method of DatagramSocket 
			// interferes/interrupts with the Window user interaction.
			Runnable runServer = new Runnable(){
				public void run() {
					server.readyToReceivePacket();
				}
			};
			runServer.run();
		}else{
			System.exit(1);
		}
	}

	/**
	 * Client will be loaded with 
	 * the users input, and be sent to a Server. 
	 * Prints error to the UI if any part of the Client fails to send.
	 */
	private void SendMessage() {
			Client client = new Client();
			
			if(!client.loadMessage())
				showMsg("Error occurred while loading message.");
			
			if(!client.sendMessage())
				showMsg("Error occurred while sending message.");
			client.close();
	}
		
	/**
	 * Prints message to the UI. Message is appended to keep a history of
	 * all incoming messages.
	 * @param msg Message to append to the text in the Incoming Messages box.
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
