package swen221.cards.util;

import swen221.cards.core.Card;
import swen221.cards.core.Player;
import swen221.cards.core.Trick;

import java.util.ArrayList;

/**
 * Implements a simple computer player who plays the highest card available when
 * the trick can still be won, otherwise discards the lowest card available. In
 * the special case that the player must win the trick (i.e. this is the last
 * card in the trick), then the player conservatively plays the least card
 * needed to win.
 * 
 * @author David J. Pearce
 * 
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer {

	public SimpleComputerPlayer(Player player) {
		super(player);
	}

	@Override
	public Card getNextCard(Trick trick) {		
		// TODO: you need to implement this!
		//Case 1: player can potentially win
		Card toUse = null;
		int attemptState = 1;
		while(toUse!=null) {
			switch (attemptState) {
				case 1:
					try {
						toUse = getBestOfSuit(trick.getCardPlayed(trick.getLeadPlayer()).suit());
						if(toUse.compareTo(trick.getLeadingCard())>0){
							return toUse;
						}
					} catch (NoSuchSuitFound noSuchSuitFound) {
						// No cards matching the lead suit found, attempts to find  trump
						if (trick.getTrumps() != null) {
							try {
								toUse = getBestOfSuit(trick.getTrumps());
								if(toUse.compareTo(trick.getLeadingCard())>0){
									return toUse;
								}
							} catch (NoSuchSuitFound noSuchSuitFound1) {
								// No card with suit matching trump found, attempt next case
								attemptState++;
								toUse = null;
								break;
							}
						}
					}
				case 2:
					
			}
		}
	}

	public Card getBestOfSuit(Card.Suit suitType) throws NoSuchSuitFound{
		ArrayList<Card> cardsOfRequestedSuit = new ArrayList<Card>();
		Card toReturn = null;
		for(Card C : super.player.hand){
			if(C.suit() == suitType){
				cardsOfRequestedSuit.add(C);
			}
		}
		if(cardsOfRequestedSuit.isEmpty()){
			throw new NoSuchSuitFound("No Cards Of Suit "+suitType+" Found\n");
		}
		for(Card C : cardsOfRequestedSuit){
			if(toReturn == null){
				toReturn = C;
			} else{
				if(C.compareTo(toReturn) > 0){
					toReturn = C;
				}
			}
		}
		return toReturn;
	}
}
