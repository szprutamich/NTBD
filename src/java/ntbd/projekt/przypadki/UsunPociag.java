package ntbd.projekt.przypadki;

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

import ntbd.projekt.encje.Pociag;
import ntbd.projekt.encje.Wagon;

public class UsunPociag {

    private static PersistenceManager pm;
    private static Transaction tx;

    public static void main(String[] args) {
        try {
            pm = getPM();
            tx = pm.currentTransaction();
            Scanner odczyt = new Scanner(System.in);
            System.out.println("Usun pociagi po: ");
            System.out.println("\t1\tNumerze");
            System.out.println("\t2\tFirmie");
            int typ = 0;
            boolean poprawny;
            do {
                poprawny = true;
                try {
                    typ = Integer.parseInt(odczyt.nextLine());
                    System.out.println(typ);
                } catch (NumberFormatException e) {
                    poprawny = false;
                }
                if (!poprawny || typ <= 0 || typ > 2)
                    System.out
                            .println("Niepoprawna wartosc. Wprawadz jeszcze raz.");
            } while (!poprawny || typ <= 0 || typ > 2);

            tx.begin();
            Query query = pm.newQuery(Pociag.class);
            List<Pociag> wynik = null;
            switch (typ) {
            case 1:
                System.out.println("Wprowadz nr pociagu:");
                int nr = 0;
                do {
                    poprawny = true;
                    try {
                        nr = Integer.parseInt(odczyt.nextLine());
                    } catch (NumberFormatException e) {
                        poprawny = false;
                    }
                } while (!poprawny);
                System.out.println(nr);

                query.setFilter("numer == :nr");
                wynik = (List<Pociag>) query.execute(nr);
                break;
            case 2:
                System.out.println("Wprowadz nazwe firmy:");
                String nazwa;
                nazwa = odczyt.nextLine();
                tx.begin();
                query = pm.newQuery(Pociag.class);
                query.setFilter("firma == :nazwa");
                wynik = (List<Pociag>) query.execute(nazwa);
                break;
            }
            tx.commit();
            int ile = wynik != null ? wynik.size() : 0;
            for (Pociag p : wynik) {
                usun(p);
            }
            System.out.println("Usunieto " + ile + " pociagow.");
            pm.close();
            odczyt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void usun(Pociag p) {
        if (p.getLokomotywa() != null) {
            p.getLokomotywa().setPociag(null);
        }
        for (Wagon w : p.getWagony())
            w.setPociag(null);
        p.setWagony(null);
        if (p.getPolaczenie() != null) {
            p.getPolaczenie().removePociag(p);
        }
        p.setPolaczenie(null);
        deleteObjects(p, pm);
    }

    private static PersistenceManager getPM() throws IOException {
        Properties properties = new Properties();
        InputStream is = DodajPociag.class.getClassLoader()
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

    public static void deleteObjects(Pociag objects, PersistenceManager pm) {
        pm.deletePersistentAll(objects);
    }
}