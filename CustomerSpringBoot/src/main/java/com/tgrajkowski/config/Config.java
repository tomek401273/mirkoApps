package com.tgrajkowski.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//        Configuration builderConfiguration = (Configuration) modelMapper.getConfiguration().copy()
//                .setDestinationNameTransformer(NameTransformers.builder())
//                .setDestinationNamingConvention(NamingConventions.builder());
//        modelMapper.createTypeMap(Source.class, Destination.Builder.class, builderConfiguration);
        return modelMapper;
    }
}
