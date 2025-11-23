package o.e;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class GamesWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

    @Override //....
    protected String[] getServletMappings(){
        return new String[] { "/" };
    }

    @Override //...
    protected Class<?>[] getRootConfigClasses(){
        return new Class<?>[] { AppConfig.class };
    }

    @Override //.....
    protected Class<?>[] getServletConfigClasses(){
        return new Class<?>[] { AppConfig.class  };
    }
}

