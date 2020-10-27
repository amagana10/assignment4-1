package com.meritamerica.assignment4;


public class ExceedsCombinedBalanceLimitException extends Exception {



	ExceedsCombinedBalanceLimitException(String errorMessage){
		super(errorMessage);
//		return null;
	}

	public static double getCombinedbalancelimit() {
		// TODO Auto-generated method stub
		return 250000.0;
	}
}

