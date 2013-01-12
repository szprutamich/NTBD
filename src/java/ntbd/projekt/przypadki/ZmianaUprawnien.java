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

import ntbd.projekt.encje.Motorniczy;

public class ZmianaUprawnien {

    private static PersistenceManager pm;
    private static Transaction tx;

    public static void main(String[] args) {
        try {
            Scanner odczyt = new Scanner(System.in);
            pm = getPM();
            String pesel;
            System.out.println("Podaj PESEL osoby do zmiany uprawnien: ");            
            pesel = odczyt.nextLine();
            System.out.println(pesel);
            System.out.println("Podaj nowe uprawnienia:");
            String uprawnienia = "";
            uprawnienia = odczyt.nextLine();
            edytujUpr(pesel, uprawnienia, pm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void edytujUpr(String pesel, String upr,
            PersistenceManager pm) {
        tx = pm.currentTransaction();
        tx.begin();
        Query query = pm.newQuery(Motorniczy.class);
        query.setFilter("pesel == :pesel");
        List<Motorniczy> result = (List<Motorniczy>) query.execute(pesel);
        tx.commit();
        if (result.size() > 0) {
            for (Motorniczy r : result)
                r.setUprawnienia(upr);
            System.out.println("Zmodyfikowano.");
        } else
            System.out
                    .println("Nic nie wybrano. Czy na pewno wybrales motorniczego?");
        pm.makePersistentAll(result);
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