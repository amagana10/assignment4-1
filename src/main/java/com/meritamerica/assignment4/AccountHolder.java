/**
 * @author indpndntluv
 *
 */
package com.meritamerica.assignment4;
public class AccountHolder implements Comparable<AccountHolder>{
	private String firstName;
	private String middleName;
	private String lastName;
	private String ssn;
	CheckingAccount[] checkingArray = new CheckingAccount[0];
	SavingsAccount[] savingsArray = new SavingsAccount[0];
	CDAccount[] cdAccountArray = new CDAccount[0];
	
	public AccountHolder(String first, String middle, String last, String ssn) {
		this.firstName = first;
		this.middleName = middle;
		this.lastName = last;
		this.ssn = ssn;
	}
	/*
	private CheckingAccount addCheckingAccount(double openBalance) {
		if(getCheckingBalance() + getSavingsBalance() + openBalance >= 250000) {
			System.out.println("Cannot open a new Checking Account because aggregate balance of accounts is too high.");
			return null;
		}
		CheckingAccount newA = new CheckingAccount(openBalance);
		CheckingAccount[] newCheckingArray = new CheckingAccount[checkingArray.length+1];
			for (int i = 0; i < newCheckingArray.length - 1; i++) {
				newCheckingArray[i] = checkingArray[i];
			}
		checkingArray = newCheckingArray;
		checkingArray[checkingArray.length-1] = newA;
		return newA;
	}
	*/
	private boolean paddCheckingAccount(CheckingAccount checkingAccount) {
		if(checkingAccount.getBalance() + getCheckingBalance() + getSavingsBalance() >= 250000) {
			System.out.println("Cannot open a new Checking Account because aggregate balance of accounts is too high.");
			return false;
		}
		CheckingAccount[] newCheckingArray = new CheckingAccount[checkingArray.length+1];
			for (int i = 0; i < newCheckingArray.length-1; i++) {
				newCheckingArray[i] = checkingArray[i];
			}
		checkingArray = newCheckingArray;
		checkingArray[checkingArray.length-1] = checkingAccount;
		return true;
		}
	

	public double getCheckingBalance() {
		double total = 0.0;
		int i;
			for(i = 0; i < checkingArray.length; i++) {
				total += checkingArray[i].getBalance();
			}
		return total;
	}
	/*
	public SavingsAccount addSavingsAccount(double openBalance) {
		if(getCheckingBalance() + getSavingsBalance() + openBalance >= 250000) {
			System.out.println("Cannot open a new Savings Account because aggregate balance of accounts is too high.");
			return null;
		}	
		SavingsAccount newA = new SavingsAccount(openBalance);
		SavingsAccount[] newSavingsArray = new SavingsAccount[savingsArray.length+1];
		for (int i = 0; i < newSavingsArray.length-1; i++) {
			newSavingsArray[i] = savingsArray[i];
		}
		savingsArray = newSavingsArray;
		savingsArray[savingsArray.length-1] = newA;
		return newA;
	}
*/
	private SavingsAccount paddSavingsAccount(SavingsAccount savingsAccount) {
		if(savingsAccount.getBalance() + getCheckingBalance() + getSavingsBalance() >= 250000) {
			System.out.println("Cannot open a new Savings Account because aggregate balance of accounts is too high.");
			return null;
		}
		SavingsAccount[] newSavingsArray = new SavingsAccount[savingsArray.length+1];
			for (int i = 0; i < newSavingsArray.length-1; i++) {
			       newSavingsArray[i] = savingsArray[i];
			}
		savingsArray = newSavingsArray;
		savingsArray[savingsArray.length-1] = savingsAccount;
		return savingsAccount;
	}
	
	public SavingsAccount[] getSavingsAccounts() {
		return savingsArray;
	}

	public int getNumberOfSavingsAccounts() {
		return savingsArray.length;
	}

	public double getSavingsBalance() {
		double total = 0.0;
			for(SavingsAccount balance : savingsArray) {
				total += balance.getBalance();
			}
		return total;

	}
	/*
	public CDAccount addCDAccount(CDOffering offering, double openBalance) {
		CDAccount newA = new CDAccount(offering, openBalance);
		CDAccount[] newCDArray = new CDAccount[cdAccountArray.length+1];
			for (int i = 0; i < newCDArray.length-1; i++) {
				newCDArray[i] = cdAccountArray[i];
			}
		cdAccountArray = newCDArray;
		cdAccountArray[cdAccountArray.length-1] = newA;
		return newA;
	}
*/	
	private CDAccount paddCDAccount(CDAccount cdAccount) {
		CDAccount[] newCDArray = new CDAccount[cdAccountArray.length+1];
			for (int i = 0; i < newCDArray.length-1; i++) {
			       newCDArray[i] = cdAccountArray[i];
			}
		cdAccountArray = newCDArray;
		cdAccountArray[cdAccountArray.length-1] = cdAccount;
		return cdAccount;
	}

	public CDAccount[] getCDAccounts() {
		return cdAccountArray;
	}

	public int getNumberOfCDAccounts() {
		return cdAccountArray.length;
	}

	public double getCDBalance() {
		double total = 0.0;
			for(CDAccount balance : cdAccountArray) {
				total += balance.getBalance();
			}
		return total;
	}
	
	
	
	public CheckingAccount addCheckingAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {
		if(getCombinedBalance() + openingBalance >= ExceedsCombinedBalanceLimitException.getCombinedbalancelimit()){
			throw new ExceedsCombinedBalanceLimitException("Exceed balance limit");
		}
		CheckingAccount toBeAdded = new CheckingAccount(openingBalance);
		paddCheckingAccount(toBeAdded);
		
		//adds deposit to transaction list
		DepositTransaction dt = new DepositTransaction(toBeAdded , openingBalance);
		toBeAdded.addTransaction(dt);
		
		if(openingBalance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			
		}
			
		return toBeAdded;
		
	}
	




public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceLimitException {
		Double balance = checkingAccount.getBalance();
		
		if(getCombinedBalance() + balance >= ExceedsCombinedBalanceLimitException.getCombinedbalancelimit()){
			throw new ExceedsCombinedBalanceLimitException("Exceed balance limit");
		}
		
		paddCheckingAccount(checkingAccount);
		
		//adds deposit to transaction list
		DepositTransaction dt = new DepositTransaction(checkingAccount , balance);
		checkingAccount.addTransaction(dt);
		
		if(balance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			
		}		
		
		return checkingAccount;
		
	}

public SavingsAccount addSavingsAccount(double startBalance) throws ExceedsCombinedBalanceLimitException {
		if(getCombinedBalance() + startBalance >= ExceedsCombinedBalanceLimitException.getCombinedbalancelimit()){
			throw new ExceedsCombinedBalanceLimitException("Exceed balance limit");
		}
		SavingsAccount toBeAdded = new SavingsAccount(startBalance);
		paddSavingsAccount(toBeAdded);
		
		//adds deposit to transaction list
		DepositTransaction dt = new DepositTransaction(toBeAdded , startBalance);
		toBeAdded.addTransaction(dt);
		
		if(startBalance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			
		}		
		
		return toBeAdded;
	}

	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException {
		Double balance = savingsAccount.getBalance();
		
		if(getCombinedBalance() + balance >= ExceedsCombinedBalanceLimitException.getCombinedbalancelimit()){
			throw new ExceedsCombinedBalanceLimitException("Exceed balance limit");
		}
		
		paddSavingsAccount(savingsAccount);


		DepositTransaction dt = new DepositTransaction(savingsAccount , balance);
		savingsAccount.addTransaction(dt);
		
		if(balance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			
		}		
		
		return savingsAccount;
	}
	
	public CDAccount addCDAccount(CDOffering cDOffering , double startBalance) throws ExceedsFraudSuspicionLimitException , NegativeAmountException {
		
		CDAccount toBeAdded = new CDAccount(cDOffering , startBalance);
		paddCDAccount(toBeAdded);
		
		//adds deposit to transaction list
		Double balance = toBeAdded.getBalance();
		DepositTransaction dt = new DepositTransaction(toBeAdded , balance);
		toBeAdded.addTransaction(dt);
		
		if(balance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			throw new ExceedsFraudSuspicionLimitException();
		}	
		
		
		return toBeAdded;
	}

	public CDAccount addCDAccount(CDAccount CDAccount) {
		
		paddCDAccount(CDAccount);
		
		//adds deposit to transaction list
		Double balance = CDAccount.getBalance();
		DepositTransaction dt = new DepositTransaction(CDAccount , balance);
		CDAccount.addTransaction(dt);		
		
		if(balance > FraudQueue.getExcessiveAmount()){
			MeritBank.addToFraudQueue(dt);
			
		}	
		
		return CDAccount;				
				
	}


	
	
	

	public double getCombinedBalance() {
		return getCDBalance() + getSavingsBalance() + getCheckingBalance();
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first) {
		this.firstName = first;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middle) {
		this.middleName = middle;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String last) {
		this.lastName = last;
	}
	
	public String getSSN() {
		return ssn;
	}
	
	public void setSSN(String ssn) {
		this.ssn = ssn;
	}
	
	public CheckingAccount[] getCheckingAccounts() {
		return checkingArray;
	}
	
	public int getNumberOfCheckingAccounts() {
		return checkingArray.length;
	}
	
	public String writeToString() {
    	StringBuilder accountHolderData = new StringBuilder();
    	accountHolderData.append(firstName).append(",");
    	accountHolderData.append(middleName).append(",");
    	accountHolderData.append(lastName).append(",");
    	accountHolderData.append(ssn);
    	return accountHolderData.toString();
    }

	public static AccountHolder readFromString(String accountHolderData) throws Exception {
	   try {
		String[] holding = accountHolderData.split(",");
	    String firstName = holding[0];
	    String middleName = holding[1];
	    String lastName = holding[2];
	    String ssn = holding[3];	
	    return new AccountHolder(firstName, middleName, lastName, ssn);
	   }
	   catch(Exception e){
		   throw new Exception();
	   }
	}
	public String toString() {
		return  "Combined Balance for Account Holder " + this.getCombinedBalance();	
	}

	@Override
	public int compareTo(AccountHolder otherAccountHolder) {
		if(this.getCombinedBalance() > otherAccountHolder.getCombinedBalance()) {
			return 1;
		}else if(this.getCombinedBalance() < otherAccountHolder.getCombinedBalance()) {
			return -1;
		}else {
			return 0;
		}
	}

}
