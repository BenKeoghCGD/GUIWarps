package uk.co.benkeoghcgd.api.GUIWarps.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.benkeoghcgd.api.GUIWarps.Data.ConfigYML;
import uk.co.benkeoghcgd.api.GUIWarps.Data.WarpsYML;
import uk.co.benkeoghcgd.api.GUIWarps.GUIWarps;
import uk.co.benkeoghcgd.api.GUIWarps.GUIs.WarpGUI;
import uk.co.benkeoghcgd.api.AxiusCore.API.AxiusCommand;

import java.util.concurrent.atomic.AtomicBoolean;

public class WarpCommand extends AxiusCommand {

    GUIWarps plugin;
    ConfigYML cyml;

    public WarpCommand(GUIWarps instance) {
        super(instance, false, "warps", "Show all warps via GUI", instance.getNameFormatted() + " §7Usage: /warps",
                "warp", "wps", "wp");
        this.plugin = instance;
        cyml = ConfigYML.getInstance();
        setPermission("guiwarps.warps");
    }

    @Override
    public boolean onCommand(CommandSender sndr, Command command, String s, String[] args) {
        if(args.length == 1) {
            AtomicBoolean teleported = new AtomicBoolean(false);

            WarpsYML.getWarpNames().forEach(i -> {
                if(i.equalsIgnoreCase(args[0])) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        ((Player)sndr).teleport(WarpsYML.getWarpLocation(i));
                        sndr.sendMessage(plugin.getNameFormatted() + "§7 Teleported to: §x§f§b§b§c§0§0" + i);
                    }, 1L);
                    teleported.set(true);
                    return;
                }
            });

            if(!teleported.get()) sndr.sendMessage(plugin.getNameFormatted() + "§7 Could not find a warp by that name.");
            return true;
        }

        if(WarpsYML.getAvailableWarps((Player) sndr).isEmpty()) {
            sndr.sendMessage(plugin.getNameFormatted() + "§7 You don't have permission for any available warps.");
            return true;
        }

        WarpGUI wgui = new WarpGUI(plugin, (Player) sndr);
        wgui.show((Player) sndr);

        return true;
    }
}
