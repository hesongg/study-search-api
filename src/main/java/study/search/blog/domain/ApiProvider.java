package study.search.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ApiProvider {

    KAKAO("https://dapi.kakao.com/v2/search/web"),
    NAVER("https://openapi.naver.com/v1/search/blog.json");

    @Getter
    private final String url;
}
