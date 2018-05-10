package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.*;
import com.grnn.chess.Actors.AI.AI;
import com.grnn.chess.Actors.IActor;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.objects.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Amund 15.03.18
 */
public class PlayState extends State {

    // Variables
    Game game;
    Board board;
    PlayerData playerData;

    private Texture bg;
    private Texture bgBoard;
    private Texture potentialTex;
    private Texture hintTex;
    private Texture captureTex;
    private Texture victoryTex;
    private ArrayList<Texture> pieceTexures;
    private ArrayList<Position> positions;
    private Position prevMove;

    private ArrayList<Position> potentialMoves;
    private ArrayList<Position> captureMoves;
    private ArrayList<Position> castlingMoves;
    private Move helpingMove;
    private TranslateToCellPos translator;


    private Boolean activegame;
    private BitmapFont fontText;
    private BitmapFont fontCounter;

    private String text;
    private String player1Name;
    private String player2Name;
    private TextButton resignBtn;
    private TextButton helpBtn;
    private Stage stage;
    private Skin skin;

    private int[] removedPieces;

    private Player player1;
    private Player player2;


    // piece animation
    private ArrayList<Position> animationPath;
    private int animationIndex;
    private boolean pieceIsMoving;
    private Move prevAImove;
    // victory animation
    private float frameCounter;
    private Animation<TextureRegion> confettiAnimation; // Must declare frame type (TextureRegion)
    private Texture confettiSheet;
    private TextureRegion finalConfettiImg;
    private float confettiY;
    private float confettiX;
    private Label victoryLabel;
    private boolean isOkToSwitchState = false;

    boolean playingCH;

    /**
     * @param gsm      Game state
     * @param aiPlayer Level of AI player
     * @param player1  Should always be player
     * @param player2  Either AI or Player
     */
    public PlayState(GameStateManager gsm, int aiPlayer, IActor player1, IActor player2, PlayerData playerData, String gameMode) {
        super(gsm);


        if (player1 instanceof Player) {
            this.player1 = (Player) player1;
        }
        if(player2 instanceof Player) {
            this.player2 = (Player) player2;
        }

        //textures
        if (gameMode == "crazyhouse") {
            playingCH = true;
            bg = new Texture("Graphics/GUI/GUICrazyhouse.png");
        }
        else bg = new Texture("Graphics/GUI/GUI.png");
        bgBoard = new Texture("Graphics/GUI/ChessBoard.png");
        pieceTexures = new ArrayList<Texture>();
        positions = new ArrayList<Position>();

        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));

        if (!(player1 instanceof Player)) {
            throw new IllegalArgumentException("player1 should always be of class Player");
        }
        //game
        game = new Game(aiPlayer, player1, player2);
        this.playerData = playerData;
        board = game.getBoard();
        potentialMoves = game.getValidMoves();
        captureMoves = game.getCaptureMoves();
        text = game.getText();
        removedPieces = game.getRemovedPieces();
        player1Name = ((Player) player1).name;
        if (player2 instanceof Player) {
            player2Name = ((Player) player2).name;
        } else {
            player2Name = "Datamaskin";
        }

        translator = new TranslateToCellPos();
        pieceIsMoving = false;
        animationIndex = 0;
        animationPath = new ArrayList<Position>();

        fontText = new BitmapFont();
        fontText.setColor(Color.BLACK);
        fontCounter = new BitmapFont();
        fontCounter.setColor(Color.WHITE);

        potentialTex = new Texture("Graphics/ChessPieces/Potential.png");
        hintTex = new Texture("Graphics/ChessPieces/Hint.png");
        captureTex = new Texture("Graphics/ChessPieces/Capture.png");
        activegame = true;

        //exit game early button
        resignBtn = new TextButton("Avslutt", skin);
        resignBtn.setSize(resignBtn.getWidth(), 60);
        resignBtn.setPosition(Gdx.graphics.getWidth()-resignBtn.getWidth()-15, resignBtn.getY()+7);
        stage.addActor(resignBtn);

        //help button
        helpBtn = new TextButton("Tips", skin);
        helpBtn.setSize(helpBtn.getWidth(),60);
        helpBtn.setPosition(Gdx.graphics.getWidth()-resignBtn.getWidth()-helpBtn.getWidth()+10,(resignBtn.getY()));
        stage.addActor(helpBtn);

        //victory message
        victoryTex = new Texture("Graphics/Menu/victory.png");
        victoryLabel = new Label("", skin);
        victoryLabel.setPosition(bgBoard.getWidth()/2-150, Gdx.graphics.getHeight()/2-victoryLabel.getHeight());
        BitmapFont buttonFont = new BitmapFont( Gdx.files.internal("Skin/raw/font-button-export.fnt"), Gdx.files.internal("Skin/raw/font-button-export.png"), false );
        Label.LabelStyle labelStyle = new Label.LabelStyle(buttonFont, Color.WHITE);
        victoryLabel.setStyle(labelStyle);
        stage.addActor(victoryLabel);

        //animate confetti
        frameCounter = 0f;
        confettiX = 50;
        confettiY = 50;
        confettiSheet = new Texture(Gdx.files.internal("Graphics/Menu/Animations/confetti.png"));
        // Initialize the Animation with the frame interval and array of frames
        confettiAnimation = createAnimation(confettiSheet, 5, 5, 0.025f);
        // finalConfettiImg is used to determine when the animation is complete
        finalConfettiImg = confettiAnimation.getKeyFrames()[confettiAnimation.getKeyFrames().length-1];


        for (int y = 40, yi = 0; y < 560; y += 65, yi++) {
            for (int x = 40, xi = 0; x < 560; x += 65, xi++) {
                AbstractChessPiece piece = board.getPieceAt(new Position(xi, yi));

                Position pos = new Position(xi, yi);
                positions.add(pos);

            }
        }
    }


    @Override
    public void update(float dt) {
        handleInput();
        board = game.getBoard();
        potentialMoves = game.getValidMoves();
        captureMoves = game.getCaptureMoves();
        text = game.getText();
        removedPieces = game.getRemovedPieces();
    }

    @Override
    public void render(SpriteBatch batch) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        frameCounter += Gdx.graphics.getDeltaTime();

        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(bgBoard, 0, 0);

        if(game.getText().contains("Uff"))
            text = game.getText();

        if (removedPieces[5] == 1) {
            text = "Du vant " + player1Name + ", gratulerer!";
            activegame = false;
        } else if (removedPieces[11] == 1) {
            text = "Du vant " + player1Name + ", du må nok øve mer..."; //TODO wrong output
            activegame = false;
        }

        fontText.draw(batch, text, 645, 334);

        //Helge, look at this for-loop! It's so pretty <3
        for(int i=0, j=668; i<6; i++, j+= 71) {
            if(i>=4){
                j+=7;
            }
            fontCounter.draw(batch, "" + removedPieces[i], j, 418);
            fontCounter.draw(batch, "" + removedPieces[6 + i], j, 105);
        }
        // Player names
        fontCounter.draw(batch, "" + player1Name, 726, 241);
        fontCounter.draw(batch, "Score: " + player1.rating , 726, 221);

        fontCounter.draw(batch, "" + player2Name, 723, 555);
        if(player2 instanceof Player) {
            fontCounter.draw(batch, "Score: " + player2.rating, 723, 535);
        }

        //iterate through cells
        for (int i = 0; i < positions.size(); i++) {
            Position piecePos = positions.get(i);
            int[] pos = translator.toPixels(piecePos.getX(), piecePos.getY());
            AbstractChessPiece piece = board.getPieceAt(piecePos);

            if (piece != null && piece.isMoving()) { //should this piece change its location?
                if (game.isAi() && game.getTurn()) { //ai piece
                    if (prevAImove == null) prevAImove = game.getAiMove();
                    animatePiece(piece, piecePos, pos, true);
                } else
                    animatePiece(piece, piecePos, pos, false);
            }

            if (piece != null) {
                Texture pieceTex = new Texture(piece.getImage());
                pieceTexures.add(pieceTex);
                batch.draw(pieceTex, pos[0], pos[1]);
            }
        }
        if (!potentialMoves.isEmpty()) {
            for (Position potPos : potentialMoves) {
                int[] pos = translator.toPixels(potPos.getX(), potPos.getY());
                batch.draw(potentialTex, pos[0], pos[1]);
            }
        }
        if (!captureMoves.isEmpty()) {
            for (Position capPos : captureMoves) {
                int[] pos = translator.toPixels(capPos.getX(), capPos.getY());
                batch.draw(captureTex, pos[0], pos[1]);
            }
        }

        if (helpingMove!=null){
            Position fromPos = helpingMove.getFromPos();
            Position toPos = helpingMove.getToPos();
            int[] drawFrom = translator.toPixels(fromPos.getX(),fromPos.getY());
            int[] drawTo = translator.toPixels(toPos.getX(),toPos.getY());
            batch.draw(hintTex,drawFrom[0],drawFrom[1]);
            batch.draw(hintTex,drawTo[0],drawTo[1]);

        }

        if (!activegame) { // spawn that beautiful confetti <3
            endGameAction(batch);
        }

        batch.end();
        if (!pieceTexures.isEmpty()) {
            for (Texture oldTexture : pieceTexures) {
                if (oldTexture.isManaged()) {
                    oldTexture.dispose();
                }
            }
        }

        stage.draw();
    }

    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY());
        Boolean notSelected = game.pieceHasNotBeenSelected();
        if (resignBtn.isPressed() && activegame) {
            game.endGame(Result.DRAW, Result.DRAW,playerData);
            gsm.set(new ShowStatsState(gsm, player1, playerData));
        }
        if(helpBtn.isPressed() && activegame){
            helpingMove = game.getHelpingMove();
            System.out.println(helpingMove);
        }

        if (activegame && !pieceIsMoving ) {
            if ((x > 40 && x < 560 && y > 40 && y < 560) ) {
                //AI
                if (!game.getTurn() && game.isAi()) {
                    prevAImove = game.aiMove();
                    return;
                }

                //first selected piece
                Position selected = null;
                if (Gdx.input.justTouched() && notSelected) {
                    game.playSound("selectPiece.wav");
                    selected = translator.toCellPos(x, y);
                    game.selectFirstPiece(selected);
                }
                //second selected piece
                else if (Gdx.input.justTouched() && !game.pieceHasNotBeenSelected()) {
                    Position potentialPos = translator.toCellPos(x, y);
                    activegame = game.moveFirstSelectedPieceTo(potentialPos);
                    prevMove = potentialPos;
                    helpingMove = null;
                }
                else if (Gdx.input.justTouched() && !game.selectedFromPocket.isEmpty()){

                }
            }
            else if (Gdx.input.justTouched() && playingCH){ //From pocket
                String pieceFromPocket = getPieceFromPocket(x, y, game.getTurn());
                if (!pieceFromPocket.isEmpty()) {
                    int index = game.getIndexOfPiece(pieceFromPocket);
                    if (!pieceFromPocket.isEmpty() && index != -1 && removedPieces[index] > 0) {
                        game.playSound("selectPiece.wav");
                        game.selectedFromPocket = pieceFromPocket;
                        game.validMoves = board.findEmptySquares(pieceFromPocket);
                    }
                }
                else { game.reset(); }
            }
        } else if (!activegame) { // TODO: Actual result
            Result result1 = Result.DRAW;
            Result result2 = Result.DRAW;

            game.endGame(result1, result2,playerData);
            gsm.set(new ShowStatsState(gsm,player1,playerData));
        }
    }

    private void endGameAction(SpriteBatch batch) {
        Result result1;
        Result result2;
        if(game.getTurn()) {
            result1 = Result.LOSS;
            result2 = Result.WIN;
        } else {
            result1 = Result.WIN;
            result2 = Result.LOSS;
        }
        String victoryMsg = "";
        if (result1 == Result.WIN) { //TODO NEEDS TESTING WITH AI
            victoryMsg = "Gratulerer\n" + appendSpaces(game.getPlayer1().name) + "\n  du vant!";
        } else if (result1 == Result.LOSS && game.isAi() && !game.getAiPlayer().isWhite()) {
            victoryMsg = "    Oida\n"+ appendSpaces(game.getPlayer1().name) + "\n  AI vant!";
        } else if (result1 == Result.WIN && game.isAi() && game.getAiPlayer().isWhite()) {
            victoryMsg = "    Oida\n"+ appendSpaces(game.getPlayer2().name) + "\n  AI vant!";
        } else if(result1 == Result.LOSS) {
            victoryMsg = "Gratulerer\n" + appendSpaces(game.getPlayer2().name) + "\n  du vant!";
        } else if(result1 == Result.DRAW) {
            victoryMsg = "Uavgjort!";
        }
        batch.draw(victoryTex, bgBoard.getWidth() / 2 - victoryTex.getWidth() / 2, Gdx.graphics.getHeight() / 2 - victoryTex.getHeight() / 2);
        victoryLabel.setText(victoryMsg);

        if (Gdx.input.justTouched() && isOkToSwitchState) {
            game.endGame(result1, result2, playerData);
            gsm.set(new ShowStatsState(gsm, game.getPlayer1(), playerData));
        }

        if (!game.isAi() || result1 == Result.WIN && !game.getAiPlayer().isWhite()
                || result1 == Result.LOSS && game.getAiPlayer().isWhite()) { // won against player? spawn confetti
            TextureRegion currentFrame = confettiAnimation.getKeyFrame(frameCounter, true);
            batch.draw(currentFrame, confettiX, confettiY);
            if (currentFrame.equals(finalConfettiImg)) {
                isOkToSwitchState = true;
                Random r = new Random();
                confettiX = r.nextInt(Gdx.graphics.getWidth() - confettiSheet.getWidth() / 5);
                confettiY = r.nextInt(Gdx.graphics.getHeight() - confettiSheet.getHeight() / 5);
            }
        }
    }

    private String appendSpaces(String name) {
        if (name.length() > 9) return name;
        String s = "";
        for (int i = 0; i < 8-name.length(); i++) {
            s += " ";
        }
        return s+name;
    }

    private void animatePiece(AbstractChessPiece piece, Position piecePos, int[] pos, boolean ai) {
        if (pieceIsMoving && piece.isMoving()) {
            if (animationIndex == animationPath.size() && animationPath.size() > 0) { //reached end of list
                piece.stopMoving();
                pieceIsMoving = false;
                //THIS IS WHERE THE ACTUAL MOVING HAPPENS

                if (ai) {
                    if(piece instanceof King && ((King) piece).getCastlingMoves(board, piecePos).contains(prevAImove.getToPos())) {
                        board.movePiece(piecePos, prevAImove.getToPos());
                        Position[] castingPos = game.handlingCasting(piece);
                        AbstractChessPiece p = board.getPieceAt(castingPos[0]);
                        prevMove = castingPos[1];
                        p.startMoving();
                    } else {
                        board.movePiece(piecePos, prevAImove.getToPos());
                    }
                } else {
                    if(piece instanceof King && ((King) piece).getCastlingMoves(board, piecePos).contains(prevMove)) {
                        board.movePiece(piecePos, prevMove);
                        Position[] castingPos = game.handlingCasting(piece);
                        AbstractChessPiece p = board.getPieceAt(castingPos[0]);
                        prevMove = castingPos[1];
                        p.startMoving();
                    } else {
                        board.movePiece(piecePos, prevMove);
                    }

                }
                pos[0] = animationPath.get(animationIndex-1).getX();
                pos[1] = animationPath.get(animationIndex-1).getY();
                animationPath.clear();
                return;
            }
            pos[0] = animationPath.get(animationIndex).getX();
            pos[1] = animationPath.get(animationIndex).getY();
            if (animationPath.size() > animationIndex + 10) animationIndex += 10; //faster animation
            else animationIndex++;
        } else if (!pieceIsMoving && piece.isMoving()) {
            if (ai)
                generateAnimationPath(prevAImove.getFromPos(), prevAImove.getToPos());
            else
                generateAnimationPath(piecePos, prevMove);
            pieceIsMoving = true;
            animationIndex = 0;
            game.playSound("movePiece.wav");
        }
    }

    private void generateAnimationPath(Position startPos, Position endPos) {
        TranslateToCellPos translator = new TranslateToCellPos();
        int[] startPixelPos = translator.toPixels(startPos.getX(), startPos.getY());
        int[] endPixelPos = translator.toPixels(endPos.getX(), endPos.getY());

        while (startPixelPos[0] != endPixelPos[0] || startPixelPos[1] != endPixelPos[1]) {
            if (shorterDistTo(true, startPixelPos, 1, endPixelPos) == 1) {
                startPixelPos[0]++;
            } else if (shorterDistTo(true, startPixelPos, 1, endPixelPos) != 0) {
                startPixelPos[0]--;
            }
            if (shorterDistTo(false, startPixelPos, 1, endPixelPos) == 1) {
                startPixelPos[1]++;
            } else if (shorterDistTo(false, startPixelPos, 1, endPixelPos) != 0) {
                startPixelPos[1]--;
            }
            animationPath.add(new Position(startPixelPos[0], startPixelPos[1]));
        }
    }
    //is it shorter distance? 1=yes -1=no 0=same

    private int shorterDistTo(boolean changeInXaxis, int[] startPixelPo, int variance, int[] endPixelPo) {
        double startVal = Math.sqrt((Math.pow(Math.abs(startPixelPo[0] - endPixelPo[0]), 2)) + (Math.pow(startPixelPo[1] - endPixelPo[1], 2)));
        double nextVal;
        if (changeInXaxis) {
            if (startPixelPo[0] == endPixelPo[0]) {
                return 0;
            }
            nextVal = Math.sqrt((Math.pow(Math.abs(startPixelPo[0] + variance - endPixelPo[0]), 2)) + (Math.pow(startPixelPo[1] - endPixelPo[1], 2)));
        } else {
            if (startPixelPo[1] == endPixelPo[1]) {
                return 0;
            }
            nextVal = Math.sqrt((Math.pow(Math.abs(startPixelPo[0] - endPixelPo[0]), 2)) + (Math.pow(startPixelPo[1] + variance - endPixelPo[1], 2)));
        }
        if (nextVal < startVal)
            return 1;
        else if (nextVal > startVal)
            return -1;
        return 0;
    }



    public String getPieceFromPocket(int x, int y, boolean turn){
        String piece ="";
        if (y<489 && y>415){
            if (turn) return piece;
            if (x>660 && x<695)
                piece = "P";
            if (x>722 && x<765)
                piece = "B";
            if (x>786 && x<837)
                piece = "H";
            if (x>869 && x<904)
                piece = "R";
            if (x>943 && x<982)
                piece = "Q";
            if (x>1016 && x<1060)
                piece = "K";
        }
        else if (y>100 && y<175){
            if (!turn) return piece;
            if (x>660 && x<695)
                piece = "p";
            if (x>722 && x<765)
                piece = "b";
            if (x>786 && x<837)
                piece = "h";
            if (x>869 && x<904)
                piece = "r";
            if (x>943 && x<982)
                piece = "q";
            if (x>1016 && x<1060)
                piece = "k";
        }

        return piece;
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
    public void dispose() {
        bg.dispose();
        bgBoard.dispose();
        for (Texture tex : pieceTexures) {
            tex.dispose();
        }
        confettiSheet.dispose();
        potentialTex.dispose();
        captureTex.dispose();
        stage.dispose();
        System.out.println("PlayState Disposed");
    }
}