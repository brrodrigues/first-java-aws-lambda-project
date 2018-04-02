package rio.brunorodrigues.function.filter;

import com.amazonaws.serverless.proxy.RequestReader;
import com.amazonaws.serverless.proxy.model.ApiGatewayRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class AWSIdentifyFilter implements Filter {

    public static final String COGNITO_IDENTITY_ATTRIBUTE = "rio.brunorodrigues.function.AppId";

    private static Logger log = LoggerFactory.getLogger(AWSIdentifyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Object apiGwContext = servletRequest.getAttribute(RequestReader.API_GATEWAY_CONTEXT_PROPERTY);
        if (apiGwContext == null) {
            log.warn("API Gateway context is null");
            filterChain.doFilter(servletRequest, servletResponse);
        }
        if (!ApiGatewayRequestContext.class.isAssignableFrom(apiGwContext.getClass())) {
            log.warn("API Gateway context object is not of valid type");
            filterChain.doFilter(servletRequest, servletResponse);
        }

        ApiGatewayRequestContext ctx = (ApiGatewayRequestContext)apiGwContext;
        if (ctx.getIdentity() == null) {
            log.warn("Identity context is null");
            filterChain.doFilter(servletRequest, servletResponse);
        }
        String cognitoIdentityId = ctx.getIdentity().getCognitoIdentityId();
        if (cognitoIdentityId == null || "".equals(cognitoIdentityId.trim())) {
            log.warn("Cognito identity id in request is null");
        }
        servletRequest.setAttribute(COGNITO_IDENTITY_ATTRIBUTE, cognitoIdentityId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
