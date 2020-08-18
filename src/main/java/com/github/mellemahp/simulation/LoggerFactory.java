package com.github.mellemahp.simulation;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import lombok.NonNull;

public class LoggerFactory {
    private static final String FORMAT = "[%1$tF %1$tT] [Thread%2$d:%3$s:%4$s] [%5$-5s] %6$s %n";

    private LoggerFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static Logger createLogger(@NonNull String name) {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(FORMAT,
                        new Date(lr.getMillis()),
                        lr.getThreadID(),
                        lr.getSourceClassName(),
                        lr.getSourceMethodName(),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage());
            }
        });
        logger.addHandler(handler);
        return logger;
    }
}