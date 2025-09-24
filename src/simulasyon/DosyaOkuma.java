package simulasyon;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DosyaOkuma {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");

    // Kisi bilgilerini oku
    public static ArrayList<Kisi> kisileriOku(String dosyaAdi) throws IOException {
        ArrayList<Kisi> kisiler = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(dosyaAdi));
        String satir;

        while ((satir = br.readLine()) != null) {
            String[] bilgiler = satir.split("#");
            if (bilgiler.length >= 4) {
                String isim = bilgiler[0];
                int yas = Integer.parseInt(bilgiler[1]);
                int kalanOmur = Integer.parseInt(bilgiler[2]);
                String uzayAraci = bilgiler[3];
                kisiler.add(new Kisi(isim, yas, kalanOmur, uzayAraci));
            }
        }
        br.close();
        return kisiler;
    }

    // Gezegen bilgilerini oku
    public static ArrayList<Gezegen> gezegenleriOku(String dosyaAdi) throws IOException {
        ArrayList<Gezegen> gezegenler = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(dosyaAdi));
        String satir;

        while ((satir = br.readLine()) != null) {
            String[] bilgiler = satir.split("#");
            if (bilgiler.length >= 3) {
                String adi = bilgiler[0];
                int gunlukSaat = Integer.parseInt(bilgiler[1]);
                LocalDate date = LocalDate.parse(bilgiler[2], formatter);
                int tarih = date.getYear() * 10000 + date.getMonthValue() * 100 + date.getDayOfMonth();
                gezegenler.add(new Gezegen(adi, gunlukSaat, tarih));
            }
        }
        br.close();
        return gezegenler;
    }

    // Uzay aracı bilgilerini oku
    public static ArrayList<UzayAraci> uzayAraclariOku(String dosyaAdi) throws IOException {
        ArrayList<UzayAraci> uzayAraclari = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(dosyaAdi));
        String satir;

        while ((satir = br.readLine()) != null) {
            String[] bilgiler = satir.split("#");
            if (bilgiler.length >= 5) {
                String adi = bilgiler[0];
                String cikisGezegeni = bilgiler[1];
                String varisGezegeni = bilgiler[2];

                LocalDate cikisDate = LocalDate.parse(bilgiler[3], formatter);
                int cikisTarihInt = cikisDate.getYear() * 10000 + cikisDate.getMonthValue() * 100 + cikisDate.getDayOfMonth();

                int hedefeKalanSaat = Integer.parseInt(bilgiler[4]);

                UzayAraci arac = new UzayAraci(adi, "Beklemede", cikisGezegeni, varisGezegeni, hedefeKalanSaat);
                arac.setCikisTarihiInt(cikisTarihInt);  // ÇIKIŞ TARİHİ ayarlanıyor!

                uzayAraclari.add(arac);
            }
        }
        br.close();
        return uzayAraclari;
    }
}
