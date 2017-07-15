package me.austinatchley.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import me.austinatchley.States.State;

public class Asteroid extends SpaceObject {
    private static final int NUM_ASTEROID_SPRITES = 64;
    private Animation<TextureRegion> animation;


    public Asteroid(World world){
        super(world);
        image = new Texture("asteroid.png");
        sprite = new Sprite(image);
        init();
    }

    public Asteroid(World world, Animation<TextureRegion> animation){
        this(world);
        this.animation = animation;
    }

    public void init() {
        BodyDef asteroidBodyDef = new BodyDef();
        asteroidBodyDef.type = BodyDef.BodyType.DynamicBody;
        asteroidBodyDef.position.set(MathUtils.random(0, State.WIDTH - 90), State.HEIGHT);

        body = world.createBody(asteroidBodyDef);
        body.setLinearDamping(MathUtils.random(0f,3f));
        body.setAngularVelocity(MathUtils.random(-5f, 5f));

        CircleShape asteroidShape = new CircleShape();
        asteroidShape.setRadius(32f);

        FixtureDef asteroidFixtureDef = new FixtureDef();
        asteroidFixtureDef.shape = asteroidShape;
        asteroidFixtureDef.density = 1f;

        Fixture asteroidFixture = body.createFixture(asteroidFixtureDef);
        asteroidFixture.setUserData("Asteroid");
    }

    public void render(SpriteBatch batch){
        float posX = body.getPosition().x;
        float posY = body.getPosition().y;
        float rotation = (float) Math.toDegrees(body.getAngle());
        sprite.setPosition(posX, posY);
        sprite.setRotation(rotation);

        // Then we simply draw it as a normal sprite.
        sprite.draw(batch);
    }
}