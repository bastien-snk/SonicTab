package dev.fls.tablist.tab;

import dev.fls.tablist.tab.page.OptionalPart;
import dev.fls.tablist.tab.page.parts.Footer;
import dev.fls.tablist.tab.page.parts.Header;
import dev.fls.tablist.tab.page.parts.body.Body;
import org.bukkit.entity.Player;

public class TabListTemplate {

    private final Header header = new Header();
    private final Footer footer = new Footer();
    private final Body body = new Body();

    public Header getHeader() {
        return header;
    }

    public Footer getFooter() {
        return footer;
    }

    public Body getBody() {
        return body;
    }

    public void display(Player player) {
        OptionalPart.sendPacket(player, header, footer);
        body.display(player);
    }
}
