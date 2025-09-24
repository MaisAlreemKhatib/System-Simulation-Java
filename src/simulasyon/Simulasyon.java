package simulasyon;

import java.io.IOException;
import java.util.ArrayList;

public class Simulasyon {
    public static void main(String[] args) {
        try {
            ArrayList<Gezegen> gezegenler = DosyaOkuma.gezegenleriOku("src/Gezegenler.txt");
            ArrayList<UzayAraci> uzayAraclari = DosyaOkuma.uzayAraclariOku("src/Araclar.txt");
            ArrayList<Kisi> kisiler = DosyaOkuma.kisileriOku("src/Kisiler.txt");

            // Kişileri uzay araçlarına yerleştir
            for (Kisi kisi : kisiler) {
                for (UzayAraci arac : uzayAraclari) {
                    if (arac.getAdi().equals(kisi.getUzayAraci())) {
                        arac.addKisi(kisi);
                        break;
                    }
                }
            }

            int saat = 0;
            while (true) {
                temizleEkran();
                System.out.println("Simülasyon Saati: " + saat + "\n");

                //  "Beklemede" olan araçları "Yolda" yap (çıkış zamanı geldiyse)
                for (UzayAraci arac : uzayAraclari) {
                    if (arac.getDurum().equals("Beklemede")) {
                        for (Gezegen g : gezegenler) {
                            if (arac.getCikisGezegeni().equals(g.getAdi()) &&
                                g.getTarih() >= arac.getCikisTarihiInt()) {
                                arac.setDurum("Yolda");
                            }
                        }
                    }
                }

                // Gezegenler başlık
                System.out.println("Gezegenler:");
                System.out.print("            ");
                for (Gezegen g : gezegenler) {
                    System.out.printf("--- %-6s ---      ", g.getAdi());
                }
                System.out.println();

                // Gezegen tarihleri
                System.out.print("Tarih       ");
                for (Gezegen g : gezegenler) {
                    System.out.printf("%-20s", formatTarih(g.getTarih()));
                }
                System.out.println();

                // Gezegen saatleri
                System.out.print("Saat        ");
                for (Gezegen g : gezegenler) {
                    System.out.printf("%-20s", g.getSaatString());
                }
                System.out.println();

                // Gezegen nüfusları
                System.out.print("Nüfus       ");
                for (Gezegen g : gezegenler) {
                    int nufus = 0;
                    for (UzayAraci arac : uzayAraclari) {
                        String durum = arac.getDurum();
                        if (durum.equals("Hedefe Ulaştı") && arac.getVarisGezegeni().equals(g.getAdi())) {
                            nufus += arac.canliYolcuSayisi();
                        } else if ((durum.equals("Beklemede") || durum.equals("Yolda")) &&
                                arac.getCikisGezegeni().equals(g.getAdi())) {
                            nufus += arac.canliYolcuSayisi();
                        }
                    }
                    g.setNufus(nufus);
                    System.out.printf("%-20s", g.getNufus());
                }
                System.out.println("\n");

                // Uzay aracı bilgileri
                System.out.println("Uzay Araçları:");
                System.out.println("Araç Adı   | Durum           | Çıkış     | Varış     | Hedefe Kalan Saat   | Hedefe Varacağı Tarih");
                System.out.println("------------------------------------------------------------------------------------------------------");

                for (UzayAraci arac : uzayAraclari) {
                    arac.saatAzalt();  // saat azalt, omur azalt, durumu güncelle

                    // 🌍 Araç varışa ulaştıysa yolcuları gezegene aktar
                    if (arac.getDurum().equals("Hedefe Ulaştı")) {
                        for (Gezegen g : gezegenler) {
                            if (arac.getVarisGezegeni().equals(g.getAdi())) {
                                g.setNufus(g.getNufus() + arac.canliYolcuSayisi());
                            }
                        }
                    }

                    String kalanSaatStr = arac.getDurum().equals("İMHA") ? "--" : String.valueOf(arac.getHedefeKalanSaat());
                    String varisTarihiStr = arac.getDurum().equals("İMHA") ? "--" : arac.getHedefeVaracagiTarih();

                    String terminalDurum = switch (arac.getDurum()) {
                        case "Beklemede" -> "Bekliyor";
                        case "Hedefe Ulaştı" -> "Vardı";
                        case "İMHA" -> "İMHA";
                        default -> "Yolda";
                    };

                    System.out.printf("%-10s | %-15s | %-9s | %-9s | %-21s | %-20s\n",
                            arac.getAdi(),
                            terminalDurum,
                            arac.getCikisGezegeni(),
                            arac.getVarisGezegeni(),
                            kalanSaatStr,
                            varisTarihiStr);
                }

                // Gezegen saatlerini ilerlet
                for (Gezegen g : gezegenler) {
                    g.birSaatIlerle();
                }

                saat++;

                boolean tamam = uzayAraclari.stream()
                        .allMatch(a -> a.getDurum().equals("Hedefe Ulaştı") || a.getDurum().equals("İMHA"));

                if (tamam) {
                    temizleEkran();
                    System.out.println("Tüm uzay araçları görevini tamamladı veya imha oldu. Simülasyon sona erdi.\n");

                    System.out.println("Son Gezegen Durumu:");
                    System.out.print("            ");
                    for (Gezegen g : gezegenler) {
                        System.out.printf("--- %-6s ---      ", g.getAdi());
                    }
                    System.out.println();

                    System.out.print("Tarih       ");
                    for (Gezegen g : gezegenler) {
                        System.out.printf("%-20s", formatTarih(g.getTarih()));
                    }
                    System.out.println();

                    System.out.print("Saat        ");
                    for (Gezegen g : gezegenler) {
                        System.out.printf("%-20s", g.getSaatString());
                    }
                    System.out.println();

                    System.out.print("Nüfus       ");
                    for (Gezegen g : gezegenler) {
                        System.out.printf("%-20s", g.getNufus());
                    }
                    System.out.println();
                    break;
                }

                Thread.sleep(100); // 0.1 saniye
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Hata oluştu: " + e.getMessage());
        }
    }

    public static void temizleEkran() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ignored) {
        }
    }

    public static String formatTarih(int tarih) {
        int yil = tarih / 10000;
        int ay = (tarih / 100) % 100;
        int gun = tarih % 100;
        return String.format("%02d.%02d.%04d", gun, ay, yil);
    }
}
