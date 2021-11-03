package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * This demo
 * prints available ports
 * a sends MESSAGE to PORT
 */
public class App{

    public static void main( String[] args ) throws InterruptedException {

        String portName = args[0];
        String message = args[1];

        System.out.println("Serial port demo");
        SerialPort[] ports = SerialPort.getCommPorts();
        SerialPort port = null;
        for (SerialPort availablePort: ports) {
            System.out.println("port found: " + availablePort.getSystemPortName());
            if (availablePort.getSystemPortName().equals(portName)){
                port = availablePort;
            }
        }
        //SerialPort port = SerialPort.getCommPort(portName);
        printToPort(port, message);
    }

    public static void printToPort(SerialPort port, String message) throws InterruptedException {

        System.out.println("> port " + port.getSystemPortName());

        if (port.isOpen()) {
            System.out.println("Port is already open! This can cause an error.");
        }else{
            System.out.println("Port closed. It will be set up and opened");
        }

//        Boolean flag = port.setBaudRate(9600);
//        System.out.println("baudRate: " + port.getBaudRate() + " -> " + flag);
//        port.setNumDataBits(8);
//        System.out.println("dataBits: " + port.getNumDataBits());
//        port.setParity(SerialPort.NO_PARITY);
//        System.out.println("parity: NO_PARITY");
//        port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
//        System.out.println("flowControl: FLOW_CONTROL_DISABLED");
//        port.setNumStopBits(1);
//        System.out.println("stopBits: " + port.getNumStopBits());
//        port.clearRTS();
//        System.out.println("clearRTS");
//        port.clearDTR();
//        System.out.println("clearDTR");
//        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 1000, 1000);

        //port.openPort(1000);
        Boolean opened = port.openPort();
        //Boolean opened = port.openPort(0, 1, 1);

        System.out.println("opened: " + opened);

        //Thread.sleep(3000);

        System.out.println("> port " + port.getSystemPortName() + " opened successfully: " + port.isOpen());

        //Thread.sleep(3000);

        OutputStream out = port.getOutputStream();
        System.out.println("- message: " + message);
        try {
            out.write(message.getBytes(StandardCharsets.UTF_8));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            //Thread.sleep(1000);

            port.closePort();
            System.out.println("> port closed");
        }
    }
}
