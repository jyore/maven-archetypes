package ${package}.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


@Order(2)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST,DispatcherType.FORWARD);

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {WebMvcConfiguration.class}; 
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebMvcConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/*"};
    }
    
    @Override
    public void onStartup(ServletContext servletContext)throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(RequestContextListener.class);
        
        // Add filter registrations here. This can be done by calling the register
        // method provided in this class. See javadoc for the method below
        // Example:
        // register(servletContext,"myFilterBean","/*");
        
    }

    /**
     * Dynamically register servlet filters
     *
     * @param servletContext The {@link ServletContext} to apply the filter to
     * @param beanName The name of the Spring bean for the filter to register
     * @param mapping The filter mapping to apply this filter to
     */
    protected void register(ServletContext servletContext, String beanName, String mapping) {
        javax.servlet.FilterRegistration.Dynamic filter = servletContext.addFilter(beanName, DelegatingFilterProxy.class);
        filter.addMappingForUrlPatterns(dispatcherTypes, false, mapping);
    }

}
