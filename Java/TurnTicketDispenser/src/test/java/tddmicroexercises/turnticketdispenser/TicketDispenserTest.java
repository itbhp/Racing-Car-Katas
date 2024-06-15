package tddmicroexercises.turnticketdispenser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;

class TicketDispenserTest {

    @BeforeEach
    void setUp() {
        TurnNumberSequence.reset();
    }

    @Test
    void first_ticket_number_is_zero() {
        TicketDispenser dispenser = new TicketDispenser();
        TurnTicket ticket = dispenser.getTurnTicket();
        assertEquals(0, ticket.getTurnNumber());
    }

    @Test
    void ticket_number_should_keep_increasing() {
        TicketDispenser dispenser = new TicketDispenser();
        assertEquals(0, dispenser.getTurnTicket().getTurnNumber());
        assertEquals(1, dispenser.getTurnTicket().getTurnNumber());
        assertEquals(2, dispenser.getTurnTicket().getTurnNumber());
    }

    @Test
    void multiple_dispensers_receive_different_ticket_number() {
        TicketDispenser dispenser = new TicketDispenser();
        assertEquals(0, dispenser.getTurnTicket().getTurnNumber());
        TicketDispenser anotherDispenser = new TicketDispenser();
        assertEquals(1, anotherDispenser.getTurnTicket().getTurnNumber());
    }

    @Test
    void multiple_dispensers_can_work_concurrently() {
        for (int i = 0; i < 100_000; i++) {
            TurnNumberSequence.reset();
            try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
                TicketDispenser dispenser = new TicketDispenser();
                TicketDispenser anotherDispenser = new TicketDispenser();
                Callable<TurnTicket> dispenserTicket = dispenser::getTurnTicket;
                Callable<TurnTicket> anotherDispenserTicket = anotherDispenser::getTurnTicket;
                List<Future<TurnTicket>> futures = executorService.invokeAll(List.of(dispenserTicket, anotherDispenserTicket));
                List<Integer> numbers = futures.stream()
                        .map(TicketDispenserTest::getTurnTicket)
                        .map(TurnTicket::getTurnNumber)
                        .toList();
//                System.out.println("execution i: " + i + " numbers: " + numbers);
                assertThat(numbers, Matchers.containsInAnyOrder(0, 1));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static TurnTicket getTurnTicket(Future<TurnTicket> f) {
        try {
            return f.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
