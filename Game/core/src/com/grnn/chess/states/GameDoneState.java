package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.Game;
import com.grnn.chess.PlayerData;
import com.grnn.chess.Result;

public class GameDoneState extends State {

    private Skin skin;
    private Stage stage;
    private Texture background;
    private String message;
    private BitmapFont fontText;
    private TextButton statButton;
    private Game game;
    private PlayerData playerData;

    /**
     * Constructor for State
     *
     * @param gsm
     * @param result1
     * @param result2
     */
    public GameDoneState(GameStateManager gsm, Result result1, Result result2, Game game, PlayerData playerData) {
        super(gsm);
        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));
        background = new Texture("Graphics/Menu/Menu_background.png");

        fontText = new BitmapFont();
        fontText.setColor(Color.WHITE);

        statButton = new TextButton("Se statestikk", skin);
        statButton.setSize(420,60);

        statButton.setPosition(background.getWidth()/2-210, background.getHeight()/2 - 200);
        stage.addActor(statButton);

        this.game = game;
        this.playerData = playerData;

        if(result1 == Result.WIN && result2 == Result.LOSS){
            message = "Du vant "+game.getPlayer1().name;
        }else if(result1 == Result.LOSS && result2 == Result.WIN){
            message = "Du vant "+game.getPlayer2().name;
        }else if(result1 == Result.DRAW && result2 == Result.DRAW){
            message = "Det ble uavgjort";
        }


    }

    @Override
    protected void handleInput() {
        if (statButton.isPressed()) {
            gsm.set(new ShowStatsState(gsm, game.getPlayer1(), playerData));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0);
        fontText.draw(sb,message,background.getWidth()/2-30,background.getHeight()/2-10);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}
