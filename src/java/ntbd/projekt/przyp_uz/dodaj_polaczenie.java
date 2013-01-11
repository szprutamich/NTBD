package ntbd.projekt.przyp_uz;

import java.io.*;
import java.util.Properties;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.jdo.*;

import ntbd.projekt.encje.*;

public class dodaj_polaczenie {

  private static PersistenceManager pm;
  private static Transaction tx; 

  public static void main(String[] args) {
    try {
      pm = getPM();
      tx = pm.currentTransaction();
      System.out.println("Podaj skad rusza pociag: ");
      Scanner scan = new Scanner(System.in);
      String skad = scan.nextLine();
      System.out.println("Stacja poczatkowa: " + skad);
      System.out.println("Podaj dokad jedzie pociag: ");
      scan = new Scanner(System.in);
      String dokad = scan.nextLine();
      System.out.println("Stacja koncowa: " + dokad);
      DodajPolaczenie(pm, skad, dokad);

      pm.close();
	  
	
		} catch (Exception e) {
		  e.printStackTrace();
		}
  }
  
  private static void DodajPolaczenie(PersistenceManager pm, String skad, String dokad)
  {        
        System.out.println("Wybierz typ polaczenia:");
        System.out.println("\t1\tRegionalne");
        System.out.println("\t2\tKrajowe");
        System.out.println("\t3\tMiedzynarodowe");
      int typ = 0;
      do
      {
        Scanner t = new Scanner(System.in);
        typ = Integer.parseInt(t.nextLine());
        if(typ > 4 && typ < 0)
            System.out.println("Podales nr spoza zakresu. Sprobuj ponownie.");
      } while(typ > 4 && typ < 0);
      String typ_pol = "";
      switch(typ)
      {
          case 1:
              typ_pol = "regionalne";
              break;
          case 2:
              typ_pol = "krajowe";
              break;
          case 3:
              typ_pol = "miedzynarodowe";
              break;              
      }
      System.out.println("Wybrany typ polaczenia: " + typ_pol);
      
        System.out.println("Wybierz ilosc pociagow, ktore maja byc dodane: ");
        int ilosc = 0;
        Random il_prz = new Random();
        int ilosc_przystankow = il_prz.nextInt(20);
        Scanner il = new Scanner(System.in);
        ilosc = Integer.parseInt(il.nextLine());
        tx.begin();
        Query query = pm.newQuery(Pociag.class);
		query.setFilter("polaczenie == null");
		query.setRange(0,ilosc);
        List<Pociag> wynik =  (List<Pociag>) query.execute();
        query = pm.newQuery(Przystanek.class);
		query.setRange(0,ilosc_przystankow);
        List<Przystanek> wynik3 =  (List<Przystanek>) query.execute();
        tx.commit();
        
      if(wynik.size() == 0)
          System.out.println("Nie ma zadnych nieprzypisanych pociagow.");
      else
      {
        Polaczenie pol = new Polaczenie();
        pol.setSkad(skad);
        pol.setDokad(dokad);
        pol.setTyp(typ_pol);
        for(Pociag p : wynik)
        {
            pol.addPociag(p);
            p.setPolaczenie(pol);
            System.out.println("Dodano pociag o numerze " + p.getNumer());
        }
        if(wynik3.size() != 0)
        {
            int il_przyst = 0;
            for(Przystanek p : wynik3)
            {
                pol.addPrzystanek(p);
                p.addPolaczenie(pol);
                il_przyst++;
            }
            System.out.println("Dodano " + il_przyst + " przystankow.");
        }
        pm.makePersistentAll(pol);
      }
  }

  private static PersistenceManager getPM() throws IOException	{
	Properties properties = new Properties();
	InputStream is=dodaj_polaczenie.class.getClassLoader().getResourceAsStream("datanucleus.properties");
	if (is == null) {
	   throw new FileNotFoundException("Could not find datanucleus.propertiesjpox.properties file that defines the Datanucles persistence setup.");
	}
	properties.load(is);
	PersistenceManagerFactory pmfactory = JDOHelper.getPersistenceManagerFactory(properties);
	return pmfactory.getPersistenceManager();
  }

}