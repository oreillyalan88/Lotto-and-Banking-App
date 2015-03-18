package ie.wit.bankaccount;

import ie.wit.app.LoginProgram;
import ie.wit.customer.Customer;
import ie.wit.io.FileHandler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class BankLoginForm extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Bank temp; // creates static Bank object of type temp
								// to be used throughout the program

	JFrame flog = new JFrame(); // creates JFrame
	JPanel panel;
	JLabel lblaccNo;
	JLabel lblpassword;
	JLabel lblmess;
	JTextField txtaccNo;
	JPasswordField txtpassword;
	JButton btlogin;
	JButton btregister;

	public BankLoginForm() { // starts bankloginform
		flog.setBackground(Color.DARK_GRAY);
		
		flog.setSize(300, 200); // sets size of Jframe
		flog.setLocation(665, 365); // sets location
		flog.setTitle("Bank Account Login"); // assigns title
		lblaccNo = new JLabel("Account Number:");
		lblpassword = new JLabel("Password:"); // creates label
		lblmess = new JLabel(""); // creates unseen label for error message to
									// occupy when needed
		btlogin = new JButton("Login");
		btlogin.addActionListener(this); // creates actionlistner event handler
											// and assigns it to login button
		btregister = new JButton("Register");
		btregister.addActionListener(this);

		txtaccNo = new JTextField(25);
		txtpassword = new JPasswordField(25); // creates password field

		panel = new JPanel(); // creates jpanel
		panel.add(lblaccNo);
		panel.add(txtaccNo);
		panel.add(lblpassword);
		panel.add(txtpassword);
		panel.add(btlogin);
		panel.add(btregister);
		panel.add(lblmess);
		flog.add(panel); // adds Jpanel to Jframe
		flog.setVisible(true); // makes it visable
		txtaccNo.requestFocus(); // focus curse on txtname field

	}

	public void actionPerformed(ActionEvent e) { // method to handle users
													// interactions with buttons
													// on JPanel

		if (BankRegisterForm.checkFile(BankRegisterForm.myFileBank) == true) { // checks
																				// to
																				// see
																				// if
																				// the
																				// binary
			// file exists
			try {
				BankRegisterForm.AccountList = BankRegisterForm.hashmapHandlerBank
						.readIn(BankRegisterForm.myFileBank); // if it does
				// pass any

				// previous
				// information
				// into the
				// array
				// CustomerList

			} catch (ClassNotFoundException | IOException e1) {

				JOptionPane.showMessageDialog(null,
						"External File Not Found.");
			}
		}
		if (e.getSource() == btlogin) { // actions to undertake if the login
										// button is pressed

			


			try {
				Integer accNo = Integer.parseInt(txtaccNo.getText());
				String passw = new String(txtpassword.getPassword());

				if (!checkBlank(passw, lblaccNo, lblpassword))
					validateUser(accNo, passw);
			} catch (NumberFormatException a) {
				JOptionPane.showMessageDialog(null,
						"You must enter the Account Number.");

			}
		}

		else if (e.getSource() == btregister) { // if the register button is
												// selected, create a new
												// RegisterForm and present it
												// to the user

			new BankRegisterForm();

		}

	}

	private boolean checkBlank(String passw, JLabel namemess, // validate
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

	private void validateUser(Integer accNo, String password) { // method to
																// validate the
																// users input

		boolean valid = false;

		try {

			if (getCustomer(accNo, password) != null) { // if the name and
														// password entered
														// exists in the array,
														// the static object
														// temp, of type
														// Customer
				temp = getCustomer(accNo, password); // gets assigned the values
														// to use through the
														// rest
														// of the program
				JOptionPane.showMessageDialog(null,
						"You have succesfully logged in.");

				valid = true;
				flog.setVisible(false); // hide the JFrame
				BankLoginProgram.mainMenu();
				/* BankLoginProgram.mainMenu(); */// load the lotto app

			}

			if (!valid || BankRegisterForm.myFileBank == null) // if input does
																// not
				// exist, or the file is
				// empty, you need to
				// register an account
				// before loging in
				showMess("Invalid login!", lblmess); // calls showMess method
			reset(lblaccNo, lblpassword); // calls reset method

		} catch (Exception ie) {

			JOptionPane.showMessageDialog(null,
					"Inout Error, Please Try Again!.");

			/*
			 * JOptionPane.showMessageDialog(null,
			 * "You need to register an account!");
			 */
		}

	}

	public static boolean search(Integer accNo, String custPass) {             

		boolean blnFound = BankRegisterForm.AccountList.containsKey(accNo);
		return blnFound;

	}

	public static Bank getCustomer(Integer accNo, String custPass) { 
		boolean exist = search(accNo, custPass);
		Bank tempCust = BankRegisterForm.AccountList.get(accNo);
		exist = tempCust.getPassw().equals(custPass);

		
		if (exist == false) {
			return null;
		} else {
			return BankRegisterForm.AccountList.get(accNo);

			

		}
	}
}
