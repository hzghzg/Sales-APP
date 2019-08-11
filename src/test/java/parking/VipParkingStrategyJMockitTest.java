package parking;

import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
@RunWith(JMockit.class)
public class VipParkingStrategyJMockitTest {

    @Test
    public void testCalculateHourlyPrice_givenSunday_thenPriceIsDoubleOfSundayPrice(){

        /* Exercise 6: Write test case for VipParkingStrategy calculateHourlyPrice
        * by using JMockit to mock static method */
//        new mockit.MockUp<ParkingLot>(){
//            @mockit.Mock
//            public int getBasicHourlyPrice(){
//                return 25;
//            }
//        };
          new mockit.MockUp<Calendar>(){
              @mockit.Mock
              public int get(int i){
                return Calendar.SUNDAY;
              }
          };
        VipParkingStrategy vipParkingStrategy=new VipParkingStrategy();
        Assert.assertEquals(50,vipParkingStrategy.calculateHourlyPrice().intValue());
    }

    @Test
    public void testCalculateHourlyPrice_givenNotSunday_thenPriceIsDoubleOfNonSundayPrice(){

        /* Exercise 6: Write test case for VipParkingStrategy calculateHourlyPrice
         * by using JMockit to mock static method */
        new mockit.MockUp<ParkingLot>(){
            @mockit.Mock
            public int getBasicHourlyPrice(){
                return 20;
            }
        };
        VipParkingStrategy vipParkingStrategy=new VipParkingStrategy();
        Assert.assertEquals(40,vipParkingStrategy.calculateHourlyPrice().intValue());

    }
}
