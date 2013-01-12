package ntbd.projekt.przypadki;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import ntbd.projekt.encje.Konduktor;
import ntbd.projekt.encje.Motorniczy;
import ntbd.projekt.encje.Osoba;
import ntbd.projekt.encje.Polaczenie;

public class UsunEmerytow {

    private static PersistenceManager pm;
    private static Transaction tx;

    public static void main(String[] args) {
        try {
            pm = getPM();
            tx = pm.currentTransaction();

            int lat = 67;
            Date d = new Date();
            d.setYear(d.getYear() - lat);

            System.out.println("Usuwanie emerytow.");
            tx.begin();
            Query query = pm.newQuery(Osoba.class);
            query.setFilter("dataUrodzenia < :d");
            List<Osoba> results = (List<Osoba>) query.execute(d);
            tx.commit();
            for (Osoba o : results) {
                o.getAdres().removeOsoba(o);
                o.setAdres(null);
                if (o instanceof Konduktor) {
                    Set<Polaczenie> polaczenia = ((Konduktor) o)
                            .getPolaczenia();
                    for (Polaczenie p : polaczenia) {
                        p.removeKonduktor((Konduktor) o);
                    }
                    ((Konduktor) o).setPolaczenia(null);
                } else if (o instanceof Motorniczy) {
                    ((Motorniczy)o).getLokomotywa().setMotorniczy(null);
                    ((Motorniczy)o).setLokomotywa(null);
                }
            }
            deleteObjects(results, pm);
            System.out.println("Usunieto " + results.size() + " osob.");
            pm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void deleteObjects(List<Osoba> objects, PersistenceManager pm) {
        pm.deletePersistentAll(objects);
    }
}