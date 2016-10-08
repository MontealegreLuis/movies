/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.db.listeners;

import com.codeup.db.di.DbContainer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.sql.Connection;

@WebListener()
public class ConnectionListener implements ServletContextListener,
    HttpSessionListener, HttpSessionAttributeListener {

    private Connection connection;

    // Public constructor is required by servlet spec
    public ConnectionListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        try {
            connection = DbContainer.connection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
    }

    public void attributeAdded(HttpSessionBindingEvent sbe) {
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
    }
}
