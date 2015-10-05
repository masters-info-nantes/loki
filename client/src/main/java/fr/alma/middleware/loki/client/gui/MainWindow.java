package fr.alma.middleware.loki.client.gui;

import fr.alma.middleware.loki.client.Client;
import fr.alma.middleware.loki.common.ITopic;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MainWindow extends JFrame implements WindowListener {
	
	private JLabel labelNickname;

	private JTextField textNickname;
	private JTextField textNewTopicName;

	private JScrollPane scrollTopic;
	private DefaultListModel<String> listTopicModel;
	private JList<String> listTopic;

	private JButton buttonCreate;
	
	private Client client;
	
	public MainWindow(String title) {

		super(title);

		this.labelNickname = new JLabel();
		this.labelNickname.setText("Nickname :");

		this.textNickname = new JTextField();
		this.listTopicModel = new DefaultListModel<String>();
		this.listTopic = new JList(this.listTopicModel);
		this.listTopic.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList)evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					openTopic(index);
				}
			}
		});
		this.scrollTopic = new JScrollPane(
			this.listTopic,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);
		this.textNewTopicName = new JTextField();
	
		this.buttonCreate = new JButton();
		this.buttonCreate.setText("Create Topic");

		Box boxFirstLine = Box.createHorizontalBox();
		boxFirstLine.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		boxFirstLine.add(this.labelNickname);
		boxFirstLine.add(this.textNickname);

		Box boxLastLine = Box.createHorizontalBox();
		boxLastLine.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		boxLastLine.add(this.textNewTopicName);
		boxLastLine.add(this.buttonCreate);

		Container windowContainer = super.getContentPane();
		windowContainer.add(boxFirstLine, BorderLayout.NORTH);
		windowContainer.add(this.scrollTopic, BorderLayout.CENTER);
		windowContainer.add(boxLastLine, BorderLayout.SOUTH);

		super.setSize(300, 400);
		super.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		super.setVisible(true);
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void addTopic(String topicName) {
		this.listTopicModel.addElement(topicName);
		List allTopics = Arrays.asList(this.listTopicModel.toArray());
		this.listTopicModel.removeAllElements();
		Collections.sort(allTopics);
		for(Object topic : allTopics) {
			this.listTopicModel.addElement((String)topic);
		}
	}
	
	public void removeTopic(String topicName) {
		this.listTopicModel.removeElement(topicName);
	}
	
	public String getNickname() {
		return this.textNickname.getText();
	}
	
	public String getNewTopicName() {
		return this.textNewTopicName.getText();
	}
	
	public void setNickname(String nickname) {
		this.textNickname.setText(nickname);
	}
	
	public void setNewTopicName(String topicName) {
		this.textNewTopicName.setText(topicName);
	}
	
	public void listenCreateButton(ActionListener listener) {
		this.buttonCreate.addActionListener(listener);
	}
	
	public void openTopic(int index) {
		String topicName = this.listTopicModel.get(index);
		this.client.openTopic(topicName);
	}
	
	// WindowListener methods
	
	public void windowActivated(WindowEvent e) {
		// Nothing to do
	}
	
	public void windowClosed(WindowEvent e) {
		// Nothing to do
	}

	public void windowClosing(WindowEvent e) {
		if(this.client != null) {
			this.client.unregister();
		}
		setVisible(false);
		dispose();
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
}
