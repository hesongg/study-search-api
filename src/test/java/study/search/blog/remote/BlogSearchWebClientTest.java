package study.search.blog.remote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootApplication
class BlogSearchWebClientTest {

    @Autowired
    BlogSearchWebClient client;

    @Test
    void callKakaoTest() {
        System.out.println(client.callKakao().block());
    }
}