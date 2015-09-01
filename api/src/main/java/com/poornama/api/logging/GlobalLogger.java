package com.poornama.api.logging;

import org.apache.log4j.Logger;

public class GlobalLogger {

    private static Logger logger = Logger.getLogger(GlobalLogger.class);
    private static String className = GlobalLogger.class.getName();

    protected GlobalLogger() {

    }

    /**
     * Returns logger object for the invoker
     *
     * @return Logger object
     */
    public static Logger getLogger() {
        logger.debug("[" + className + "] getLogger()");
        return logger;
    }
}
