package ntbd.projekt.przyp_uz;

import java.io.*;
import java.util.Collection;
import java.util.Properties;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.jdo.*;

import ntbd.projekt.encje.*;

public class usun_pociag {

  private static PersistenceManager pm;
  private static Transaction tx; 

  public static void main(String[] args) {
    try {
      pm = getPM();
      tx = pm.currentTransaction();
      System.out.println("Usun pociagi po: ");
      System.out.println("\t1\tNumerze");
      System.out.println("\t2\tFirmie");
      
      int typ;
      do{
      Scanner odczyt = new Scanner(System.in);
      typ = Integer.parseInt(odczyt.nextLine());
            System.out.println(typ);
            if(typ <= 0 || typ > 2)
                System.out.println("Niepoprawna wartosc. Wprawadz jeszcze raz.");
      }
      while(typ <= 0 || typ > 2);
      
      switch(typ) {
          case 1: 
              usunNumer();
              break;
          case 2:
              usunFirme();
              break;
      }

      pm.close();
	  
	
		} catch (Exception e) {
		  e.printStackTrace();
		}
  }
  
  private static void usunNumer()
  {
      System.out.println("Wprowadz numer pociagu:");
      int nr;
      Scanner odczyt = new Scanner(System.in);
      nr = Integer.parseInt(odczyt.nextLine());
            System.out.println(nr);
      tx.begin();
        Query query = pm.newQuery(Pociag.class);
		query.setFilter("numer == :nr");
        List<Pociag> wynik =  (List<Pociag>) query.execute(nr);
      tx.commit();
      int ilosc = 0;
        for(Pociag p : wynik)
        {
            usun(p);
            ilosc++;
        }
        System.out.println("Usunieto :" + ilosc + " elementow.");
  }
  
  private static void usunFirme()
  {
      System.out.println("Wprowadz nazwe firmy:");
      String nazwa;
      Scanner odczyt = new Scanner(System.in);
      nazwa = odczyt.nextLine();
      tx.begin();
        Query query = pm.newQuery(Pociag.class);
		query.setFilter("firma == :nazwa");
        List<Pociag> wynik =  (List<Pociag>) query.execute(nazwa);
      tx.commit();
      int ilosc = 0;
        for(Pociag p : wynik)
        {
            usun(p);
            ilosc++;
        }
        System.out.println("Usunieto :" + ilosc + " elementow.");
  }
  
  private static void usun(Pociag p)
  {
      if(p.getLokomotywa() != null)
      {
        p.getLokomotywa().setPociag(null);
        for(Wagon w : p.getWagony())
            w.setPociag(null);
      }
        deleteObjects(p, pm);
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
  
   public static void deleteObjects(Pociag objects, PersistenceManager pm) {
        pm.deletePersistentAll(objects);
    }
}