package ntbd.projekt.przyp_uz;

import java.io.*;
import java.util.Properties;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.jdo.*;

import ntbd.projekt.encje.*;

public class dodaj_pociag {

  private static PersistenceManager pm;
  private static Transaction tx; 

  public static void main(String[] args) {
    try {
      pm = getPM();
      tx = pm.currentTransaction();
      WolneLokomotywy();


      pm.close();
	  
	
		} catch (Exception e) {
		  e.printStackTrace();
		}
  }
  
  private static void WolneLokomotywy()
  {
      tx = pm.currentTransaction();
        tx.begin();
        Query query = pm.newQuery(Lokomotywa.class);
		query.setFilter("pociag == null");
		query.setResult("count(moc)");
        Object ilosc_wolnych =  query.execute();
            System.out.println("Ilosc wolnych maszyn: " + ilosc_wolnych);
      System.out.println();
      long il_lok = ((Long)ilosc_wolnych).longValue();
        tx.commit();
      if(il_lok != 0)
      {
        tx.begin();
        Query query2 = pm.newQuery(Lokomotywa.class);
                  query2.setFilter("pociag == null");
                  query2.setRange(0,1);
          List<Lokomotywa> result = (List<Lokomotywa>) query2.execute();

        tx.commit();
        Lokomotywa lok= new Lokomotywa();
        for (Lokomotywa l : result)
              lok = l;
        Zapytanie(pm,lok);
      }
      else
          System.out.println("Nie ma wolnych lokomotyw");
  }
  
  private static void Zapytanie(PersistenceManager pm, Lokomotywa lok)
  {
        tx = pm.currentTransaction();
        tx.begin();
        int typ = 0;
        System.out.println("Wybierz typ pociagu:");
        System.out.println("\t1\tPospieszny");
        System.out.println("\t2\tNormalny");
        System.out.println("\t3\tExpress");
      do{
      Scanner odczyt = new Scanner(System.in);
      typ = Integer.parseInt(odczyt.nextLine());
            System.out.println(typ);
            if(typ <= 0 || typ > 3)
                System.out.println("Niepoprawna wartosc. Wprawadz jeszcze raz.");
      }
      while(typ <= 0 || typ > 3);
      
      String typ_poc = "";
      switch(typ) {
          case 1: 
              typ_poc = "pospieszny";
              break;
          case 2:
              typ_poc = "normalny";
              break;
          case 3:
              typ_poc = "express";
              break;
      }
    System.out.println(typ_poc);
    System.out.println("Wprowadz nazwe firmy:");
    String firma = "";
    Scanner odczyt = new Scanner(System.in);
    firma = odczyt.nextLine();
    System.out.println(firma);
    System.out.println("Wprowadz numer pociagu:");
    int nr = 0;
    Scanner odczyt2 = new Scanner(System.in);
      nr = Integer.parseInt(odczyt2.nextLine());
            System.out.println(nr);
        Pociag p = new Pociag();
        p.setFirma(firma);
        p.setNumer(nr);
        p.setTyp(typ_poc);
        
       p.setLokomotywa(lok);
       lok.setPociag(p);
    System.out.println("Utworzono " + typ_poc + " pociag o numerze " + nr);
    pm.makePersistentAll(p);

        tx.commit();
  }

  private static PersistenceManager getPM() throws IOException	{
	Properties properties = new Properties();
	InputStream is=dodaj_pociag.class.getClassLoader().getResourceAsStream("datanucleus.properties");
	if (is == null) {
	   throw new FileNotFoundException("Could not find datanucleus.propertiesjpox.properties file that defines the Datanucles persistence setup.");
	}
	properties.load(is);
	PersistenceManagerFactory pmfactory = JDOHelper.getPersistenceManagerFactory(properties);
	return pmfactory.getPersistenceManager();
  }

}