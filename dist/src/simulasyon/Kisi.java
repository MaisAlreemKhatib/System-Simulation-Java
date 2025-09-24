package simulasyon;

public class Kisi {
    private String isim;
    private int yas;
    private int kalanOmur;
    private String uzayAraci;

    public Kisi(String isim, int yas, int kalanOmur, String uzayAraci) {
        this.isim = isim;
        this.yas = yas;
        this.kalanOmur = kalanOmur;
        this.uzayAraci = uzayAraci;
    }

    public boolean hayattaMi() {
        return kalanOmur > 0;
    }

    public void omurAzalt() {
        if (kalanOmur > 0) {
            kalanOmur--;
        }
    }

    public String getUzayAraci() {
        return uzayAraci;
    }

    public void yazdir() {
        System.out.println("Kisi: " + isim + " - Yaş: " + yas + " - Kalan Ömür: " + kalanOmur +
                           " - Uzay Aracı: " + uzayAraci);
    }

    public String getIsim() {
        return isim;
    }

    public int getKalanOmur() {
        return kalanOmur;
    }
}
