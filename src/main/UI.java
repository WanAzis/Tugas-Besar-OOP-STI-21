package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import objek.Objek;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;

    Font arial_40, zarbVille;
    File fontFile = new File("../resources/font/ZarbvilleNbpRegular-MJOJ.ttf");

    public int commandNum = 0;
    public int objIndex;
    private String notifMessage = "";
    int slotCol = 0;
    int slotRow = 0;

    //GETTER

    //SETTER
    public void setNotifMessage(String notifMessage){this.notifMessage=notifMessage;}

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = new FileInputStream(fontFile);
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

        g2.setFont(zarbVille);
        g2.setColor(Color.white);

        if(gp.gameState==gp.titleState){
            drawTitleScreen();
        }
        if(gp.gameState==gp.playState){
            if(gp.sim.interactObject){
                drawInteractObject(gp.sim.interactObjectIdx);
            }
            drawStatus();
        }
        if(gp.gameState==gp.pauseState){
            drawPauseScreen();
        }
        if(gp.gameState==gp.simInfo){
            drawSimInfo();
            drawInventory();
        }
        if(gp.gameState==gp.durationState){
            drawDurationState(gp.obj[gp.sim.interactObjectIdx]);
        }
        if(gp.gameState==gp.useObjectState){
            drawUseObject(gp.obj[gp.sim.interactObjectIdx]);
            drawStatus();
        }
        if(gp.gameState==gp.notifState){
            drawNotifScreen();
        }
    }

    private void drawTitleScreen(){

        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
        String text = "Sim-Plicity";
        int x = getXforCenteredText(text, gp.screenWidth/2);
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
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));
        text = "NEW GAME";
        x = getXforCenteredText(text, gp.screenWidth/2);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum==0){
            g2.drawString(">", x-gp.originalTileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text, gp.screenWidth/2);
        y += gp.tileSize/2 + 15;
        g2.drawString(text, x, y);
        if(commandNum==1){
            g2.drawString(">", x-gp.originalTileSize, y);
        }

        text = "QUIT GAME";
        x = getXforCenteredText(text, gp.screenWidth/2);
        y += gp.tileSize/2 + 15;
        g2.drawString(text, x, y);
        if(commandNum==2){
            g2.drawString(">", x-gp.originalTileSize, y);
        }
    }
    private void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));
        String text1 = "GAME";
        String text2 = "PAUSED";
        int x = getXforCenteredText(text1, gp.screenWidth/2);
        int y = gp.screenHeight/2 - 30;

        g2.drawString(text1, x, y);

        x = getXforCenteredText(text2, gp.screenWidth/2);
        y += 60;

        g2.drawString(text2, x, y);
    }
    public void drawInteractObject(int i){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,10F));
        String text1 = "APAKAH ANDA INGIN MELAKUKAN";
        String text2 = "AKSI " + gp.obj[i].action + "?";
        
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
        y+=gp.originalTileSize + 5;
        g2.drawString(text1, x, y);

        x = getXforCenteredBodyPlayer(text2);
        y+=gp.originalTileSize;
        g2.drawString(text2, x, y);
    }
    public void drawSimInfo(){
        final int frameX = gp.originalTileSize;
        final int frameY = gp.tileSize/2;
        final int frameWidth = gp.tileSize*3 + gp.originalTileSize;
        final int frameHeight = gp.tileSize*3;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(17F));

        int textX = frameX + 10;
        int textY = frameY + 25;
        final int lineHeight = 20;

        //INFO
        g2.drawString("Nama", textX, textY);
        textY+=lineHeight;
        g2.drawString("Pekerjaan", textX, textY);
        textY+=lineHeight;
        g2.drawString("Kesehatan", textX, textY);
        textY+=lineHeight;
        g2.drawString("Kekenyangan", textX, textY);
        textY+=lineHeight;
        g2.drawString("Mood", textX, textY);
        textY+=lineHeight;
        g2.drawString("Uang", textX, textY);
        textY+=lineHeight;

        //VALUES
        int tailX = (frameX + frameWidth) - 10;
        textY = frameY + 25;
        String value;

        value = String.valueOf(gp.sim.getNamaLengkap());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.sim.getPekerjaan());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.sim.getKesehatan());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.sim.getKekenyangan());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.sim.getMood());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.sim.getUang());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
    }
    public void drawInventory(){
        //FRAME
        final int frameX = gp.originalTileSize;
        final int frameY = gp.tileSize*4 - 5;
        final int frameWidth = gp.tileSize*3 + gp.originalTileSize;
        final int frameHeight = gp.tileSize*2 - gp.originalTileSize + 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXstart = frameX + 10;
        final int slotYstart = frameY + 10;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.originalTileSize+2;

        //DRAW ITEMS
        for(int i = 0; i<gp.sim.inventory.size(); i++){
            g2.drawImage(gp.sim.inventory.get(i).image, slotX, slotY, slotSize, slotSize, null);
            slotX+=slotSize;
            if(i == 7 || i == 15 || i == 23){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //CURSOR 
        int cursorX = slotXstart + (slotSize*slotCol);
        int cursorY = slotYstart + (slotSize*slotRow);
        int cursorWidth = slotSize;
        int cursorHeight = slotSize;

        //DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 4, 4);

        //DESC WINDOW
        final int dframeX = gp.originalTileSize;
        final int dframeY = gp.tileSize*4 - 5;
        final int dframeWidth = gp.tileSize*3 + gp.originalTileSize;
        final int dframeHeight = gp.tileSize*2 - gp.originalTileSize + 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

    }
    public void drawStatus(){
        //FRAME
        final int frameX = gp.tileSize*4;
        final int frameY = gp.tileSize*4 + gp.tileSize/2;
        final int frameWidth = gp.tileSize*2;
        final int frameHeight = gp.tileSize + gp.tileSize/2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,16F));
        
        String text = "Day - " + gp.getDay();
        int textX = frameX + 10;
        int textY = frameY + 20;
        final int lineHeight = 20;
        
        g2.drawString(text, textX, textY);
        
        text = gp.getTime();
        textX = getXforAligntoRightText(text, frameX+frameWidth-10);
        g2.drawString(text, textX, textY);
        textY+=lineHeight;
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,17F));
        text = gp.sim.getStatus();
        textX = getXforCenteredText(text, frameX+(frameWidth/2));
        g2.drawString(text, textX, textY);
        textY+=lineHeight;

        //DRAW NAMA RUANGAN
    }
    public void drawDurationState(Objek obj){

        //FRAME
        final int frameX = gp.tileSize + gp.tileSize/2;
        final int frameY = gp.tileSize + gp.tileSize/2;
        final int frameWidth = gp.screenWidth - (2*(gp.tileSize + gp.tileSize/2));
        final int frameHeigth = gp.screenHeight - (2*(gp.tileSize + gp.tileSize/2));
        drawSubWindow(frameX, frameY, frameWidth, frameHeigth);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));

        String text = "Silahkan pilih durasi";
        int textX = getXforCenteredText(text, frameX+(frameWidth/2));
        int textY = frameY + 20;
        final int lineHeight = 20;

        g2.drawString(text, textX, textY);
        textY+=lineHeight;
        switch(obj.getName()){
            case "Kasur": drawOptionKasur(frameX, frameY, frameWidth, frameHeigth);
        }
    }
    private void drawOptionKasur(int frameX, int frameY, int frameWidth, int frameHeight){
        int textX = frameX + 30;
        int textY = frameY + 50;
        final int lineHeight = 25;
        
        String text = "4 Jam";
        g2.drawString(text, textX, textY);
        if(commandNum==0){
            g2.drawString(">", textX-gp.originalTileSize, textY);
        }
        textY+=lineHeight;

        text = "8 Jam";
        g2.drawString(text, textX, textY);
        if(commandNum==1){
            g2.drawString(">", textX-gp.originalTileSize, textY);
        }
        textY+=lineHeight;

        text = "12 Jam";
        g2.drawString(text, textX, textY);
        if(commandNum==2){
            g2.drawString(">", textX-gp.originalTileSize, textY);
        }
        textY+=lineHeight;

        text = "16 Jam";
        g2.drawString(text, textX, textY);
        if(commandNum==3){
            g2.drawString(">", textX-gp.originalTileSize, textY);
        }
    }
    private void drawNotifScreen(){
        //WINDOW
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize/2;
        final int frameWidth = gp.tileSize*4;
        final int frameHeight = gp.tileSize + gp.originalTileSize;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,18F));

        int textX = frameX + 20;
        int textY = frameY + 25;
        final int lineHeight = 20;

        for(String line : notifMessage.split("\n")){
            g2.drawString(line, textX, textY);
            textY+=lineHeight;
        }
        
    }
    public void drawUseObject(Objek obj){
        
    }
    private void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x+2, y+2, width-2, height-2, 20, 20);
    }
    private int getXforCenteredText(String text, int center){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = center - length/2;
        return x;
    }
    private int getXforCenteredBodyPlayer(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.sim.screenX + gp.tileSize/2 - length/2;
        return x;
    }
    private int getXforAligntoRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
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
