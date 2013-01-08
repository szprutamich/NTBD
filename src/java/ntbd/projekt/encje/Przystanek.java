package ntbd.projekt.encje;

import java.util.HashSet;
import java.util.Set;

public class Przystanek {
    private String miasto;
    private int numer;
    private String nazwa;
    private Set<Polaczenie> polaczenia;

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Set<Polaczenie> getPolaczenia() {
        return polaczenia;
    }

    public void setPolaczenia(Set<Polaczenie> polaczenia) {
        this.polaczenia = polaczenia;
    }

    public void addPolaczenie(Polaczenie p) {
        if (polaczenia == null) {
            polaczenia = new HashSet<Polaczenie>();
        }
        polaczenia.add(p);
    }

    public String toString() {
        return String.format("%s w %s, numer: %d", nazwa, miasto, numer);
    }
}