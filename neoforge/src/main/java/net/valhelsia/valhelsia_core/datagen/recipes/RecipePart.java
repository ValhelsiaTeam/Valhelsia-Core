package net.valhelsia.valhelsia_core.datagen.recipes;

/**
 * @author Valhelsia Team
 * @since 2022-10-24
 */
public class RecipePart<T> {

    private final T part;

    public RecipePart(T part) {
        this.part = part;
    }

    public static <T> RecipePart<T> of(T part) {
        return new RecipePart<>(part);
    }

    T get() {
        return this.part;
    }
}
