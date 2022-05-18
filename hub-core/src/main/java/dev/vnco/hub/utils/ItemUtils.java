package dev.vnco.hub.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class ItemUtils {

    public ItemStack parse(String itemID) {
        ItemStack nullItem = new ItemStack(Material.AIR);

        String[] split = itemID.split(":");

        Material material = Material.getMaterial(Integer.parseInt(split[0]));

        if (material == null) {
            return nullItem;
        }

        ItemStack itemStack = new ItemStack(material, 1, Short.parseShort(split[1]));

        if (itemStack.getData() == null) {
            return nullItem;
        }

        return itemStack;
    }

}
