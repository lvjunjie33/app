package snod.com.cn.basic.swagger2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lvjj
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 可以定义多个组，比如本类中定义把多个接口模块分开 
     */
    @Bean
    public Docket initApi() {
        List<Parameter> pars = new ArrayList<Parameter>(); 
    	 ParameterBuilder keyPar = new ParameterBuilder(); 
    	 ParameterBuilder lanPar = new ParameterBuilder();
    	 ParameterBuilder chPar = new ParameterBuilder();
    	 ParameterBuilder vPar = new ParameterBuilder();
    	 ParameterBuilder sdkPar = new ParameterBuilder();
    	 ParameterBuilder nwPar = new ParameterBuilder();
    	 
    	 keyPar.name("_key").description("key唯一标识").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	 
    	 lanPar.name("_lan").description("语言").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();

         chPar.name("_ch").description("渠道").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();

         vPar.name("_v").description("版本").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();

         sdkPar.name("_sdk").description("sdk").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();

         nwPar.name("_nw").description("网络").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();
         
    	 pars.add(keyPar.build());
    	 pars.add(lanPar.build());
    	 pars.add(chPar.build());
    	 pars.add(vPar.build());
    	 pars.add(sdkPar.build());
    	 pars.add(nwPar.build());
    
        return new Docket(DocumentationType.SWAGGER_2)
        		 .groupName("init")
        		 .apiInfo(initApiInfo())
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("snod.com.cn.controller"))
                 .paths(PathSelectors.regex("/init/.*"))
                 .build()
                 .globalOperationParameters(pars);
        }
    
    @Bean
    public Docket loginApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        	   	.groupName("login")
        		 .apiInfo(loginApiInfo())
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("snod.com.cn.controller"))
                 .paths(PathSelectors.regex("/login/.*"))
                 .build();
        }
    @Bean
    public Docket userApi() {
    	List<Parameter> pars = new ArrayList<Parameter>(); 
    	 ParameterBuilder tokenPar = new ParameterBuilder(); 
    	 ParameterBuilder lanPar = new ParameterBuilder();
    	 ParameterBuilder chPar = new ParameterBuilder();
    	 ParameterBuilder vPar = new ParameterBuilder();
    	 ParameterBuilder sdkPar = new ParameterBuilder();
    	 ParameterBuilder nwPar = new ParameterBuilder();
    	 
         tokenPar.name("_token").description("token").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();

         lanPar.name("_lan").description("语言").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();

         chPar.name("_ch").description("渠道").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();

         vPar.name("_v").description("版本").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();

         sdkPar.name("_sdk").description("sdk").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();

         nwPar.name("_nw").description("网络").
    	 modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	 pars.add(tokenPar.build());
    	 pars.add(lanPar.build());
    	 pars.add(chPar.build());
    	 pars.add(vPar.build());
    	 pars.add(sdkPar.build());
    	 pars.add(nwPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("business")
        		 .apiInfo(userApiInfo())
                 .select()
                 .apis(RequestHandlerSelectors.basePackage("snod.com.cn.controller"))
                 .paths(PathSelectors.regex("/business/.*"))
                 .build()
                 .globalOperationParameters(pars);
        }
    private ApiInfo initApiInfo() {
        ApiInfo apiInfo = new ApiInfo("三诺数字科技有限公司",//大标题
                "初始化接口",//小标题
                "1.0",//版本
                "NO terms of service",
                "吕俊杰",//作者
                "",//链接显示文字
                ""//网站链接
        );
        return apiInfo;
    } 
    private ApiInfo loginApiInfo() {
        ApiInfo apiInfo = new ApiInfo("三诺数字科技有限公司",//大标题
                "登录接口",//小标题
                "1.0",//版本
                "NO terms of service",
                "吕俊杰",//作者
                "",//链接显示文字
                ""//网站链接
        );
        return apiInfo;
    }

    private ApiInfo userApiInfo() {
        ApiInfo apiInfo = new ApiInfo("三诺数字科技有限公司",//大标题
                "业务接口",//小标题
                "1.0",//版本
                "NO terms of service",
                "吕俊杰",//作者
                "",//链接显示文字
                ""//网站链接
        );
        return apiInfo;
    }
}