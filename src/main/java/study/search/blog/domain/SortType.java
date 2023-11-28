package study.search.blog.domain;

import java.util.Arrays;

public enum SortType {
    ACCURACY,
    RECENCY;

    public static SortType from(String sortType) {
        return Arrays.stream(SortType.values())
                .filter(s -> s.name().equalsIgnoreCase(sortType))
                .findAny()
                .orElse(ACCURACY);
    }

    public boolean isAccuracy() {
        return this == ACCURACY;
    }
}
