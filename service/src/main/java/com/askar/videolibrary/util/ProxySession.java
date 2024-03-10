package com.askar.videolibrary.util;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;
public class ProxySession {

    public static EntityManager getProxySession(SessionFactory sessionFactory) {
        return (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                new Class[]{EntityManager.class}, (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }
}
