package cc.before30.websocketex.config;

import java.security.Principal;

/**
 * StompPrincipal
 *
 * @author before30
 * @since 2020/07/24
 */
public class StompPrincipal implements Principal {

    private String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
