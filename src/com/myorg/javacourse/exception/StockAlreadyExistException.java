package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class StockAlreadyExistException extends PortfolioException {

	public StockAlreadyExistException(String symbol) {
		super("Sorry, The stock " + symbol + " already exist in portfolio");
	}

}
