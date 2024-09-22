package DiscordBot;

import Unica.MenusForDay;
import Unica.RESTAURANTS;
import Unica.Restaurant;
import Unica.unicaServer;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class main {
    public static void main(String[] args) {
        // Start the server (this sets up the Timer and runs the fetch task every 3 seconds)
        unicaServer.startServer();

        // Set the current restaurant
        //unicaServer.setRestaurant(RESTAURANTS.ASSARIN_ULLAKKO);

        // Infinite loop to print "testing" every second
        System.out.println(unicaServer.Restaurants.get("Assarin Ullakko").getMenusForDays().size());
        List<MenusForDay> menus =unicaServer.Restaurants.get("Assarin Ullakko").getMenusForDays();
        int days =menus.size();
        Locale finnishLocale = Locale.forLanguageTag("fi"); // Finnish locale
        for (MenusForDay day :menus){
            //System.out.println(day.getDate());
            LocalDate date = LocalDate.parse(day.getDate().substring(0, 10)); // Extract the date part and parse it
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, finnishLocale).toUpperCase();
            System.out.println(dayOfWeek);
        }
    }
}
