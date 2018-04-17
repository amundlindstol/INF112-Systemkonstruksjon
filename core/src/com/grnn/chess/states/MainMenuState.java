package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.PlayerData;

/**
 * @author Helge Mikael Landro, 21.03.2018
 * A class to represent the main menu.
 * This is the initial state
 */
public class MainMenuState extends State {

    // Variables
    private Texture background, pieces;
    private Texture kingBlack;
    private TextButton loginBtn, registerBtn;
    private int xPos, yPos, countKingX, countKingY;
    private boolean falling = false;

    private Stage stage;
    private Skin skin;

    private PlayerData playerData;

    /**
     * Constructor for the Login State
     * @param gsm, the GameStateManager
     */
    public MainMenuState(GameStateManager gsm) {
        super(gsm);
        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));

        background = new Texture("Graphics/Menu/Menu_background.png");
        pieces = new Texture("Graphics/Menu/Menu_pieces.png");
        kingBlack = new Texture("Graphics/Menu/KingBlack.png");
        playerData = new PlayerData(1);
        xPos = Gdx.graphics.getWidth() / 2;
        yPos = Gdx.graphics.getHeight() / 2;
        if(playerData.isOffline()) {
            loginBtn = new TextButton("offline", skin);

        }else{
            registerBtn = new TextButton("registrer", skin);
            loginBtn = new TextButton("login", skin);


            registerBtn.setPosition(xPos - registerBtn.getWidth() / 2 - 20, yPos);

            stage.addActor(registerBtn);
        }
        loginBtn.setPosition(xPos - loginBtn.getWidth() / 2 - 20, yPos + loginBtn.getHeight());

        loginBtn.setSize(loginBtn.getWidth(), loginBtn.getHeight());
        stage.addActor(loginBtn);

        countKingX = -200;
        countKingY = -1;
    }

    @Override
    public void handleInput() {
        if(playerData.isOffline()){
            if(loginBtn.isPressed()){
                gsm.set(new SelectPlayerState(gsm,new Player("Player1","", true),playerData));
            }
        }else {
            if (loginBtn.isPressed()) {
                gsm.set(new LoginUserState(gsm,playerData)); //change to StartGameState if u want to skip login
            }
            if (registerBtn.isPressed()) {
                gsm.set(new RegisterUserState(gsm,playerData));
            }
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
        animateKing();
        sb.draw(kingBlack, countKingX, countKingY);
        sb.draw(pieces, 0, 0);
        sb.end();
        stage.draw();
    }

    private void animateKing() {
        if (countKingX < xPos - kingBlack.getWidth()/2) {
            countKingX++;
        } else {
            if (!falling)
                countKingY++;
            else if (falling)
                countKingY--;

            if (countKingY < 0)
                falling = false;
            if (countKingY > 10)
                falling = true;
        }
    }

    @Override
    public void dispose() {
        kingBlack.dispose();
        background.dispose();
        pieces.dispose();
        stage.dispose();
    }
}