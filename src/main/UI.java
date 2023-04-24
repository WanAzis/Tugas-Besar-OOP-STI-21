package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;

    Font arial_40, zarbVille;

    public int commandNum = 0;
    public int objIndex;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/ZarbvilleNbpRegular-MJOJ.ttf");
            zarbVille = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch(FontFormatException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState==gp.titleState){
            drawTitleScreen();
        }
        if(gp.gameState==gp.playState){
            if(gp.sim.interactObject){
                drawInteractObject(objIndex);
            }
        }
        if(gp.gameState==gp.pauseState){
            drawPauseScreen();
        }
    }

    private void drawTitleScreen(){

        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
        String text = "Sim-Plicity";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*1;

        //TEXT
        g2.setColor(Color.gray);
        g2.drawString(text, x+2, y+2);;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //SIM
        x = gp.screenWidth/2 - (gp.originalTileSize*5)/2;
        y += gp.tileSize/2;
        g2.drawImage(gp.sim.def, x, y, gp.originalTileSize*5, gp.originalTileSize*5, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y = gp.tileSize*4 + gp.originalTileSize;
        g2.drawString(text, x, y);
        if(commandNum==0){
            g2.drawString(">", x-gp.originalTileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize/2;
        g2.drawString(text, x, y);
        if(commandNum==1){
            g2.drawString(">", x-gp.originalTileSize, y);
        }

        text = "QUIT GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize/2;
        g2.drawString(text, x, y);
        if(commandNum==2){
            g2.drawString(">", x-gp.originalTileSize, y);
        }
    }
    private void drawPauseScreen(){
        String text1 = "GAME";
        String text2 = "PAUSED";
        int x = getXforCenteredText(text1);
        int y = gp.screenHeight/2 - 30;

        g2.drawString(text1, x, y);

        x = getXforCenteredText(text2);
        y += 40;

        g2.drawString(text2, x, y);
    }
    public void drawInteractObject(int i){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,7F));
        String text1 = "Apakah anda ingin melakukan";
        String text2 = "aksi " + gp.obj[i].action + " ?";
        
        //WINDOW
        int x;
        int width;
        if(getLongerText(text1, text2)){
            x = getXforCenteredBodyPlayer(text1) - gp.originalTileSize/2;
            width = (int)g2.getFontMetrics().getStringBounds(text1, g2).getWidth() + gp.originalTileSize;
        } else{
            x = getXforCenteredBodyPlayer(text2) - gp.originalTileSize/2;
            width = (int)g2.getFontMetrics().getStringBounds(text2, g2).getWidth() + gp.originalTileSize;
        }
        int y = gp.sim.screenY - gp.tileSize;
        int height = gp.tileSize;
        
        drawSubWindow(x,y,width,height);
        
        g2.setColor(Color.white);
        x = getXforCenteredBodyPlayer(text1);
        y+=gp.originalTileSize;
        g2.drawString(text1, x, y);

        x = getXforCenteredBodyPlayer(text2);
        y+=gp.originalTileSize;
        g2.drawString(text2, x, y);
    }
    private void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
    }
    private int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    private int getXforCenteredBodyPlayer(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.sim.screenX + gp.tileSize/2 - length/2;
        return x;
    }
    private boolean getLongerText(String text1, String text2){
        int length1 = (int)g2.getFontMetrics().getStringBounds(text1, g2).getWidth();
        int length2 = (int)g2.getFontMetrics().getStringBounds(text2, g2).getWidth();
        if(length1>length2){
            return true;
        } else return false;
    }
}
