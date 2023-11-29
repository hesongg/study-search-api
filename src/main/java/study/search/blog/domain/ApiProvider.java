package study.search.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ApiProvider {

    KAKAO("https://dapi.kakao.com/v2/search/web", "accuracy", "recency"),
    NAVER("https://openapi.naver.com/v1/search/blog.json", "sim", "date");

    @Getter
    private final String url;
    private final String sortTypeAccuracy;
    private final String sortTypeRecency;

    public String getSortTypeByProvider(SortType sortType) {
        return sortType.isAccuracy()
                ? sortTypeAccuracy
                : sortTypeRecency;
    }
}
