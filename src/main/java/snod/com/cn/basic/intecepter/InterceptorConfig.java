package snod.com.cn.basic.intecepter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import snod.com.cn.basic.redis.RedisService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lvjj
 * */
@Configuration
@EnableSwagger2
public class InterceptorConfig extends WebMvcConfigurerAdapter{
	
	  @Bean
	  public SystemIntecepter systemIntecepter() {
	        return new SystemIntecepter();
	    }

	  @Override
	    public void addInterceptors(InterceptorRegistry registry) {
		  
	        registry.addInterceptor(systemIntecepter()).addPathPatterns("/**");
	        
	    }
}
