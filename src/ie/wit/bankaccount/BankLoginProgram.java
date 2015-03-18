package ie.wit.bankaccount;

import ie.wit.bankaccount.BankManager;

public class BankLoginProgram {

	public static void main(String args[]) {
		new BankLoginForm(); // create new LoginForm

	}

	public static void mainMenu(){
		int option = 0;

		BankManager app = new BankManager();
		do {
			option = app.menuMain();

			switch (option) {
			case 1:
				app.removeAccount();
				break;
			case 2:
				app.changeAddress();
				break;

			case 3:
				app.depositFunds();
				break;

			case 4:
				app.withdrawFunds();
				break;

			case 5:
				app.listAccounts();
				break;

			case 6:
				break;


			}
		} while (option != 6);

	}

}
