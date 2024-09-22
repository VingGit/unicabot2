package DiscordBot;

import Unica.MenusForDay;
import Unica.SetMenu;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import Unica.Restaurant;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.awt.Color;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class EmbedMenu {

    public static MessageEmbed createMenuEmbed(Restaurant restaurant, MenusForDay selectedDay) {
        // Create a new EmbedBuilder instance
        EmbedBuilder embedBuilder = new EmbedBuilder();

        // Set the title of the embed to the restaurant's name
        embedBuilder.setTitle(restaurant.getRestaurantName());
        embedBuilder.setUrl(restaurant.getRestaurantUrl());

        // Optionally, set a color for the embed
        embedBuilder.setColor(2579779); // You can change the color as needed

        // Parse and format the date
        LocalDate date = LocalDate.parse(selectedDay.getDate().substring(0, 10)); // Extract the date part and parse it
        Locale finnishLocale = Locale.forLanguageTag("fi"); // Finnish locale
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, finnishLocale).toUpperCase();
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        int weekNumber = date.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        // Add description or fields
        StringBuilder description = new StringBuilder();

        description.append(dayOfWeek).append(", ").append(formattedDate).append("\n");
        description.append("Auki välillä: ").append(selectedDay.getLunchTime()).append("\n\n");
        description.append("VIIKKO: ").append(weekNumber).append("\n");

        embedBuilder.setDescription(description.toString());

        for (SetMenu menu : selectedDay.getSetMenus()){
            embedBuilder.addField(new MessageEmbed.Field(menu.getName()+"\n"+"Hinta: "+menu.getPrice(), formatComponents(menu.getComponents()), true));
        }

        // Optionally, add a footer or timestamp
        embedBuilder.setFooter(restaurant.getFooter() + "\n");

        // Build the embed and return it
        return embedBuilder.build();
    }

    private static String formatComponents(List<String> components){
        StringBuilder value= new StringBuilder();
        for (String component :components){
            String[] allergens = component.split("[(]");
            value.append(allergens[0]).append("\n(").append(allergens[1]).append("\n");
        }
        return value.toString();
    }

    public static StringSelectMenu createDaySelectMenu(Restaurant restaurant) {
        StringSelectMenu.Builder menuBuilder = StringSelectMenu.create("day-select").setPlaceholder("Valitse päivä...");

        Locale finnishLocale = Locale.forLanguageTag("fi");

        for (MenusForDay day : restaurant.getMenusForDays()) {
            LocalDate date = LocalDate.parse(day.getDate().substring(0, 10));
            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, finnishLocale).toUpperCase();
            String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            menuBuilder.addOption(dayOfWeek + " " + formattedDate, day.getDate());
        }

        return menuBuilder.build();
    }
}