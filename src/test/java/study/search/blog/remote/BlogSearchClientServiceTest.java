package study.search.blog.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.search.blog.dto.SearchRequest;
import study.search.blog.remote.service.BlogSearchClientService;

import static org.assertj.core.api.Assertions.assertThat;
import static study.search.blog.domain.ApiProvider.KAKAO;
import static study.search.blog.domain.ApiProvider.NAVER;

@Slf4j
@SpringBootTest
class BlogSearchClientServiceTest {

    @Autowired
    BlogSearchClientService clientService;

    @Test
    void callKakaoTest() {
        var kakaoResponse = clientService.getBlogSearchResult(SearchRequest.defaultOf(KAKAO, "테스트")).block();

        assertThat(kakaoResponse).isNotNull();

        log.info(String.valueOf(kakaoResponse));
    }

    @Test
    void callNaverTest() {
        var naverResponse = clientService.getBlogSearchResult(SearchRequest.defaultOf(NAVER, "테스트")).block();

        assertThat(naverResponse).isNotNull();

        log.info(String.valueOf(naverResponse));
    }
}