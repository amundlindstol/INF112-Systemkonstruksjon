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
import com.grnn.chess.multiPlayer.MultiPlayer;

public class WaitForPlayerState extends State{

    private Skin skin;
    private Stage stage;
    private Texture background;
    private Player currentPlayer;
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

    public WaitForPlayerState(GameStateManager gsm, Player currentPlayer, PlayerData playerData, MultiPlayer multiplayer){
        super(gsm);

        this.playerData = playerData;
        this.currentPlayer = currentPlayer;
        this.multiPlayer = multiplayer;

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

        multiplayer.createGame(currentPlayer);

        fontText = new BitmapFont();
        fontText.setColor(Color.WHITE);
        stage.addActor(menuButton);
    }


    private Animation<TextureRegion> createAnimation(Texture textureSheet, int frameColums, int frameRows, float duration) {

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(textureSheet,
                textureSheet.getWidth() / frameColums,
                textureSheet.getHeight() / frameRows);
        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
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
        multiPlayer.endGame();
        stage.dispose();
        background.dispose();
        fontText.dispose();
        skin.dispose();
        gearSheet.dispose();
        System.out.println("WaitForPlayer State Disposed");
    }
}
