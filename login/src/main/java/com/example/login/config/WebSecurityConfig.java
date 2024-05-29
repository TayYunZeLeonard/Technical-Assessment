package com.example.login.config;

import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.example.login.service.UserDetailsServiceImpl;
 
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer{
     
    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
 
    @Bean
    PasswordEncoder passwordEncoder() {
        return new PasswordEncoderTest();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
    
    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver()  {
		CookieLocaleResolver resolver= new CookieLocaleResolver();
		return resolver;
	} 
    
	@Bean(name = "messageSource")
	public MessageSource getMessageResource()  {
		ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();
		
		// Read lang/messages_xxx.properties file.
		// For example: lang/messages_en.properties
		messageResource.setBasename("classpath:lang/messages");
		messageResource.setDefaultEncoding("UTF-8");
		return messageResource;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}
 
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
         
    	
    	/* just to make it easy to access h2 console 
    	 
        http.authorizeRequests()
        .requestMatchers("/").permitAll()
        .requestMatchers("/h2/**").permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();	
    	*/

        http.authenticationProvider(authenticationProvider());
          
        http.authorizeHttpRequests(auth ->
            auth.requestMatchers("/welcome").authenticated()
            .requestMatchers("/restricted").hasAuthority("MANAGER")
            .anyRequest().permitAll()
            )
            .formLogin(login ->
                login
                .loginPage("/login")
                .usernameParameter("username")
                .defaultSuccessUrl("/welcome")
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/").permitAll()
        );
         
        return http.build();
    }  
    
    //create a password encoder to not use password encoding for this test
    public class PasswordEncoderTest implements PasswordEncoder {
        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return charSequence.toString().equals(s);
        }
    }
}