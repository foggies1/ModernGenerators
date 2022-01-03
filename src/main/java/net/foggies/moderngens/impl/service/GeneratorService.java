package net.foggies.moderngens.impl.service;

import foggielib.location.SimpleLocation;
import foggielib.persistent.PersistentData;
import foggielib.serializer.SerializeUtils;
import foggielib.string.StringUtils;
import net.foggies.moderngens.ModernGens;
import net.foggies.moderngens.impl.obj.GenPlayer;
import net.foggies.moderngens.impl.obj.Generator;
import net.foggies.moderngens.impl.obj.Resource;
import net.foggies.moderngens.impl.storage.GeneratorStorage;
import net.foggies.moderngens.impl.storage.PlayerStorage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Optional;

public class GeneratorService {

    // Add a new hashmap into generator storage that
    // corresponds to the generator identifier so we can retrieve the resources.

    private ModernGens modernGens;
    private GeneratorStorage generatorStorage;
    private PlayerStorage playerStorage;

    public GeneratorService(ModernGens modernGens) {
        this.modernGens = modernGens;
        this.generatorStorage = modernGens.getGeneratorStorage();
        this.playerStorage = modernGens.getPlayerStorage();
    }

    public boolean pickupGenerator(BlockBreakEvent e) throws IOException {
        Player player = e.getPlayer();
        SimpleLocation location = new SimpleLocation(e.getBlock().getLocation());

        Optional<GenPlayer> genPlayer = playerStorage.get(player.getUniqueId());
        if(genPlayer.isEmpty()) return false;
        Optional<Generator> generator = genPlayer.get().byLocation(location);
        if(generator.isEmpty()) return false;

        Optional<ItemStack> generatorItem = toItemStack(generator.get());
        if(generatorItem.isEmpty()) return false;

        if(player.getInventory().firstEmpty() == -1) return false;
        player.getInventory().addItem(generatorItem.get());
        return true;
    }

    public boolean placeGenerator(BlockPlaceEvent e) throws IOException, ClassNotFoundException {
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(!isGeneratorItem(itemStack) || itemStack.getType() == Material.AIR) return false;

        Optional<Generator> generator = toGenerator(itemStack);
        Optional<GenPlayer> genPlayer = playerStorage.get(player.getUniqueId());

        if(generator.isEmpty() || genPlayer.isEmpty()) return false;

        SimpleLocation simpleLocation = new SimpleLocation(e.getBlockPlaced().getLocation());
        genPlayer.get().add(simpleLocation, generator.get());

        return true;
    }

    public boolean giveGenerator(Player player, String type) throws IOException {
        Optional<ItemStack> generatorItem = generatorStorage.get(type);
        if(generatorItem.isEmpty()) return false;

        ItemStack generator = generatorItem.get();
        Generator generatorObject = new Generator(player.getUniqueId(), type, 1,
                new Resource("STONE", 100.0D));

        new PersistentData(generator, new NamespacedKey(modernGens, "GEN-TYPE")).setString(type);
        new PersistentData(generator, new NamespacedKey(modernGens, "GEN-DATA")).setString(generatorObject.serialize());

        if(player.getInventory().firstEmpty() == -1) return false;
        player.getInventory().addItem(generator);
        return true;
    }

    private Optional<Generator> toGenerator(ItemStack itemStack) throws IOException, ClassNotFoundException {
        return Optional.ofNullable((Generator) SerializeUtils.fromString(
                new PersistentData(itemStack, new NamespacedKey(modernGens, "GEN-DATA")).getString()
        ));
    }

    private Optional<ItemStack> toItemStack(Generator generator) throws IOException {
        Optional<ItemStack> generatorItem = generatorStorage.get(generator.getGeneratorType());
        if(generatorItem.isEmpty()) return Optional.empty();

        ItemStack gen = generatorItem.get();

        new PersistentData(gen, new NamespacedKey(modernGens, "GEN-TYPE")).setString(generator.getGeneratorType());
        new PersistentData(gen, new NamespacedKey(modernGens, "GEN-DATA")).setString(generator.serialize());

        return Optional.of(gen);
    }

    private boolean isGeneratorItem(ItemStack itemStack){
        return new PersistentData(itemStack, new NamespacedKey(modernGens, "GEN-TYPE")).containsKey();
    }

}
