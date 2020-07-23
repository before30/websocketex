package cc.before30.websocketex.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BrowserSession
 *
 * @author before30
 * @since 2020/07/23
 */
@Data
public class BrowserSession {
    private String sessionId;

    private String userName;

    private List<Integer> includes = new ArrayList<>();

    public void addAll(Integer ... ids) {
        Arrays.stream(ids).forEach(includes::add);
    }
}
