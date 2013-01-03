package ntbd.projekt.encje;

import java.util.HashSet;
import java.util.Set;

public class Polaczenie {
    private String skad;
    private String dokad;
    private String typ;
    private Set<Konduktor> konduktorzy;
    private Set<Przystanek> przystanki;
    private Set<Pociag> pociagi;

    public String getSkad() {
        return skad;
    }

    public void setSkad(String skad) {
        this.skad = skad;
    }

    public String getDokad() {
        return dokad;
    }

    public void setDokad(String dokad) {
        this.dokad = dokad;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Set<Konduktor> getKonduktorzy() {
        return konduktorzy;
    }

    public void setKonduktorzy(Set<Konduktor> konduktorzy) {
        this.konduktorzy = konduktorzy;
    }

    public Set<Przystanek> getPrzystanki() {
        return przystanki;
    }

    public void setPrzystanki(Set<Przystanek> przystanki) {
        this.przystanki = przystanki;
    }

    public Set<Pociag> getPociagi() {
        return pociagi;
    }

    public void setPociagi(Set<Pociag> pociagi) {
        this.pociagi = pociagi;
    }

    public void addKonduktor(Konduktor k) {
        if (konduktorzy == null) {
            konduktorzy = new HashSet<Konduktor>();
        }
        konduktorzy.add(k);
    }
}