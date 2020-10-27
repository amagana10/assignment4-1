package com.meritamerica.assignment4;

import java.util.Date;

public class TransferTransaction extends Transaction {
	
	TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount,
			double amount){
		super(sourceAccount,targetAccount,amount);
	}

	public TransferTransaction(BankAccount targetAccount, double ammountToBeAdded, Date dateToBeAdded) {
		super(targetAccount,ammountToBeAdded,dateToBeAdded);
	}

	@Override
	public void process()
			throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		
		super.getSourceAccount().withdraw(super.getAmount());
		super.getTargetAccount().deposit(super.getAmount());
	}
}
