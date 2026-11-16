
package com.mycompany.praktikum_9;

import java.io.FileWriter;
import java.io.IOException;

public class TransaksiPeminjaman {
    public static void prosesPinjam(Anggota anggota, Koleksi koleksi) {
        System.out.println("\n===== STRUK TRANSAKSI =====");
        System.out.println("Peminjam : " + anggota.getNama() + " (" + anggota.getNpm() + ")");
        System.out.println("Item     : " + koleksi.getJudul());
        System.out.println("Status   : Berhasil dicatat di sistem.");
        
        koleksi.caraPinjam();
        if (koleksi instanceof DapatDipinjam) {
           ((DapatDipinjam) koleksi).prosesPinjamFisik();
        }
        System.out.println("==========================");
        
        try (FileWriter pipaTulis = new FileWriter("log_transaksi.txt", true)) {
            String log = "Peminjam: " + anggota.getNama() + " | Item: " + koleksi.getJudul() + "\n";
            pipaTulis.write(log);
            pipaTulis.close();
        }catch (IOException e) {
            System.out.println("Peringatan: Gagal menulis log transaksi ke file. " +e.getMessage());
        }
    }
}
