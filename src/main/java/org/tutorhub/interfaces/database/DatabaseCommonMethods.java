package org.tutorhub.interfaces.database;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.interfaces.services.ServiceCommonMethods;
import org.tutorhub.database.HibernateConnector;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;

import jakarta.validation.ValidatorFactory;
import org.apache.commons.lang3.Validate;

@SuppressWarnings( value = "хранит все стандартные методы для сервисов работающих с БД" )
public interface DatabaseCommonMethods extends ServiceCommonMethods {
    default void isSessionClosed () {
        Validate.isTrue(
                this.getSession().isConnected() && this.getSession().isOpen(),
                String.join(
                        StringOperations.SPACE,
                        HibernateConnector.class.getName(),
                        "is closed"
                )
        );
    }

    default Session getSession() {
        return HibernateConnector.getInstance().getSession();
    };

    default Transaction newTransaction () {
        return this.getSession().beginTransaction();
    }

    default SessionFactory getSessionFactory() {
        return HibernateConnector.getInstance().getSessionFactory();
    };

    default StandardServiceRegistry getRegistry() {
        return HibernateConnector.getInstance().getRegistry();
    };

    default ValidatorFactory getValidatorFactory() {
        return HibernateConnector.getInstance().getValidatorFactory();
    };
}
