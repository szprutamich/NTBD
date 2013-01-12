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

import ntbd.projekt.encje.Lokomotywa;
import ntbd.projekt.encje.Pociag;

public class DodajPociag {

    private static PersistenceManager pm;
    private static Transaction tx;

    public static void main(String[] args) {
        try {
            pm = getPM();
            tx = pm.currentTransaction();
            wolneLokomotywy();

            pm.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void wolneLokomotywy() {
        tx = pm.currentTransaction();
        tx.begin();
        Query query = pm.newQuery(Lokomotywa.class);
        query.setFilter("pociag == null");
        List<Lokomotywa> result = (List<Lokomotywa>) query.execute();
        int ile_lok = result.size();
        System.out.println(String.format("Ilosc wolnych lokomotyw: %d \n",
                ile_lok));

        tx.commit();
        if (ile_lok != 0) {
            zapytanie(pm, result.get(0));
        } else {
            System.out.println("Nie ma wolnych lokomotyw");
        }
    }

    private static void zapytanie(PersistenceManager pm, Lokomotywa lok) {
        tx = pm.currentTransaction();
        tx.begin();
        int typ = 0;
        System.out.println("Wybierz typ pociagu:");
        System.out.println("\t1\tPospieszny");
        System.out.println("\t2\tNormalny");
        System.out.println("\t3\tExpress");
        Scanner odczyt = new Scanner(System.in);
        boolean fail = false;
        do {
            try {
                fail = false;
                typ = Integer.parseInt(odczyt.nextLine());
                System.out.println(typ);
                if (typ <= 0 || typ > 3)
                    System.out
                            .println("Niepoprawna wartosc. Wprawadz jeszcze raz.");
            } catch (NumberFormatException e) {
                fail = true;
            }
        } while (fail || typ <= 0 || typ > 3);

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
        System.out.println(typ_poc);
        System.out.println("Wprowadz nazwe firmy:");
        String firma = "";
        firma = odczyt.nextLine();
        System.out.println(firma);
        System.out.println("Wprowadz numer pociagu:");
        boolean poprawny;
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
        Pociag p = new Pociag();
        p.setFirma(firma);
        p.setNumer(nr);
        p.setTyp(typ_poc);
        p.setLokomotywa(lok);
        lok.setPociag(p);
        System.out.println("Utworzono " + typ_poc + " pociag o numerze " + nr);
        odczyt.close();
        pm.makePersistentAll(p);
        tx.commit();
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
}