package study.search.blog.remote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import study.search.blog.dto.SearchRequestDTO;
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

    public Mono<String> getBlogSearchResult(SearchRequestDTO request) {

        var increaseKeywordCountMono = Mono.fromRunnable(() -> keywordService.increaseKeywordCount(request.query()))
                .subscribeOn(Schedulers.boundedElastic());

        var resultApiProviderMono = getResultApiProvider(request.toApiProvider(KAKAO));

        return Mono.when(increaseKeywordCountMono, resultApiProviderMono)
                .then(resultApiProviderMono)
                .onErrorResume(e -> {
                    var naverRequest = request.toApiProvider(NAVER);
                    log.error("ERROR: getBlogSearchResult by kakaoRequest: {}", ExceptionUtils.getStackTrace(e));
                    return getResultApiProvider(naverRequest);
                });
    }

    private Mono<String> getResultApiProvider(SearchRequestDTO request) {
        return switch (request.apiProvider()) {
            case KAKAO -> kakaoBlogSearchClient.callClient(request);
            case NAVER -> naverBlogSearchClient.callClient(request);
        };
    }
}
