package javvernaut.alfabank.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class RatesMap {
    private String ETag;
    private String Date;

    @JsonProperty("rates")
    private Map<String, BigDecimal> ratesMap;

    public RatesMap() {
    }

    public String getETag() {
        return ETag;
    }

    public void setETag(String ETag) {
        this.ETag = ETag;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public Map<String, BigDecimal> getRatesMap() {
        return ratesMap == null ? new HashMap<>() : ratesMap;
    }

    public void setRatesMap(Map<String, BigDecimal> ratesMap) {
        this.ratesMap = ratesMap;
    }
}
