package tddmicroexercises.tirepressuremonitoringsystem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class AlarmTest {

    /**
     * test list
     * - in the beginning the alarm is not on
     * - if the sensor pressure is below 17 alarm is on
     * - if the sensor pressure is above 21 alarm is on
     * - if the pressure (p) is 17 <= p <= 21 alarm is off
     */

    /* constructor vs setters
      1. pros:
         - favor immutability
      2. cons:
         - change in the constructor (adding parameters will impact the callers of te constructor)
           -- you can use smart constructors (factory methods)
     */

    /* environments
      - dev -> I will construct my objects in some way
      - prod -> I will construct my objects in some way
     */
    @Test
    public void alarm_is_off_in_the_beginning() {
        Alarm alarm = new Alarm();
        assertFalse(alarm.isAlarmOn());
    }

    @ParameterizedTest(name = "alarm is on when pressure is {0}")
    @ValueSource(doubles = {16.0d, 16.9d, 15.9d})
    void pressure_below_low_threshold_should_set_alarm_on(double pressure) {

        Alarm alarm = new TestableAlarm(new StubSensor(pressure));
        // reflection
        alarm.check();
        assertTrue(alarm.isAlarmOn());
    }

    static class TestableAlarm extends Alarm {

        public TestableAlarm(Sensor sensor) {
            this.sensor = sensor;
        }
    }

    static class StubSensor extends Sensor {
        private final double fixedValue;

        StubSensor(double fixedValue) {
            this.fixedValue = fixedValue;
        }

        @Override
        public double popNextPressurePsiValue() {
            return fixedValue;
        }
    }
}
