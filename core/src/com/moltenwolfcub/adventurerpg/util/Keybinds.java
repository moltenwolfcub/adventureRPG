package com.moltenwolfcub.adventurerpg.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

public abstract class Keybinds {
    public static final KeybindCategory MISC = new KeybindCategory("Miscellaneous");
    public static final KeybindCategory MOVEMENT = new KeybindCategory("Movement");
    public static final KeybindCategory EDITOR = new KeybindCategory("Level Editor");

    public static final Keybind FORWARDS = new Keybind("Forwards", InputType.KEY, Keys.W, MOVEMENT);
    public static final Keybind BACKWARDS = new Keybind("Backwards", InputType.KEY, Keys.S, MOVEMENT);
    public static final Keybind LEFT = new Keybind("Left", InputType.KEY, Keys.A, MOVEMENT);
    public static final Keybind RIGHT = new Keybind("Right", InputType.KEY, Keys.D, MOVEMENT);

    public static final Keybind TOGGLE_EDITOR = new Keybind("Toggle Editor", InputType.KEY, Keys.E, EDITOR);
    public static final Keybind PLACE_TILE = new Keybind("Place Tile", InputType.BUTTON, Buttons.RIGHT, EDITOR);
    public static final Keybind CLEAR_TILE = new Keybind("Clear Tile", InputType.BUTTON, Buttons.LEFT, EDITOR);
    public static final Keybind PICK_TILE = new Keybind("Pick Tile", InputType.BUTTON, Buttons.MIDDLE, EDITOR);

    public static class Keybind {
        public final KeybindCategory keybindCategory;
        private final Integer defaultKeycode;
        private final InputType defaultInputType;

        private final String name;

        private Integer keycode;
        private InputType inputType;
        private String keybindCharName;

        public Keybind(String name) {
            this(name, MISC);
        }
        public Keybind(String name, InputType type, int defaultKeycode) {
            this(name, type, defaultKeycode, MISC);
        }
        public Keybind(String name, KeybindCategory category) {
            this(name, InputType.NONE, null, category);
        }
        public Keybind(String name, InputType defaultType, Integer defaultKey, KeybindCategory category) {
            keybindCategory = category;
            keybindCategory.keys.add(this);
            defaultKeycode = defaultKey;
            defaultInputType = defaultType;
            setBinding(defaultType, defaultKeycode);

            this.name = name;
        }

        public void setBinding(InputType type, Integer key) {
            inputType = type;
            keycode = key;
            if (inputType == InputType.KEY && key != null) {
                keybindCharName = Keys.toString(key);
            } else if (inputType == InputType.BUTTON && key != null) {
                switch (key) {
                    case Buttons.RIGHT: keybindCharName = "Right Click"; break;
                    case Buttons.LEFT: keybindCharName = "Left Click"; break;
                    case Buttons.MIDDLE: keybindCharName = "Middle Click"; break;
                    case Buttons.BACK: keybindCharName = "Back Click"; break;
                    case Buttons.FORWARD: keybindCharName = "Forward Click"; break;
                }
            } else {
                keybindCharName = "Unbound";
            }
        }
        
        public boolean isJustPressed() {
            if (keycode == null) { return false; }
            switch (inputType) {
                case KEY: return Gdx.input.isKeyJustPressed(keycode);
                case BUTTON: return Gdx.input.isButtonJustPressed(keycode);
                case NONE: return false;
                default: return false;
            }
        }
        public boolean isPressed() {
            if (keycode == null) { return false; }
            switch (inputType) {
                case KEY: return Gdx.input.isKeyPressed(keycode);
                case BUTTON: return Gdx.input.isButtonPressed(keycode);
                case NONE: return false;
                default: return false;
            }
        }
        
        public int getKey() {
            return keycode;
        }
        public String getKeyCharStr() {
            return keybindCharName;
        }
        public String getName() {
            return name;
        }
        public KeybindCategory getCategory() {
            return keybindCategory;
        }

        public void resetKeybind() {
            setBinding(defaultInputType, defaultKeycode);
        }
        public boolean isDefault() {
            return keycode == defaultKeycode;
        }

        public boolean hasBinding() {
            return inputType != InputType.NONE && keycode != null;
        }
    }
    public static class KeybindCategory {
        public String name;
        private List<Keybind> keys = new ArrayList<Keybind>();

        public KeybindCategory(String name) {
            this.name = name;
        }

        public void addKey(Keybind key) {
            keys.add(key);
        }
        public List<Keybind> getKeys() {
            return keys;
        }
    }

    public static enum InputType {
        KEY,
        BUTTON,
        NONE;
    }
}
