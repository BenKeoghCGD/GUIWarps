package uk.co.benkeoghcgd.api.GUIWarps.Commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.benkeoghcgd.api.GUIWarps.Data.WarpsYML;
import uk.co.benkeoghcgd.api.GUIWarps.GUIWarps;
import uk.co.benkeoghcgd.api.AxiusCore.API.AxiusCommand;
import uk.co.benkeoghcgd.api.AxiusCore.API.AxiusPlugin;

public class UpdateWarpCommand extends AxiusCommand {

    public UpdateWarpCommand(AxiusPlugin instance) {
        super(instance, false, "updatewarp", "Alter the position, icon, or name of an existing warp",
                instance.getNameFormatted() + "§7 /updatewarp <name> [icon|name|location] [newIcon|newName",
                "updw", "uw", "updatewarps", "uws", "updws");
        setPermission("guiwarps.update");
    }

    @Override
    public boolean onCommand(CommandSender sndr, Command command, String s, String[] args) {
        switch (args.length) {
            case 3:
                if(!WarpsYML.getWarpNames().contains(args[0])) {
                    sndr.sendMessage(GUIWarps.getInstance().getNameFormatted() + "§7 No warp by this name exists!");
                    break;
                }

                switch (args[1].toLowerCase()) {
                    case "icon":
                    case "i":
                        Material m1 = Material.getMaterial(args[2]);
                        if(m1 == null) {
                            sndr.sendMessage(GUIWarps.getInstance().getNameFormatted() + "§7 No material by this name exists!");
                            break;
                        }

                        WarpsYML.updateWarp(args[0], WarpsYML.getWarpLocation(args[0]), m1);
                        sndr.sendMessage(GUIWarps.getInstance().getNameFormatted() + "§7 Warp updated successfully!");
                        break;

                    case "name":
                    case "n":
                        Location l = WarpsYML.getWarpLocation(args[0]);
                        Material m2 = WarpsYML.getWarpIcon(args[0]);

                        WarpsYML.deleteWarp(args[0]);

                        WarpsYML.updateWarp(args[2], l, m2);
                        sndr.sendMessage(GUIWarps.getInstance().getNameFormatted() + "§7 Warp updated successfully!");
                        break;

                    case "location":
                    case "l":
                        WarpsYML.updateWarp(args[0], ((Player) sndr).getLocation(), WarpsYML.getWarpIcon(args[0]));
                        break;

                    default:
                        sndr.sendMessage(getUsage());
                        break;
                }
                break;
            default:
                sndr.sendMessage(getUsage());
                break;
        }
        return true;
    }
}
