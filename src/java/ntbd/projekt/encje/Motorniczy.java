package ntbd.projekt.encje;

public class Motorniczy extends Osoba {
    private String uprawnienia;
    private Lokomotywa lokomotywa;

    public String getUprawnienia() {
        return uprawnienia;
    }

    public void setUprawnienia(String uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    public Lokomotywa getLokomotywa() {
        return lokomotywa;
    }

    public void setLokomotywa(Lokomotywa lokomotywa) {
        this.lokomotywa = lokomotywa;
    }

    public String toString() {
        return String.format("%s %s, uprawnienia: %s,  pesel: %s, ur. %s",
                nazwisko, imie, uprawnienia, pesel, dataUrodzenia);
    }
}