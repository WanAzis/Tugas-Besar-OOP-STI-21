package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.awt.BasicStroke;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import entity.Sim;

import java.awt.*;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import objek.Objek;
import objek.barang.Barang;
import objek.barang.Jam;
import objek.barang.KasurKing;
import objek.barang.KasurQueen;
import objek.barang.KasurSingle;
import objek.barang.KomporGas;
import objek.barang.KomporListrik;
import objek.barang.MejaKursi;
import objek.barang.MesinCuci;
import objek.barang.RakBuku;
import objek.barang.Sajadah;
import objek.barang.TV;
import objek.barang.Toilet;
import objek.makanan.Ayam;
import objek.makanan.Bayam;
import objek.makanan.Kacang;
import objek.makanan.Kentang;
import objek.makanan.Nasi;
import objek.makanan.Sapi;
import objek.makanan.Susu;
import objek.makanan.Wortel;
import tile.Ruangan;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;

    Font arial_40, zarbVille;
    File fontFile = new File("../resources/font/ZarbvilleNbpRegular-MJOJ.ttf");
    BufferedImage coin;

    public static String simName;
    public int commandNum = 0;
    public int objIndex;
    private String notifMessage = "";
    public ArrayList<Objek> store = new ArrayList<>();
    private Ruangan chooseRuangan;
    public Barang chooseBarang;
    int slotCol = 0;
    int slotRow = 0;
    int subState = 0;

    //GETTER

    //SETTER
    public void setNotifMessage(String notifMessage){this.notifMessage=notifMessage;}

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = new FileInputStream(fontFile);
            zarbVille = Font.createFont(Font.TRUETYPE_FONT, is);
            coin = ImageIO.read(new File("../resources/barang/koin.png"));
        } catch(FontFormatException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        createStore();
    }

    private void createStore(){
        //ADD BARANG
        store.add(new KasurSingle(gp));
        store.add(new KasurKing(gp));
        store.add(new KasurQueen(gp));
        store.add(new Jam(gp));
        store.add(new KomporGas(gp));
        store.add(new KomporListrik(gp));
        store.add(new MejaKursi(gp));
        store.add(new MesinCuci(gp));
        store.add(new RakBuku(gp));
        store.add(new Sajadah(gp));
        store.add(new Toilet(gp));
        store.add(new TV(gp));
        // store.add(new Treadmil(gp));

        //ADD BAHAN MAKANAN
        store.add(new Nasi(gp));
        store.add(new Ayam(gp));
        store.add(new Bayam(gp));
        store.add(new Wortel(gp));
        store.add(new Kacang(gp));
        store.add(new Kentang(gp));
        store.add(new Sapi(gp));
        store.add(new Susu(gp));
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
            drawInventory(true);
        }
        if(gp.gameState==gp.durationState){
            if(gp.curSim.interactObject){
                drawDurationState(gp.curSim.curRuangan.obj[gp.curSim.interactObjectIdx].getName());
            } else {
                drawDurationState(gp.curSim.getStatus());
            }
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
        if(gp.gameState==gp.menuSimState){
            drawMenuSimScreen();
        }
        if(gp.gameState==gp.storeState){
            drawStore();
        }
        if(gp.gameState==gp.kerjaState){
            drawStatus();
        }
        if(gp.gameState == gp.menuGameState)
        {
            drawMenuGameScreen();
        }
        if(gp.gameState==gp.editRoomState){
            drawEditRoomScreen();
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
    // private void drawCreateSimScreen(){
    //     //FRAME


    //     //TEXT
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
    public void drawInventory(boolean draw){
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

        if(draw){
            //CURSOR 
            int cursorX = slotXstart + (slotSize*slotCol);
            int cursorY = slotYstart + (slotSize*slotRow);
            int cursorWidth = slotSize;
            int cursorHeight = slotSize;
    
            //DRAW CURSOR
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 4, 4);
        }

        //DESC WINDOW
        final int dframeX = gp.startRoomX + gp.tileSize*4 - 10;
        final int dframeY = gp.startRoomY + gp.tileSize*4 - 15;
        final int dframeWidth = gp.tileSize*2;
        final int dframeHeight = gp.tileSize*2 - gp.originalTileSize + 10;
        drawSubWindow(dframeX, dframeY, dframeWidth, dframeHeight);

        if(draw){
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
        } else{
            //COIN TEXT
            String text = "Your Coin: " + gp.curSim.getUang();
            int textX = dframeX + 8;
            int textY = dframeY + 23;
            g2.setFont(g2.getFont().deriveFont(17F));
            g2.drawString(text, textX, textY);
        }
    }
    public int getItemIndexOnSlot(){
        int itemIdx = slotCol + (slotRow*8);
        return itemIdx;
    }
    public void drawStore(){

        switch(subState){
            case 0 : storeSelect(); break;
            case 1 : storeBuy(); break;
            case 2 : storeSell(); break;
        }

        gp.keyH.enterPressed = false;
    }
    private void storeSelect(){

        //FRAME
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*7;
        final int frameX = gp.tileSize + gp.tileSize/2;
        final int frameY = gp.tileSize*2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));

        int textX;
        int textY;

        String text = "Menu Store";
        textX = getXforCenteredText(text, frameX + frameWidth/2);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        text = "Beli"; 
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString(text, textX, textY);
        if(commandNum==0){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                subState = 1;
            }
        }

        text = "Jual"; 
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);
        if(commandNum==1){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                subState = 2;
            }
        }
    }
    private void drawListStore(){
        //FRAME
        final int frameX = gp.startRoomX + gp.originalTileSize;
        final int frameY = gp.startRoomY + gp.originalTileSize;
        final int frameWidth = gp.tileSize*3 + gp.originalTileSize;
        final int frameHeight = gp.tileSize*3;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXstart = frameX + 10;
        final int slotYstart = frameY + 10;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.originalTileSize+2;

        //DRAW ITEMS
        for(int i = 0; i<store.size(); i++){
            g2.drawImage(store.get(i).getImage(), slotX, slotY, slotSize, slotSize, null);
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
        final int dframeY = gp.startRoomY + gp.originalTileSize;
        final int dframeWidth = gp.tileSize*2;
        final int dframeHeight = gp.tileSize*3;
        drawSubWindow(dframeX, dframeY, dframeWidth, dframeHeight);

        //DESC TEXT
        int textX = dframeX + 8;
        int textY = dframeY + 23;
        g2.setFont(g2.getFont().deriveFont(17F));

        int itemIdx = getItemIndexOnSlot();
        if(itemIdx < store.size()){
            for(String line : store.get(itemIdx).deskripsi.split("\n")){
                g2.drawString(line, textX, textY);
                textY+=18;
            }
        }
    }
    private void storeBuy(){ // DIPERBAIKI
        //DRAW INVENTORY
        drawInventory(false);
        //DRAWSTORE
        drawListStore();

        //DRAW PRICE WINDOW
        int itemIdx = getItemIndexOnSlot();
        if(itemIdx < store.size()){
            int x = gp.startRoomX + gp.originalTileSize + 13;
            int y = gp.startRoomY + gp.tileSize*3 - 10;
            g2.drawImage(coin, x, y, gp.originalTileSize, gp.originalTileSize, null);

            int price = store.get(itemIdx).harga;
            String text = "" + price;
            x += 25;
            y += 14;
            g2.drawString(text, x, y);

            //BUY ITEM
            if(gp.keyH.enterPressed){
                if(store.get(itemIdx).harga > gp.curSim.getUang()){
                    subState = 0;
                    gp.gameState = gp.notifState;
                    notifMessage = "Uang anda tidak cukup untuk \nmembeli barang";
                    drawNotifScreen();
                } else if(gp.sim.inventory.size() == gp.sim.maxInventorySize){
                    subState = 0;
                    gp.gameState = gp.notifState;
                    notifMessage = "Inventory anda tidak cukup \nmenampung barang";
                    drawNotifScreen();
                } else {
                    gp.curSim.setUang(gp.curSim.getUang() - store.get(itemIdx).harga);
                    gp.curSim.inventory.add(store.get(itemIdx));
                }
            }
        }

    }
    private void storeSell(){
        //DRAW INVENTORY
        drawInventory(true);

        //DRAW PLAYER COIN
        final int dframeX = gp.startRoomX + gp.tileSize*4 - 10;
        final int dframeY = gp.startRoomY + gp.tileSize*3 - 10;
        final int dframeWidth = gp.tileSize*2;
        final int dframeHeight = gp.tileSize - 10;
        drawSubWindow(dframeX, dframeY, dframeWidth, dframeHeight);

        String text = "Your Coin: " + gp.curSim.getUang();
        int textX = dframeX + 8;
        int textY = dframeY + 23;
        g2.setFont(g2.getFont().deriveFont(17F));
        g2.drawString(text, textX, textY);

        //DRAW PRICE SELL
        //FRAME
        final int frameX = gp.startRoomX + gp.originalTileSize;
        final int frameY = gp.startRoomY + gp.tileSize*3 - 10;
        final int frameWidth = gp.tileSize*3 + gp.originalTileSize;
        final int frameHeight = gp.tileSize - 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        int itemIdx = getItemIndexOnSlot();
        if(itemIdx < gp.curSim.inventory.size()){
            int x = frameX + 8;
            int y = frameY + 10;
            g2.drawImage(coin, x, y, gp.originalTileSize, gp.originalTileSize, null);

            int price = gp.curSim.inventory.get(itemIdx).harga/2;
            text = "" + price;
            x += 25;
            y += 14;
            g2.drawString(text, x, y);

            //SELL ITEM
            if(gp.keyH.enterPressed){
                gp.curSim.inventory.remove(itemIdx);
                gp.curSim.setUang(gp.sim.getUang() + price);
            }
        }    
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
    public void drawDurationState(String entitas){

        //FRAME
        final int frameX = gp.startRoomX + gp.tileSize - gp.originalTileSize;
        final int frameY = gp.startRoomY + gp.tileSize/2;
        final int frameWidth = gp.roomWidth - (gp.tileSize + gp.tileSize/2);
        final int frameHeigth = gp.roomHeight - (gp.tileSize + gp.tileSize/2);
        drawSubWindow(frameX, frameY, frameWidth, frameHeigth);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));

        String text = "Silahkan pilih durasi";
        int textX = getXforCenteredText(text, frameX+(frameWidth/2));
        int textY = frameY + 27;
        final int lineHeight = 23;

        g2.drawString(text, textX, textY);
        textY+=lineHeight;
        switch(entitas){
            case "Kasur": drawOptionKasur(frameX, frameY, frameWidth, frameHeigth);
            case "Kerja": drawOptionKerja(frameX, frameY, frameWidth, frameHeigth);
        }
        gp.keyH.enterPressed = false;
    }
    private void drawOptionKasur(int frameX, int frameY, int frameWidth, int frameHeight){
        int textX = frameX + 30;
        int textY = frameY + 65;
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
    private void drawOptionKerja(int frameX, int frameY, int frameWidth, int frameHeight){
        int textX = frameX + 30;
        int textY = frameY + 65;
        final int lineHeight = 25;
        
        String text = "4 Jam";
        g2.drawString(text, textX, textY);
        if(commandNum==0){
            g2.drawString(">", textX-gp.originalTileSize, textY);
            if(gp.keyH.enterPressed){
                gp.curSim.setDurationKerja(60*60*2);   //ganti jadi 60*2 menit
                gp.curSim.setNullImage();
                gp.gameState=gp.kerjaState;
            }
        }
        textY+=lineHeight;

        text = "8 Jam";
        g2.drawString(text, textX, textY);
        if(commandNum==1){
            g2.drawString(">", textX-gp.originalTileSize, textY);
            if(gp.keyH.enterPressed){
                gp.curSim.setDurationKerja(60*60*4);
                gp.curSim.setNullImage();
                gp.gameState=gp.kerjaState;
            }
        }
        textY+=lineHeight;

        text = "12 Jam";
        g2.drawString(text, textX, textY);
        if(commandNum==2){
            g2.drawString(">", textX-gp.originalTileSize, textY);
            if(gp.keyH.enterPressed){
                gp.curSim.setDurationKerja(60*60*6);
                gp.curSim.setNullImage();
                gp.gameState=gp.kerjaState;
            }
        }
        textY+=lineHeight;

        text = "16 Jam";
        g2.drawString(text, textX, textY);
        if(commandNum==3){
            g2.drawString(">", textX-gp.originalTileSize, textY);
            if(gp.keyH.enterPressed){
                gp.curSim.setDurationKerja(60*60*8);
                gp.curSim.setNullImage();
                gp.gameState=gp.kerjaState;
            }
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
    private void drawMenuGameScreen()
    {
        //WINDOW
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*7;
        final int frameX = gp.tileSize + gp.tileSize/2;
        final int frameY = gp.tileSize*2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight); 

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));
        
        //SUBSTATE
        switch(subState){
            case 0: menuGame_top(frameX, frameY, frameWidth, frameHeight); break;
            case 1: drawChangeSimScreen(frameX, frameY, frameWidth, frameHeight); break;
        }
        gp.keyH.enterPressed = false;
    }

    private void menuGame_top(int frameX, int frameY, int frameWidth, int frameHeight){
        int textX;
        int textY;

        //MENU
        String text = "Menu Game";
        textX = getXforCenteredText(text, frameX + frameWidth/2);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        text = "Add New SIM";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum==0){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 0;
                gp.gameState = gp.playState;
                String simName = (String) JOptionPane.showInputDialog(null, "Enter New SIM Name: ");
				gp.keyH.createNewSimState(gp,simName);
            }
        }

        //CHANGE SIM
        text = "Change SIM";
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);
        if(commandNum==1){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                commandNum = 0;
                subState = 1;
            }
        }
    }

    private void drawChangeSimScreen(int frameX, int frameY, int frameWidth, int frameHeigth){
        int textX;
        int textY;

        String text = "Daftar SIM";
        textX = getXforCenteredText(text, frameX + frameWidth/2);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        for(int i = 0; i<gp.listSim.size(); i++)
        {
            text = gp.listSim.get(i).getSimName();
            textX = frameX + gp.tileSize;
            textY += gp.tileSize;
            g2.drawString(text, textX, textY);
            if(commandNum==i){
                g2.drawString(">", textX-gp.tileSize/2, textY);
                if(gp.keyH.enterPressed){
                    gp.curSim = gp.listSim.get(i);
                    gp.gameState = gp.playState;
                    commandNum = 0; subState = 0;
                }
            }
        }
    }

    private void drawMenuSimScreen(){
        //WINDOW
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*7;
        final int frameX = gp.tileSize + gp.tileSize/2;
        final int frameY = gp.tileSize*2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,25F));

        //SUBSTATE
        switch(subState){
            case 0: menuSim_top(frameX, frameY, frameWidth, frameHeight); break;
            case 1: viewLocationScreen(frameX, frameY, frameWidth, frameHeight); break;
            case 2: drawTambahRuanganScreen(frameX, frameY, frameWidth, frameHeight); break;
            case 3: drawDaftarRuanganScreen(frameX, frameY, frameWidth, frameHeight); break;
            case 4: drawChoosePosisiRuangan(frameX, frameY, frameWidth, frameHeight); break;
        }
        gp.keyH.enterPressed = false;
    }
    private void menuSim_top(int frameX, int frameY, int frameWidth, int frameHeigth){

        int textX;
        int textY;

        //MENU
        String text = "Menu Sim";
        textX = getXforCenteredText(text, frameX + frameWidth/2);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        //VIEW LOCATION
        text = "View Location";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize - 10;
        g2.drawString(text, textX, textY);
        if(commandNum==0){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                subState = 1;
                commandNum = 0;
            }
        }

        //BELI JUAL BARANG
        text = "Beli/Jual Barang";
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);
        if(commandNum==1){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                commandNum = 0;
                subState = 0;
                gp.gameState = gp.storeState;
            }
        }

        //SIM
        text = "SIM";
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);
        if(commandNum==2){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                
            }
        }

        //MOVE RUANGAN
        text = "Move Ruangan";
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);
        if(commandNum==3){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                commandNum = 0;
                subState = 3;
            }
        }

        //KUNJUNGAN
        text = "Kunjungan";
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);
        if(commandNum==4){
            g2.drawString(">", textX-gp.tileSize/2, textY);
        }

        //KERJA
        text = "Kerja";
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);
        if(commandNum==5){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                commandNum = 0;
                subState = 0;
                gp.curSim.setStatus("Kerja");
                gp.gameState=gp.durationState;
            }
        }

        //EDIT ROOM
        text = "Edit Room";
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);
        if(commandNum==6){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                commandNum = 0;
                subState = 0;
                gp.gameState=gp.editRoomState;
            }
        }

        //UPGRADE RUMAH
        text = "Upgrade Rumah";
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);
        if(commandNum==7){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                commandNum = 0;
                subState = 2;
            }
        }
    }
    private void viewLocationScreen(int frameX, int frameY, int frameWidth, int frameHeigth){
        
        int textX;
        int textY;

        String text = "Lokasi Sim";
        textX = getXforCenteredText(text, frameX + frameWidth/2);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);


        text = "Rumah"; 
        textX = frameX + gp.originalTileSize;
        textY += gp.tileSize*2;
        g2.drawString(text, textX, textY);

        text = "Ruangan"; 
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);

        text = gp.curSim.curRumah.haveSim.getSimName();
        textX = getXforAligntoRightText(text, frameX + frameWidth - gp.originalTileSize);
        textY = frameY + gp.tileSize*3;
        g2.drawString(text, textX, textY);

        text = gp.curSim.curRuangan.getNama(); 
        textX = getXforAligntoRightText(text, frameX + frameWidth - gp.originalTileSize);
        textY += gp.tileSize/2 + 10;
        g2.drawString(text, textX, textY);

        text = "Kembali"; 
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*3 - gp.tileSize/2;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
            }
        }

    }
    private void drawTambahRuanganScreen(int frameX, int frameY, int frameWidth, int frameHeight){

        
        int textX;
        int textY;

        //LIST RUANGAN
        String text = "Pilih Ruangan";
        textX = getXforCenteredText(text, frameX + frameWidth/2);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //LOOP
        for(int i = 0; i<gp.curSim.curRumah.listRuangan.size(); i++){
            text = "Ruangan" + gp.curSim.curRumah.listRuangan.get(i).getNama();
            textX = frameX + gp.tileSize;
            textY += gp.tileSize;
            g2.drawString(text, textX, textY);
            if(commandNum==i){
                g2.drawString(">", textX-gp.tileSize/2, textY);
                if(gp.keyH.enterPressed){
                    chooseRuangan = gp.curSim.curRumah.listRuangan.get(i);
                    subState = 4;
                }
            }
        }
    }
    private void drawDaftarRuanganScreen(int frameX, int frameY, int frameWidth, int frameHeight){

        int textX;
        int textY;

        String text = "Daftar Ruangan";
        textX = getXforCenteredText(text, frameX + frameWidth/2);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Ruangan Atas
        text = "Atas";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum==0){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed && gp.curSim.curRuangan.getRuanganTetangga(0)!=null){
                subState = 0;
                commandNum = 0;
                gp.gameState = gp.playState;
                gp.curSim.curRuangan=gp.curSim.curRuangan.getRuanganTetangga(0);
            }
        }

        //Ruangan Bawah
        text = "Bawah";
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum==1){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed && gp.curSim.curRuangan.getRuanganTetangga(1)!=null){
                subState = 0;
                commandNum = 0;
                gp.gameState = gp.playState;
                gp.curSim.curRuangan=gp.curSim.curRuangan.getRuanganTetangga(1);
            }
        }

        //Ruangan Kiri
        text = "Kiri";
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum==2){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed && gp.curSim.curRuangan.getRuanganTetangga(2)!=null){
                subState = 0;
                commandNum = 0;
                gp.gameState = gp.playState;
                gp.curSim.curRuangan=gp.curSim.curRuangan.getRuanganTetangga(2);
            }
        }

        //Ruangan Kanan
        text = "Kanan";
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum==3){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed && gp.curSim.curRuangan.getRuanganTetangga(4)!=null){
                subState = 0;
                commandNum = 0;
                gp.gameState = gp.playState;
                gp.curSim.curRuangan=gp.curSim.curRuangan.getRuanganTetangga(3);
            }
        }

        //Nama Ruangan Atas
        if(gp.curSim.curRuangan.getRuanganTetangga(0)!=null){
            text = gp.curSim.curRuangan.getRuanganTetangga(0).getNama();
            textX = getXforAligntoRightText(text, frameX+frameWidth-10);
            textY = frameY + gp.tileSize*2;
            g2.drawString(text, textX, textY);
        }

        //Nama Ruangan Bawah
        if(gp.curSim.curRuangan.getRuanganTetangga(1)!=null){
            text = gp.curSim.curRuangan.getRuanganTetangga(1).getNama();
            textX = getXforAligntoRightText(text, frameX+frameWidth-10);
            textY = frameY + gp.tileSize*3;
            g2.drawString(text, textX, textY);
        }

        //Nama Ruangan Kiri
        if(gp.curSim.curRuangan.getRuanganTetangga(2)!=null){
            text = gp.curSim.curRuangan.getRuanganTetangga(2).getNama();
            textX = getXforAligntoRightText(text, frameX+frameWidth-10);
            textY = frameY + gp.tileSize*4;
            g2.drawString(text, textX, textY);
        }

        //Nama Ruangan Kanan
        if(gp.curSim.curRuangan.getRuanganTetangga(3)!=null){
            text = gp.curSim.curRuangan.getRuanganTetangga(3).getNama();
            textX = getXforAligntoRightText(text, frameX+frameWidth-10);
            textY = frameY + gp.tileSize*5;
            g2.drawString(text, textX, textY);
        }
    }
    private void drawChoosePosisiRuangan(int frameX, int frameY, int frameWidth, int frameHeight){

        int textX;
        int textY;

        String text = "Pilih Ruangan";
        textX = getXforCenteredText(text, frameX + frameWidth/2);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Ruangan Atas
        text = "Atas";
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum==0){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed && chooseRuangan.getRuanganTetangga(0)==null){
                //MASUK KE STATE MASUKKAN NAMA RUANGAN 
                subState = 0;
                commandNum = 0;
                gp.gameState = gp.playState;
                gp.curSim.curRumah.tambahRuang("Atas", "Default", chooseRuangan);

            }
        }

        //Ruangan Bawah
        text = "Bawah";
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum==1){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed && chooseRuangan.getRuanganTetangga(1)==null){
                subState = 0;
                commandNum = 0;
                gp.gameState = gp.playState;
                gp.curSim.curRumah.tambahRuang("Bawah", "Default", chooseRuangan);
            }
        }

        //Ruangan Kiri
        text = "Kiri";
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum==2){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed && chooseRuangan.getRuanganTetangga(2)==null){
                subState = 0;
                commandNum = 0;
                gp.gameState = gp.playState;
                gp.curSim.curRumah.tambahRuang("Kiri", "Default", chooseRuangan);
            }
        }

        //Ruangan Kanan
        text = "Kanan";
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum==3){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed && chooseRuangan.getRuanganTetangga(3)==null){
                subState = 0;
                commandNum = 0;
                gp.gameState = gp.playState;
                gp.curSim.curRumah.tambahRuang("Kanan", "Default", chooseRuangan);
            }
        }

        //Nama Ruangan Atas
        if(gp.curSim.curRuangan.getRuanganTetangga(0)!=null){
            text = gp.curSim.curRuangan.getRuanganTetangga(0).getNama();
            textX = getXforAligntoRightText(text, frameX+frameWidth-10);
            textY = frameY + gp.tileSize*2;
            g2.drawString(text, textX, textY);
        }

        //Nama Ruangan Bawah
        if(gp.curSim.curRuangan.getRuanganTetangga(1)!=null){
            text = gp.curSim.curRuangan.getRuanganTetangga(1).getNama();
            textX = getXforAligntoRightText(text, frameX+frameWidth-10);
            textY = frameY + gp.tileSize*3;
            g2.drawString(text, textX, textY);
        }

        //Nama Ruangan Kiri
        if(gp.curSim.curRuangan.getRuanganTetangga(2)!=null){
            text = gp.curSim.curRuangan.getRuanganTetangga(2).getNama();
            textX = getXforAligntoRightText(text, frameX+frameWidth-10);
            textY = frameY + gp.tileSize*4;
            g2.drawString(text, textX, textY);
        }

        //Nama Ruangan Kanan
        if(gp.curSim.curRuangan.getRuanganTetangga(3)!=null){
            text = gp.curSim.curRuangan.getRuanganTetangga(3).getNama();
            textX = getXforAligntoRightText(text, frameX+frameWidth-10);
            textY = frameY + gp.tileSize*5;
            g2.drawString(text, textX, textY);
        }
    }
    public void drawEditRoomScreen(){

        Barang chooseBarang = gp.curSim.curRuangan.getBarang(0);
        Color c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.fill(chooseBarang.getSolidArea());
        g2.draw(chooseBarang.getSolidArea());
        // g2.fillRect(chooseBarang);
        // drawSubWindow(0, 0, 96,96);
        // drawSubWindow((int) chooseBarang.solidArea.x,(int) chooseBarang.solidArea.getY(),(int) chooseBarang.solidArea.getWidth(),(int) chooseBarang.solidArea.getHeight());
        // g2.fillRoundRect(gp.curSim.curRuangan.obj[commandNum].solidArea.x+gp.tileSize, gp.curSim.curRuangan.obj[commandNum].solidArea.y+gp.tileSize, 
        // gp.curSim.curRuangan.obj[commandNum].solidArea.width, gp.curSim.curRuangan.obj[commandNum].solidArea.height, 35, 35);
        // // g2.drawRect(solidAreaObj.x, solidAreaObj.y, solidAreaObj.width, solidAreaObj.height);
        // c = new Color(255,255,255);
        // g2.setColor(c);
        // g2.setStroke(new BasicStroke(3));
        // g2.drawRoundRect(gp.curSim.curRuangan.obj[commandNum].solidArea.x+2, gp.curSim.curRuangan.obj[commandNum].solidArea.y+2, 
        // gp.curSim.curRuangan.obj[commandNum].solidArea.width-2, gp.curSim.curRuangan.obj[commandNum].solidArea.height-2, 20, 20);
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
