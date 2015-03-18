package ie.wit.bankaccount;

import ie.wit.exceptions.InvalidWithdrawException;
import ie.wit.exceptions.MustBeLetters;
import ie.wit.exceptions.NegativeBalanceException;
import ie.wit.exceptions.NegativeDepositException;
import ie.wit.exceptions.PasswLength;
import ie.wit.exceptions.StringLength;
import ie.wit.io.FileHandler;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JOptionPane;

public class Bank implements Serializable {

	public Bank(String fName, String lName, String address, String passw,
			double balance) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.address = address;
		this.passw = passw;
		this.balance = balance;
		/* this.accountNumber = accountNumber; */
	}

	private static final long serialVersionUID = 1L;

	// bank account
	protected String fName;
	protected String lName;
	protected String address;
	protected String passw;
	protected double balance;

    
	
	
	// deposit method
	public void deposit(double tempDeposit) {
		balance += tempDeposit;
	}

	// method to withdraw from account, throws exception if amount attempted to withdraw is less than balance 
	public void withdraw(double tempWithdraw) throws InvalidWithdrawException {
		
		if (tempWithdraw > balance) throw new InvalidWithdrawException();
		
		balance -= tempWithdraw; //if nothing thrown, subtract withdraw amount from balance
		
	}



	public String getfName() {
		return fName;
	}

	public void setfName(String fName) throws MustBeLetters {

		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) throws MustBeLetters {

		this.lName = lName;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {

		this.passw = passw;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {

		this.balance = balance;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "First Name=" + fName + ", Last Name=" + lName + ", Address="
				+ address + ", Account Balance=" + balance + " \n ";
	}

}
