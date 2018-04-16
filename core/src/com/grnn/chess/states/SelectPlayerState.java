package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.Actors.AI.AI;
import com.grnn.chess.Actors.Player;

import java.util.ArrayList;

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
    private Player humanPlayer;

    /**
     * Constructor for the SelectPlayerState
     * @param gsm, the GameStateManager
     */
    public SelectPlayerState(GameStateManager gsm, Player player){
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
        playBtn4.setPosition(xPlay+380, yPlay);

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
        humanPlayer = player;
    }

    /**
     * Method to handle inputs from the mouse
     */
    public void handleInput() { // TODO: change isWhite for AI?
        // Button for play against AI lett
        if (playBtn.isPressed()) {
            gsm.set(new PlayState(gsm, 1, humanPlayer, new AI(1, true)));
        }
        // Button for play against AI medium
        if (playBtn2.isPressed()) {
            gsm.set(new PlayState(gsm, 2, humanPlayer, new AI(2, true)));
        }
        // Button for play against AI vanskelig
        if (playBtn3.isPressed()) {
            gsm.set(new PlayState(gsm, 3, humanPlayer, new AI(1, true)));
        }
        // Button for play with a friend
        if (playBtn4.isPressed()) {
            gsm.set(new PlayState(gsm,0, humanPlayer, new Player("spiller2", "2", true)));  //TODO: should isWhite be initialized here?
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