package study.search.blog.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.search.blog.domain.SortType;
import study.search.blog.dto.SearchRequest;
import study.search.blog.remote.service.BlogSearchClientService;

@RequiredArgsConstructor
@RestController
public class BlogSearchController {

    private final BlogSearchClientService blogSearchClientService;

    @GetMapping("/study/blog-search")
    public ResponseEntity<String> getBlogSearchResult(@RequestParam String query,
                                                      @RequestParam(required = false, defaultValue = "1") int page,
                                                      @RequestParam(required = false, defaultValue = "10") int size,
                                                      @RequestParam(required = false) String sort) {

        //조회 결과 json 반환을 위해 이스케이프 처리 제거함
        //실제 운영 환경에서는 객체에 매핑하여 반환 필요할 듯
        var result = blogSearchClientService.getBlogSearchResult(SearchRequest.of(query, page, size, SortType.from(sort)));
        var unescapeResult = StringEscapeUtils.unescapeJava(result.block());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(unescapeResult);
    }
}
