package tddmicroexercises.telemetrysystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TelemetryDiagnosticControlsTest
{

    /**
     * 1. connection error
     * 2. client is not online
     * 4. happy path: it receives the diagnostic message
     */

    @Test
    public void CheckTransmission_should_send_a_diagnostic_message_and_receive_a_status_message_response()
    {
        TelemetryDiagnosticControls telemetryDiagnosticControls = new TelemetryDiagnosticControls();

        try {
            telemetryDiagnosticControls.checkTransmission();
            assertFalse(telemetryDiagnosticControls.getDiagnosticInfo().isEmpty());
            assertEquals("LAST TX rate................ 100 MBPS\r\n"
                    + "HIGHEST TX rate............. 100 MBPS\r\n"
                    + "LAST RX rate................ 100 MBPS\r\n"
                    + "HIGHEST RX rate............. 100 MBPS\r\n"
                    + "BIT RATE.................... 100000000\r\n"
                    + "WORD LEN.................... 16\r\n"
                    + "WORD/FRAME.................. 511\r\n"
                    + "BITS/FRAME.................. 8192\r\n"
                    + "MODULATION TYPE............. PCM/FM\r\n"
                    + "TX Digital Los.............. 0.75\r\n"
                    + "RX Digital Los.............. 0.10\r\n"
                    + "BEP Test.................... -5\r\n"
                    + "Local Rtrn Count............ 00\r\n"
                    + "Remote Rtrn Count........... 00", telemetryDiagnosticControls.getDiagnosticInfo());
        } catch (Exception e) {
            fail("error checking transmission");
        }
    }

    @Test
    void cannot_connect_to_the_telemetry_system() {
        TelemetryDiagnosticControls underTest = new TestableDiagnosticControl(false);
        try {
            underTest.checkTransmission();
            fail("it got unexpectedly the connection");
        } catch (Exception e) {
            assertEquals("Unable to connect.", e.getMessage());
        }
    }

    private static class TestableDiagnosticControl extends TelemetryDiagnosticControls {
        private final boolean online;

        private TestableDiagnosticControl(boolean online) {
            this.online = online;
        }

        @Override
        protected boolean isOnlineStatus() {
            return online;
        }
    }
}
