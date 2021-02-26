package javvernaut.alfabank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AppException.class})
    public ResponseEntity<?> handleAppException(AppException ex) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", ex.getStatus().value());
        map.put("error", ex.getStatus().getReasonPhrase());
        map.put("message", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(map);
    }
}
