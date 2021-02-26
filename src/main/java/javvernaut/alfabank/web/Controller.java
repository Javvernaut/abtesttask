package javvernaut.alfabank.web;

import javvernaut.alfabank.repository.Giphy;
import javvernaut.alfabank.repository.OpenExchangeRates;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class Controller {

    private final Giphy giphy;
    private final OpenExchangeRates openExchangeRates;

    public Controller(Giphy giphy, OpenExchangeRates openExchangeRates) {
        this.giphy = giphy;
        this.openExchangeRates = openExchangeRates;
    }

    @RequestMapping(value = "/{currency:[a-zA-Z]{3}}", produces = MediaType.IMAGE_GIF_VALUE)
    public ResponseEntity<byte[]> get(@PathVariable String currency) {
        int shift = openExchangeRates.getShift(currency);
        if (shift == 0) {
            return ResponseEntity.noContent().build();
        }
        ResponseEntity<byte[]> response = ResponseEntity.ok(giphy.getImage(shift));
        giphy.updateImages();
        return response;
    }

    @PostConstruct
    private void init() {
        giphy.updateImages();
        openExchangeRates.updateShiftsMap();
    }
}
