package diamant;

import java.util.Random;

public class BalancePlayer extends diamant.Player {

    public BalancePlayer(diamant.Pawn playerColour, int playerNumber)
    {
        super(playerColour, playerNumber);
    }

    /** A balanced player will stay in the mine until 2 trap cards have been revealed and then have a 50/50 chance of leaving.
     * @param numTraps - number of traps revealed
     * @param numRubies - number of rubies in the mine
     * @param numPlayers - number of players in the mine
     * @param cardsRemain - number of cards left ot be revealed in the deck
     * @return return true if leaving the mine, false otherwise.
     */
    public boolean leaveMine(int numTraps, int numRubies, int numPlayers, int cardsRemain)
    {
        Random rand = new Random();

        if(numTraps >=2)
        {
            int chance = rand.nextInt(100);
            if(chance >= 51) {
                this.leave = true;
                this.inMine = false;
                return true;
            }
        }
        this.leave = false;
        this.inMine = true;

        return false;
    }

    public String reportStrategy()
    {
        return "Balance";
    }

    public static void main(String[] args)
    {
        BalancePlayer balanced = new BalancePlayer(Pawn.BLUE, 2);

        balanced.leaveMine(0,6,2,27);
        if(balanced.isStaying() && balanced.isInMine())
        {
            System.out.println("balanced Player test 1: Succeeded");
        }
        else
        {
            System.out.println("balanced Player test 1: Failed");
        }

        if(balanced.reportStrategy().equals("Balance"))
        {
            System.out.println("Player test for strategy: succeeded (balanced Player Strategy)");
        }
        else
        {
            System.out.println("Player test for strategy : Failed\n Reported strategy is " + balanced.reportStrategy());
        }


        int leaveCount = 0;
        int stayCount = 0;
        int tries = 1000;
        for(int i = 0; i < tries; i++)
        {
            balanced.leaveMine(3, 33, 3, 10);
            if (balanced.isLeaving() && !balanced.isInMine()) {
                leaveCount++;
            } else {
                stayCount++;
            }
        }
        System.out.printf("Manual check (the percentage should be close to 50): out of 200 times the player stayed in the mine when there was more than 1 trap %d times, and left %d times." +
                "\nWhich means the player stayed in the mine %.2f%% of the times.", stayCount, leaveCount, (stayCount/(double)tries) * 100);
    }

}
