package com.meritamerica.assignment4;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FraudQueue {
	
	private Queue<Transaction> fq;
	public static final double EXCESSIVEAMOUNT = 1000;
	
	public FraudQueue() {
		this.fq = null;
	}
	
	public void addTransaction(Transaction transaction) {
		if(fq == null) {
			fq = new LinkedList<Transaction>();
		}
		fq.add(transaction);
	}
	
	
	public Transaction getTransaction() {
		return fq.remove();		
	}
	
	public static double getExcessiveAmount() {
		return EXCESSIVEAMOUNT;
	}
}
