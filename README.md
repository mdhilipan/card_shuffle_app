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

# Request/Response samples

E.g 

curl -X PUT http://localhost:9000/deck - creates a deck , returns deckid , e.g 8031b9c9-8207-400b-ae42-d13f8170f833

            
            
            200 ok,"8031b9c9-8207-400b-ae42-d13f8170f833"
            
            
curl -X POST http://localhost:9000/deck/shuffle/8031b9c9-8207-400b-ae42-d13f8170f833 {substitute with deckid you got as a result of PUT in the first request} - shuffles a deck
     Arranges the cards in a different permutations.
     
   Ex o/p : 
   
            400 Bad Request , if user sends an invalid deck id thats not yet created.
            
            200 ok, "Deck Shuffled"
            
curl http://localhost:9000/deck/deal/8031b9c9-8207-400b-ae42-d13f8170f833  {substitute with deckid you got as a result of PUT in the first request} - deals a card

   Ex o/p : 
   
            400 Bad Request , if user sends an invalid deck id thats not yet created.
            
            406 Not Acceptible, if user sends deal requests more than number of cards, i.e if user sends more than 52 "deal" requests without shuffling it.
            
            200 ok,"DiamondsJACK"
            
# How to Run the application:
The application relies on mongodb for physical storage and hazelcast for speed retreival and update of in memory hashmap.
The in memory hashmap is shared across all instances of the application.

Step 1:MongoDB setup

    //pull the latest image from docker hub

    docker pull mongo

    //Map the host port 27017 to docker container port 

    docker run -p 27017:27017 mongo:latest 

Step 2: Deck application setup

 The deck application has been developed in such a way that it can horizontally scale.
 
 You can start multiple instances of the application and send requests to different servers and any server can process any request.
 
    # Checkout the source code
    
    git checkout https://github.com/mdhilipan/card_shuffle_app.git
    
    cd card_shuffle_app
    
    mvn clean install
    
    cd target
    
    #start instance 1
    
    java -jar -Dserver.port=9000 actuator-service-0.0.1-SNAPSHOT.jar
    
    #start instance 2(different port)
    
    java -jar -Dserver.port=9001 actuator-service-0.0.1-SNAPSHOT.jar

    
    
    




