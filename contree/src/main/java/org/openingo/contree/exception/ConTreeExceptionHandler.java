package org.openingo.contree.exception;

import org.openingo.jdkits.http.RespData;
import org.openingo.spring.exception.ServiceException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * TreeExceptionHandler
 *
 * @author Qicz
 */
@RestControllerAdvice
@Order(-5)
public class ConTreeExceptionHandler {

    @ExceptionHandler({ServiceException.class})
    public RespData conTreeExceptionHandler(ServiceException e) {
        return RespData.failure(e.getExceptionCode(), e.getMessage());
    }
}
