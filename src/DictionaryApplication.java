import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.ScrollPane;

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
import javax.swing.event.ListSelectionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyAdapter;
import java.util.LinkedList;

/*Dictionary App*/
public class DictionaryApplication extends JFrame {

	private JPanel contentPane;
	private JTextField wordInput;
	DictionaryManagement dicM= new DictionaryManagement();
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
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Title 
		JLabel lblNewLabel = new JLabel("DICTIONARY");
		lblNewLabel.setBackground(Color.GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 99, 71));
		lblNewLabel.setFont(new Font("Segoe UI Historic", Font.PLAIN, 51));
		lblNewLabel.setBounds(63, 0, 427, 50);
		contentPane.add(lblNewLabel);
		
		//Create Explain Area
		JTextArea explainArea = new JTextArea();
		explainArea.setFont(new Font("Arial", Font.PLAIN, 20));
		explainArea.setBackground(Color.WHITE);
		explainArea.setBounds(211, 102, 349, 348);
		contentPane.add(explainArea);
		
		//
		
		/*
		 * Create List Word
		 * When click Word in ListWord => display explain word_explain in explainArea
		 */
		JList listWord = new JList();
		listWord.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		listWord.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String str = (String) listWord.getSelectedValue();
				explainArea.setText(dicM.dictionaryLookup(str));
			}
		});
		
		listWord.setBackground(Color.WHITE);
		listWord.setBounds(31, 102, 170, 348);
		contentPane.add(listWord);
		
		// Set scroll 
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (Word w : dicM.dictionaries.words) {
			model.addElement(w.word_target);
		}
		listWord.setModel(model);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(31, 102, 170, 348);
		contentPane.add(scrollPane);
		scrollPane.setVisible(true);
		scrollPane.add(listWord);
		
		/*Create Jtext Filed */
		wordInput = new JTextField();
		wordInput.addKeyListener(new KeyAdapter() {
			@Override
			
			/*press Enter to Look up*/
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String str = dicM.dictionaryLookup(wordInput.getText());
					explainArea.setText(str);
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				LinkedList<Word> temp = new LinkedList<Word>();
				String text = wordInput.getText();
				text += e.getKeyChar();
				text = text.toLowerCase().trim();
				DefaultListModel<String> md = new DefaultListModel<>();
				temp = dicManL.dictionarySearcher(text);
				for(Word w : temp) {
					md.addElement(w.word_target);
				}
				listWord.setModel(md);
				
			}
		});
		
		
		wordInput.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		wordInput.setToolTipText("Input text here");
		wordInput.setBounds(63, 61, 300, 30);
		contentPane.add(wordInput);
		wordInput.setColumns(10);
		
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setBackground(new Color(255, 255, 255));
		// Click Button to Search
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = dicM.dictionaryLookup(wordInput.getText());
				explainArea.setText(str);
			}
		});
		btnSearch.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		btnSearch.setBounds(388, 61, 102, 30);
		contentPane.add(btnSearch);
		
		
	}
}