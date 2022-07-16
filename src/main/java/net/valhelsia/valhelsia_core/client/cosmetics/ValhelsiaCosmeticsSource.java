package net.valhelsia.valhelsia_core.client.cosmetics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;

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
public class ValhelsiaCosmeticsSource implements CosmeticsSource {

    @Override
    public List<Cosmetic> loadCosmeticsFor(UUID uuid) {
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

    private List<Cosmetic> loadFromJson(JsonObject jsonObject) {
        if (!jsonObject.has("data")) {
            return List.of();
        }

        List<Cosmetic> list = new ArrayList<>();
        JsonArray purchases = jsonObject.getAsJsonObject("data").getAsJsonArray("purchases");

        for (JsonElement purchase : purchases) {
            String name = purchase.getAsJsonObject().get("name").getAsString().toLowerCase(Locale.ROOT).replace(" ", "_").replace("'", "");

            if (name.contains("bundle")) {
                CosmeticsBundle.getCosmeticsFromBundle(name).forEach(cosmetic -> {
                    list.add(new Cosmetic(cosmetic, CosmeticsCategory.getForCosmetic(cosmetic)));
                });
            } else {
                list.add(new Cosmetic(name, CosmeticsCategory.getForCosmetic(name)));
            }
        }

        return list;
    }
}
