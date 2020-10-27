package com.meritamerica.assignment4;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Vanessa Marsh , Andres Magana
 *
 */
public class CDAccount extends BankAccount {
	CDOffering offering;
	
	
	public CDAccount(CDOffering offering, double openBalance) {
		super(openBalance,offering.getInterestRate());
		this.offering = offering;
	}
	
	public CDAccount(long accountNumber, double balance, double interestRate, Date accountOpenedOn, int term) {
		super(accountNumber, balance,interestRate,accountOpenedOn);
		this.offering = new CDOffering(term, interestRate);
	}
	
	public int getTerm() {
		return this.offering.getTerm();
	}
	
	public Date getStartDate(){
		return this.getOpenedOn();
	}
	@Override
	public boolean withdraw(double amount) {
        return false;
    }
    @Override
    public boolean deposit(double amount) {
    	return false;
    }
    double futureValue() {
		double futureValue = super.futureValue(this.offering.getTerm());
		return futureValue;
	}
	public static CDAccount readFromString(String accountData) throws ParseException, NumberFormatException {
	    	String[] holding = accountData.split(",");
	    	SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
	    	
	    	//[0] is accountNumber, [1] is balance, [2] is interestRate, date is [3] which is SimpleDate, term is [4]
	    	//might throw exception
	    	long accountNumber = Long.parseLong(holding[0]);
	    	double balance = Double.parseDouble(holding[1]);
	    	double interestRate = Double.parseDouble(holding[2]);
	    	Date accountOpenedOn = date.parse(holding[3]);
	    	int term = Integer.parseInt(holding[4]);
	    	
	    	//creates the Bank account
	    	return new CDAccount(accountNumber, balance, interestRate, accountOpenedOn, term);
	}
	@Override
	public String writeToString() {
		StringBuilder accountData = new StringBuilder(super.writeToString());
		accountData.append(",").append(this.offering.getTerm());
		return accountData.toString();
	}
    
}
