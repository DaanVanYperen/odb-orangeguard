package net.mostlyoriginal.game.system;

import com.artemis.Aspect;
import com.artemis.E;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.MathUtils;
import net.mostlyoriginal.game.component.Desire;
import net.mostlyoriginal.game.component.Effect;
import net.mostlyoriginal.game.component.module.Exit;
import net.mostlyoriginal.game.component.module.Toilet;
import net.mostlyoriginal.game.system.common.FluidSystem;

/**
 * @author Daan van Yperen
 */
public class DesireSystem extends FluidSystem {

    public static final int MISSING_ENTITY_ID = -1;

    public DesireSystem() {
        super(Aspect.all(Desire.class));
    }

    @Override
    protected void process(E e) {
        if (!e.hasHunt()) {
            startHunt(e);
        }
    }

    private void startHunt(E e) {

        int entityId = MISSING_ENTITY_ID;

        switch (e.desireType()) {
            case LEAVE:
                entityId = randomOf(getExits());
                break;
            case POOP:
                entityId = randomOf(getToilets());
                break;
        }

        if (entityId != MISSING_ENTITY_ID) {
            e.huntEntityId(entityId);
        }
    }

    private int randomOf(IntBag exits) {
        return !exits.isEmpty() ? exits.get(MathUtils.random(exits.size()-1)) : MISSING_ENTITY_ID;
    }

    private IntBag getToilets() {
        return world
                .getAspectSubscriptionManager()
                .get(Aspect.all(Toilet.class)).getEntities();
    }


    private IntBag getExits() {
        return world
                .getAspectSubscriptionManager()
                .get(Aspect.all(Exit.class)).getEntities();
    }
}
