package ntbd.projekt;

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

import ntbd.projekt.encje.Adres;
import ntbd.projekt.encje.Konduktor;
import ntbd.projekt.encje.Lokomotywa;
import ntbd.projekt.encje.Motorniczy;
import ntbd.projekt.encje.Pociag;
import ntbd.projekt.encje.Polaczenie;
import ntbd.projekt.encje.Przystanek;
import ntbd.projekt.encje.Wagon;

public class Zapytania {
    private static PersistenceManager pm;
    private static Transaction tx;

    public static void main(String[] args) {
        try {
            pm = getPM();
            tx = pm.currentTransaction();
            if (args[0].equals("adres")) {
                getAdresy(pm);
            } else if (args[0].equals("konduktor")) {
                getKonduktorzy(pm);
            } else if (args[0].equals("lokomotywa")) {
                getLokomotywy(pm);
            } else if (args[0].equals("motorniczy")) {
                getMotorniczowie(pm);
            } else if (args[0].equals("pociag")) {
                getPociagi(pm);
            } else if (args[0].equals("polaczenie")) {
                getPolaczenia(pm);
            } else if (args[0].equals("przystanek")) {
                getPrzystanki(pm);
            } else if (args[0].equals("wagon")) {
                getWagony(pm);
            }
            pm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getWagony(PersistenceManager pm2) {
        tx = pm.currentTransaction();
        tx.begin();

        Query query = pm.newQuery(Wagon.class);
        List<Wagon> results = (List<Wagon>) query.execute();
        for (Wagon w : results) {
            System.out.println(w);
        }
        tx.commit();
    }

    private static void getPrzystanki(PersistenceManager pm2) {
        tx = pm.currentTransaction();
        tx.begin();

        Query query = pm.newQuery(Przystanek.class);
        List<Przystanek> results = (List<Przystanek>) query.execute();
        for (Przystanek p : results) {
            System.out.println(p);
        }
        tx.commit();
    }

    private static void getPolaczenia(PersistenceManager pm2) {
        tx = pm.currentTransaction();
        tx.begin();

        Query query = pm.newQuery(Polaczenie.class);
        List<Polaczenie> results = (List<Polaczenie>) query.execute();
        for (Polaczenie p : results) {
            System.out.println(p);
        }
        tx.commit();
    }

    private static void getPociagi(PersistenceManager pm2) {
        tx = pm.currentTransaction();
        tx.begin();

        Query query = pm.newQuery(Pociag.class);
        List<Pociag> results = (List<Pociag>) query.execute();
        for (Pociag p : results) {
            System.out.println(p);
        }
        tx.commit();
    }

    private static void getMotorniczowie(PersistenceManager pm2) {
        tx = pm.currentTransaction();
        tx.begin();

        Query query = pm.newQuery(Motorniczy.class);
        List<Motorniczy> results = (List<Motorniczy>) query.execute();
        for (Motorniczy m : results) {
            System.out.println(m);
        }
        tx.commit();
    }

    private static void getLokomotywy(PersistenceManager pm2) {
        tx = pm.currentTransaction();
        tx.begin();

        Query query = pm.newQuery(Lokomotywa.class);
        List<Lokomotywa> results = (List<Lokomotywa>) query.execute();
        for (Lokomotywa l : results) {
            System.out.println(l);
        }
        tx.commit();
    }

    private static void getKonduktorzy(PersistenceManager pm2) {
        tx = pm.currentTransaction();
        tx.begin();

        Query query = pm.newQuery(Konduktor.class);
        List<Konduktor> results = (List<Konduktor>) query.execute();
        for (Konduktor k : results) {
            System.out.println(k);
        }
        tx.commit();
    }

    private static void getAdresy(PersistenceManager pm) {
        tx = pm.currentTransaction();
        tx.begin();

        Query query = pm.newQuery(Adres.class);
        List<Adres> results = (List<Adres>) query.execute();
        for (Adres a : results) {
            System.out.println(a);
        }
        tx.commit();
    }

    private static PersistenceManager getPM() throws IOException {
        Properties properties = new Properties();
        InputStream is = Zapytania.class.getClassLoader().getResourceAsStream(
                "datanucleus.properties");
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