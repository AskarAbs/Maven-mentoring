package com.askar.videolibrary.repository;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Proxy;

public class ProxySession {

    @NotNull
    public static EntityManager getProxySession(SessionFactory sessionFactory) {
        return (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                new Class[]{EntityManager.class}, (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }
}
