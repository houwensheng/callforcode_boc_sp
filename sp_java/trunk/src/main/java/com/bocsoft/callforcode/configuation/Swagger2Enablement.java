package com.bocsoft.callforcode.configuation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bocsoft.callforcode.configuation.vo.Swagger2ConfiguationProperties;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile({"local","dev","sit","uat"})
@EnableSwagger2
@EnableConfigurationProperties(Swagger2ConfiguationProperties.class)
public class Swagger2Enablement {
	@Autowired
	private Swagger2ConfiguationProperties props;
	
    @Bean
    public Docket createRestApi() {// 创建API基本信息  
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bocsoft.callforcode"))// 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外  
                .paths(PathSelectors.any())
                .build();
    }

	private ApiInfo apiInfo() {// 创建API的基本信息，这些信息会在Swagger UI中进行显示  
        return new ApiInfoBuilder()  
                .title(props.getTitle())// API 标题  
                .description(props.getDescription())// API描述  
                .version(props.getVersion())// 版本号  
                .build();  
    }
	
 }