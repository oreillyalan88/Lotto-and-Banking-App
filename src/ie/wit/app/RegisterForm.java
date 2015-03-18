package ie.wit.app;

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

public class RegisterForm extends JFrame implements ActionListener {/*
	*//**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	public static File myFile = new File("accounts.dat"); // creates an new file called
													// "accounds.dat" and
													// assigns it the name
													// myFile, which it will be
													// refered to as from now on



	public static HashMap<String, Customer> CustomerList = new HashMap<String, Customer>(); // creates
																	// new hashmap with String Key
																	// of type
																	// Customer

	JFrame freg = new JFrame();
	JPanel panelreg;

	JLabel lblnamereg;   // create Jlabels etc
	JLabel lblpasswordreg;
	JLabel lblfirstnamereg;
	JLabel lblsecondnamereg;
	static JLabel lblmessreg;

	JTextField txtnamereg;
	JPasswordField txtpasswordreg;
	JTextField txtfirstnamereg;
	JTextField txtsecondnamereg;

	JButton btsubmit;
	
	
	 public String firstLetterCaps ( String givenString )  // method to structure string input, needed for sorting
	   
	   
	  {
	      String firstLetter = givenString.substring(0,1).toUpperCase(); // firstLetter becomes upper case
	      String restLetters = givenString.substring(1).toLowerCase(); // restletters become lower case
	      return firstLetter + restLetters; // return proper format concatinated 
	  }
 

  
  public static String toTitleCase(String givenString) { // method needed to format address, because spaces are allowed
	    String[] arr = givenString.split(" ");          // split given string by spaces, add to array
	    StringBuffer sb = new StringBuffer();			// new instance of StringBuffer

	    for (int i = 0; i < arr.length; i++) {					// navigate through arr array
	        sb.append(Character.toUpperCase(arr[i].charAt(0)))  // convert first char of each index to uppercase,add to sb
	            .append(arr[i].substring(1)).append(" ");		//append the rest of the string, plus the space to sb
	    }          
	    return sb.toString().trim();					//return toString of sb with leading and trailing whiteSpace trimed
	} 
  
  
  public static String containsWhiteSpace(String givenString) throws ContainsWhiteSpaceException { //custom exception to check for white space with First and Second
	    if(givenString != null){                  // check if string was entered
	        for(int i = 0; i < givenString.length(); i++){	//navigate the length of the string
	            if(Character.isWhitespace(givenString.charAt(i))){  //if white space is found 
	            	throw new ContainsWhiteSpaceException();	// throw exception
	            }
	        }
	    }
	    return givenString;		// return the entered string
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

		for (int i = 0; i < data.length(); i++) {	// navigate though the passed in string

			char x = data.charAt(i);               // x is the index of the navigation
			if (!(Character.isAlphabetic(x)))     // if the char is NOT alphapetical

				throw new MustBeLetters();			// throw exception
		}
		return data;								// return data

	}


	public RegisterForm() { // register form is public because of its use in
							// LoginFormn

		freg.setSize(300, 300);
		freg.setLocation(965, 365);
		freg.setTitle("Member Registration");
		lblnamereg = new JLabel("User Name:");
		lblpasswordreg = new JLabel("Password:");
		lblfirstnamereg = new JLabel("First Name:");
		lblsecondnamereg = new JLabel("Scond Name:");
		lblmessreg = new JLabel("");
		btsubmit = new JButton("Submit");
		btsubmit.addActionListener(this); // event handler assigned to submit
											// button

		txtnamereg = new JTextField(20);
		txtpasswordreg = new JPasswordField(20);
		txtpasswordreg.addKeyListener(new KeyList()); // listener interface for
														// receiving keyboard
														// events, also creates
														// a new KeyList object
														// to be used in the the
														// checkStrngth method
		txtfirstnamereg = new JTextField(20);
		txtsecondnamereg = new JTextField(20);

		panelreg = new JPanel();
		panelreg.add(lblnamereg);
		panelreg.add(txtnamereg);
		panelreg.add(lblpasswordreg);
		panelreg.add(txtpasswordreg);
		panelreg.add(lblfirstnamereg);
		panelreg.add(txtfirstnamereg);
		panelreg.add(lblsecondnamereg);
		panelreg.add(txtsecondnamereg);
		panelreg.add(btsubmit);
		panelreg.add(lblmessreg);
		freg.add(panelreg);
		freg.setVisible(true);
		
		txtnamereg.requestFocus();

	}

	public void actionPerformed(ActionEvent e) { // event listner to handle what
													// is done when the submit
													// button is pressed

		if (e.getSource() == btsubmit) {         // action to perform if submit is clicked
			try {

				String uname = txtnamereg.getText();
				String fname = txtfirstnamereg.getText();
				String sname = txtsecondnamereg.getText();
				String passw = new String(txtpasswordreg.getPassword());
				
				
				usernameLength(uname, 2);
				
				inputType(fname);
				toTitleCase(fname);
				usernameLength(fname, 2);
				firstLetterCaps(fname);
				
				
				inputType(sname);
				toTitleCase(sname);
				usernameLength(sname, 2);
				firstLetterCaps(sname);

				
				passLength(passw, 6);

				if (!checkBlank(uname, fname, sname, passw, lblnamereg, // first
																		// make
																		// sure
																		// textfields
																		// are
																		// not
																		// blank
						lblfirstnamereg, lblsecondnamereg, lblpasswordreg)) {
					if (checkExist(uname) == false) { // then make sure the user
														// name does not already
														// exist, to avoid
														// duplicates

						Customer Customer = new Customer(fname, sname, // if
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
						CustomerList.put(uname, Customer); // pass customer
													// object to array
						JOptionPane.showMessageDialog(null, "Account Registered");
						LoginForm.hashmapHandlerCust.writeOut(CustomerList, myFile);// save to file
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
				JOptionPane.showMessageDialog(null, "Error in writing to file!");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Read instructions clearer");
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

	private boolean checkBlank(String name, String passw,
			String fname, // method to check for blank fields
			String sname, JLabel namemess, JLabel passwmess, JLabel fnamemess,
			JLabel snamemess) {
		boolean hasBlank = false;
		if (name.length() < 1) {
			showMess("User name is required.", namemess);
			hasBlank = true;
		}
		if (fname.length() < 1) {
			showMess("Fist name is required.", namemess);
			hasBlank = true;
		}
		if (sname.length() < 1) {
			showMess("Second name is required.", namemess);
			hasBlank = true;
		}
		if (passw.length() < 1) {
			showMess("Password is required.", passwmess);
			hasBlank = true;
		}
		return hasBlank;

	}

	private static void showMess(String mess, JLabel lbl) { // show message
															// method
		lbl.setText(mess);
		lbl.setForeground(Color.RED);
	}

	private static String checkStrength(String passw) { // check strength method

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

	public static boolean checkExist(String custuName) { // method to check if the
													// username has already been
													// used

		boolean exist = false;
		try {

			if (getCustomer(custuName) != null) {
				showMess("The account already exists.", lblmessreg);
				exist = true;

			}

		} catch (Exception ie) {
			JOptionPane.showMessageDialog(null, "Error!");
		}
		return exist;
	}

	public static boolean search(String custuName) { // method used to search
														// the
														// array for the
														// username
		boolean blnFound = CustomerList.containsKey(custuName);
		return blnFound;
	}

	public static Customer getCustomer(String custuName) { // method to return
															// the index of the
															// object once its
															// found, or null,
															// to indicate it
															// hasn't, once
															// again expects an
															//index or null		
		boolean exist = search(custuName);
		if (exist == false) {
			return null;
		} else {
			return CustomerList.get(custuName);
		}
	}


	public static boolean checkFile() { // method to check if the accounts.dat
										// file exists in RegisterForm

		if (myFile.exists()) {
			return true;

		} else
			return false;

	}

}
