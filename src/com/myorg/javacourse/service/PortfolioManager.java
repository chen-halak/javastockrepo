package com.myorg.javacourse.service;

import org.algo.dto.PortfolioDto;
import org.algo.dto.PortfolioTotalStatus;
import org.algo.dto.StockDto;
import org.algo.exception.PortfolioException;
import org.algo.exception.SymbolNotFoundInNasdaq;
import org.algo.model.PortfolioInterface;
import org.algo.service.DatastoreService;
import org.algo.service.MarketService;
import org.algo.service.PortfolioManagerInterface;

import com.myorg.javacourse.model.Stock;
import com.myorg.javacourse.model.Portfolio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.algo.model.StockInterface;

/**
 * An instance of this class represents portfolio
 * 
 * @author Chen Halak JDK 1.7 4/23/2015
 */
public class PortfolioManager implements PortfolioManagerInterface {

	private DatastoreService datastoreService = DatastoreService.getInstance();
	Portfolio portfolio;

	/**
	 * @return portfolio with with data of stocks that in it
	 */
	public PortfolioInterface getPortfolio() {
		if (portfolio == null)
			init();
		
		return portfolio;
	}

	/**
	 * create porfolio with data returned not simplified
	 */
	private void init() {
		portfolio = new Portfolio();
		portfolio.setTitle("Exercise 8 portfolio");
		portfolio.updateBalance(10000);
		Stock PIH = new Stock();
		Stock AAL = new Stock();
		Stock CAAS = new Stock();
		PIH.setSymbol("ABY");
		AAL.setSymbol("GOOG");
		CAAS.setSymbol("MRVC");
		PIH.setAsk(10.0F);
		AAL.setAsk(30.0F);
		CAAS.setAsk(20.0F);
		PIH.setBid(8.5F);
		AAL.setBid(25.5F);
		CAAS.setBid(15.5F);

		PIH.setDate("12/15/2014");
		AAL.setDate("12/14/2014");
		CAAS.setDate("12/13/2014");

		portfolio.addStock(PIH);
		portfolio.addStock(AAL);
		portfolio.addStock(CAAS);

		portfolio.buyStock("ABY", 20);
		portfolio.buyStock("GOOG", 30);
		portfolio.buyStock("MRVC", 40);

		portfolio.sellStock("AAL", -1);

		portfolio.removeStock("CAAS");
	}
	/**
	 * Update portfolio with stocks
	 */
	@Override
	public void update() {
		datastoreService.updatePortfolio(toDto(portfolio));
		StockInterface[] stocks = getPortfolio().getStocks();
		List<String> symbols = new ArrayList<>();
		for (StockInterface si : stocks) {
			symbols.add(si.getSymbol());
		}

		List<Stock> update = new ArrayList<>();
		List<Stock> currentStocksList = new ArrayList<Stock>();
		try {
			List<StockDto> stocksList = MarketService.getInstance().getStocks(
					symbols);
			for (StockDto stockDto : stocksList) {
				Stock stock = fromDto(stockDto);
				currentStocksList.add(stock);
			}

			for (Stock stock : currentStocksList) {
				update.add(new Stock(stock));
			}

			datastoreService.saveToDataStore(toDtoList(update));

		} catch (SymbolNotFoundInNasdaq e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void setTitle(String title) {
		portfolio.setTitle(title);

	}

	@Override
	public void updateBalance(float value) throws PortfolioException {
		portfolio.updateBalance(value);

	}

	@Override
	/**
	 * get portfolio totals
	 */
	public PortfolioTotalStatus[] getPortfolioTotalStatus () {
		Portfolio portfolio = (Portfolio) getPortfolio();

		datastoreService.updatePortfolio(toDto(portfolio));
		Map<Date, Float> map = new HashMap<>();

		//get stock status from db.
		StockInterface[] stocks = portfolio.getStocks();
		for (int i = 0; i < stocks.length; i++) {
			StockInterface stock = stocks[i];

			if(stock != null) {
				List<StockDto> stockHistory = null;
				try {
					stockHistory = datastoreService.getStockHistory(stock.getSymbol(),30);
				} catch (Exception e) {
					return null;
				}
				for (StockDto stockDto : stockHistory) {
					Stock stockStatus = fromDto(stockDto);
					float value = stockStatus.getBid()*stockStatus.getStockQuantity();

					Date date = stockStatus.getDate();
					Float total = map.get(date);
					if(total == null) {
						total = value;
					}else {
						total += value;
					}

					map.put(date, value);
				}
			}
		}

		PortfolioTotalStatus[] ret = new PortfolioTotalStatus[map.size()];

		int index = 0;
		//create dto objects
		for (Date date : map.keySet()) {
			ret[index] = new PortfolioTotalStatus(date, map.get(date));
			index++;
		}

		//sort by date ascending.
		Arrays.sort(ret);

		return ret;
	}



	/**
	 * add stock if it in nasdaq.
	 */
	@Override
	public void addStock(String symbol) throws PortfolioException {
		try {
			StockDto stock = MarketService.getInstance().getStock(symbol);
			portfolio.addStock(new Stock(stock));

		} catch (SymbolNotFoundInNasdaq e) {

		}
	}

	/**
	 * buy stock with the required quantity
	 */
	@Override
	public void buyStock(String symbol, int quantity) throws PortfolioException {
		portfolio.buyStock(symbol, quantity);
	}

	/**
	 * sell stock with the given quantity
	 */
	@Override
	public void sellStock(String symbol, int quantity)
			throws PortfolioException {
		portfolio.sellStock(symbol, quantity);

	}

	/**
	 * remove stock from portfolio
	 */
	@Override
	public void removeStock(String symbol) throws PortfolioException {
		portfolio.removeStock(symbol);

	}

	/**
	 * fromDto - get stock from Data Transfer Object
	 * 
	 * @param stockDto
	 * @return Stock
	 */
	private Stock fromDto(StockDto stockDto) {
		Stock newStock = new Stock();

		newStock.setSymbol(stockDto.getSymbol());
		newStock.setAsk(stockDto.getAsk());
		newStock.setBid(stockDto.getBid());
		newStock.setDate(stockDto.getDate());
		newStock.setStockQuantity(stockDto.getQuantity());
		if (stockDto.getRecommendation() != null)
			newStock.setRecommendation(Portfolio.ALGO_RECOMMANDATION
					.valueOf(stockDto.getRecommendation()));

		return newStock;
	}

	/**
	 * toDtoList - convert List of Stocks to list of Stock DTO
	 * 
	 * @param stocks
	 * @return stockDto
	 */
	private List<StockDto> toDtoList(List<Stock> stocks) {

		List<StockDto> ret = new ArrayList<StockDto>();

		for (Stock stockStatus : stocks) {
			ret.add(toDto(stockStatus));
		}

		return ret;
	}
	
	/**
	 * toDto - covert Stock to Stock DTO
	 * @param inStock
	 * @return
	 */
	private StockDto toDto(StockInterface inStock) {
		if (inStock == null) {
			return null;
		}
		
		Stock stock = (Stock) inStock;
		return new StockDto(stock.getSymbol(), stock.getAsk(), stock.getBid(), 
				stock.getDate(), stock.getStockQuantity(), stock.getRecommendation().name());
	}
	
	/**
	 * toDto - converts Portfolio to Portfolio DTO
	 * @param portfolio
	 * @return
	 */
	private PortfolioDto toDto(Portfolio portfolio) {
		StockDto[] array = null;
		StockInterface[] stocks = portfolio.getStocks();
		if(stocks != null) {
			array = new StockDto[stocks.length];
			for (int i = 0; i < stocks.length; i++) {
				array[i] = toDto(stocks[i]);
			}
		}
		return new PortfolioDto(portfolio.getTitle(), portfolio.getBalance(), array);
	}

}