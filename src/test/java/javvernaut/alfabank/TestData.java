package javvernaut.alfabank;

import java.time.LocalDate;

public class TestData {

    public static final String CURRENCY_CODE = "rub";
    public static final String WRONG_CURRENCY_CODE = "rrur";
    public static final String OER_CURRENT_PATH = "/api/latest.json?app_id=10&base=USD";
    public static final String OER_PREVIOUS_PATH =
            "/api/historical/" + LocalDate.now().minusDays(1) + ".json?app_id=10&base=USD";

    public static final String GIPHY_URL_GROWTH = "/v1/gifs/random?api_key=10&tag=rich";
    public static final String GIPHY_URL_FALL = "/v1/gifs/random?api_key=10&tag=broke";
    public static final String IMAGE_URL_GROWTH = "/media/xULW8ppNg4FI8VquKA/giphy.gif";
    public static final String IMAGE_URL_FALL = "/media/xULW8ppNg4FI8VquKA/gip333.gif";
    public static final byte[] IMAGE_DATA_GROWTH = new byte[]{9, 5, 3, 6};
    public static final byte[] IMAGE_DATA_FALL = new byte[]{3, 2, 7, 7};

}
