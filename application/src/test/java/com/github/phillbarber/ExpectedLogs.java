package com.github.phillbarber;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.read.ListAppender;
import org.junit.rules.ExternalResource;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class ExpectedLogs extends ExternalResource {
    public static final String APPENDER_NAME = ExpectedLogs.class.getSimpleName();
    private ListAppender<ILoggingEvent> listAppender;


    public static ExpectedLogs forRootLogger() {
        return new ExpectedLogs();
    }

    @Override
    protected void before() throws Throwable {
        listAppender = new ListAppender<>();
        listAppender.setName(APPENDER_NAME);
        listAppender.start();
        rootLogger().addAppender(listAppender);
    }

    @Override
    protected void after() {
        rootLogger().detachAppender(APPENDER_NAME);
    }

    private Logger rootLogger() {
        return (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    }

    public void assertLogsContain(Level level, String message) {
        assertThat(hasLogEvent(level, message))
                .withFailMessage(format("Log event not found. Expected log event with level '%s' and message '%s'. \nActual log events: %s", level, message, logEvents()))
                .isTrue();
    }

    public void assertLogsDoNotContain(Level level, String message) {
        assertThat(hasLogEvent(level, message))
                .withFailMessage(format("Log event found. Expected log event with level '%s' and message containing '%s' to NOT be present\n" +
                        "Actual log events: %s", level, message, logEvents()))
                .isFalse();
    }

    private List<ILoggingEvent> logEvents() {
        return listAppender.list;
    }

    private boolean hasLogEvent(Level level, String message) {
        return logEvents().stream()
                .filter(log -> logLevelMatches(level, log))
                .filter(log -> logMessageMatches(message, log))
                .findAny()
                .isPresent();
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private boolean hasLogEvent(Level level, String message, Throwable exception) {
        return logEvents().stream()
                .filter(log -> logLevelMatches(level, log))
                .filter(log -> logMessageMatches(message, log))
                .filter(log -> throwableMatches(exception, log))
                .findAny().isPresent();
    }

    private boolean throwableMatches(Throwable exception, ILoggingEvent log) {
        return ((ThrowableProxy)log.getThrowableProxy()).getThrowable().equals(exception);
    }

    private boolean logMessageMatches(String message, ILoggingEvent log) {
        return log.getFormattedMessage().contains(message);
    }

    private boolean logLevelMatches(Level level, ILoggingEvent log) {
        return log.getLevel().equals(level);
    }
}

