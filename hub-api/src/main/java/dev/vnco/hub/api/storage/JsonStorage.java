package dev.vnco.hub.api.storage;

import com.google.gson.Gson;
import dev.vnco.hub.api.model.Savable;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("all")
public class JsonStorage<O extends Savable<?>> implements Storage<O> {

    private final Gson gson;
    private final Class<O> clazz;

    private final File dir;

    public JsonStorage(@NotNull Gson gson, Class<O> clazz, JavaPlugin plugin) {
        this.gson = gson;
        this.clazz = clazz;

        String name = clazz.getSimpleName().toLowerCase();
        this.dir = new File(plugin.getDataFolder().getAbsolutePath(), (name.endsWith("s") ? name : name + "s"));
    }

    @Override
    public void save(O o) {
        File file = new File(dir, o.getId().toString() + ".json");

        try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            if (!file.exists()) {
                file.createNewFile();
            }

            writer.write(gson.toJson(o, clazz));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public @Nullable O get(String id) {
        File file = new File(dir, id + ".json");

        try (Reader reader = new BufferedReader(new FileReader(file))) {
            return gson.fromJson(reader, clazz);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public Collection<O> values() {
        Set<O> values = new HashSet<>();

        for (File file : Objects.requireNonNull(dir.listFiles(), "File must not be null")) {
            O o = get(file.getName());
            values.add(o);
        }

        return values;
    }
}
