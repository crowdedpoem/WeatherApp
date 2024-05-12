import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.net.URL;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Middleware {
    private String fileName;
    
    public Middleware(String fileName){
        this.fileName = fileName;
    }

    public List<City> getCities(){
        List<City> cities = new ArrayList<>();
         // read from file, else null
         try {
            URL fileUrl = this.getClass().getResource(fileName);
            if (fileUrl == null) {
                System.err.println("File not found: " + fileName);
                return null;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(fileUrl.openStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    // create city object from line
                    String[] commaSep = line.split(",");
                    float lat = Float.parseFloat(commaSep[1]);
                    float lon = Float.parseFloat(commaSep[2]);
                    cities.add(new City(commaSep[0], lat, lon));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        

        return cities;
    }

    public void saveCity(String name){
        
        try (FileWriter fw = new FileWriter(Middleware.class.getResource(fileName).getFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)) {

       out.println(name + ",4.3,3.4");
       System.out.println("Data appended to " + fileName);

   } catch (NumberFormatException e) {
       System.err.println("NumberFormatException: " + e.getMessage());
   } catch (IOException e) {
       System.err.println("Error appending to the file: " + e.getMessage());
   }
    }

    public City getCity(String name){
        // read from file, else null
        try {
            URL fileUrl = this.getClass().getResource(fileName);
            if (fileUrl == null) {
                System.err.println("File not found: " + fileName);
                return null;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(fileUrl.openStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return null;
    }
}
