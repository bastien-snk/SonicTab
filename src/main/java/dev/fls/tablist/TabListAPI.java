package dev.fls.tablist;

import dev.fls.tablist.tab.TabListTemplate;
import dev.fls.tablist.tab.page.parts.body.Body;
import dev.fls.tablist.tab.page.parts.body.BodyLine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TabListAPI extends JavaPlugin implements Listener {

    private final TabListTemplate template = new TabListTemplate();

    @Override
    public void onEnable() {
        template.getHeader().addLines("", "§dTest");
        template.getFooter().addLines("", "§dTest");

        Body body = template.getBody();
        body.addLine(new BodyLine("§7----------------", 0, 0))
            .addLine(new BodyLine("§7----------------", 0, 1))
            .addLine(new BodyLine("§7----------------", 0, 3))
            .addLine(new BodyLine("§7----------------", 0, 4))
            .addLine(new BodyLine(" ", 0, 5))
            .addLine(new BodyLine(" ", 0, 6))
            .addLine(new BodyLine(" ", 0, 7))
            .addLine(new BodyLine(" ", 0, 8))
            .addLine(new BodyLine("§7----------------", 0, 9))
            .addLine(new BodyLine("§7----------------", 0, 90))
            .addLine(new BodyLine("§7----------------", 0, 91))
            .addLine(new BodyLine("§7----------------", 0, 92))
            .setColumns(3)
            .setRemoveBaseLines(true);

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        template.display(event.getPlayer());
    }
}
