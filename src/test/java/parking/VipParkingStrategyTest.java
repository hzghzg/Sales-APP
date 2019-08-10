package parking;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class VipParkingStrategyTest {
    @Mock
    CarDao carDao;
    @InjectMocks//这样mock掉的是类的某个实例，而不是整个类，这样该实例既能调用原来的方法也能改变原来的方法（直接mock原来的方法就不能调用了）
    VipParkingStrategy vipParkingStrategy;


	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */
        ParkingLot parkingLot=new ParkingLot("parkinglot1",1);
        Car car=new Car("beci");
        parkingLot.getParkedCars().add(car);
        List<ParkingLot> parkingLotList=new ArrayList<>();
        parkingLotList.add(parkingLot);
        Car car1=new Car("AAAAAA");
        VipParkingStrategy vipParkingStrategy=spy(new VipParkingStrategy());
        //本来应该isallow一直返回的是false,也就是不会调用createReceipt,现在直接改返回值，让continue不生效
        doReturn(true).when(vipParkingStrategy).isAllowOverPark(car1);
        vipParkingStrategy.park(parkingLotList,car1);
        verify(vipParkingStrategy,times(1)).createReceipt(parkingLot,car1);
        verify(vipParkingStrategy,never()).createNoSpaceReceipt(car1);
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        ParkingLot parkingLot=new ParkingLot("parkinglot1",1);
        Car car=new Car("beci");
        parkingLot.getParkedCars().add(car);
        List<ParkingLot> parkingLotList=new ArrayList<>();
        parkingLotList.add(parkingLot);
        Car car1=new Car("BBBB");
        VipParkingStrategy vipParkingStrategy=spy(new VipParkingStrategy());
        //本来应该isallow一直返回的是false,也就是不会调用createReceipt,现在直接改返回值，让continue不生效
        doReturn(false).when(vipParkingStrategy).isAllowOverPark(car1);
        vipParkingStrategy.park(parkingLotList,car1);
        verify(vipParkingStrategy,times(1)).createNoSpaceReceipt(car1);
        verify(vipParkingStrategy,never()).createReceipt(parkingLot,car1);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        when(carDao.isVip(any())).thenReturn(true);
        Car car=new Car("AAAAAA");
        boolean isAllowOverParkFlag=vipParkingStrategy.isAllowOverPark(car);
        Assert.assertTrue(isAllowOverParkFlag);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        when(carDao.isVip(any())).thenReturn(true);
        Car car=new Car("BBBBBB");
        boolean isAllowOverParkFlag=vipParkingStrategy.isAllowOverPark(car);
        Assert.assertFalse(isAllowOverParkFlag);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        when(carDao.isVip(any())).thenReturn(false);
        Car car=new Car("AAAAAAA");
        boolean isAllowOverParkFlag=vipParkingStrategy.isAllowOverPark(car);
        Assert.assertFalse(isAllowOverParkFlag);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        when(carDao.isVip(any())).thenReturn(false);
        Car car=new Car("BBBBBBB");
        boolean isAllowOverParkFlag=vipParkingStrategy.isAllowOverPark(car);
        Assert.assertFalse(isAllowOverParkFlag);
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
