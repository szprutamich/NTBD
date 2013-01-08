package ntbd.projekt.przyp_uz;

import java.io.*;
import java.util.Properties;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.jdo.*;

import ntbd.projekt.encje.*;

public class zmiana_uprawnien {

  private static PersistenceManager pm;
  private static Transaction tx; 

  public static void main(String[] args) {
    try {
      pm = getPM();
      String pesel;
      System.out.println("Podaj PESEL osoby do zmiany uprawnien: ");
      Scanner odczyt = new Scanner(System.in);
      pesel = odczyt.nextLine();
      System.out.println(pesel);
      System.out.println("Podaj nowe uprawnienia:");
      String uprawnienia = "";
      odczyt = new Scanner(System.in);
      uprawnienia = odczyt.nextLine();
      EdytujUpr(pesel,uprawnienia, pm);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private static void EdytujUpr(String pesel, String upr, PersistenceManager pm)
  {
      tx = pm.currentTransaction();
        tx.begin();
        Query query = pm.newQuery(Motorniczy.class);
		query.setFilter("pesel == :pesel");
        List<Motorniczy> result =  (List<Motorniczy>) query.execute(pesel);
        tx.commit();
        if(result.size() > 0)
        {
            for(Motorniczy r : result)
                r.setUprawnienia(upr);
            System.out.println("Zmodyfikowano.");
        }
        else
            System.out.println("Nic nie wybrano. Czy na pewno wybrales motorniczego?");
        pm.makePersistentAll(result);
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