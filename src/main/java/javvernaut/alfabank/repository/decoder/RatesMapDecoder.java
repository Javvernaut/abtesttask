package javvernaut.alfabank.repository.decoder;

import feign.Response;
import feign.jackson.JacksonDecoder;
import javvernaut.alfabank.model.RatesMap;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Обработчик JSON для добавления хэдеров в объект.(В итоге передумал использовать)
 */

public class RatesMapDecoder extends JacksonDecoder {

    @Override
    public Object decode(Response response, Type type) throws IOException {
        RatesMap ratesMap = (RatesMap) super.decode(response, type);
        ratesMap.setETag(response.headers().get("Etag").stream().findFirst().orElse(""));
        ratesMap.setDate(response.headers().get("Date").stream().findFirst().orElse(""));
        return ratesMap;
    }
}
