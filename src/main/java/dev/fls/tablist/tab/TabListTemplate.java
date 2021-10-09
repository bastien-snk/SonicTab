package dev.fls.tablist.tab;

import dev.fls.tablist.tab.page.OptionalPart;
import dev.fls.tablist.tab.page.parts.Footer;
import dev.fls.tablist.tab.page.parts.Header;
import dev.fls.tablist.tab.page.parts.body.Body;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TabListTemplate {

    private final Header header = new Header();
    private final Footer footer = new Footer();
    private final Body body = new Body(this);
    private final List<UUID> displayedTo = new ArrayList<>();

    public Header getHeader() {
        return header;
    }

    public Footer getFooter() {
        return footer;
    }

    public Body getBody() {
        return body;
    }

    public List<UUID> getDisplayedTo() {
        return displayedTo;
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
