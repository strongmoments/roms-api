package com.roms.api.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Configuration
public class InstantConverter{
}
