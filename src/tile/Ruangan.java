package tile;

import objek.barang.*;

import java.util.ArrayList;

public class Ruangan
{
    private int sisaLuas;
    private String nama;
    private Ruangan[] ruanganTetangga;
    public Barang obj[];

    public Ruangan(String nama)
    {
        this.nama = nama;
        obj = new Barang[10];
        this.ruanganTetangga = new Ruangan[4];
    }

    //Getter and setter

    public Ruangan getRuanganTetangga(int index)
    {
        return ruanganTetangga[index];
    }

    public void setRuanganTetangga(int index, Ruangan ruangan)
    {
        ruanganTetangga[index] = ruangan;
    }

    public String getNama()
    {
        return this.nama;
    }

    public void setNama(String nama)
    {
        this.nama = nama;
    }

    public Barang getBarang(int idx) 
    {
        return obj[idx];
    }

    public void addBarang(Barang barang)
    {
		int i = 0;
		while(i<obj.length && obj[i]!=null){
			i++;
		} 
		obj[i] = barang;
	}
}
/* yang masih kurang
1. sisaLuas itu gimana???
2. mungkin tambah addBarang
3. setListBarang gimana??
*/