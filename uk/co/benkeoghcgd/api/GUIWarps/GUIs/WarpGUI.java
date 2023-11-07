package uk.co.benkeoghcgd.api.GUIWarps.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import uk.co.benkeoghcgd.api.GUIWarps.Data.ConfigYML;
import uk.co.benkeoghcgd.api.GUIWarps.Data.WarpsYML;
import uk.co.benkeoghcgd.api.GUIWarps.GUIWarps;
import uk.co.benkeoghcgd.api.AxiusCore.API.Utilities.GUI;
import uk.co.benkeoghcgd.api.AxiusCore.Utils.GUIAssets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WarpGUI extends GUI {

    Player target;

    public WarpGUI(GUIWarps instance, Player target) {
        super(instance, GUIAssets.getInventoryRows(WarpsYML.getAvailableWarps(target).size()), instance.getNameFormatted() + "§7All Warps");
        this.target = target;
        Populate();
    }

    @Override
    protected void Populate() {
        for(String s : ((boolean)ConfigYML.getInstance().data.get("showWarpsWithoutPermissions") ? WarpsYML.getWarpNames() : WarpsYML.getAvailableWarps(target))) {
            List<String> lores = new ArrayList<>();
            if(target.hasPermission("guiwarps.warp." + s)) lores.add("§7Left-Click to Teleport");
            else lores.add("§cYou don't have permission for this warp.");
            if(target.hasPermission("guiwarps.delete")) lores.add("§c§lRight-Click to delete");
            ItemStack im = createGuiItem(WarpsYML.getWarpIcon(s), "§x§f§b§b§c§0§0§l" + s.toUpperCase(), lores);
            container.addItem(im);
        }
    }

    @Override
    protected void onInvClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;

        String warpName = ChatColor.stripColor(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName());
        if(e.getClick() == ClickType.LEFT && e.getWhoClicked().hasPermission("guiwarps.warp." + warpName)) {
            e.getWhoClicked().closeInventory();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                e.getWhoClicked().teleport(WarpsYML.getWarpLocation(warpName));
                e.getWhoClicked().sendMessage(GUIWarps.getInstance().getNameFormatted() + "§7 Teleported to: §x§f§b§b§c§0§0" + warpName);
            }, 1L);
            return;
        }

        if(e.getClick() == ClickType.RIGHT && e.getWhoClicked().hasPermission("guiwarps.delete")) {
            WarpsYML.deleteWarp(warpName);
            e.getWhoClicked().sendMessage(GUIWarps.getInstance().getNameFormatted() + "§7 Deleted warp: §x§f§b§b§c§0§0" + warpName);
            container.clear();
            Populate();
            return;
        }

        e.getWhoClicked().sendMessage(GUIWarps.getInstance().getNameFormatted() + "§c You do not have permission for this warp.");
    }
}
