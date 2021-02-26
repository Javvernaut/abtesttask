package javvernaut.alfabank.repository.decoder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import javvernaut.alfabank.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Парсинг ноды с прямым УРЛ гифа
 */

@Component
public class GiphyDecoder implements Decoder {

    private final ObjectMapper mapper;

    public GiphyDecoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        JsonNode node = mapper.readTree(response.body().toString()).at("/data/image_url");
        if (node.isMissingNode()) {
            throw new AppException(HttpStatus.BAD_GATEWAY, "Giphy: Url not returned");
        }
        return node.asText();
    }
}
