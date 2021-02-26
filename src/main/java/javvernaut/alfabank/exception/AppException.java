package javvernaut.alfabank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Исключение для обработки внутри приложения
 */
public class AppException extends ResponseStatusException {
    public AppException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
