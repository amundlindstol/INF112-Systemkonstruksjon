package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.PlayerData;
import com.grnn.chess.multiPlayer.ConnectionListener;
import com.grnn.chess.multiPlayer.MultiPlayer;

import java.util.Iterator;

public class WaitForPlayerState extends State{

    private boolean gameIsCreated;
    private Skin skin;
    private Stage stage;
    private Texture background;
    private Player currentPlayer;
    private String player2Name;
    private TextButton menuButton;
    private int xPos;
    private int yPos;
    private BitmapFont fontText;
    private PlayerData playerData;
    private float frameCounter;
    private Animation<TextureRegion> gearAnimation; // Must declare frame type (TextureRegion)
    private Texture gearSheet;
    private TextureRegion finalGearImg;
    private float gearY;
    private float gearX;
    private boolean isOkToSwitchState;
    private MultiPlayer multiPlayer;
    private boolean foundPlayer = false;
    private Thread thread;

    public WaitForPlayerState(GameStateManager gsm, Player currentPlayer, PlayerData playerData, MultiPlayer multiplayer){
        super(gsm);

        this.playerData = playerData;
        this.currentPlayer = currentPlayer;
        this.multiPlayer = multiplayer;

        this.multiPlayer = new MultiPlayer(playerData.getConnection());
        gameIsCreated = multiPlayer.createGame(currentPlayer);

        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));
        background = new Texture("Graphics/Menu/Menu_background.png");
        xPos = 712;
        yPos = 130;
        menuButton = new TextButton("Tilbake", skin);
        menuButton.setPosition((float) (xPos - menuButton.getWidth()*1.08), yPos - menuButton.getHeight() - 15);
        menuButton.setSize(280,60);

        //animate gear
        frameCounter = 0f;
        gearX = 430;
        gearY = 120;
        gearSheet = new Texture(Gdx.files.internal("Graphics/Menu/Animations/GearAnimation.png"));
        // Initialize the Animation with the frame interval and array of frames
        gearAnimation = createAnimation(gearSheet, 5, 9, 0.025f);
        // finalGearImg is used to determine when the animation is complete
        finalGearImg = gearAnimation.getKeyFrames()[gearAnimation.getKeyFrames().length-1];


        fontText = new BitmapFont();
        fontText.setColor(Color.WHITE);
        stage.addActor(menuButton);

        // Register anonymous listener class
         multiPlayer.registerWorkerListener(new ConnectionListener() {
            public void workDone(MultiPlayer multi) {
                System.out.println("Found player");
                player2Name = multi.getP2Name();
                foundPlayer = true;
            }
        });
         thread = new Thread(multiPlayer);
         thread.start();
    }

    public void pushState(){
        Player player2 = playerData.getPlayer(player2Name);
        if (player2Name.length() > 0) {
            gsm.set(new PlayState(gsm, 0, currentPlayer, player2, playerData, true, multiPlayer));
        }
    }


    private Animation<TextureRegion> createAnimation(Texture textureSheet, int frameColums, int frameRows, float duration) {
        // 2D array of TextureRegions
        TextureRegion[][] tmp = TextureRegion.split(textureSheet,
                textureSheet.getWidth() / frameColums,
                textureSheet.getHeight() / frameRows);
        // 1D array in the correct order
        TextureRegion[] animationFrames = new TextureRegion[frameColums * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameColums; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }
        return new Animation<TextureRegion>(duration, animationFrames);
    }

    @Override
    protected void handleInput() {
        if (menuButton.isPressed()) {
            multiPlayer.endGame();
            gsm.set(new StartGameState(gsm, currentPlayer, playerData));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        if (foundPlayer) pushState();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0);
        frameCounter += Gdx.graphics.getDeltaTime();

        fontText.draw(sb, "Venter på spiller 2", 506, 420);

        TextureRegion currentFrame = gearAnimation.getKeyFrame(frameCounter, true);
        sb.draw(currentFrame, gearX, gearY);

        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        thread.interrupt();
        stage.dispose();
        background.dispose();
        fontText.dispose();
        skin.dispose();
        gearSheet.dispose();
        System.out.println("WaitForPlayer State Disposed");
    }
}
