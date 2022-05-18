package dev.vnco.hub.hotbar;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter @Setter
public class HotbarItem {

    private String permission;
    private ItemStack itemStack;
    private List<String> actions;

}
