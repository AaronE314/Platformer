package com.ex.plat.scenes;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.ex.plat.Constants.V_HEIGHT;
import static com.ex.plat.Constants.V_WIDTH;

public class HUD {

    private int score;
    private int lives;

    private Stage stage;
    private Viewport viewport;

    private Label scoreLabel, livesLabel, scoreWordLabel, livesWordLabel;

    public HUD(SpriteBatch sb) {

        score = 0;
        lives = 3;

        viewport = new FitViewport(V_WIDTH, V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesLabel = new Label(String.format("%03d", lives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreWordLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        livesWordLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(scoreWordLabel).expandX().padTop(10);
        table.add(livesWordLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(livesLabel).expandX();

        stage.addActor(table);

    }

    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();

    }

}
