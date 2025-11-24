package o.e;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class GamesWebInitializer extends AbstractSecurityWebApplicationInitializer {
    public GamesWebInitializer() {
        super(AppConfig.class);
    }
//    @Override //....
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
//
//    @Override //...
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[]{SecurityConfig.class};
//    }
//
//    @Override //.....
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class<?>[]{SecurityConfig.class, AppConfig.class};
//    }
}

