package sample.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring MVC API")
                        .version("1.0")
                        .description("Spring MVC 학습을 위한 API 문서")
                        .contact(new Contact()
                                .name("개발자")
                                .email("developer@example.com")
                        )
                );
    }
}
