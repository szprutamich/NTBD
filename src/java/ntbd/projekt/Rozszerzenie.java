package ntbd.projekt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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

public class Rozszerzenie {

    protected String[] miejscowosci;
    protected String[] imiona;
    protected String[] nazwiska;
    protected String[] uprawnienia;
    protected String[] ulice;
    protected String[] typyPolaczen;
    protected String[] firmy;
    protected String[] typyPociagow;
    protected String[] typyLokomotyw;
    protected String[] rodzajeWagonow;

    protected PersistenceManager pm;
    protected Transaction tx;
    protected Query q;

    public void setPm(PersistenceManager pm) {
        this.pm = pm;
    }

    public Transaction getTx() {
        return tx;
    }

    public void setTx(Transaction tx) {
        this.tx = tx;
    }

    public Query getQ() {
        return q;
    }

    public void setQ(Query q) {
        this.q = q;
    }

    @SuppressWarnings("rawtypes")
    protected void deleteObjects(PersistenceManager pm) {
        q = pm.newQuery(Adres.class);
        Collection col1 = (Collection) q.execute();
        for (Iterator i = col1.iterator(); i.hasNext();) {
            Adres a = (Adres) (i.next());
            a.setOsoby(null);
        }

        q = pm.newQuery(Konduktor.class);
        Collection col2 = (Collection) q.execute();
        for (Iterator i = col2.iterator(); i.hasNext();) {
            Konduktor k = (Konduktor) (i.next());
            k.setPolaczenia(null);
            k.setAdres(null);
        }

        q = pm.newQuery(Lokomotywa.class);
        Collection col3 = (Collection) q.execute();
        for (Iterator i = col3.iterator(); i.hasNext();) {
            Lokomotywa l = (Lokomotywa) (i.next());
            l.setPociag(null);
            l.setMotorniczy(null);
        }

        q = pm.newQuery(Motorniczy.class);
        Collection col4 = (Collection) q.execute();
        for (Iterator i = col4.iterator(); i.hasNext();) {
            Motorniczy m = (Motorniczy) (i.next());
            m.setAdres(null);
            m.setLokomotywa(null);
        }

        q = pm.newQuery(Pociag.class);
        Collection col5 = (Collection) q.execute();
        for (Iterator i = col5.iterator(); i.hasNext();) {
            Pociag p = (Pociag) (i.next());
            p.setLokomotywa(null);
            p.setPolaczenie(null);
            p.setWagony(null);
        }

        q = pm.newQuery(Polaczenie.class);
        Collection col6 = (Collection) q.execute();
        for (Iterator i = col6.iterator(); i.hasNext();) {
            Polaczenie p = (Polaczenie) (i.next());
            p.setKonduktorzy(null);
            p.setPociagi(null);
            p.setPrzystanki(null);
        }

        q = pm.newQuery(Przystanek.class);
        Collection col7 = (Collection) q.execute();
        for (Iterator i = col7.iterator(); i.hasNext();) {
            Przystanek p = (Przystanek) (i.next());
            p.setPolaczenia(null);
        }

        q = pm.newQuery(Wagon.class);
        Collection col8 = (Collection) q.execute();
        for (Iterator i = col8.iterator(); i.hasNext();) {
            Wagon w = (Wagon) (i.next());
            w.setPociag(null);
        }

        deleteObjects(col1, pm, "adresow");
        deleteObjects(col2, pm, "konduktorow");
        deleteObjects(col3, pm, "lokomotyw");
        deleteObjects(col4, pm, "motorniczych");
        deleteObjects(col5, pm, "pociagow");
        deleteObjects(col6, pm, "polaczen");
        deleteObjects(col7, pm, "przystankow");
        deleteObjects(col8, pm, "wagonow");
    }

    @SuppressWarnings("rawtypes")
    private void deleteObjects(Collection objects, PersistenceManager pm,
            String type) {
        System.out
                .println(String.format("Usuwanie %d %s", objects.size(), type));
        pm.deletePersistentAll(objects);
        System.out
                .println(String.format("Usunieto %d %s", objects.size(), type));
    }

    protected void createObjects(PersistenceManager pm, int ilosc) {
        List<Adres> adresy = new LinkedList<Adres>();
        List<Konduktor> konduktorzy = new LinkedList<Konduktor>();
        List<Lokomotywa> lokomotywy = new LinkedList<Lokomotywa>();
        List<Motorniczy> motorniczowie = new LinkedList<Motorniczy>();
        List<Pociag> pociagi = new LinkedList<Pociag>();
        List<Polaczenie> polaczenia = new LinkedList<Polaczenie>();
        List<Przystanek> przystanki = new LinkedList<Przystanek>();
        List<Wagon> wagony = new LinkedList<Wagon>();

        Calendar c = Calendar.getInstance();

        Random rand = new Random();

        int iloscAdresow = ilosc;
        int iloscKonduktorow = ilosc;
        int iloscLokomotyw = ilosc;
        int iloscMotorniczych = ilosc;
        int iloscPociagow = ilosc;
        int iloscPolaczen = ilosc;
        int iloscPrzystankow = ilosc;
        int iloscWagonow = ilosc;

        for (int i = 0; i < iloscAdresow; i++) {
            String miejscowosc = miejscowosci[rand.nextInt(miejscowosci.length)];

            Adres a = new Adres();
            a.setKod((rand.nextInt(90) + 10) + "-" + (rand.nextInt(900) + 100));
            a.setMiejscowosc(miejscowosc);
            a.setNrDomu(String.valueOf(rand.nextInt(1000)));
            a.setNrMieszkania(String.valueOf(rand.nextInt(100)));
            a.setPoczta(miejscowosc);
            a.setUlica(ulice[rand.nextInt(ulice.length)]);

            adresy.add(a);
        }

        for (int i = 0; i < iloscKonduktorow; i++) {
            String pesel = "";
            for (int j = 0; j < 11; j++) {
                pesel += rand.nextInt(10);
            }

            c.set(rand.nextInt(50) + 1940, rand.nextInt(12) + 1,
                    rand.nextInt(28) + 1);
            Date data = c.getTime();

            Konduktor k = new Konduktor();
            k.setDataUrodzenia(data);
            k.setImie(imiona[rand.nextInt(imiona.length)]);
            k.setNazwisko(nazwiska[rand.nextInt(nazwiska.length)]);
            k.setPesel(pesel);

            konduktorzy.add(k);
        }

        for (int i = 0; i < iloscLokomotyw; i++) {
            Lokomotywa l = new Lokomotywa();
            l.setMoc(rand.nextInt(5000) + 1000);
            l.setPredkoscMax(rand.nextInt(100) + 100);
            l.setRokProdukcji(rand.nextInt(50) + 1960);
            l.setTyp(typyLokomotyw[rand.nextInt(typyLokomotyw.length)]);

            lokomotywy.add(l);
        }

        for (int i = 0; i < iloscMotorniczych; i++) {
            String pesel = "";
            for (int j = 0; j < 11; j++) {
                pesel += rand.nextInt(10);
            }

            c.set(rand.nextInt(50) + 1940, rand.nextInt(12) + 1,
                    rand.nextInt(28) + 1);
            Date data = c.getTime();

            Motorniczy m = new Motorniczy();
            m.setDataUrodzenia(data);
            m.setImie(imiona[rand.nextInt(imiona.length)]);
            m.setNazwisko(nazwiska[rand.nextInt(nazwiska.length)]);
            m.setPesel(pesel);
            m.setUprawnienia(uprawnienia[rand.nextInt(uprawnienia.length)]);

            motorniczowie.add(m);
        }

        for (int i = 0; i < iloscPociagow; i++) {
            Pociag p = new Pociag();
            p.setFirma(firmy[rand.nextInt(firmy.length)]);
            p.setNumer(rand.nextInt(1000));
            p.setTyp(typyPociagow[rand.nextInt(typyPociagow.length)]);

            pociagi.add(p);
        }

        for (int i = 0; i < iloscPolaczen; i++) {
            Polaczenie p = new Polaczenie();
            p.setDokad(miejscowosci[rand.nextInt(miejscowosci.length)]);
            p.setSkad(miejscowosci[rand.nextInt(miejscowosci.length)]);
            p.setTyp(typyPolaczen[rand.nextInt(typyPolaczen.length)]);

            polaczenia.add(p);
        }

        for (int i = 0; i < iloscPrzystankow; i++) {
            String miejscowosc = miejscowosci[rand.nextInt(miejscowosci.length)];

            Przystanek p = new Przystanek();
            p.setMiasto(miejscowosc);
            p.setNazwa(String.format("Przystanek %s", miejscowosc));
            p.setNumer(rand.nextInt(100));

            przystanki.add(p);
        }

        for (int i = 0; i < iloscWagonow; i++) {
            Wagon w = new Wagon();
            w.setKlasa(String.valueOf(rand.nextInt(2) + 1));
            w.setMaxObciazenie(rand.nextInt(5000) + 1000);
            w.setNumer(rand.nextInt(1000));
            w.setRodzaj(rodzajeWagonow[rand.nextInt(rodzajeWagonow.length)]);

            wagony.add(w);
        }
        
        for (Konduktor k : konduktorzy) {
            Adres a = adresy.get(rand.nextInt(adresy.size()));
            k.setAdres(a);
            a.addOsoba(k);
            
            Polaczenie p = polaczenia.get(rand.nextInt(polaczenia.size()));
            // do 10 polaczen
            int ile = rand.nextInt(10);
            for (int i = 0; i <ile; i++) {
                k.addPolaczenie(p);
                p.addKonduktor(k);
            }
            
        }
        
        for (Motorniczy m : motorniczowie) {
            Adres a = adresy.get(rand.nextInt(adresy.size()));
            m.setAdres(a);
            a.addOsoba(m);
        }

        System.out.println("Tworzenie " + iloscAdresow + " adresow.");
        pm.makePersistentAll(adresy);
        System.out.println("Utworzono " + iloscAdresow + " adresow.");

        System.out.println("Tworzenie " + iloscKonduktorow + " konduktorow.");
        pm.makePersistentAll(konduktorzy);
        System.out.println("Utworzono " + iloscKonduktorow + " konduktorow.");

        System.out.println("Tworzenie " + iloscLokomotyw + " lokomotyw.");
        pm.makePersistentAll(lokomotywy);
        System.out.println("Utworzono " + iloscLokomotyw + " lokomotyw.");

        System.out.println("Tworzenie " + iloscMotorniczych + " motorniczych.");
        pm.makePersistentAll(motorniczowie);
        System.out.println("Utworzono " + iloscMotorniczych + " motorniczych.");

        System.out.println("Tworzenie " + iloscPociagow + " pocisgow.");
        pm.makePersistentAll(pociagi);
        System.out.println("Utworzono " + iloscPociagow + " pociagow.");

        System.out.println("Tworzenie " + iloscPolaczen + " polaczen.");
        pm.makePersistentAll(polaczenia);
        System.out.println("Utworzono " + iloscPolaczen + " polaczen.");

        System.out.println("Tworzenie " + iloscPrzystankow + " przystankow.");
        pm.makePersistentAll(przystanki);
        System.out.println("Utworzono " + iloscPrzystankow + " przystankow.");

        System.out.println("Tworzenie " + iloscWagonow + " wagonow.");
        pm.makePersistentAll(wagony);
        System.out.println("Utworzono " + iloscWagonow + " wagonow.");

    }

    protected PersistenceManager getPm() throws IOException {
        if (pm == null) {
            Properties properties = new Properties();
            InputStream is = Rozszerzenie.class.getClassLoader()
                    .getResourceAsStream("datanucleus.properties");
            if (is == null) {
                throw new FileNotFoundException(
                        "Could not find datanucleus.propertiesjpox.properties file that defines the Datanucles persistence setup.");
            }
            properties.load(is);
            PersistenceManagerFactory pmfactory = JDOHelper
                    .getPersistenceManagerFactory(properties);
            pm = pmfactory.getPersistenceManager();
        }
        return pm;
    }

    public void setMiejscowosci(String[] miejscowosci) {
        this.miejscowosci = miejscowosci;
    }

    public void setImiona(String[] imiona) {
        this.imiona = imiona;
    }

    public void setNazwiska(String[] nazwiska) {
        this.nazwiska = nazwiska;
    }

    public void setUprawnienia(String[] uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    public void setUlice(String[] ulice) {
        this.ulice = ulice;
    }

    public void setTypyPolaczen(String[] typyPolaczen) {
        this.typyPolaczen = typyPolaczen;
    }

    public void setFirmy(String[] firmy) {
        this.firmy = firmy;
    }

    public void setTypyPociagow(String[] typyPociagow) {
        this.typyPociagow = typyPociagow;
    }

    public void setTypyLokomotyw(String[] typyLokomotyw) {
        this.typyLokomotyw = typyLokomotyw;
    }

    public void setRodzajeWagonow(String[] rodzajeWagonow) {
        this.rodzajeWagonow = rodzajeWagonow;
    }
}