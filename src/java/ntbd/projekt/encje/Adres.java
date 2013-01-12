package ntbd.projekt.encje;

import java.util.HashSet;
import java.util.Set;

public class Adres {
    private String miejscowosc;
    private String kod;
    private String ulica;
    private String nrDomu;
    private String nrMieszkania;
    private String poczta;
    private Set<Osoba> osoby;

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getNrDomu() {
        return nrDomu;
    }

    public void setNrDomu(String nrDomu) {
        this.nrDomu = nrDomu;
    }

    public String getNrMieszkania() {
        return nrMieszkania;
    }

    public void setNrMieszkania(String nrMieszkania) {
        this.nrMieszkania = nrMieszkania;
    }

    public String getPoczta() {
        return poczta;
    }

    public void setPoczta(String poczta) {
        this.poczta = poczta;
    }

    public Set<Osoba> getOsoby() {
        return osoby;
    }

    public void setOsoby(Set<Osoba> osoby) {
        this.osoby = osoby;
    }
    
    public void removeOsoba(Osoba o) {
        osoby.remove(o);
    }

    public void addOsoba(Osoba o) {
        if (osoby == null) {
            osoby = new HashSet<Osoba>();
        }
        osoby.add(o);
    }

    public String toString() {
        return String.format("%s, ul. %s %s/%s, %s %s", miejscowosc, ulica,
                nrDomu, nrMieszkania, kod, poczta);
    }
}