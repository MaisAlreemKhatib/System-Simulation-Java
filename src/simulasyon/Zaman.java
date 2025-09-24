package simulasyon;

public class Zaman {
    private int saat;

    public Zaman() {
        this.saat = 0;  // Simülasyon başlangıcı
    }

    public void ilerle(int saatSayisi) {
        this.saat += saatSayisi;
    }

    public int getSaat() {
        return this.saat;
    }

    public void yazdir() {
        System.out.println("Simülasyon Zamanı: " + saat + " saat");
    }
}
