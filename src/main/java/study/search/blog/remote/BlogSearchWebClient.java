package study.search.blog.remote;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BlogSearchWebClient {

    private final WebClient kakaoClient;
    private final WebClient naverClient;

    public Mono<String> callKakao() {
        return kakaoClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("query", "test").build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
