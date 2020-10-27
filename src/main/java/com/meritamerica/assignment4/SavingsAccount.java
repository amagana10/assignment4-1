package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingsAccount extends BankAccount {
	
		
		public static final double INTEREST_RATE = 0.01;
		
		public SavingsAccount(double openBalance){
			super(openBalance, INTEREST_RATE);
		}
		
		public SavingsAccount (long accountNumber, double openBalance, double interestRate, Date accountOpenedOn) {
			super(accountNumber, openBalance, interestRate, accountOpenedOn);
		}
		
		
		public static SavingsAccount readFromString(String accountData)throws ParseException, NumberFormatException {
		    	String[] holding = accountData.split(",");
		    	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		    	
		    	//[0] is accountNumber, [1] is balance, [2] is interestRate, date is [3] which is SimpleDate
		    	//might throw exception
		    	long accountNumber = Long.parseLong(holding[0]);
		    	double balance = Double.parseDouble(holding[1]);
		    	double interestRate = Double.parseDouble(holding[2]);
		    	Date accountOpenedOn = date.parse(holding[3]);
		    	
		    	//creates the Bank account
		    	return new SavingsAccount(accountNumber, balance, interestRate,accountOpenedOn);
		}

}
