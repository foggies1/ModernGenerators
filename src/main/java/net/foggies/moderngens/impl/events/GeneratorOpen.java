package net.foggies.moderngens.impl.events;

import foggielib.location.SimpleLocation;
import net.foggies.moderngens.ModernGens;
import net.foggies.moderngens.impl.menus.GeneratorMenu;
import net.foggies.moderngens.impl.obj.GenPlayer;
import net.foggies.moderngens.impl.obj.Generator;
import net.foggies.moderngens.impl.storage.PlayerStorage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;
import java.util.Optional;

public class GeneratorOpen implements Listener {

    private ModernGens modernGens;
    private PlayerStorage playerStorage;

    public GeneratorOpen(ModernGens modernGens) {
        this.modernGens = modernGens;
        this.playerStorage = modernGens.getPlayerStorage();
    }

    @EventHandler
    public void onGenClick(PlayerInteractEvent e) throws IOException {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();

        if(block == null) return;

        Optional<GenPlayer> genPlayer = playerStorage.get(player.getUniqueId());
        SimpleLocation simpleLocation = new SimpleLocation(block.getLocation());
        if(genPlayer.isEmpty()) {
            System.out.println("GEN PLAYER NULLLLL");
            return;
        }

        Optional<Generator> generator = genPlayer.get().byLocation(simpleLocation);
        if(generator.isEmpty()) {
            System.out.println("GENERATOR NULLLLL");
            return;
        }

        new GeneratorMenu(player, generator.get()).open();
    }

}
