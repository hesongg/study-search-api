package study.search.blog.remote.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import study.search.blog.dto.SearchRequest;

@RequiredArgsConstructor
@Component
public class NaverBlogSearchClient implements BlogSearchClient {

    private final WebClient naverWebClient;

    @Override
    public Mono<String> callClient(SearchRequest request) {
        return naverWebClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("query", request.query())
                        .queryParam("sort", request.sort())
                        .queryParam("start", request.page())
                        .queryParam("display", request.size())
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
