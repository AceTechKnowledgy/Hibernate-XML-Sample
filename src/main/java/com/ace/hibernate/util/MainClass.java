package com.ace.hibernate.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.ace.hibernate.model.UserForm;

public class MainClass {

	private static ServiceRegistry serviceRegistry;
	public static SessionFactory sessionFactory;
	public static Session session;

	public static void main(String[] args) {

		//Configuring the hiberbate from hibernate-config.xml
		Configuration configuration = new Configuration();
		configuration.configure("hibernate-config.xml");

		//Creating Service Registry
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

		//Creating Sessionfactory Object
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		//Creating Session object from SessionFactory
		session = sessionFactory.openSession();

		//Beginning the database transaction
		session.beginTransaction();

		//Create or Insert into database
		insertData();

		//Read the database and get the record by id
		getUserById(1);

		//Updating the record
		updateUser();
		
		//Delete a record
		deleteUser();

		//Closing the session object
		session.close();

		//Closing session factory object
		sessionFactory.close();
	}

	//Method to insert into database
	private static void insertData() {
		//Creating a UserForm object to insert into the database
		UserForm userForm = new UserForm();
		userForm.setName("Sivasurya");
		userForm.setEmail("Sivasurya@gamil.com");
		userForm.setPassword("abc123456");

		session.save(userForm);

		//Commiting the unit-of-work
		session.getTransaction().commit();
	}

	//Method to get the user by the id
	private static void getUserById(int id) {
		/*Here, get method is used becoz, if there is no record we will get null.
		 * We can use load(), but if the record is not there, it will give ObjectNotFoundException
		 */
		UserForm userForm = (UserForm) session.get(UserForm.class, id);
		System.out.println("Name: "+userForm.getName());
		
		//Uncomment bewlo set of codes only when you want to update the already read record with load() or get() function
		//Becoz, the record is already present in the first-level cache maintained by the session object
		/*
		userForm.setEmail("Sivakumar123@gamil.com");
		userForm.setPassword("xyz123456");

		session.update(userForm);

		session.getTransaction().commit();
		*/
	}

	//Method to update a record or Object in the database
	private static void updateUser() {
		//Uncomment below line if any of the session is committed previously
		//session.beginTransaction();
		
		UserForm userForm = new UserForm();
		userForm.setId(2);
		userForm.setName("Siva");
		userForm.setEmail("Sivakumar123@gamil.com");
		userForm.setPassword("xyz123456");

		session.update(userForm);

		//Commiting the unit-of-work
		session.getTransaction().commit();
	}
	
	//Method to delete a record or Object in the database
	private static void deleteUser() {
		UserForm userForm = new UserForm();
		userForm.setId(1);
		
		session.delete(userForm);
		
		//Can use the below method also
		//session.delete("UserForm", userForm);
		
		session.getTransaction().commit();
	}
}
