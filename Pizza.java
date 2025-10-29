public class Pizza {
    private String navn;
    private int pris;

    public Pizza(String navn, int pris) {
        this.navn = navn;
        this.pris = pris;
    }

    public String getNavn() { return navn; }
    public int getPris() { return pris; }

    @Override
    public String toString() {
        return navn + " (" + pris + " kr)";
    }
}

