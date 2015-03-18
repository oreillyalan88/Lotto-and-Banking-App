package ie.wit.app;



import ie.wit.lotto.LottoManager;

public class LoginProgram {

	public static void main(String args[]) {


		new LoginForm(); // create new LoginForm

	}

	public static void mainMenu(){
		int option = 0;

		LottoManager manager = new LottoManager();
		do {
			option = manager.menuMain();

			switch (option) {
			case 1:
				manager.removeAccount();
				break;
			case 2:
				manager.changeUsername();
				break;

			case 3:
				manager.menuBuyAQuickTicket();
				break;

			case 4:
				manager.inputLottoNums();
				break;

			case 5:
				manager.startLotto();
				break;

			case 6:
				manager.depositFunds();
				break;
				
			case 7:
				manager.withdrawFunds();
				break;

			default:
				break;

			}
		} while (option != 8);

	}

}
