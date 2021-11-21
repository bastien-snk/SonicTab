package dev.fls.tablist;

import dev.fls.tablist.page.OptionalPart;
import dev.fls.tablist.page.parts.Footer;
import dev.fls.tablist.page.parts.Header;
import dev.fls.tablist.page.parts.body.Body;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TabListTemplate {

    private static JavaPlugin plugin;

    public TabListTemplate(JavaPlugin pl) {
        plugin = pl;
    }

    private final Header header = new Header();
    private final Footer footer = new Footer();
    private Body body = new Body();
    private final List<UUID> displayedTo = new ArrayList<>();

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public void show(Player player) {
        displayedTo.add(player.getUniqueId());
        OptionalPart.sendPacket(player, header, footer);
        body.show(player);
    }

    public void hide(Player player) {
        if(displayedTo.contains(player.getUniqueId())) displayedTo.remove(player.getUniqueId());
        OptionalPart.sendPacket(player, new Header(), new Footer());
        body.reset(player);
    }
}
