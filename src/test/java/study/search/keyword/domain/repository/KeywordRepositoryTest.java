package study.search.keyword.domain.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.search.keyword.domain.Keyword;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class KeywordRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    KeywordRepository repository;

    @DisplayName("Keyword Insert, Update, Select 테스트")
    @Test
    void repositoryTest() {
        //given
        var searchKeyWord = "테스트";
        var key = new Keyword();
        key.setKeyword(searchKeyWord);
        key.setSearchCount(1L);

        //when
        repository.save(key);
        key.setSearchCount(key.getSearchCount() + 1);
        repository.save(key);

        //query test
        em.flush(); //show insert, update query
        em.clear(); //show select query

        //then
        assertThat(repository.findById(key.getKeyword()).get().getSearchCount()).isEqualTo(2);
    }
}