package com.majority.contactbookapi.config.validate;


import com.fasterxml.jackson.core.JsonParseException;
import com.majority.contactbookapi.dto.FormErrorDto;
import com.majority.contactbookapi.dto.JsonParseErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class FormErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormErrorDto> errorHandler(MethodArgumentNotValidException exception){
        List<FormErrorDto> formErrorsDto = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(field -> {
            String message = messageSource.getMessage(field, LocaleContextHolder.getLocale());

            FormErrorDto formErrorDto = new FormErrorDto(field.getField(), message);

            formErrorsDto.add(formErrorDto);
        });

        return formErrorsDto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonParseException.class)
    public JsonParseErrorDto errorParseJsonHandler(JsonParseException exception){
        JsonParseErrorDto jsonParseError = new JsonParseErrorDto("JSON parse error",exception.getMessage());
        return jsonParseError;
    }

}
