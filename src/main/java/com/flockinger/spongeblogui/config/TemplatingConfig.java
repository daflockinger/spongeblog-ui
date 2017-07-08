package com.flockinger.spongeblogui.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.flockinger.spongeblogui.dto.Category;
import com.flockinger.spongeblogui.resource.dto.CategoryDTO;

@Configuration
public class TemplatingConfig {

	@Bean
	public ModelMapper getMapper () {
		ModelMapper mapper = new ModelMapper();
		
		mapper.addMappings(new PropertyMap<CategoryDTO, Category>() {
			@Override
			protected void configure() {
				map().setId(source.getCategoryId());
			}
		});
		
		return mapper;
	}
}
