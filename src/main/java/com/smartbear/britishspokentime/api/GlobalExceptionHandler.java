package com.smartbear.britishspokentime.api;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // --- 400: JSON body validation (@RequestBody @Valid) ---
  @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
  ProblemDetail handleBindExceptions(BindException ex, HttpServletRequest req) {
    return handleBadRequest(
        req, ex.getBindingResult().getFieldErrors().stream().map(this::toFieldError));
  }

  // ---- helpers ----
  private Map<String, Object> toFieldError(FieldError fe) {
    return Map.of(
        "field", fe.getField(),
        "rejectedValue", fe.getRejectedValue(),
        "message", fe.getDefaultMessage());
  }

  private ProblemDetail handleBadRequest(HttpServletRequest req, Stream<Map<String, Object>> ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setTitle("Bad Request");
    pd.setDetail("One or more request fields are invalid.");
    pd.setProperty("path", req.getRequestURI());

    List<Map<String, Object>> fieldErrors = ex.toList();

    pd.setProperty("errors", fieldErrors);
    return pd;
  }
}
