package study.search.keyword.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import study.search.keyword.domain.Keyword;

import java.util.List;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword, String> {

    List<Keyword> findTop10ByOrderBySearchCountDesc();
}
