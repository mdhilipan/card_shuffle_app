package com.seegrid.deckofcards.cards;

/* Supported card types per suit*/
public enum SupportedCards {
    ACE,
    JACK,
    KING,
    QUEEN,
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10);

    public int getValue() {
        return value;
    }

    private int value;

    SupportedCards(int value) {
        this.value = value;
    }

    SupportedCards() {

    }

}
