package com.pl.projectfiles;

import org.apache.log4j.*;

import com.pl.exception.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;





@NamedNativeQueries({
	@NamedNativeQuery(
	name = "findStockByStockCodeNativeSQL",
	query = "select * from product",
        resultClass = Product.class
	)
})
@Entity
public class Product {

	
	@Id
	@GeneratedValue
	public int ID;
	
	@OneToOne
	@JoinColumn(name="Product_ID")
	private Product product;	
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	private Client owner;
	
	public String title;
	public int price;

	
	private static Logger logger = Logger.getLogger(Product.class);

	
	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product manga) {
		this.product = manga;
	}	

	public int getId() {
		return ID;
	}

	public void setId(int ID) {
		this.ID = ID;
	}
	
	
	public Product(String title, int price) {
		this.title = title;
		this.price = price;
	}

	public String printMangas() {
		String tempStorePrintMangas = "\t" + title + " - " + price + "zl";
		System.out.println(tempStorePrintMangas);
		return tempStorePrintMangas;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	
	public int getPrice() {
		return price;
	}

	public int setPrice(int price) throws Cena_mniej_niz_zero {
		if(price < 0) {
			throw new Cena_mniej_niz_zero("Cena ponizej zera!");
		}else{
			logger.info("New manga: " + title + "added to list" + " Price:" + price);
			return this.price = price;
		}
	}

}

