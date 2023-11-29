package study.search.blog.remote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import study.search.blog.dto.SearchRequest;
import study.search.blog.remote.client.BlogSearchClient;
import study.search.keyword.service.KeywordService;

import static study.search.blog.domain.ApiProvider.KAKAO;
import static study.search.blog.domain.ApiProvider.NAVER;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlogSearchClientService {

    private final BlogSearchClient kakaoBlogSearchClient;
    private final BlogSearchClient naverBlogSearchClient;
    private final KeywordService keywordService;

    public Mono<Object> getBlogSearchResult(SearchRequest request) {
        keywordService.addKeywordCount(request.query());

        var kakaoRequest = request.toApiProvider(KAKAO);
        var naverRequest = request.toApiProvider(NAVER);

//        var naverRequest = request.toApiProvider(NAVER).toBuilder().query("네이버 블로그 검색 테스트").build();

        return getResultApiProvider(kakaoRequest)
                .onErrorResume(e -> {
                    log.error("ERROR: getBlogSearchResult by kakaoRequest: {}", ExceptionUtils.getStackTrace(e));
                    return getResultApiProvider(naverRequest);
                });
    }

    public Mono<Object> getResultApiProvider(SearchRequest request) {
        return switch (request.apiProvider()) {
            case KAKAO -> kakaoBlogSearchClient.callClient(request);
            case NAVER -> naverBlogSearchClient.callClient(request);
        };
    }
}
