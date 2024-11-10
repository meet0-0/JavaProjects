import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner;
import models.WeatherData;

public class WeatherApp {
    private static List<models.WeatherData> monitoredCities = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("WEATHER MONITORING APP");

        while (true) {
            System.out.println("Options: \n1) Add City \n2) View Weather \n3) Save Cities \n4) Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCity(scanner);
                case 2 -> viewWeather();
                case 3 -> saveCities();
                case 4 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    
    // Method overloading to add a city with country or just city name
    private static void addCity(Scanner scanner) {
        System.out.print("Enter city name: ");
        String cityName = scanner.nextLine();
        System.out.print("Enter country(skip if you don't know): ");
        String country = scanner.nextLine();

        if (country.isEmpty()) {
            addCity(cityName);
        } else {
            addCity(cityName, country);
        }
    }

    private static void addCity(String cityName, String country) {
        WeatherData city = new WeatherData(cityName, country);
        monitoredCities.add(city);
        System.out.println("City added successfully.");
    }

    private static void addCity(String cityName) {
        WeatherData city = new WeatherData(cityName, "Unknown"); // Default country if not provided
        monitoredCities.add(city);
        System.out.println("City added successfully with default country.");
    }

    private static void viewWeather() {
        for (WeatherData city : monitoredCities) {
            city.fetchWeatherData();
            city.display();
        }
    }

    private static void saveCities() {
        try (FileWriter writer = new FileWriter("monitoredCities.txt")) {
            for (WeatherData city : monitoredCities) {
                writer.write(city.getCityName() + "," + city.getCountry() + "\n");
            }
            System.out.println("Cities saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving cities: " + e.getMessage());
        }
    }
}
