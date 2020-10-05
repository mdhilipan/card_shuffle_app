# card_shuffle_app
App for multi player shuffle and deal game on a deck of cards

This spring boot app exposes three http Rest endpoints.

PUT /deck 

Used to create a deck of cards of 52 cards split into 4 suits of each 13 cards.

Response 200 ok , containing unique identifier for deckid.

User has to send this deckid along in subsequent requests for shuffle,deal cards.

POST /deck/shuffle/{deckid}

Used to shuffle a deck of cards in an algorithm configured by the application.properties.
E.g PairWise

Response 200 ok, "Deck shuffled"

If deckid is not found in the system, Response 400 bad request, "Deck Not Found"

GET /deck/deal/{deckid}

Used to deal a card in deck of cards identified by deckid.
Response 200 ok, along with card value
e.g 200 ok, Clubs7

DELETE /deck/delete/{deckid}

Used to delete a deck.

E.g 

curl -X PUT http://localhost:9000/deck - creates a deck , returns deckid , e.g a123456b5677989i23

   Ex o/p 200 ok,"8031b9c9-8207-400b-ae42-d13f8170f833"

curl -X POST http://localhost:9000/deck/shuffle/a123456b5677989i23 {substitute with deckid you got as a result of PUT in the first request} - shuffles a deck
     Arranges the cards in a different permutations.
     
   Ex o/p : 200 ok, "Deck Shuffled"
   
            400 Bad Request , if user sends an invalid deck id thats not yet created.
            
curl http://localhost:9000/deck/deal/a123456b5677989i23  {substitute with deckid you got as a result of PUT in the first request} - deals a card

   Ex o/p : 200 ok,"DiamondsJACK"
   
            400 Bad Request , if user sends an invalid deck id thats not yet created.
            
            406 Not Acceptible, if user sends deal requests more than number of cards, i.e if user sends more than 52 "deal" requests without shuffling it.
            


