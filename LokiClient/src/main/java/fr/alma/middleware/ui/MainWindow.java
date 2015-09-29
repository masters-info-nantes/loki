package fr.alma.middleware.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;


public class MainWindow extends JFrame {
	
	private String title;
	
	private Box boxFirstLine;
	private Box boxLastLine;
	
	private JLabel nicknameLabel;
	private JTextField nickname;
	//~ private JTextArea history;
	private JTextPane history;
	private JTextField message;
	private JButton send;
	
	public MainWindow(String title) {
		this.title = title;
		setTitle(this.title);
		setSize(400, 600);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		nicknameLabel = new JLabel();
		nicknameLabel.setText("Nickname :");
		nickname = new JTextField();
		//~ history = new JTextArea();
		history = new JTextPane();
		message = new JTextField();
		send = new JButton();
		send.setText("Send");

		boxFirstLine = Box.createHorizontalBox();
		boxFirstLine.add(nicknameLabel);
		boxFirstLine.add(nickname);
		boxLastLine = Box.createHorizontalBox();
		boxLastLine.add(message);
		boxLastLine.add(send);
		getContentPane().add(boxFirstLine,BorderLayout.NORTH);
		getContentPane().add(history,BorderLayout.CENTER);
		getContentPane().add(boxLastLine,BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	public String getNickname() {
		return this.nickname.getText();
	}
	
	public String getMessage() {
		return this.message.getText();
	}
	
	public void appendToHistory(String text) {
		//~ this.history.append(text);
		appendToHistory(text,Color.BLACK);
	}
	
	public void appendToHistory(String text, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		
		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		
		int len = this.history.getDocument().getLength();
		this.history.setCaretPosition(len);
		this.history.setCharacterAttributes(aset, false);
		this.history.replaceSelection(text);
    }
	
	public void setNickname(String nickname) {
		this.nickname.setText(nickname);
	}
	
	public void setMessage(String message) {
		this.message.setText(message);
	}
	
}
