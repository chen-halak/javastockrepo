package com.myorg.javacourse.model;

import org.algo.model.PortfolioInterface;

import com.myorg.javacourse.exception.BalanceException;
import com.myorg.javacourse.exception.LackOfStocksToSellException;
import com.myorg.javacourse.exception.PortfolioFullException;
import com.myorg.javacourse.exception.StockAlreadyExistException;
import com.myorg.javacourse.exception.StockNotExistException;

/**
 * An instance of this class represent portfilo-manage stocks on one place
 * 
 * @author Chen Halak since JDK 1.7 4/23/15
 */
public class Portfolio implements PortfolioInterface {

	/**
	 * the recommendation of stocks in the portpolio
	 */
	public enum ALGO_RECOMMANDATION {
		BUY, SELL, REMOVE, HOLD
	};

	/**
	 * the maximum size of stocks in the portpolio
	 */
	private final static int MAX_PORTFOLIO_SIZE = 5;
	/**
	 * the name of the portfolio
	 */
	private String title;
	/**
	 * array that include instances of stocks
	 */
	private Stock[] stocks;
	/**
	 * the physical size of the array that include instances of stocks
	 */
	private int portfolioSize = 0;

	private float balance = 0;

	/**
	 * create instance of stocks returned not simplified
	 */
	public Portfolio() {
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
	}

	/**
	 * returns instance of portfolio with the parameters below
	 * 
	 * @param title
	 * @param stocks
	 * @param portfolioSize
	 * @throws PortfolioFullException
	 * @throws StockAlreadyExistException
	 */

	public Portfolio(String title, Stock[] stocks, int portfolioSize)
			throws StockAlreadyExistException, PortfolioFullException {
		this();
		this.title = new String(title);

		for (int i = 0; i < portfolioSize; i++) {
			Stock x = new Stock(stocks[i]);
			addStock(x, false);
		}
	}

	/**
	 * @param portfolio
	 *            -copy this parameter returns instance of the same portfolio
	 *            that given as parameter
	 * @throws PortfolioFullException
	 * @throws StockAlreadyExistException
	 */
	public Portfolio(Portfolio portfolio) throws StockAlreadyExistException,
			PortfolioFullException {
		this(new String(portfolio.getTitle()), portfolio.getStocks(), portfolio
				.getPortfolioSize());
	}

	public int getPortfolioSize() {
		return portfolioSize;
	}

	public void setPortfolioSize(int portfolioSize) {
		this.portfolioSize = portfolioSize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return stocks array
	 */
	public Stock[] getStocks() {
		return stocks;
	}

	/**
	 * 
	 * @param stock
	 *            -the stock we want to check if it in portfolio
	 * @return if the stock already in portfolio
	 */
	private boolean isStockInPortfolio(Stock stock) {
		for (int i = 0; i < portfolioSize; i++) {
			if (stock.getSymbol().equals(stocks[i].getSymbol())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return total value of stocks
	 */
	public float getStockValue() {
		float result = 0;
		for (int i = 0; i < portfolioSize; i++) {
			result += stocks[i].getBid() * stocks[i].getStockQuantity();
		}
		return result;
	}

	/**
	 * @return value of balance
	 */
	public float getBalance() {
		return balance;
	}

	/**
	 * @return total value of stocks and value of balance
	 */
	public float getTotalValue() {
		return getBalance() + getStockValue();
	}

	/**
	 * add stock to the portfolio returned not simplified
	 * 
	 * @param stock
	 *            -the stock we want to add to portfolio
	 * @param isQuantityZero
	 *            -say if quantity is equal to zero
	 * @throws StockAlreadyExistException
	 * @throws PortfolioFullException
	 */

	private void addStock(Stock stock, boolean isQuantityZero)
			throws StockAlreadyExistException, PortfolioFullException {
		if (stock == null)
			return;
		if (stock.getSymbol() == null)
			return;
		if (isStockInPortfolio(stock))
			throw new StockAlreadyExistException(stock.getSymbol());

		if (portfolioSize < MAX_PORTFOLIO_SIZE) {
			stocks[portfolioSize] = stock;
			if (isQuantityZero) {
				stock.setStockQuantity(0);
			}
			portfolioSize++;
		} else if (portfolioSize == MAX_PORTFOLIO_SIZE) {
			throw new PortfolioFullException(MAX_PORTFOLIO_SIZE);
		}
	}

	/**
	 * returned is not simplified add stock to the portfolio
	 * 
	 * @throws PortfolioFullException
	 * @throws StockAlreadyExistException
	 */
	public void addStock(Stock stock) throws StockAlreadyExistException,
			PortfolioFullException {
		addStock(stock, true);
	}

	/**
	 * remove stock from portfolio
	 * 
	 * @param symbol
	 *            of stock we want remove
	 * @return if stock was removed from portfolio
	 * @throws PortfolioFullException
	 * @throws StockAlreadyExistException
	 * @throws LackOfStocksToSell
	 * @throws StockNotExistException
	 * @throws BalanceException
	 */
	public void removeStock(String symbol) throws StockAlreadyExistException,
			PortfolioFullException, StockNotExistException, LackOfStocksToSellException,
			BalanceException {
		sellStock(symbol, -1);
		Stock[] oldStocks = stocks;
		int oldPortfolioSize = portfolioSize;

		removeAllStocks();

		for (int i = 0; i < oldPortfolioSize; i++) {
			if (!oldStocks[i].getSymbol().equals(symbol)) {
				Stock x = oldStocks[i];
				addStock(x, false);
			}
		}

	}

	/**
	 * 
	 * @param symbol
	 *            -name of stock
	 * @return stock that the symbol is here name
	 */
	private Stock findStock(String symbol) {

		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(symbol)) {
				return stocks[i];
			}
		}
		return null;
	}

	/**
	 * 
	 * @param symbol
	 *            -name of stock
	 * @param quantity
	 *            -the amount stock we went to sell
	 * @return if the selling was successful
	 * @throws StockNotExistException
	 * @throws LackOfStocksToSell
	 * @throws BalanceException
	 */
	public void sellStock(String symbol, int quantity)
			throws StockNotExistException, LackOfStocksToSellException, BalanceException {
		Stock stock = findStock(symbol);

		if (stock == null)
			throw new StockNotExistException(symbol);

		if (quantity == -1) {
			sellStock(symbol, stock.getStockQuantity());
			return;
		}

		int tempQuantity = stock.getStockQuantity();
		tempQuantity -= quantity;
		if (tempQuantity < 0) {
			throw new LackOfStocksToSellException();
		}

		float bid = stock.getBid();
		updateBalance(bid * quantity);

		stock.setStockQuantity(tempQuantity);
	}

	/**
	 * @param symbol
	 *            -name of stock
	 * @param quantity
	 *            -the amount stock we went to buy
	 * @return if the buying was successful
	 * @throws StockNotExistException
	 * @throws BalanceException
	 */
	public void buyStock(String symbol, int quantity)
			throws StockNotExistException, BalanceException {
		Stock stock = findStock(symbol);

		if (stock == null)
			throw new StockNotExistException(symbol);

		float ask = stock.getAsk();
		if (quantity == -1) {
			buyStock(symbol, (int) (balance / ask));
			return;
		}

		int tempQuantity = stock.getStockQuantity();
		tempQuantity += quantity;

		updateBalance(-ask * quantity);
		// System.out.println("Not enough balance to complete purchase");

		stock.setStockQuantity(tempQuantity);

	}

	/**
	 * returned is not simplified remove the first stock from portfolio
	 * 
	 * @throws PortfolioFullException
	 * @throws StockAlreadyExistException
	 */
	public void removeFirstStock() throws StockAlreadyExistException,
			PortfolioFullException {
		Stock[] oldStocks = stocks;
		int oldPortfolioSize = portfolioSize;

		removeAllStocks();

		for (int i = 1; i < oldPortfolioSize; i++) {
			Stock x = oldStocks[i];
			addStock(x, false);
		}

	}

	/**
	 * returned is not simplified create empty fortfolio
	 */
	private void removeAllStocks() {
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
		portfolioSize = 0;
	}

	/**
	 * @returns string with portfolio html code
	 */
	public String getHtmlString() {
		String result = "<h1>" + getTitle() + "</h1>";
		result += "Total Portfolio Value:" + getTotalValue() + "$,<BR>"
				+ "TotalStocksvalue:" + getStockValue() + "$,<BR>" + "Balance:"
				+ getBalance() + "$<BR><BR>";
		for (int i = 0; i < portfolioSize; i++) {
			result += stocks[i].getHtmlDescription() + "<BR>";
		}
		return result;
	}

	/**
	 * returns the last stock from the portfolio
	 */
	public Stock getLastStock() {
		return stocks[portfolioSize - 1];
	}

	/**
	 * add the amount to the current balance
	 * 
	 * @param amount
	 *            -how much to the add
	 * @return if the update done successfully
	 * @throws BalanceException
	 */
	public void updateBalance(float amount) throws BalanceException {
		if (balance + amount >= 0) {
			balance += amount;
		} else {
			throw new BalanceException();
		}
	}
}
