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
import com.grnn.chess.Actors.AI.AI;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.PlayerData;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Helge Mikael Landro, 19.03.2018
 * A class to represent the game menu.
 * The user can choose from three levels of difficulty for playing against an AI, or play against a friend
 */
public class SelectPlayerState extends State {

    private final Stage stage;
    private final Skin skin;
    // Variables
    private Texture background, pieces;
    private TextButton playBtn, playBtn2, playBtn3, playBtn4;
    private Texture emoticonEasy, emoticonEasy2, emoticonMedium, emoticonMedium2, emoticonHard, emoticonHard2;
    private int xPlay, yPlay, count;
    private ArrayList<Texture> test;
    private Player player1;
    private PlayerData playerData;
    private boolean player1isWhite;

    // variables for player2 input fields
    private TextField usernameField, passwordField;
    private TextButton menuButton, loginButton;
    private Label message, usernameTxt, passwordTxt;
    private int xPos, yPos;


    /**
     * Constructor for the SelectPlayerState
     * @param gsm, the GameStateManager
     */
    public SelectPlayerState(GameStateManager gsm, Player player, PlayerData playerData){
        super(gsm);
        // init stage and listener
        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));
        background = new Texture("Graphics/Menu/AI_menu_larger.png");
        pieces = new Texture("Graphics/Menu/Menu_pieces.png");
        xPlay = 230;
        yPlay = 350;
        count = 0;
        this.playerData = playerData;
        player1 = player;

        Random random = new Random();
        player1isWhite = random.nextBoolean();

        //buttons
        playBtn = new TextButton("lett", skin);
        playBtn2 = new TextButton("middels", skin);
        playBtn3 = new TextButton("vanskelig", skin);
        playBtn4 = new TextButton("ok", skin);
        playBtn3.setSize(playBtn3.getWidth(), 50);
        playBtn.setSize(playBtn3.getWidth(), 50);
        playBtn2.setSize(playBtn3.getWidth(), 50);
        playBtn4.setSize(playBtn3.getWidth()-70, 50);
        playBtn.setPosition(xPlay, yPlay);
        playBtn2.setPosition(xPlay, yPlay-70);
        playBtn3.setPosition(xPlay, yPlay-140);
        playBtn4.setPosition(xPlay+375, yPlay - 140);


        if(!playerData.isOffline()) {
            // input fields of player 2
            usernameField = new TextField("", skin);
            passwordField = new TextField("", skin);
            usernameTxt = new Label("Bruker", skin);
            passwordTxt = new Label("Passord", skin);
            xPos = (int) (Gdx.graphics.getWidth() / 2 - usernameField.getWidth() / 2 + 200);
            yPos = (int) (Gdx.graphics.getHeight() / 2 + usernameField.getHeight());
            usernameTxt.setPosition(xPos - usernameTxt.getWidth() / 2 - usernameField.getWidth() / 2 + 70, yPos + usernameTxt.getHeight() / 2 );
            usernameField.setPosition(xPos + 70, yPos);
            passwordTxt.setPosition(xPos - passwordTxt.getWidth() / 2 - passwordField.getWidth() / 2 + 70, yPos - usernameField.getHeight() + passwordTxt.getHeight() / 2 );
            passwordField.setPosition(xPos + 70, (int) (yPos - usernameField.getHeight()) );

            //add textfield to display errors, help etc.
            message = new Label("                     ", skin);
            message.setFontScale(0.8f);
            message.setPosition(xPos - message.getWidth()+95, (int) (yPos - usernameField.getHeight()) - 30);

            stage.addActor(message);
            stage.addActor(usernameTxt);
            stage.addActor(passwordTxt);
            stage.addActor(usernameField);
            stage.addActor(passwordField);
        }

            stage.addActor(playBtn);
            stage.addActor(playBtn2);
            stage.addActor(playBtn3);
            stage.addActor(playBtn4);


        // emoticons
        emoticonEasy = new Texture("Graphics/Menu/Animations/Emoji_1/1.png");
        emoticonEasy2 = new Texture("Graphics/Menu/Animations/Emoji_1/2.png");
        emoticonMedium = new Texture("Graphics/Menu/Animations/Emoji_2/3.png");
        emoticonMedium2 = new Texture("Graphics/Menu/Animations/Emoji_2/4.png");
        emoticonHard = new Texture("Graphics/Menu/Animations/Emoji_3/5.png");
        emoticonHard2 = new Texture("Graphics/Menu/Animations/Emoji_3/6.png");
        test = new ArrayList<Texture>();
        test.add(emoticonEasy);
        test.add(emoticonEasy2);
        test.add(emoticonMedium);
        test.add(emoticonMedium2);
        test.add(emoticonHard);
        test.add(emoticonHard2);
    }

    /**
     * Method to handle inputs from the mouse
     */
    public void handleInput() { // TODO: change isWhite for AI?
        // Button for play against AI lett
        if (playBtn.isPressed()) {
            player1.setIsWhite(true);
            gsm.set(new PlayState(gsm, 1, player1, new AI(1, true)));
        }
        // Button for play against AI medium
        if (playBtn2.isPressed()) {
            player1.setIsWhite(true);
            gsm.set(new PlayState(gsm, 2, player1, new AI(2, true)));
        }
        // Button for play against AI vanskelig
        if (playBtn3.isPressed()) {
            player1.setIsWhite(true);
            gsm.set(new PlayState(gsm, 3, player1, new AI(1, true)));
        }


        if(playerData.isOffline()) {
            // Button for play with a friend
            if (playBtn4.isPressed()) {
                player1.setIsWhite(player1isWhite);
                gsm.set(new PlayState(gsm, 0, player1, new Player("Spiller2", "2", false)));  //TODO: should isWhite be initialized here?
            }
        }
        if(!playerData.isOffline()){
            if (playBtn4.isPressed()) {
                player1.setIsWhite(player1isWhite);
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
                Player player2 = playerData.getPlayer(username);
                player2.setIsWhite(!player1isWhite);

                if (password.equals(player2.getPassword())) {
                    gsm.set(new PlayState(gsm,0,player1,player2));
                } else {
                    message.setText("Feil brukernavn eller passord.");
                }
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
        sb.end();
        stage.draw();

        count++;
        // Draw emoticons
        renderEmoticons(sb);
    }

    private void renderEmoticons(SpriteBatch sb) {
        sb.begin();
        if(count  < 15 ) {
            sb.draw(test.get(0), xPlay-30, yPlay);
            sb.draw(test.get(4), xPlay-17, yPlay-88);
            sb.draw(test.get(2), xPlay-10, yPlay-134);

        } else{
            sb.draw(test.get(1), xPlay-30, yPlay);
            sb.draw(test.get(5), xPlay-17, yPlay-88);
            sb.draw(test.get(3), xPlay-10, yPlay-134);

            if(count  == 30)
                count = 0;
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        pieces.dispose();
        emoticonEasy.dispose();
        emoticonEasy2.dispose();
        emoticonMedium.dispose();
        emoticonMedium2.dispose();
        emoticonHard.dispose();
        emoticonHard2.dispose();
    }
}