package net.valhelsia.valhelsia_core.api.common.registry.helper;

import net.minecraft.util.StringRepresentable;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Valhelsia Team
 * @since 2022-12-27
 */
public class RegistryEntrySet<E, T extends E, K extends Enum<K> & StringRepresentable, R extends RegistryEntry<E, T>> implements Iterable<R> {

    private final Map<K, R> entries;
    private final List<R> entryList;

    public RegistryEntrySet(Class<K> keyType, Function<K, R> entryFactory) {
        EnumMap<K, R> map = new EnumMap<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            map.put(key, entryFactory.apply(key));
        }

        this.entries = Collections.unmodifiableMap(map);
        this.entryList = List.copyOf(map.values());
    }

    public R get(K key) {
        return this.entries.get(key);
    }

    public List<R> getEntries() {
        return this.entryList;
    }

    public Collection<R> values() {
        return this.entryList;
    }

    public Set<K> keySet() {
        return this.entries.keySet();
    }

    public Set<Map.Entry<K, R>> entrySet() {
        return this.entries.entrySet();
    }

    public Map<K, R> asMap() {
        return this.entries;
    }

    public void forEach(BiConsumer<? super K, ? super R> action) {
        this.entries.forEach(action);
    }

    @Override
    public @NonNull Iterator<R> iterator() {
        return this.entryList.iterator();
    }

    public boolean containsKey(K key) {
        return this.entries.containsKey(key);
    }

    public int size() {
        return this.entries.size();
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }
}

