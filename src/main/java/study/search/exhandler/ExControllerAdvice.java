package study.search.exhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult validationExceptionHandler(MethodArgumentNotValidException e) {
        log.error("[validation exception handler] ex", e);
        return new ErrorResult(BAD_REQUEST.value(), Optional.ofNullable(e.getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse(""));
    }
}
