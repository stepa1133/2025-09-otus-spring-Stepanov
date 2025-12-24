package ru.otus.hw.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handeNotFoundException(Exception ex) {
        String text = "Что-то случилось, свяжитесь с психиатром если вы используете это приложение";
        return new ModelAndView("error", "errorText", text);
    }

}
