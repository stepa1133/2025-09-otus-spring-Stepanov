package ru.otus.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.shell.Availability;
import org.springframework.shell.AvailabilityProvider;
import ru.otus.hw.service.LocalizedMessagesService;
import ru.otus.hw.service.LoginContext;

@Configuration
@Profile("shell")
public class ShellConfig {
    @Bean
    public AvailabilityProvider shellAvailabilityProvider(LoginContext loginContext,
                                                          LocalizedMessagesService messageService) {
        return () -> loginContext.isStudentSignIn()
                    ? Availability.available()
                    : Availability.unavailable(messageService.getMessage("Shell.need.sign.info"));
    }
}
