package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;
/**
 * An instance of this class represent exception that it  thrown when balance
 * become negative
 * @author Chen Halak since JDK 1.7 6/5/15
 */
public class BalanceException extends PortfolioException {
	
	public BalanceException() {
		super("Sorry, not enough money in balance");
	}

}
