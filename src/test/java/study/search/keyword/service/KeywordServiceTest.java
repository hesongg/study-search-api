package study.search.keyword.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import study.search.keyword.domain.Keyword;
import study.search.keyword.domain.repository.KeywordRepository;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class KeywordServiceTest {

    @Autowired
    KeywordService keywordService;

    @Autowired
    KeywordRepository keywordRepository;

    @DisplayName("검색어 검색 횟수로 정렬하여 조회")
    @Test
    void getPopularKeywords_test() {
        var test_keyword_format = "test_";
        int first_PopularItemIndex = 0;
        int tenth_PopularItemIndex = 9;

        //given
        for (int cnt = 1; cnt <= 12; cnt++) {
            keywordRepository.save(Keyword.of(test_keyword_format.concat(String.valueOf(cnt)), cnt));
        }

        //when
        var popularKeywords = keywordService.getPopularKeywords();

        //then
        assertThat(popularKeywords.keywords().size()).isEqualTo(10);
        assertThat(popularKeywords.keywords().get(first_PopularItemIndex).keyword()).isEqualTo(test_keyword_format.concat("12"));
        assertThat(popularKeywords.keywords().get(tenth_PopularItemIndex).keyword()).isEqualTo(test_keyword_format.concat("3"));
    }

    @DisplayName("검색어 insert, 검색 횟수 update")
    @Test
    void increaseKeywordTest() {
        //given
        var testKeyword = "test";
        keywordService.increaseKeywordCount(testKeyword);

        //when
        var keyword = keywordService.getPopularKeywords().keywords().get(0);

        //then
        assertThat(keyword.keyword()).isEqualTo(testKeyword);
        assertThat(keyword.searchCount()).isEqualTo(1);

        //given
        keywordService.increaseKeywordCount(testKeyword);

        //when
        keyword = keywordService.getPopularKeywords().keywords().get(0);

        //then
        assertThat(keyword.searchCount()).isEqualTo(2);
    }

    @DisplayName("update 동시 수행 시 낙관적락 테스트")
    @Test
    void optimisticLockTest() {
        int numberOfThreads = 3;

        var testKeyword = "test";
        keywordService.increaseKeywordCount(testKeyword);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        Future<?> future = executorService.submit(
                () -> keywordService.increaseKeywordCount(testKeyword));
        Future<?> future2 = executorService.submit(
                () -> keywordService.increaseKeywordCount(testKeyword));
        Future<?> future3 = executorService.submit(
                () -> keywordService.increaseKeywordCount(testKeyword));

        Exception result = new Exception();

        try {
            future.get();
            future2.get();
            future3.get();
        } catch (ExecutionException e) {
            result = (Exception) e.getCause();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertThat(result).isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }
}