public class City {
    private String name;
    private float lat;
    private float lon;

    public City(String name, float lat, float lon){
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    public float getLon(){
        return lon;
    }

    public String getName(){
        return name;
    }

    public float getLat(){
        return lat;
    }
}
