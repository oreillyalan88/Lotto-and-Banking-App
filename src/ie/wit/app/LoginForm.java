package ie.wit.app;

import ie.wit.customer.Customer;
import ie.wit.io.FileHandler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;




import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginForm extends JFrame implements ActionListener {

	
	
	public static FileHandler<HashMap<String, Customer>> hashmapHandlerCust			// hashmapHandlerCust will allow any HashMap<String, Customer> to be used with these methods but could just as easily be
																					// ArrayList<Customer> if it was paramatized that way
	= new FileHandler<HashMap<String, Customer>>();
	 
	private static final long serialVersionUID = 1L;
	public static Customer temp; // creates static Customer object of type temp
									// to be used throughout the program
	
	JFrame flog = new JFrame(); // creates JFrame
	JPanel panel;
	JLabel lblname;
	JLabel lblpassword;
	JLabel lblmess;
	JTextField txtname;
	JPasswordField txtpassword;
	JButton btlogin;
	JButton btregister;

	LoginForm() { // starts loginform

		flog.setSize(300, 200); // sets size of Jframe
		flog.setLocation(665, 365); // sets location
		flog.setTitle("Member Login"); // assigns title
		lblname = new JLabel("User Name:");
		lblpassword = new JLabel("Password:"); // creates label
		lblmess = new JLabel(""); // creates unseen label for error message to
									// occupy when needed
		btlogin = new JButton("Login");
		btlogin.addActionListener(this); // creates actionlistner event handler
											// and assigns it to login button
		btregister = new JButton("Register");
		btregister.addActionListener(this);
		
		
		
		txtname = new JTextField(25);
		txtpassword = new JPasswordField(25); // creates password field

		panel = new JPanel(); // creates jpanel
		panel.add(lblname);
		panel.add(txtname);
		panel.add(lblpassword);
		panel.add(txtpassword);
		panel.add(btlogin);
		panel.add(btregister);
		panel.add(lblmess);
		flog.add(panel); // adds Jpanel to Jframe
		flog.setVisible(true); // makes it visable
		txtname.requestFocus(); // focus curse on txtname field

	}

	public void actionPerformed(ActionEvent e) { // method to handle users
													// interactions with buttons
													// on JPanel

		if (RegisterForm.checkFile() == true) { // checks to see if the binary
												// file exists
			try {
				RegisterForm.CustomerList =hashmapHandlerCust.readIn(RegisterForm.myFile);  // if it does
																	// pass any
																	// previous
																	// information
																	// into the
																	// array
																	// CustomerList
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null,
						"Error Reading From File.");
			}
		}
		if (e.getSource() == btlogin) { // actions to undertake if the login
										// button is pressed

		            
			String uname = txtname.getText();
			String passw = new String(txtpassword.getPassword());
			if (!checkBlank(uname, passw, lblname, lblpassword))   // check if jTextFields are blank
				validateUser(uname, passw);  // check if the uname key & passw exists in the HashMap

		}

		else if (e.getSource() == btregister) { // if the register button is
												// selected, create a new
												// RegisterForm and present it
												// to the user

			new RegisterForm();

		}

	}

	private boolean checkBlank(String name, String passw, JLabel namemess, // validate
																			// against
																			// the
																			// user
																			// leaving
																			// the
																			// text
																			// fields
																			// blank
			JLabel passwmess) {
		boolean hasBlank = false;
		if (name.length() < 1) { // if string length < 1, show message
			showMess("User name is required.", namemess);	
			hasBlank = true;
		}
		if (passw.length() < 1) {
			showMess("Password is required.", passwmess);
			hasBlank = true;
		}
		return hasBlank;

	}

	private void showMess(String mess, JLabel lbl) { // error message to enter
														// in label passed into
														// mess and the label
														// used to display
														// message passed into
														// lbl
		lbl.setText(mess);
		lbl.setForeground(Color.RED); // sets the message color to red
	}

	private void reset(JLabel lblname, JLabel lblpassw) { // method to reset the
															// labels after the
															// show mess changes
															// the JLabel values
		lblname.setText("User Name:");
		lblname.setForeground(Color.BLACK);
		lblpassw.setText("Password:");
		lblpassw.setForeground(Color.BLACK);
	}

	private void validateUser(String name, String password) { // method to
																// validate the
																// users input

		boolean valid = false;

		try {

			if (getCustomer(name, password) != null) { // if the name and
														// password entered
														// exists in the array,
														// the static object
														// temp, of type
														// Customer
				temp = getCustomer(name, password); // gets assigned the values
													// to use through the rest
													// of the program
				JOptionPane.showMessageDialog(null,
						"You have succesfully logged in.");
				valid = true;
				flog.setVisible(false); // hide the JFrame

				LoginProgram.mainMenu(); // load the lotto app

			}

			if (!valid || RegisterForm.myFile == null) // if input does not
														// exist, or the file is
														// empty, you need to
														// register an account
														// before loging in
				showMess("Invalid login!", lblmess); // calls showMess method
			reset(lblname, lblpassword); // calls reset method

		} catch (Exception ie) {
			JOptionPane.showMessageDialog(null,
					"You need to register an account!");
		}

	}


	
	public static boolean search(String custuName) {//method to search keys of hashmap
		
		boolean blnFound = RegisterForm.CustomerList.containsKey(custuName);//returns false if key not found,or true if it is 
		return blnFound;
		
}
	
	
	public static Customer getCustomer(String custuName, String custPass) { 
		boolean exist = search(custuName);  // exists is t/f depending on return of search
		Customer tempCust = RegisterForm.CustomerList.get(custuName);  // temp Customer object to store returned hashmap
		exist = tempCust.getPassword().equals(custPass);  // second t/f check on password. if the password passed exists in temp Customer
														 // return t, else return f
		
		if (exist == false) {								// null if false
			return null;
		} else {
		return RegisterForm.CustomerList.get(custuName); // return the Customer object if both checks stay true. ie user name password exist
			
																			

	}
	}
}
