package net.mostlyoriginal.game.system;

import com.artemis.Aspect;
import com.artemis.E;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import net.mostlyoriginal.api.component.basic.Pos;
import net.mostlyoriginal.game.component.Boxed;
import net.mostlyoriginal.game.system.common.FluidSystem;

/**
 * @author Daan van Yperen
 */
public class BoxPhysicsSystem extends FluidSystem {

    public float scaling = 8f;

    public BoxPhysicsSystem() {
        super(Aspect.all(Pos.class, Boxed.class));
        box2d = new World(new Vector2(0, -98f), true);
        addFloor();
    }

    public World box2d;

    @Override
    protected void initialize() {
        super.initialize();
    }

    protected void addPhysics(E e, float cx, float cy) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = e.getPos().xy.x / scaling;
        bodyDef.position.y = e.getPos().xy.y / scaling;

        Body body = box2d.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(cx / scaling, cy / scaling);

        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        body.createFixture(fixtureDef);

        e.boxedBody(body);

        shape.dispose();
    }

    protected void addFloor() {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.x = 0;
        bodyDef.position.y = 0 / scaling;

        Body body = box2d.createBody(bodyDef);

        EdgeShape shape = new EdgeShape();
        shape.set(0,0,600/scaling,0);
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(20 / scaling, 1000 / scaling);

        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    float cooldown = 0;

    float timeStep = (1.0f/60.0f);

    @Override
    protected void begin() {
        super.begin();

        cooldown = cooldown -= world.delta;
        if ( cooldown <= 0 ) {
            cooldown += timeStep;
            box2d.step(timeStep, 6, 2);
        }
    }

    @Override
    protected void end() {
        super.end();
    }

    @Override
    protected void process(E e) {
        Body body = e.boxedBody();
        e.pos(body.getPosition().x * scaling - e.boundsCx(), body.getPosition().y * scaling - e.boundsCy());
        e.angleRotation((float)Math.toDegrees(body.getAngle()));
        //body.applyForceToCenter(new Vector2(10f,0), true);
    }

    @Override
    protected void dispose() {
        super.dispose();
        if (box2d != null) {
            box2d.dispose();
        }
    }
}
