package study.search.keyword.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import study.search.keyword.domain.Keyword;

import java.util.List;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword, String> {

    @Query("SELECT k FROM Keyword k ORDER BY k.searchCount DESC")
    List<Keyword> findMostPopularKeywords(Pageable pageable);
}
