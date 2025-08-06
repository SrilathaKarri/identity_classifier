//package carestack.base.config;
//
//import carestack.ai.AiService;
//import carestack.ai.EncryptionUtilities;
//import carestack.encounter.Encounter;
//import carestack.encounter.documentLinking.DocumentLinking;
//import carestack.encounter.documentLinking.mappers.Mapper;
//import carestack.organization.Demographic;
//import carestack.organization.Organization;
//import carestack.patient.Patient;
//import carestack.patient.abha.AbhaService;
//import carestack.practitioner.Practitioner;
//import carestack.practitioner.hpr.Hpr;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.validation.Validator;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//@EnableConfigurationProperties(EmbeddedSdkProperties.class)
//@Import({WebClientConfig.class, CacheConfig.class})
//public class SdkAutoConfiguration {
//
//    @Bean
//    @ConditionalOnMissingBean
//    public EncryptionUtilities encryptionUtilities(ObjectMapper objectMapper) {
//        return new EncryptionUtilities(objectMapper);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public AiService aiService(ObjectMapper objectMapper, WebClient webClient, Validator validator) {
//        return new AiService(objectMapper, webClient, validator);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public AbhaService abhaService(ObjectMapper objectMapper, WebClient webClient, Validator validator) {
//        return new AbhaService(objectMapper, webClient, validator);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public Patient patient(ObjectMapper objectMapper, WebClient webClient, AbhaService abhaService) {
//        return new Patient(objectMapper, webClient, abhaService);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public Practitioner practitioner(ObjectMapper objectMapper, WebClient webClient, Hpr hpr) {
//        return new Practitioner(objectMapper, webClient, hpr);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public Organization organization(ObjectMapper objectMapper,
//                                     WebClient webClient,
//                                     @Value("${google.api.key:#{null}}") String userGoogleApiKey,
//                                     Demographic demographic,
//                                     EmbeddedSdkProperties embeddedProperties) {
//        return new Organization(objectMapper, webClient, userGoogleApiKey, demographic, embeddedProperties);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public Encounter encounter(ObjectMapper objectMapper, WebClient webClient,
//                               Validator validator,
//                               DocumentLinking documentLinking) {
//        return new Encounter(objectMapper, webClient, validator,documentLinking);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public Hpr hpr(ObjectMapper objectMapper, WebClient webClient, Validator validator) {
//        return new Hpr(objectMapper, webClient, validator);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public Demographic demographic(WebClient webClient, ObjectMapper objectMapper) {
//        return new Demographic(webClient, objectMapper);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public DocumentLinking documentLinking(ObjectMapper objectMapper, WebClient webClient, Mapper mapper)
//    {
//        return  new DocumentLinking(objectMapper,webClient,mapper);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public Mapper mapper()
//    {
//        return new Mapper();
//    }
//
//}
package carestack.base.config;

import carestack.ai.AiService;
import carestack.ai.EncryptionUtilities;
import carestack.encounter.Encounter;
import carestack.encounter.documentLinking.DocumentLinking;
import carestack.encounter.documentLinking.mappers.Mapper;
import carestack.organization.Demographic;
import carestack.organization.Organization;
import carestack.patient.Patient;
import carestack.patient.abha.AbhaService;
import carestack.practitioner.Practitioner;
import carestack.practitioner.hpr.Hpr;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory; // 2. Add this import
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(EmbeddedSdkProperties.class)
@Import({WebClientConfig.class, CacheConfig.class, EmbeddedPropertiesConfiguration.class})
public class SdkAutoConfiguration {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    /**
     * A helper method to create a bean and then have Spring autowire its internal dependencies.
     * This allows us to use explicit `new` while still supporting @Autowired and @Value in the services.
     */
    private <T> T autowire(T bean) {
        beanFactory.autowireBean(bean);
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    public EncryptionUtilities encryptionUtilities(ObjectMapper objectMapper) {
        return autowire(new EncryptionUtilities(objectMapper));
    }

    @Bean
    @ConditionalOnMissingBean
    public AiService aiService(ObjectMapper objectMapper, WebClient webClient, Validator validator) {
        return autowire(new AiService(objectMapper, webClient, validator));
    }

    @Bean
    @ConditionalOnMissingBean
    public AbhaService abhaService(ObjectMapper objectMapper, WebClient webClient, Validator validator) {
        return autowire(new AbhaService(objectMapper, webClient, validator));
    }

    @Bean
    @ConditionalOnMissingBean
    public Patient patient(ObjectMapper objectMapper, WebClient webClient, AbhaService abhaService) {
        return autowire(new Patient(objectMapper, webClient, abhaService));
    }

    @Bean
    @ConditionalOnMissingBean
    public Practitioner practitioner(ObjectMapper objectMapper, WebClient webClient, Hpr hpr) {
        return autowire(new Practitioner(objectMapper, webClient, hpr));
    }

    @Bean
    @ConditionalOnMissingBean
    public Organization organization(ObjectMapper objectMapper,
                                     WebClient webClient,
                                     Demographic demographic) {
        return autowire(new Organization(objectMapper, webClient, demographic));
    }

    @Bean
    @ConditionalOnMissingBean
    public Encounter encounter(ObjectMapper objectMapper, WebClient webClient,
                               Validator validator,
                               DocumentLinking documentLinking) {
        return autowire(new Encounter(objectMapper, webClient, validator, documentLinking));
    }

    @Bean
    @ConditionalOnMissingBean
    public Hpr hpr(ObjectMapper objectMapper, WebClient webClient, Validator validator) {
        return autowire(new Hpr(objectMapper, webClient, validator));
    }

    @Bean
    @ConditionalOnMissingBean
    public Demographic demographic(WebClient webClient, ObjectMapper objectMapper) {
        return autowire(new Demographic(webClient, objectMapper));
    }

    @Bean
    @ConditionalOnMissingBean
    public DocumentLinking documentLinking(ObjectMapper objectMapper, WebClient webClient, Mapper mapper) {
        return autowire(new DocumentLinking(objectMapper, webClient, mapper));
    }

    @Bean
    @ConditionalOnMissingBean
    public Mapper mapper() {
        return new Mapper();
    }


}