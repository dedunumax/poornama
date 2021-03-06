package com.poornama.web.configuration;

import com.poornama.api.logging.GlobalLogger;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author dedunu
 */
@Configuration
@ImportResource("classpath:poornama-context.xml")
@ComponentScan("com.poornama")
@EnableWebMvc
public class SpringConfiguration {

    private static Logger log = GlobalLogger.getLogger();
    private static String className = SpringConfiguration.class.getName();

    /**
     * Setup view resolver for the spring application. returns a UrlBasedViewResolver with configurations
     *
     * @return UrlBasedViewResolver
     */
    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        log.debug("[" + className + "] setupViewResolver()");
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }
}