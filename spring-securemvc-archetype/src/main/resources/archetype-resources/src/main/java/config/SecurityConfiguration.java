package ${package}.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    // This is just to get up and running, should be replaced with a proper
    // DB backed store and password hashing
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        //@formatter:off
        auth.inMemoryAuthentication()
            .withUser("jdoe")
                .password("password")
                .roles("USER")
            .and().withUser("admin")
                .password("password")
                .roles("ADMIN","USER")
        ;
        //@formatter:on
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .authorizeRequests()
                .antMatchers("/","/login").permitAll()
                .anyRequest().hasRole("USER")
            .and().exceptionHandling()
                .accessDeniedPage("/login?authorization_error=true")
            .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
            .and().formLogin()
                .loginProcessingUrl("/login")
                .failureUrl("/login?authentication_error=true")
                .loginPage("/login")
        ;
        //@formatter:on
    }
}
