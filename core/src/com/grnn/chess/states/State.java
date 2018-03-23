package com.grnn.chess.states;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Abstract class to handle the different States of the game
 * @author Amund 15.03.18
 */
public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    /**
     * Constructor for State
     * @param gsm, GameStateManager
     */
    public State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    /**
     * Method to handle inputs from the user
     */
    protected abstract void handleInput();

    /**
     *
     * @param dt
     */
    public abstract void update(float dt);


    /**
     * Method to write the content to the screen
     * @param sb
     */
    public abstract void render(SpriteBatch sb);

    /**
     * Method to dispose resources when not in use
     */
    public abstract void dispose();
}