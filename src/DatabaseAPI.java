import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAPI {
    private String fileName;

    public DatabaseAPI(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
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
                    cities.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return cities;
    }

    public void deleteCity(String name) {
        List<String> cities = getCities();
        Pattern pattern = Pattern.compile(name);

        try (FileWriter fw = new FileWriter(DatabaseAPI.class.getResource(fileName).getFile(), false);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {

            for (String city : cities) {
                Matcher match = pattern.matcher(city);
                if (match.find()) {
                    System.out.println("found match");
                } else {
                    out.println(city);
                }
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

    }

    public void saveCity(String name) {
        try (FileWriter fw = new FileWriter(DatabaseAPI.class.getResource(fileName).getFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(name);
            System.out.println("Data appended to " + fileName);

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

}
