package swen221.cards.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Card implements Comparable<Card>, Serializable {
	
	/**
	 * Represents a card suit.
	 * 
	 * @author David J. Pearce
	 *
	 */
	public enum Suit {
		HEARTS,
		CLUBS,
		DIAMONDS,
		SPADES;
	}
	
	/**
	 * Represents the different card "numbers".
	 * 
	 * @author David J. Pearce
	 *
	 */
	public enum Rank {
		TWO,
		THREE,
		FOUR,
		FIVE,
		SIX,
		SEVEN,
		EIGHT,
		NINE,
		TEN,
		JACK,
		QUEEN,
		KING,
		ACE;
	}
	
	// =======================================================
	// Card stuff
	// =======================================================
	
	private Suit suit; // HEARTS, CLUBS, DIAMONDS, SPADES
	private Rank rank; // 2 <= number <= 14 (ACE)
	
	/**
	 * Construct a card in the given suit, with a given number
	 * 
	 * @param suit
	 *            --- between 0 (HEARTS) and 3 (SPADES)
	 * @param number
	 *            --- between 2 and 14 (ACE)
	 */
	public Card(Suit suit, Rank number) {				
		this.suit = suit;
		this.rank = number;
	}

	/**
	 * Get the suit of this card, between 0 (HEARTS) and 3 (SPADES).
	 * 
	 * @return
	 */
	public Suit suit() {
		return suit;
	}

	/**
	 * Get the number of this card, between 2 and 14 (ACE).
	 * 
	 * @return
	 */
	public Rank rank() {
		return rank;
	}	
		
	private static String[] suits = { "Hearts","Clubs","Diamonds","Spades"};
	private static String[] ranks = { "2 of ", "3 of ", "4 of ",
			"5 of ", "6 of ", "7 of ", "8 of ", "9 of ", "10 of ", "Jack of ",
			"Queen of ", "King of ", "Ace of " };
	
	public String toString() {
		return ranks[rank.ordinal()] + suits[suit.ordinal()];		
	}

	@Override
	public int compareTo(Card o) {
		ArrayList<Suit> tempSuits = new ArrayList<Suit>(Arrays.asList(Suit.values()));
		ArrayList<Rank> tempRank = new ArrayList<Rank>(Arrays.asList(Rank.values()));
		if(tempSuits.indexOf(this.suit)>tempSuits.indexOf(o.suit)){
			return 1;
		} else if(tempSuits.indexOf(this.suit)<tempSuits.indexOf(o.suit)){
			return -1;
		} else if(tempSuits.indexOf(this.suit)==tempSuits.indexOf(o.suit)){
			if(tempRank.indexOf(this.rank)>tempRank.indexOf(o.rank)){
				return 1;
			} else if(tempRank.indexOf(this.rank)<tempRank.indexOf(o.rank)){
				return -1;
			} else{
				return 0;
			}
		} else{
			return 0;
		}
	}

	/**
	 * Returns true if the cards are equal
	 * @param o the card to check for equality
	 * @return returns true if equal or false if not
	 */
	public boolean equals(Card o){
		if(o.suit==suit&&o.rank==rank){
			return true;
		} else{
			return false;
		}
	}



	@Override
	public int hashCode() {
		return Objects.hash(suit, rank);
	}
}
