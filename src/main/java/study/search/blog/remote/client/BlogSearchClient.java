package study.search.blog.remote.client;

import reactor.core.publisher.Mono;
import study.search.blog.dto.SearchRequest;

public interface BlogSearchClient {

    Mono<String> callClient(SearchRequest request);
}
