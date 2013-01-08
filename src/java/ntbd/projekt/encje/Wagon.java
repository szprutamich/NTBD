package ntbd.projekt.encje;

public class Wagon {
    private String rodzaj;
    private int numer;
    private int maxObciazenie;
    private String klasa;
    private Pociag pociag;

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public int getMaxObciazenie() {
        return maxObciazenie;
    }

    public void setMaxObciazenie(int maxObciazenie) {
        this.maxObciazenie = maxObciazenie;
    }

    public String getKlasa() {
        return klasa;
    }

    public void setKlasa(String klasa) {
        this.klasa = klasa;
    }

    public Pociag getPociag() {
        return pociag;
    }

    public void setPociag(Pociag pociag) {
        this.pociag = pociag;
    }

    public String toString() {
        return String.format("Wagon %s, numer: %d, maksymalne obciazenie: %d",
                rodzaj, numer, maxObciazenie);
    }
}