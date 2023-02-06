package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenAdd() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        assertThat(cache.add(model)).isTrue();
        assertThat(cache.get(model)).isEqualTo(new Base(1, 1));
    }

    @Test
    void whenDoesNotAdd() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        assertThat(cache.add(model)).isFalse();
        assertThat(cache.add(new Base(1, 1))).isFalse();
    }

    @Test
    void whenUpdate() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        model.setName("John");
        cache.add(model);
        assertThat(cache.update(new Base(1, 1))).isTrue();
        assertThat(cache.get(model).getId()).isEqualTo(1);
        assertThat(cache.get(model).getVersion()).isEqualTo(2);
        assertThat(cache.get(model).getName()).isEqualTo("John");
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        cache.delete(model);
        assertThat(cache.get(model)).isNull();
    }
}