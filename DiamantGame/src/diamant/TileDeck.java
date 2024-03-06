package diamant;

import java.util.Random;

import static diamant.TrapTile.*;

public class TileDeck {

    private MineTile[] deck;
    private final int INIT_DECK_SIZE = 35;
    private int currDeckSize;
    private int currReveal;

    /** Initialize the TileDeck object.  Creates original deck of INIT_DECK_SIZE tiles. */
    public TileDeck()
    {
        //assigning the number of tiles (35) to be the size of the deck array
        deck = new MineTile[INIT_DECK_SIZE];
        currDeckSize = INIT_DECK_SIZE;

        initializeDeck(); //calling a method to initialize the Deck
    }

    /** Retrieve a tile located at a particular point in the deck given by an index originating from 0
     *
     * @param tileInd the Index of a tile
     * @return the MineTile object or its null pointer located at the index.
     */
    public MineTile getTileAt(int tileInd)
    {
        //get a Tile by its Index
        return deck[tileInd];
    }

    /** Initialize the deck with the standard 20 treasure cards and 15 trap cards.
     */
    private void initializeDeck()
    {
        //initialize the Deck
        for(int i = 0; i < INIT_DECK_SIZE; i++)
        {
            if(i < 20)
            {
                deck[i] = new MineTile(i+2);
            }
            else if(i < 23)
            {
                deck[i] = new TrapTile(LAVA);
            }
            else if(i < 26)
            {
                deck[i] = new TrapTile(POISON);
            }
            else if(i < 29)
            {
                deck[i] = new TrapTile(SPIKES);
            }
            else if(i < 32)
            {
                deck[i] = new TrapTile(SNAKES);
            }
            else
            {
                deck[i] = new TrapTile(SPIDERS);
            }
        }

        currReveal = -1; // all cards are hidden
    }

    /** Return the current deck size.
     *
     * @return Returns the current deck size, which changes throughout a game, and thus is distinct from the initial deck size.
     */
    public int getCurrentDeckSize()
    {
        //return the current Deck Size
        return currDeckSize;
    }

    /** Flip the next tile in the deck to be face up
     *
     * @return Return true if able to flip the next card
     */
    public boolean revealNextTile()
    {
        if(currReveal < currDeckSize - 1)
        {
            deck[currReveal + 1].turnFaceUp();
            currReveal++;
            return true;
        }
        else
        {
            return false;
        }
    }

    /** Shuffle the current tile deck using a standard shuffling algorithm.
     */
    public void shuffle()
    {
        Random random = new Random(); //create a Random object

        for(int i = 0; i < 250; i++) //for 250 times take a random card and switch it with another random card.
        {
            //declare 2 variables and assign a random Index for each of them
            int randomIndex = random.nextInt(deck.length - 1);
            int secondRandomInedx = random.nextInt(deck.length - 1);

            //declare a variable to hold one of the cards to switch the cards places in the array
            MineTile temp = deck[randomIndex];

            //assign the element at index randomInedx to the lement at secondRandomInedx and the other way around
            deck[randomIndex] = deck[secondRandomInedx];
            deck[secondRandomInedx] = temp;
        }
    }

    /** Find and remove the first trap tile of a particular type from the tile deck.  This should resize the data
     * structure such that it does not have any empty slots.  The current deck size should be updated appropriately.
     * @param trap - the type of trap being returned.  In theory should be from the TrapTile class but could be
     *             any string.
     * @return the size of the newly reconfigured deck
     */
    public int removeTrapCard(String trap)
    {
        //if the trap String is one of the 5 trap types
        if(trap.equals(LAVA) || trap.equals(POISON) || trap.equals(SPIDERS) || trap.equals(SNAKES) || trap.equals(SPIKES))
        {
            MineTile[] temp = new MineTile[deck.length - 1]; //new deck size without the removed card

            boolean found = false; //if the trap was found in the deck

            for(int i = 0; i < currDeckSize; i++) //repeat for every element in the array
            {
                if(deck[i].toString().equals(trap) && !found) //if it was found for the first time
                {
                    found = true;
                }
                else if(!found) //before the element is found store each element in the deck in the same index in the new array
                {
                    temp[i] =  deck[i];
                }
                else //after the element is found store every element in the deck in the index same index - 1 in the new array
                {
                    temp[i-1] = deck[i];
                }
            }
            //assign the new deck to deck
            deck = temp;
        }
        //assign the new deck length to the currDecskSize variable and return it
        currDeckSize = deck.length;
        return currDeckSize;
    }

    /** Take all the tiles in the tile deck and set them so they are face down.  Currently revealed cards in the deck is set to
     * -1 to indicate all cards are face down.
     */
    public void flipDeckFaceDown()
    {
        for(int i = currReveal; i >= 0; i--) //starting from the last revealed card flip them all to face down
        {
            deck[i].turnFaceDown();
        }

        currReveal = -1; //-1 indicates all cards are face down
    }

    /** Take all the tiles in the tile deck and set them so they are face down.  Currently revealed cards in the deck is set to
     * one less of the current size of the deck to indicate all cards are face up.
     */
    public void flipDeckFaceUp()
    {
        for(int i = 0; i < currDeckSize; i++)//go through the deck and flip it to face up
        {
            deck[i].turnFaceUp();
        }

        currReveal = currDeckSize - 1; //assign the index of the last revealed card (the last element in the deck)
    }


    /** Counting a trap of a particular type contained within the deck.
     *
     * @param trap type of trap drawn from the enum Trap
     * @return number of traps of that type
     */
    public int countTraps(String trap)
    {
        int count = 0;

        for(int i = 0; i < currDeckSize; i++)//for each element in the deck check if it is of the given trap type, and count it if it is
        {
            if(deck[i].toString().equals(trap))
            {
                count++;
            }
        }

        //return the number of face up traps of a certain type
        return count;
    }

    /** Returns the total number of traps revealed in the current mine
     *
     * @return the number of traps that have been revealed in the face up cards.
     */
    public int countRevealedTraps()
    {
        int count = 0;

        for(int i = 0; i < currDeckSize; i++)// for each card in the deck check if it's a trap and if it is count it
        {
            if(deck[i].toString().equals(LAVA) || deck[i].toString().equals(SPIKES) || deck[i].toString().equals(SPIDERS)
                    || deck[i].toString().equals(SNAKES) || deck[i].toString().equals(POISON))
            {
                count++;
            }
        }

        //return the number of face up trap cards
        return count;
    }

    /** Calculate remaining face down tiles.
     *
     * @return the number of tiles that can still be revealed.
     */
    public int remainingFaceDownTiles()
    {
        if(currReveal == -1) //if all cards are faced down
        {
            return currDeckSize;
        }
        else
        {
            return currDeckSize - currReveal;
        }
    }

    /** Retrieve the last mine tile that was revealed from the deck.
     *
     * @return Return the last face up tile assuming the tiles are revealed in order from the beginning of the deck.
     */
    public MineTile getLastRevealed()
    {
        // return the last revealed card
        return getTileAt(currReveal);
    }

    /** Retrieve the index of the last mine tile that was revealed from the deck.
     *
     * @return an integer index of the last face up tile assuming tiles are revealed in order from the beginning of the deck.
     */
    public int getLastRevealedIndex()
    {
        //return the last revealed card index
        return currReveal;
    }

    /** Convert the deck into a string for printing.  This should be a comma delimited list, with tile values for face up cards and asterisks * for face down cards.
     * NB: This format is very important to implement precisely pass the tests in the test suite for this class.
     * @return the String object representing the deck.
     */
    public String toString()
    {
        StringBuilder outPut = new StringBuilder();

        for(int i = 0; i < currDeckSize; i++) //for each card in the deck
        {
            if(i < currDeckSize - 1) // if this is not the last card in the deck
            {
                outPut.append(deck[i].toString()).append(",");
            }
            else
            {
                outPut.append(deck[i].toString());
            }
        }
        //return the string containing the value of all cards
        return outPut.toString();
    }

    public static void main(String[] args)
    {
        TileDeck newDeck = new TileDeck();

        // Ensure the deck is initialized with cards down
        if(newDeck.toString().equals("*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*"))
            System.out.println("Test: TileDeck Init SUCCESS");
        else {
            System.out.println("Test: TileDeck Init FAIL");
            System.out.println("Expected: *,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*");
            System.out.println("Received: "+newDeck);
        }

        // Flip the deck up and ensure the initialization did actually work and flipDeckFaceUp works
        newDeck.flipDeckFaceUp();

        if(newDeck.toString().equals("2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Lava,Poison,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders"))
            System.out.println("Test: TileDeck Flip Up SUCCESS");
        else {
            System.out.println("Test: TileDeck Flip Up FAIL");
            System.out.println("Expected: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Lava,Poison,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders");
            System.out.println("Received: "+newDeck);
        }

        // flip the deck down and ensure that works.
        newDeck.flipDeckFaceDown();
        if(newDeck.toString().equals("*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*"))
            System.out.println("Test: TileDeck Flip Down SUCCESS");
        else {
            System.out.println("Test: TileDeck Flip Down FAIL");
            System.out.println("Expected: *,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*,*");
            System.out.println("Received: "+newDeck);
        }

        // flip the deck up so error messages have meaning on failure
        newDeck.flipDeckFaceUp();

        // Remove 1 trap of one type in the first slot
        newDeck.removeTrapCard(TrapTile.LAVA);
        if(newDeck.toString().equals("2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Poison,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders"))
            System.out.println("Test: TileDeck removeTrapCard 1 SUCCESS");
        else {
            System.out.println("Test: TileDeck removeTrapCard 1 FAIL");
            System.out.println("Expected: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Poison,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders");
            System.out.println("Received: "+newDeck);
        }

        // Remove 1 trap of another type
        newDeck.removeTrapCard(POISON);
        if(newDeck.toString().equals("2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders"))
            System.out.println("Test: TileDeck removeTrapCard 2 SUCCESS");
        else {
            System.out.println("Test: TileDeck removeTrapCard 2 FAIL");
            System.out.println("Expected: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Lava,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders");
            System.out.println("Received: "+newDeck);
        }

        // Remove 1 trap of the first type again
        newDeck.removeTrapCard(TrapTile.LAVA);
        if(newDeck.toString().equals("2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders"))
            System.out.println("Test: TileDeck removeTrapCard 3 SUCCESS");
        else {
            System.out.println("Test: TileDeck removeTrapCard 3 FAIL");
            System.out.println("Expected: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Poison,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders,Spiders");
            System.out.println("Received: "+newDeck);
        }

        // Remove 1 trap of a third type just to be sure.
        newDeck.removeTrapCard(TrapTile.SPIDERS);
        if(newDeck.toString().equals("2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders"))
            System.out.println("Test: TileDeck removeTrapCard 4 SUCCESS");
        else {
            System.out.println("Test: TileDeck removeTrapCard 4 FAIL");
            System.out.println("Expected: 2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,Lava,Poison,Poison,Spikes,Spikes,Spikes,Snakes,Snakes,Snakes,Spiders,Spiders");
            System.out.println("Received: "+newDeck);
        }

        //Shuffle the deck
        newDeck.shuffle();
        System.out.println("Test: TileDeck Shuffle 1 MANUAL CHECK");
        System.out.println(newDeck);

        // Count the number of traps of one type
        int numSnakes = newDeck.countTraps(TrapTile.SNAKES);
        // Remove a trap of a from a shuffled deck of that type
        newDeck.removeTrapCard(TrapTile.SNAKES);

        // Check to make sure the number of snake traps is 1 fewer
        if (newDeck.countTraps(TrapTile.SNAKES) == numSnakes-1)
            System.out.println("Test: TileDeck removeTrapCard 5 SUCCESS");
        else
            System.out.println("Test: TileDeck removeTrapCard 5 FAIL");

        newDeck.shuffle();
        System.out.println("Test: Deck Shuffle 2 MANUAL CHECK");
        System.out.println(newDeck);

        // Count the number of traps of one type
        int numPoison = newDeck.countTraps(POISON);
        // Remove a trap of a from a shuffled deck of that type
        newDeck.removeTrapCard(POISON);
        if (newDeck.countTraps(POISON) == numPoison-1)
            System.out.println("Test: TileDeck removeTrapCard 6 SUCCESS");
        else
            System.out.println("Test: TileDeck removeTrapCard 6 FAIL");

        // reset the deck to 35 cards
        newDeck = new TileDeck();

        // flip the entire deck down, and then reveal the first card
        newDeck.flipDeckFaceUp();

        int slot = newDeck.getLastRevealedIndex();

        // Check correct reporting of all tiles revealed, corresponding to the size of the current deck minus 1 (34)
        if (slot == newDeck.currDeckSize-1)
            System.out.println("Test: TileDeck getLastRevealedIndex 1 SUCCESS");
        else
            System.out.println("Test: TileDeck getLastRevealedIndex 1 FAIL");

        newDeck.flipDeckFaceDown();

        slot = newDeck.getLastRevealedIndex();

        // Check correct reporting of no tiles have been revealed corresponding to -1.
        if (slot == -1)
            System.out.println("Test: TileDeck getLastRevealedIndex 2 SUCCESS");
        else
            System.out.println("Test: TileDeck getLastRevealedIndex 2 FAIL");


        newDeck.revealNextTile();
        System.out.println("Test: TileDeck revealNextTile 1 MANUAL CHECK Confirm Tile Index 0 revealed.");
        System.out.println(newDeck);

        // we expect tile 2 to be revealed in slot 0
        if(newDeck.getLastRevealed().getTileValue() == 2)
            System.out.println("Test: TileDeck getLastRevealed 1 SUCCESS");
        else
        {
            System.out.println("Test: TileDeck getLastRevealed 1 FAIL");
            System.out.println("Expected: 2");
            System.out.println("Received: "+newDeck.getLastRevealed().getTileValue());

        }

        // check to make sure the right slot is revealed and returned
        int slot3 = newDeck.getLastRevealedIndex();
        if (slot3 == 0)
            System.out.println("Test: TileDeck getLastRevealedIndex 3 SUCCESS");
        else {
            System.out.println("Test: TileDeck getLastRevealedIndex 3 FAIL");
            System.out.println("Expected: 3");
            System.out.println("Received: "+newDeck.getLastRevealedIndex());

        }
        // do it again a few of times and check it is working
        newDeck.revealNextTile();
        newDeck.revealNextTile();
        newDeck.revealNextTile();
        System.out.println("Test: TileDeck revealNextTile 2 MANUAL CHECK Confirm Tile Index 0-3 revealed");
        System.out.println(newDeck);

        // we expect tile 5 to be revealed in slot 3
        slot = newDeck.getLastRevealedIndex();
        if (slot == 3)
            System.out.println("Test: TileDeck getLastRevealedIndex 4 SUCCESS");
        else {
            System.out.println("Test: TileDeck getLastRevealedIndex 4 FAIL");
            System.out.println("Expected: 3");
            System.out.println("Received: "+newDeck.getLastRevealedIndex());

        }

        if(newDeck.getLastRevealed().getTileValue() == 5)
            System.out.println("Test: TileDeck getLastRevealed 2 SUCCESS");
        else {
            System.out.println("Test: TileDeck getLastRevealed 2 FAIL");
            System.out.println("Expected: 5");
            System.out.println("Received: "+newDeck.getLastRevealed().getTileValue());

        }
        // flip the deck down for a big test on trap functionality
        newDeck.flipDeckFaceDown();
        // first trap is located at slot 20,  reveal deck up to that point and check each one
        for(int reveal=0; reveal<21; reveal++)
        {
            newDeck.revealNextTile();
        }

        // we expect this to be a trap card, and thus value 0
        if(newDeck.getLastRevealed().getTileValue() == 0)
            System.out.println("Test: TileDeck getLastRevealed 3 SUCCESS");
        else
            System.out.println("Test: TileDeck getLastRevealed 3 FAIL");

        // we expect this to be a trap card, and so should be listed as such
        if(newDeck.getLastRevealed().isTrap())
        {
            if (((TrapTile) newDeck.getLastRevealed()).getTrapType().equals(TrapTile.LAVA))
                System.out.println("Test: TileDeck getLastRevealed 4 SUCCESS");
            else {
                System.out.println("Test: TileDeck getLastRevealed 4 Trap Type FAIL");
                System.out.println("Expected: Lava");
                System.out.println("Received: "+((TrapTile) newDeck.getLastRevealed()).getTrapType());
            }
        }
        else
        {
            System.out.println("Test: TileDeck getLastRevealed 5 Trap Detection FAIL");
            System.out.println("Expected: false");
            System.out.println("Received: "+newDeck.getLastRevealed().isTrap());
        }

        // to be on the safe side, we will check slot 23 just to be sure we have this right with Poison
        newDeck.revealNextTile();
        newDeck.revealNextTile();
        newDeck.revealNextTile();
        if(newDeck.getLastRevealed().isTrap())
        {
            if (((TrapTile) newDeck.getLastRevealed()).getTrapType().equals(POISON))
                System.out.println("Test: TileDeck getLastRevealed 5 SUCCESS");
            else {
                System.out.println("Test: TileDeck getLastRevealed 5 Trap Type FAIL");
                System.out.println("Expected: Poison");
                System.out.println("Received: "+((TrapTile) newDeck.getLastRevealed()).getTrapType());
            }
        }
        else
        {
            System.out.println("Test: TileDeck getLastRevealed 5 Trap Detection FAIL");
            System.out.println("Expected: false");
            System.out.println("Received: "+newDeck.getLastRevealed().isTrap());
        }
    }

}
