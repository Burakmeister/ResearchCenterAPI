package com.denert.app.rest.aspects;

import com.denert.app.rest.adnotations.PublicsLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.*;

@Aspect
@Component
public class ClassLogger {

    private final LogManager logManager = LogManager.getLogManager();

    @Around("@within(publicsLogger) || @annotation(publicsLogger)")
    public Object logPublicMethods(ProceedingJoinPoint joinPoint, PublicsLogger publicsLogger) throws Throwable {
        Class<?> clazz = joinPoint.getTarget().getClass();
        Logger logger = Logger.getLogger(clazz.getName());

        // Załaduj konfigurację logowania raz (opcjonalnie przenieś do konstruktora/asynchronicznie)
        try {
            logManager.readConfiguration(ClassLogger.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Cannot load logging.properties", e);
        }

        logger.finest(() -> {
            StringBuilder sb = new StringBuilder();
            sb.append(joinPoint.getSignature()).append(", args: ");
            for (Object o : joinPoint.getArgs()) {
                sb.append(o).append(" ");
            }
            return "Public method called: " + sb;
        });

        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Method " + joinPoint.getSignature().getName() + " threw an exception", e);
            throw e;
        }
    }
}
