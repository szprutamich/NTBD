package ntbd.projekt.zestawienia;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.jdo.*;

import ntbd.projekt.encje.*;

public class motorniczy_na_trasie {

  private static PersistenceManager pm;
  private static Transaction tx; 

  public static void main(String[] args) {
    try {
      pm = getPM();
      tx = pm.currentTransaction();
      System.out.println("Wybierz pierwsze miasto:");
      Scanner m1 = new Scanner(System.in);
      String miasto1 = m1.nextLine();
      System.out.println("Miasto pierwsze: " + miasto1);
      System.out.println("Wybierz drugie miasto:");
      m1 = new Scanner(System.in);
      String miasto2 = m1.nextLine();
      System.out.println("Miasto drugie: " + miasto2);

      PolaczeniaMiedzymiastowe(pm, miasto1, miasto2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private static void PolaczeniaMiedzymiastowe(PersistenceManager pm, String miasto1, String miasto2)
  {
      tx = pm.currentTransaction();
        tx.begin();
        Query query = pm.newQuery(Motorniczy.class);
		query.setFilter("(lokomotywa.pociag.polaczenie.skad == ':miasto1' && lokomotywa.pociag.polaczenie.dokad == ':miasto2') || (lokomotywa.pociag.polaczenie.skad == ':miasto2' && lokomotywa.pociag.polaczenie.dokad == ':miasto1')");
        List<Motorniczy> wynik = (List<Motorniczy>) query.execute(miasto1, miasto2);
        tx.commit();
        int nr = 1;
        if(wynik.size()==0)
            System.out.println("Nie znaleziono zadnego motorniczego spelniajacego dane wymogi.");
        else
        {
            System.out.println("Motorniczy kursujacy miedzy " + miasto1 + " a " + miasto2 + ":");
            for(Motorniczy m : wynik)
            {
                System.out.println("\t" + nr + " " + m.getImie() + " " + m.getNazwisko() + " " + m.getPesel());
                nr++;
            }
        }
  }
  
  private static PersistenceManager getPM() throws IOException	{
	Properties properties = new Properties();
	InputStream is=motorniczy_na_trasie.class.getClassLoader().getResourceAsStream("datanucleus.properties");
	if (is == null) {
	   throw new FileNotFoundException("Could not find datanucleus.propertiesjpox.properties file that defines the Datanucles persistence setup.");
	}
	properties.load(is);
	PersistenceManagerFactory pmfactory = JDOHelper.getPersistenceManagerFactory(properties);
	return pmfactory.getPersistenceManager();
  }

}