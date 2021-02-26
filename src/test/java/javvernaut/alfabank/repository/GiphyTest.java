package javvernaut.alfabank.repository;

import javvernaut.alfabank.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static javvernaut.alfabank.TestData.IMAGE_DATA_FALL;
import static javvernaut.alfabank.TestData.IMAGE_DATA_GROWTH;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class GiphyTest extends BaseTest {

    @Autowired
    private Giphy giphy;

    @Test
    void getImage1() {
        assertArrayEquals(giphy.getImage(1), IMAGE_DATA_GROWTH);
    }

    @Test
    void getImage0() {
        assertArrayEquals(giphy.getImage(0), IMAGE_DATA_FALL);
    }

    @Test
    void getImageM1() {
        assertArrayEquals(giphy.getImage(-1), IMAGE_DATA_FALL);
    }
}