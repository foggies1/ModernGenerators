package net.foggies.moderngens.impl.storage;

import foggielib.items.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import net.foggies.moderngens.ModernGens;
import net.foggies.moderngens.api.IGeneratorStorage;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class GeneratorStorage implements IGeneratorStorage {

    private FileConfiguration config;
    private ModernGens modernGens;
    private Map<String, ItemStack> generatorItems; // Identifier -> Item

    public GeneratorStorage(ModernGens modernGens) {
        this.modernGens = modernGens;
        this.config = modernGens.getConfigManager().getConfig("generators.yml");
        this.generatorItems = loadGeneratorItems();
    }

    @Override
    public Optional<ItemStack> get(String identifier){
        return Optional.ofNullable(generatorItems.get(identifier));
    }

    private Map<String, ItemStack> loadGeneratorItems(){
        Map<String, ItemStack> gens = new HashMap<>();
        ConfigurationSection genSection = config.getConfigurationSection("generators");
        if(genSection == null) return null;

        for(String generator : genSection.getKeys(false)){
            ConfigurationSection itemSection = genSection.getConfigurationSection(generator + ".item");

            gens.put(generator, new ItemBuilder()
                    .setMaterial(Material.valueOf(itemSection.getString("material")))
                    .setName(itemSection.getString("name"))
                    .setLore(itemSection.getStringList("lore"))
                    .addEnchantment(Enchantment.ARROW_DAMAGE).hideFlags().create()
            );

        }

        return gens;
    }

}
