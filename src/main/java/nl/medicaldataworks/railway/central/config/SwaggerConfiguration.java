package nl.medicaldataworks.railway.central.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("nl.medicaldataworks.railway.central")).build()
                .apiInfo(apiInfo())
                .tags(new Tag("train-controller", "Train related operations. Trains are docker containers containing research algorithms to send around."))
                .tags(new Tag("station-controller", "Station related operations. Stations are client-side applications running the local part of a Train."))
                .tags(new Tag("task-controller", "Task related operations. Tasks are one iteration of a Train run. This could be either a task run on the master node or client node."))
                .tags(new Tag("forwarding-controller", "Only relevant for central development purposes. Forwards requests to the front-end after authentication."))
                .tags(new Tag("train-task-controller", "Task operations for the supplied train."));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Railway API").version("1.0.0").build();
    }
}