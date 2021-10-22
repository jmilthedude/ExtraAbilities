package net.ninjadev.extraabilities.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class Config {
    protected abstract String getName();

    protected abstract void reset();

    protected File getRoot() {
        return new File("./config/");
    }

    private String extension() {
        return ".json";
    }

    private File getFile() {
        return new File(getRoot(), getName() + extension());
    }

    protected Gson getGson() {
        return getDefaultGsonBuilder().create();
    }

    public <T extends Config> T read() {
        try {
            Config config = getGson().fromJson(new FileReader(this.getFile()), (Type) this.getClass());

            Config defaultConfig = this.getDefault();

            setMissingDefaults(config, defaultConfig);

            config.write();

            return (T) config;
        } catch (IOException e) {
            this.generate();
        }

        return (T) this;
    }

    private void generate() {
        this.reset();

        try {
            this.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write() throws IOException {
        if (!getRoot().exists() && !getRoot().mkdirs()) return;
        if (!this.getFile().exists() && !this.getFile().createNewFile()) return;
        FileWriter writer = new FileWriter(this.getFile());
        getGson().toJson(this, writer);
        writer.flush();
        writer.close();
    }

    protected GsonBuilder getDefaultGsonBuilder() {
        return new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation();
    }

    private Config getDefault() {
        Class<?> clazz = this.getClass();
        try {
            Config config = (Config) clazz.getDeclaredConstructor().newInstance();
            config.reset();
            return config;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    // this is kinda disgusting but /shrug
    private void setMissingDefaults(Config config, Config defaultConfig) {
        Arrays.stream(config.getClass().getDeclaredFields()).forEach(field -> {
            try {
                if (field.isAnnotationPresent(Expose.class)) {
                    boolean access = field.canAccess(config);
                    field.setAccessible(true);
                    Object stored = field.get(config);
                    Object defaultObj = field.get(defaultConfig);
                    if (stored == null) {
                        field.set(config, defaultObj);
                    } else if (stored instanceof HashMap && defaultObj instanceof HashMap) {
                        HashMap<Object, Object> def = (HashMap<Object, Object>) defaultObj;
                        HashMap<Object, Object> sto = (HashMap<Object, Object>) stored;
                        def.forEach((k, v) -> {
                            if (!sto.containsKey(k)) {
                                sto.put(k, v);
                            }
                        });
                    } else if (stored instanceof List && defaultObj instanceof List) {
                        List<Object> def = (List<Object>) defaultObj;
                        List<Object> sto = (List<Object>) stored;
                        def.forEach((o) -> {
                            if (!sto.contains(o)) {
                                sto.add(o);
                            }
                        });
                    }
                    field.setAccessible(access);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
