package fr.alma.middleware.loki.client.gui;

import fr.alma.middleware.loki.client.ClientTopic;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class TopicWindow extends JFrame implements WindowListener,KeyListener {

	private ClientTopic clientTopic;
	
	private JTextField textMessage;
	private JScrollPane scrollHistory;
	private JTextPane textHistory;
	private JButton buttonSend;
	
	private static final int[] KONAMI_CODE = {
		KeyEvent.VK_UP, KeyEvent.VK_UP,
		KeyEvent.VK_DOWN, KeyEvent.VK_DOWN, 
		KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
		KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
		KeyEvent.VK_B, KeyEvent.VK_A
	};
	
	private int konamiCodeStep = 0;
	private boolean konamiCodeState = false;
	
	public TopicWindow(String title) {
		super(title);
		this.textHistory = new JTextPane();
		this.textHistory.setEditable(false);
		this.scrollHistory = new JScrollPane(
			this.textHistory,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);
		this.textMessage = new JTextField();

		this.buttonSend = new JButton();
		this.buttonSend.setText("Send");

		Box boxLastLine = Box.createHorizontalBox();
		boxLastLine.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		boxLastLine.add(this.textMessage);
		boxLastLine.add(this.buttonSend);

		Container windowContainer = super.getContentPane();
		windowContainer.add(this.scrollHistory, BorderLayout.CENTER);
		windowContainer.add(boxLastLine, BorderLayout.SOUTH);

		super.setSize(400, 600);
		super.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		
		this.textMessage.addKeyListener(this);
		
		super.setVisible(true);
	}
	
	public String getMessage() {
		if(this.konamiCodeState) {
			String msg = this.textMessage.getText();
			return new StringBuffer(msg).reverse().toString();
		} else {
			return this.textMessage.getText();
		}
	}
	
	public void appendToHistory(String text) {
		appendToHistory(text,Color.BLACK);
	}
	
	public void appendToHistory(String text, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		
		this.textHistory.setEditable(true);
		int len = this.textHistory.getDocument().getLength();
		this.textHistory.setCaretPosition(len);
		this.textHistory.setCharacterAttributes(aset, false);
		this.textHistory.replaceSelection(text);
		this.textHistory.setEditable(false);
    }
	
	public void setMessage(String message) {
		this.textMessage.setText(message);
	}
	
	public void listenSendButton(ActionListener listener) {
		this.buttonSend.addActionListener(listener);
	}
	
	/** Sets topic name in window title
	 * @param topicName The new topic name of the window. If {@code null} remove topic name in window title.
	 */
	public void setTopicName(String topicName) {
		String originalTitle = this.getTitle();
		int indexSeperator = originalTitle.lastIndexOf(" -- ");
		if(indexSeperator != -1) {
			originalTitle = originalTitle.substring(0,indexSeperator);
		}
		if(topicName != null) {
			this.setTitle(originalTitle+" -- "+topicName);
		} else {
			this.setTitle(originalTitle);
		}
	}
	
	public void setClientTopic(ClientTopic clientTopic) {
		this.clientTopic = clientTopic;
	}
	
	public void close() {
		setVisible(false);
		dispose();
	}
	
	// WindowListener methods
	
	public void windowActivated(WindowEvent e) {
		// Nothing to do
	}
	
	public void windowClosed(WindowEvent e) {
		// Nothing to do
	}

	public void windowClosing(WindowEvent e) {
		if(this.clientTopic != null) {
			this.clientTopic.close();
		}
	}

	public void windowDeactivated(WindowEvent e) {
		// Nothing to do
	}

	public void windowDeiconified(WindowEvent e) {
		// Nothing to do
	}

	public void windowIconified(WindowEvent e) {
		// Nothing to do
	}

	public void windowOpened(WindowEvent e) {
		// Nothing to do
	}
	
	// KeyListener methods
	
	public void keyPressed(KeyEvent e) {
		// Nothing to do
	}
	
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.clientTopic.actionPerformed(null);
		} else if(e.getKeyCode() == KONAMI_CODE[konamiCodeStep]) {
			konamiCodeStep++;
			if(konamiCodeStep >= KONAMI_CODE.length) {
				konamiCodeState = !konamiCodeState;
				konamiCodeStep = 0;
			}
		} else {
			konamiCodeStep = 0;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		// Nothing to do
	}
}
