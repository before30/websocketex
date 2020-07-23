package cc.before30.websocketex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginMessage
 *
 * @author before30
 * @since 2020/07/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginMessage {
    private String include;
}
