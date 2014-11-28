package ch.bfh.iot.smoje.raspi.sensors;

import org.zu.ardulink.Link;
import org.zu.ardulink.RawDataListener;

import ch.bfh.iot.smoje.raspi.common.SensorState;

public class Test implements ISensor {

    private static final String CONNECTION_PORT = "/dev/ttyS80";
    private static final int BAUD_RATE = 9600;
    private String value = null;

    @Override
    public String getId() {
        return "test";
    }

    @Override
    public String getValue() {
        Link arduinoLink = Link.getDefaultInstance();

        // Mit Arduino verbinden
        boolean connected = arduinoLink.connect(CONNECTION_PORT, BAUD_RATE);

        // Verbindungsstatus ausgeben
        if (connected) {
            System.out.println("Arduino connected");
        } else {
            System.out.println("Arduino not connected");
        }

        // Stream senden an Arduino
        // arduinoLink.addRawDataListener(incomingDataListener);
        arduinoLink.addRawDataListener(new RawDataListener() {

            @Override
            public void parseInput(String arg0, int arg1, int[] arg2) {
                // TODO Auto-generated method stub
            	System.out.println("start " + "------------------------------------------------------------------------------------------");
                Test.this.value = arg0;
                System.out.println("Arg0: " + arg0);
                System.out.println("Arg: 1" + arg1);
                
                StringBuffer arg2Result = new StringBuffer();
                for (int i = 0; i < arg2.length; i++) {
                	arg2Result.append( ", " + arg2[i] );
                }
                String arg2String = arg2Result.toString();
                
                System.out.println("Arg: 2" + arg2String);
                System.out.println("end " + "------------------------------------------------------------------------------------------");
//                notify();
            }
        });
//        synchronized (this) {

            String messageToSend = "1";
            arduinoLink.writeSerial(messageToSend);
            System.out.println("Send Message: " + messageToSend);
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }

        // System.out.println("Write " + messageToSend + " to Arduino");
        return value;
    }

    @Override
    public String getUnit() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SensorState getStatus() {
        // TODO Auto-generated method stub
        return null;
    }
}