package com.pl.projectfiles;

import com.pl.exception.*;
import com.pl.report.SampleReport;
import com.pl.services.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main {

	static final Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws Cena_mniej_niz_zero {

		PropertyConfigurator.configure("log4j.properties");
		logger.debug("Sample debug message");
		logger.info("Sample info message");
		logger.warn("Sample warn message");
		logger.error("Sample error message");
		logger.fatal("Sample fatal message");
		SampleReport obj = new SampleReport();
		obj.generateReport();


	
	
		List<Product> products = new ArrayList<Product>();


		
		

		Client customer1 = new Client("Waldek", "Kiepski", products);
			try {
				customer1.addProduct(new Product("plyta CD", -1));
				customer1.addProduct(new Product("plyta DVD", 2));
				customer1.addProduct(new Product("kabel USB", 8));
				customer1.addProduct(new Product("slon", 10000));

			}catch(Cena_mniej_niz_zero e){
				logger.error(e);
			}

			customer1.printCustomers();
			customer1.printMangas();
			System.out.println("Ilosc produktow " + products.size());
			System.out.println("\n");


	
	
	//HIBERNATE		
			
			List<Client> owners= new ArrayList<Client>();
			
			SessionFactory sessionFactory= new Configuration().configure().buildSessionFactory();
			Session session=sessionFactory.openSession();
			session.beginTransaction();			
			
			/*session.save(p);
			for(Car car :cars)
			{
				session.save(car);
			}*/
			session.persist(customer1);

			
			
			
			
			
			
			List<Client> clients= session.getNamedQuery("Client.all").list();
			
			
			//session.save(garage2);
			//session.save(garage);
			session.get(Client.class, 1000);
			session.getTransaction().commit();
			int i=1;
			do
				{
				owners.add((Client)session.get(Client.class,i));
				i++;
				}
			while(session.get(Client.class,i)!=null);
			
			
			
			

			session.close();
			System.out.println(owners.size());
			
			for(Client client :clients)
			{
				System.out.print(client.getName());
			}	
		
			
			
	}


}