package ru.otus.hw.config.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class IdMappingCache {

    private final Map<Long, String> authorIdMap = new HashMap<>();

    private final Map<Long, String> genreIdMap = new HashMap<>();

    private final Map<Long, String> bookIdMap = new HashMap<>();

    private final Map<Long, String> commentIdMap = new HashMap<>();

    public String getOrCreateAuthorUuid(Long h2Id) {
        return authorIdMap.computeIfAbsent(h2Id, id -> UUID.randomUUID().toString());
    }

    public String getOrCreateGenreUuid(Long h2Id) {
        return genreIdMap.computeIfAbsent(h2Id, id -> UUID.randomUUID().toString());
    }

    public String getOrCreateBookUuid(Long h2Id) {
        return bookIdMap.computeIfAbsent(h2Id, id -> UUID.randomUUID().toString());
    }

    public String getOrCreateCommentUuid(Long h2Id) {
        return commentIdMap.computeIfAbsent(h2Id, id -> UUID.randomUUID().toString());
    }
}