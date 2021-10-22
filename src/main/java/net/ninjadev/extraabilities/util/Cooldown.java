package net.ninjadev.extraabilities.util;

public class Cooldown {

    private int value = -1;

    public void update() {
        value--;
    }

    public boolean isActive() {
        return value >= 0;
    }

    public void set(int value) {
        this.value = value;
    }
}
