package net.ninjadev.extraabilities.world.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.ninjadev.extraabilities.ExtraAbilities;

import java.io.*;

public abstract class Data {

    protected File root = new File(ExtraAbilities.getInstance().getDataFolder(), "data/");
    protected String extension = ".json";

    private boolean dirty = true;

    protected Gson getGson() {
        return new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    }

    public abstract String getName();

    public abstract void tick();

    private File getDataFile() {
        return new File(this.root, this.getName() + this.extension);
    }

    public Data read() {
        try {
            return getGson().fromJson(new FileReader(this.getDataFile()), this.getClass());
        } catch (FileNotFoundException e) {
            this.generate();
        }

        return this;
    }

    public void generate() {
        try {
            this.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() throws IOException {
        if (!this.root.exists() && !this.root.mkdirs()) return;
        if (!this.getDataFile().exists() && !this.getDataFile().createNewFile()) return;
        FileWriter writer = new FileWriter(this.getDataFile());
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

    public boolean isDirty() {
        return dirty;
    }

    public void markDirty() {
        dirty = true;
    }
}
