package be.ing.fundtransfer.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableAutoConfiguration
public class AddCustomLocations {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };
    @Bean
    WebMvcConfigurer configurer() {

        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                if (!registry.hasMappingForPattern("/**")) {
                    registry.addResourceHandler("/**").addResourceLocations(
                            "classpath:/static/test/");
                }
                if (!registry.hasMappingForPattern("/**")) {
                    registry.addResourceHandler("/**").addResourceLocations(
                            CLASSPATH_RESOURCE_LOCATIONS);
                }
            }
        };

    }
}
