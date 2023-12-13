package study.search.blog.remote.client;

import reactor.core.publisher.Mono;
import study.search.blog.dto.SearchRequestDTO;

public interface BlogSearchClient {

    Mono<String> callClient(SearchRequestDTO request);
}
