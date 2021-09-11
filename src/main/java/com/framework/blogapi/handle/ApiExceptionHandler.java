package com.framework.blogapi.handle;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.framework.blogapi.dto.ErrorResponse;
import com.framework.blogapi.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<List<ErrorDetails>> handleNumberFormatException(NumberFormatException exception, Locale locale) {
        LOG.error("Error not expected ", exception);
        return ResponseEntity.badRequest()
                .body(Arrays.asList(new ErrorDetails("Existe(m) um ou mais parâmetros numéricos inválidos")));
    }

    @ExceptionHandler(DataConversionException.class)
    public ResponseEntity<ErrorDetails> handleDataConversionHSException(DataConversionException conversionException,
                                                                        Locale locale) {
        ErrorDetails error = new ErrorDetails("Falha na compatibilidade de dados do Health Connect");
        if (conversionException.getStatus() < 0)
            error = new ErrorDetails("Tempo limite expirado para a resposta dessa requisição");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(ServiceDownException.class)
    public ResponseEntity<ErrorDetails> handleServiceDownException(ServiceDownException serviceDownException,
                                                                   Locale locale) {
        ErrorDetails error = new ErrorDetails("Erro na conexão com o barramento");
        LOG.warn(error.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails(notFoundException.getMessage()));
    }



    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<List<ErrorDetails>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception, Locale locale) {
        String msg = "Parametro " + exception.getParameterName() + " não contém valor";
        return ResponseEntity.badRequest().body(Arrays.asList(new ErrorDetails(msg)));
    }

    @ExceptionHandler(HtmlCoverterException.class)
    public ResponseEntity<ErrorDetails> handleHtmlCoverterException(HtmlCoverterException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDetails(exception.getMessage()));
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<List<ErrorDetails>> handleInvalidFormatException(InvalidFormatException exception, Locale locale) {
        String msg = "Valor inválido para " + exception.getPath().get(0).getFieldName() + ": " + exception.getValue();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Arrays.asList(new ErrorDetails(msg)));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<List<ErrorDetails>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        List<ErrorDetails> errors = new ArrayList<>();
        ErrorDetails error = new ErrorDetails("Valor para " + exception.getName() + " é inválido");
        LOG.error("erro ao conerter parametro: {} ", exception.getMessage());
        errors.add(error);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<List<ErrorDetails>> handleConversionFailedException(ConversionFailedException exception) {
        List<ErrorDetails> errors = new ArrayList<>();
        ErrorDetails error = new ErrorDetails(exception.getMessage());
        LOG.error("erro ao conerter parametro: {} ", exception.getMessage());
        if (exception.getTargetType().getObjectType().equals(LocalDate.class))
            error = new ErrorDetails(
                    String.format("Falha ao completar a requisição, a data '%s' não corresponde ao formato requerido",
                            exception.getValue()));
        errors.add(error);
        return ResponseEntity.badRequest().body(errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return new ErrorResponse<String>(errors);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse<String> handleValidationExceptions(BadCredentialsException ex) {
        List<String> errors = new ArrayList();
        errors.add("E-mail/senha incorretos");
        return new ErrorResponse<String>(errors);
    }
}
