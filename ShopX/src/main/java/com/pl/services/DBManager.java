package com.pl.services;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.pl.projectfiles.*;


public class DBManager {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement addMangaToCustomerStmt;
	private PreparedStatement deleteAllMangaFromCustomerStmt;
	private PreparedStatement getMangaCustomerStmt;
	private PreparedStatement deleteAllCustomerMangasStmt;
	
	public DBManager() {
		
		Properties props = new Properties();
		
		try {
			
			try {	
				props.load(ClassLoader.getSystemResourceAsStream("jdbc.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}		

		
			conn = DriverManager.getConnection(props.getProperty("url"));
			stmt = conn.createStatement();
			boolean customer_mangaTableExists = false;

			ResultSet rs = conn.getMetaData().getTables(null, null, null, null);

			while(rs.next()) {
				if("client_product".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					customer_mangaTableExists = true;
					break;
				}
			}


			if(!customer_mangaTableExists) {
				stmt.executeUpdate("" +
						"CREATE TABLE client_product(customer_id int, manga_id int, " +
						"CONSTRAINT client_id_fk FOREIGN KEY (client_id) REFERENCES client (id), " +
						"CONSTRAINT product_id_fk FOREIGN KEY (product_id) REFERENCES product (id))" +
						"");
			}

			addMangaToCustomerStmt = conn.prepareStatement("" +
					"INSERT INTO client_product (client_id, product_id) VALUES (?, ?)" +
					"");

			deleteAllMangaFromCustomerStmt = conn.prepareStatement("" +
					"DELETE FROM client_product WHERE client_id = ?" +
					"");
			
			getMangaCustomerStmt = conn.prepareStatement("SELECT Manga.title, Manga.price, Manga.mangatype FROM product, client_product WHERE client_id = ? and product_id = product.id");

			deleteAllCustomerMangasStmt = conn.prepareStatement("DELETE FROM client_product");			

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	public Connection getConnection() {
		return conn;
	}	

	public void addMangaToCustomer(List<Integer> listCustomerId, List<Integer> listMangaId) {
		try {
			for (Integer customerID : listCustomerId) {
				for (Integer mangaID : listMangaId) {
					addMangaToCustomerStmt.setInt(1, customerID);
					addMangaToCustomerStmt.setInt(2, mangaID);
					addMangaToCustomerStmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteAllMangaFromCustomer (List<Integer> listCustomerId) {
		try {
			for (Integer customerID : listCustomerId) {
				deleteAllMangaFromCustomerStmt.setInt(1, customerID);
				deleteAllMangaFromCustomerStmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}			


	public List<Product> getCustomerManga (List<Integer> listCustomerId) {
		List<Product> mangas = new ArrayList<Product>();
		
		try {
			for (Integer customerID : listCustomerId)
			{
				getMangaCustomerStmt.setInt(1, customerID);
				ResultSet rs = getMangaCustomerStmt.executeQuery();
				while (rs.next()) 
				{
				
					if(rs.getString("mangatype").equalsIgnoreCase("mystery"))
						mangas.add(new Product(rs.getString("title"), rs.getInt("price")));
					else if(rs.getString("mangatype").equalsIgnoreCase("action"))
						mangas.add(new Product(rs.getString("title"), rs.getInt("price")));
					else if(rs.getString("mangatype").equalsIgnoreCase("shounen"))
						mangas.add(new Product(rs.getString("title"), rs.getInt("price")));

				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mangas;

	
	}
	
	public void deleteAllCustomerMangas ()
	{
		try
		{
			deleteAllCustomerMangasStmt.executeUpdate();
		}
		catch (SQLException e)
		{

			e.printStackTrace();
		}

	}	
	

}