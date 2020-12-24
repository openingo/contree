package org.openingo.contree.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableTree
 *
 * @author Qicz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EnableTree.ConTreeScanner.class)
public @interface EnableTree {

    String PACKAGE_NAME = "org.openingo.contree";
    String MAPPER_PACKAGE_NAME = "org.openingo.contree.**.mapper";

    @ComponentScan(PACKAGE_NAME)
    @MapperScan(MAPPER_PACKAGE_NAME)
    class ConTreeScanner {

    }
}