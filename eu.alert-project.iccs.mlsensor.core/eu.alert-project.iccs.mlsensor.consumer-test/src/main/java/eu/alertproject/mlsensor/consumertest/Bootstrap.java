package eu.alertproject.mlsensor.consumertest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: fotis
 * Date: 04/11/11
 * Time: 21:13
 */
public class Bootstrap {

    private static ApplicationContext context;

    public static void main(String[] args) {

        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

}
