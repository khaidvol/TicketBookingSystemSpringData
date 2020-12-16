package com.epam.jgmp.config;

import com.epam.jgmp.xml.ObjXMLMapper;
import com.epam.jgmp.xml.XMLTicket;
import com.epam.jgmp.xml.XMLTicketListContainer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

@Configuration
@ComponentScan(basePackages = "com.epam.jgmp")
@PropertySource("classpath:application.properties")
@EnableWebMvc
@EntityScan("com.epam.jgmp.model")
@EnableJpaRepositories(basePackages = "com.epam.jgmp.repository")
public class TbsApplicationConfig extends WebMvcConfigurationSupport {

  private final ApplicationContext applicationContext;

  public TbsApplicationConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  /** PDF view bundle */
  @Bean
  public ResourceBundleViewResolver resourceBundleViewResolver() {

    ResourceBundleViewResolver viewResolver = new ResourceBundleViewResolver();
    viewResolver.setOrder(2);
    viewResolver.setBasename("views");

    return viewResolver;
  }

  /** XML to Object Mapping configuration */
  @Bean
  public Jaxb2Marshaller jaxb2Marshaller() {

    Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
    jaxb2Marshaller.setClassesToBeBound(XMLTicket.class, XMLTicketListContainer.class);

    return jaxb2Marshaller;
  }

  @Bean
  public ObjXMLMapper objXMLMapper() {

    ObjXMLMapper objXMLMapper = new ObjXMLMapper();
    objXMLMapper.setMarshaller(jaxb2Marshaller());

    return objXMLMapper;
  }
}
