package net.valhelsia.valhelsia_core.core.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;

/**
 * Config Spec <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.config.ConfigSpec
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2022-03-07
 */
public interface ConfigSpec<T extends ConfigSpec<T>> extends UnmodifiableConfig {
    @SuppressWarnings("unchecked")
    default T self() {
        return (T) this;
    }

    void acceptConfig(CommentedConfig data);

    boolean isCorrecting();

    boolean isCorrect(CommentedConfig commentedFileConfig);

    int correct(CommentedConfig commentedFileConfig);

    void afterReload();
}
