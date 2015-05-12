package com.myorg.javacourse.model;

/**
 * An instance of this class represent portfilo-manage stocks on one place
 * 
 * @author Chen Halak since JDK 1.7 4/23/15
 */
public class Portfolio {

	/**
	 * the recommendation of stocks in the portpolio
	 */
	enum ALGO_RECOMMANDATION {
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
	 */

	public Portfolio(String title, Stock[] stocks, int portfolioSize) {
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
	 */
	public Portfolio(Portfolio portfolio) {
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

	public float getStockValue() {
		float result = 0;
		for (int i = 0; i < portfolioSize; i++) {
			result += stocks[i].getBid() * stocks[i].getStockQuantity();
		}
		return result;
	}

	public float getBalance() {
		return balance;
	}

	public float getTotalValue() {
		return getBalance() + getStockValue();
	}

	/**
	 * add stock to the portfolio returned not simplified
	 * 
	 * @param stock
	 *            the stock that added to the portfolio
	 */
	private void addStock(Stock stock, boolean isQuantityZero) {
		if (stock == null)
			return;
		if (stock.getSymbol() == null)
			return;

		boolean inPortfolio = isStockInPortfolio(stock);

		if (inPortfolio == false && portfolioSize < MAX_PORTFOLIO_SIZE) {
			stocks[portfolioSize] = stock;
			if (isQuantityZero) {
				stock.setStockQuantity(0);
			}
			portfolioSize++;
		} else if (portfolioSize == MAX_PORTFOLIO_SIZE) {
			System.out.println("Can't add new stock,portfolio can have only"
					+ MAX_PORTFOLIO_SIZE + "stocks");

		}
	}

	public void addStock(Stock stock) {
		addStock(stock, true);
	}

	/**
	 * returned is not simplified remove the first stock from portfolio
	 */
	public boolean removeStock(String symbol) {
		if (!sellStock(symbol, -1))
			return false;
		Stock[] oldStocks = stocks;
		int oldPortfolioSize = portfolioSize;
		boolean isSuccess = false;

		removeAllStocks();

		for (int i = 0; i < oldPortfolioSize; i++) {
			if (oldStocks[i].getSymbol().equals(symbol)) {
				isSuccess = true;
			} else {
				Stock x = oldStocks[i];
				addStock(x, false);
			}
		}
		return isSuccess;

	}

	private Stock findStock(String symbol) {

		for (int i = 0; i < portfolioSize; i++) {
			if (stocks[i].getSymbol().equals(symbol)) {
				return stocks[i];
			}
		}
		return null;
	}

	public boolean sellStock(String symbol, int quantity) {
		Stock stock = findStock(symbol);

		if (stock == null)
			return false;

		if (quantity == -1)
			return sellStock(symbol, stock.getStockQuantity());

		int tempQuantity = stock.getStockQuantity();
		tempQuantity -= quantity;
		if (tempQuantity < 0) {
			System.out.println("Not enough stocks to sell");
			return false;
		}

		float bid = stock.getBid();
		updateBalance(bid * quantity);

		stock.setStockQuantity(tempQuantity);

		return true;
	}

	public boolean buyStock(String symbol, int quantity) {
		Stock stock = findStock(symbol);

		if (stock == null)
			return false;

		float ask = stock.getAsk();
		if (quantity == -1)
			return buyStock(symbol, (int) (balance / ask));

		int tempQuantity = stock.getStockQuantity();
		tempQuantity += quantity;

		if (!updateBalance(-ask * quantity)) {
			System.out.println("Not enough balance to complete purchase");
			return false;
		}

		stock.setStockQuantity(tempQuantity);

		return true;
	}

	/**
	 * returned is not simplified remove the first stock from portfolio
	 */
	public void removeFirstStock() {
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

	public boolean updateBalance(float amount) {
		if (balance + amount >= 0) {
			balance += amount;
		} else {
			return false;
		}
		return true;
	}
}
