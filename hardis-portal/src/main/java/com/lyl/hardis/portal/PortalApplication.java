package com.lyl.hardis.portal;

import com.lyl.hardis.common.HardisCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.actuate.system.EmbeddedServerPortFileWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan(basePackageClasses = {PortalApplication.class, HardisCommonConfig.class})
public class PortalApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PortalApplication.class, args);
        context.addApplicationListener(new ApplicationPidFileWriter());
        context.addApplicationListener(new EmbeddedServerPortFileWriter());
    }
}
