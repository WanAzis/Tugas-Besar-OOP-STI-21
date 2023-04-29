package tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private int panjang;
    private int lebar;
    private int sisaLuas;
    private List<Rumah> listRumah;
    private char[][] matriksWorld;

    public World() {
        this.panjang = 64;
        this.lebar = 64;
        this.sisaLuas = panjang * lebar;
        this.listRumah = new ArrayList<Rumah>();
        this.matriksWorld = new char[panjang][lebar];
        for (int i = 0; i < 64; i++){
            for (int j = 0 ;j < 64; j++){
                matriksWorld[i][j] = 'X';
            }
        }
    }

    // public void tambahRumah(Rumah rumah) {
    //     // if (sisaLuas > 0) {
    //     //     listRumah.add(rumah);
    //     //     sisaLuas -= 1;
    //     //     matriksWorld[rumah.getX()][rumah.getY()]
    //     //     System.out.println("Rumah berhasil ditambahkan");
    //     // } else {
    //     //     System.out.println("Tidak cukup luas tanah untuk membangun rumah baru");
    //     // }
    // }

    public void tambahRumah (Rumah rumah){
        if (sisaLuas>0){
            listRumah.add(rumah);
            sisaLuas-=1;
            int min = 0;
            int max=63;
            int koordinatx = 0;
            int koordinaty= 0;
            Boolean flag = true;
            while (flag){
                Random random = new Random();
                koordinatx= random.nextInt((max-min)+1) + min;
                koordinaty=random.nextInt((max-min)+1)+min;
                if (matriksWorld[koordinatx][koordinaty] != 'R'){
                    flag = false;
                }
    
            }
            matriksWorld[koordinatx][koordinaty] = 'R';
        }

    }

    // private void updateMatriksWorld(Rumah rumah) {
    //     for (int i = rumah.getX(); i < rumah.getX() + rumah.getPanjang(); i++) {
    //         for (int j = rumah.getY(); j < rumah.getY() + rumah.getLebar(); j++) {
    //             matriksWorld[i][j] = rumah.getId();
    //         }
    //     }
    // }

    public void viewWorld() {
        for (int i = 0; i < 64; i++){
            for (int j = 0 ;j < 64; j++){
                System.out.print(matriksWorld[i][j]);
            }
            System.out.println("");
        }
    }
 }
