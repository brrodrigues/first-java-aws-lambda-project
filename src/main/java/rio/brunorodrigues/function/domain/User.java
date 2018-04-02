package rio.brunorodrigues.function.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class User {

    private String id;
    private String name;

}
