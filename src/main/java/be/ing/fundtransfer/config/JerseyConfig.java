package be.ing.fundtransfer.config;

import be.ing.fundtransfer.controller.LoginController;
import be.ing.fundtransfer.controller.MailController;
import be.ing.fundtransfer.controller.SendMessageController;
import be.ing.fundtransfer.controller.TransactionController;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.MediaType;
@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
        register(RequestContextFilter.class);
        register(WadlResource.class);
        registerEndpoints();
        register(CorsResponseFilter.class);
        configureSwagger();
    }

    private void registerEndpoints() {
        register(LoginController.class);
        register(MailController.class);
        register(SendMessageController.class);
        register(TransactionController.class);

    }
    private void configureSwagger() {
		register(ApiListingResource.class);
		register(SwaggerSerializers.class);
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0");
		beanConfig.setSchemes(new String[] { "http", "https" });
		beanConfig.setHost("localhost:9099");
		//beanConfig.setBasePath("/app");
		beanConfig.setTitle("Fund Transfer Rest API ");
		//beanConfig.setDescription("https://github.com/VIIgit/swagger-spring-jaxrs-sample-app");
		beanConfig.getSwagger().addConsumes(MediaType.APPLICATION_JSON);
		beanConfig.getSwagger().addProduces(MediaType.APPLICATION_JSON);
		beanConfig.setContact("Distance Instraction Team");
		beanConfig.setResourcePackage("be.ing.fundtransfer.controller");
		beanConfig.setPrettyPrint(true);
		beanConfig.setScan();
	}
}