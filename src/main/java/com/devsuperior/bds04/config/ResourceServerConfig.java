package com.devsuperior.bds04.config;

import com.devsuperior.bds04.Enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String[] PUBLIC_ROUTES = { "/oauth/token", "/h2-console/**" };
    private static final String[] PUBLIC_GET = { "/cities/**", "/events/**" };
    private static final String[] PRIVATE_POST = { "/events/**" };

    @Autowired private JwtTokenStore tokenStore;
    @Autowired private Environment env;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            authorizeH2Database(http);
        }

        http.authorizeRequests()
                .antMatchers(PUBLIC_ROUTES).permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_GET).permitAll()
                .antMatchers(HttpMethod.POST, PRIVATE_POST).hasAnyRole(Roles.ADMIN.name(), Roles.CLIENT.name())
                .anyRequest().hasAnyRole(Roles.ADMIN.name());
    }

    private void authorizeH2Database(HttpSecurity http) throws Exception {
        disableFrameOptions(http);
    }

    private void disableFrameOptions(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
    }
}
