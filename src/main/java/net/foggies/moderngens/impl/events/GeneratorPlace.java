package net.foggies.moderngens.impl.events;

import net.foggies.moderngens.ModernGens;
import net.foggies.moderngens.impl.service.GeneratorService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.IOException;

public class GeneratorPlace implements Listener {

    private ModernGens modernGens;
    private GeneratorService generatorService;

    public GeneratorPlace(ModernGens modernGens) {
        this.modernGens = modernGens;
        this.generatorService = modernGens.getGeneratorService();
    }

    @EventHandler
    public void onGenPlace(BlockPlaceEvent e) throws IOException, ClassNotFoundException {
        generatorService.placeGenerator(e);
    }


}
