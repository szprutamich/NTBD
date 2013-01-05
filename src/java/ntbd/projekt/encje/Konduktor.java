package ntbd.projekt.encje;

import java.util.HashSet;
import java.util.Set;

public class Konduktor extends Osoba {
    private Set<Polaczenie> polaczenia;

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
        return String.format("%s %s, pesel: %s, ur. %s", nazwisko, imie, pesel,
                dataUrodzenia);
    }
}