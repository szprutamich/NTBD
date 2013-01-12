package ntbd.projekt.przypadki;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import ntbd.projekt.encje.Pociag;
import ntbd.projekt.encje.Polaczenie;
import ntbd.projekt.encje.Przystanek;

public class DodajPolaczenie {

    private static PersistenceManager pm;
    private static Transaction tx;

    public static void main(String[] args) {
        try {
            pm = getPM();
            tx = pm.currentTransaction();
            tx.begin();
            dodajPolaczenie(pm);
            tx.commit();
            pm.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dodajPolaczenie(PersistenceManager pm) {
        System.out.println("Podaj skad rusza pociag: ");
        Scanner scan = new Scanner(System.in);
        String skad = scan.nextLine();
        System.out.println("Stacja poczatkowa: " + skad);
        System.out.println("Podaj dokad jedzie pociag: ");
        String dokad = scan.nextLine();
        System.out.println("Stacja koncowa: " + dokad);
        System.out.println("Wybierz typ polaczenia:");
        System.out.println("\t1\tRegionalne");
        System.out.println("\t2\tKrajowe");
        System.out.println("\t3\tMiedzynarodowe");
        int typ = 0;
        boolean fail = false;
        do {
            try {
                fail = false;
                typ = Integer.parseInt(scan.nextLine());
                if (typ > 3 || typ < 1)
                    System.out
                            .println("Podales nr spoza zakresu. Sprobuj ponownie.");
            } catch (NumberFormatException e) {
                fail = true;
            }
        } while (fail || typ > 3 || typ < 1);
        String typ_pol = "";
        switch (typ) {
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

        Random il_prz = new Random();
        int ilosc_przystankow = il_prz.nextInt(20);

        System.out.println("Wybierz ilosc pociagow, ktore maja byc dodane: ");
        int ilosc = 0;
        boolean poprawny;
        do {
            poprawny = true;
            try {
                ilosc = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                poprawny = false;
            }
        } while (!poprawny);
        Query query = pm.newQuery(Pociag.class);
        query.setFilter("polaczenie == null");
        query.setRange(0, ilosc);
        List<Pociag> pociagi = (List<Pociag>) query.execute();
        query = pm.newQuery(Przystanek.class);
        query.setRange(0, ilosc_przystankow);
        List<Przystanek> przystanki = (List<Przystanek>) query.execute();

        if (pociagi.size() == 0)
            System.out.println("Nie ma zadnych nieprzypisanych pociagow.");
        else {
            Polaczenie pol = new Polaczenie();
            pol.setSkad(skad);
            pol.setDokad(dokad);
            pol.setTyp(typ_pol);
            for (Pociag p : pociagi) {
                pol.addPociag(p);
                p.setPolaczenie(pol);
                System.out.println("Dodano pociag o numerze " + p.getNumer());
            }
            if (przystanki.size() != 0) {
                for (Przystanek p : przystanki) {
                    pol.addPrzystanek(p);
                    p.addPolaczenie(pol);
                }
                System.out.println("Dodano " + przystanki.size() + " przystankow.");
            }
            pm.makePersistentAll(pol);
        }
        scan.close();
    }

    private static PersistenceManager getPM() throws IOException {
        Properties properties = new Properties();
        InputStream is = DodajPolaczenie.class.getClassLoader()
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