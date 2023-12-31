package study.search.blog.remote.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import study.search.blog.dto.SearchRequestDTO;

@RequiredArgsConstructor
@Component
public class KakaoBlogSearchClient implements BlogSearchClient {

    private final WebClient kakaoWebClient;

    @Override
    public Mono<String> callClient(SearchRequestDTO request) {
        return kakaoWebClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("query", request.query())
                        .queryParam("sort", request.sort())
                        .queryParam("page", request.page())
                        .queryParam("size", request.size())
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
