package com.tri.erp.spring.config;

import javax.sql.DataSource;

import com.tri.erp.spring.commons.helpers.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Environment env;

    @Autowired
    DataSource dataSource;

    /**
     *
     * @param builder
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity builder) throws Exception {
        builder .ignoring()
                .antMatchers("/resources/**", "/logoutSuccess");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception { 
        http
            .authorizeRequests()
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            .anyRequest().authenticated();
        http
            .formLogin().loginPage("/login")
            .permitAll();
        http
            .logout()
                .logoutSuccessUrl("/logoutSuccess")
            .and()
                .csrf();
        http
            .exceptionHandling().accessDeniedPage("/403");

        SystemUtil systemUtil = new SystemUtil(env);
        if (systemUtil.inActiveProfiles("local") || systemUtil.inActiveProfiles( "staging")) {
            http.csrf().disable();
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
        auth
                .jdbcAuthentication()
                .passwordEncoder(passwordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username,password, enabled FROM User WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, Role.name AS role FROM UserRole " +
                                            "JOIN User on FK_userId = User.id " +
                                            "JOIN Role on FK_roleId = Role.id " +
                                            "WHERE username=?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
