package com.lucianbc.onechat.client.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneChatClient {
    private static Logger logger = LoggerFactory.getLogger(OneChatClient.class);
    public void run() throws Exception {
        // load app context
        logger.info("Initializing context");
        ApplicationContext context;
        try {
            context = ApplicationContext.getInsance();
        } catch (Exception ex) {
            logger.error("Failed loading application context", ex);
            throw new Exception();
        }
        SwingAppContainer container = new SwingAppContainer(context);
        container.run();
    }
}
