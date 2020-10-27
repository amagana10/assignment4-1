package com.meritamerica.assignment4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MeritBank {
	static String accountData;
	static long accountNumber;
	static AccountHolder[] accountHolders = new AccountHolder[0];
	static CDOffering[] CDOfferingsArray = new CDOffering[0];
	static FraudQueue fraudQueue;
	

	static void addAccountHolder(AccountHolder accountHolder) {
		AccountHolder[] temp = Arrays.copyOf(accountHolders, accountHolders.length + 1);
		accountHolders = temp;
		accountHolders[accountHolders.length-1] = accountHolder;
	}
	
	static AccountHolder[] getAccountHolders(){
		return accountHolders;
	}
	
	static AccountHolder[] sortAccountHolders() {
		Arrays.sort(accountHolders);
		return accountHolders;
	}
	
	static CDOffering[] getCDOfferings() {
		return CDOfferingsArray;
	}
	
	static CDOffering getBestCDOffering(double depositAmount){
		double bestValue = 0.0;
		CDOffering bestOfferingAvailable = null;
		if(CDOfferingsArray == null) {
			return null;
		} else {
			for(CDOffering offering: CDOfferingsArray) {
				if(futureValue(depositAmount, offering.getInterestRate(), offering.getTerm()) > bestValue) {
					bestOfferingAvailable = offering;
					bestValue = futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
				}
			}
		}
		return bestOfferingAvailable;
		
	}
	
	static CDOffering getSecondBestCDOffering(double depositAmount){
		CDOffering secondBest = null;
		double bestValue = 0.0;
		CDOffering bestOfferingAvailable = null;
		if(CDOfferingsArray == null) {
			return null;
		} else {
			for(CDOffering offering: CDOfferingsArray) {
				if(MeritBank.futureValue(depositAmount, offering.getInterestRate(), offering.getTerm()) > bestValue) {
					secondBest = bestOfferingAvailable;
					bestOfferingAvailable = offering;
					bestValue = MeritBank.futureValue(depositAmount, offering.getInterestRate(), offering.getTerm());
				}
			}
		}
		return secondBest;
	}
	
	static void clearCDOfferings(){
		CDOfferingsArray = null;
	}
	
	static void setCDOfferings(CDOffering[] offerings){
		CDOfferingsArray = offerings;
	}
	
	static long getNextAccountNumber() {
		return accountNumber;
	}
	static void setNextAccountNumber(long nextAccountNumber) {
		MeritBank.accountNumber = nextAccountNumber;
	}
	
	static double totalBalances() {
		double totalBalance = 0.0;
		for(AccountHolder accountHolder: accountHolders) {
			totalBalance += accountHolder.getCombinedBalance();
		}
		return totalBalance;
	}
	
	static double futureValue(double presentValue, double interestRate, int term) {
		return MeritBank.recursiveFutureValue(presentValue, term, interestRate);
	}
	
	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		double p = amount;
		double interest = interestRate;
		int n = years;
	
		if(n == 0){
		return p;
		}
		return (1 + interest) * (recursiveFutureValue(p , n -1, interest));  
		
	}
	
	
	public static boolean processTransaction(Transaction transaction) throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException {
		
		boolean result = true;
		double test = transaction.getAmount();
		BankAccount target = transaction.getTargetAccount();
		
		if(test > FraudQueue.getExcessiveAmount()){
			fraudQueue.addTransaction(transaction);
			throw new ExceedsFraudSuspicionLimitException();
			
		}else if(test < 0){
			result = false;
			throw new NegativeAmountException();
			
		}else{
			if(transaction.getTargetAccount() != null){
				double testie = transaction.getTargetAccount().getBalance();
				if(testie < test && transaction instanceof WithdrawTransaction) {
					result = false;
					throw new ExceedsAvailableBalanceException("Exceeds Balance");
				}
				transaction.process();
			}
			
		}
		target.addTransaction(transaction);
		return result;
	}
	
	
	public static FraudQueue getFraudQueue() {
		return fraudQueue;
	}
	
	
	public BankAccount getBankAccount(long accountId) {
		/*
		BankAccount[] tempBankAccountArray = null;
		
		for(int i = 0 ; i < accountHolders.length ; i++) {			
				BankAccount[] temptempBankAccountArray = accountHolders[i].getBankAccounts();
				
				if(tempBankAccountArray == null){					 
					tempBankAccountArray = temptempBankAccountArray;
				}else{			
					BankAccount[] temp = new  BankAccount[temptempBankAccountArray.length + tempBankAccountArray.length];
					for(int j = 0 ; j < tempBankAccountArray.length ; j++){
						temp[j] = tempBankAccountArray[j];				
					}
					for(int j = tempBankAccountArray.length ; j < temp.length ; j++){
						temp[i] = temptempBankAccountArray[j - tempBankAccountArray.length];				
					}
				tempBankAccountArray = temp;
						
				}
		 }
		 BankAccount temp = null;
			for(int i = 0 ; i < tempBankAccountArray.length ; i++) {
				if(tempBankAccountArray[i].getAccountNumber() == accountId) {
				temp = tempBankAccountArray[i];
			    }			
		    }		
		 return temp;
			*/	
		if(MeritBank.accountHolders != null) {
			for(AccountHolder ah: MeritBank.getAccountHolders()) {
				for(BankAccount ba : ah.getCDAccounts() ) {
					if(ba.getAccountNumber() == accountId) {
						return ba;
					}
				}
				for(BankAccount ba : ah.getCheckingAccounts() ) {
					if(ba.getAccountNumber() == accountId) {
						return ba;
					}
				}
				for(BankAccount ba : ah.getCDAccounts() ) {
					if(ba.getAccountNumber() == accountId) {
						return ba;
					}
				}
			}
		}
		return null;
	}
		
				
	
	
	public static void addToFraudQueue(Transaction transaction) {
		if(fraudQueue == null){
			fraudQueue = new FraudQueue();
		}
		fraudQueue.addTransaction(transaction);
	}
	
	static boolean readFromFile(String fileName){
		//resets all the static variables
		MeritBank.accountData = "";
		MeritBank.accountNumber = 0;
		MeritBank.accountHolders = new AccountHolder[0];
		MeritBank.CDOfferingsArray = new CDOffering[0];
		
		try (BufferedReader rd = new BufferedReader(new FileReader(fileName))){
			//reads first line sets account number
			MeritBank.accountNumber = Long.parseLong(rd.readLine());
			
			//reads line 2 number of cd offerings
			int numOfCDOfferings = Integer.parseInt(rd.readLine());
			
			//keeps reading cd offerings in file
			CDOffering[] cds = new CDOffering[numOfCDOfferings];
			for(int i = 0; i < numOfCDOfferings; i++) {
				cds[i] = CDOffering.readFromString(rd.readLine());
			}
			MeritBank.setCDOfferings(cds);//adds the offerings to the bank
			
			//reads number number of account holders
			int numOfAccountHolders = Integer.parseInt(rd.readLine());
			
			
			for(int i = 0; i < numOfAccountHolders; i++) {
				//reads account holder info for each accountHolder
				AccountHolder holder = AccountHolder.readFromString(rd.readLine());
				
				//reads number of checking accounts
				int numOfChecking = Integer.parseInt(rd.readLine());
				//reads in data for each checking account
				for(int j = 0; j < numOfChecking; j++) {
					MeritBank.accountData = rd.readLine();
					CheckingAccount ca = CheckingAccount.readFromString(MeritBank.accountData);
					holder.addCheckingAccount(ca);
					//reads number of checking account transactions
					int numOfTransactions = Integer.parseInt(rd.readLine());
					//reads in data for each checking account transaction
					for(int k = 0; k < numOfTransactions; k++) {
						ca.addTransaction(Transaction.readFromString(rd.readLine(), ca));
					}
				}
				
				//reads number of Saving accounts
				int numOfSavings = Integer.parseInt(rd.readLine());
				//reads in data for each savings account transaction
				for(int j = 0; j < numOfSavings; j++) {
					MeritBank.accountData = rd.readLine();
					SavingsAccount sa = SavingsAccount.readFromString(MeritBank.accountData);
					holder.addSavingsAccount(sa);
					//reads number of Savings account transactions
					int numOfTransactions = Integer.parseInt(rd.readLine());
					//reads in data for each savings account transaction
					for(int k = 0; k < numOfTransactions; k++) {
						sa.addTransaction(Transaction.readFromString(rd.readLine(), sa));
					}
				}
				
				//reads number of CD accounts
				int numOfCDs = Integer.parseInt(rd.readLine());
				//reads in data for each CD account
				for(int j = 0; j < numOfCDs; j++) {
					MeritBank.accountData = rd.readLine();
					CDAccount cd = CDAccount.readFromString(MeritBank.accountData);
					holder.addCDAccount(cd);
					//reads number of CD account transactions
					int numOfTransactions = Integer.parseInt(rd.readLine());
					//reads in data for each CD account transaction
					for(int k = 0; k < numOfTransactions; k++) {
						cd.addTransaction(Transaction.readFromString(rd.readLine(), cd));
					}
				}
				//adds AccountHolder to the bank
				MeritBank.addAccountHolder(holder);
			}
		}
		catch(IOException ex){
			System.out.println("did not read because i/o");
			ex.printStackTrace();
			return false;
		}
		catch(Exception e) {
			System.out.println("did not read");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	static boolean writeToFile(String fileName) {
		
		
		return true;
	}
	
}