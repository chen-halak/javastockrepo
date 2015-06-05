package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;
/**
 * An instance of this class represent exception that it thrown when stock already
 * exist in portfolio
 * than max stocks limitation
 * @author Chen Halak since JDK 1.7 6/5/15
 */
public class StockAlreadyExistException extends PortfolioException {

	public StockAlreadyExistException(String symbol) {
		super("Sorry, The stock " + symbol + " already exist in portfolio");
	}


}
