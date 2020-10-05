package com.seegrid.deckofcards.Deck;

import com.seegrid.customexception.NoMoreCardToDealException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class DeckOfCardsTest {
    private DeckOfCards testdata;
    @BeforeEach
    void setUp() {
        testdata = new DeckOfCards();
        testdata.createDeck();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_createDeck_create52Cards() {
        DeckOfCards cards = new DeckOfCards();
        //call the create deck method
        //no logic inside create deck method
        //testing its side effect of creating deck of size 52
        cards.createDeck();
        assertEquals(cards.getDeck().size(),52);
    }

    @Test()
    void dealCard_NoMoreCardToDealException() {
        Assertions.assertThrows(NoMoreCardToDealException.class, () -> {
            testdata.setCurrentCard(51);//setting the index to be last card
            testdata.dealCard();
        });
    }

    /*
    Deal card happy path
     */
    @Test()
    void dealCard_NoException_happyPath() throws Exception{
        String card = testdata.dealCard();
        assertNotNull(card);

    }

}