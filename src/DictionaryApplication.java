import java.awt.BorderLayout;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.event.ListSelectionListener;

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

/*Dictionary App
 * Author : Xuân Linh
 * 
 */
public class DictionaryApplication extends JFrame {

	private JPanel contentPane;
	private JTextField jText;
	DictionaryManagement dicM = new DictionaryManagement();
	DictionaryCommandLine dicManL = new DictionaryCommandLine();

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
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		//

		/*
		 * Create List Word When click Word in ListWord => display explain word_explain
		 * in explainArea And Jtext setText to Speech
		 */
		JList listWord = new JList();
		listWord.setFont(listWord.getFont().deriveFont(listWord.getFont().getStyle() | Font.BOLD));
		listWord.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String str = (String) listWord.getSelectedValue();
				explainArea.setText(dicM.dictionaryLookup(str));
				jText.setText(str);
			}
		});
		// Set scroll
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (Word w : dicM.dictionaries.words) {
			model.addElement(w.word_target);
		}
		listWord.setModel(model);

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
//						if(md.size() > 0)listWord.setSelectedIndex(0);
				try {
					if (!text.equals("")) {
						explainArea.setText(temp.getFirst().word_explain);
					}
				} catch (Exception event) {
					explainArea.setText("Sorry, Dictionary doesn't have this word!!!");
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
				String str = dicM.dictionaryLookup(jText.getText());
				explainArea.setText(str);
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
		
		JLabel oLabel = new JLabel("");
		oLabel.setBounds(372, 21, 30, 30);
		contentPane.add(oLabel);
		ImageIcon oIcon = new ImageIcon("img/magnifying-glass.png");
		i = oIcon.getImage();
		oIcon = new ImageIcon(i.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		oLabel.setIcon(oIcon);
		
		JLabel dLabel = new JLabel("");
		dLabel.setBounds(222, 5, 60, 60);
		contentPane.add(dLabel);
		ImageIcon dIcon = new ImageIcon("img/d.png");
		i = dIcon.getImage();
		dIcon = new ImageIcon(i.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		dLabel.setIcon(dIcon);
		
		JLabel introLabel = new JLabel("");
		introLabel.setBounds(0, 0, 115, 115);
		contentPane.add(introLabel);
		ImageIcon introIcon = new ImageIcon("img/abc.png");
		i = introIcon.getImage();
		introIcon = new ImageIcon(i.getScaledInstance(115, 115, Image.SCALE_SMOOTH));
		introLabel.setIcon(introIcon);
		
		JLabel addLabel = new JLabel("");
		addLabel.setBounds(30, 190, 60, 60);
		contentPane.add(addLabel);
		ImageIcon addIcon = new ImageIcon("img/add-file.png");
		i = addIcon.getImage();
		addIcon = new ImageIcon(i.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		addLabel.setIcon(addIcon);
		
		JLabel editLabel = new JLabel("");
		editLabel.setBounds(30, 290, 60, 60);
		contentPane.add(editLabel);
		ImageIcon editIcon = new ImageIcon("img/edit.png");
		i = editIcon.getImage();
		editIcon = new ImageIcon(i.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		editLabel.setIcon(editIcon);
		
		JLabel deleteLabel = new JLabel("");
		deleteLabel.setBounds(30, 390, 60, 60);
		contentPane.add(deleteLabel);
		ImageIcon deleteIcon = new ImageIcon("img/delete.png");
		i = deleteIcon.getImage();
		deleteIcon = new ImageIcon(i.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
		deleteLabel.setIcon(deleteIcon);
		
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
		
		/* API */

	}
}