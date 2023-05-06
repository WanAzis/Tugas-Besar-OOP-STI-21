package main;

import entity.Entity;
import objek.barang.Barang;

public class CollisionChecker {
	
	private GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	//METHOD
	public int checkObjek(Entity entity, boolean sim) {
		 
		int index = 999;
		
		for(int i = 0; i<gp.curSim.curRuangan.obj.length; i++) {
			if(gp.curSim.curRuangan.obj[i]!=null) {
				entity.solidArea.x = entity.screenX + entity.solidArea.x;
				entity.solidArea.y = entity.screenY + entity.solidArea.y;
				
				gp.curSim.curRuangan.obj[i].solidArea.x = gp.curSim.curRuangan.obj[i].screenX + gp.curSim.curRuangan.obj[i].solidArea.x;
				gp.curSim.curRuangan.obj[i].solidArea.y = gp.curSim.curRuangan.obj[i].screenY + gp.curSim.curRuangan.obj[i].solidArea.y;
				
				switch(entity.direction) {
				case "up": 
					entity.solidArea.y -= entity.speed; 
					if(entity.solidArea.intersects(gp.curSim.curRuangan.obj[i].solidArea)) {
						if(gp.curSim.curRuangan.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(sim) {
							index = i;
						}
					}
					break;
				case "down": 
					entity.solidArea.y += entity.speed; 
					if(entity.solidArea.intersects(gp.curSim.curRuangan.obj[i].solidArea)) {
						if(gp.curSim.curRuangan.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(sim) {
							index = i;
						}
					}
					break;
				case "left": 
					entity.solidArea.x -= entity.speed; 
					if(entity.solidArea.intersects(gp.curSim.curRuangan.obj[i].solidArea)) {
						if(gp.curSim.curRuangan.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(sim) {
							index = i;
						}
					}
					break;
				case "right": 
					entity.solidArea.x += entity.speed; 
					if(entity.solidArea.intersects(gp.curSim.curRuangan.obj[i].solidArea)) {
						if(gp.curSim.curRuangan.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(sim) {
							index = i;
						}
					}
					break;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.curSim.curRuangan.obj[i].solidArea.x = gp.curSim.curRuangan.obj[i].solidAreaDefaultX;
				gp.curSim.curRuangan.obj[i].solidArea.y = gp.curSim.curRuangan.obj[i].solidAreaDefaultY;
				
			}
		}
		
		return index;
	}
	public void checkScreen(Entity entity){

		entity.solidArea.x = entity.screenX + entity.solidArea.x;
		entity.solidArea.y = entity.screenY + entity.solidArea.y;

		int check = 0;

		switch(entity.direction) {
			case "up":  
				check = entity.solidArea.y - entity.speed;
				if(check <= gp.startRoomY) {
					entity.collisionOn = true;
				}
				break;
			case "down": 
				check = entity.solidArea.y+entity.solidArea.height;
				if(check >= gp.startRoomY + 6*gp.tileSize) {
					entity.collisionOn = true;
				}
				break;
			case "left": 
				check = entity.solidArea.x - entity.speed;
				if(check<=gp.startRoomX) {
					entity.collisionOn = true;
				}
				break;
			case "right":  
				check = entity.solidArea.x + entity.solidArea.width;
				if(check >= gp.startRoomX + 6*gp.tileSize) {
					entity.collisionOn = true;
				}
				break;
			}
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
	}
	public void checkPlaceObject(Barang barang){
		for(int i = 0; i<gp.curSim.curRuangan.obj.length; i++) {
			if(gp.curSim.curRuangan.obj[i]!=null && !gp.curSim.curRuangan.obj[i].equals(barang)){
				barang.solidArea.x = barang.screenX + barang.solidArea.x;
				barang.solidArea.y = barang.screenY + barang.solidArea.y;

				gp.curSim.curRuangan.obj[i].solidArea.x = gp.curSim.curRuangan.obj[i].screenX + gp.curSim.curRuangan.obj[i].solidArea.x;
				gp.curSim.curRuangan.obj[i].solidArea.y = gp.curSim.curRuangan.obj[i].screenY + gp.curSim.curRuangan.obj[i].solidArea.y;

				if(barang.solidArea.intersects(gp.curSim.curRuangan.obj[i].solidArea)){
					barang.collisionWithOthers=true;
				}
				barang.solidArea.x = barang.solidAreaDefaultX;
				barang.solidArea.y = barang.solidAreaDefaultY;

				gp.curSim.curRuangan.obj[i].solidArea.x = gp.curSim.curRuangan.obj[i].solidAreaDefaultX;
				gp.curSim.curRuangan.obj[i].solidArea.y = gp.curSim.curRuangan.obj[i].solidAreaDefaultY;
			}
		}
	}
}
