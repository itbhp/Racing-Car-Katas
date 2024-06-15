package tddmicroexercises.turnticketdispenser;

public class TicketDispenser {

    public TurnTicket getTurnTicket()
    {
        int newTurnNumber = TurnNumberSequence.getNextTurnNumber();

        return new TurnTicket(newTurnNumber);
    }

}

