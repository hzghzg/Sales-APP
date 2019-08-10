package parking;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class InOrderParkingStrategyTest {
    InOrderParkingStrategy inOrderParkingStrategy=new InOrderParkingStrategy();

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */
	    ParkingLot parkingLot=mock(ParkingLot.class);
	    Car car=mock(Car.class);
	    when(parkingLot.getName()).thenReturn("parkinglot1");
	    when(car.getName()).thenReturn("car1");
	    Receipt receipt=inOrderParkingStrategy.createReceipt(parkingLot,car);
        Assert.assertEquals("parkinglot1",receipt.getParkingLotName());
        Assert.assertEquals("car1",receipt.getCarName());
    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        Car car=mock(Car.class);
        when(car.getName()).thenReturn("car1");
        Receipt receipt=inOrderParkingStrategy.createNoSpaceReceipt(car);
        Assert.assertEquals("car1",receipt.getCarName());
    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
        Car car=new Car("beci");
        List<ParkingLot> parkingLotList=new ArrayList<>();
        InOrderParkingStrategy inOrderParkingStrategy=spy(new InOrderParkingStrategy());
        inOrderParkingStrategy.park(parkingLotList,car);
        verify(inOrderParkingStrategy,times(1)).createNoSpaceReceipt(car);
    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
        ParkingLot parkingLot=new ParkingLot("parkinglot1",10);
        Car car=new Car("beci");
        List<ParkingLot> parkingLotList=new ArrayList<>();
        parkingLotList.add(parkingLot);
        InOrderParkingStrategy inOrderParkingStrategy=spy(new InOrderParkingStrategy());
        inOrderParkingStrategy.park(parkingLotList,car);
        verify(inOrderParkingStrategy,times(1)).createReceipt(parkingLot,car);
        verify(inOrderParkingStrategy,times(0)).createNoSpaceReceipt(car);
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
        ParkingLot parkingLot=new ParkingLot("parkinglot1",1);
        Car car=new Car("beci");
        parkingLot.getParkedCars().add(car);
        List<ParkingLot> parkingLotList=new ArrayList<>();
        parkingLotList.add(parkingLot);
        InOrderParkingStrategy inOrderParkingStrategy=spy(new InOrderParkingStrategy());
        inOrderParkingStrategy.park(parkingLotList,car);
        verify(inOrderParkingStrategy,times(1)).createNoSpaceReceipt(car);
        verify(inOrderParkingStrategy,times(0)).createReceipt(parkingLot,car);

    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        ParkingLot parkingLot=new ParkingLot("parkinglot1",1);
        Car car=new Car("beci");
        parkingLot.getParkedCars().add(car);
        ParkingLot parkingLot1=new ParkingLot("parkinglot2",1);
        Car car1=new Car("audi");
        List<ParkingLot> parkingLotList=new ArrayList<>();
        parkingLotList.add(parkingLot);
        parkingLotList.add(parkingLot1);
        InOrderParkingStrategy inOrderParkingStrategy=spy(new InOrderParkingStrategy());
        inOrderParkingStrategy.park(parkingLotList,car);
        verify(inOrderParkingStrategy,times(0)).createReceipt(parkingLot,car);
        verify(inOrderParkingStrategy,times(1)).createReceipt(parkingLot1,car);
    }


}
