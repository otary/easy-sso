package cn.chenzw.sso.easy.springmvc.examples.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@ComponentScan(basePackages = {"cn.chenzw.sso.easy.server.examples"})

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


}
