public class Point
{
    private int x;
    private int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
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
    public void setX()
    {
        this.x = x;
    }
    public void setY()
    {
        this.y = y;
    }

    //jarak
    public int hitungJarak(Point p1, Point p2)
    {
        int xDiff = p2.getX() - p1.getX();
        int yDiff = p2.getY() - p1.getY();
        return Math.sqrt(xDiff*xDiff + yDiff*yDiff);
    }

}