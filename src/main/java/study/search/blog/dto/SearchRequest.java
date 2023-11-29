package study.search.blog.dto;

import lombok.Builder;
import study.search.blog.domain.ApiProvider;
import study.search.blog.domain.SortType;

@Builder(toBuilder = true)
public record SearchRequest(ApiProvider apiProvider,
                            String query,
                            SortType sortType,
                            String sort,
                            int page,
                            int size) {

    public static SearchRequest defaultOf(ApiProvider apiProvider,
                                          String query) {
        return SearchRequest.builder()
                .apiProvider(apiProvider)
                .query(query)
                .page(1)
                .size(10)
                .sortType(SortType.ACCURACY)
                .build();
    }

    public static SearchRequest of(String query,
                                   int page,
                                   int size,
                                   SortType sortType) {
        return SearchRequest.builder()
                .query(query)
                .page(page)
                .size(size)
                .sortType(sortType)
                .build();
    }

    public SearchRequest toApiProvider(ApiProvider apiProvider) {
        return this.toBuilder()
                .apiProvider(apiProvider)
                .sort(apiProvider.getSortTypeByProvider(sortType))
                .build();
    }
}
