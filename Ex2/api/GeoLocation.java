package api;

public class GeoLocation implements geo_location
{
    private double x;
    private double y;
    private double z;
    public GeoLocation()
    {
        this(0,0,0);
    }

    public GeoLocation(double x, double y, double z)
    {
        setX(x);
        setY(y);
        setZ(z);
    }



    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(geo_location g) {
        return Math.sqrt(Math.pow(g.x()-x(),2)+Math.pow(g.y()-y(),2)+Math.pow(g.z()-z(),2));
    }

    private void setX(double x) {
        this.x = x;
    }

    private void setY(double y) {
        this.y = y;
    }

    private void setZ(double z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof GeoLocation)
        {
            GeoLocation t = (GeoLocation) obj;
            return x==t.x && y == t.y && z==t.z;
        }
        else
            return false;
    }

}