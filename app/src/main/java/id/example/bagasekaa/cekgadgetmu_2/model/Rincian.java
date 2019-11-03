package id.example.bagasekaa.cekgadgetmu_2.model;

public class Rincian {
    private String sparepart;
    private String harga;
    private String jumlah;

    public Rincian(){

    }

    public Rincian(String sparepart, String harga, String jumlah) {
        this.sparepart = sparepart;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getSparepart() {
        return sparepart;
    }

    public String getHarga() {
        return harga;
    }


    public String getJumlah() {
        return jumlah;
    }

}
