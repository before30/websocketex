package cc.before30.websocketex.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.fileslice.ArraySlice;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    private final SimpMessagingTemplate messageTemplate;

    @Scheduled(fixedRateString = "5000", initialDelay = 3000)
    public void greetings() {
        Greeting greeting = Greeting.builder().content("hello," + RandomStringUtils.randomAlphanumeric(10)).build();
        log.info("send message {}", greeting);
        messageTemplate.convertAndSend("/topic/greetings", greeting);
    }

    private ChangeEvent generateRandomEvent(long id) {
        return ChangeEvent.builder().id(id).price(RandomUtils.nextInt(100, (int) ((id + 1) * 100))).build();
    }
}
