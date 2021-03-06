package me.austinatchley.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import me.austinatchley.GameStateManager;
import me.austinatchley.Tools.Starfield;
import me.austinatchley.Tools.Utils;

public class GameOverState extends InterfaceState {
    private Starfield starfield;

    private int score;
    private Label scoreLabel;

    private Label playLabel;

    private float elapsedTime;
    private boolean visible;

    public GameOverState(final GameStateManager gsm) {
        super(gsm);

        setupUITable(gsm);

        starfield = new Starfield(400, camera, null);
        starfield.useVelocity(false);

        elapsedTime = 0f;
        visible = true;

        starfield = new Starfield(400, camera, null);
        starfield.useVelocity(false);
    }

    private void setupUITable(final GameStateManager gsm) {
        final Drawable exitButtonImage =
                new TextureRegionDrawable(new TextureRegion(new Texture("exitbutton.png")));
        final ImageButton exitButton = new ImageButton(exitButtonImage);

        exitButton.setBounds(Utils.WIDTH - exitButton.getPrefWidth(),
                Utils.HEIGHT - exitButton.getPrefHeight(),
                exitButton.getPrefWidth(),
                exitButton.getPrefHeight());
        stage.addActor(exitButton);

        exitButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Gdx.app.exit();
                        System.exit(0);
                    }
                });

        final Label titleLabel = new Label("Game Over", skin, "title");
        table.add(titleLabel).expandX().center().padTop(
                // Don't let the title go off the screen if the top is close
                    -1f * Math.min(titleLabel.getPrefHeight() / 2f, exitButton.getPrefHeight())
                ).spaceBottom(titleLabel.getPrefHeight() / 2f);

        table.row();

        scoreLabel = new Label("Score: ", skin, "text");
        table.add(scoreLabel).expandX();
        table.row();

        final Label highScoreLabel = new Label("High Score: " + gsm.getHighScore(), skin, "text");
        table.add(highScoreLabel).spaceBottom(titleLabel.getPrefHeight() / 2);
        table.row();

        final Drawable playButtonImage =
                new TextureRegionDrawable(new TextureRegion(new Texture("playbutton.png")));
        final ImageButton playButton = new ImageButton(playButtonImage);

        table.add(playButton);

        playButton.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        gsm.set(new GameState(gsm, starfield));
                    }
                });
        table.row();

        playLabel = new Label("Tap to Play Again", skin, "subtitle");
        table.add(playLabel).spaceTop(playLabel.getPrefHeight() / 2f);
    }

    @Override
    protected void handleInput() {}

    public GameOverState(GameStateManager gsm, int score) {
        this(gsm);
        this.score = score;
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        starfield.render();

        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        elapsedTime += dt;

        // Create blinking effect for playLabel
        if (elapsedTime >= .4f) {
            visible = !visible;
            playLabel.setVisible(visible);
            elapsedTime = 0;
        }
    }
}
