package pl.org.grzybek.har.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import pl.org.grzybek.har.HomeAssignmentRoApplication;
import springfox.documentation.builders.AlternateTypeBuilder;
import springfox.documentation.builders.AlternateTypePropertyBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Type;

import static java.util.Arrays.asList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private final TypeResolver typeResolver = new TypeResolver();

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                //Pageable class is not properly represented, a workaround is needed
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class),
                        pageableMixin(),
                        Ordered.HIGHEST_PRECEDENCE))
                .select()
                .apis(RequestHandlerSelectors.basePackage(HomeAssignmentRoApplication.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }

    private Type pageableMixin() {
        return new AlternateTypeBuilder()
                .fullyQualifiedClassName(
                        String.format("%s.generated.%s",
                                Pageable.class.getPackage().getName(),
                                Pageable.class.getSimpleName()))
                .withProperties(asList(
                        property(Integer.class, "page"),
                        property(Integer.class, "size"),
                        property(String.class, "sort")
                ))
                .build();
    }

    private AlternateTypePropertyBuilder property(Class<?> type, String name) {
        return new AlternateTypePropertyBuilder()
                .withName(name)
                .withType(type)
                .withCanRead(true)
                .withCanWrite(true);
    }
}
