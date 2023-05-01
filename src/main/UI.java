package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.BasicStroke;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.*;

import objek.barang.Barang;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;

    Font arial_40, zarbVille;
    File fontFile = new File("../resources/font/ZarbvilleNbpRegular-MJOJ.ttf");

    public static String simName;
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
            if(gp.curSim.interactObject){
                drawInteractObject(gp.curSim.interactObjectIdx);
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
            drawDurationState(gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx]);
        }
        if(gp.gameState==gp.useObjectState){
            drawUseObject(gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx]);
            drawStatus();
        }
        if(gp.gameState==gp.notifState){
            drawNotifScreen();
        }
        if(gp.gameState==gp.useMakananState){
            drawStatus();
        }

    }

    private void drawTitleScreen(){

        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,80F));
        String text = "Sim-Plicity";
        int x = getXforCenteredText(text, gp.screenWidth/2);
        int y = gp.tileSize*2 + gp.originalTileSize;

        //TEXT
        g2.setColor(Color.gray);
        g2.drawString(text, x+3, y+3);;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //SIM
        x = gp.screenWidth/2 - (gp.originalTileSize*6)/2;
        y += gp.tileSize - 10;
        g2.drawImage(gp.sim.def, x, y, gp.originalTileSize*6, gp.originalTileSize*6, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,35F));
        text = "NEW GAME";
        x = getXforCenteredText(text, gp.screenWidth/2);
        y = gp.tileSize*6 + gp.tileSize/2;
        g2.drawString(text, x, y);
        if(commandNum==0){
            g2.drawString(">", x-gp.tileSize/2, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text, gp.screenWidth/2);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum==1){
            g2.drawString(">", x-gp.tileSize/2, y);
        }

        text = "QUIT GAME";
        x = getXforCenteredText(text, gp.screenWidth/2);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum==2){
            g2.drawString(">", x-gp.tileSize/2, y);
        }
    }
    // private void createSimScreen() {
        
        // gp.gameState = gp.playState;
        // //Frame
        // g2.setColor(new Color(0, 0, 0));
        // g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        // g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        // String text = "Enter SIM Name";
        // int x = getXforCenteredText(text, gp.screenWidth/2);
        // int y = gp.tileSize*2 + gp.originalTileSize;
    
        // //TEXT
        // g2.setColor(Color.white);
        // g2.drawString(text, x, y + gp.originalTileSize*6 + 20);
    
        // //SIM
        // x = gp.screenWidth/2 - (gp.originalTileSize*6)/2;
        // y -= 20; // Move the sim image up by 20 pixels
        // g2.drawImage(gp.sim.def, x, y, gp.originalTileSize*6, gp.originalTileSize*6, null);
    
        // //TEXT BOX
        // JTextField textField = new JTextField();
        // Dimension size = new Dimension(300, 40); // set the preferred size to be 300 pixels wide and 40 pixels high
        // textField.setPreferredSize(size);
        // textField.setBounds(gp.screenWidth/2 - size.width/2, y + gp.originalTileSize*6 + 50, size.width, size.height);
        // textField.setFont(new Font("Arial", Font.PLAIN, 24));
        // textField.setHorizontalAlignment(JTextField.CENTER);
        // textField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        // textField.setBackground(new Color(220, 220, 220));

        // // cari tau gimana cara ngilangin text field sama ngubah screen karena looping terus
        // // Add the text field to the frame
        // gp.add(textField);
        // textField.requestFocusInWindow();
        // gp.gameState = gp.createSimState;
        // System.out.println("before: " + gp.gameState);
        // textField.addKeyListener(new KeyAdapter() {
        //     @Override
        //     public void keyPressed(KeyEvent e) {
                
        //         simName = textField.getText();
        //         if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    
        //             gp.gameState = gp.playState;
        //             System.out.println(simName);
        //             System.out.println("after: " + gp.gameState);
                    
        //         }
        //     }
        // });

    // }
    
    private void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));
        String text1 = "GAME";
        String text2 = "PAUSED";
        int x = getXforCenteredText(text1, gp.startRoomX + gp.roomWidth/2);
        int y = gp.screenHeight/2 + gp.tileSize + gp.originalTileSize;

        g2.drawString(text1, x, y);

        x = getXforCenteredText(text2, gp.startRoomX + gp.roomWidth/2);
        y += 60;

        g2.drawString(text2, x, y);
    }
    public void drawInteractObject(int i){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,10F));
        String text1 = "APAKAH ANDA INGIN MELAKUKAN";
        String text2 = "AKSI " + gp.curSim.curRuangan.obj[i].action + "?";
        
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
        int y = gp.curSim.screenY - gp.tileSize;
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
        //FRAME
        final int frameWidth = gp.tileSize*4;
        final int frameHeight = gp.tileSize*3;
        final int frameX = gp.startRoomX + gp.roomWidth/2 - frameWidth/2;
        final int frameY = gp.startRoomY + gp.tileSize/2;
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

        value = String.valueOf(gp.curSim.getSimName());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.curSim.getPekerjaan());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.curSim.getKesehatan());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.curSim.getKekenyangan());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.curSim.getMood());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
        textY+=lineHeight;

        value = String.valueOf(gp.curSim.getUang());
        textX = getXforAligntoRightText(value, tailX);
        g2.drawString(value,textX,textY);
    }
    public void drawInventory(){
        //FRAME
        final int frameX = gp.startRoomX + gp.originalTileSize;
        final int frameY = gp.startRoomY + gp.tileSize*4 - 15;
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
        for(int i = 0; i<gp.curSim.inventory.size(); i++){
            g2.drawImage(gp.curSim.inventory.get(i).getImage(), slotX, slotY, slotSize, slotSize, null);
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
        final int dframeX = gp.startRoomX + gp.tileSize*4 - 10;
        final int dframeY = gp.startRoomY + gp.tileSize*4 - 15;
        final int dframeWidth = gp.tileSize*2;
        final int dframeHeight = gp.tileSize*2 - gp.originalTileSize + 10;
        drawSubWindow(dframeX, dframeY, dframeWidth, dframeHeight);

        //DESC TEXT
        int textX = dframeX + 8;
        int textY = dframeY + 23;
        g2.setFont(g2.getFont().deriveFont(17F));

        int itemIdx = getItemIndexOnSlot();
        if(itemIdx < gp.curSim.inventory.size()){
            for(String line : gp.curSim.inventory.get(itemIdx).deskripsi.split("\n")){
                g2.drawString(line, textX, textY);
                textY+=18;
            }
        }

    }
    public int getItemIndexOnSlot(){
        int itemIdx = slotCol + (slotRow*8);
        return itemIdx;
    }
    public void drawStatus(){
        //FRAME
        final int frameX = gp.startPanelX;
        final int frameY = gp.startPanelY + 20;
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
        text = gp.curSim.getStatus();
        textX = getXforCenteredText(text, frameX+(frameWidth/2));
        g2.drawString(text, textX, textY);
        textY+=lineHeight;

        //DRAW NAMA RUANGAN
    }
    public void drawDurationState(Barang obj){

        //FRAME
        final int frameX = gp.startRoomX + gp.tileSize + gp.tileSize/2;
        final int frameY = gp.startRoomY + gp.tileSize + gp.tileSize/2;
        final int frameWidth = gp.roomWidth - (2*(gp.tileSize + gp.tileSize/2));
        final int frameHeigth = gp.roomHeight - (2*(gp.tileSize + gp.tileSize/2));
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
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*2;
        final int frameX = gp.tileSize + gp.roomWidth/2 - frameWidth/2;
        final int frameY = gp.tileSize/2 + gp.tileSize;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,18F));

        int textX = frameX + 10;
        int textY = frameY + 25;
        final int lineHeight = 20;

        for(String line : notifMessage.split("\n")){
            g2.drawString(line, textX, textY);
            textY+=lineHeight;
        }
        
    }
    public void drawUseObject(Barang obj){
        
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
        int x = gp.curSim.screenX + gp.tileSize/2 - length/2;
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
