import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/*
 * This is the test program that uses SearchableLinkedList and MyMazeNode class
 * The program utilizes the heavyweight GUI components in Java
 */

@SuppressWarnings("serial")
public class Assembler extends JFrame implements ActionListener {

	// GUI elements needed for this class
	JButton jButton = new JButton("Choose input file...");
	JFileChooser jFileChooser = new JFileChooser();
	JTextArea jta1 = new JTextArea();
	JTextArea jta2 = new JTextArea();

	public Assembler() {

		setTitle("Custom Assembler");

		// Setting layouts and placing components
		Container container = getContentPane();
		container.setLayout(new BorderLayout());

		// Setting up panels
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(0, 2, 2, 2));

		// Adding components to panels
		p1.add(jButton, BorderLayout.NORTH);
		p1.add(new JLabel("Assembler code"), BorderLayout.WEST);
		p1.add(new JLabel("Machine code"), BorderLayout.EAST);
		p2.add(new JScrollPane(jta1));
		p2.add(new JScrollPane(jta2));

		// Adding components to container
		container.add(p1, BorderLayout.NORTH);
		container.add(p2, BorderLayout.CENTER);

		// Register action listener
		jButton.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {
		// on click, open file selector dialog

		if (e.getSource() == jButton)
			try {
				openDialog();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	}

	private void openDialog() throws Exception {

		// TEST - view current path
		System.out.println("Current Directory = " + jFileChooser.getCurrentDirectory().getPath());
		// Setting a preselected file for file selector
		jFileChooser.setSelectedFile(new File(jFileChooser.getCurrentDirectory(), "Assemble.txt"));

		if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			openFile(jFileChooser.getSelectedFile());
		}
	}

	private void openFile(File selectedFile) throws Exception {
		// Opens the text file and shows it on the window

		// init area
		BufferedReader infile = null;
		String inLine;
		jta1.setText(null);
		jta2.setText(null);

		try {
			// Create a character-input stream from the file
			infile = new BufferedReader(new FileReader(selectedFile));

			// read each line
			while ((inLine = infile.readLine()) != null) {
				jta1.append(inLine + '\n');
			}

			/*
			 * This is the main execution area
			 */
			interprete();

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} finally {
			// Need to close the file resource if exception occurs

			try {
				if (infile != null)
					infile.close();
			} catch (IOException ex) {
				// throws an exception inside an exception when action fails
				System.out.println(ex.getMessage());
			}
		}
	}

	private void interprete() {
		String[] token = jta1.getText().toLowerCase().split("\\s|, ");
		// StringTokenizer st = new StringTokenizer(jta1.getText(), "
		// \t\n\r,:");

		HashMap<String, String> op1 = new HashMap<String, String>();
		HashMap<String, String> op2 = new HashMap<String, String>();
		HashMap<String, String> op3 = new HashMap<String, String>();

		op1.put("halt", "000");
		op1.put("jump", "001");
		op1.put("jumpn", "010");
		op1.put("jumpz", "011");
		op1.put("load", "100");
		op1.put("store", "101");

		op2.put("movein", "110000");
		op2.put("moveout", "110001");
		op2.put("neg", "110010");
		op2.put("bshl", "110011");
		op2.put("bshr", "110100");
		op2.put("input", "110101");
		op2.put("output", "110110");
		op2.put("add", "1110");
		op2.put("mult", "1111");

		op3.put("r0", "00");
		op3.put("r1", "01");
		op3.put("r2", "10");
		op3.put("r3", "11");

		jta2.append("--Pass 1 (tokenize)--\n");
		for (int x = 0; x < token.length; x++)
			jta2.append(token[x] + "\n");
		jta2.append("\n--Pass 2 (opcode)--\n");

		String[] decode = token.clone();
		String s;  
		for (int x = 0; x < decode.length; x++) {
			if (decode[x].equals("halt"))
				break;
			s = op1.get(decode[x]);
			if (s != null) {
				decode[x] = s;
				// check if the next one is binary
				try {
					Integer.parseInt(decode[x + 1]);
					x = x + 1;
				} catch (NumberFormatException nfe) {
					//non-binary value
					String var = decode[x + 1];
					
					// find the ref for variable in the array
					for (int y = 0; y < token.length; y++) {
						if (token[y].equals(var + ":")) {
							// label found
							
							//skipping one index
							int i = Integer.parseInt(token[y + 1]);
							decode[x + 1] = Integer.toBinaryString(i);
							x = x + 1;
							break;
						}
					}
				}
			} else {
				s = op2.get(token[x]);
				if (s != null)
					decode[x] = s;
				else {
					s = op3.get(token[x]);
					if (s != null)
						decode[x] = s;
				}

			}
			jta2.append(decode[x]);
		}

	}

	private static String numFlipper(String bin) {
		String s = "";
		for (int i = 0; i < bin.length(); i++) {
			if (bin.charAt(i) == '0')
				s += "1";
			else
				s += "0";
		}
		return s;
	}

	public static void main(String[] args) {

		// Creating a GUI frame
		Assembler frame = new Assembler();
		frame.setSize(600, 400);

		// Setting a minimum size
		Dimension minsize = new Dimension(300, 300);
		frame.setMinimumSize(minsize);

		// Let's center it!

		// Get the dimension of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		// Get the dimension of the frame
		Dimension frameSize = frame.getSize();
		int x = (screenWidth - frameSize.width) / 2;
		int y = (screenHeight - frameSize.height) / 2;

		frame.setLocation(x, y);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
