package kkashin.dev;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.support.ConfigurableConversionService;

@Configuration
@ComponentScan("kkashin.dev")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
