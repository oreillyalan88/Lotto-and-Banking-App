package ie.wit.lotto;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ie.wit.app.LoginForm;
import ie.wit.app.RegisterForm;
import ie.wit.bankaccount.Bank;
import ie.wit.bankaccount.BankLoginForm;
import ie.wit.customer.Customer;
import ie.wit.exceptions.InputBoundariesException;
import ie.wit.exceptions.InvalidWithdrawException;
import ie.wit.exceptions.NegativeBalanceException;
import ie.wit.exceptions.NegativeDepositException;
import ie.wit.exceptions.PasswLength;
import ie.wit.exceptions.StringLength;
import ie.wit.io.FileHandler;

public class LottoManager {

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

	private static double negativeBalance(double input, double balance)	// custom exception to ensure the remaining balance after any deposit is not negative
			throws NegativeBalanceException {

		if (input > balance)
			throw new NegativeBalanceException();

		return input;
	}

	private static int optionLimitationsMin(int input, int min)
			throws InputBoundariesException {
		if (input < min)
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

	private static double depositAmount(double deposit, int min) { // custom exception to ensure deposited amount is not less than 1
		if (deposit < min)
			throw new NegativeDepositException();

		return deposit;
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	FileHandler<Integer> readJackPot = new FileHandler<Integer>();			// Initialisation of generic FileHandler class for 	<Integer>, allows  the JackPot to be read and wrote (or any Integer)

	public static File myJackPot = new File("jackPot.dat");					// file to save rolling jackPot too
	static Integer jackPot;													// jackPot will be stored here
	static double winnings;													// lotto winnings are stored here
	StartLotto Lotto = new StartLotto();
	BuyOwnLotteryTicket ownTicket = new BuyOwnLotteryTicket();
	BuyQuickPickLotteryTicket ownQPTicket = new BuyQuickPickLotteryTicket();
	ArrayList<ArrayList<Integer>> checkNums = new ArrayList<ArrayList<Integer>>( // ArrayList
																					// of
																					// ArrayLists
																					// to
																					// hold
																					// all
																					// quick
																					// pick
																					// and
																					// chosen
																					// numbers
			ownQPTicket.Games);
	static HashMap<Integer, Bank> temp = new HashMap<Integer, Bank>();	// temp HashMap to load in bank accounts
	static FileHandler<HashMap<Integer, Bank>> tempBankHashMapHandler = new FileHandler<HashMap<Integer, Bank>>();		// Initialisation of generic FileHandler class for 	HashMap<Integer, Bank>, allows to be read in/ wrote out basically

	static File tempFile = new File("Bankaccounts.dat");										// access to bank accounts to link lotto accounts and bank accounts together
	private Icon myIcon = new ImageIcon(getClass().getResource("..\\Register\\img\\lotto.jpg"));
	private static Icon anIcon = new ImageIcon();

	// /////////////////////////////////////////////////////////////
	public int menuMain() { // main menu method, called from LottoApp class

		if (myJackPot.exists()) {
			try {
				jackPot = readJackPot.readIn(myJackPot);								// checks for jackPot file to load into jackPot 
			} catch (ClassNotFoundException | IOException e1) {

								JOptionPane.showMessageDialog(null,
						" Error" + e1.getMessage()+
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			jackPot = 250;																	// otherwise jackpot starts at 250 (if the file is deleted or this is the first ever load of the app)S
		}

		int option = 0;

		String opt1 = new String("1. Delete Account:");
		String opt2 = new String("2. Change User Name :");
		String opt3 = new String("3. Buy a quick pick :");
		String opt4 = new String("4. Buy a ticket using own numbers  :");
		String opt5 = new String("5. Start the draw :");
		String opt6 = new String("6. Add funds to account from bank :\n");
		String opt7 = new String("7. Withdraw funds to bank from account :\n");
		String opt8 = new String("8. Exit\n");
		String opt9 = new String("\n");
		String msg = new String("Enter Option:");
		JTextField opt = new JTextField("");

		Object message[] = new Object[12];

		message[0] = myIcon;
		message[1] = opt1;
		message[2] = opt2;
		message[3] = opt3;
		message[4] = opt4;
		message[5] = opt5;
		message[6] = opt6;
		message[7] = opt7;
		message[8] = opt8;
		message[9] = opt9;
		message[10] = msg;
		message[11] = opt;

		opt.requestFocusInWindow();
		int response = JOptionPane.showConfirmDialog(null, message,
				"Lotto App    User: " + LoginForm.temp.getUserFirstName()
						+ "    Balance: " + LoginForm.temp.getLottoBalance(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				anIcon);

		if (response == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		} else {

			try {

				option = Integer.parseInt(opt.getText());

				optionLimitations(option, 1, 8);

			}

			catch (InputBoundariesException a) {
				JOptionPane.showMessageDialog(null, a.getMessage()
						+ "Please Try Again.", "Input Error",
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
		String msgUname = new String("Enter your User Name :");
		String msgPass = new String("Enter your Password :");

		JTextField Uname = new JTextField("");
		JPasswordField Pass = new JPasswordField("");

		Object message[] = new Object[8];

		message[0] = myIcon;
		message[1] = msgConfirm;
		message[2] = space;
		message[3] = msgUname;
		message[4] = Uname;
		message[5] = msgPass;
		message[6] = Pass;

		int response = JOptionPane.showConfirmDialog(
				null,
				message,
				"Delete Your Account     User: "
						+ LoginForm.temp.getUserFirstName() + "    Balance: "
						+ LoginForm.temp.getLottoBalance(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				anIcon);

		if (response == JOptionPane.CANCEL_OPTION)
			;

		else {
			try {

				String tempuName;
				String tempPass;

				tempuName = Uname.getText();
				tempPass = new String(Pass.getPassword());
				usernameLength(tempuName, 2);
				passLength(tempPass, 6);
				if (LoginForm.getCustomer(tempuName, tempPass) != null)

				{

					remove(tempuName);

					LoginForm.hashmapHandlerCust.writeOut(
							RegisterForm.CustomerList, RegisterForm.myFile);
					JOptionPane
							.showMessageDialog(
									null,
									"Your account with the username "
											+ tempuName
											+ " has been removed and the app will now close, Good Bye");
					System.exit(0);

				}

				else { // if the boolean returned false the account does not
						// exist or password was wrong
					JOptionPane
							.showMessageDialog(
									null,
									"An account with the username "
											+ tempuName
											+ " does not exist, or your Password is incorrect, please try again.");
					removeAccount();

				}

			} catch (StringLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
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
	public void changeUsername() // change username method

	{
		String msgConfirm = new String(
				"Please confirm your details to change this accounts Username");
		String space = new String("\n");
		String msgUname = new String("Enter your User Name :");
		String msgPass = new String("Enter your Password :");
		String msgNuname = new String("Enter your new User Name :");

		JTextField Uname = new JTextField("");
		JPasswordField Pass = new JPasswordField("");
		JTextField Nuname = new JTextField("");

		Object message[] = new Object[10];

		message[0] = myIcon;
		message[1] = msgConfirm;
		message[2] = space;
		message[3] = msgUname;
		message[4] = Uname;
		message[5] = msgPass;
		message[6] = Pass;
		message[7] = msgNuname;
		message[8] = Nuname;

		int response = JOptionPane.showConfirmDialog(
				null,
				message,
				"Change User Name     User: "
						+ LoginForm.temp.getUserFirstName() + "    Balance: "
						+ LoginForm.temp.getLottoBalance(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				anIcon);

		if (response == JOptionPane.CANCEL_OPTION)
			;

		else {
			try {

				String tempuName;
				String tempPass;
				String tempNusername;

				tempuName = Uname.getText();
				tempPass = new String(Pass.getPassword());
				tempNusername = Nuname.getText();

				usernameLength(tempuName, 2);
				passLength(tempPass, 6);
				usernameLength(tempNusername, 2);

				if (LoginForm.getCustomer(tempuName, tempPass) != null
						&& RegisterForm.checkExist(tempNusername) == false) {

					Customer obj = RegisterForm.CustomerList.remove(tempuName);

					RegisterForm.CustomerList.put(tempNusername, obj);

					LoginForm.hashmapHandlerCust.writeOut(
							RegisterForm.CustomerList, RegisterForm.myFile);
					JOptionPane.showMessageDialog(null,
							"Your account with the pervious Username "
									+ tempuName + " has been changed to " // show
																			// a
																			// pop
																			// up
																			// confirming
																			// this
									+ tempNusername + ".");

				}

				else {
					JOptionPane.showMessageDialog(null,
							"Your account does not exists");

				}

			} catch (StringLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				changeUsername();
			} catch (PasswLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				changeUsername();
			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Data Input Error" + e
						+ "\nPlease Try Again");
				changeUsername();
			}
		}
	}

	// /////////////////////////////////////////////////////////////

	public void menuBuyAQuickTicket() { // method to enter the amount of quick
										// picks wanted, then load buyAQuickPick
										// method to see randomly generated 6
										// numbers

		String msgConfirm = new String(
				"How many quick pick tickets would you like to buy? (€ 1 each)");

		String space = new String("\n");
		String msgTicketAmount = new String("Enter ticket amount :");

		JTextField Ticket = new JTextField("");



		Object message[] = new Object[7];

		message[0] = myIcon;
		message[1] = msgConfirm;
		message[2] = space;
		message[3] = msgTicketAmount;
		message[4] = Ticket;

		int response = JOptionPane.showConfirmDialog(
				null,
				message,
				"Amount of tickets     User: "
						+ LoginForm.temp.getUserFirstName() + "    Balance: "
						+ LoginForm.temp.getLottoBalance(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				anIcon);

		if (response == JOptionPane.CANCEL_OPTION)
			;
		else {
			try {
				int tempTicketAmount = Integer.parseInt(Ticket.getText());
				optionLimitationsMin(tempTicketAmount, 1);
				if (LoginForm.temp.getLottoBalance() < (tempTicketAmount)) {		// tickets are €1 so depending we use the ticket amount to calc cost, and check against the available balance
					JOptionPane
							.showMessageDialog(null,
									"Not enough funds available"
											+ "\nPlease Try Again");
					menuBuyAQuickTicket();

				} else {
					buyAQuickPick(tempTicketAmount);									// pass amount into method to buy that amount

					LoginForm.temp.setLottoBalance(LoginForm.temp
							.getLottoBalance() - tempTicketAmount);						// already been validated against available balance so now we can deduct the price

					LoginForm.hashmapHandlerCust.writeOut(
							RegisterForm.CustomerList, RegisterForm.myFile);   // write out updated CustomerList
				}

			} catch (InputBoundariesException ibe) {
				JOptionPane.showMessageDialog(null, "Must Be A Positive Number"
						+ "\nPlease Try Again");
				menuBuyAQuickTicket();

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Must Be A Number"
						+ "\nPlease Try Again");
				menuBuyAQuickTicket();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Data Input Error" + e
						+ "\nPlease Try Again");
				menuBuyAQuickTicket();
			}
		}
	}

	// /////////////////////////////////////////////////////////////
	private void buyAQuickPick(int ticketAmount) {

		String ticketDetails = "";

		ownQPTicket.Generate(ticketAmount); // passes the inputed ticket amount
											// from menuBuyAQuickTicket and
											// generates that amount of lines
											// for your ticket from the
											// BuyQuickPickLotteryTicket class
		for (ArrayList<Integer> i : ownQPTicket.Games) {
			ticketDetails += i + "\n"; // adds each generated 6 numbers to
										// ticketDetails but uses "\n" to move
										// to a new line on each iteration of
										// the loop
		}

		JOptionPane.showMessageDialog(null, ticketDetails,
				"Your quick pic lotto numbers!", 1); // shows randomly generated
														// numbers to the user

	}

	// ///////////////////////////////////////////////////////////////
	public void inputLottoNums() { // method to allow the user to enter there
									// own numbers between 1-49

		String msgConfirm = new String("Please Enter numbers from 1 - 49 :");
		String space = new String("\n");
		String msgFirstNum = new String("Enter First Number :");
		String msgSecondNum = new String("Enter Second Number :");
		String msgThirdNum = new String("Enter Third Number :");
		String msgFourthNum = new String("Enter Fourth Number :");
		String msgFifthNum = new String("Enter Fifth Number :");
		String msgSixthNum = new String("Enter Sixth Number :");

		JTextField FirstNum = new JTextField("");
		JTextField SecondNum = new JTextField("");
		JTextField ThirdNum = new JTextField("");
		JTextField FourthNum = new JTextField("");
		JTextField FifthNum = new JTextField("");
		JTextField SixthNum = new JTextField("");

		Object message[] = new Object[15];

		message[0] = myIcon;
		message[1] = msgConfirm;
		message[2] = space;
		message[3] = msgFirstNum;
		message[4] = FirstNum;
		message[5] = msgSecondNum;
		message[6] = SecondNum;
		message[7] = msgThirdNum;
		message[8] = ThirdNum;
		message[9] = msgFourthNum;
		message[10] = FourthNum;
		message[11] = msgFifthNum;
		message[12] = FifthNum;
		message[13] = msgSixthNum;
		message[14] = SixthNum;

		int response = JOptionPane.showConfirmDialog(null, message,
				"Enter Your Lotto Numbers for ticket      User: "
						+ LoginForm.temp.getUserFirstName() + "    Balance: "
						+ LoginForm.temp.getLottoBalance(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				anIcon);

		if (response == JOptionPane.CANCEL_OPTION)
			;

		else {
			try {

				if (LoginForm.temp.getLottoBalance() > 1) {										// if the balance is less that one you cant play, as tickets cost €1

					int tempNum1 = Integer.parseInt(FirstNum.getText());
					int tempNum2 = Integer.parseInt(SecondNum.getText());
					int tempNum3 = Integer.parseInt(ThirdNum.getText());
					int tempNum4 = Integer.parseInt(FourthNum.getText());
					int tempNum5 = Integer.parseInt(FifthNum.getText());
					int tempNum6 = Integer.parseInt(SixthNum.getText());
					optionLimitations(tempNum1, 1, 49);
					optionLimitations(tempNum2, 1, 49);
					optionLimitations(tempNum3, 1, 49);
					optionLimitations(tempNum4, 1, 49);
					optionLimitations(tempNum5, 1, 49);
					optionLimitations(tempNum6, 1, 49);
					if (tempNum1 == tempNum2 || tempNum1 == tempNum3
							|| tempNum1 == tempNum4 || tempNum1 == tempNum5
							|| tempNum1 == tempNum6 || tempNum2 == tempNum1
							|| tempNum2 == tempNum3 || tempNum2 == tempNum4
							|| tempNum2 == tempNum5 || tempNum2 == tempNum6
							|| tempNum3 == tempNum1 || tempNum3 == tempNum2
							|| tempNum3 == tempNum4 || tempNum3 == tempNum5
							|| tempNum3 == tempNum6 || tempNum4 == tempNum1
							|| tempNum4 == tempNum2 || tempNum4 == tempNum3
							|| tempNum4 == tempNum5 || tempNum4 == tempNum6
							|| tempNum5 == tempNum1 || tempNum5 == tempNum2
							|| tempNum5 == tempNum3 || tempNum5 == tempNum4
							|| tempNum5 == tempNum6 || tempNum6 == tempNum1
							|| tempNum6 == tempNum2 || tempNum6 == tempNum3
							|| tempNum6 == tempNum4 || tempNum6 == tempNum5) { // method
																				// used
																				// to
																				// check
																				// for
																				// duplicates
						JOptionPane.showMessageDialog(null,
								"You can not have duplicate numbers! ");
						inputLottoNums();
					}

					else {
						getInput(tempNum1, ownTicket.SelectedNumbers); // call
																		// the
																		// getInput
																		// method
																		// to
																		// add
																		// the
																		// input
																		// to
																		// the
																		// HashSet
																		// SelectedNumbers
						getInput(tempNum2, ownTicket.SelectedNumbers); // HashSet
																		// was
																		// used
																		// to
																		// avoid
																		// duplicates
						getInput(tempNum3, ownTicket.SelectedNumbers);
						getInput(tempNum4, ownTicket.SelectedNumbers);
						getInput(tempNum5, ownTicket.SelectedNumbers);
						getInput(tempNum6, ownTicket.SelectedNumbers);

						ownTicket.Generate(); // creates a lotto ticket with the
												// entered numbers, using the
												// generate method from the
												// class
												// BuyOwnLottoTicket, as to keep
												// the
												// data structure for all
												// tickets,
												// quickpick or not
						String ticketDetails = "";
						for (ArrayList<Integer> x : ownTicket.Games) { // adds
																		// each
																		// generated
																		// 6
																		// numbers
																		// to
																		// ticketDetails
																		// but
																		// uses
																		// "\n"
																		// to
																		// move
																		// to a
																		// new
																		// line
																		// on
																		// each
																		// iteration
																		// of
																		// the
																		// loop
							ticketDetails += x + "\n";
						}
						LoginForm.temp.setLottoBalance(LoginForm.temp
								.getLottoBalance() - 1);					// deduct ticket price from balance
						LoginForm.hashmapHandlerCust.writeOut(
							RegisterForm.CustomerList, RegisterForm.myFile);
						JOptionPane.showMessageDialog(null, ticketDetails,
								"Your own lotto numbers!", 1);
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"You do not have enough funds "
									+ "\nPlease make a Deposit and Try Again");
				}

			} catch (InputBoundariesException e) {
				JOptionPane.showMessageDialog(null,
						"You must enter values between 1 and 49"
								+ "\nPlease Try Again");
				inputLottoNums();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Data Input Error" + e
						+ "\nPlease Try Again");
				inputLottoNums();
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////
	public void depositFunds() { // method to deposuit funds from bank account

		String msgConfirm = new String(
				"How much would you like to deposit from you BankAccount? ");

		String space = new String("\n");

		String msgAccountNumber = new String("Enter Bank Account Number  :");

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

		int response = JOptionPane.showConfirmDialog(
				null,
				message,
				"Deposit from Bank Account     User: "
						+ LoginForm.temp.getUserFirstName() + "    Balance: "
						+ LoginForm.temp.getLottoBalance(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				anIcon);

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

				if (getCustomer(tempaccNo, tempPass) != null) {				//check if BankAccount exists
					Bank obj = temp.remove(tempaccNo);						//if so remove the it, passing HashMap value to  obj

					obj.withdraw(tempDeposit);								// withdraw the specified amount from obj

					temp.put(tempaccNo, obj);								// add updated obj and previously deleted key to temp Bank HashMap
					LoginForm.temp.setLottoBalance(tempDeposit				// set the lotto balance to the previous balance + deposit amount
							+ LoginForm.temp.getLottoBalance());

					LoginForm.hashmapHandlerCust.writeOut(						// write updated lotto details out
							RegisterForm.CustomerList, RegisterForm.myFile);

					tempBankHashMapHandler.writeOut(temp, tempFile);				// write updated lotto details out, wont affect actual bank package (other than the way we inteded) because of the file handling

					JOptionPane.showMessageDialog(null, "Your have deposited "
							+ tempDeposit + " from the account " + tempaccNo
							+ " to the Lotto Account with the Name "
							+ LoginForm.temp.getUserFirstName() + " .");

				}

				else {
					JOptionPane.showMessageDialog(null,
							"Your account does not exists");

				}

			} catch (InvalidWithdrawException a) {

				JOptionPane.showMessageDialog(null,
						"You do not have enough funds in you bank account to make this transaction,"
								+ " your current account balance is "
								+ BankLoginForm.temp.getBalance());
				depositFunds();
			}

			catch (NumberFormatException a) {

				JOptionPane
						.showMessageDialog(null,
								"Please Enter Your Accoun Number And/Or A Positive Deposit Amount");
				depositFunds();
			} catch (PasswLength a) {

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

				e.printStackTrace();
				/* JOptionPane.showMessageDialog(null, "Data Input Error"); */
				depositFunds();
			}
		}
	}

	// /////////////////////////////////////////////////////////////

	public void withdrawFunds() {										// withdraw method handled same was as the deposit method, it just withdraws from lotto to bank instead of bank to lotto
	
		String msgConfirm = new String(
				"How much would you like to withdraw to your BankAccount? ");

		String space = new String("\n");

		String msgAccountNumber = new String("Enter Bank Account Number  :");

		JTextField Acc = new JTextField("");

		String msgAccountPass = new String("Enter Account Password  :");

		JPasswordField Pass = new JPasswordField("");

		String msgWithdrawAmount = new String("Enter Withdrawl Amount :");

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

		int response = JOptionPane.showConfirmDialog(
				null,
				message,
				"Withdraw to Bank Account     User: "
						+ LoginForm.temp.getUserFirstName() + "    Balance: "
						+ LoginForm.temp.getLottoBalance(),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				anIcon);

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
				negativeBalance(tempWithdraw, LoginForm.temp.getLottoBalance());

				if (getCustomer(tempaccNo, tempPass) != null) {
					Bank obj = temp.remove(tempaccNo);
					

					obj.deposit(tempWithdraw);

					temp.put(tempaccNo, obj);
					LoginForm.temp.setLottoBalance(LoginForm.temp
							.getLottoBalance() - tempWithdraw);

					LoginForm.hashmapHandlerCust.writeOut(
							RegisterForm.CustomerList, RegisterForm.myFile);

					tempBankHashMapHandler.writeOut(temp, tempFile);

					JOptionPane.showMessageDialog(null, "Your have withdrawn "
							+ tempWithdraw + " to the account " + tempaccNo
							+ " from the Lotto Account with the Name "
							+ LoginForm.temp.getUserFirstName() + " .");

				}

				else {
					JOptionPane.showMessageDialog(null,
							"Your account does not exists");

				}

			} catch (NegativeBalanceException a) {

				JOptionPane.showMessageDialog(null,
						"You do not have enough funds in your lotto account to make this transaction,"
								+ " your current account balance is "
								+ LoginForm.temp.getLottoBalance());
				withdrawFunds();
			}

			catch (NumberFormatException a) {

				JOptionPane
						.showMessageDialog(null,
								"Please Enter Your Accoun Number And/Or A Positive Deposit Amount");
				withdrawFunds();
			} catch (PasswLength a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				withdrawFunds();
			}

			catch (NegativeDepositException a) {

				JOptionPane.showMessageDialog(null, a.getMessage(),
						"Error Message", JOptionPane.ERROR_MESSAGE);
				withdrawFunds();
			}

			catch (Exception e) {

			
				JOptionPane.showMessageDialog(null, "Data Input Error");
				withdrawFunds();
			}
		}
	}

	// /////////////////////////////////////////////////////////////

	public void startLotto() { // method to start the lotto

		if (ownTicket.Games.isEmpty() && ownQPTicket.Games.isEmpty()) { // check
																		// to
																		// see
																		// if
																		// any
																		// tickets
																		// are
																		// bought
																		// by
																		// checking
																		// the
																		// Games
																		// ArrayLists
																		// of
																		// ArrayLists
																		// from
																		// the
																		// BuyOwnLotteryTicket
																		// and
																		// BuyQuickPickLotteryTicket
																		// if
																		// they
																		// are
																		// empty
																		// obviously
																		// no
																		// tickets
																		// have
																		// been
																		// bought
			JOptionPane
					.showMessageDialog(null,
							"You have not chosen any numbers yet for this weeks lotto!");

		}

		else { // if they have been filled

			Lotto.Generate(); // we start the lotto by generating the winning
								// numbers by calling the generate method from
								// the StartLotto class

			ArrayList<Integer> winNums = new ArrayList<Integer>(Lotto.Lottery); // we
			// assign
			// the
			// ArrayList
			// 'Lotto'
			// from
			// the
			// StartLotto
			// class
			// to
			// winNums
			// ArrayList

			String results = "WINNG NUMBERS ARE :\n____________________________\n Todays Jackpot is: €"
					+ jackPot //displays winning nums and todays jackpot to user
					+ winNums

					+ "\n"
					+ "LOTTO RESULTS FOR USERNAME "
					+ LoginForm.temp.getUserFirstName()
					+ " : "
					+ "\n____________________________";
			checkNums.addAll(ownQPTicket.Games); // add quick pick to checkNums
			// ArrayList of ArrayList
			checkNums.addAll(ownTicket.Games); // add selected numbers to
			// checkNums ArrayList of
			// ArrayList
			int x = 1;							// x reps the ticket number	
			String Result = "";					// declared to be concatenated to later

			for (ArrayList<Integer> i : checkNums) {					// loops through nested arraylist of check nums 
				int prize = 0;											// sets prize to 0
				Set<Integer> intersection = new TreeSet<Integer>(i);	// creates a TreeSet and assings the nested ArrayList to it. used to distinguish matches, because of set properties, and to create numerical order (1,2,3,4...etc) 
				intersection.retainAll(winNums);						// retains numbers that match winning numbers

				Result += "\nTicket " + x + "is " + i + "\n" + "You had "   // concatenates all necessary details to Result, to display to user after each loop
						+ intersection.size() + " matching numbers.\n"; //if the intersection of the winning ticket set and bought tickets set is 0 you win nothing

				if (intersection.size() > 0) {

					if (intersection.size() == 1) {							
						prize = 6;
					}
					if (intersection.size() == 2) {
						prize = 34;
					}
					if (intersection.size() == 3) {
						prize = 79;
					}
					if (intersection.size() == 4) {
						prize = 100;
					}
					if (intersection.size() == 5) {
						prize = 170;
					}
					if (intersection.size() == 6) {
						prize = jackPot;
						jackPot = 250;									//	if 6 numbers are matched, reset the jackpot back to 250

					}

					Result += "The matched numbers are " + intersection + "\n"
							+ "Your prize is $" + prize + "\n";							// prize depends obviously on how long the intersection is each time, if you get all 6, you win the jack pot
				}

				winnings += prize;											// add prize to winnings 
				x++;														// increment the ticket number

			}																// return and start the loop again till all ticket are checked against the winners
			jackPot = jackPot + 50;											// regardless of what value the jackpot is +50 to it
			LoginForm.temp.setLottoBalance(LoginForm.temp.getLottoBalance() + winnings);     //add winnings to lotto balance
			try {

				LoginForm.hashmapHandlerCust.writeOut(						// write out updated accounts
						RegisterForm.CustomerList, RegisterForm.myFile);

				readJackPot.writeOut(jackPot, myJackPot);					//write out jackpot totals

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
												JOptionPane.showMessageDialog(null, " Error" + e
						+ "\nPlease Try Again");
			} catch (IOException e) {
				// TODO Auto-generated catch block
												JOptionPane.showMessageDialog(null, " Error" + e
						+ "\nPlease Try Again");
			}
			JOptionPane.showMessageDialog(null, results + "\n" + Result);
			System.exit(0); // exists the program
		}

	}

	// /////////////////////////////////////////////////////////////

	private void remove(String uName) { // remove method
		RegisterForm.CustomerList.remove(uName);

	}

	private void getInput(int numIn, HashSet<Integer> selectedNumbers) { // adding
																			// a
																			// manually
																			// entered
																			// lotto
																			// number
																			// to
																			// the
																			// slectedNumbers
																			// hashset

		if (selectedNumbers.add(numIn)) {

		}

	}

	public static boolean search(Integer accNo, String custPass) {

		boolean blnFound = temp.containsKey(accNo);
		return blnFound;

	}

	public static Bank getCustomer(Integer accNo, String custPass) {         // Method created to return bank account

		if (checkFile(tempFile) == false) {

			return null;

		} else {
			try {
				temp = tempBankHashMapHandler.readIn(tempFile);							// read from bank accounts to temp HashMap
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, " Error" + e
						+ "\nPlease Try Again");
			}
		}

		boolean exist = search(accNo, custPass);
		Bank tempCust = temp.get(accNo);
		exist = tempCust.getPassw().equals(custPass);

		if (exist == false) {
			return null;
		} else {
			return temp.get(accNo);

			// to

		}
	}

	public static boolean checkFile(File fileToCheck) { // method to check if
														// a file
		// file exists in RegisterForm

		if (fileToCheck.exists()) {
			return true;

		} else
			return false;

	}

}
