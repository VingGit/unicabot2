
package DiscordBot;

import Unica.*;
        import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class test extends ListenerAdapter {
    private static Restaurant selectedRestaurant;

    public static void main(String[] args) throws LoginException {
        // Start the server
        unicaServer.startServer();
        JDABuilder builder = JDABuilder.createDefault("");  // Replace with your bot's token
        builder.addEventListeners(new test());
        JDA jda = builder.build();

        try {
            jda.awaitReady();  // Wait until JDA is fully loaded
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        jda.updateCommands().queue(commands -> {
            System.out.println("Cleared all global commands.");

            // Register the /menu command with a restaurant option that supports autocomplete
            SlashCommandData command = Commands.slash("menu", "Get the menu of a restaurant")
                    .addOption(OptionType.STRING, "restaurant", "Select the restaurant", true, true);

            jda.updateCommands().addCommands(command).queue();

            System.out.println("Registered new global /menu command.");
        });
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("menu")) {
            String restaurantName = Objects.requireNonNull(event.getOption("restaurant")).getAsString();

            // Retrieve the restaurant from the Restaurants HashMap
            selectedRestaurant = unicaServer.Restaurants.get(restaurantName);

            if (selectedRestaurant == null) {
                event.reply("Invalid restaurant name! Please try again.").queue();
                return;
            }

            // Create the dropdown menu for selecting the day
            StringSelectMenu daySelectMenu = EmbedMenu.createDaySelectMenu(selectedRestaurant);

            // Create the initial embed for the first day (or any default day)
            MenusForDay defaultDay = selectedRestaurant.getMenusForDays().get(0);  // Default to the first available day
            MessageEmbed initialEmbed = EmbedMenu.createMenuEmbed(selectedRestaurant, defaultDay);

            // Send the initial embed with the dropdown menu attached
            event.replyEmbeds(initialEmbed)
                    .addActionRow(daySelectMenu)
                    .queue();
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if (event.getName().equals("menu") && event.getFocusedOption().getName().equals("restaurant")) {
            // Retrieve the current list of restaurant names from the Restaurants HashMap (keys)
            List<String> options = unicaServer.Restaurants.keySet().stream()
                    .filter(name -> name.toLowerCase().startsWith(event.getFocusedOption().getValue().toLowerCase()))
                    .limit(25) // Discord limits the number of options to 25
                    .collect(Collectors.toList());

            // Send the autocomplete choices back to Discord
            event.replyChoices(options.stream()
                            .map(option -> new net.dv8tion.jda.api.interactions.commands.Command.Choice(option, option))
                            .collect(Collectors.toList()))
                    .queue();
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("day-select")) {
            String selectedDate = event.getValues().get(0); // Get the selected date (the value of the selected option)

            if (selectedRestaurant == null) {
                event.reply("Invalid restaurant selected.").setEphemeral(true).queue();
                return;
            }

            // Find the corresponding menu for the selected date
            MenusForDay selectedDay = selectedRestaurant.getMenusForDays().stream()
                    .filter(day -> day.getDate().equals(selectedDate))
                    .findFirst()
                    .orElse(null);

            if (selectedDay == null) {
                event.reply("Menu for the selected day is not available.").setEphemeral(true).queue();
                return;
            }

            // Create and send the updated embed with the selected day's menu
            MessageEmbed menuEmbed = EmbedMenu.createMenuEmbed(selectedRestaurant, selectedDay);
            event.editMessageEmbeds(menuEmbed).queue(); // Edit the existing message with the new embed
        }
    }
}
