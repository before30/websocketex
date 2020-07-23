package cc.before30.websocketex.controller;

import cc.before30.websocketex.domain.BrowserSession;
import cc.before30.websocketex.domain.Greeting;
import cc.before30.websocketex.domain.HelloMessage;
import cc.before30.websocketex.domain.LoginMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HelloController
 *
 * @author before30
 * @since 2020/07/21
 */

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final WebsocketEventListener websocketEventListener;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return Greeting.builder().content("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!").build();
    }

    @MessageMapping("/login")
    public Greeting login(SimpMessageHeaderAccessor headerAccessor, LoginMessage loginMessage, Principal principal) {
        List<Integer> includes = Arrays.stream(loginMessage.getInclude().split(",")).map(x -> Integer.valueOf(x)).collect(Collectors.toList());

        String uuid = headerAccessor.getSessionId();
        BrowserSession browserSession = new BrowserSession();
        browserSession.setSessionId(uuid);
        browserSession.setIncludes(includes);
        browserSession.setUserName(principal.getName());
        websocketEventListener.registerBrowserSession(browserSession, headerAccessor.getSessionId());
        return Greeting.builder().content("Good").build();
    }
}
