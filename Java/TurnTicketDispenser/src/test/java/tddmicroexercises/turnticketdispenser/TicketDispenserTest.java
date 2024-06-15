package tddmicroexercises.turnticketdispenser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
                CompletableFuture<TurnTicket> dispenserFutureTicket =
                        supplyAsync(dispenser::getTurnTicket, executorService);
                CompletableFuture<TurnTicket> anotherDispenserFutureTicket =
                        supplyAsync(anotherDispenser::getTurnTicket, executorService);
                List<Integer> numbers = Stream.of(dispenserFutureTicket, anotherDispenserFutureTicket)
                        .map(CompletableFuture::join)
                        .map(TurnTicket::getTurnNumber).toList();
                assertThat("Expected list with 2 elements, actual: " + numbers, numbers, hasSize(2));
                assertThat("Expected list containing 0 and 1 and actual: " + numbers, numbers, hasItems(0, 1));
            }
        }
    }

}
