package study.search.blog.dto;

import lombok.Builder;
import study.search.blog.domain.ApiProvider;
import study.search.blog.domain.SortType;

@Builder(toBuilder = true)
public record SearchRequestDTO(ApiProvider apiProvider,
                               String query,
                               SortType sortType,
                               String sort,
                               int page,
                               int size) {

    public static SearchRequestDTO defaultOf(ApiProvider apiProvider,
                                             String query) {
        return SearchRequestDTO.builder()
                .apiProvider(apiProvider)
                .query(query)
                .page(1)
                .size(10)
                .sortType(SortType.ACCURACY)
                .build();
    }

    public static SearchRequestDTO of(SearchRequest request) {
        return SearchRequestDTO.builder()
                .query(request.query)
                .page(request.page)
                .size(request.size)
                .sortType(SortType.from(request.sort))
                .build();
    }

    public SearchRequestDTO toApiProvider(ApiProvider apiProvider) {
        return this.toBuilder()
                .apiProvider(apiProvider)
                .sort(apiProvider.getSortTypeByProvider(sortType))
                .build();
    }
}
