package study.search.keyword.domain.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.search.keyword.domain.Keyword;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class KeywordRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    KeywordRepository repository;

    @Test
    void repositoryTest() {
        var searchKeyWord = "테스트";

        var key = new Keyword();
        key.setKeyword(searchKeyWord);
        key.setSearchCount(1L);

        repository.save(key);

        key.setSearchCount(key.getSearchCount() + 1);
        repository.save(key);

        //query test
        em.flush();
        em.clear();

        assertThat(repository.findById(key.getKeyword()).get().getSearchCount()).isEqualTo(2);
    }
}