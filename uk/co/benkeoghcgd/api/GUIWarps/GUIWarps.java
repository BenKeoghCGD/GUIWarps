package uk.co.benkeoghcgd.api.GUIWarps;

import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import uk.co.benkeoghcgd.api.AxiusCore.API.Enums.UpdaterMethod;
import uk.co.benkeoghcgd.api.AxiusCore.API.PluginData.PluginInfoData;
import uk.co.benkeoghcgd.api.AxiusCore.API.PluginData.PluginInfoDataButton;
import uk.co.benkeoghcgd.api.AxiusCore.API.PluginData.PublicPluginData;
import uk.co.benkeoghcgd.api.AxiusCore.Utils.GUIAssets;
import uk.co.benkeoghcgd.api.GUIWarps.Commands.SetWarpCommand;
import uk.co.benkeoghcgd.api.GUIWarps.Commands.UpdateWarpCommand;
import uk.co.benkeoghcgd.api.GUIWarps.Commands.WarpCommand;
import uk.co.benkeoghcgd.api.GUIWarps.Data.ConfigYML;
import uk.co.benkeoghcgd.api.GUIWarps.Data.WarpsYML;
import uk.co.benkeoghcgd.api.AxiusCore.API.AxiusPlugin;
import uk.co.benkeoghcgd.api.AxiusCore.Utils.Logging;
import uk.co.benkeoghcgd.api.AxiusCore.API.Utilities.GUI;

public class GUIWarps extends AxiusPlugin {
    int spigotResourceID = 113434, bStatsResourceID = 18505;
    ConfigYML cfgyml;
    WarpsYML waryml;

    @Override
    protected void Preregister() {
        this.instance = this;
        Logging.Log(this, "Running plugin pre-registry tasks");
        // EnableUpdater(spigotResourceID, VersionFormat.MajorMinorPatch, ".");
        Metrics m = new Metrics(this, bStatsResourceID);

        PublicPluginData ppd = new PublicPluginData();
        ppd.setVersionSeparator(".");
        ppd.setUpdaterMethod(UpdaterMethod.SPIGOT);
        ppd.setSpigotID(spigotResourceID);
        ppd.setPublicStatus(true);
        ppd.setRegisterStatus(true);

        SetPublicPluginData(ppd);

        PluginInfoData pid = new PluginInfoData();
        pid.addButton(GUI.createGuiItem(Material.WRITABLE_BOOK, "§c§lRELOAD CONFIG", "§7Reload all configuration."),
                new PluginInfoDataButton() {
                    @Override
                    public void execute() { }

                    @Override
                    public void execute(CommandSender sender) {
                        ConfigYML.getInstance().loadData();
                        WarpsYML.getInstance().loadData();

                        sender.sendMessage(getNameFormatted() + "§7 Reloaded all configuration.");
                    }
                });
        SetPluginInfoData(pid);

        Logging.Log(this, "Registering Data Files");
        cfgyml = new ConfigYML();
        waryml = new WarpsYML();

        Logging.Log(this, "Collecting Commands");
        // set warp
        commands.add(new WarpCommand(this));
        commands.add(new SetWarpCommand(this));
        commands.add(new UpdateWarpCommand(this));
    }

    @Override
    protected void Postregister() {
        setIcon(GUI.createGuiItem(Material.ENDER_PEARL, "§6§lGUIWarps"));
        setFormattedName("&x&f&b&b&c&0&0&lG&x&f&b&c&5&1&b&lU&x&f&c&c&d&3&6&lI&x&f&c&d&6&5&1&lW&x&f&c&d&e&6&b&la&x&f&c&e&7&8&6&lr&x&f&d&e&f&a&1&lp&x&f&d&f&8&b&c&ls");

        Logging.Log(this, "Registering Commands");
        registerCommands();
    }

    @Override
    protected void Stop() {

    }

    @Override
    protected void FullStop() {

    }

    static GUIWarps instance;

    public static GUIWarps getInstance() {
        return instance;
    }
}
