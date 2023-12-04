package study.search.config.webclient.properteis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("api.blog-search-auth")
@Configuration
public class BlogSearchProperties {

    private KakaoAuth kakao;
    private NaverAuth naver;

    @Getter
    @Setter
    public static class KakaoAuth {
        private String authorization;
    }

    @Getter
    @Setter
    public static class NaverAuth {
        private String clientId;
        private String clientSecret;
    }
}
