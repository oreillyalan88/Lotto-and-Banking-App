package ie.wit.bankaccount;

import ie.wit.customer.Customer;
import ie.wit.exceptions.ContainsWhiteSpaceException;
import ie.wit.exceptions.MustBeLetters;
import ie.wit.exceptions.PasswLength;
import ie.wit.exceptions.StringLength;
import ie.wit.io.FileHandler;








import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class BankRegisterForm extends JFrame implements ActionListener {/*
	*//**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	public static File myFileBank = new File("Bankaccounts.dat"); // creates an new file called
													// "accounds.dat" and
													// assigns it the name
													// myFile, which it will be
													// refered to as from now on


	public static File myCounter = new File("counts.dat");   // creates file called counts.dat to store counter for account number
	public static FileHandler<HashMap<Integer, Bank>> hashmapHandlerBank  //creates instance of generic file handler and passes
	= new FileHandler<HashMap<Integer, Bank>>();						 // the hashmap <K, V>, so the generic methods of FileHandler class
																		// now be used aforementioned HashMaps 
	
	public static FileHandler<Integer> counterHandler		// creates an instance of the generic class FileHandler to allow
	= new FileHandler<Integer> ();							// <Integers to be read in and wrote out to a file
	
	Integer count;
	
	public static HashMap<Integer, Bank> AccountList = new HashMap<Integer, Bank>(); // creates
																	// new HASHMAP
																	// of type
																	// < Integer, Bank>

	JFrame freg = new JFrame();
	JPanel panelreg;

	JLabel lblaccreg;
	JLabel lblpasswordreg;
	JLabel lblfirstnamereg;
	JLabel lblsecondnamereg;
	JLabel lbladdress;
	static JLabel lblmessreg;

	JTextField txtaccreg;
	JPasswordField txtpasswordreg;
	JTextField txtfirstnamereg;
	JTextField txtsecondnamereg;
	JTextField txtaddressreg;

	JButton btsubmit;
   public String firstLetterCaps ( String givenString )  // used to avoid string order issues
   
   
	  {
	      String firstLetter = givenString.substring(0,1).toUpperCase();
	      String restLetters = givenString.substring(1).toLowerCase();
	      return firstLetter + restLetters;
	  }
  

   
   public static String toTitleCase(String givenString) {       //// used to avoid string order issues when sorting
	    String[] arr = givenString.split(" ");
	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < arr.length; i++) {
	        sb.append(Character.toUpperCase(arr[i].charAt(0)))
	            .append(arr[i].substring(1)).append(" ");
	    }          
	    return sb.toString().trim();
	} 
   
   
   public static String containsWhiteSpace(String givenString) throws ContainsWhiteSpaceException { // validates against white space when needed
	    if(givenString != null){
	        for(int i = 0; i < givenString.length(); i++){
	            if(Character.isWhitespace(givenString.charAt(i))){
	            	throw new ContainsWhiteSpaceException();
	            }
	        }
	    }
	    return givenString;
	}
   
	private static String usernameLength(String username, int min)  // custom
																	// exception
																	// used to
																	// handle
																	// the
																	// username
																	// length
																	// validation
			throws StringLength {
		if (username.length() < min)
			throw new StringLength();

		return username;
	}

	private static String passLength(String passw, int min) throws PasswLength { // custom
																					// exception
																					// used
																					// to
																					// handle
																					// the
																					// password
																					// length
																					// validation
		if (passw.length() < min)
			throw new PasswLength();

		return passw;
	}

	private static String inputType(String data) throws MustBeLetters { // custom
																		// exception
																		// used
																		// to
																		// make
																		// sure
																		// first
																		// name
																		// an
																		// second
																		// name
																		// are
																		// Alphabetic,
		// Unfortunately stops hyphens spaces and inverted comma

		for (int i = 0; i < data.length(); i++) {

			char x = data.charAt(i);
			if (!(Character.isAlphabetic(x)))

				throw new MustBeLetters();
		}
		return data;

	}


	public BankRegisterForm() { // register form is public because of its use in
							// BankLoginFormn

		freg.setSize(280, 300);
		freg.setLocation(965, 365);
		freg.setTitle("Member Registration");
		lblaccreg = new JLabel("Account Number:");
		lblpasswordreg = new JLabel("Password:");
		lblfirstnamereg = new JLabel("First Name:");
		lblsecondnamereg = new JLabel("Second Name:");
		lbladdress = new JLabel("Address:");
		lblmessreg = new JLabel("");
		btsubmit = new JButton("Submit");
		btsubmit.addActionListener(this); // event handler assigned to submit
											// button
		
		
		if (checkFile(myCounter) == true){				// if the counter file exists
			
			try {
				count=counterHandler.readIn(myCounter);	 // try read in the Integer value and assign it to static Integer count
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,
						"Error Reading From File.");
			}
		}
		
		else {count= 20060000;}		//if the file doesnt exists set the count to 20060000
				
		txtaccreg = new JTextField(20);
		txtaccreg.setHorizontalAlignment(JTextField.CENTER); // centre account number
		txtaccreg.setText(""+count); // store generated account number in JTextField
		txtaccreg.setEditable(false); // grey out TextField
		txtpasswordreg = new JPasswordField(20);
		txtpasswordreg.addKeyListener(new KeyList()); // listener interface for
														// receiving keyboard
														// events, also creates
														// a new KeyList object
														// to be used in the the
														// checkStrngth method
		txtfirstnamereg = new JTextField(20);
		txtsecondnamereg = new JTextField(20);
		txtaddressreg = new JTextField(20);

		panelreg = new JPanel();
		panelreg.add(lblaccreg);
		panelreg.add(txtaccreg);
		panelreg.add(lblpasswordreg);
		panelreg.add(txtpasswordreg);
		panelreg.add(lblfirstnamereg);
		panelreg.add(txtfirstnamereg);
		panelreg.add(lblsecondnamereg);
		panelreg.add(txtsecondnamereg);
		panelreg.add(lbladdress);
		panelreg.add(txtaddressreg);
		panelreg.add(btsubmit);
		panelreg.add(lblmessreg);
		freg.add(panelreg);
		freg.setVisible(true);
		
		txtaccreg.requestFocus();

	}

	public void actionPerformed(ActionEvent e) { // event listner to handle what
													// is done when the submit
													// button is pressed

		if (e.getSource() == btsubmit) {
			try {

				Integer accNo = Integer.parseInt(txtaccreg.getText());
				String fname = firstLetterCaps(txtfirstnamereg.getText());
				String sname = firstLetterCaps(txtsecondnamereg.getText());
				String passw = new String(txtpasswordreg.getPassword());
				String address = toTitleCase(txtaddressreg.getText());
				
				inputType(fname);
				containsWhiteSpace(fname);
				usernameLength(fname, 2);

				
				inputType(sname);
				containsWhiteSpace(fname);
				usernameLength(sname, 2);


				
				usernameLength(address, 2);


				passLength(passw, 6);

				


				if (!checkBlank( fname, sname, passw, address,  // first
																		// make
																		// sure
																		// textfields
																		// are
																		// not
																		// blank
						lblfirstnamereg, lblsecondnamereg, lblpasswordreg, lbladdress)) {
					if (checkExist(accNo) == false) { // then make sure the user
														// name does not already
														// exist, to avoid
														// duplicates

						Bank acc = new Bank(fname, sname, address, // if
																			// all
																			// conditions
																			// are
																			// met,
																			// create
																			// a
																			// new
																			// customer
																			// object
								passw, 0);
						AccountList.put(accNo, acc); //set accNo as key, and acc<Bank> as value
						JOptionPane.showMessageDialog(null, "Account Registered");
						hashmapHandlerBank.writeOut(AccountList, myFileBank);// save to file
						count++;	//count iterates by 1
						counterHandler.writeOut(count, myCounter); // save count for next registration
						freg.setVisible(false);
					}

				}
			} catch (StringLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
			} catch (PasswLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);

			} catch (MustBeLetters a) {
				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Error in writing to file...");
			} catch (ContainsWhiteSpaceException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,
						"First Name and Second Name can contain no spaces.");
			} 
			
			 catch (IOException e1) {
				// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,
							"Input Error, Please Try Again.");

		}
	}
	}
	private class KeyList extends KeyAdapter { // The KeyList class extends the
												// KeyAdapter class to rewrite
												// the keyPressed method of the
												// KeyAdpater class.
		public void keyPressed(KeyEvent ke) { // This method will enable the
												// txtpasswordreg text to check
												// its strength when the key is
												// pressed by invoking the
												// checkStrength method.
			String passw = new String(txtpasswordreg.getPassword());
			String mess = checkStrength(passw);
			showMess(mess + " password", lblpasswordreg);
		}

	}

	private boolean checkBlank( String passw,
			String fname, String sname, String address,JLabel passwmess, JLabel fnamemess,
			JLabel snamemess, JLabel addressmess) {
		boolean hasBlank = false;
		if (fname.length() < 1) {
			showMess("Fist name is required.", fnamemess);
			hasBlank = true;
		}
		if (sname.length() < 1) {
			showMess("Second name is required.", snamemess);
			hasBlank = true;
		}
		if (passw.length() < 1) {
			showMess("Password is required.", passwmess);
			hasBlank = true;
		}
		if (address.length() < 1) {
			showMess("Password is required.", addressmess);
			hasBlank = true;
		}
		return hasBlank;

	}

	private static void showMess(String mess, JLabel lbl) { // show message
															// method
		lbl.setText(mess);
		lbl.setForeground(Color.RED);
	}

	private static String checkStrength(String passw) { // check strngth method

		Pattern pat = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]))"); // creates
																			// the
																			// regular
																			// expression
																			// pattern
																			// to
																			// be
																			// used
																			// to
																			// check
																			// the
																			// password
																			// strength
		Matcher mat = pat.matcher(passw); // matcher is created from the pattern
											// to check the password against
		if (mat.find()) { // if the a match entered string matches above
							// criteria (uppercase, number, lowecase)
			if (passw.length() >= 8) // and is greater than or equal to 8
										// characthers long
				return "Strong"; // return string
			else
				return "Medium"; // else return medium
		} else
			return "Weak"; // if not a match its weak
	}

	public static boolean checkExist(Integer accNo) { // method to check if the
													// username has already been
													// used

		boolean exist = false;
		try {

			if (getCustomer(accNo) != null) {
				showMess("The account already exists.", lblmessreg);
				exist = true;

			}

		} catch (Exception ie) {
			JOptionPane.showMessageDialog(null, "Error!");
		}
		return exist;
	}

	public static boolean search(Integer accNo) { // method used to search
														// the
														// array for the
														// username
		boolean blnFound = AccountList.containsKey(accNo);
		return blnFound;
	}

	public static Bank getCustomer(Integer accNo) { // method to return
															// the index of the
															// object once its
															// found, or null,
															// to indicate it
															// hasn't, once
															// again expects an
															//index or null		
		boolean exist = search(accNo);
		if (exist == false) {
			return null;
		} else {
			return AccountList.get(accNo);
		}
	}


	public static boolean checkFile(File fileToCheck) { // method to check if a file
										// file exists in RegisterForm

		if (fileToCheck.exists()) {
			return true;

		} else
			return false;

	}

}
