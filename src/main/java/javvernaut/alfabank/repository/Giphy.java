package javvernaut.alfabank.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.RequestLine;
import feign.codec.ErrorDecoder;
import javvernaut.alfabank.exception.GiphyErrorDecoder;
import javvernaut.alfabank.repository.decoder.GiphyDecoder;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Клиент Giphy
 */
@Component
public class Giphy {

    @Value("${giphy.url}")
    private String giphyUrlTemplate;

    @Value("${giphy.growth}")
    private String growthParam;

    @Value("${giphy.fall}")
    private String fallParam;

    @Value("${giphy.image_cache_size}")
    private int cache_size;

    private final ObjectMapper mapper;

    private static ArrayBlockingQueue<byte[]> imagesGrowth;
    private static ArrayBlockingQueue<byte[]> imagesFall;

    public Giphy(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public byte[] getImage(int shift) {
        byte[] image = shift > 0 ? imagesGrowth.poll() : imagesFall.poll();
        if (image == null) {
            String url = getGiphyUrl(shift > 0 ? growthParam : fallParam);
            return getContent(getImageUrl(url));
        }
        return image;
    }

    @Async("processExecutor")
    public void updateImages() {
        if (imagesGrowth.size() > cache_size / 2 && imagesFall.size() > cache_size / 2) {
            return;
        }
        while (imagesGrowth.remainingCapacity() > 0) {
            imagesGrowth.offer(getContent(getImageUrl(getGiphyUrl(growthParam))));
        }
        while (imagesFall.remainingCapacity() > 0) {
            imagesFall.offer(getContent(getImageUrl(getGiphyUrl(fallParam))));
        }

    }

    private String getImageUrl(String url) {
        return Feign.builder()
                .decoder(new GiphyDecoder(mapper))
                .errorDecoder(new GiphyErrorDecoder())
                .target(Client.class, url).getImageUrl();
    }

    private byte[] getContent(String url) {
        return Feign.builder()
                .errorDecoder(new ErrorDecoder.Default())
                .target(Client.class, url)
                .getFile();
    }

    private String getGiphyUrl(String param) {
        return MessageFormatter.format(giphyUrlTemplate, param).getMessage();
    }

    private interface Client {

        @RequestLine("GET")
        String getImageUrl();

        @RequestLine("GET")
        byte[] getFile();
    }

    @PostConstruct
    private void init() {
        imagesGrowth = new ArrayBlockingQueue<>(cache_size);
        imagesFall = new ArrayBlockingQueue<>(cache_size);
    }
}
