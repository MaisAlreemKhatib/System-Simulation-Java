package simulasyon;

public class Gezegen {
    private String adi;
    private int nufus;
    private int tarih;
    private int saat;
    private int gunlukSaat; // Yeni eklendi

    public Gezegen(String adi, int gunlukSaat, int tarih) {
        this.adi = adi;
        this.gunlukSaat = gunlukSaat;
        this.tarih = tarih;
        this.saat = 0;
        this.nufus = 0;
    }

    public String getAdi() {
        return adi;
    }

    public int getNufus() {
        return nufus;
    }

    public void setNufus(int nufus) {
        this.nufus = nufus;
    }

    public int getTarih() {
        return tarih;
    }

    public void setTarih(int tarih) {
        this.tarih = tarih;
    }

    public int getSaat() {
        return saat;
    }

    public String getSaatString() {
        return String.format("%02d:00", saat);
    }

    public int getGunlukSaat() {
        return gunlukSaat;
    }

    public void birSaatIlerle() {
        saat++;
        if (saat >= gunlukSaat) {
            saat = 0;
            tarih = birGunIlerle(tarih);
        }
    }

    private int birGunIlerle(int tarih) {
        int yil = tarih / 10000;
        int ay = (tarih / 100) % 100;
        int gun = tarih % 100;

        gun++;
        if (gun > aySonu(ay, yil)) {
            gun = 1;
            ay++;
            if (ay > 12) {
                ay = 1;
                yil++;
            }
        }

        return yil * 10000 + ay * 100 + gun;
    }

    private int aySonu(int ay, int yil) {
        switch (ay) {
            case 2:
                return (yil % 4 == 0 && yil % 100 != 0) || (yil % 400 == 0) ? 29 : 28;
            case 4: case 6: case 9: case 11: return 30;
            default: return 31;
        }
    }

    public void yazdir() {
        System.out.println("Gezegen: " + adi + " - Nüfus: " + nufus + " - Tarih: " + tarih);
    }
}
