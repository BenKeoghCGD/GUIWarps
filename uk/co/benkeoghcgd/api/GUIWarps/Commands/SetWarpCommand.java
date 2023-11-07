package uk.co.benkeoghcgd.api.GUIWarps.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.benkeoghcgd.api.GUIWarps.Data.WarpsYML;
import uk.co.benkeoghcgd.api.GUIWarps.GUIWarps;
import uk.co.benkeoghcgd.api.AxiusCore.API.AxiusCommand;

public class SetWarpCommand extends AxiusCommand {

    public SetWarpCommand(GUIWarps instance) {
        super(instance, false, "setwarp", "Set a new warp at your current position", instance.getNameFormatted() + "§7 Usage: /setwarp <name> <material_name>",
                "setwarps", "sw", "setwp", "swp", "setwps");
        setPermission("guiwarps.set");
    }

    @Override
    public boolean onCommand(CommandSender sndr, Command command, String s, String[] args) {
        if(args.length != 2) {
            sndr.sendMessage(getUsage());
            return true;
        }

        if(WarpsYML.getWarpNames().contains(args[0])) {
            sndr.sendMessage(GUIWarps.getInstance().getNameFormatted() + "§7 A warp with that name already exists!");
            return true;
        }

        Material mat = Material.getMaterial(args[1]);
        if(mat == null) {
            sndr.sendMessage(GUIWarps.getInstance().getNameFormatted() + "§7 A material by that name doesn't exist!");
            return true;
        }

        WarpsYML.updateWarp(args[0], ((Player)sndr).getLocation(), mat);
        sndr.sendMessage(GUIWarps.getInstance().getNameFormatted() + "§7 Successfully saved a new warp: §x§f§b§b§c§0§0" + args[0]);
        return true;
    }
}
