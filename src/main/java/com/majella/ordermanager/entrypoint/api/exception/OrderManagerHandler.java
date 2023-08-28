package com.majella.ordermanager.entrypoint.api.exception;

import com.majella.ordermanager.core.exception.BusinessException;
import com.majella.ordermanager.core.exception.DocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class OrderManagerHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ApiError> documentNotFoundExceptionHandler(DocumentNotFoundException exception) {

        var apiError = ApiError.builder()
                .descriptions(List.of(exception.getMessage()))
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> businessExceptionHandler(BusinessException exception) {

        var apiError = ApiError.builder()
                .descriptions(List.of(exception.getMessage()))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception, BindingResult bindingResult) {

        var objectErrors = bindingResult.getAllErrors();

        var descriptions = objectErrors.stream()
                .map(objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale()))
                .toList();

        var apiError = ApiError.builder()
                .descriptions(descriptions)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}
