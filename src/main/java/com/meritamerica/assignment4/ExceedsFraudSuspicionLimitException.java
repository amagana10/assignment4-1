package com.meritamerica.assignment4;


public class ExceedsFraudSuspicionLimitException extends Exception {

	public ExceedsFraudSuspicionLimitException() {}
	public ExceedsFraudSuspicionLimitException(String message) {
		super(message);
	}

	public String toString() {
		return "Are you with the mob?";
	}

}


