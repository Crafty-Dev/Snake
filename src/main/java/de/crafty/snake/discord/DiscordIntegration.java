package de.crafty.snake.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordIntegration {

    private static String CURRENT_PRESENCE = "Playing";


    private static boolean RUNNING;
    private static long startTimeStamp = 0;

    public static void setCurrentPresence(String currentPresence) {
        CURRENT_PRESENCE = currentPresence;
    }

    public static void start() {
        RUNNING = true;
        startTimeStamp = System.currentTimeMillis();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(discordUser -> System.out.println("Sucessfully connected to Discord as user: " + discordUser.username + "#" + discordUser.discriminator)).build();
        DiscordRPC.discordInitialize("979456456057311232", handlers, true);
        new Thread("Discord Callback") {
            @Override
            public void run() {
                while (RUNNING) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
        DiscordIntegration.updateRichPresence();
    }

    public static void shutdown() {
        if (!RUNNING)
            return;

        RUNNING = false;
        DiscordRPC.discordShutdown();
    }

    public static void updateRichPresence() {
        if (!RUNNING)
            return;
        DiscordRichPresence.Builder presenceBuilder = new DiscordRichPresence.Builder(CURRENT_PRESENCE);
        presenceBuilder.setBigImage("big", "");
        presenceBuilder.setSmallImage("small", "");
        presenceBuilder.setStartTimestamps(startTimeStamp);
        DiscordRPC.discordUpdatePresence(presenceBuilder.build());
    }

}
