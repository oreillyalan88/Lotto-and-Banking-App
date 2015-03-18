package ie.wit.bankaccount;

import ie.wit.exceptions.InputBoundariesException;
import ie.wit.exceptions.InvalidWithdrawException;
import ie.wit.exceptions.NegativeDepositException;
import ie.wit.exceptions.PasswLength;
import ie.wit.exceptions.StringLength;
import ie.wit.sort.MapSort;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class BankManager {

	// /////////////////////////////////////////////////////////////
	private static int optionLimitations(int input, int min, int max) // custom
																		// exception
																		// method
																		// to
																		// validate
																		// user
																		// input
			throws InputBoundariesException {
		if (input < min || input > max)
			throw new InputBoundariesException();

		return input;
	}


	
	

	private static String usernameLength(String username, int min) // custom
																	// exception
																	// for
																	// username
			throws StringLength {
		if (username.length() < min)
			throw new StringLength();

		return username;
	}

	private static String passLength(String passw, int min) throws PasswLength { // custome
																					// exception
																					// for
																					// password
																					// length
		if (passw.length() < min)
			throw new PasswLength();

		return passw;
	}
	
	private static double depositAmount(double deposit, int min)  { 
		if (deposit < min)
			throw new NegativeDepositException();

		return deposit;
	}

	// ///////////////////////////////////////////////////////////////////////////////////

	



	private Icon myIcon = new ImageIcon(getClass().getResource("..\\Register\\img\\bank.jpg"));
	private static Icon anIcon = new ImageIcon();

	// /////////////////////////////////////////////////////////////
	public int menuMain() { 

		
		int option = 0;

		String opt1 = new String("1. Delete Bank Account:");
		String opt2 = new String("2. Change Address :");
		String opt3 = new String("3. Deposit to account :");
		String opt4 = new String("4. Withdraw from account:");
		String opt5 = new String("5. Sort Accoutns :\n");
		String opt6 = new String("6. Exit\n");
		String opt7 = new String("\n");
		String msg = new String("Enter Option:");
		JTextField opt = new JTextField(""); 

		Object message[] = new Object[10];

		message[0] = myIcon;
		message[1] = opt1;
		message[2] = opt2;
		message[3] = opt3;
		message[4] = opt4;
		message[5] = opt5;
		message[6] = opt6;
		message[7] = opt7;
		message[8] = msg;
		message[9] = opt;

		opt.requestFocusInWindow();
		int response = JOptionPane.showConfirmDialog(null, message, "Bank Account    User: "+BankLoginForm.temp.getfName()+"    Balance: "+BankLoginForm.temp.getBalance(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				anIcon);

		if (response == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		} else {

			try {

				option = Integer.parseInt(opt.getText());

				optionLimitations(option, 1, 6);

			}

			catch (InputBoundariesException a) {
				JOptionPane.showMessageDialog(null,
						" Input must be between 1 and 6 , Please Try Again.", "Input Error",
						JOptionPane.ERROR_MESSAGE);
				menuMain();
			}

			catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Input must be a number. "
						+ "Please Try Again.", "Input Error",
						JOptionPane.ERROR_MESSAGE);
				menuMain();
			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Input Error"
						+ "Please Try Again.", "Input Error",
						JOptionPane.ERROR_MESSAGE);
				menuMain();
			}

		}

		return option;
	}

	public void removeAccount() // remove account method used to delete accounts

	{
		String msgConfirm = new String(
				"Please confirm your details to delete this account");
		String space = new String("\n");
		String msgAccno = new String("Enter your Account Number :");
		String msgPass = new String("Enter your Password :");

		JTextField accNo = new JTextField("");
		JPasswordField Pass = new JPasswordField("");

		Object message[] = new Object[7];

		message[0] = myIcon;
		message[1] = msgConfirm;
		message[2] = space;
		message[3] = msgAccno;
		message[4] = accNo;
		message[5] = msgPass;
		message[6] = Pass;

		int response = JOptionPane.showConfirmDialog(null, message,
				"Delete Your Account     User: "+BankLoginForm.temp.getfName()+"    Balance: "+BankLoginForm.temp.getBalance(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, anIcon);

		if (response == JOptionPane.CANCEL_OPTION)
			;

		else {
			try {
				
				Integer tempacNo;
				String tempPass;

				tempacNo = Integer.parseInt(accNo.getText());
				tempPass = new String(Pass.getPassword());
				passLength(tempPass, 6);
				if (BankLoginForm.getCustomer(tempacNo, tempPass) != null) //checks account exists
					
						
				{	

				remove(tempacNo); // if it does, remove using key
				
				
					// write out updated hash map to bank accounts file
					BankRegisterForm.hashmapHandlerBank.writeOut(BankRegisterForm.AccountList, BankRegisterForm.myFileBank);  
					JOptionPane
							.showMessageDialog(
									null,
									"Your account with the Account Number "
											+ tempacNo
											+ " has been removed and the app will now close, Good Bye");
					System.exit(0);

				}

				else { // if the boolean returned false the account does not
						// exist or password was wrong
					JOptionPane
							.showMessageDialog(
									null,
									"An account with the Account Number "
											+ tempacNo
											+ " does not exist, or your Password is incorrect, please try again.");
					removeAccount();

				}

			}catch (NumberFormatException a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Account Number Must Be A Number", JOptionPane.ERROR_MESSAGE);

			
				removeAccount();
			

			} catch (PasswLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				removeAccount();
			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Data Input Error" + e
						+ "\nPlease Try Again");
				removeAccount();
			}

		}

	}

	// /////////////////////////////////////////////////////////////
	public void changeAddress() // change username method

	{
		String msgConfirm = new String(
				"Please confirm your details to change this accounts Username");
		String space = new String("\n");
		String msgaccNo = new String("Enter your Account Number :");
		String msgPass = new String("Enter your Password :");
		String msgAddress = new String("Enter your new Address :");

		JTextField accNo = new JTextField("");
		JPasswordField Pass = new JPasswordField("");
		JTextField address = new JTextField("");

		Object message[] = new Object[10];

		message[0] = myIcon;
		message[1] = msgConfirm;
		message[2] = space;
		message[3] = msgaccNo;
		message[4] = accNo;
		message[5] = msgPass;
		message[6] = Pass;
		message[7] = msgAddress;
		message[8] = address;

		int response = JOptionPane.showConfirmDialog(null, message,
				"Change Account Address     User: "+BankLoginForm.temp.getfName()+"    Balance: "+BankLoginForm.temp.getBalance(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, anIcon);

		if (response == JOptionPane.CANCEL_OPTION)
			;

		else {
			try {

				Integer tempaccNo;
				String tempPass;
				String tempAddress;

				tempaccNo = Integer.parseInt(accNo.getText());
				tempPass = new String(Pass.getPassword());
				tempAddress = address.getText();

				
				passLength(tempPass, 6);
				usernameLength(tempAddress, 2);

				if (BankLoginForm.getCustomer(tempaccNo, tempPass) != null) {
					
					String oldAddress = BankLoginForm.temp.getAddress(); 
					Bank obj = BankRegisterForm.AccountList  // .remove returns values of hashmap, store them in Bank obj
							.remove(tempaccNo);
					
					obj.setAddress(tempAddress); // set the address of the temp Bank obj to new address
					
					BankRegisterForm.AccountList.put(tempaccNo, obj);  // store new bj with updated address ,and old key   


					// save HashMap to file
					BankRegisterForm.hashmapHandlerBank.writeOut(BankRegisterForm.AccountList, BankRegisterForm.myFileBank);
					JOptionPane.showMessageDialog(null,
							"Your account with the pervious Address "
									+ oldAddress + " has been changed to " // show
																			// a
																			// pop
																			// up
																			// confirming
																			// this
									+ tempAddress + ".");

				}

				else {
					JOptionPane.showMessageDialog(null,
							"Your account does not exists");

				}

			} catch (NumberFormatException a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Account Number Must Be A Number", JOptionPane.ERROR_MESSAGE);
				changeAddress();
			
			} catch (StringLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				changeAddress();
			} catch (PasswLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				changeAddress();
			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Data Input Error" + e
						+ "\nPlease Try Again");
				changeAddress();
			}
		}
	}
	


	// /////////////////////////////////////////////////////////////

	
	////////////////////////////////////////////////////////////////////////////////////
	public void depositFunds() {
		
		String msgConfirm = new String(
				"How much would you like to deposit? ");
		
		String space = new String("\n");

		String msgAccountNumber = new String("Enter Account Number  :");

		JTextField Acc = new JTextField("");
		
		String msgAccountPass = new String("Enter Account Password  :");

		JPasswordField Pass = new JPasswordField("");
		
		String msgDepositAmount = new String("Enter Deposit Amount :");

		JTextField deposit = new JTextField("");
		

		Object message[] = new Object[9];

		message[0] = myIcon;
		message[1] = msgConfirm;
		message[2] = space;
		message[3] = msgAccountNumber;
		message[4] = Acc;
		message[5] = msgAccountPass;
		message[6] = Pass;
		message[7] = msgDepositAmount;
		message[8] = deposit;
		

		int response = JOptionPane.showConfirmDialog(null, message,
				"Deposit to Account     User: "+BankLoginForm.temp.getfName()+"    Balance: "+BankLoginForm.temp.getBalance(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, anIcon);

		if (response == JOptionPane.CANCEL_OPTION)
			;

		else {
			try {
				
				Integer tempaccNo;
				String tempPass;
				double tempDeposit;

				

				tempaccNo = Integer.parseInt(Acc.getText());
				tempPass = new String(Pass.getPassword());
				tempDeposit = Double.parseDouble(deposit.getText());



				passLength(tempPass, 6);
				depositAmount(tempDeposit, 1);

				if (BankLoginForm.getCustomer(tempaccNo, tempPass) != null
						) {

					BankLoginForm.temp.deposit(tempDeposit); // deposit entered amount
					
					BankRegisterForm.hashmapHandlerBank.writeOut(BankRegisterForm.AccountList,
							BankRegisterForm.myFileBank); //save updated hashmap to file
					JOptionPane.showMessageDialog(null,
							"Your have deposited "+tempDeposit +" to the account "+ tempaccNo +".");

				}

				else {
					JOptionPane.showMessageDialog(null,
							"Your account does not exists");

				}

			}  catch (NumberFormatException a) {

				JOptionPane.showMessageDialog(null,"Please Enter Your Accoun Number And/Or A Positive Deposit Amount");
				depositFunds();
			}
			catch (PasswLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				depositFunds();
			}
			
			catch (NegativeDepositException a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				depositFunds();
			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Data Input Error");
				depositFunds();
			}
		}
	}
	

	// /////////////////////////////////////////////////////////////

	public void withdrawFunds() {
		
		String msgConfirm = new String(
				"How much would you like to withdaw? ");
		
		String space = new String("\n");

		String msgAccountNumber = new String("Enter Account Number  :");

		JTextField Acc = new JTextField("");
		
		String msgAccountPass = new String("Enter Account Password  :");

		JPasswordField Pass = new JPasswordField("");
		
		String msgWithdrawAmount = new String("Enter Withdraw Amount :");

		JTextField withdraw = new JTextField("");
		

		Object message[] = new Object[9];

		message[0] = myIcon;
		message[1] = msgConfirm;
		message[2] = space;
		message[3] = msgAccountNumber;
		message[4] = Acc;
		message[5] = msgAccountPass;
		message[6] = Pass;
		message[7] = msgWithdrawAmount;
		message[8] = withdraw;
		

		int response = JOptionPane.showConfirmDialog(null, message,
				"Withdraw from Account   User: "+BankLoginForm.temp.getfName()+"  Balance: "+BankLoginForm.temp.getBalance(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, anIcon);

		if (response == JOptionPane.CANCEL_OPTION)
			;

		else {
			try {
				
				Integer tempaccNo;
				String tempPass;
				double tempWithdraw;

				

				tempaccNo = Integer.parseInt(Acc.getText());
				tempPass = new String(Pass.getPassword());
				tempWithdraw = Double.parseDouble(withdraw.getText());



				passLength(tempPass, 6);
				depositAmount(tempWithdraw, 1);
				
				if (BankLoginForm.getCustomer(tempaccNo, tempPass) != null
						) {

					BankLoginForm.temp.withdraw(tempWithdraw);  // withdraw entered amount from account
					
					BankRegisterForm.hashmapHandlerBank.writeOut(BankRegisterForm.AccountList,
							BankRegisterForm.myFileBank); // save updated file
					JOptionPane.showMessageDialog(null,
							"Your have deposited "+tempWithdraw +" to the account "+ tempaccNo +".");

				}

				else {
					JOptionPane.showMessageDialog(null,
							"Your account does not exists");

				}

			}  catch (NumberFormatException a) {

				JOptionPane.showMessageDialog(null,"Please Enter Your Accoun Number And/Or A Positive Deposit Amount");
				withdrawFunds();
			}
			catch (PasswLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				withdrawFunds();
			}
			
			catch (NegativeDepositException a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				withdrawFunds();
			}
			 catch (InvalidWithdrawException a) {

					JOptionPane.showMessageDialog(null,a.getMessage());
					withdrawFunds();
			 }
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Data Input Error");
				withdrawFunds();
			}
		}
	}
	
	
	

	// /////////////////////////////////////////////////////////////
public void listAccounts() {
		
		String msgConfirm = new String(
				"How much would you like to withdaw? ");
		
		String space = new String("\n");

		JRadioButton balanceSort = new JRadioButton("Sort Accounts by Balance:" );  // radio buttons
		

		JRadioButton accountSort = new JRadioButton("Sort Accounts by Account Number");

		
		Object message[] = new Object[7];

		message[0] = myIcon;
		message[1] = msgConfirm;
		message[2] = space;
		message[3] = balanceSort;
		message[4] = accountSort;


		

		int response = JOptionPane.showConfirmDialog(null, message,
				"Withdraw from Account   User: "+BankLoginForm.temp.getfName()+"  Balance: "+BankLoginForm.temp.getBalance(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, anIcon);

		if (response == JOptionPane.CANCEL_OPTION)
			;

		else {
			try {
				
				if(balanceSort.isSelected()){ // if this radio button is selected
					
					JOptionPane.showMessageDialog(null,
							""+ MapSort.sortByValue(BankRegisterForm.AccountList)); // sort values of hashmap
				}
	         else if (accountSort.isSelected()){
	        	 
					
					JOptionPane.showMessageDialog(null,
							""+ MapSort.sortByKey(BankRegisterForm.AccountList)); //sort hashmap buy key(Account No)
				
				
	         }
				else {
					JOptionPane.showMessageDialog(null,
							"Your account does not exists");

				}

			}  catch (NumberFormatException a) {

				JOptionPane.showMessageDialog(null,"Please Enter Your Accoun Number And/Or A Positive Deposit Amount");
				withdrawFunds();
			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Data Input Error");
				withdrawFunds();
			}
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void remove(Integer tempacNo) { // remove method
			BankRegisterForm.AccountList.remove(tempacNo);
			
	}







}
