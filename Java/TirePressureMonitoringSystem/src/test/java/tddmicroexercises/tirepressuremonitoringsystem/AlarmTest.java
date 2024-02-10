package tddmicroexercises.tirepressuremonitoringsystem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tddmicroexercises.tirepressuremonitoringsystem.AlarmTest.TestableAlarm.anAlarmWith;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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

        Alarm alarm = anAlarmWith(new StubRandomSensor(pressure));
        alarm.check();
        assertTrue(alarm.isAlarmOn());
    }

    @ParameterizedTest(name = "alarm is on when pressure is {0}")
    @ValueSource(doubles = {21.1d, 22.0d, 23.0d})
    void pressure_above_upper_threshold_should_set_alarm_on(double pressure) {

        Alarm alarm = anAlarmWith(new StubRandomSensor(pressure));
        alarm.check();
        assertTrue(alarm.isAlarmOn());
    }

    @ParameterizedTest(name = "alarm is off when pressure is {0}")
    @ValueSource(doubles = {17.0d, 18.0d, 20.0d, 17.0d})
    void pressure_in_range_alarm_is_off(double pressure) {

        Alarm alarm = anAlarmWith(new StubRandomSensor(pressure));
        alarm.check();
        assertFalse(alarm.isAlarmOn());
    }

    static class TestableAlarm extends Alarm {
        private TestableAlarm(RandomSensor sensor) {
            this.sensor = sensor;
        }

        public static Alarm anAlarmWith(RandomSensor sensor){
            return new TestableAlarm(sensor);
        }
    }

    static class StubRandomSensor extends RandomSensor {
        private final double fixedValue;

        StubRandomSensor(double fixedValue) {
            this.fixedValue = fixedValue;
        }

        @Override
        public double popNextPressurePsiValue() {
            return fixedValue;
        }
    }
}
