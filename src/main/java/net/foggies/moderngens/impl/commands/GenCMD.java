package net.foggies.moderngens.impl.commands;

import foggielib.command.CommandBase;
import foggielib.command.SubcommandBase;
import net.foggies.moderngens.ModernGens;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GenCMD extends CommandBase {

    private ModernGens modernGens;

    public GenCMD(ModernGens modernGens) {
        this.modernGens = modernGens;
    }

    @Override
    public String getName() {
        return "gens";
    }

    @Override
    public List<SubcommandBase> getSubcommands() {
        return Arrays.asList(
                new GenGiveCMD(modernGens)
        );
    }

    @Override
    public void perform(CommandSender sender, Command command, String label, String[] args) throws IOException, ClassNotFoundException {

    }
}
