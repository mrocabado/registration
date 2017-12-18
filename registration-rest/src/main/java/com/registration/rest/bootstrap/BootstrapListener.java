package com.registration.rest.bootstrap;

import java.util.Objects;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class BootstrapListener  implements ServletContextListener {
	private static final Logger logger =  LoggerFactory.getLogger(BootstrapListener.class);
	

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if ( !Objects.isNull(RepoFactory.instance) ) {
			logger.info("RepoFactory succesfully initialized");
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing	
	}

}
