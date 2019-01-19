package edu.sjsu.cmpe275.termproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@EnableScheduling
@ComponentScan({ "edu.sjsu.cmpe275" })
@Order
public class Application extends SpringBootServletInitializer{
	   @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(Application.class);
	        
	       
	    }

	   
	   
    public static void main( String[] args ) throws Exception
    {
    	SpringApplication.run(Application.class, args);
    }
}
