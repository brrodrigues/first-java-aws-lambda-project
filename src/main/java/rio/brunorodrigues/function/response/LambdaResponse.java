package rio.brunorodrigues.function.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@ToString
public class LambdaResponse {

    private String body;
    private int statusCode;
    private Map<String, String> headers = new HashMap<String, String>();


}
