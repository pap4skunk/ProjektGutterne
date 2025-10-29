public class Pizza {
    private String navn;
    private int pris;

    public Pizza(String etNavn, int enPris) {
        navn = etNavn;
        pris = enPris;
    }

    public String getNavn() {
        return navn;
    }

    public int getPris() {
        return pris;
    }
}
