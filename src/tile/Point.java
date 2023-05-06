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
    public int hitungJarak(Point p1, Point p2)
    {
        int xDiff = p2.getX() - p1.getX();
        int yDiff = p2.getY() - p1.getY();
        return (int) Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    }
}