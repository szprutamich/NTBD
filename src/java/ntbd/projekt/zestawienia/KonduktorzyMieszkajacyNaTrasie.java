package ntbd.projekt.zestawienia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import ntbd.projekt.encje.Konduktor;

public class KonduktorzyMieszkajacyNaTrasie {

  private static PersistenceManager pm;
  private static Transaction tx; 

  public static void main(String[] args) {
    try {
      pm = getPM();
      tx = pm.currentTransaction();
      
      wypiszKonduktorow(pm);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private static void wypiszKonduktorow(PersistenceManager pm)
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
	InputStream is=KonduktorzyMieszkajacyNaTrasie.class.getClassLoader().getResourceAsStream("datanucleus.properties");
	if (is == null) {
	   throw new FileNotFoundException("Could not find datanucleus.propertiesjpox.properties file that defines the Datanucles persistence setup.");
	}
	properties.load(is);
	PersistenceManagerFactory pmfactory = JDOHelper.getPersistenceManagerFactory(properties);
	return pmfactory.getPersistenceManager();
  }

}