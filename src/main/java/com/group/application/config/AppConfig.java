package com.group.application.config;

import com.group.application.RunOrLoad;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({OddLoadConfig.class, RunOrLoad.class, NamesConfig.class})
public class AppConfig {

}
