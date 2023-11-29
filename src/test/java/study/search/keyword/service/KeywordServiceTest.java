package study.search.keyword.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.search.keyword.domain.Keyword;
import study.search.keyword.domain.repository.KeywordRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KeywordServiceTest {

    @Autowired
    KeywordService keywordService;

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    void getPopularKeywords_test() {
        int firstPopularItemIndex = 0;
        int tenthPopularItemIndex = 9;

        for (int i = 1; i <= 12; i++) {
            keywordRepository.save(Keyword.of("test".concat(String.valueOf(i)), i));
        }

        var popularKeywords = keywordService.getPopularKeywords();

        assertThat(popularKeywords.keywords().size()).isEqualTo(10);
        assertThat(popularKeywords.keywords().get(firstPopularItemIndex).keyword()).isEqualTo("test12");
        assertThat(popularKeywords.keywords().get(tenthPopularItemIndex).keyword()).isEqualTo("test3");
    }
}