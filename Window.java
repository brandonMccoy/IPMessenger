package messenger;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


/**
 * 
 * @author Brandon McCoy
 * @since 05/17/2016
 */
public class Window {

	// The window container
	JFrame frame = new JFrame("IP Messenger");
	
	// Fixed text labels
	JLabel lblHeader = new JLabel("User Datagram Protocol (UDP) to send messages");
	JLabel lblMyIPTitle = new JLabel("My IPv4 Address:");
	JLabel lblMyPortTitle = new JLabel("My Port:");
	JLabel lblMySNTitle = new JLabel("My Subnet Mask:");
	JLabel lblDestIPTitle = new JLabel("Destination IP Address:");
	JLabel lblDestPortTitle = new JLabel("Destination Port:");
	JLabel lblMsgOutTitle = new JLabel("Outgoing Message:");
	JLabel lblMsgInTitle = new JLabel("Incoming Messages:");
	
	// Text labels that will be set by the application
	JLabel lblMyIP = new JLabel("...");
	JLabel lblMyPort = new JLabel("");
	JLabel lblMySN = new JLabel("...");
	
	// Text boxes that are editable by the user
	JTextField txtDestIP = new JTextField(16);
	JTextField txtDestPort = new JTextField(16);
	JTextField txtMsgOut = new JTextField(38);
	
	// Incoming Messages box
	JTextArea txtMsgIn = new JTextArea("",8, 38);
	JScrollPane scrMsgInPane = new JScrollPane(txtMsgIn,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	// MyIP box
	JTextArea txtMyIP = new JTextArea("",8,38);
	JScrollPane scrMyIP = new JScrollPane(txtMyIP,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	// Buttons
	JButton btnSubmit = new JButton("Send Message");
	
	// INSETS
	Insets evenInsets = new Insets(10, 10, 10, 10);
	Insets leftInsets = new Insets(10, 50, 10, 10);
	Insets rightInsets = new Insets(10, 10, 10, 50);
	Insets centerInsets = new Insets(10, 0, 10, 0);
	
	/**
	 * The JFrame window is set up with a GridBagLayout. 
	 * All components are added to the container, and given a 
	 * location in the layout. IP Address, Port number, and Subnet Mask
	 * are displayed in the UI.
	 * 
	 * @param mIP the IP Address to display to the UI
	 * @param mPort the Port to display to the UI
	 * @param mSN the Subnet Mask to display to the UI
	 */
	public Window(String mIP, String mPort, String mSN)
	{
		// Set label text to Window arguments
		lblMyIP.setText(mIP);
		lblMyPort.setText(mPort);
		lblMySN.setText(mSN);
		
		// Set up container for contents
		frame.setLayout(new GridBagLayout());
		
		// Header
		addComponent(frame, lblHeader,
				0, 0, 4, 1,
				GridBagConstraints.CENTER,
				evenInsets);
		
		// My IP
		addComponent(frame, lblMyIPTitle,
				0, 1, 4, 1,
				GridBagConstraints.WEST,
				leftInsets);
		/*		
		addComponent(frame, lblMyIP,
				1, 1, 1, 1,
				GridBagConstraints.EAST,
				rightInsets);
		*/
		
		///////////////////////////////////////////////////////////////////////
		addComponent(frame, scrMyIP,
				0, 2, 4, 1,
				GridBagConstraints.CENTER,
				centerInsets);
		txtMyIP.setWrapStyleWord(true);
		txtMyIP.setLineWrap(true);
		txtMyIP.setEditable(false);
		txtMyIP.setFocusable(false);
		txtMyIP.setOpaque(false);
		///////////////////////////////////////////////////////////////////////
		
		// My Port
		addComponent(frame, lblMyPortTitle,
				0, 3, 1, 1,
				GridBagConstraints.WEST,
				leftInsets);
		addComponent(frame, lblMyPort,
				1, 3, 1, 1,
				GridBagConstraints.EAST,
				rightInsets);
		
		// My Subnet Mask
		addComponent(frame, lblMySNTitle,
				2, 3, 1, 1,
				GridBagConstraints.WEST,
				leftInsets);
		addComponent(frame, lblMySN,
				3, 3, 1, 1,
				GridBagConstraints.EAST,
				rightInsets);
		
		// Destination IP
		addComponent(frame, lblDestIPTitle,
				0, 4, 2, 1,
				GridBagConstraints.WEST,
				leftInsets);
		addComponent(frame, txtDestIP,
				2, 4, 2, 1,
				GridBagConstraints.EAST,
				rightInsets);
		txtDestIP.grabFocus();
		
		// Destination Port
		addComponent(frame, lblDestPortTitle,
				0, 5, 2, 1,
				GridBagConstraints.WEST,
				leftInsets);
		addComponent(frame, txtDestPort,
				2, 5, 2, 1,
				GridBagConstraints.EAST,
				rightInsets);
		
		// Outgoing Message
		addComponent(frame, lblMsgOutTitle,
				0, 6, 4, 1,
				GridBagConstraints.WEST,
				leftInsets);
		addComponent(frame, txtMsgOut,
				0, 7, 4, 1,
				GridBagConstraints.CENTER,
				centerInsets);
		
		// Send Button
		addComponent(frame, btnSubmit,
				0, 8, 4, 1,
				GridBagConstraints.CENTER,
				evenInsets);
		
		// Incoming Message
		addComponent(frame, lblMsgInTitle,
				0, 9, 4, 1,
				GridBagConstraints.WEST,
				leftInsets);
		addComponent(frame, scrMsgInPane,
				0, 10, 4, 1,
				GridBagConstraints.CENTER,
				centerInsets);
		txtMsgIn.setWrapStyleWord(true);
		txtMsgIn.setLineWrap(true);
		txtMsgIn.setEditable(false);
		txtMsgIn.setFocusable(false);
		txtMsgIn.setOpaque(false);

		// Frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 550, 700);
		frame.setMinimumSize(new Dimension(550,700));
		frame.setResizable(false);
		frame.setVisible(true);
		
		// Load this machines IPv4 Addresses
		displayIPAddresses();
	}
	
	/**
	 * 
	 * @return String message written by the user, 
	 * meant to be sent to the host.
	 */
	public String getMsgOut(){
		return txtMsgOut.getText();
	}
	
	/**
	 * 
	 * @return IP address entered by the user.
	 */
	public String getDestinationIP(){
		return txtDestIP.getText();
	}
	
	/**
	 * 
	 * @return Integer Port number entered by the user.
	 */
	public Integer getDestinationPortInt(){
		return Integer.parseInt(txtDestPort.getText());
	}
	
	/**
	 * 
	 * @return String Port number entered by the user.
	 */
	public String getDestinationPort(){
		return txtDestPort.getText();
	}
	
	/**
	 * 
	 * @return the port used for this application.
	 */
	public String getMyPort(){
		return lblMyPort.getText();
	}

	/**
	 * 
	 * @return the IP address of this pc.
	 */
	public String getMyIP(){
		return lblMyIP.getText();
	}
	
	/**
	 * 
	 * @param msg 
	 */
	public void appendIncomingMessage(String msg){
		txtMsgIn.append(msg + "\n");
	}
	
	/**
	 * Displays available IPv4 addresses of this machine
	 */
	private void displayIPAddresses(){
		IPv4Addresses ipv4addr = new IPv4Addresses();
		ipv4addr.loadIPAddresses();
		List<String> addresses = ipv4addr.getIPv4Addresses();
		// Add each address to window incoming message box
		String addrs = new String();
		for(int i = 0; i < addresses.size(); i++){
			addrs = new String(addrs + addresses.get(i)+ "\n\n");
		}
		txtMyIP.setText(addrs);
		// appendIncomingMessage(addrs);
	}

	/**
	 * An easier way of creating GridBagRestraints, 
	 * and then adding a component to the container.
	 * 
	 * @param container The JFrame container
	 * @param component Labels, Textboxes, and submit buttons to add to the JFrame
	 * @param gridX The Components column position in the grid bag
	 * @param gridY The components row position in the grid bag
	 * @param gridWidth The number of columns the component will occupy
	 * @param gridHeight The number of rows the component will occupy
	 * @param anchor The positioning of the component within its cell
	 * @param insets The padding inside the cell
	 * @return void
	 */
	private static void addComponent(Container container, Component component, int gridX, int gridY,
			int gridWidth, int gridHeight, int anchor, Insets insets) {
		GridBagConstraints gbc = new GridBagConstraints(gridX, gridY, gridWidth, gridHeight, 1.0, 1.0,
				anchor, GridBagConstraints.NONE, insets, 0, 0);
		container.add(component, gbc);
	}
}
