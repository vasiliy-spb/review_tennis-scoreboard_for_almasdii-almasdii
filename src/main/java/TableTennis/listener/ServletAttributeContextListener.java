package TableTennis.listener;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

import javax.management.Attribute;

@Slf4j
@WebListener
public class ServletAttributeContextListener implements ServletContextAttributeListener {
    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        ServletContextAttributeListener.super.attributeAdded(event);
        log.debug("new Attribute name : {} and value : {}",event.getName(),event.getValue());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        ServletContextAttributeListener.super.attributeRemoved(event);
        log.debug("removed Attribute name : {} and value : {} ",event.getName(),event.getValue());
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        ServletContextAttributeListener.super.attributeReplaced(event);
        log.debug("replaced Attribute name : {} and value : {} ",event.getName(),event.getValue());
    }
}
