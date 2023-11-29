package study.search.keyword.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Keyword {

    @Id
    private String keyword;

    private Long searchCount;

    @Version
    private Long version;

    public static Keyword of(String keyword,
                             long searchCount) {
        return new Keyword(keyword, searchCount, null);
    }

    public static Keyword emptyOf() {
        return new Keyword(null, null, null);
    }

    public boolean isEmpty() {
        return keyword == null || searchCount == null;
    }

    public void increaseSearchCount() {
        setSearchCount(searchCount + 1);
    }
}
