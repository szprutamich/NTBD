package ntbd.projekt.zestawienia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import ntbd.projekt.encje.Polaczenie;

public class PolaczniaPrzezMiasto {

    private static PersistenceManager pm;
    private static Transaction tx;

    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            pm = getPM();
            tx = pm.currentTransaction();
            System.out.println("Wybierz typ pociagu:");
            System.out.println("\t1\tPospieszny");
            System.out.println("\t2\tNormalny");
            System.out.println("\t3\tExpress");
            int typ = 0;
            boolean poprawny = false;
            do {
                try {
                    poprawny = true;
                    typ = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e) {
                    poprawny = false;
                }
                if (!poprawny || typ > 3 || typ < 1)
                    System.out
                            .println("Podales nr spoza zakresu. Sprobuj ponownie.");
            } while (!poprawny || typ > 3 || typ < 1);
            String typ_poc = "";
            switch (typ) {
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
            miasto = scan.nextLine();
            System.out.println("Wybrane miasto: " + miasto);
            tx.begin();
            wyszukajPol(pm, miasto, typ_poc);
            tx.commit();
            scan.close();
            pm.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void wyszukajPol(PersistenceManager pm, String miasto,
            String typ) {
        Query query = pm.newQuery(Polaczenie.class);
        query.setFilter("((przystanki.contains(p) && p.miasto == :miasto) || skad == :miasto || dokad == :miasto) && (pociagi.contains(poc) && poc.typ == :typ)");
        List<Polaczenie> wynik = (List<Polaczenie>) query.execute(miasto, typ);
        System.out.println();

        if (wynik.size() == 0)
            System.out.println("Nie znaleziono ¿adnego polaczenia.");
        else {
            System.out.println("Pociagi typu " + typ + " przejezdzajace przez "
                    + miasto + ":");
            int nr = 1;
            for (Polaczenie p : wynik) {
                System.out.println("\t" + nr + ". Polaczenie z " + p.getSkad()
                        + " do " + p.getDokad() + " typu " + p.getTyp());
                nr++;
            }
        }
    }

    private static PersistenceManager getPM() throws IOException {
        Properties properties = new Properties();
        InputStream is = StarePociagi.class.getClassLoader()
                .getResourceAsStream("datanucleus.properties");
        if (is == null) {
            throw new FileNotFoundException(
                    "Could not find datanucleus.propertiesjpox.properties file that defines the Datanucles persistence setup.");
        }
        properties.load(is);
        PersistenceManagerFactory pmfactory = JDOHelper
                .getPersistenceManagerFactory(properties);
        return pmfactory.getPersistenceManager();
    }
}