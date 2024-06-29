package tddmicroexercises.telemetrysystem;

public class TelemetryDiagnosticControls {
    private final String DiagnosticChannelConnectionString = "*111#";

    private final TelemetryClient telemetryClient;
    private String diagnosticInfo = "";

    public TelemetryDiagnosticControls() {
        telemetryClient = new TelemetryClient();
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

        disconnect();

        int retryLeft = 3;
        while (isOnlineStatus() == false && retryLeft > 0) {
            connect();
            retryLeft -= 1;
        }

        if (isOnlineStatus() == false) {
            throw new Exception("Unable to connect.");
        }

        String message = TelemetryClient.DIAGNOSTIC_MESSAGE;
        send(message);
        diagnosticInfo = receive();
    }

    protected void connect() {
        telemetryClient.connect(DiagnosticChannelConnectionString);
    }

    protected String receive() {
        return telemetryClient.receive();
    }

    protected void send(String message) {
        telemetryClient.send(message);
    }

    protected boolean isOnlineStatus() {
        return telemetryClient.getOnlineStatus();
    }

    protected void disconnect() {
        telemetryClient.disconnect();
    }
}
