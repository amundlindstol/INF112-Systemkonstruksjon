package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.PlayerData;

/**
 *
 * @author Helge Mikael Landro, 21.03.2018
 */

public class RegisterUserState extends State {

    // Variables
    private Texture background, pieces;
    private TextButton confirmBtn, menuBtn;
    private TextField usernameField, passwordField;
    private Label message, usernameTxt, passwordTxt;
    private int xPos, yPos;

    private Skin skin;
    private Stage stage;

    private PlayerData playerData;

    public RegisterUserState(GameStateManager gsm,PlayerData playerData) {
        super(gsm);
        // create skin
        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));

        this.playerData = playerData;

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        usernameTxt = new Label("Bruker", skin);
        passwordTxt = new Label("Passord", skin);
        xPos = (int) (Gdx.graphics.getWidth()/2 - usernameField.getWidth()/2);
        yPos = (int) (Gdx.graphics.getHeight()/2 + usernameField.getHeight());
        usernameTxt.setPosition(xPos - usernameTxt.getWidth()/2 - usernameField.getWidth()/2, yPos+usernameTxt.getHeight()/2);
        usernameField.setPosition(xPos, yPos);
        passwordTxt.setPosition(xPos - passwordTxt.getWidth()/2 - passwordField.getWidth()/2, yPos-usernameField.getHeight() + passwordTxt.getHeight()/2);
        passwordField.setPosition(xPos, (int)(yPos-usernameField.getHeight()));

        //buttons
        xPos = Gdx.graphics.getWidth()/2;
        yPos = Gdx.graphics.getHeight()/2;
        menuBtn = new TextButton("meny", skin);
        confirmBtn = new TextButton("login", skin);
        menuBtn.setSize((float) (menuBtn.getWidth()/1.1), (float) (menuBtn.getHeight()/1.5));
        confirmBtn.setSize(menuBtn.getWidth(), menuBtn.getHeight());
        menuBtn.setPosition((float) (xPos - menuBtn.getWidth()*1.08), yPos - menuBtn.getHeight() - 25);
        confirmBtn.setPosition(xPos, yPos - confirmBtn.getHeight() - 25);

        //add textfield to display errors, help etc.
        message = new Label("                     ", skin);
        message.setPosition(xPos-message.getWidth(), (float) (yPos - usernameField.getHeight()/2));

        stage.addActor(message);
        stage.addActor(usernameTxt);
        stage.addActor(passwordTxt);
        stage.addActor(usernameField);
        stage.addActor(passwordField);
        stage.addActor(menuBtn);
        stage.addActor(confirmBtn);


        background = new Texture("Graphics/Menu/MenuRegister.png");
        pieces = new Texture("Graphics/Menu/Menu_pieces.png");
    }


    @Override
    public void handleInput() {
        if (confirmBtn.isPressed()) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String checkUsr = username.replaceAll("[^A-Za-z0-9]", "");
            String checkPwd = password.replaceAll("[^A-Za-z0-9]", "");

            // valid username and password?
            if (playerData.nameExists(username)) {
                message.setText("Brukernavnet finnes allerede.");
                return;
            } else if (username.length() == 0) {
                message.setText("    Oppgi et brukernavn.");
                return;
            } else if (password.length() == 0) {
                message.setText("      Lag et passord."); //TODO add ascii code æøå
                return;
            } else if (!username.equals(checkUsr) || !password.equals(checkPwd)) {
                message.setText("Spesialtegn kan ikke brukes.");
                return;
            }

            Player player = new Player(username, password, true); //TODO: should isWhite be initialized here?
            playerData.addAccountToDatabase(player);
            gsm.set(new StartGameState(gsm, player));
        }

        if (menuBtn.isPressed()) {
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
        stage.dispose();
        background.dispose();
        pieces.dispose();
    }
}
