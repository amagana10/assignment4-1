package com.meritamerica.assignment4;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class Transaction {
	private BankAccount sourceAccount;
	private BankAccount targetAccount;
	private double amount;
	private java.util.Date transactionDate;
	private Boolean isProcessed;
	private String rejectionReason;
	
	public Transaction() {
	} 

	public Transaction(BankAccount targetAccount , double amount) {
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.transactionDate = new java.util.Date();
		this.isProcessed = false;
		this.rejectionReason = "none";
	} 

	public Transaction(BankAccount sourceAccount , BankAccount targetAccount , double amount) {
		this.sourceAccount = sourceAccount;
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.transactionDate = new java.util.Date();
		this.isProcessed = false;
		this.rejectionReason = "none";
	}

	public Transaction(BankAccount targetAccount, double amount , java.util.Date dateToBeAdded) {
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.transactionDate = dateToBeAdded;
		this.isProcessed = false;
		this.rejectionReason = "none";
	}
	
	//getters and setters
	public BankAccount getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(BankAccount sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	
	public BankAccount getTargetAccount() {
		return targetAccount;
	}

	public void setTargetAccount(BankAccount targetAccount) {
		this.targetAccount = targetAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public java.util.Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(java.util.Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String writeToString() {
		return "";
	}

	public static Transaction readFromString(String transactionDataString , BankAccount target) {
		Transaction toBeAdded = null;

		try{
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
			String[] toBeParsed = transactionDataString.split(",");
			BankAccount targetAccount = target;
			
			long aThingToBeAdded = Long.parseLong(toBeParsed[0]);
			long accountNumber = Long.parseLong(toBeParsed[1]);
			double ammountToBeAdded = Double.parseDouble(toBeParsed[2]);
			java.util.Date dateToBeAdded = dateFormatter.parse(toBeParsed[3]);
			
			if(aThingToBeAdded < 0 && ammountToBeAdded > 0){
			    toBeAdded = new DepositTransaction(targetAccount , ammountToBeAdded , dateToBeAdded);
			}else if(aThingToBeAdded < 0 && ammountToBeAdded < 0) {
				toBeAdded = new WithdrawTransaction(targetAccount , ammountToBeAdded , dateToBeAdded);
			}else {
				toBeAdded = new TransferTransaction(targetAccount , ammountToBeAdded , dateToBeAdded); 
			}     

		}catch(NumberFormatException exception) {

			throw exception;
			
		}catch(ParseException exception) { 

		}
		return toBeAdded;
	}
	
	public abstract void process() throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException;
	public boolean isProcessedByFraudTeam() {
		return this.isProcessed;
	}
	public void setProcessedByFraudTeam(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
	public String getRejectionReason() {
		return this.rejectionReason;
	}
	public void setRejectionReason(String reason) {
		this.rejectionReason = reason;
	}



}






