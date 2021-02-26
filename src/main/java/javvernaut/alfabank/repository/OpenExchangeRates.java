package javvernaut.alfabank.repository;

import feign.Feign;
import feign.RequestLine;
import javvernaut.alfabank.exception.AppException;
import javvernaut.alfabank.exception.OerErrorDecoder;
import javvernaut.alfabank.model.RatesMap;
import javvernaut.alfabank.repository.decoder.RatesMapDecoder;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Клиент OpenExchangeRates
 */

@Component
public class OpenExchangeRates {

    @Value("${openexchangerates.current_url}")
    private String currentUrl;

    @Value("${openexchangerates.previous_url}")
    private String previousUrlTemplate;

    private static Map<String, Integer> shiftsMap = new HashMap<>();

    public int getShift(String currency) {
        return shiftsMap.get(currency.toUpperCase()) != null ? shiftsMap.get(currency.toUpperCase()) : 0;
    }

    @Async("processExecutor")
    @Scheduled(cron = "${openexchangerates.schedule}")
    public void updateShiftsMap() {
        String previousURl = MessageFormatter.format(previousUrlTemplate, LocalDate.now().minusDays(1)).getMessage();
        Map<String, BigDecimal> currentRatesMap = getRates(currentUrl).getRatesMap();
        Map<String, BigDecimal> previousRatesMap = getRates(previousURl).getRatesMap();
        shiftsMap = currentRatesMap.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    if (previousRatesMap.get(e.getKey()) != null) {
                        return e.getValue().compareTo(previousRatesMap.get(e.getKey()));
                    }
                    return 0;
                }));
    }

    private RatesMap getRates(String url) {
        RatesMap ratesMap = Feign.builder()
                .decoder(new RatesMapDecoder())
                .errorDecoder(new OerErrorDecoder())
                .target(Client.class, url)
                .getRatesMap();
        if (ratesMap.getRatesMap().isEmpty()) {
            throw new AppException(HttpStatus.NOT_FOUND, "OpenExchangeRates did not return data");
        }
        return ratesMap;
    }

    private interface Client {
        @RequestLine("GET")
        RatesMap getRatesMap();
    }
}
