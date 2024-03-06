package diamant;

public class MineTile{

    // indicator if a card is face up (true) or face down (false)
    private boolean up = false;

    // the value of the tile; positive integer for treasure, negative 1 for traps
    private int tileValue = 0;

    // indicator if a card is a trap card; false if not a trap, true if there is a trap
    private boolean trap = false;

    /** Create a mine tile with the value passed through the first parameter. Tiles default to not being traps and being face down.
     *
     * @param value - the value of the card
     */
    public MineTile(int value)
    {
        //assign a value for the card, turn it face down, and assign it as not trap
        this.tileValue = value;
        this.up = false;
        this.trap = false;
    }

    /** Retrieve the value of the tile.
     *
     * @return the value of the card
     */
    public int getTileValue()
    {
        //return the value of a card
        return this.tileValue;
    }

    /** Test if there is a trap on a tile.
     *
     * @return true if the tile is a trap, false if it is not a trap.
     */
    public boolean isTrap()
    {
        //return true if a card is a trap, false if it is not
        return this.trap;
    }

    /** Set a tile to be a trap or not.
     *
     * @param trap true if the tile is to have a trap, false otherwise.
     */
    public void setTrap(boolean trap)
    {
        //assign a card to be a trap if the parameter trap is true
        this.trap = trap;
    }

    /** Turn a tile face up if it is down, and down if it is up.
     *
     * @return resulting orientation of the tile.
     */
    public boolean flipTile()
    {
        //if a card is faced up, turn it to face down, and if a card is faced down, turn it to face up
        this.up = !up;

        //return the value of (up)
        return this.up;
    }

    /** Test to see if a tile is face up.
     *
     * @return true if face up, false otherwise.
     */
    public boolean isFaceUp()
    {
        //return up as true if the card is faced up, false if it is faced down
        return this.up;
    }

    /** Test to see if a tile is face down.
     *
     * @return true if face down, false otherwise.
     */
    public boolean isFaceDown()
    {
        //return the inverse of up, if it's true return false and if false return true
        return !this.up;
    }

    /** Turn a tile face down if it is up */
    public void turnFaceDown()
    {
        if(this.up) //if it is up, turn it down
        {
            this.up = false;
        }

    }

    /** Turn a tile face up if it is down */
    public void turnFaceUp()
    {
        if(!this.up) //if it is not up, turn it up
        {
            this.up = true;
        }
    }

    /** Convert a tile to a string for printing.
     * @return An asterisk * if face down, or its value if it is face up.
     * */
    public String toString()
    {
        if(this.up) // if the card is face up then print its value
        {
            return Integer.toString(this.tileValue);
        }
        else // if a card is faced down print an asterisk
        {
            return "*";
        }
    }

    public static void main(String[] args)
    {
        MineTile three = new MineTile(3);

        three.turnFaceUp();

        if((three.toString().equals("3"))&&(three.isFaceUp()))
        {
            System.out.println("Test: MineTile turnFaceUp SUCCESS");
        }
        else
        {
            System.out.println("Test: MineTile turnFaceUp FAIL");
        }

        three.turnFaceDown();

        if((three.toString().equals("*"))&&(three.isFaceDown()))
        {
            System.out.println("Test: MineTile turnFaceDown SUCCESS");
        }
        else
        {
            System.out.println("Test: MineTile turnFaceDown FAIL");
        }

        three.flipTile();
        if((three.toString().equals("3"))&&(three.isFaceUp()))
        {
            System.out.println("Test: MineTile flipTile SUCCESS");
        }
        else
        {
            System.out.println("Test: MineTile flipTile FAIL");
        }


    }

}
