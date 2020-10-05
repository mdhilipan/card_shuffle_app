package com.seegrid.algorithms.shuffle;

import com.seegrid.deckofcards.Deck.DeckOfCards;
import org.springframework.stereotype.Component;

@Component
/*
Interface to be implemented by classes which implements specific
algorithms
 */
public interface IDeckShuffler {
    void shuffle(DeckOfCards deckOfCards);
}
