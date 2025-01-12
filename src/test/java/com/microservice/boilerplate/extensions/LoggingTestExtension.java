package com.microservice.boilerplate.extensions;

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingTestExtension implements BeforeTestExecutionCallback {
    private static final Logger logger = LoggerFactory.getLogger(LoggingTestExtension.class);

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        String testClassName = context.getTestClass().map(Class::getSimpleName).orElse("Unknown");
        logger.info("Running test class: {}", testClassName);
    }
}
