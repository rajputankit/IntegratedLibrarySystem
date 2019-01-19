package edu.sjsu.cmpe275.termproject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import edu.sjsu.cmpe275.termproject.exceptions.MyCustomInterceptor;

@Configuration
@ComponentScan(basePackages="edu.sjsu.cmpe275")
public class Config extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyCustomInterceptor()).addPathPatterns("/*/*");
        
    }

}