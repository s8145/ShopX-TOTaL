package com.pl.projectfiles;

import com.pl.exception.*;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;






@NamedNativeQueries({
	@NamedNativeQuery(
	name = "Client.all",
	query = "select * from client",
        resultClass = Client.class
	)
})
@Entity
@Table(name="Client")
public class Client {

	@Id
	@GeneratedValue
	private int ID;	
	
	public String name;
	public String surname;

	@OneToMany(mappedBy="owner",cascade=javax.persistence.CascadeType.PERSIST)
	public List<Product> products = new ArrayList<Product>();

	
	public Client()
	{}
	
	private static Logger logger = Logger.getLogger(Client.class);

	public void addProduct(Product product) throws Cena_mniej_niz_zero {
		if (product.getPrice()>0)
			products.add(product);
	}	
	
	public int getId() {
		return ID;
	}

	public void setId(int ID) {
		this.ID = ID;
	}	
	
	public Client(String name, String surname) {
		this.name = name;
		this.surname = surname;
		this.products = new ArrayList<Product>();
	}

	public Client(String name, String surname, List<Product> products) {
		this.name = name;
		this.surname = surname;
		this.products= products;
	}


	public String printCustomers() {
		String tempStorePrintCustomers = name + " " + surname;
		System.out.println(tempStorePrintCustomers);
		return tempStorePrintCustomers;
	}


	public void printMangas() {
			for(Product m : this.products) {
				m.printMangas();
			}
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
		
	}

	public List<Product> getMangasList() {
		return products;
	}
	
	public void removeManga(Product manga) {	
		products.remove(manga);
		System.out.println("Produkt: " + manga.getTitle() + " | removed");
	}


	public void removeAllMangas() {
		products.clear();
		System.out.println("Usunieto wszystkie produkty");
		
	}	

	public Product findMangaByTitle(String title) {
		for (Product manga : products) {
			if (manga.getTitle().equals(title)) {
				return manga;
			}
		}
		return null;
	}	
	

	
	
	public void printSearchResult (List<Product> list){
		for (Product manga : list)
			System.out.println("\t" + manga.getTitle() + " | Price: " + manga.getPrice() + "ZL" );
	}	
	
	public void editMangaPrice (List<Product> list, int price) throws Cena_mniej_niz_zero {
		for (Product manga : list) {
			manga.setPrice(price);
		}
	}
	

	}
