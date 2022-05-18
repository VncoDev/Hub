package dev.vnco.hub.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.List;

/**
 * ItemCreator | vLib
 * Author: OldVnco | ItemBuilder Fork
 */
@Getter @Setter
public class ItemCreator {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    private Material material;

    public ItemCreator(Material material){
        this(material, 1, (short) 0);
    }

    public ItemCreator(Material material, int amount){
        this(new ItemStack(material, amount, (short) 0));
    }

    public ItemCreator(Material material, short data){
        this(new ItemStack(material, 1, data));
    }

    public ItemCreator(Material material, String amount, short data){
        this(new ItemStack(material, Integer.parseInt(amount), data));
    }

    public ItemCreator(Material material, int amount, short data){
        this(new ItemStack(material, amount, data));
    }

    public ItemCreator(ItemStack itemStack){
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
        this.material = itemStack.getType();
    }

    public ItemCreator setMaterial(Material material){
        itemStack = new ItemStack(material);
        return this;
    }

    public ItemCreator setAmount(int amount){
        itemStack.setAmount(amount);
        return this;
    }

    public ItemCreator setData(int data){
        itemStack.setData(new MaterialData((byte) data));
        return this;
    }

    public ItemCreator setDisplayName(String displayName){
        itemMeta.setDisplayName(ChatUtil.translate(displayName));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator setLore(List<String> lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(ChatUtil.translate(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator setLore(String... lore){
        setLore(Arrays.asList(lore));
        return this;
    }

    public ItemCreator setColor(Color color, boolean b){
        if (b) {
            if (material == Material.LEATHER_BOOTS || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_LEGGINGS || material == Material.LEATHER_HELMET) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
                leatherArmorMeta.setColor(color);
                itemStack.setItemMeta(leatherArmorMeta);
            }
        }

        return this;
    }

    public ItemCreator setDurability(int durability){
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemCreator setGlow(boolean glow){
        if (glow){
            itemMeta.addEnchant(new FakeGlow(70), 2, false);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemCreator setUnbreakable(boolean unbreakable) {
        itemMeta.spigot().setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator setSkullOwner(String skullOwner, boolean b) {
        if (b) {
            if (material == Material.SKULL_ITEM) {
                SkullMeta skullMeta = (SkullMeta) itemMeta;
                skullMeta.setOwner(skullOwner);
                itemStack.setItemMeta(skullMeta);
            }
        }
        return this;
    }

    public ItemCreator addEnchant(Enchantment enchantment, int level){
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemCreator getEnchantedBook(Enchantment enchantment, int level){
        if (material == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) itemMeta;
            storageMeta.addStoredEnchant(enchantment, level, true);
            itemStack.setItemMeta(storageMeta);
        }
        return this;
    }

    public ItemStack build(){
        return itemStack;
    }

    private static class FakeGlow extends EnchantmentWrapper {

        public FakeGlow(int id) {
            super(id);
        }

        public int getStartLevel() {
            return 1;
        }

        public int getMaxLevel() {
            return 10;
        }

        @Override
        public EnchantmentTarget getItemTarget() {
            return super.getItemTarget();
        }

        @Override
        public boolean canEnchantItem(ItemStack itemStack) {
            return super.canEnchantItem(itemStack);
        }

        @Override
        public Enchantment getEnchantment() {
            return super.getEnchantment();
        }

        public boolean conflictsWith(Enchantment enchantment) {
            return false;
        }

    }

}
