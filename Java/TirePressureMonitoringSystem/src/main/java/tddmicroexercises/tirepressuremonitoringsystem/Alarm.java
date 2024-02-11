package tddmicroexercises.tirepressuremonitoringsystem;

public class Alarm
{
    /*
      S -> SRP
      O -> Open closed
      L -> Liskov subs
      I -> interface segregation
      D -> Dependency inversion (it is not dependency injection)
     */

    /*
      class Alarm, class Sensor
      Alarm -> Sensor
      RandomSensor -> Sensor
     */

    private static final double LOW_PRESSURE_THRESHOLD = 17;
    private static final double HIGH_PRESSURE_THRESHOLD = 21;

    protected RandomSensor sensor = new RandomSensor();

    protected boolean alarmOn = false;

    public void check()
    {
        double psiPressureValue = sensor.popNextPressurePsiValue();

        if (psiPressureValue < LOW_PRESSURE_THRESHOLD || psiPressureValue > HIGH_PRESSURE_THRESHOLD)
        {
            alarmOn = true;
        }
    }

    public boolean isAlarmOn()
    {
        return alarmOn; 
    }
}
