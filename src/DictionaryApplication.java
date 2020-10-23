import java.awt.BorderLayout;
import com.darkprograms.speech.translator.*;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.parser.ParseException;
import java.awt.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.ScrollPane;

import javax.security.sasl.AuthorizeCallback;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.event.ListSelectionListener;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyAdapter;
import java.util.LinkedList;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

/*Dictionary App
 * Author : Xuân Linh && Nguyễn Ngọc Hoa
 */
public class DictionaryApplication extends JFrame {

	private JPanel contentPane;
	private JTextField jText;
	DictionaryManagement dicM = new DictionaryManagement();
	DictionaryCommandLine dicManL = new DictionaryCommandLine();
	private JTextField addTargetTextField;
	private JTextField addExplainTextField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DictionaryApplication frame = new DictionaryApplication();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Create the frame.
	 */
	public DictionaryApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 791, 592);
		/* Content Panel */
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JList listWord = new JList();
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		
		/* API */
		JCheckBox apiCheck = new JCheckBox("Using API");
		
		apiCheck.setBounds(620, 75, 97, 23);
		contentPane.add(apiCheck);
		
		
		// Title
		JLabel lblNewLabel = new JLabel("ICTIONARY");
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 99, 71));
		lblNewLabel.setFont(new Font("Segoe UI Historic", Font.PLAIN, 51));
		lblNewLabel.setBounds(68, 0, 678, 68);
		contentPane.add(lblNewLabel);

		// Create Explain Area
		JTextArea explainArea = new JTextArea();
		explainArea.setFont(new Font("Arial", Font.PLAIN, 20));
		explainArea.setBackground(Color.WHITE);
		explainArea.setBounds(309, 134, 437, 383);
		contentPane.add(explainArea);
		
		// Set scroll
//		for (Word w : dicM.dictionaries.words) {
//			model.addElement(w.word_target);
//		}
//		listWord.setModel(model);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(127, 134, 165, 383);
		contentPane.add(scrollPane);
		scrollPane.setVisible(true);
		scrollPane.add(listWord);

		/* Create Jtext Filed */
		jText = new JTextField();
		jText.addKeyListener(new KeyAdapter() {
			@Override

			/* press Enter to Look up */
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String str = dicM.dictionaryLookup(jText.getText());
					explainArea.setText(str);
				}
			}

			@Override
			/* Search by typing */
			public void keyTyped(KeyEvent e) {
				if (apiCheck.isSelected()) {
					
				} else {
					LinkedList<Word> temp = new LinkedList<Word>();
					String text = jText.getText();
					text += e.getKeyChar();
					text = text.toLowerCase().trim();
					DefaultListModel<String> md = new DefaultListModel<>();
					temp = dicManL.dictionarySearcher(text);
					for (Word w : temp) {
						md.addElement(w.word_target);
					}

					listWord.setModel(md);
//							if(md.size() > 0)listWord.setSelectedIndex(0);
					try {
						if (!text.equals("")) {
							explainArea.setText(temp.getFirst().word_explain);
						}
					} catch (Exception event) {
						explainArea.setText("Sorry, Dictionary doesn't have this word!!!");
					}
				}
				// listWord.setSelectedIndex(0);
			}
		});
		listWord.setBackground(Color.WHITE);
		listWord.setBounds(31, 102, 170, 348);
		
		// contentPane.add(listWord);
		jText.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		jText.setToolTipText("Input text here");
		jText.setBounds(41, 68, 389, 35);
		contentPane.add(jText);
		jText.setColumns(10);
		
		/*Add button Search*/
		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setBackground(new Color(255, 255, 255));
		// Click Button to Search
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (apiCheck.isSelected()) {
					try {
						String str = GoogleTranslate.translate("en", "vi", jText.getText());
						explainArea.setText(str);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					String str = dicM.dictionaryLookup(jText.getText());
					explainArea.setText(str);
				}
			}
		});
		btnSearch.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnSearch.setBounds(450, 68, 102, 35);
		contentPane.add(btnSearch);

		/* Speech Text */
		Voice voice = new Voice("kevin16");
//		JButton speechButton = new JButton("Speak");
//		speechButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String str = jText.getText();
//				if (str.equals("")) {
//					str = "Sorry, you do nothing";
//				}
//
//				voice.say(str);
//			}
//		});
		
		JLabel speechLabel = new JLabel("");
		speechLabel.setToolTipText("click here to speak");
		speechLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		speechLabel.setBounds(574, 68, 35, 35);
		contentPane.add(speechLabel);
		ImageIcon add_icon = new ImageIcon("img/speaking.png");
		Image i = add_icon.getImage();
		add_icon = new ImageIcon(i.getScaledInstance(35, 35, Image.SCALE_SMOOTH));
		speechLabel.setIcon(add_icon);
		speechLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String str = jText.getText();
				if (str.equals("")) {
					str = "Sorry, you do nothing";
				}

				voice.say(str);
			}
		});
		ImageIcon oIcon = new ImageIcon("img/magnifying-glass.png");
		i = oIcon.getImage();
		oIcon = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		
		JLabel dLabel = new JLabel("");
		dLabel.setBounds(222, 5, 60, 60);
		contentPane.add(dLabel);
		ImageIcon dIcon = new ImageIcon("img/d.png");
		i = dIcon.getImage();
		dIcon = new ImageIcon(i.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		dLabel.setIcon(dIcon);
		ImageIcon introIcon = new ImageIcon("img/abc.png");
		i = introIcon.getImage();
		introIcon = new ImageIcon(i.getScaledInstance(115, 115, Image.SCALE_SMOOTH));
		setVisible(false);
		
		
		/* Add Label */
		JLabel addLabel = new JLabel("");
		addLabel.setToolTipText("Add word");
		
		// Add word
		JPanel addPanel = new JPanel();
		addPanel.setBackground(UIManager.getColor("CheckBox.highlight"));
		addPanel.setBounds(115, 106, 650, 436);
		contentPane.add(addPanel);
		addPanel.setLayout(null);
		
		addTargetTextField = new JTextField();
		addTargetTextField.setBounds(309, 110, 216, 30);
		addPanel.add(addTargetTextField);
		addTargetTextField.setColumns(10);
		
		addExplainTextField = new JTextField();
		addExplainTextField.setBounds(309, 196, 216, 30);
		addPanel.add(addExplainTextField);
		addExplainTextField.setColumns(10);
		
		JButton addTargetButton = new JButton("Add Word");
		addTargetButton.setBackground(SystemColor.text);
		addTargetButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		addTargetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dicM.addWord(addTargetTextField.getText(), addExplainTextField.getText());
				listWord.removeAll();
				for (Word w : dicM.dictionaries.words) {
					model.addElement(w.word_target);
				}
				listWord.setModel(model);
				model.addElement(addTargetTextField.getText());
				scrollPane.setVisible(true);
				JOptionPane.showMessageDialog(addPanel, "Add word  successfully, scroll your list to see word");
			}
		});
		addTargetButton.setBounds(309, 245, 111, 30);
		addPanel.add(addTargetButton);
		
		JButton doneBtn = new JButton("Done");
		doneBtn.setToolTipText("End edit word");
		doneBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		doneBtn.setBackground(SystemColor.text);
		doneBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPanel.setVisible(false);
				explainArea.setVisible(true);
				scrollPane.setVisible(true);
//				listWord.removeAll();
//				model.removeAllElements();
//				for (Word w : dicM.dictionaries.words) {
//					model.addElement(w.word_target);
//				}
//				listWord.setModel(model);
			}
		});
		
		/* to end pane edit */
		doneBtn.setBounds(524, 376, 89, 30);
		addPanel.add(doneBtn);
		
		JLabel addTargetWordLabel = new JLabel("Input English Word");
		addTargetWordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		addTargetWordLabel.setBounds(309, 86, 216, 23);
		addPanel.add(addTargetWordLabel);
		
		JLabel addExplainWordLabel = new JLabel("Input Vietnamese Word");
		addExplainWordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		addExplainWordLabel.setBounds(309, 172, 216, 23);
		addPanel.add(addExplainWordLabel);
		addPanel.setVisible(false);
		addLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addPanel.setVisible(true);
				scrollPane.setVisible(false);
				explainArea.setVisible(false);
			}
		});
		
		addLabel.setBounds(30, 190, 60, 60);
		contentPane.add(addLabel);
		ImageIcon addIcon = new ImageIcon("img/add-file.png");
		i = addIcon.getImage();
		addIcon = new ImageIcon(i.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		addLabel.setIcon(addIcon);
		
		/* Delete Pane */
		JPanel deletePanel = new JPanel();
		deletePanel.setBackground(UIManager.getColor("CheckBox.highlight"));
		deletePanel.setBounds(115, 106, 650, 436);
		contentPane.add(deletePanel);
		deletePanel.setLayout(null);
		
		
		JLabel deleteWordLabel = new JLabel("Input English Word");
		deleteWordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		deleteWordLabel.setBounds(309, 86, 216, 23);
		deletePanel.add(deleteWordLabel);
		deleteWordLabel.setVisible(false);
		
		JTextField deleteTextField = new JTextField();
		deleteTextField.setBounds(309, 110, 216, 30);
		deletePanel.add(deleteTextField);
		deleteTextField.setColumns(10);
		deleteTextField.setVisible(false);
		
		JButton removeWordButton = new JButton("Remove Word");
		removeWordButton.setHorizontalAlignment(SwingConstants.LEFT);
		removeWordButton.setBounds(309, 150, 129, 30);
		removeWordButton.setBackground(SystemColor.text);
		removeWordButton.setVisible(false);
		removeWordButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		deletePanel.add(removeWordButton);
		removeWordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dicM.deleteWord(deleteTextField.getText());
				listWord.removeAll();
				model.removeAllElements();
				for (Word w : dicM.dictionaries.words) {
					model.addElement(w.word_target);
				}
				listWord.setModel(model);
				JOptionPane.showMessageDialog(addPanel, "Delete word  successfully, scroll your list to see word");
			}
		});
		
		JButton doneBtnDel = new JButton("Done");
		doneBtnDel.setToolTipText("End edit word");
		doneBtnDel.setFont(new Font("Tahoma", Font.BOLD, 12));
		doneBtnDel.setBackground(SystemColor.text);
		doneBtnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletePanel.setVisible(false);
				explainArea.setVisible(true);
				scrollPane.setVisible(true);
			}
		});
		
		/* to end pane delete */
		doneBtnDel.setBounds(524, 376, 89, 30);
		deletePanel.add(doneBtnDel);
		
		JButton replaceWordButton = new JButton("Replace");
		/**/
		
		JLabel deleteLabel = new JLabel("");
		deleteLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deletePanel.setVisible(true);
				explainArea.setVisible(false);
				scrollPane.setVisible(true);
				addPanel.setVisible(false);
				deleteTextField.setVisible(true);
				deleteWordLabel.setVisible(true);
				removeWordButton.setVisible(true);
				replaceWordButton.setVisible(false);
			}
		});
		
		deleteLabel.setToolTipText("Delete Word");
		deleteLabel.setBounds(30, 390, 60, 60);
		contentPane.add(deleteLabel);
		ImageIcon deleteIcon = new ImageIcon("img/delete.png");
		i = deleteIcon.getImage();
		deleteIcon = new ImageIcon(i.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		deleteLabel.setIcon(deleteIcon);
		
		/* Replace Pane */
		JPanel replacePanel = new JPanel();
		replacePanel.setBackground(UIManager.getColor("CheckBox.highlight"));
		replacePanel.setBounds(115, 106, 650, 436);
		contentPane.add(replacePanel);
		replacePanel.setLayout(null);
		replacePanel.setVisible(false);
		
			/* target word text field before */
		JTextField targetTextFieldBefore = new JTextField();
		targetTextFieldBefore.setBounds(200, 110, 200, 30);
		replacePanel.add(targetTextFieldBefore);
		targetTextFieldBefore.setColumns(10);
		
//			/* explain word text field before */
//		JTextField explainTextFieldBefore = new JTextField();
//		explainTextFieldBefore.setBounds(200, 176, 200, 30);
//		replacePanel.add(explainTextFieldBefore);
//		explainTextFieldBefore.setColumns(10);
		
			/* target and explain label before*/
		JLabel targetWordLabelBefore = new JLabel("Old English Word");
		targetWordLabelBefore.setFont(new Font("Tahoma", Font.BOLD, 14));
		targetWordLabelBefore.setBounds(200, 86, 200, 23);
		replacePanel.add(targetWordLabelBefore);
//		JLabel expalainWordLabelBefore = new JLabel("Old Vietnamese Word");
//		expalainWordLabelBefore.setFont(new Font("Tahoma", Font.BOLD, 14));
//		expalainWordLabelBefore.setBounds(200, 152, 200, 23);
//		replacePanel.add(expalainWordLabelBefore);
		
		/* target word text field after */
		JTextField targetTextFieldAfter = new JTextField();
		targetTextFieldAfter.setBounds(430, 110, 200, 30);
		replacePanel.add(targetTextFieldAfter);
		targetTextFieldAfter.setColumns(10);
		
			/* explain word text field after */
		JTextField explainTextFieldAfter = new JTextField();
		explainTextFieldAfter.setBounds(430, 176, 200, 30);
		replacePanel.add(explainTextFieldAfter);
		explainTextFieldAfter.setColumns(10);
		
			/* target and explain label before*/
		JLabel targetWordLabelAfter = new JLabel("New English Word");
		targetWordLabelAfter.setFont(new Font("Tahoma", Font.BOLD, 14));
		targetWordLabelAfter.setBounds(430, 86, 200, 23);
		replacePanel.add(targetWordLabelAfter);
		JLabel explainWordLabelAfter = new JLabel("New Vietnamese Word");
		explainWordLabelAfter.setFont(new Font("Tahoma", Font.BOLD, 14));
		explainWordLabelAfter.setBounds(430, 152, 200, 23);
		replacePanel.add(explainWordLabelAfter);
		
		/* Button replace */
		replaceWordButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dicM.deleteWord(targetTextFieldBefore.getText());
				dicM.addWord(targetTextFieldAfter.getText(), explainTextFieldAfter.getText());
				listWord.removeAll();
				model.removeAllElements();
				for (Word w : dicM.dictionaries.words) {
					model.addElement(w.word_target);
				}
				listWord.setModel(model);
				JOptionPane.showMessageDialog(addPanel, "Delete word  successfully, scroll your list to see word");
			}
		});
		replaceWordButton.setBounds(370, 230, 90, 30);
		replaceWordButton.setBackground(SystemColor.text);
		replaceWordButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		replacePanel.add(replaceWordButton);
		
		JButton doneBtnReplace = new JButton("Done");
		doneBtnReplace.setToolTipText("End edit word");
		doneBtnReplace.setFont(new Font("Tahoma", Font.BOLD, 12));
		doneBtnReplace.setBackground(SystemColor.text);
		doneBtnReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPanel.setVisible(false);
				deletePanel.setVisible(false);
				replacePanel.setVisible(false);
				explainArea.setVisible(true);
				scrollPane.setVisible(true);
			}
		});
		
		/* to end pane edit */
		doneBtnReplace.setBounds(524, 376, 89, 30);
		replacePanel.add(doneBtnReplace);
		
		
		/* editLabel 
		 * to setVisible replace Pane
		 */
		JLabel editLabel = new JLabel("");
		editLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				replacePanel.setVisible(true);
				explainArea.setVisible(false);
				scrollPane.setVisible(true);
				addPanel.setVisible(false);
				deletePanel.setVisible(false);
				replaceWordButton.setVisible(true);
			}
		});
		
		/*
		 * Create List Word When click Word in ListWord => display explain word_explain
		 * in explainArea And Jtext setText to Speech
		 */
		
		listWord.setModel(model);
		listWord.setFont(listWord.getFont().deriveFont(listWord.getFont().getStyle() | Font.BOLD));
		listWord.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String str = (String) listWord.getSelectedValue();
				explainArea.setText(dicM.dictionaryLookup(str));
				jText.setText(str);
			}
		});
		editLabel.setToolTipText("Edit Word");
		editLabel.setBounds(30, 290, 60, 60);
		contentPane.add(editLabel);
		ImageIcon editIcon = new ImageIcon("img/edit.png");
		i = editIcon.getImage();
		editIcon = new ImageIcon(i.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		editLabel.setIcon(editIcon);
		
		JLabel lblNewLabel_1 = new JLabel("English");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(127, 103, 204, 25);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Vietnamese");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		lblNewLabel_1_1.setBackground(Color.WHITE);
		lblNewLabel_1_1.setBounds(347, 103, 399, 25);
		contentPane.add(lblNewLabel_1_1);
		
		
		/* Database
		 * Using JDBC and Sql Server
		 */
		
		
	}
}