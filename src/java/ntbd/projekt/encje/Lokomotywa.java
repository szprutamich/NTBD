package ntbd.projekt.encje;

public class Lokomotywa {
    private int predkoscMax;
    private int moc;
    private int rokProdukcji;
    private String typ;
    private Motorniczy motorniczy;
    private Pociag pociag;

    public int getPredkoscMax() {
        return predkoscMax;
    }

    public void setPredkoscMax(int predkoscMax) {
        this.predkoscMax = predkoscMax;
    }

    public int getMoc() {
        return moc;
    }

    public void setMoc(int moc) {
        this.moc = moc;
    }

    public int getRokProdukcji() {
        return rokProdukcji;
    }

    public void setRokProdukcji(int rokProdukcji) {
        this.rokProdukcji = rokProdukcji;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public Motorniczy getMotorniczy() {
        return motorniczy;
    }

    public void setMotorniczy(Motorniczy motorniczy) {
        this.motorniczy = motorniczy;
    }

    public Pociag getPociag() {
        return pociag;
    }

    public void setPociag(Pociag pociag) {
        this.pociag = pociag;
    }

    public String toString() {
        return String.format(
                "Lokomotywa %s, predkosc: %d, moc: %d, rok produkcji: %d",
                typ, predkoscMax, moc, rokProdukcji);
    }
}