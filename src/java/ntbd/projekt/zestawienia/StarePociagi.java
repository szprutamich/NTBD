package ntbd.projekt.zestawienia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import ntbd.projekt.encje.Pociag;

public class StarePociagi {

    private static PersistenceManager pm;
    private static Transaction tx;

    public static void main(String[] args) {
        try {
            pm = getPM();
            tx = pm.currentTransaction();
            System.out.println("Podaj maksymalny wiek lokomotywy:");
            int wiek = 0;
            Scanner w = new Scanner(System.in);
            wiek = Integer.parseInt(w.nextLine());
            System.out.println("wiek: " + wiek);
            Date d = new Date();
            int obecna_data = 1900 + d.getYear();
            int min_data = obecna_data - wiek;

            wyswietlPociagi(pm, min_data);
            pm.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void wyswietlPociagi(PersistenceManager pm, int data) {
        tx = pm.currentTransaction();
        tx.begin();
        Query query = pm.newQuery(Pociag.class);
        query.setFilter("lokomotywa.rokProdukcji < :data && (wagony.cointains(w) && w.rodzaj != 'towarowy')");
        List<Pociag> wynik = (List<Pociag>) query.execute(data);
        System.out.println();
        tx.commit();
        int nr = 1;
        if (wynik.size() == 0)
            System.out.println("Nie ma tak starych pociagow.");
        else {
            System.out
                    .println("Pociagi z lokomotywami o roku produkcji sprzed "
                            + data + " roku:");
            for (Pociag p : wynik) {
                System.out.println("\t" + nr + ". Pociag numer " + p.getNumer()
                        + " typu " + p.getTyp() + " firmy " + p.getFirma());
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