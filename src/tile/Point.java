package tile;
public class Point
{
    private int x;
    private int y;

    public Point()
    {
        x = 20;
        y = 20;
    }
    
    //getter
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

    //setter
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }

    //jarak
    public int hitungJarak(Point p)
    {
        int xDiff = p.getX() - this.getX();
        int yDiff = p.getY() - this.getY();
        return (int) Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    }
}