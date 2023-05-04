package main;

import objek.barang.KasurSingle;
import objek.barang.MejaKursi;
import objek.barang.Toilet;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.curSim.curRuangan.obj[0] = new KasurSingle(gp);
		gp.curSim.curRuangan.obj[0].screenX = 0 * gp.tileSize;
		gp.curSim.curRuangan.obj[0].screenY = 0 * gp.tileSize;
		
		gp.curSim.curRuangan.obj[1] = new Toilet(gp);
		gp.curSim.curRuangan.obj[1].screenX = 5 * gp.tileSize;
		gp.curSim.curRuangan.obj[1].screenY = 1 * gp.tileSize;

		gp.curSim.curRuangan.obj[2] = new MejaKursi(gp);
		gp.curSim.curRuangan.obj[2].screenX = 3 * gp.tileSize;
		gp.curSim.curRuangan.obj[2].screenY = 3 * gp.tileSize;
		
	}
}
