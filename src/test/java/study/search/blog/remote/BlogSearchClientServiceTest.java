package study.search.blog.remote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.search.blog.dto.SearchRequest;
import study.search.blog.remote.service.BlogSearchClientService;
import study.search.keyword.domain.repository.KeywordRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static study.search.blog.domain.ApiProvider.KAKAO;
import static study.search.blog.domain.ApiProvider.NAVER;

@Slf4j
@Transactional
@SpringBootTest
class BlogSearchClientServiceTest {

    @Autowired
    BlogSearchClientService clientService;

    @Autowired
    KeywordRepository repository;

    @Test
    void callKakaoTest() {
        //given
        var testKeyword = "테스트";

        //when
        var kakaoResponse = clientService.getBlogSearchResult(SearchRequest.defaultOf(KAKAO, testKeyword)).block();
        var findKeyword = repository.findById(testKeyword).get();

        //then
        assertThat(kakaoResponse).isNotNull();
        assertThat(findKeyword.getKeyword()).isEqualTo(testKeyword);
        assertThat(findKeyword.getSearchCount()).isEqualTo(1);

        log.info(kakaoResponse);
    }

    @Test
    void callNaverTest() {
        //given
        var testKeyword = "테스트";

        //when
        var naverResponse = clientService.getBlogSearchResult(SearchRequest.defaultOf(NAVER, testKeyword)).block();
        var findKeyword = repository.findById(testKeyword).get();

        //then
        assertThat(naverResponse).isNotNull();
        assertThat(findKeyword.getKeyword()).isEqualTo(testKeyword);
        assertThat(findKeyword.getSearchCount()).isEqualTo(1);

        log.info(naverResponse);
    }
}