public class Ruangan
{
    private int sisaLuas;
    private List<Barang> listBarang;
    private String nama;
    private Ruangan[] ruanganTetangga;

    public Ruangan(String nama)
    {
        this.nama = nama;
        this.listBarang = new ArrayList<Barang>();
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

    public String setNama(String nama)
    {
        this.nama = nama;
    }

    public List<Barang> getListBarang() 
    {
        return listBarang;
    }
}
/* yang masih kurang
1. sisaLuas itu gimana???
2. mungkin tambah addBarang
3. setListBarang gimana??
*/