package com.offcn.bookstore.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.offcn.bookstore.dao.AccountDAO;
import com.offcn.bookstore.dao.BookDAO;
import com.offcn.bookstore.dao.TradeDAO;
import com.offcn.bookstore.dao.TradeItemDAO;
import com.offcn.bookstore.dao.UserDAO;
import com.offcn.bookstore.dao.impl.AccountDAOIml;
import com.offcn.bookstore.dao.impl.BookDAOImpl;
import com.offcn.bookstore.dao.impl.TradeDAOImpl;
import com.offcn.bookstore.dao.impl.TradeItemDAOImpl;
import com.offcn.bookstore.dao.impl.UserDAOImpl;
import com.offcn.bookstore.db.JDBCUtils;
import com.offcn.bookstore.domain.Book;
import com.offcn.bookstore.domain.ShoppingCart;
import com.offcn.bookstore.domain.ShoppingCartItem;
import com.offcn.bookstore.domain.Trade;
import com.offcn.bookstore.domain.TradeItem;
import com.offcn.bookstore.web.CriteriaBook;
import com.offcn.bookstore.web.Page;

public class BookService {
	
	private BookDAO bookDAO = new BookDAOImpl();
	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDAO.getPage(criteriaBook);
	}

	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart sc) {
		Book book = bookDAO.getBook(id);
		
		if(book != null){
			sc.addBook(book);
			return true;
		}
		
		return false;
	}

	public void removeItemFromShoppingCart(ShoppingCart sc, int id) {
		sc.removeItem(id);
	}

	public void clearShoppingCart(ShoppingCart sc) {
		sc.clear();
	}

	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.updateItemQuantity(id, quantity);
	}
	
	private AccountDAO accountDAO = new AccountDAOIml();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();

	//ҵ�񷽷�. 
	public void cash(ShoppingCart shoppingCart, String username,
			String accountId) {

		//1. ���� mybooks ���ݱ���ؼ�¼�� salesamount �� storenumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
		int i = 10 / 1;
		
		//2. ���� account ���ݱ�� balance
		accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		
		//3. �� trade ���ݱ����һ����¼
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDAO.getUser(username).getUserId());
		tradeDAO.insert(trade);
		
		//4. �� tradeitem ���ݱ���� n ����¼
		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sci: shoppingCart.getItems()){
			TradeItem tradeItem = new TradeItem();
			
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);
		
		//5. ��չ��ﳵ
		shoppingCart.clear();
	}
	
}
