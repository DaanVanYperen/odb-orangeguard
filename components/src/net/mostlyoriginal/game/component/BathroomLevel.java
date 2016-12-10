package net.mostlyoriginal.game.component;

import com.artemis.Component;
import com.artemis.utils.IntBag;

/**
 * @author Daan van Yperen
 */
public class BathroomLevel extends Component {
    public static final int MAX_SLOTS = 32;

    public BathroomLevel() {}

    public Type[] modules;
    public IntBag moduleEntityIds = new IntBag();
    public boolean initialized;

    public enum Type {
        ENTRANCE,
        TOILET,
        SUPPLY_CLOSET
    }
}