package com.trungtamjava;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.CacheControl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.trungtamjava.security.JwtTokenFilter;
import com.trungtamjava.security.JwtTokenProvider;
import com.trungtamjava.utils.FileStore;
import com.trungtamjava.utils.RoleEnum;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = { "com.trungtamjava" })
public class SectionBoot9Application extends WebMvcConfigurationSupport  {
//	@Autowired
//	UserLoginServiceImpl loginServiceImpl;
//	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public static void main(String[] args) {
		SpringApplication.run(SectionBoot9Application.class, args);
	}
	@Configuration
	@Order(1)
	public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable();

			// No session will be created or used by spring security
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

			http.antMatcher("/api/**").authorizeRequests().antMatchers("/api/admin/**")
					.hasAnyRole(RoleEnum.ADMIN.getRoleName()).antMatchers("/api/member/**").authenticated().anyRequest()
					.permitAll().and().httpBasic();
			// Apply JWT
			http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		}
	}
	

	@Configuration
	public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().authorizeRequests().antMatchers("/admin/**").hasAnyRole(RoleEnum.ADMIN.getRoleName())
					.antMatchers("/member/**").authenticated().anyRequest().permitAll().and().formLogin()
					.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/")
					.failureUrl("/login?e=error").and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).clearAuthentication(true)
					.invalidateHttpSession(true).deleteCookies("JSESSIONID", "app-remember-me").permitAll().and()
					.exceptionHandling().accessDeniedPage("/access-deny").and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS).sessionFixation().migrateSession()
					.maximumSessions(-1).sessionRegistry(sessionRegistry());
			http.headers().frameOptions().sameOrigin();
		}
		
	}

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser("admin").password("{noop}123").roles("ADMIN");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
		return bCryptPasswordEncoder;
		// Ma hoa mat khau nguoi dung
	}
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/image/**").addResourceLocations("file:" + FileStore.UPLOAD_FOLDER)
				.setCacheControl(CacheControl.maxAge(24 * 365, TimeUnit.HOURS).cachePublic());
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/")
				.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}