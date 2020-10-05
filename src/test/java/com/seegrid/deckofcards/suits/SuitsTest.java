package com.seegrid.deckofcards.suits;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SuitsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addSuitOfCards_test() {
        Suits classUnderTest = new Suits();

        List<String> clubs = new ArrayList<>();
        List<String> diamonds = new ArrayList<>();
        List<String> hearts = new ArrayList<>();
        List<String> spades = new ArrayList<>();
        //make sure the method generated 52 cards
        List<String> output = classUnderTest.addSuitOfCards();
        assertEquals(52,output.size());
        //make sure the method generated 13 cards of each type
        for(String card:output) {
            if(card.startsWith(SupportedSuits.Clubs.name())) {
                clubs.add(card);
            }
            else if(card.startsWith(SupportedSuits.Diamonds.name())) {
                diamonds.add(card);
            }
            else if(card.startsWith(SupportedSuits.Hearts.name())) {
                hearts.add(card);
            }
            else {
                spades.add(card);
            }
        }
        assertEquals(13,clubs.size());
        assertEquals(13,diamonds.size());
        assertEquals(13,hearts.size());
        assertEquals(13,spades.size());

    }
}