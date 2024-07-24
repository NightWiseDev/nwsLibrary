package ru.nwsbukkitlibrary.library.MessagesUtils;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;
import ru.nwsbukkitlibrary.library.NwsBukkitLibrary;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {
    public void sendTitle(Player player, String message, String subMessage) {
        player.sendTitle(ChatColor.translateAlternateColorCodes('&', message),
                ChatColor.translateAlternateColorCodes('&', subMessage),
                10, 20, 10
        );
    }

    public void ActionBar(Player player, String message) {
        final int[] secondsLater = {5};
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendActionBar(ChatColor.translateAlternateColorCodes('&', message));
                secondsLater[0] -= 1;

                if (secondsLater[0] <= 0) {
                    cancel();
                }
            }
        }.runTaskTimer(NwsBukkitLibrary.getInstance(), 0, 20L);
    }

    //Сокращение кода по сопровождению звука
    public void SoundPlayer(Player player, String soundName) {
        try {
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        } catch (IllegalArgumentException e) {
            player.sendMessage("Звук " + soundName + " не найден.");
        }
    }
        public void sendMessage(CommandSender player, String message) {
            String formattedMessage = ChatColor.translateAlternateColorCodes('&', message);
            String[] lines = formattedMessage.split("\\\\n");
            String[] var5 = lines;
            int var6 = lines.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String line = var5[var7];
                player.sendMessage(line);
            }

        }

    public @Nullable String text(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String hex(String message) {
        Pattern pattern = Pattern.compile("(#[a-fA-F0-9]{6})");

        for(Matcher matcher = pattern.matcher(message); matcher.find(); matcher = pattern.matcher(message)) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
            char[] charArray = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            char[] var7 = charArray;
            int var8 = charArray.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                char c = var7[var9];
                builder.append("&" + c);
            }

            message = message.replace(hexCode, builder.toString());
        }

        return ChatColor.translateAlternateColorCodes('&', message).replace('&', '§');
    }

    public void configMessage(CommandSender player, String path, Map<String, String> args) {
        String text = NwsBukkitLibrary.getInstance().getConfig().getString(path);
        if (text == null) {
            text = path;
        }

        Map.Entry entry;
        for (Iterator var5 = args.entrySet().iterator(); var5.hasNext(); text = text.replace((CharSequence) entry.getKey(), (CharSequence) entry.getValue())) {
            entry = (Map.Entry) var5.next();
        }

        this.sendMessage(player, text);
    }
}


