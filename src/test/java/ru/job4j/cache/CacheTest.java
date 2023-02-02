package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenAdd() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 1);
        assertThat(cache.add(user1)).isTrue();
        assertThat(cache.get(user1.getId())).isEqualTo(new Base(1, 1));
    }

    @Test
    void whenDoesNotAdd() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 1);
        cache.add(user1);
        assertThat(cache.add(user1)).isFalse();
        assertThat(cache.add(new Base(1, 1))).isFalse();
    }

    @Test
    void whenUpdate() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 1);
        cache.add(user1);
        assertThat(cache.update(new Base(1, 1))).isTrue();
        assertThat(cache.get(1).getId()).isEqualTo(1);
        assertThat(cache.get(1).getVersion()).isEqualTo(2);
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base user1 = new Base(1, 1);
        cache.add(user1);
        cache.delete(user1);
        assertThat(cache.get(user1.getId())).isNull();
    }
}