package resources.player;

import resources.Sentient;

/**
 * Player class for the online version of the game.
 */
public class Player extends Sentient {


    public Player(String _name, int hitPoints, int _defense, int _damage, double _toHit, boolean _isHostile){
        super(_name, hitPoints, _defense, _damage, _toHit, _isHostile);
    }
}
