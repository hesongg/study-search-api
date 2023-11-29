package study.search.keyword.dto;

import study.search.keyword.domain.Keyword;

import java.util.List;

public record KeywordsDTO(List<KeywordDTO> keywords) {

    public static KeywordsDTO from(List<Keyword> keywords) {
        return new KeywordsDTO(keywords.stream()
                .map(KeywordDTO::from)
                .toList());
    }
}
