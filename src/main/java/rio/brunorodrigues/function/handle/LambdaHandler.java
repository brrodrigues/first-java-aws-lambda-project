package rio.brunorodrigues.function.handle;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.testutils.Timer;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import lombok.extern.java.Log;
import rio.brunorodrigues.function.Startup;
import rio.brunorodrigues.function.filter.AWSIdentifyFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.EnumSet;

@Log
public class LambdaHandler implements RequestStreamHandler{

    private static SpringBootLambdaContainerHandler handler;

    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Startup.class);

            // we use the onStartup method of the handler to register our custom filter
            handler.onStartup(servletContext -> {
                FilterRegistration.Dynamic registration = servletContext.addFilter("CognitoIdentityFilter", AWSIdentifyFilter.class);
                registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
            });

        } catch (ContainerInitializationException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not initialize users microservice", e);
        }
    }


    public LambdaHandler() {
        Timer.enable();
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        handler.proxyStream(inputStream, outputStream, context);

        // Por precaucao, o stream e fechado
        outputStream.close();
    }
}
