package co.com.grupoasd.services.fixedassets.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact("Telintel Development", "https://www.go4clients.com",
			"dev@telintel.net");
	public static final ApiInfo DEFAULT = new ApiInfo("Api Documentation for the voice campaigns management service",
			"This module is in charge of managing the voice campaigns for Go4Clients V3", "1.0", "urn:tos",
			DEFAULT_CONTACT, "Telintel rights reserved", "https://www.telintel.com", new ArrayList<VendorExtension>());

	private static final Set<String> DEFAULT_PRODUCES = new HashSet<>(Arrays.asList("application/json"));

	private static final Set<String> DEFAULT_CONSUMES = new HashSet<>(Arrays.asList("application/json"));

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT).produces(DEFAULT_PRODUCES)
				.consumes(DEFAULT_CONSUMES);
	}

}
