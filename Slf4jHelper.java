package com.wlp.e3.cnsl.ui.cntrlrs;

import java.lang.reflect.Field;

import org.apache.log4j.Level;
import org.slf4j.Logger;

public class Slf4jHelper
{
	public static void setLogLevel(Logger logger, Slf4jLevel level) {

		if (logger instanceof org.slf4j.impl.Log4jLoggerAdapter) 
		{
			try {
				Class loggerIntrospected = logger.getClass();
				Field fields[] = loggerIntrospected.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) 
				{
					String fieldName = fields[i].getName();
					if (fieldName.equals("logger"))
					{
						fields[i].setAccessible(true);
						org.apache.log4j.Logger loggerImpl = (org.apache.log4j.Logger) fields[i]
								.get(logger);
						
						setLevel(loggerImpl, level);
					}
				}
			} catch (Exception e) {
				org.apache.log4j.Logger.getLogger(Slf4jHelper.class).error("An error was thrown while changing the Logger level",e);
			}
		}

	}

    public static enum Slf4jLevel {
        TRACE, DEBUG, INFO, WARN, ERROR
    }
    
    private static void setLevel(org.apache.log4j.Logger logger, Slf4jLevel level)
    {
    	if(logger!=null && level!=null)
    	{
    		switch(level)
    		{
    			case TRACE:
    				logger.setLevel(Level.TRACE);
    				break;
                case DEBUG:
                	logger.setLevel(Level.DEBUG);
                    break;
                case INFO:
                	logger.setLevel(Level.INFO);
                    break;
                case WARN:
                	logger.setLevel(Level.WARN);
                    break;
                case ERROR:
                	logger.setLevel(Level.ERROR);
                    break;
                default:
                	logger.setLevel(org.apache.log4j.Logger.getRootLogger().getLevel());

    		}
    	}
    }
}
