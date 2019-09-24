package com.shop.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.shop.models.Product;
import com.shopdb.conn.JDBCUtil;

@Service
public class ProductService {
	Connection conn = null;
	
	public List<Product> getAllProducts(){
		ArrayList<Product> listOfProducts = new ArrayList<Product>();
//		listOfProducts.add(new Product(001,"IPHONEX",1200.83));
//		listOfProducts.add(new Product(002,"IPHONX",1203.00));
		
		try{			
			//get connection
			conn = JDBCUtil.getConnection();
 
//			//create statement
//			statement = conn.createStatement();
// 
//			//execute query
//			statement.execute(query);
			
			Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT * FROM product");
//	         System.out.println("id  name    price");
	         
	         while (rs.next()) {
	            int id = rs.getInt("pid");
	            String name = rs.getString("name");
	            Double price = (double) rs.getInt("price");
	            int stockLeft = rs.getInt("stock");
	            listOfProducts.add(new Product(id,name,price,stockLeft));
	            
//	            System.out.println(id+"   "+name+"    "+price);
	         }
			//close connection
			conn.close();
 
			System.out.println("Table records retrieved");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return listOfProducts;
	}
	
	public Product getProductById(Integer id) {

		Predicate<Product> byId = p -> p.getId().equals(id);
		return filterProducts(byId);
	}

	public Product filterProducts(Predicate<Product> strategy) {
		return getAllProducts().stream().filter(strategy).findFirst().orElse(null);
}
}
