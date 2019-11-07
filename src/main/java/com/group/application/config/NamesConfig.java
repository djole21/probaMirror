package com.group.application.config;

import com.group.application.names.INameFactory;
import com.group.application.names.NameFactory;
import com.group.application.utils.BettingPlaceNames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
public class NamesConfig {

    @Bean(name = "Bet365NameFactory")
    public INameFactory getBet365NameFactory(){
        return NameFactory.getFactory(BettingPlaceNames.Bet365.toString());
    }
    @Bean(name = "PinnbetNameFactory")
    INameFactory getPinnbetNameFactory(){
        return NameFactory.getFactory(BettingPlaceNames.Pinnbet.toString());
    }
    @Bean(name = "MozzartNameFactory")
    public INameFactory getMozzartNameFactory(){
        return NameFactory.getFactory(BettingPlaceNames.Mozzart.toString());
    }
    @Bean(name = "MeridianNameFactory")
    INameFactory getMeridianNameFactory(){
        return NameFactory.getFactory(BettingPlaceNames.Meridian.toString());
    }
    @Bean(name = "888SportNameFactory")
    INameFactory get888SportNameFactory(){
        return NameFactory.getFactory(BettingPlaceNames.Sport888.toString());
    }
}
