package ntbd.projekt;

import java.io.File;
import java.util.Scanner;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

public class WypelnijDane extends Rozszerzenie {

    public static void main(String[] args) {
        try {
            Rozszerzenie ex = new Rozszerzenie();

            Scanner scan = new Scanner(new File("data.txt"));

            ex.setMiejscowosci(scan.nextLine().split(";"));
            ex.setImiona(scan.nextLine().split(";"));
            ex.setNazwiska(scan.nextLine().split(";"));
            ex.setUprawnienia(scan.nextLine().split(";"));
            ex.setUlice(scan.nextLine().split(";"));
            ex.setTypyPolaczen(scan.nextLine().split(";"));
            ex.setFirmy(scan.nextLine().split(";"));
            ex.setTypyPociagow(scan.nextLine().split(";"));
            ex.setTypyLokomotyw(scan.nextLine().split(";"));
            ex.setRodzajeWagonow(scan.nextLine().split(";"));

            scan.close();

            PersistenceManager pm = ex.getPm();
            Transaction tx = pm.currentTransaction();
            tx.begin();
            ex.deleteObjects(pm);
            tx.commit();

            tx.begin();
            int count = 1000;
            ex.createObjects(pm, count);
            tx.commit();

            pm.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}