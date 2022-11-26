package com.moltenwolfcub.adventurerpg.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

/**
 * A class to hold the {@code Keybind}s and {@code KeybindCategory}s.
 * 
 * @author      MoltenWolfCub
 * @version     %I%
 * @see         Keybinds.Keybind
 * @see         Keybinds.KeybindCategory
 */
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
    public static final Keybind TILE_TRACING = new Keybind("Trace Tiles", InputType.KEY, Keys.SPACE, EDITOR);
    public static final Keybind SET_LAYER_1 = new Keybind("Set Layer 1", InputType.KEY, Keys.NUM_1, EDITOR);
    public static final Keybind SET_LAYER_2 = new Keybind("Set Layer 2", InputType.KEY, Keys.NUM_2, EDITOR);
    public static final Keybind SET_LAYER_3 = new Keybind("Set Layer 3", InputType.KEY, Keys.NUM_3, EDITOR);

	/**
	 * A class for a keybind.
	 * Stores the category that this key is under,
	 * the current {@code InputType} of the keybind,
	 * the keycode that is currently bound and
	 * the currently bound keycode represented as a
	 * string (E.G. 29 -> "A").
	 * <p>
	 * Also stores default values for keycode and
	 * input type.
	 * 
 	 * @author      MoltenWolfCub
 	 * @version     %I%
	 */
    public static class Keybind {
        /**The category this is located in*/
        public final KeybindCategory keybindCategory;
        /**The default keycode Integer that this will reset to.*/
        protected final Integer defaultKeycode;
        /**The default {@code Keybinds.InputType}.*/
        protected final InputType defaultInputType;

        /**The name of the keybind's function.*/
        protected final String name;

        /**The current keycode Integer that is checked for input.*/
        protected Integer keycode;
        /**The current {@code Keybinds.InputType} being used.*/
        protected InputType inputType;
        /**The current keycode represented as a String of the character.*/
        protected String keybindCharName;

        /**
         * Calls {@code Keybinds(String, KeybindCategory)} and
         * fills in the {@code KeybindCategory} (The default
         * keybind values will be set by this constructor).
         * 
         * @param name      The name of the keybind.
         * @see				#Keybind(String, KeybindCategory)
		 * @see				Keybinds#MISC
         */
        public Keybind(String name) {
            this(name, MISC);
        }
        /**
         * Calls {@code Keybinds(String, InputType, Integer, KeybindCategory)}
         * and fills the value of the {@code KeybindCategory} with
         * a default value of {@code MISC}.
         * 
         * 
         * @param name              String for the name of the keybind.
         * @param type              {@code InputType} that this should
         *                          use for the default keybind
		 * @param defaultKeycode    Integer of the default keybind
         *                          (should be obtained from 
         *                          {@code Keys} or {@code Buttons}).
         * @see                     #Keybind(String, InputType, Integer, KeybindCategory)
         * @see                     Buttons
         * @see                     Keys
		 * @see						Keybinds#MISC
         */
        public Keybind(String name, InputType type, Integer defaultKeycode) {
            this(name, type, defaultKeycode, MISC);
        }
        /**
         * Calls {@code Keybinds(String, InputType, Integer, KeybindCategory)}
         * and fills in the values of the default keybind
         * as empty values. (Integer of null and {@code InputType} of
         * {@code InputType.NONE})
         * 
         * @param name          String for the name of the keybind.
         * @param category      The Category that this keybind belongs to.
         * @see                 #Keybind(String, InputType, Integer, KeybindCategory)
         * @see                 Keybinds.InputType#NONE
         */
        public Keybind(String name, KeybindCategory category) {
            this(name, InputType.NONE, null, category);
        }
        /**
		 * Constructor that sets up the default
		 * values and assings the keybind as the
         * default. Also takes in a name for the
         * keybind.
		 * 
		 * @param name          String for the name of keybind.
		 * @param defaultType   The {@code InputType} used by
         *                      the default keybind.
		 * @param defaultKey    Integer of the default keybind
         *                      (should be obtained from 
         *                      {@code Keys} or {@code Buttons}).
		 * @param category      The {@code KeybindCategory} that
         *                      this keybind would be found in.
         * 
         * @see                 Buttons
         * @see                 Keys
         * @see                 Keybinds.KeybindCategory
		 */
		public Keybind(String name, InputType defaultType, Integer defaultKey, KeybindCategory category) {
            keybindCategory = category;
            keybindCategory.keys.add(this);
            defaultKeycode = defaultKey;
            defaultInputType = defaultType;
            setBinding(defaultType, defaultKeycode);

            this.name = name;
        }

        /**
         * Sets the binding of this keybind.
         * <p>
         * Updates the {@code inputType}, {@code keycode}
         * and the {@code keybindCharName} of this key.
         * 
         * @param type      The {@code InputType} of the
         *                  new binding for this key.
         * @param key       The Integer representing the
         *                  Key or Button being set.
         *                  (Should be obtained from {@code Keys}
         *                  or {@code Buttons}).
         * @see             Keybinds.InputType
         * @see             Buttons
         * @see             Keys
         */
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
        
        /**
         * Used to test for this keybind's keydown event
         * @return      If the current keybind was just
         *              pressed in the recent tick.
         * @see         com.badlogic.gdx.Input#isKeyJustPressed
         * @see         com.badlogic.gdx.Input#isButtonJustPressed
         */
        public Boolean isJustPressed() {
            if (keycode == null) { return false; }
            switch (inputType) {
                case KEY: return Gdx.input.isKeyJustPressed(keycode);
                case BUTTON: return Gdx.input.isButtonJustPressed(keycode);
                case NONE: return false;
                default: return false;
            }
        }
        /**
         * Used to test if this keybind is down.
         * @return      If the current keybind is down.
         * @see         com.badlogic.gdx.Input#isKeyPressed
         * @see         com.badlogic.gdx.Input#isButtonPressed
         */
        public Boolean isPressed() {
            if (keycode == null) { return false; }
            switch (inputType) {
                case KEY: return Gdx.input.isKeyPressed(keycode);
                case BUTTON: return Gdx.input.isButtonPressed(keycode);
                case NONE: return false;
                default: return false;
            }
        }
        
        /**
         * Used to get the integer keycode id that is the 
         * same as the mapping in Buttons or Keys class.
         * @return      The current binding that is set
         * @see         Keys
         * @see         Buttons
         */
        public Integer getKey() {
            return keycode;
        }
        /**
         * Gets the current keybind binding as a
         * text representation. E.G. it would return
         * "A" instead of the id 29.
         * @return      The current keybind as
         *              a string of the letter.
         */
        public String getKeyCharStr() {
            return keybindCharName;
        }
        /**
         * Get's the keybind name which is
         * usually the functionality that it
         * is used for.
         * @return      The keybinds name.
         */
        public String getName() {
            return name;
        }
        /**
         * Gets the {@code Keybinds.KeybindCategory} that it's
         * stored and will be found in.
         * @return      The {@code Keybinds.KeybindCategory}
         *              that the keybind is associated with.
         */
        public KeybindCategory getCategory() {
            return keybindCategory;
        }

        /**
         * Sets the keybind to the default
         * keybind.
         * 
         * @see     #defaultKeycode
         * @see     #defaultInputType
         * @see     #setBinding
         */
        public void resetKeybind() {
            setBinding(defaultInputType, defaultKeycode);
        }
        /**
         * Used to find whether the binding is at
         * its default state or not.
         * @return      Whether the keybind is set
         *              to it's default binding.
         */
        public Boolean isDefault() {
            return keycode == defaultKeycode;
        }

        /**
         * Used to find if there is a key or button that will
         * trigger this key or if it's null.
         * @return      Whether the keybind isn't
         *              currently null and is actually
         *              set to something.
         */
        public Boolean hasBinding() {
            return inputType != InputType.NONE && keycode != null;
        }
    }
    /**
     * A class for a Category storing Keybinds.
     * Has a name and a list of keys in it's category.
     * 
     * @author      MoltenWolfCub
     * @version     %I%
     */
    public static class KeybindCategory {
        /** Name of the category */
        public String name;
        /** A list of all the keys stored in this category */
        protected List<Keybind> keys = new ArrayList<Keybind>();

        /**
         * Constructor for a {@code KeybindCategory}
         * @param name		name of the Category
         */
    	public KeybindCategory(String name) {
            this.name = name;
        }

        /**
         * Adds a keybind to this category
         * @param key	The Keybind that is 
		 *              getting added to the category
         * 
         * @see         #keys
         */
        public void addKey(Keybind key) {
            keys.add(key);
        }
		/**
		 * @return		A list of the keys in this category.
         * @see         #keys
		 */
        public List<Keybind> getKeys() {
            return keys;
        }
    }

    /**
     * An enum that stores whether an input
     * is keyboard or mouse.
     * 
     * @author      MoltenWolfCub
     * @version     %I%
     */
    public static enum InputType {
        /** Used for {@code Keys} keybinds. */ KEY,
        /** Used for {@code Buttons} keybinds. */ BUTTON,
        /** Used for unbound keybinds. */ NONE;
    }
}
