package cc.before30.websocketex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Greeting
 *
 * @author before30
 * @since 2020/07/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Greeting {

    private String content;

}
