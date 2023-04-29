package objek;

import java.awt.image.BufferedImage;

import main.GamePanel;

public class Objek {
    
    public GamePanel gp;
	protected BufferedImage image;
	protected String name;
	public String deskripsi = "";

    //Use Counter
	protected int counter = 0;
    
    //GETTER
    public String getName(){return name;}
    public BufferedImage getImage(){return image;}

    //SETTER
}
