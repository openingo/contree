package org.openingo.contree.annotation;

import org.openingo.contree.scanner.ConTreeScanner;
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
@Import(ConTreeScanner.class)
public @interface EnableTree {

}