package simulasyon;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UzayAraci {
    private String adi;
    private String durum;
    private String cikisGezegeni;
    private String varisGezegeni;
    private int hedefeKalanSaat;
    private String hedefeVaracagiTarih;
    private int cikisTarihiInt;

    private ArrayList<Kisi> yolcular = new ArrayList<>();

    public UzayAraci(String adi, String durum, String cikisGezegeni, String varisGezegeni, int hedefeKalanSaat) {
        this.adi = adi;
        this.durum = durum;
        this.cikisGezegeni = cikisGezegeni;
        this.varisGezegeni = varisGezegeni;
        this.hedefeKalanSaat = hedefeKalanSaat;
        hesaplaHedefTarihi(20250420); // Başlangıçta örnek bir çıkış tarihi ile hesap
    }

    // Saat azaltma işlemi
    public void saatAzalt() {
        if (durum.equals("İMHA") || durum.equals("Hedefe Ulaştı")) {
            return; // Bu durumda zaman ilerlemez
        }

        if (hedefeKalanSaat > 0) {
            hedefeKalanSaat--;
        }

        // Yolcuların ömrü azalır, ölenler çıkarılır
        yolcular.removeIf(k -> {
            k.omurAzalt();
            return !k.hayattaMi();
        });

        // Tüm yolcular öldüyse: Araç imha edilir
        if (yolcular.isEmpty()) {
            durum = "İMHA";
            hedefeKalanSaat = 0;
            hedefeVaracagiTarih = "--";
            return;
        }

        // Hedefe ulaşıldıysa
        if (hedefeKalanSaat == 0) {
            durum = "Hedefe Ulaştı";
            hesaplaHedefTarihi(cikisTarihiInt); // Gerçek çıkış tarihiyle hesapla
            return;
        }

        // Her durumda hedef tarih güncellenir
        hesaplaHedefTarihi(cikisTarihiInt);
    }

    // Hedefe varacağı tarihi hesaplar
    public void hesaplaHedefTarihi(int cikisTarihiYyyymmdd) {
        if (hedefeKalanSaat <= 0) {
            this.hedefeVaracagiTarih = "--";
            return;
        }

        int yil = cikisTarihiYyyymmdd / 10000;
        int ay = (cikisTarihiYyyymmdd / 100) % 100;
        int gun = cikisTarihiYyyymmdd % 100;

        LocalDate cikisDate = LocalDate.of(yil, ay, gun);
        LocalDate hedefTarih = cikisDate.plusDays(hedefeKalanSaat / 24);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.hedefeVaracagiTarih = hedefTarih.format(formatter);
    }

    // Kişi ekleme
    public void addKisi(Kisi kisi) {
        yolcular.add(kisi);
    }

    // Hayatta kalan yolcu sayısı
    public int canliYolcuSayisi() {
        return (int) yolcular.stream().filter(Kisi::hayattaMi).count();
    }

    // Getter – Setter
    public String getAdi() {
        return adi;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    public String getCikisGezegeni() {
        return cikisGezegeni;
    }

    public String getVarisGezegeni() {
        return varisGezegeni;
    }

    public int getHedefeKalanSaat() {
        return hedefeKalanSaat;
    }

    public String getHedefeVaracagiTarih() {
        return hedefeVaracagiTarih;
    }

    public ArrayList<Kisi> getYolcular() {
        return yolcular;
    }

    public int getCikisTarihiInt() {
        return cikisTarihiInt;
    }

    public void setCikisTarihiInt(int cikisTarihiInt) {
        this.cikisTarihiInt = cikisTarihiInt;
    }
}
