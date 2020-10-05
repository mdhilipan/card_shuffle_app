package com.seegrid.algorithms.shuffle;

import com.seegrid.deckofcards.Deck.DeckOfCards;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pairWiseCardShuffler")
public class PairWiseCardShuffler implements IDeckShuffler{
    private static final int NCARDS = 52;

    /* ------------------------------------------------
       shuffle(n): shuffle the deck using 5 exchanges
       ------------------------------------------------ */
    public void shuffle(DeckOfCards deck)
    {
        List<String> cards = deck.getDeck();
        int first, second, swaps;
        //performs 5 swaps by taking two random cards
        //and swap them.
        for ( swaps = 0; swaps < 5; swaps++ )
        {
            first = (int) ( NCARDS * Math.random() );  // Pick 2 random cards
            second = (int) ( NCARDS * Math.random() );  // in the deck

          /* ---------------------------------
             Swap these randomly picked cards
             --------------------------------- */
            String tmp = cards.get(first);
            cards.set(first,cards.get(second));
            cards.set(second,tmp);
        }

        deck.setCurrentCard(0);   // Reset current card
        deck.setCurrentCardValue(cards.get(0));//Reset current card value
    }
}
