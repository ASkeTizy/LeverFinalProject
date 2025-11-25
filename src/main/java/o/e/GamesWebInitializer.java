package o.e;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class GamesWebInitializer extends AbstractSecurityWebApplicationInitializer {
    public GamesWebInitializer() {
        super(AppConfig.class);
    }

}

