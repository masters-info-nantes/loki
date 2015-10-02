package fr.alma.middleware.loki.client.gui;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
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


public class MainWindow extends JFrame {
	
	private JLabel labelNickname;

	private JTextField textNickname;
	private JTextField textMessage;

	private JTextPane textHistory;

	private JButton buttonSend;
	
	public MainWindow(String title) {

		super(title);

		this.labelNickname = new JLabel();
		this.labelNickname.setText("Nickname :");

		this.textNickname = new JTextField();
		this.textHistory = new JTextPane();
		this.textMessage = new JTextField();

		this.buttonSend = new JButton();
		this.buttonSend.setText("Send");

		Box boxFirstLine = Box.createHorizontalBox();
		boxFirstLine.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		boxFirstLine.add(this.labelNickname);
		boxFirstLine.add(this.textNickname);

		Box boxLastLine = Box.createHorizontalBox();
		boxLastLine.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		boxLastLine.add(this.textMessage);
		boxLastLine.add(this.buttonSend);

		Container windowContainer = super.getContentPane();
		windowContainer.add(boxFirstLine, BorderLayout.NORTH);
		windowContainer.add(this.textHistory, BorderLayout.CENTER);
		windowContainer.add(boxLastLine, BorderLayout.SOUTH);

		super.setSize(400, 600);
		super.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		super.setVisible(true);
	}
	
	public String getNickname() {
		return this.textNickname.getText();
	}
	
	public String getMessage() {
		return this.textMessage.getText();
	}
	
	public void appendToHistory(String text) {
		appendToHistory(text,Color.BLACK);
	}
	
	public void appendToHistory(String text, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		
		int len = this.textHistory.getDocument().getLength();
		this.textHistory.setCaretPosition(len);
		this.textHistory.setCharacterAttributes(aset, false);
		this.textHistory.replaceSelection(text);
    }
	
	public void setNickname(String nickname) {
		this.textNickname.setText(nickname);
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
	public void setCurrentTopicName(String topicName) {
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
}
