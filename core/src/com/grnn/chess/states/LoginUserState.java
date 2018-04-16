package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.PlayerData;

/**
 * A class to for the handling of user login
 * @author Helge Mikael Landro, 21.03.2018
 */

public class LoginUserState extends State {

    // Variables
    private Texture background, pieces;
    private TextField usernameField, passwordField;
    private TextButton menuButton, loginButton;
    private Label message, usernameTxt, passwordTxt;
    private int xPos, yPos;

    private Skin skin;
    private Stage stage;

    /**
     * Constructor for the state
     * @param gsm, GameStateManager
     */
    public LoginUserState(GameStateManager gsm) {
        super(gsm);
        // init stage and listener
        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));

        //textfields
        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        usernameTxt = new Label("Bruker", skin);
        passwordTxt = new Label("Passord", skin);
        xPos = (int) (Gdx.graphics.getWidth()/2 - usernameField.getWidth()/2);
        yPos = (int) (Gdx.graphics.getHeight()/2 + usernameField.getHeight());
        usernameTxt.setPosition(xPos - usernameTxt.getWidth()/2 - usernameField.getWidth()/2, yPos+usernameTxt.getHeight()/2);
        usernameField.setPosition(xPos, yPos);
        passwordTxt.setPosition(xPos - passwordTxt.getWidth()/2 - passwordField.getWidth()/2, yPos-usernameField.getHeight()+passwordTxt.getHeight()/2);
        passwordField.setPosition(xPos, (int)(yPos-usernameField.getHeight()));

        //buttons
        xPos = Gdx.graphics.getWidth()/2;
        yPos = Gdx.graphics.getHeight()/2;
        menuButton = new TextButton("meny", skin);
        loginButton = new TextButton("login", skin);
        menuButton.setSize((float) (menuButton.getWidth()/1.1), (float) (menuButton.getHeight()/1.5));
        loginButton.setSize(menuButton.getWidth(), menuButton.getHeight());
        menuButton.setPosition((float) (xPos - menuButton.getWidth()*1.08), yPos - menuButton.getHeight() - 25);
        loginButton.setPosition(xPos, yPos - loginButton.getHeight() - 25);


        //add textfield to display errors, help etc.
        message = new Label("                     ", skin);
        message.setPosition(xPos-message.getWidth(), (float) (yPos - usernameField.getHeight()/2));

        passwordField.setPasswordMode(true);
        stage.addActor(message);
        stage.addActor(usernameTxt);
        stage.addActor(passwordTxt);
        stage.addActor(usernameField);
        stage.addActor(passwordField);
        stage.addActor(menuButton);
        stage.addActor(loginButton);
        background = new Texture("Graphics/Menu/MenuLogin.png");
        pieces = new Texture("Graphics/Menu/Menu_pieces.png");
    }


    @Override
    public void handleInput() {

        if (loginButton.isPressed()) {
            PlayerData playerData = new PlayerData();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String checkUsr = username.replaceAll("[^A-Za-z0-9]", "");
            String checkPwd = password.replaceAll("[^A-Za-z0-9]", "");
            if (!playerData.nameExists(username)) {
                message.setText("Brukernavnet finnes ikke.");
                return;
            } else if (password != checkPwd || username != checkUsr) {
                message.setText("Spesialtegn kan ikke brukes.");
                return;
            }
            Player player = playerData.getPlayer(username);
            if (password.equals(player.getPassword())) {
                gsm.set(new StartGameState(gsm, player));
            } else {
                message.setText("Feil brukernavn eller passord.");
            }
        }

        if (menuButton.isPressed()) {
            gsm.set(new MainMenuState(gsm));
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
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        pieces.dispose();
        stage.dispose();
    }
}