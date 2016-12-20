package ua.tifoha.fink.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ua.tifoha.fink.services.UserService;
import ua.tifoha.fink.services.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth,
								UserService userService
								) throws Exception {
//		auth
//				.inMemoryAuthentication()
//				.withUser("user").password("user")
//				.roles("USER");
//		auth
//				.inMemoryAuthentication()
//				.withUser("superadmin").password("superadmin")
//				.roles("SUPERADMIN");
		auth.userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.authorizeRequests()
//			.antMatchers("/scheduler/**").access("hasRole('ROLE_USER')")
//			.antMatchers("/administration/**").access("hasRole('ROLE_SUPERADMIN')")
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/login", "/logout").permitAll()
				.antMatchers("/**").authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.loginProcessingUrl("/login")
				.failureUrl("/login?fail")
				.defaultSuccessUrl("/", false)
////			.permitAll()
		;

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
				.ignoring()
				.antMatchers("/resources/**"); // #3
	}
}