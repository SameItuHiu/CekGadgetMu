package id.example.bagasekaa.cekgadgetmu_2;

public class account {
    public String email,password,pilih;

    public account() {
    }

    public account(String email, String password, String pilih) {
        this.email = email;
        this.password = password;
        this.pilih = pilih;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPilih() {
        return pilih;
    }

    public void setPilih(String pilih) {
        this.pilih = pilih;
    }
}
