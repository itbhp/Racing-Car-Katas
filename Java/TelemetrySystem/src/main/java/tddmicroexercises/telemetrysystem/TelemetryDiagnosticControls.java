package tddmicroexercises.telemetrysystem;

public class TelemetryDiagnosticControls {
    private final String DiagnosticChannelConnectionString = "*111#";

    private final TelemetryClient telemetryClient;
    private String diagnosticInfo = "";

    public TelemetryDiagnosticControls(TelemetryClient client) {
        telemetryClient = client;
    }

    public String getDiagnosticInfo() {
        return diagnosticInfo;
    }

    public void setDiagnosticInfo(String diagnosticInfo) {
        this.diagnosticInfo = diagnosticInfo;
    }

    /*
     * A.disconnect() -> B.connect() -> C.send() -> D.receive()
     *
     * temporal coupling -> action A should happen before action B
     *
     *
     */

    public void checkTransmission() throws Exception {
        diagnosticInfo = "";

        telemetryClient.disconnect();

        int retryLeft = 3;
        while (!telemetryClient.getOnlineStatus() && retryLeft > 0) {
            telemetryClient.connect(DiagnosticChannelConnectionString);
            retryLeft -= 1;
        }

        if (!telemetryClient.getOnlineStatus()) {
            throw new Exception("Unable to connect.");
        }

        String message = TelemetryClient.DIAGNOSTIC_MESSAGE;
        telemetryClient.send(message);
        diagnosticInfo = telemetryClient.receive();
    }

}
