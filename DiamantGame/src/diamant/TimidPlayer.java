package diamant;

public class TimidPlayer extends diamant.Player {

    public TimidPlayer(diamant.Pawn playerColour, int playerNumber)
    {
        super(playerColour, playerNumber);
    }

    /** A timid player will leave a mine as soon as one trap appears.
     * @param numTraps - number of traps revealed
     * @param numRubies - number of rubies in the mine
     * @param numPlayers - number of players in the mine
     * @param cardsRemain - number of cards left ot be revealed in the deck
     * @return
     */
    public boolean leaveMine(int numTraps, int numRubies, int numPlayers, int cardsRemain)
    {
        if (numTraps>=1)
        {
            this.leave = true;
            this.inMine = false;
            return true;

        }
        this.leave = false;
        this.inMine = true;

        return false;
    }

    public String reportStrategy()
    {
        return "Timid";
    }

    public static void main(String[] args)
    {
        TimidPlayer timid = new TimidPlayer(Pawn.BLACK, 2);

        timid.leaveMine(0,6,2,27);
        if(timid.isStaying() && timid.isInMine())
        {
            System.out.println("timid Player test 1: Succeeded");
        }
        else
        {
            System.out.println("timid Player test 1: Failed");
        }

        if(timid.reportStrategy().equals("Timid"))
        {
            System.out.println("Player test for strategy: succeeded (Timid Player Strategy)");
        }
        else
        {
            System.out.println("Player test for strategy : Failed\n Reported strategy is " + timid.reportStrategy());
        }

        timid.leaveMine(1,33,3,10);
        if(timid.isLeaving() && !timid.isInMine())
        {
            System.out.println("timid Player test 2: Succeeded");
        }
        else
        {
            System.out.println("timid Player test 2: Failed");
        }
    }
}
