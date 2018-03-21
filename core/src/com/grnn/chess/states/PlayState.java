package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.grnn.chess.AI.AI;
import com.grnn.chess.Board;
import com.grnn.chess.Position;
import com.grnn.chess.TranslateToCellPos;
import com.grnn.chess.objects.AbstractChessPiece;
import com.grnn.chess.objects.King;
import javafx.geometry.Pos;

import java.util.ArrayList;

/**
 * @author Amund 15.03.18
 */
public class PlayState extends State {
    Board board;
    Texture bg;
    Texture bgBoard;
    private Position selected;
    private ArrayList<Position> potentialMoves;
    private ArrayList<Position> captureMoves;
    private ArrayList<Position> castlingMoves;
    private TranslateToCellPos translator;
    private Boolean turn;
    private Boolean aiPlayer;
    private AI ai;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("GUI2.png");
        bgBoard = new Texture("sjakk2.png");
        board = new Board();
        board.addPieces();
        selected = null;
        potentialMoves = new ArrayList<Position>();
        captureMoves = new ArrayList<Position>();
        castlingMoves = new ArrayList<Position>();
        translator = new TranslateToCellPos();
        turn = true;
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(bgBoard, 0, 0);
        for (int y = 40, yi = 0; y < 560; y += 65, yi++) {
            for (int x = 40, xi = 0; x < 560; x += 65, xi++) {
                AbstractChessPiece piece = board.getPieceAt(new Position(xi, yi));
                if (piece != null) {
                    String img = piece.getImage();
                    batch.draw(new Texture(img), x, y);
                }
            }
        }
        if (!potentialMoves.isEmpty()) {
            for (Position potPos : potentialMoves) {
                int[] pos = translator.toPixels(potPos.getX(), potPos.getY());
                batch.draw(new Texture("ChessPieces/Potential.png"), pos[0], pos[1]);
            }
        }
        if (!captureMoves.isEmpty()) {
            for (Position capPos : captureMoves) {
                int[] pos = translator.toPixels(capPos.getX(), capPos.getY());
                batch.draw(new Texture("ChessPieces/Capture.png"), pos[0], pos[1]);
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bgBoard.dispose();

        System.out.println("PlayState Disposed");
    }


    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY());

        //first selected piece
        if (Gdx.input.justTouched() && selected == null) {
            selected = translator.toCellPos(x, y);
            AbstractChessPiece selectedPiece = board.getPieceAt(selected);
            if (selectedPiece != null && selectedPiece.isWhite() == turn) {
                potentialMoves = selectedPiece.getValidMoves(board);
                captureMoves = selectedPiece.getCaptureMoves(board);
                if (selectedPiece instanceof King) {
                    castlingMoves = ((King) selectedPiece).getCastlingMoves(board, selected);
                }
            } else {
                selected = null;
            }
        }
        //second selected piece
        else if (Gdx.input.justTouched() && selected != null) {
            Position potentialPos = translator.toCellPos(x, y);
            AbstractChessPiece potentialPiece = board.getPieceAt(potentialPos);
            Boolean valid = potentialMoves.contains(potentialPos) || captureMoves.contains(potentialPos) || castlingMoves.contains(potentialPos);
            if (potentialPiece != null) {
                if (valid) {
                    board.removePiece(potentialPiece);
                    board.movePiece(selected, potentialPos);
                    reset();
                    turn = !turn;
                } else if (potentialPiece.isWhite() == turn) {
                    reset();
                    potentialMoves = potentialPiece.getValidMoves(board);
                    captureMoves = potentialPiece.getCaptureMoves(board);
                    selected = potentialPos;
                } else {
                    reset();
                }
            } else if (potentialPiece == null && valid) {
                handleCastling(potentialPos);
                board.movePiece(selected, potentialPos);
                reset();
                turn = !turn;
            } else {
                reset();
            }

        }


    }

    /**
     * Moves the rook if the king does castling.
     * @param potentialPos The position the king is trying to move to.
     */
    private void handleCastling(Position potentialPos) {
        if(!castlingMoves.contains(potentialPos))
            return;

        AbstractChessPiece potentialPiece = board.getPieceAt(selected);
        Position rookOriginalPos = null;
        Position rookNewPos = null;

        if(potentialPiece.isWhite()) {
            if(potentialPos.equals(new Position(2, 0))) {
                rookOriginalPos = new Position(0,0);
                rookNewPos = new Position(3, 0);
            } else if (potentialPos.equals(new Position(6, 0))) {
                rookOriginalPos = new Position(7,0);
                rookNewPos = new Position(5, 0);
            }
        } else {
            if(potentialPos.equals(new Position(2, 7))) {
                rookOriginalPos = new Position(0,7);
                rookNewPos = new Position(3, 7);
            } else if (potentialPos.equals(new Position(6, 7))) {
                rookOriginalPos = new Position(7,7);
                rookNewPos = new Position(5, 7);
            }
        }
        board.movePiece(rookOriginalPos, rookNewPos);
    }

    public void reset() {
        selected = null;
        potentialMoves = new ArrayList<Position>();
        captureMoves = new ArrayList<Position>();
        castlingMoves = new ArrayList<Position>();
    }
}