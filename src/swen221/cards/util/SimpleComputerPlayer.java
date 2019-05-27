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
 */
public class SimpleComputerPlayer extends AbstractComputerPlayer {

    public SimpleComputerPlayer(Player player) {
        super(player);
    }

    @Override
    public Card getNextCard(Trick trick) {
        // TODO: you need to implement this!
        Card toUse = null;
        int attemptState = 1;
        while (toUse == null) {
            switch (attemptState) {
                //Case 1: player is the final player
                case 1:
                    if (trick.getCardsPlayed().size() == 3) {
                        try {
                            toUse = getMinWiningCard(trick.getCardPlayed(trick.getLeadPlayer()));
                        } catch (swen221.cards.util.noValidCard noValidCard) {
                            attemptState = 3;
                            break;
                        } finally {
                            if(toUse!=null){
                                break;
                            }
                        }
                    } else {
                        attemptState++;
                        break;
                    }
                    //Case 2: check if player is the first
                case 2:
                    if(super.player.direction==trick.getLeadPlayer()){
                        toUse = getBestOfHand();
                        break;
                    }
                    //Case 3: player can potentially win
                case 3:
                    try {
                        toUse = getBestOfSuit(trick.getCardPlayed(trick.getLeadPlayer()).suit());
                    } catch (NoSuchSuitFound noSuchSuitFound) {
                        // No cards matching the lead suit found, attempts to find  trump
                        if (trick.getTrumps() != null) {
                            try {
                                toUse = getBestOfSuit(trick.getTrumps());
                            } catch (NoSuchSuitFound noSuchSuitFound1) {
                                // No card with suit matching trump found, attempt next case
                                attemptState++;
                                toUse = null;
                                break;
                            }
                        }
                    } finally {
                        if (toUse!=null&&toUse.compareTo(trick.getLeadingCard()) < 1) {
                             toUse = null;
                            attemptState++;
                        } else{
                            break;
                        }
                    }
                    //Case 4: player cant win
                case 4:
                    try {
                        toUse = getWorstOfSuit(trick.getCardPlayed(trick.getLeadPlayer()).suit());
                    } catch (NoSuchSuitFound noSuchSuitFound) {
                        toUse = getWorseOfHand();
                    }
            }
        }
        return toUse;
    }

    /**
     * Returns the best card in the player's hand
     * @return returns the best card of the hand
     */
    private Card getBestOfHand() {
        Card toReturn = null;
        for (Card C : super.player.hand) {
            if (toReturn == null) {
                toReturn = C;
            } else {
                if (C.compareTo(toReturn) > 0) {
                    toReturn = C;
                }
            }
        }
        return toReturn;
    }

    /**
     * Gets the minimum card to put the player into the lead
     * @param cardPlayed the card to beat
     * @return returns a card that beats the supplied card
     * @throws noValidCard thrown if there is no card that can be used
     */
    private Card getMinWiningCard(Card cardPlayed) throws noValidCard {
        Card toReturn = null;
        for (Card C : super.player.hand) {
            if (toReturn==null && C.compareTo(cardPlayed) > 0) {
                toReturn = C;
            } else if (toReturn!=null && C.compareTo(cardPlayed) > 0 && C.compareTo(toReturn)<0){
                toReturn = C;
            }
        }
        if(toReturn==null){
            throw new noValidCard("No winning card found");
        }
        return toReturn;
    }

    /**
     * Returns the worst card of the hand
     * @return the worse card
     */
    private Card getWorseOfHand() {
        Card toReturn = null;
        for (Card C : super.player.hand) {
            if (toReturn == null) {
                toReturn = C;
            } else {
                if (C.compareTo(toReturn) < 0) {
                    toReturn = C;
                }
            }
        }
        return toReturn;
    }

    /**
     * Gets the worse card in the players hand of the supplied
     * @param suitType the suit type to check
     * @return returns the bad card
     * @throws NoSuchSuitFound thrown if there is no cards in the matching
     */
    private Card getWorstOfSuit(Card.Suit suitType) throws NoSuchSuitFound {
        ArrayList<Card> cardsOfRequestedSuit = getAllCardsOfSuitType(suitType);
        Card toReturn = null;
        for (Card C : cardsOfRequestedSuit) {
            if (toReturn == null) {
                toReturn = C;
            } else {
                if (C.compareTo(toReturn) < 0) {
                    toReturn = C;
                }
            }
        }
        return toReturn;
    }

    /**
     * Gets the best card in the players hand of the supplied
     * @param suitType the suit type to check
     * @return returns the card
     * @throws NoSuchSuitFound thrown if there are no cards of the requested type
     */
    private Card getBestOfSuit(Card.Suit suitType) throws NoSuchSuitFound {
        ArrayList<Card> cardsOfRequestedSuit = getAllCardsOfSuitType(suitType);
        Card toReturn = null;
        for (Card C : cardsOfRequestedSuit) {
            if (toReturn == null) {
                toReturn = C;
            } else {
                if (C.compareTo(toReturn) > 0) {
                    toReturn = C;
                }
            }
        }
        return toReturn;
    }

    /**
     * Gets all cards of the requested suit
     * @param suit the suit to check
     * @return returns the arraylist of cards
     * @throws NoSuchSuitFound thrown if there are no cards of the matching suit
     */
    private ArrayList<Card> getAllCardsOfSuitType(Card.Suit suit) throws NoSuchSuitFound {
        ArrayList<Card> toReturn = new ArrayList<>();
        for (Card C : super.player.hand) {
            if (C.suit() == suit) {
                toReturn.add(C);
            }
        }
        if (toReturn.isEmpty()) {
            throw new NoSuchSuitFound("No Cards Of Suit " + suit + " Found\n");
        }
        return toReturn;
    }
}
