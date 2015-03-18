package ie.wit.customer;

import java.io.Serializable;

public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8461246140549314457L;
	public Customer(String userFirstName, String userSurname, String password, double lottoBalance ) {
		super();
		this.userFirstName = userFirstName;
		this.userSurname = userSurname;
		this.password = password;
		this.lottoBalance=lottoBalance;
	}
	protected String userFirstName;
	protected String userSurname;
	protected String password;
	protected double lottoBalance;
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserSurname() {
		return userSurname;
	}
	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getLottoBalance() {
		return lottoBalance;
	}
	public void setLottoBalance(double lottoBalance) {
		this.lottoBalance = lottoBalance;
	}


	@Override
	public String toString() {
		return "Customer [userFirstName=" + userFirstName + ", userSurname="
				+ userSurname + ", password=" + password + ", lottoBalance="
				+ lottoBalance + "]";
	}

}
