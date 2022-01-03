package net.foggies.moderngens;

import foggielib.command.CommandBase;
import foggielib.config.ConfigManager;
import foggielib.menu.MenuListener;
import lombok.Getter;
import lombok.SneakyThrows;
import net.foggies.moderngens.impl.commands.GenCMD;
import net.foggies.moderngens.impl.events.GeneratorBreak;
import net.foggies.moderngens.impl.events.GeneratorOpen;
import net.foggies.moderngens.impl.events.GeneratorPlace;
import net.foggies.moderngens.impl.events.JoinQuit;
import net.foggies.moderngens.impl.service.GeneratorService;
import net.foggies.moderngens.impl.storage.GeneratorStorage;
import net.foggies.moderngens.impl.storage.PlayerDatabase;
import net.foggies.moderngens.impl.storage.PlayerStorage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public final class ModernGens extends JavaPlugin {

    private ConfigManager configManager;
    private GeneratorStorage generatorStorage;
    private PlayerStorage playerStorage;
    private GeneratorService generatorService;
    private PlayerDatabase playerDatabase;
    private Economy economy;

    @SneakyThrows
    @Override
    public void onEnable() {

        this.configManager = ConfigManager.getInstance();
        this.configManager.setPlugin(this);

        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.playerDatabase = new PlayerDatabase("localhost", "3306", "generators", "root", "");
        this.generatorStorage = new GeneratorStorage(this);
        this.playerStorage = new PlayerStorage(this);
        this.generatorService = new GeneratorService(this);

        registerEvents(new GeneratorBreak(this), new GeneratorPlace(this),
                new JoinQuit(this), new GeneratorOpen(this),
                new MenuListener());

        registerCommands(new GenCMD(this));

    }

    @SneakyThrows
    @Override
    public void onDisable() {
        playerStorage.unloadOnline();
        playerDatabase.getConnection().close();
    }

    private void registerEvents(Listener... listeners){
        Arrays.stream(listeners).forEach(event -> getServer().getPluginManager().registerEvents(event, this));
    }

    private void registerCommands(CommandBase... commands){
        Arrays.stream(commands).forEach(cmd -> getCommand(cmd.getName()).setExecutor(cmd));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }



}
