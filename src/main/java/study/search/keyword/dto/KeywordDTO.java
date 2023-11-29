package study.search.keyword.dto;

import study.search.keyword.domain.Keyword;

public record KeywordDTO(String keyword,
                         long searchCount) {

    public static KeywordDTO from(Keyword keyword) {
        return new KeywordDTO(keyword.getKeyword(), keyword.getSearchCount());
    }
}
