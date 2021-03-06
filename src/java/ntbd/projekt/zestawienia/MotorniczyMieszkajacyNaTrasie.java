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

import ntbd.projekt.encje.Motorniczy;

public class MotorniczyMieszkajacyNaTrasie {

    private static PersistenceManager pm;
    private static Transaction tx;

    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            pm = getPM();
            tx = pm.currentTransaction();
            System.out.println("Wybierz pierwsze miasto:");
            String miasto1 = scan.nextLine();
            System.out.println("Miasto pierwsze: " + miasto1);
            System.out.println("Wybierz drugie miasto:");
            String miasto2 = scan.nextLine();
            System.out.println("Miasto drugie: " + miasto2);

            tx.begin();
            polaczeniaMiedzymiastowe(pm, miasto1, miasto2);
            tx.commit();
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void polaczeniaMiedzymiastowe(PersistenceManager pm,
            String miasto1, String miasto2) {
        Query query = pm.newQuery(Motorniczy.class);
        query.setFilter("(lokomotywa.pociag.polaczenie.skad == :miasto1 && lokomotywa.pociag.polaczenie.dokad == :miasto2) || (lokomotywa.pociag.polaczenie.skad == :miasto2 && lokomotywa.pociag.polaczenie.dokad == :miasto1)");
        List<Motorniczy> wynik = (List<Motorniczy>) query.execute(miasto1,
                miasto2);
        if (wynik.size() == 0)
            System.out
                    .println("Nie znaleziono zadnego motorniczego spelniajacego dane wymogi.");
        else {
            System.out.println("Motorniczy kursujacy miedzy " + miasto1 + " a "
                    + miasto2 + ":");
            int nr = 1;
            for (Motorniczy m : wynik) {
                System.out.println("\t" + nr + "\t" + m.getImie() + " "
                        + m.getNazwisko() + " " + m.getPesel());
                nr++;
            }
        }
    }

    private static PersistenceManager getPM() throws IOException {
        Properties properties = new Properties();
        InputStream is = MotorniczyMieszkajacyNaTrasie.class.getClassLoader()
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