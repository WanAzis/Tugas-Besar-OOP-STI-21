package main;

import objek.*;
import objek.barang.KasurSingle;
import objek.barang.MejaKursi;
import objek.barang.Toilet;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.obj[0] = new KasurSingle(gp);
		gp.obj[0].screenX = 0 * gp.tileSize;
		gp.obj[0].screenY = 0 * gp.tileSize;
		
		gp.obj[1] = new Toilet(gp);
		gp.obj[1].screenX = 5 * gp.tileSize;
		gp.obj[1].screenY = 1 * gp.tileSize;

		gp.obj[2] = new MejaKursi(gp);
		gp.obj[2].screenX = 3 * gp.tileSize;
		gp.obj[2].screenY = 3 * gp.tileSize;
		
	}
}
