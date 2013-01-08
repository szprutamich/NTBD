package ntbd.projekt.encje;

import java.util.HashSet;
import java.util.Set;

public class Pociag {
    private int numer;
    private String typ;
    private String firma;
    private Polaczenie polaczenie;
    private Lokomotywa lokomotywa;
    private Set<Wagon> wagony;

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Polaczenie getPolaczenie() {
        return polaczenie;
    }

    public void setPolaczenie(Polaczenie polaczenie) {
        this.polaczenie = polaczenie;
    }

    public Lokomotywa getLokomotywa() {
        return lokomotywa;
    }

    public void setLokomotywa(Lokomotywa lokomotywa) {
        this.lokomotywa = lokomotywa;
    }

    public Set<Wagon> getWagony() {
        return wagony;
    }

    public void setWagony(Set<Wagon> wagony) {
        this.wagony = wagony;
    }

    public void addWagon(Wagon w) {
        if (wagony == null) {
            wagony = new HashSet<Wagon>();
        }
        wagony.add(w);
    }

    public String toString() {
        return String.format("Pociag: %s, firma: %s, numer: %d", typ, firma,
                numer);
    }
}