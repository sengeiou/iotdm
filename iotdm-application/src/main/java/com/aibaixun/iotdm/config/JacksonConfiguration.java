package com.aibaixun.iotdm.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.util.List;

/**
 * jackson 配置
 * <p>序列化枚举类与null 字符串为'' null 集合为 []</p>
 * @author wangxiao@aibaixun.com
 * @date 2022/3/3
 */
@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer(){
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    }

//    @Bean
//    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
//        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper mapper = converter.getObjectMapper();
//        mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new NullSerializerModifier()));
//        return converter;
//    }

    private static class NullSerializerModifier extends BeanSerializerModifier {

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                                                         BeanDescription beanDesc,
                                                         List<BeanPropertyWriter> beanProperties) {
            for (int i = 0; i < beanProperties.size(); i++) {
                BeanPropertyWriter writer = beanProperties.get(i);
                if (isArrayType(writer)) {
                    writer.assignNullSerializer(new JsonSerializer<>() {
                        @Override
                        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                            if (o == null) {
                                jsonGenerator.writeStartArray();
                                jsonGenerator.writeEndArray();
                            }
                        }
                    });
                } else {
                    writer.assignNullSerializer(new JsonSerializer<>() {
                        @Override
                        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                            jsonGenerator.writeString("");
                        }
                    });
                }
            }
            return beanProperties;
        }

        protected boolean isArrayType(BeanPropertyWriter writer) {
            JavaType javaType = writer.getType();
            return   javaType.isArrayType() || javaType.isCollectionLikeType();
        }

    }
}
