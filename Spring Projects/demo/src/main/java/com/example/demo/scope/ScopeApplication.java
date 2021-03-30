package com.example.demo.scope;

import com.example.demo.BinarySearchImpl;
import com.example.demo.DemoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ScopeApplication {

	private	static Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);
	public static void main(String[] args) {

		//BinarySearchImpl binarySearch = new BinarySearchImpl(new BubbleSortAlgo());


		ApplicationContext applicationContext = SpringApplication.run(ScopeApplication.class, args);

		PersonDAO personDAO = applicationContext.getBean(PersonDAO.class);
		PersonDAO personDAO1 = applicationContext.getBean(PersonDAO.class);

		LOGGER.info("{}",personDAO);
		LOGGER.info("{}",personDAO.getJdbcConnection());
		LOGGER.info("{}",personDAO1);
		LOGGER.info("{}",personDAO1.getJdbcConnection());

	}

}
