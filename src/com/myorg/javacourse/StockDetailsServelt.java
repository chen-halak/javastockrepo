package com.myorg.javacourse;

import java.io.IOException;

import javax.servlet.http.*;

import com.myorg.javacourse.model.Stock;

@SuppressWarnings("serial")
public class StockDetailsServelt extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		Stock PIH = new Stock();
		Stock AAL = new Stock();
		Stock CAAS = new Stock();
		PIH.setSymbol("PIH");
		AAL.setSymbol("AAL");
		CAAS.setSymbol("CAAS");
		PIH.setAsk(13.1F);
		AAL.setAsk(5.78F);
		CAAS.setAsk(32.2F);
		PIH.setBid(12.4F);
		AAL.setBid(5.5F);
		CAAS.setBid(31.5F);
		
		PIH.setDate("11/15/2014");
		AAL.setDate("11/15/2014");
		CAAS.setDate("11/15/2014");
		

		resp.getWriter().println(PIH.getHtmlDescription());
		resp.getWriter().println(AAL.getHtmlDescription());
		resp.getWriter().println(CAAS.getHtmlDescription());
		
	}

	

}