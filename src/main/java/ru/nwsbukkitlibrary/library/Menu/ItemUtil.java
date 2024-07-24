package ru.nwsbukkitlibrary.library.Menu;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ru.nwsbukkitlibrary.library.MessagesUtils.MessageUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ItemUtil {


    public ItemStack createItem(Material material, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(MessageUtil.hex(displayName));
            List<String> hexLore = lore.stream()
                    .map(MessageUtil::hex)
                    .collect(Collectors.toList());
            meta.setLore(hexLore);
            item.setItemMeta(meta);
        }

        return item;
    }

    public ItemStack createHead(Material material, String displayName, List<String> lore, String value) {
        if (material != Material.PLAYER_HEAD && material != Material.DRAGON_HEAD) {
            throw new IllegalArgumentException("Material must be PLAYER_HEAD or DRAGON_HEAD");
        }

        ItemStack item = new ItemStack(material);
        SkullMeta metaSkull = (SkullMeta) item.getItemMeta();

        if (metaSkull != null) {
            metaSkull.setDisplayName(MessageUtil.hex(displayName));
            metaSkull.setLore(lore.stream().map(MessageUtil::hex).collect(Collectors.toList()));

            GameProfile headProfile = new GameProfile(UUID.randomUUID(), null);
            headProfile.getProperties().put("textures", new Property("textures", value));

            try {
                Field profileField = metaSkull.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(metaSkull, headProfile);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            item.setItemMeta(metaSkull);
        }
        return item;
    }


    public ItemStack addEnchantment(ItemStack item, Enchantment enchantment, int level) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addEnchant(enchantment, level, true);
            item.setItemMeta(meta);
        }
        return item;
    }

    public ItemStack hideFlags(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(ItemFlag.values());
            item.setItemMeta(meta);
        }
        return item;
    }
}