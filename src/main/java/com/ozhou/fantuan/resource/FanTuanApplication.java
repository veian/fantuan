package com.ozhou.fantuan.resource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class FanTuanApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(MealResource.class);
        classes.add(AccountResource.class);
        classes.add(AuthenticationResource.class);
        return classes;
    }

}
