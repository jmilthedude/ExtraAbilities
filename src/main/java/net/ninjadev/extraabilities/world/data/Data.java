package net.ninjadev.extraabilities.world.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.ninjadev.extraabilities.ExtraAbilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class Data {

    protected String extension = ".json";

    private boolean dirty = true;

    protected Gson getGson() {
        return getDefaultGsonBuilder().create();
    }

    protected abstract void reset();

    public abstract String getName();

    protected String getDirectory() {
        return "data";
    }

    private File getRoot() {
        return new File(ExtraAbilities.getInstance().getDataFolder(), getDirectory());
    }

    public abstract void tick();

    private File getFile() {
        return new File(getRoot(), getName() + extension);
    }

    public <T extends Data> T read() {
        try {
            Data config = getGson().fromJson(new FileReader(this.getFile()), (Type) this.getClass());

            Data defaultConfig = this.getDefault();

            setMissingDefaults(config, defaultConfig);

            config.write();

            return (T) config;
        } catch (IOException e) {
            this.generate();
        }

        return (T) this;
    }

    protected GsonBuilder getDefaultGsonBuilder() {
        return new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation();
    }

    public void generate() {
        try {
            this.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() throws IOException {
        if (!this.getRoot().exists() && !this.getRoot().mkdirs()) return;
        if (!this.getFile().exists() && !this.getFile().createNewFile()) return;
        FileWriter writer = new FileWriter(this.getFile());
        getGson().toJson(this, writer);
        writer.flush();
        writer.close();
    }

    public void save() {
        if (!this.isDirty()) return;
        try {
            this.write();
            this.dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private <T extends Data> T getDefault() {
        Class<?> clazz = this.getClass();
        try {
            T config = (T) clazz.getDeclaredConstructor().newInstance();
            config.reset();
            return config;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    // this is kinda disgusting but /shrug
    private <T extends Data> void setMissingDefaults(T data, T defaultData) {
        Arrays.stream(data.getClass().getDeclaredFields()).forEach(field -> {
            try {
                if (field.isAnnotationPresent(Expose.class)) {
                    boolean access = field.canAccess(data);
                    field.setAccessible(true);
                    Object stored = field.get(data);
                    Object defaultObj = field.get(defaultData);
                    if (stored == null) {
                        field.set(data, defaultObj);
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

    public boolean isDirty() {
        return dirty;
    }

    public void markDirty() {
        dirty = true;
    }
}
