package cc.before30.websocketex.controller;

import cc.before30.websocketex.domain.BrowserSession;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebsocketEventListener
 *
 * @author before30
 * @since 2020/07/23
 */

@Component
@Slf4j
public class WebsocketEventListener {

    private static Map<String, BrowserSession> browserSessionMap = new ConcurrentHashMap<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        log.info("Received a new web socket connection. Session ID : [{}]", headerAccessor.getSessionId());

    }

    @EventListener
    public void handleWebSocketDisConnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = findBrowserSessionId(headerAccessor.getSessionId());
        if(sessionId != null) {
            browserSessionMap.remove(headerAccessor.getSessionId());
        }

        log.info("Web socket session closed. Message : [{}]", event.getMessage());
    }

    public String findBrowserSessionId(String sessionId) {
        String session = null;

        for (Map.Entry<String, BrowserSession> entry : browserSessionMap.entrySet()) {
            if (entry.getKey().equals(sessionId)) {
                session = entry.getKey();
            }
        }

        return session;
    }

    public synchronized void registerBrowserSession(BrowserSession browserSession, String sessionId) {
        browserSessionMap.put(sessionId, browserSession);
    }

    public List<String> findSessionIdsByMemberId(String username) {
        List<String> sessionIdList = new ArrayList<String>();

        for (Map.Entry<String, BrowserSession> entry : browserSessionMap.entrySet()) {
            if (entry.getValue().getSessionId().equals(username)) {
                sessionIdList.add(entry.getKey());
            }
        }

        return sessionIdList;
    }

    public MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);

        return headerAccessor.getMessageHeaders();
    }

    public Set<String> sessionIds() {
        return browserSessionMap.keySet();
    }

    public String getUserName(String sessionId) {
        return browserSessionMap.get(sessionId).getUserName();
    }
}
