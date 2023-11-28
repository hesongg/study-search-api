package study.search.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import study.search.blog.domain.SortType;
import study.search.blog.dto.SearchRequest;
import study.search.blog.remote.service.BlogSearchClientService;

@RequiredArgsConstructor
@RestController
public class BlogSearchController {

    private final BlogSearchClientService blogSearchClientService;

    @GetMapping("/study/blog-search")
    public Mono<String> getBlogSearchResult(@RequestParam String query,
                                            @RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size,
                                            @RequestParam(required = false) String sort) {
        return blogSearchClientService.getBlogSearchResult(SearchRequest.of(query, page, size, SortType.from(sort)));
    }
}
