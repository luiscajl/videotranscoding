package es.urjc.videotranscoding.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll().anyRequest().authenticated()
                .and().oauth2Login().and().oauth2ResourceServer().jwt();

        // http.requiresChannel()
        // .requestMatchers(RequestMatcher {
        // r -> r.getHeader("X-Forwarded-Proto") != null
        // }).requiresSecure();
        http.cors();
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.headers().contentSecurityPolicy("script-src 'self'; report-to /csp-report-endpoint/");
    }

    // @Bean
    // public FilterRegistrationBean corsFilter() {
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowCredentials(true);
    //     config.addAllowedOrigin("http://localhost:4200");
    //     config.addAllowedHeader("*");
    //     config.addAllowedMethod("*");
    //     source.registerCorsConfiguration("/**", config);
    //     FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    //     bean.setOrder(0);
    //     return bean;
    // }

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    // registry.addMapping("/**").allowedMethods("*");
    // }

    // @Override
    // public void doFilter(ServletRequest req, ServletResponse res, FilterChain
    // chain)
    // throws ServletException, IOException {
    // HttpServletRequest request = (HttpServletRequest) req;
    // HttpServletResponse response = (HttpServletResponse) res;
    // response.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    // response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT,
    // PATCH, HEAD, OPTIONS");
    // response.addHeader("Access-Control-Allow-Headers",
    // "Origin, Accept, X-Requested-With, Content-Type,
    // Access-Control-Request-Method, Access-Control-Request-Headers,
    // Authorization");
    // response.addHeader("Access-Control-Expose-Headers",
    // "Access-Control-Allow-Origin,
    // Access-Control-Allow-Credentials,authorization");
    // response.addHeader("Access-Control-Allow-Credentials", "true");
    // response.addIntHeader("Access-Control-Max-Age", 10);
    // if (request.getMethod().equals("OPTIONS")) {
    // response.setStatus(200);
    // }
    // chain.doFilter(request, response);
    // }

}
