package study.search.keyword.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import study.search.keyword.domain.Keyword;
import study.search.keyword.domain.repository.KeywordRepository;
import study.search.keyword.dto.KeywordsDTO;

@RequiredArgsConstructor
@Service
public class KeywordService {

    private static final PageRequest TOP_TEN_PAGING = PageRequest.of(0, 10);

    private final KeywordRepository keywordRepository;

    public KeywordsDTO getPopularKeywords() {
        var mostPopularKeywords = keywordRepository.findMostPopularKeywords(TOP_TEN_PAGING);

        return KeywordsDTO.from(mostPopularKeywords);
    }

    public void addKeywordCount(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return;
        }

        var findKeyword = keywordRepository.findById(keyword).orElse(Keyword.emptyOf());
        if (findKeyword.isEmpty()) {
            keywordRepository.save(Keyword.of(keyword, 1));
            return;
        }

        findKeyword.increaseSearchCount();
        keywordRepository.save(findKeyword);
    }
}
