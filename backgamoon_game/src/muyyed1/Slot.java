package muyyed1;

public class Slot
{
    int value;
    int player;

    public Slot() {
        this.value = 0;
        this.player = Table.NO_PLAYER;
    }

    public Slot(int value, int player) {
        this.value = value;
        this.player = player;  //  here remind that player =1 ==> means [Y]
    }

    String GetPlayerAsString() {
        if (player == Table.PLAYER_X) {
            return "X";
        } else if (player == Table.PLAYER_Y) {
            return "Y";
        } else if (player == Table.NO_PLAYER) {
            return " ";
        } else {
            // ERROR 
            System.out.print("error is : player = " + player);
            System.exit(0);
        }

        return "X";

    }
    
}
