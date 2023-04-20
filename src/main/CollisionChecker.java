package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public int checkObjek(Entity entity, boolean sim) {
		 
		int index = 999;
		
		for(int i = 0; i<gp.obj.length; i++) {
			if(gp.obj[i]!=null) {
				entity.solidArea.x = entity.screenX + entity.solidArea.x;
				entity.solidArea.y = entity.screenY + entity.solidArea.y;
				
				gp.obj[i].solidArea.x = gp.obj[i].screenX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].screenY + gp.obj[i].solidArea.y;
				
				switch(entity.direction) {
				case "up": 
					entity.solidArea.y -= entity.speed; 
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(sim) {
							index = i;
						}
					}
					break;
				case "down": 
					entity.solidArea.y += entity.speed; 
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(sim) {
							index = i;
						}
					}
					break;
				case "left": 
					entity.solidArea.x -= entity.speed; 
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision) {
							entity.collisionOn = true;
						}
						if(sim) {
							index = i;
						}
					}
					break;
				case "right": 
					entity.solidArea.x += entity.speed; 
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision) {
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
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
				
			}
		}
		
		return index;
	}
}
