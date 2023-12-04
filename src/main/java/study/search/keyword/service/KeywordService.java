package study.search.keyword.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import study.search.keyword.domain.Keyword;
import study.search.keyword.domain.repository.KeywordRepository;
import study.search.keyword.dto.KeywordsDTO;

@Slf4j
@RequiredArgsConstructor
@Service
public class KeywordService {

    private static final PageRequest TOP_TEN_PAGING = PageRequest.of(0, 10);
    private static final int MAX_RETRY_COUNT = 3;

    private final KeywordRepository keywordRepository;

    public KeywordsDTO getPopularKeywords() {
        var mostPopularKeywords = keywordRepository.findMostPopularKeywords(TOP_TEN_PAGING);

        return KeywordsDTO.from(mostPopularKeywords);
    }

    public void increaseKeywordCount(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return;
        }

        var findKeyword = keywordRepository.findById(keyword).orElse(Keyword.emptyOf());
        if (findKeyword.isEmpty()) {
            insertKeyword(keyword);
            return;
        }

        updateKeywordCount(findKeyword);
    }

    private void insertKeyword(String keyword) {
        try {
            keywordRepository.save(Keyword.of(keyword, 1));
        } catch (Exception e) {
            log.error("FAIL-insert keyword: {}, {}", keyword, ExceptionUtils.getStackTrace(e));
        }
    }

    private void updateKeywordCount(Keyword findKeyword) {
        int retry = 0;

        while (retry < MAX_RETRY_COUNT) {
            try {
                findKeyword.increaseSearchCount();
                keywordRepository.save(findKeyword);

                break;
            } catch (ObjectOptimisticLockingFailureException e) {
                retry++;

                if (retry >= MAX_RETRY_COUNT) {
                    log.error("FAIL-update retry over, keyword: {}, {}", findKeyword.getKeyword(), ExceptionUtils.getStackTrace(e));
                    throw new ObjectOptimisticLockingFailureException("optimistic lock exception", e);
                }
            } catch (Exception e) {
                log.error("FAIL-update keyword: {}, {}", findKeyword.getKeyword(), ExceptionUtils.getStackTrace(e));
                throw new RuntimeException(e);
            }
        }
    }
}
