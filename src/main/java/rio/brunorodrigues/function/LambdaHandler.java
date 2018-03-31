package rio.brunorodrigues.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.java.Log;
import rio.brunorodrigues.function.response.LambdaResponse;

import java.util.Map;


@Log
public class LambdaHandler implements RequestHandler<Map,LambdaResponse> {

    public LambdaResponse handleRequest(Map map, Context context) {
        log.info(map.toString());
        LambdaResponse build = LambdaResponse.builder().headers(map).build();
        return build;
    }
}
