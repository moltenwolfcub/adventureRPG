package com.moltenwolfcub.adventurerpg.util;

import com.badlogic.gdx.Input.Keys;

public abstract class Keybinds {
    public static final KeybindCategory MISC = new KeybindCategory("Miscellaneous");
    public static final KeybindCategory MOVEMENT = new KeybindCategory("Movement");
    public static final KeybindCategory EDITOR = new KeybindCategory("Level Editor");

    public static final Keybind FORWARDS = new Keybind("Forwards", MOVEMENT, Keys.W);
    public static final Keybind BACKWARDS = new Keybind("Backwards", MOVEMENT, Keys.S);
    public static final Keybind LEFT = new Keybind("Left", MOVEMENT, Keys.A);
    public static final Keybind RIGHT = new Keybind("Right", MOVEMENT, Keys.D);

    public static final Keybind TOGGLE_EDITOR = new Keybind("Toggle Editor", EDITOR, Keys.E);

    public static class Keybind {
        public final KeybindCategory keybindCategory;
        private final int defaultKeycode;

        private String name;

        private int keybindCode;
        private String keybindCharacter;

        public Keybind(String name) {
            this(name, MISC);
        }
        public Keybind(String name, int defaultKeycode) {
            this(name, MISC, defaultKeycode);
        }
        public Keybind(String name, KeybindCategory category) {
            this(name, category, 0);
        }
        public Keybind(String name, KeybindCategory category, int defaultKeycode) {
            keybindCategory = category;
            this.defaultKeycode = defaultKeycode;
            setChar(defaultKeycode);

            this.name = name;
        }

        public void setChar(int keycode) {
            keybindCode = keycode;
            keybindCharacter = Keys.toString(keycode);
        }
        public int getKeyCode() {
            return keybindCode;
        }
        public String getKeyCharacter() {
            return keybindCharacter;
        }
        public String getName() {
            return name;
        }

        public void resetKeybind() {
            setChar(defaultKeycode);
        }
        public boolean isDefault() {
            return keybindCode == defaultKeycode;
        }
    }
    public static class KeybindCategory {
        public String name;

        public KeybindCategory(String name) {
            this.name = name;
        }
    }
}
