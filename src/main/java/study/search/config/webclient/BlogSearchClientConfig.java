package study.search.config.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import study.search.config.webclient.properteis.BlogSearchProperties;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static study.search.blog.domain.ApiProvider.KAKAO;
import static study.search.blog.domain.ApiProvider.NAVER;

@RequiredArgsConstructor
@Configuration
public class BlogSearchClientConfig {

    private static final String NAVER_CLIENT_ID_HEADER = "X-Naver-Client-Id";
    private static final String NAVER_CLIENT_SECRET_HEADER = "X-Naver-Client-SECRET";

    private final BlogSearchProperties properties;

    @Bean
    public WebClient kakaoClient() {
        return WebClient.builder()
                .baseUrl(KAKAO.getUrl())
                .defaultHeader(AUTHORIZATION, properties.getKakao().getAuthorization())
                .build();
    }

    @Bean
    public WebClient naverClient() {
        return WebClient.builder()
                .baseUrl(NAVER.getUrl())
                .defaultHeader(NAVER_CLIENT_ID_HEADER, properties.getNaver().getClientId())
                .defaultHeader(NAVER_CLIENT_SECRET_HEADER, properties.getNaver().getClientSecret())
                .build();
    }
}
