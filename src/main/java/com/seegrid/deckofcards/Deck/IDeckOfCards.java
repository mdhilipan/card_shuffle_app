package com.seegrid.deckofcards.Deck;

public interface IDeckOfCards {
    DeckOfCards createDeck();
    String dealCard() throws Exception;
}
