package toni.ferreiro.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import toni.ferreiro.brewery.security.PasswordEncondingFactories;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/h2-console/**").permitAll() //do not use in production!
                            .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                            .antMatchers("/beers/find", "/beers*").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                            .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                            .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();

        //h2 console config
        http.csrf().disable();
        http.headers().frameOptions().disable();
        //http.headers().frameOptions().sameOrigin();
    }


    /**** En los siguientes metodos se utilizan password encoder
     * paquete org.springframework.security.crypto.password
     *
     */
/*//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }*/

//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new LdapShaPasswordEncoder();
//    }

//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new StandardPasswordEncoder();
//    }

//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }


    /**** Utiliza custom delegating password encoder
     * Se usa la clase PasswordEncondingFactories del paquete toni.ferreiro.brewery.security
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncondingFactories.createDelegatingPasswordEncoder();
    }


    /**** IN MEMORY AUTHENTICATION PROVIDER ****/

    /*** FLUENT API ***/
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("toni")
//                .password("{bcrypt10}$2a$10$Li6lfVOfdG2PF9QYH2O69e/nLXB7ujQ0YBTwfxMLOOdmAQDJGvGCq")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{sha256}4b023bb55060a2652e46bc8c61cdba4ee21893a923e6df8ccff665c37df6323511543cb021ffe300")
//                .roles("USER");
//
//        auth.inMemoryAuthentication().withUser("user2").password("{ldap}{SSHA}VaDo0Qs5shefdCxR3uiIqB0hhJRm9Rjqo90rIA==").roles("CUSTOMER");
//    }

    /*** DEPRECATED **/
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("toni")
//                .password("fercou")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
}
