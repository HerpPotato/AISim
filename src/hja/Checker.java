package hja;

import java.util.ArrayList;

class Checker implements Constants
{
    boolean checkWin(int player, Data data)
    {
        int one = data.getPos()[0][0],
        two = data.getPos()[0][1],
        three = data.getPos()[0][2],
        four = data.getPos()[1][0],
        five = data.getPos()[1][1],
        six = data.getPos()[1][2],
        seven = data.getPos()[2][0],
        eight = data.getPos()[2][1],
        nine = data.getPos()[2][2];
        return (one == player && two == player && three == player) ||
                (four == player && five == player && six == player) ||
                (seven == player && eight == player && nine == player) ||
                (one == player && four == player && seven == player) ||
                (two == player && five  == player && eight == player) ||
                (three == player && six == player && nine == player) ||
                (one == player && five == player && nine == player) ||
                (three == player && five == player && seven == player);
    }

    Position getMoveToWin(int player, Data data)
    {
        Data newData = data.clone();
        ArrayList<Position> movesAvailable = data.getAvailableMoves();
        for (int i = 0; i < movesAvailable.size(); i++)
            if (checkWin(player, (newData.clone()).move(player, movesAvailable.get(i))))
                return movesAvailable.get(i);
        return new Position();
    }

    private Position getForkingMove(int player, Data data)
    {
        ArrayList<Position> movesAvailable = data.getAvailableMoves();
        System.out.println(movesAvailable + " getForkingMovesList");
        for (int i = movesAvailable.size() - 1; i >= 0; i--)
        {
            System.out.println(i);
            System.out.println("data");
            System.out.println(data);
            Data newData = data.clone();
            newData.move(player, movesAvailable.get(i));
            System.out.println("newData");
            System.out.println(newData);
            if (!this.getMoveToWin(player, newData).isValid())
            {
                System.out.println(movesAvailable.get(i) + " cannot win");
                System.out.println();
                movesAvailable.remove(i);
                continue;
            }
            else
            {
                Data newNewData = newData.clone();
                if (this.getMoveToWin(player * enemy, newNewData).isValid())
                {
                    System.out.println(movesAvailable.get(i) + " will lose :1");
                    System.out.println();
                    movesAvailable.remove(i);
                    continue;
                }
                newNewData.move(player * enemy, this.getMoveToWin(player, newData));
                System.out.println("newNewData");
                System.out.println(newNewData);
                if (this.getMoveToWin(player, newNewData).isValid())
                {
                    if (checkWin(player * enemy, newNewData))
                    {
                        System.out.println(movesAvailable.get(i) + " will lose :2");
                        System.out.println();
                        movesAvailable.remove(i);
                        continue;
                    }
                    System.out.println(" can be forked on " + movesAvailable.get(i) + " with " + this.getMoveToWin(player, newNewData));
                    return movesAvailable.get(i);
                }
            }
        }

        return new Position();
    }


    ArrayList<Position> getNonForkingMoves(int player, Data data)
    {
        ArrayList<Position> movesAvailable = data.getAvailableMoves();
        for (int i = movesAvailable.size() - 1; i >= 0; i--)
        {
            System.out.print(movesAvailable.get(i));
            Data newData = data.clone();
            newData.move(player, movesAvailable.get(i));
            if (this.getForkingMove(player * enemy, newData).isValid())
            {
                movesAvailable.remove(i);
                continue;
            }
            System.out.println(movesAvailable.get(i) + " is a valid non-forking move.");
        }
        if (movesAvailable.size() > 0)
            System.out.println("I have found " + movesAvailable.size() + " non-forkable move(s).");
        return movesAvailable;
    }
}
