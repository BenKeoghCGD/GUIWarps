package uk.co.benkeoghcgd.api.GUIWarps.Data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import uk.co.benkeoghcgd.api.AxiusCore.API.Utilities.DataHandler;
import uk.co.benkeoghcgd.api.AxiusCore.Utils.Logging;
import uk.co.benkeoghcgd.api.GUIWarps.GUIWarps;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WarpsYML extends DataHandler {

    static WarpsYML instance;

    public static WarpsYML getInstance() {
        return instance;
    }

    public WarpsYML() {
        super(GUIWarps.getInstance(), "Warps");
        instance = this;
    }

    @Override
    protected void saveDefaults() {
        setData("spawn.World", "world", false);
        setData("spawn.Location", "0:0:0:0:0", false);
        setData("spawn.Icon", "GRASS_BLOCK", false);
    }

    public static void updateWarp(String name, Location newLocation, Material newIcon) {
        name = name.toLowerCase();
        instance.setData(name + ".World", Objects.requireNonNull(newLocation.getWorld()).getName(), true);
        instance.setData(name + ".Location",
                newLocation.getBlockX() + ":" + newLocation.getBlockY() + ":" + newLocation.getBlockZ() + ":" + newLocation.getYaw() + ":" + newLocation.getPitch(),
                true);
        instance.setData(name + ".Icon", newIcon.name(), true);

        instance.loadData();
    }

    public static Location getWarpLocation(String name) {
        name = name.toLowerCase();
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(instance.file);
        String[] s = Objects.requireNonNull(cfg.getString(name + ".Location")).split(":");
        return new Location(Bukkit.getWorld(instance.data.get(name + ".World").toString()),
                Double.parseDouble(s[0]),
                Double.parseDouble(s[1]),
                Double.parseDouble(s[2]),
                Float.parseFloat(s[3]),
                Float.parseFloat(s[4]));
    }

    public static Material getWarpIcon(String name) {
        name = name.toLowerCase();
        return Material.matchMaterial(instance.data.get(name + ".Icon").toString());
    }

    public static List<String> getWarpNames() {
        YamlConfiguration y = YamlConfiguration.loadConfiguration(instance.file);
        return Objects.requireNonNull(y.getConfigurationSection("")).getKeys(false).stream().toList();
    }

    public static List<String> getAvailableWarps(Player target) {
        List<String> l = new ArrayList<>();
        for(String s : getWarpNames()) {
            if(target.hasPermission("guiwarps.warp." + s)) l.add(s);
        }

        return l;
    }

    public static void deleteWarp(String name) {
        name = name.toLowerCase();
        instance.setData(name + ".Location", null, true);
        instance.setData(name + ".World", null, true);
        instance.setData(name + ".Icon", null, true);
        instance.setData(name, null, true);

        instance.loadData();
    }
}
