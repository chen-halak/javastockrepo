package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class BalanceException extends PortfolioException {
	
	public BalanceException() {
		super("Sorry, not enough money in balance");
	}

}
