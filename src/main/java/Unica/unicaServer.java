package Unica;

import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import Unica.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class unicaServer {
    public static HashMap<String ,Restaurant> Restaurants =new HashMap<>();



    public static void startServer() {
        Timer timer = new Timer();

        // Create the TimerTask
        TimerTask fetchAndProcessTask = new TimerTask() {
            @Override
            public void run() {
                for (String url : RESTAURANTS.getAllUrls()) {
                    try {
                        // Fetch JSON from URL
                        //System.out.println(url);
                        JsonObject jsonObject = fetchJsonFromUrl(url);
                        //System.out.println(jsonObject);
                        // Create objects using the provided constructors
                        Restaurant RestaurantObject = createUnicaMainObject(jsonObject);

                        // Store the restaurant object in the map
                        Restaurants.put(RestaurantObject.getRestaurantName(), RestaurantObject);

                        // Example: Access and print some data
                        //System.out.println("Restaurant Name: " + RestaurantObject.getRestaurantName());
                        System.out.println(RestaurantObject);
                        //System.out.println("--------------------------");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Run the task immediately
        fetchAndProcessTask.run();

        // Schedule the task to run daily at 6 AM
        timer.scheduleAtFixedRate(fetchAndProcessTask, getNext6AM(), 24 * 60 * 60 * 1000); // Repeat every 24 hours= 24 * 60 * 60 * 1000
    }




    public static JsonObject fetchJsonFromUrl(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Add a User-Agent header to the request
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private static Restaurant createUnicaMainObject(JsonObject jsonObject) {
        // Handle nulls and provide default values
        String currentRestaurantName = jsonObject.has("RestaurantName") && !jsonObject.get("RestaurantName").isJsonNull()
                ? jsonObject.get("RestaurantName").getAsString()
                : "Restaurant Closed";

        String currentRestaurantUrl = jsonObject.has("RestaurantUrl") && !jsonObject.get("RestaurantUrl").isJsonNull()
                ? jsonObject.get("RestaurantUrl").getAsString()
                : "No URL available";

        String footer = jsonObject.has("Footer") && !jsonObject.get("Footer").isJsonNull()
                ? jsonObject.get("Footer").getAsString()
                : "No footer available";

        JsonElement priceHeader = jsonObject.has("PriceHeader") && !jsonObject.get("PriceHeader").isJsonNull()
                ? jsonObject.get("PriceHeader")
                : new JsonObject(); // Assuming priceHeader is an Object, replace with an empty object or suitable default

        JsonElement errorText = jsonObject.has("ErrorText") && !jsonObject.get("ErrorText").isJsonNull()
                ? jsonObject.get("ErrorText")
                : new JsonObject(); // Assuming errorText is an Object, replace with an empty object or suitable default

        // Process the MenusForDays array
        List<MenusForDay> menusForDaysList = new ArrayList<>();

        if (jsonObject.has("MenusForDays") && !jsonObject.get("MenusForDays").isJsonNull()) {
            JsonArray menusForDaysArray = jsonObject.getAsJsonArray("MenusForDays");
            for (JsonElement menusForDaysElement : menusForDaysArray) {
                JsonObject menusForDaysObject = menusForDaysElement.getAsJsonObject();
                String date = menusForDaysObject.has("Date") && !menusForDaysObject.get("Date").isJsonNull()
                        ? menusForDaysObject.get("Date").getAsString()
                        : "No date available";

                String lunchTime = menusForDaysObject.has("LunchTime") && !menusForDaysObject.get("LunchTime").isJsonNull()
                        ? menusForDaysObject.get("LunchTime").getAsString()
                        : "No lunch time available";

                // Process the SetMenus array
                List<SetMenu> setMenusList = new ArrayList<>();
                if (menusForDaysObject.has("SetMenus") && !menusForDaysObject.get("SetMenus").isJsonNull()) {
                    JsonArray setMenusArray = menusForDaysObject.getAsJsonArray("SetMenus");
                    for (JsonElement setMenusElement : setMenusArray) {
                        SetMenu setMenu = getSetMenu(setMenusElement);
                        setMenusList.add(setMenu);
                    }
                }

                // Create MenusForDay object
                MenusForDay menusForDay = new MenusForDay(date, lunchTime, setMenusList);
                menusForDaysList.add(menusForDay);
            }
        }

        // Create Restaurant object (UnicaMain)
        return new Restaurant(currentRestaurantName, currentRestaurantUrl, priceHeader, footer, menusForDaysList, errorText);
    }


    private static SetMenu getSetMenu(JsonElement setMenusElement) {
        JsonObject setMenusObject = setMenusElement.getAsJsonObject();

        // Handle null values with default Finnish text
        Integer sortOrder = setMenusObject.has("SortOrder") && !setMenusObject.get("SortOrder").isJsonNull()
                ? setMenusObject.get("SortOrder").getAsInt()
                : 0; // Default to 0 if SortOrder is null

        String name = setMenusObject.has("Name") && !setMenusObject.get("Name").isJsonNull()
                ? setMenusObject.get("Name").getAsString()
                : "Ei nimeä"; // Default to "Ei nimeä" (No name) if Name is null

        String price = setMenusObject.has("Price") && !setMenusObject.get("Price").isJsonNull()
                ? setMenusObject.get("Price").getAsString()
                : "Hinta ei saatavilla"; // Default to "Hinta ei saatavilla" (Price not available) if Price is null

        // Process the Components array
        List<String> componentsList = new ArrayList<>();
        JsonArray componentsArray = setMenusObject.getAsJsonArray("Components");
        for (JsonElement component : componentsArray) {
            componentsList.add(component.getAsString());
        }

        // Create SetMenu object
        SetMenu setMenu = new SetMenu(sortOrder, name, price, componentsList);
        return setMenu;
    }


    // Calculate the time of the next 6 AM
    private static Date getNext6AM() {
        Calendar calendar = Calendar.getInstance();

        // Set the time to 6 AM today
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // If the time is already past 6 AM today, schedule for 6 AM tomorrow
        if (calendar.getTime().before(new Date())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return calendar.getTime();
    }
}
