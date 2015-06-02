package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class LackOfStocksToSellException extends PortfolioException {

	public LackOfStocksToSellException() {
		super("Sorry, Not enough stocks to sell");
	}
}
