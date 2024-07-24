package ru.library.ItemUtils;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.library.MessagesUtils.MessageUtil;

import java.lang.reflect.Field;
import java.util.List;

public class ItemBuilder {
    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder setAmount(int value) {
        this.itemStack.setAmount(value);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(MessageUtil.hex(name));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = this.itemStack.getItemMeta();

        for(int i = 0; i < lore.size(); ++i) {
            lore.set(i, MessageUtil.hex(lore.get(i)));
        }

        meta.setLore(lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addPersistentValue(String key, PersistentDataType persistentDataType, Object value) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(key), persistentDataType, value);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder removePersistentValue(String key) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().remove(NamespacedKey.fromString(key));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addEnchant(enchantment, level, true);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeEnchant(Enchantment enchantment) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.removeEnchant(enchantment);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setCustomModelData(int value) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setCustomModelData(value);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder hideEnchantment() {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        this.itemStack.setItemMeta(meta);
        return this;
    }
    public ItemBuilder createHead(String value){

        SkullMeta metaSkull = (SkullMeta) itemStack.getItemMeta();

        if(metaSkull != null) {
            GameProfile headProfile = new GameProfile(Bukkit.getOfflinePlayer("DummyPlayer").getUniqueId(),null);
            headProfile.getProperties().put("textures", new Property("textures", value));

            try {
                Field profileField = metaSkull.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(metaSkull, headProfile);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            itemStack.setItemMeta(metaSkull);
        }
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }

    public static void takeItem(Player player) {
        player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount() - 1);
    }
}
