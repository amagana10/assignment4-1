package com.meritamerica.assignment4;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BankAccount {
	double balance;
	double interestRate;
	Date accountOpenedOn;
	long  accountNumber;
	List<Transaction> transactions;

	public BankAccount(double balance, double interestRate) {
		this.balance = balance;
	    this.interestRate = interestRate;
	    this.transactions = new ArrayList<>();
	}
	    
	BankAccount(double balance, double interestRate, Date accountOpenedOn){
		this(balance, interestRate);
		this.accountOpenedOn = accountOpenedOn;
	    this.transactions = new ArrayList<>();
	}
	    
	public BankAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn){
		this(balance, interestRate, accountOpenedOn);
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.interestRate = interestRate;
		this.accountOpenedOn = accountOpenedOn;
	    this.transactions = new ArrayList<>();
	}
	   
	public long getAccountNumber() {
		return accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public java.util.Date getOpenedOn() {
		return accountOpenedOn;
	}
	
	public boolean withdraw(double amount) {
        if(amount <= this.balance && amount >= 0) {
        	this.balance = this.balance-amount;
        	return true;
        }
        else {
    		return false;       	
        }
    }
	
    public boolean deposit(double amount) {
    	if(amount >= 0) {
        	this.balance = this.balance+ amount;
        	return true;
        }
        else {
    		return false;       	
        }
    }
    double futureValue(int term) {
		double futureValue = MeritBank.futureValue(this.balance, this.interestRate, term);
		return futureValue;
	}
	
	public String writeToString() {
		StringBuilder accountData = new StringBuilder();
		accountData.append(accountNumber).append(",");
		accountData.append(accountOpenedOn).append(",");
		accountData.append(balance).append(",");
		accountData.append(interestRate);
		return accountData.toString();
	}

	public void addTransaction(Transaction transaction) {
		this.transactions.add(transaction);
		
	}
	public List<Transaction> getTransactions(){
		return this.transactions;
		
	}
	    

}
