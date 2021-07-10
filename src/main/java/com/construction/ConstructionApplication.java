package com.construction;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class ConstructionApplication {

    public static void main(String[] args) {
        final var app = new SpringApplicationBuilder(ConstructionApplication.class).web(WebApplicationType.SERVLET);
        app.build().addListeners(new ApplicationPidFileWriter("./bin/shutdown.pid"));
        app.run();
    }
}
