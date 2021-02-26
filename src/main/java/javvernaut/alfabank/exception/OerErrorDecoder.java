package javvernaut.alfabank.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

/**
 * Обработчик ошибок OpenExchangeRates
 */

public class OerErrorDecoder extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                throw new AppException(HttpStatus.BAD_REQUEST, "OpenExchangeRates: Unsupported base currency");
            case 401:
                throw new AppException(HttpStatus.UNAUTHORIZED, "OpenExchangeRates: Missing or invalid App ID");
            case 405:
            case 429:
                throw new AppException(HttpStatus.BAD_REQUEST, "OpenExchangeRates: Method not allowed");
        }
        return super.decode(methodKey, response);
    }
}
