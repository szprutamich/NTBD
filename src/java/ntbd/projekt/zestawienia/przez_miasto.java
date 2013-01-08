package ntbd.projekt.zestawienia;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.jdo.*;

import ntbd.projekt.encje.*;

public class przez_miasto {

  private static PersistenceManager pm;
  private static Transaction tx; 

  public static void main(String[] args) {
    try {
      pm = getPM();
      tx = pm.currentTransaction();
      System.out.println("Wybierz typ pociagu:");
        System.out.println("\t1\tPospieszny");
        System.out.println("\t2\tNormalny");
        System.out.println("\t3\tExpress");
      int typ = 0;
      do
      {
        Scanner t = new Scanner(System.in);
        typ = Integer.parseInt(t.nextLine());
        if(typ > 4 && typ < 0)
            System.out.println("Podales nr spoza zakresu. Sprobuj ponownie.");
      } while(typ > 4 && typ < 0);
      String typ_poc = "";
      switch(typ)
      {
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
      System.out.println("Wybrany typ pociagu: " + typ_poc);
      System.out.println("Podaj nazwe miasta: ");
      String miasto = "";
      Scanner m = new Scanner(System.in);
      miasto = m.nextLine();
      
      System.out.println("Wybrane miasto: " + miasto);

      WyszukajPol(pm, miasto, typ_poc);
      pm.close();
	  
	
		} catch (Exception e) {
		  e.printStackTrace();
		}
  }
  
  private static void WyszukajPol(PersistenceManager pm, String miasto, String typ)
  {
      tx = pm.currentTransaction();
        tx.begin();
        Query query = pm.newQuery(Polaczenie.class);
		query.setFilter("((przystanki.contains(p) && p.miasto == ':miasto') || skad == ':miasto' || dokad == ':miasto') && (pociagi.contains(poc) && poc.typ == ':typ')");
        List<Polaczenie> wynik = (List<Polaczenie>) query.execute(miasto, typ);
        System.out.println();
        tx.commit();
        int nr = 1;
        if(wynik.size()==0)
            System.out.println("Nie znaleziono ¿adnego polaczenia.");
        else
        {
            System.out.println("Pociagi typu " + typ + " przejezdzajace przez " + miasto + ":");
            for(Polaczenie p : wynik)
            {
                System.out.println("\t" + nr + ". Polaczenie z " + p.getSkad() + " do " + p.getDokad() + " typu " + p.getTyp());
                nr++;
            }
        }
  }
  
  private static PersistenceManager getPM() throws IOException	{
	Properties properties = new Properties();
	InputStream is=stare_pociagi.class.getClassLoader().getResourceAsStream("datanucleus.properties");
	if (is == null) {
	   throw new FileNotFoundException("Could not find datanucleus.propertiesjpox.properties file that defines the Datanucles persistence setup.");
	}
	properties.load(is);
	PersistenceManagerFactory pmfactory = JDOHelper.getPersistenceManagerFactory(properties);
	return pmfactory.getPersistenceManager();
  }

}