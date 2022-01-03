package net.foggies.moderngens.impl.commands;

import foggielib.command.SubcommandBase;
import foggielib.player.PlayerUtils;
import net.foggies.moderngens.ModernGens;
import net.foggies.moderngens.impl.service.GeneratorService;
import net.foggies.moderngens.impl.storage.GeneratorStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenGiveCMD extends SubcommandBase {

    private ModernGens modernGens;
    private GeneratorStorage generatorStorage;
    private GeneratorService generatorService;

    public GenGiveCMD(ModernGens modernGens) {
        this.modernGens = modernGens;
        this.generatorStorage = modernGens.getGeneratorStorage();
        this.generatorService = modernGens.getGeneratorService();
    }

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getSyntax() {
        return "&cUsage: /gens give <player> <type> <amount>";
    }

    @Override
    public void perform(CommandSender sender, String[] args) throws IOException, ClassNotFoundException {

        if(args.length == 4){

            Player target = PlayerUtils.get(args[1]);
            String type = args[2];
            int amount = Integer.parseInt(args[3]);

            if(target == null){
                PlayerUtils.message(sender, "&cThis isn't a valid player.");
                return;
            }

            if(amount <= 0) {
                PlayerUtils.message(sender, "&cPlease enter an amount greater than 0.");
                return;
            }

            generatorService.giveGenerator(target, type);

        } else {
            PlayerUtils.message(sender, getSyntax());
        }

    }

    @Override
    public List<String> getParameters(CommandSender sender, String[] args) {
        return new ArrayList<>(generatorStorage.getGeneratorItems().keySet());
    }
}
