package ntbd.projekt.zestawienia;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.jdo.*;

import ntbd.projekt.encje.*;

public class konduktorzy_mieszkajacy_na_trasie {

  private static PersistenceManager pm;
  private static Transaction tx; 

  public static void main(String[] args) {
    try {
      pm = getPM();
      tx = pm.currentTransaction();
      
      WypiszKonduktorow(pm);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private static void WypiszKonduktorow(PersistenceManager pm)
  {
      tx = pm.currentTransaction();
        tx.begin();
        Query query = pm.newQuery(Konduktor.class);
		query.setFilter("polaczenia.contains(p) && (p.skad == adres.miejscowosc && p.dokad == adres.miejscowosc)");
        List<Konduktor> wynik = (List<Konduktor>) query.execute();
        tx.commit();
        int nr = 1;
        if(wynik.size()==0)
            System.out.println("Nie znaleziono zadnego konduktora spelniajacego dane wymogi.");
        else
        {
            System.out.println("Konduktor mieszkajacy w miescie, przez ktore kursuje:");
            for(Konduktor k : wynik)
            {
                System.out.println("\t" + nr + " " + k.getImie() + " " + k.getNazwisko() + " " + k.getPesel());
                nr++;
            }
        }
  }
  
  private static PersistenceManager getPM() throws IOException	{
	Properties properties = new Properties();
	InputStream is=konduktorzy_mieszkajacy_na_trasie.class.getClassLoader().getResourceAsStream("datanucleus.properties");
	if (is == null) {
	   throw new FileNotFoundException("Could not find datanucleus.propertiesjpox.properties file that defines the Datanucles persistence setup.");
	}
	properties.load(is);
	PersistenceManagerFactory pmfactory = JDOHelper.getPersistenceManagerFactory(properties);
	return pmfactory.getPersistenceManager();
  }

}