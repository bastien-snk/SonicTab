package dev.fls.tablist;

import dev.fls.tablist.tab.TabListTemplate;
import dev.fls.tablist.tab.page.parts.body.Body;
import dev.fls.tablist.tab.page.parts.body.BodyLine;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TabListAPI extends JavaPlugin implements Listener {

    private TabListTemplate template;

    @Override
    public void onEnable() {
        template = new TabListTemplate(this);
        template.getHeader().addLines("", "§dTest", "");
        template.getFooter().addLines("", "§dTest");

        Body body = template.getBody();
        body.addLine(new BodyLine("Test", 0,0,10));
        body.setColumns(2, 10);

        body.setRemoveBaseLines(true);

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        template.show(event.getPlayer());
    }
}
