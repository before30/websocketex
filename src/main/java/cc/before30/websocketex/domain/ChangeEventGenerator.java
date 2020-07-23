package cc.before30.websocketex.domain;

import cc.before30.websocketex.controller.WebsocketEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * ChangeEventGenerator
 *
 * @author before30
 * @since 2020/07/22
 */
@Component
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class ChangeEventGenerator {

    private final WebsocketEventListener websocketEventListener;
    private final SimpMessagingTemplate messageTemplate;


    public void greetings() {
        Greeting greeting = Greeting.builder().content("hello," + RandomStringUtils.randomAlphanumeric(10)).build();
        log.info("send message {}", greeting);
        messageTemplate.convertAndSend("/topic/greetings", greeting);
    }

    private ChangeEvent generateRandomEvent(long id) {
        return ChangeEvent.builder().id(id).price(RandomUtils.nextInt(100, (int) ((id + 1) * 100))).build();
    }

    @Scheduled(fixedRateString = "5000", initialDelay = 3000)
    public void greetingsAll() {
        Set<String> ids = websocketEventListener.sessionIds();
        Greeting greeting = Greeting.builder().content("hello," + RandomStringUtils.randomAlphanumeric(10)).build();

        for (String id: ids) {
            log.info("send message {}, {}, {}",id,  websocketEventListener.getUserName(id), greeting);
            messageTemplate.convertAndSendToUser(websocketEventListener.getUserName(id), "/topic/greetings", greeting);
        }
    }
}
