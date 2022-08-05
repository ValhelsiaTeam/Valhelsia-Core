package net.valhelsia.valhelsia_core.client.cosmetics.source;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Valhelsia Team
 * @since 2022-07-15
 */
public class ValhelsiaCosmeticsSource extends CosmeticsSource {

    public ValhelsiaCosmeticsSource(String name) {
        super(name);
    }

    @Override
    public List<CosmeticKey> loadCosmeticsFor(UUID uuid) {
        try {
            URL url = new URL("https://valhelsia.net/api/webhook/mod/valhelsia_core/purchases?uuid=" + uuid.toString().replace("-", ""));

            //URL url = new URL("https://valhelsia.net/api/webhook/mod/valhelsia_core/purchases?uuid=435be545e56241878cd5e148908c139b");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            InputStream stream = connection.getInputStream();

            if (connection.getResponseCode() != 200) {
                stream.close();

                if (connection.getErrorStream() != null) {
                    connection.getErrorStream().close();
                }
            } else {
                return this.loadFromJson(GsonHelper.parse(new InputStreamReader(stream)));
            }

        } catch (IOException e) {
            // Either player is offline or hasn't bought any cosmetics.
        }

        return List.of();
    }

    @Override
    public void loadTextures(CosmeticKey key) {
        String name = key.name();

        this.downloadMainTexture(key, "https://static.valhelsia.net/cosmetics/" + name + ".png");

        if (name.contains("cape")) {
            String elytra = name.substring(0, name.length() - 4).concat("elytra");

            this.downloadTexture(key ,"elytra", "https://static.valhelsia.net/cosmetics/" + elytra + ".png");
        } else if (name.equalsIgnoreCase("propeller_cap")) {
            for (int i = 0; i < 10; i++) {
                this.downloadTexture(key, "propeller_animation_" + i, "https://static.valhelsia.net/cosmetics/propeller_animation_" + i + ".png");
            }
        }
    }

    private List<CosmeticKey> loadFromJson(JsonObject jsonObject) {
        if (!jsonObject.has("data")) {
            return List.of();
        }

        List<CosmeticKey> list = new ArrayList<>();
        JsonArray purchases = jsonObject.getAsJsonObject("data").getAsJsonArray("purchases");

        for (JsonElement purchase : purchases) {
            String name = purchase.getAsJsonObject().get("name").getAsString().toLowerCase(Locale.ROOT).replace(" ", "_").replace("'", "");

            if (name.contains("bundle")) {
                Bundle.getCosmeticsFromBundle(name).forEach(cosmetic -> {
                    list.add(new CosmeticKey(this, cosmetic));
                });
            } else {
                list.add(new CosmeticKey(this, name));
            }
        }

        return list;
    }

    private enum Bundle implements StringRepresentable {
        HALLOWEEN_2021("halloween_2021_collection_bundle", "witchs_broom", "cauldron_backpack", "green_witchs_wand", "purple_witchs_wand", "green_witch_hat", "purple_witch_hat");

        private final String name;
        private final List<String> cosmetics;

        Bundle(String name, String... cosmetics) {
            this.name = name;
            this.cosmetics = ImmutableList.copyOf(cosmetics);
        }

        @Nonnull
        @Override
        public String getSerializedName() {
            return this.name;
        }

        public List<String> getCosmetics() {
            return this.cosmetics;
        }

        public static List<String> getCosmeticsFromBundle(String bundleName) {
            for (Bundle bundle : Bundle.values()) {
                if (bundle.getSerializedName().equals(bundleName)) {
                    return bundle.getCosmetics();
                }
            }
            return List.of();
        }
    }
}
