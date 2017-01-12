package ua.tifoha.fink.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ua.tifoha.fink.converters.QuartzConversionService;
import ua.tifoha.fink.jackson.mixins.JobDetailsMixin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan({"ua.tifoha.fink.controllers"})
@PropertySource("classpath:application.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Value("${web.resourceHandler}")
	private String resourceHandler;

	@Value("${web.resourceLocations}")
	private String resourceLocations;

	@Value("${web.viewResolver.prefix}")
	private String prefix;

	@Value("${web.viewResolver.suffix}")
	private String suffix;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler(resourceHandler)
				.addResourceLocations(resourceLocations);
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer matcher) {
		matcher.setUseRegisteredSuffixPatternMatch(true);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(jackson2HttpMessageConverter());
	}

	@Bean
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
		ObjectMapper mapper = new ObjectMapper()
				.addMixIn(JobDetail.class, JobDetailsMixin.class);
		return new MappingJackson2HttpMessageConverter(mapper);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer
				.favorPathExtension(false)
//				.favorParameter(false)
				.favorParameter(true)
				.parameterName("mediaType")
				.ignoreAcceptHeader(true)
				.useJaf(false)
//				.defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML)
				.mediaType("json", MediaType.APPLICATION_JSON)
 ;
	}

	@Autowired
	public void registerConverters(GenericConversionService conversionService) {
		conversionService.addConverter(new QuartzConversionService());
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		dateFormat.setLenient(false);
//		conversionService.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix(prefix);
		viewResolver.setSuffix(suffix);
		return viewResolver;
	}
}
