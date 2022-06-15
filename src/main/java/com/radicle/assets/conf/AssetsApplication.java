package com.radicle.assets.conf;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.radicle.assets.common.ApiHelper;

@SpringBootApplication
public class AssetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetsApplication.class, args);
	}

	@Bean
	public ApiHelper apiHelper() {
		return new ApiHelper();
	}

	@Bean
	public RestOperations restTemplate() {
		return createRestTemplate();
	}

	public static RestTemplate createRestTemplate() {
		RestTemplate template = new RestTemplate();
		template.getMessageConverters().add(new StringHttpMessageConverter());
		return template;
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowCredentials(true)
				.allowedMethods("GET", "HEAD", "POST", "PUT", "OPTIONS")
				.allowedHeaders("http://api.risidio.local,https://tapi.brightblock.org", "https://api.brightblock.org", "http://localhost:8085", "http://localhost:8080", "http://localhost:8081", "http://localhost:8082", "http://localhost:8083", "http://localhost:8084", "http://localhost:8085", "http://localhost:8086", "http://localhost:8087", "http://localhost:8088", "http://localhost:8089", "https://prom.risidio.com", "https://thisisnumberone.com", "https://staging.thisisnumberone.com", "https://staging.loopbomb.io", "https://staging.risidio.com", "https://risidio.com", "https://loopbomb.com", "https://test.loopbomb.com", "https://loopbomb.io", "https://test.loopbomb.io", "https://stacksmate.com", "https://staging.stacksmate.com", "https://staging.electricart.gallery", "https://electricart.gallery", "https://testnet.claritylab.dev", "https://claritylab.dev")
				.allowedOrigins("http://api.risidio.local,https://tapi.brightblock.org", "https://api.brightblock.org", "http://localhost:8080", "http://localhost:8081", "http://localhost:8082", "http://localhost:8083", "http://localhost:8084", "http://localhost:8085", "http://localhost:8086", "http://localhost:8087", "http://localhost:8088", "http://localhost:8089", "https://prom.risidio.com", "https://thisisnumberone.com", "https://staging.thisisnumberone.com", "https://staging.loopbomb.io", "https://staging.risidio.com", "https://risidio.com", "https://loopbomb.com", "https://test.loopbomb.com", "https://loopbomb.io", "https://test.loopbomb.io", "https://stacksmate.com", "https://staging.stacksmate.com", "https://staging.electricart.gallery", "https://electricart.gallery", "https://testnet.claritylab.dev", "https://claritylab.dev")
				.exposedHeaders("IdentityAddress", "Authorization", "content-type", "x-auth-token");
			}
		};
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://api.risidio.local,https://tapi.brightblock.org", "https://api.brightblock.org", "http://localhost:8085", "http://localhost:8080", "http://localhost:8081", "http://localhost:8082", "http://localhost:8083", "http://localhost:8084", "http://localhost:8085", "http://localhost:8086", "http://localhost:8087", "http://localhost:8088", "http://localhost:8089", "https://prom.risidio.com", "https://thisisnumberone.com", "https://staging.thisisnumberone.com", "https://staging.loopbomb.io", "https://staging.risidio.com", "https://risidio.com", "https://loopbomb.com", "https://test.loopbomb.com", "https://loopbomb.io", "https://stacksmate.com", "https://staging.stacksmate.com", "https://staging.electricart.gallery", "https://electricart.gallery", "https://testnet.claritylab.dev", "https://claritylab.dev"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("IdentityAddress", "Authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("IdentityAddress", "Authorization", "content-type", "x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
	}

}
