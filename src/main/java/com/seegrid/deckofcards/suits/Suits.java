package com.seegrid.deckofcards.suits;

import com.seegrid.deckofcards.cards.SupportedCards;

import java.util.ArrayList;
import java.util.List;
/*
Utility method to form suit of cards
 */
public class Suits {
    public  List<String> addSuitOfCards() {
        List<String> SuitOfCards = new ArrayList<String>();
        //Get the enum of supported suits
        SupportedSuits[] supportedSuits = SupportedSuits.values();
        //Get the enum of supported cards
        SupportedCards[] cardValues = SupportedCards.values();
        //Iterate each suit type and add cards
        for(SupportedSuits suit:supportedSuits) {
           for(SupportedCards card:cardValues) {
               //cards will be created like Diamond5,ClubJACK,
               SuitOfCards.add(suit.name()+ (card.getValue() == 0 ? card.name():card.getValue()));
           }
        }
        //returning formed suits
        return SuitOfCards;
    }

}
