package fi.ficora.lippu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-11-22T16:56:34.734+02:00")

@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Lippu")
            .description("Transport Code broughts together legal provisions on transport market under one act. The LIPPU-project was created to help transport operators enable Mobility as a Service (MaaS) capabilities. This is API specification of LIPPU-project. NOTE, the ticket payment capabilities are scoped out from LIPPU-project.")
            .license("EUPL v1.2")
            .licenseUrl("https://joinup.ec.europa.eu/sites/default/files/ckeditor_files/files/EUPL%20v1_2%20EN(1).txt")
            .termsOfServiceUrl("")
            .version("0.4.0-SNAPSHOT")
            .contact(new Contact("","", ""))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("fi.ficora.lippu.controller"))
                    .build()
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

}
