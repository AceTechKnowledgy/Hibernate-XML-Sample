package com.ace.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.ace.hibernate.model.UserForm;

public class MainClass {
	public static void main(String[] args) {
		
		//Configuring the hiberbate from hibernate-config.xml
		Configuration configuration = new Configuration();
		configuration.configure("hibernate-config.xml");
		
		//Creating Service Registry
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		
		//Creating Sessionfactory Object
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		//Creating Session object from SessionFactory
		Session session = sessionFactory.openSession();
		
		//Beginning the database transaction
		session.beginTransaction();
		
		//Creating a UserForm object to insert into the database
		UserForm userForm = new UserForm();
		userForm.setName("kumar");
		userForm.setEmail("kumar@gamil.com");
		userForm.setPassword("abc123456");
		
		session.save(userForm);
		
		//Commiting the unit-of-work
		session.getTransaction().commit();
		
		//Closing the session object
		session.close();
		
		//Closing session factory object
		sessionFactory.close();
	}
}
