package study.search.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchRequest {

    @NotBlank(message = "query must be not null")
    String query;
    int page = 1;
    int size = 10;
    String sort;
}
