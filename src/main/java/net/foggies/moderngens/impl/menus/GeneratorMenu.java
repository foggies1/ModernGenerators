package net.foggies.moderngens.impl.menus;

import foggielib.items.ItemBuilder;
import foggielib.menu.Menu;
import net.foggies.moderngens.impl.obj.Generator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class GeneratorMenu extends Menu {

    private Generator generator;

    public GeneratorMenu(Player player, Generator generator) {
        super(player);
        this.generator = generator;
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public String getTitle() {
        return "&d&lGenerator Home";
    }

    @Override
    public void setItems() {

        ItemStack upgradeItem = new ItemBuilder()
                .setMaterial(Material.EXPERIENCE_BOTTLE)
                .setName("&b&lUpgrade Generator")
                .setLore(
                        "&7Click here to open the",
                        "&dGenerator Upgrade &7menu."
                ).addEnchantment(Enchantment.ARROW_DAMAGE).hideFlags().create();

        ItemStack resourceItem = new ItemBuilder()
                .setMaterial(Material.IRON_INGOT)
                .setName("&b&lResource Picker")
                .setLore(
                        "&7Click here to pick a &dResource",
                        "&7to begin generating."
                ).addEnchantment(Enchantment.ARROW_DAMAGE).hideFlags().create();

        getInventory().setItem(11, resourceItem);
        getInventory().setItem(13, upgradeItem);

    }

    @Override
    public void handle(InventoryClickEvent e) throws IOException, ClassNotFoundException {

    }
}
