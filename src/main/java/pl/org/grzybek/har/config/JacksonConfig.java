package pl.org.grzybek.har.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    /**
     * Allows Spring to define LocalDateTime controller's parameters
     */
    @Bean
    public Converter<String, LocalDateTime> stringToLocalDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            public LocalDateTime convert(String source) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                return LocalDateTime.parse(source, formatter);
            }
        };

    }
}
