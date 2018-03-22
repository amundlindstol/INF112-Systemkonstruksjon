package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.Player;
import com.grnn.chess.PlayerData;

/**
 * A class to for the handling of user login
 * @author Helge Mikael Landro, 21.03.2018
 */

public class LoginUserState extends State {

    // Variables
    private Texture background, pieces;
    private Texture confirmBtn;
    private TextField usernameField, passwordField;
    private Label message, usernameTxt, passwordTxt;
    private int xPos, yPos;

    private Skin skin;
    private Stage stage;
    private PolygonSpriteBatch batch;

    /**
     * Constructor for the state
     * @param gsm, GameStateManager
     */
    public LoginUserState(GameStateManager gsm) {
        super(gsm);
        // create skin
        batch = new PolygonSpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));
        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        usernameTxt = new Label("Bruker", skin);
        passwordTxt = new Label("Passord", skin);
        xPos = (int) (Gdx.graphics.getWidth()/2 - usernameField.getWidth()/2);
        yPos = (int) (Gdx.graphics.getHeight()/2 + usernameField.getHeight());
        usernameTxt.setPosition(xPos - usernameTxt.getWidth()/2 - usernameField.getWidth()/2, yPos+usernameTxt.getHeight()/2);
        usernameField.setPosition(xPos, yPos);
        passwordTxt.setPosition(xPos - passwordTxt.getWidth()/2 - passwordField.getWidth()/2, yPos-usernameField.getHeight()+passwordTxt.getHeight()/2);
        passwordField.setPosition(xPos, (int)(yPos-usernameField.getHeight()));
        stage.addActor(usernameTxt);
        stage.addActor(passwordTxt);
        stage.addActor(usernameField);
        stage.addActor(passwordField);

        //add textfield to display errors, help etc.
        message = new Label("                             ", skin);
        message.setPosition(xPos-message.getWidth()/2, (float) (yPos - usernameField.getHeight()*1.5));
        stage.addActor(message);


        background = new Texture("Graphics/Menu/MenuLogin.png");
        pieces = new Texture("Graphics/Menu/Menu_pieces.png");
        confirmBtn = new Texture("Graphics/Menu/menu_button_venn.png");

        xPos = 450;
        yPos = 225;
    }


    @Override
    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY()-Gdx.graphics.getHeight());
        int texturePosX = xPos;
        int texturePosY = yPos;
        if (x > texturePosX && y > texturePosY && x < confirmBtn.getWidth()+texturePosX && y < confirmBtn.getHeight()+texturePosY && Gdx.input.justTouched()) {
            PlayerData playerData = new PlayerData();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String checkUsr = username.replaceAll("[^A-Za-z0-9]", "");
            String checkPwd = password.replaceAll("[^A-Za-z0-9]", "");
            if (!playerData.nameExists(username)) {
                message.setText("Brukernavnet finnes ikke!");
                return;
            } else if (password != checkPwd || username != checkUsr) {
                message.setText("Spesialtegn kan ikke brukes");
                return;
            }
            Player player = playerData.getPlayer(username);
            if (password.equals(player.getPassword())) {
                gsm.set(new StartGameState(gsm, player));
            } else {
                message.setText("Feil brukernavn eller passord");
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
        sb.draw(pieces, 0, 0);
        sb.draw(confirmBtn, xPos, yPos);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}