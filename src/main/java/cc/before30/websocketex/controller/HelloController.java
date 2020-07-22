package cc.before30.websocketex.controller;

import cc.before30.websocketex.domain.Greeting;
import cc.before30.websocketex.domain.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * HelloController
 *
 * @author before30
 * @since 2020/07/21
 */

@Controller
public class HelloController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return Greeting.builder().content("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!").build();
    }
}
