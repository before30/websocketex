package cc.before30.websocketex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ChangeEvent
 *
 * @author before30
 * @since 2020/07/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeEvent {

    private Long id;

    private Integer price;

}
