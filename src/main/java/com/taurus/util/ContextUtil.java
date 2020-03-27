package com.taurus.util;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Diego
 */
public class ContextUtil {

    private static ApplicationContext appContext;

    public static ApplicationContext getAppContext() {
        return appContext;
    }

    public static void setAppContext(ApplicationContext appContext) {
        ContextUtil.appContext = appContext;
    }

    public static <T> T getBean(Class<T> classe) {
        try {
            return appContext.getBean(classe);
        } catch (NoUniqueBeanDefinitionException ex) {
            return appContext.getBean(classe.getSimpleName(), classe);
        }
    }
}
