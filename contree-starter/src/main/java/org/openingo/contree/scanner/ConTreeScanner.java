package org.openingo.contree.scanner;

import org.mybatis.spring.annotation.MapperScan;
import org.openingo.contree.config.ConTreeConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ConTreeScanner
 *
 * @author Qicz
 */
@Configuration
@ComponentScan(ConTreeConfig.PACKAGE_NAME)
@MapperScan(ConTreeConfig.MAPPER_PACKAGE_NAME)
public class ConTreeScanner {

}
