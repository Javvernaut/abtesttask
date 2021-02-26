package javvernaut.alfabank.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

/**
 * Обработчик ошибок Giphy
 */
public class GiphyErrorDecoder extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                throw new AppException(HttpStatus.BAD_REQUEST, "Giphy: Bad request");
            case 403:
                throw new AppException(HttpStatus.UNAUTHORIZED, "Giphy : Missing or invalid App ID");
        }
        return super.decode(methodKey, response);
    }
}
