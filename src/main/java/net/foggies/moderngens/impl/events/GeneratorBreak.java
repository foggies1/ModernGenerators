package net.foggies.moderngens.impl.events;

import net.foggies.moderngens.ModernGens;
import net.foggies.moderngens.impl.service.GeneratorService;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;

public class GeneratorBreak implements Listener {

    private ModernGens modernGens;
    private GeneratorService generatorService;

    public GeneratorBreak(ModernGens modernGens) {
        this.modernGens = modernGens;
        this.generatorService = modernGens.getGeneratorService();
    }

    @EventHandler
    public void onGenBreak(BlockBreakEvent e) throws IOException {
        if (generatorService.pickupGenerator(e)) {
            e.getBlock().setType(Material.AIR);
            e.setCancelled(true);
        }
    }

}
