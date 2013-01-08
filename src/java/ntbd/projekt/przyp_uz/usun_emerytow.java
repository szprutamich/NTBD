package ntbd.projekt.przyp_uz;

import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.jdo.*;

import ntbd.projekt.encje.*;

public class usun_emerytow {

  private static PersistenceManager pm;
  private static Transaction tx; 

  
  public static void main(String[] args) {
    try {
      pm = getPM();
      tx = pm.currentTransaction();
      
      int lat = 67;
      Date d = new Date();
      d.setYear(d.getYear() - lat);
      
      System.out.println("Usuwanie emerytow.");
      tx.begin();
        Query query = pm.newQuery(Osoba.class);
		query.setFilter("dataUrodzenia < :d");
        List<Osoba> results =  (List<Osoba>) query.execute(d);
      tx.commit();
      int ilosc = 0;
      for(Osoba o : results)
      {
          o.setAdres(null);
          ilosc++;
      }
      System.out.println("Usunieto :" + ilosc + " elementow.");
      deleteObjects(results, pm);
      pm.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
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
  
   public static void deleteObjects(List<Osoba> objects, PersistenceManager pm) {
        pm.deletePersistentAll(objects);
    }
}