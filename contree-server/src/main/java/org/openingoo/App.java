package org.openingoo;

import org.openingo.contree.annotation.EnableTree;
import org.openingo.spring.annotation.EnableExtension;
import org.openingo.spring.boot.SpringApplicationX;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * App
 *
 * @author Qicz
 */
@EnableExtension
@EnableTree
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplicationX.run(App.class, args);
    }

}
