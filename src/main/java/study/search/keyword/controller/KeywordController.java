package study.search.keyword.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.search.keyword.dto.KeywordsDTO;
import study.search.keyword.service.KeywordService;

@RequiredArgsConstructor
@RequestMapping(("/keyword"))
@RestController
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping("/popular-keywords")
    public KeywordsDTO getMostPopularKeywords() {
        return keywordService.getPopularKeywords();
    }
}
