package com.pl.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.pl.projectfiles.*;
import com.pl.exception.*;

public class ClientTest {
	
	private static Client testCustomer;
	private static Product manga;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testCustomer = new Client("Johnny", "Bravo");
		manga = new Product("TEST", 111);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testCustomer.addProduct(manga); 
	}

	@After
	public void tearDown() throws Exception {
		testCustomer.removeAllMangas();
	}

	@Test
	public void test_addManga() throws Cena_mniej_niz_zero {		
		assertTrue(testCustomer.getMangasList().size() > 0);
		assertSame(manga, testCustomer.getMangasList().get(0));
		assertNotNull(testCustomer.getMangasList());
	}	
	

	
	@Test
	public void test_getName() {
		assertTrue(testCustomer.getName().equals("Johnny"));
	}	

	@Test
	public void test_setName() {
		testCustomer.setName("JohnnyTest");
		assertTrue(testCustomer.getName().equals("JohnnyTest"));
	}	

	@Test
	public void test_getSurname() {
		assertTrue(testCustomer.getSurname().equals("Bravo"));
	}	

	@Test
	public void test_setSurname() {
		testCustomer.setSurname("BravoTest");
		assertTrue(testCustomer.getSurname().equals("BravoTest"));
	}	
	
	@Test
	public void test_getMangasList() {
		assertNotNull(testCustomer.getMangasList());
	}
	
	@Test
	public void test_removeManga() throws Cena_mniej_niz_zero {
		testCustomer.removeManga(testCustomer.findMangaByTitle("TEST"));
		assertTrue(testCustomer.getMangasList().size() == 0); 
	}
	
	@Test
	public void test_removeAllMangas() throws Cena_mniej_niz_zero {
		testCustomer.removeAllMangas();
		assertTrue(testCustomer.getMangasList().size() == 0);
	}

	@Test
	public void test_findMangaByTitle() {
		assertSame(testCustomer.findMangaByTitle("TEST"), testCustomer.getMangasList().get(0));
	}
	
	@Test(expected=Cena_mniej_niz_zero.class, timeout=100)
	public void testPriceLessThanZeroException() throws Cena_mniej_niz_zero {
		manga.setPrice(-8);
	}
}

