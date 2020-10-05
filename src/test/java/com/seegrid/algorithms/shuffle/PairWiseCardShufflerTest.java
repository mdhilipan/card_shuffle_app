package com.seegrid.algorithms.shuffle;

import com.seegrid.deckofcards.Deck.DeckOfCards;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairWiseCardShufflerTest {

   private DeckOfCards deckOfCards;
   private IDeckShuffler iDeckShuffler;
    @BeforeEach
    void setUp() {
        //sets up test input
        //create deck of cards and shuffler instance
        deckOfCards = new DeckOfCards().createDeck();
        iDeckShuffler = new PairWiseCardShuffler();
    }

    /*
    Test method to test shuffle produces different permutations when invoked.
     */
    @Test
    void shuffle() {
        String cardBeforeShuffle = deckOfCards.getCurrentCardValue();
        iDeckShuffler.shuffle(deckOfCards);
        String cardAfterShuffle = deckOfCards.getCurrentCardValue();
        assertNotEquals(cardBeforeShuffle,cardAfterShuffle);
    }

    @AfterEach
    void tearDown() {
        //clear the reference
        deckOfCards = null;
    }
}