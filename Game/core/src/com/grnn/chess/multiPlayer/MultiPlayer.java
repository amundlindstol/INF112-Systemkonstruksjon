package com.grnn.chess.multiPlayer;

import java.util.ArrayList;

public class MultiPlayer {

    /**
     * Constructur of multiplayer, Should either connect to a Peer of create a new peer
     */
    public MultiPlayer(boolean peerOne){{
        if(peerOne){
            createPeer();
        }else{
            createPeer();
        }
    }

    }

    /**
     * Return a list of open peers
     * @return
     */
    public ArrayList<String> getGames() {

    }

    /**
     * Create a new Peer
     * @return
     */
    public boolean createPeer(){

    }

    /**
     * Connect to peer
     */
    public boolean connectToPeer(int nr){
        String Ip = getPeerIP(nr);

    }

    /**
     *
     */
    public String getPeerIP(int nr){
        //sql
        return Ip;
    }
}
