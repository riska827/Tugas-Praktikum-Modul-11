/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum_9;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Perpustakaan {
    private String namaPerpus;
    private RuangBaca ruangBacaUtama; // Composition
    private ArrayList<Koleksi> daftarKoleksi; // Aggregation
    private HashMap<String, Anggota> dataAnggota; // Aggregation

    public Perpustakaan(String namaPerpus) {
        this.namaPerpus = namaPerpus;
        // Composition: RuangBaca diciptakan di dalam constructor Perpustakaan
        this.ruangBacaUtama = new RuangBaca("Ruang Baca Utama", 50);
        this.daftarKoleksi = new ArrayList<>();
        this.dataAnggota = new HashMap<>();
    }
    
    public void simpanDataKeFile() {
    try {
        FileOutputStream fileKeluar = new FileOutputStream("data_perpustakaan.dat");
        ObjectOutputStream mesinPembeku = new ObjectOutputStream(fileKeluar);
        
        mesinPembeku.writeObject(this.daftarKoleksi);
        mesinPembeku.writeObject(this.dataAnggota);
        
        mesinPembeku.close();
        System.out.println("-> [SYSTEM LOG] Seluruh data berhasil disimpan (Serialization) ke dalam Harddisk!");
    } catch (Exception e) {
        System.out.println("-> [SYSTEM ERROR] Gagal menyimpan data: " + e.getMessage());
    }
}
    
@SuppressWarnings("unchecked")
public void muatDataDariFile() {
    try {
        File cekFile = new File("data_perpustakaan.dat");
        if (!cekFile.exists()) return; 
        
        FileInputStream fileMasuk = new FileInputStream(cekFile);
        ObjectInputStream mesinPembangkit = new ObjectInputStream(fileMasuk);
        
        this.daftarKoleksi = (ArrayList<Koleksi>) mesinPembangkit.readObject();
        this.dataAnggota = (HashMap<String, Anggota>) mesinPembangkit.readObject();
        
        Koleksi.totalKoleksiBerhasilDibuat = this.daftarKoleksi.size();
        
        mesinPembangkit.close();
        System.out.println("-> [SYSTEM LOG] Data sistem berhasil dimuat ulang dari penyimpanan.");
    } catch (Exception e) {
        System.out.println("-> [SYSTEM ERROR] Gagal memuat data: " + e.getMessage());
    }
}

public void bacaLogTransaksi() {
    System.out.println("\n--- Riwayat Log Transaksi ---");
    try {
        File fileLog = new File("log_transaksi.txt");
        if (!fileLog.exists()) {
            System.out.println("Belum ada log transaksi perpustakaan.");
            return;
        }
        
        Scanner mesinBaca = new Scanner(fileLog);
        while (mesinBaca.hasNextLine()) {
            System.out.println(mesinBaca.nextLine());
        }
        mesinBaca.close();
    } catch (FileNotFoundException e) {
        System.out.println("Gagal membuka file log.");
    }
}

    public void bukaPerpustakaan() {
        System.out.println("\n*** Menginisialisasi sistem " + this.namaPerpus + " ***");
        ruangBacaUtama.bukaRuangan();
    }

    public void tambahKoleksi(Koleksi k) {
        daftarKoleksi.add(k);
        System.out.println("Sukses! Koleksi ditambahkan. (Total Koleksi: " + daftarKoleksi.size() + ")");
    }

    public void tambahAnggota(Anggota a) {
        dataAnggota.put(a.getNpm(), a);
        System.out.println("Berhasil menambahkan anggota!");
    }

    public void lihatSemuaKoleksi() {
        if (daftarKoleksi.isEmpty()) {
            System.out.println("Belum ada koleksi yang tersimpan.");
            return;
        }
        for (int i = 0; i < daftarKoleksi.size(); i++) {
            System.out.print((i + 1) + ". ");
            daftarKoleksi.get(i).tampilkanInfo();
        }
    }
    
    public void cariKoleksi(String judul) throws KoleksiTidakDitemukanException {
        System.out.println("Mencari Koleksi dengan judul: " + judul);
        boolean ditemukan = false;

        for (Koleksi k : daftarKoleksi) {
            if (k.getJudul().equalsIgnoreCase(judul)) {
                k.tampilkanInfo();
                ditemukan = true;
            }
        }

        if (!ditemukan) {
            throw new KoleksiTidakDitemukanException("Gagal: Koleksi dengan judul '" + judul + "' tidak terdaftar.");
        }
    }

    public void cariAnggota(String npm) {
        if (dataAnggota.containsKey(npm)) {
            System.out.println("Ditemukan! Nama: " + dataAnggota.get(npm).getNama());
        } else {
            System.out.println("Anggota dengan NPM tersebut tidak terdaftar.");
        }
    }

    public void lihatSemuaAnggota() {
        System.out.println("Total Anggota Terdaftar: " + dataAnggota.size());
        for (Anggota a : dataAnggota.values()) {
            System.out.println("- NPM: " + a.getNpm() + " | Nama: " + a.getNama());
        }
    }

    public Anggota getAnggota(String npm) { return dataAnggota.get(npm);}
    public Koleksi getKoleksiInt(int index) { return daftarKoleksi.get(index);}
    public int getJumlahKoleksi() { return daftarKoleksi.size();}
}
