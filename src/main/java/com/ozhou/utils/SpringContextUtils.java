/**
 * 
 */
package com.ozhou.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		applicationContext = context;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static <T> T getBean(String key, Class<T> cls) {
		return applicationContext.getBean(key, cls);
	}
	
	/**
	 * The bean id name is same as the class name.
	 * @param <T>
	 * @param cls
	 * @return <T>
	 */
	public static <T> T getBean(Class<T> cls) {
		return applicationContext.getBean(cls);
	}
}