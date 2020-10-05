package com.seegrid.deckofcards.Deck;

import com.seegrid.customexception.NoMoreCardToDealException;
import com.seegrid.deckofcards.suits.Suits;

import java.io.Serializable;
import java.util.List;

/*
Deck Of Cards logical data structure, it contains self methods
such as creating a deck, shuffling a deck and dealing a card
 */
public class DeckOfCards implements IDeckOfCards, Serializable {

    //List of cards comprising a deck, 52 in total without jokers
    private List<String> deck;
    //UUID identifying a given deck.
    private String deckId;
    //Current card that is dealt
    private int currentCard;
    private String currentCardValue;

    /* This method creates new deck
    by invoking Suits which is a helper utility
     */
    public DeckOfCards createDeck() {
        this.setDeck(new Suits().addSuitOfCards());
        return this;
    }

    /*
     This method deals a card, takes a current index and increment card value
     and returns it , if there are no cards to deal,
     throws NoMoreCardToDealException.
     */
    public String dealCard() throws Exception{
        //increment the current card
        int cardToTake = this.getCurrentCard()+1;
        //check whether all cards have been dealt
        if(cardToTake > 51) {
            throw new NoMoreCardToDealException("All cards are dealt, shuffle again or create a new deck");
        }
        //update current card
        this.setCurrentCard(cardToTake);
        this.setCurrentCardValue(this.getDeck().get(cardToTake));
        return this.getCurrentCardValue();
    }

    public List<String> getDeck() {
        return deck;
    }

    public void setDeck(List<String> deck) {
        this.deck = deck;
    }

    public int getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(int currentCard) {
        this.currentCard = currentCard;
    }
    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }
    public String getCurrentCardValue() {
        return currentCardValue;
    }

    public void setCurrentCardValue(String currentCardValue) {
        this.currentCardValue = currentCardValue;
    }
}
