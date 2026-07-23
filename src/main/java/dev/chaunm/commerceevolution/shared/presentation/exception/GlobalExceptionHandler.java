package dev.chaunm.commerceevolution.shared.presentation.exception;

import dev.chaunm.commerceevolution.shared.domain.exception.CommonErrorCode;
import dev.chaunm.commerceevolution.shared.domain.exception.ConflictException;
import dev.chaunm.commerceevolution.shared.domain.exception.DomainException;
import dev.chaunm.commerceevolution.shared.domain.exception.NotFoundException;
import dev.chaunm.commerceevolution.shared.domain.exception.UnauthorizedException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Translates domain exceptions raised by any bounded context into RFC 7807
 * problem responses, so controllers stay free of HTTP-status decisions. This class is a
 * presentation/web concern (Spring MVC advice) and therefore lives outside {@code domain}
 * packages, which must stay framework-free.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ProblemDetail handleConflict(ConflictException ex) {
        return problem(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFound(NotFoundException ex) {
        return problem(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ProblemDetail handleUnauthorized(UnauthorizedException ex) {
        return problem(HttpStatus.UNAUTHORIZED, ex);
    }

    @ExceptionHandler(DomainException.class)
    public ProblemDetail handleDomain(DomainException ex) {
        return problem(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ProblemDetail handleOptimisticLocking(OptimisticLockingFailureException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, "The resource was modified concurrently; please retry");
        detail.setTitle(HttpStatus.CONFLICT.getReasonPhrase());
        detail.setProperty("errorCode", CommonErrorCode.CONCURRENT_MODIFICATION.code());
        return detail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle("Validation failed");
        detail.setProperty("errorCode", CommonErrorCode.VALIDATION_FAILED.code());
        detail.setProperty(
                "fieldErrors",
                ex.getBindingResult().getFieldErrors().stream()
                        .collect(java.util.stream.Collectors.toMap(
                                org.springframework.validation.FieldError::getField,
                                fieldError -> fieldError.getDefaultMessage() == null
                                        ? "invalid value"
                                        : fieldError.getDefaultMessage(),
                                (a, b) -> a)));
        return detail;
    }

    private ProblemDetail problem(HttpStatus status, DomainException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        detail.setTitle(status.getReasonPhrase());
        detail.setProperty("errorCode", ex.getErrorCode().code());
        return detail;
    }
}
