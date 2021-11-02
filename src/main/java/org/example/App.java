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
        for (SerialPort port: ports) {
            System.out.println("port found: " + port.getSystemPortName());
        }
        SerialPort port = SerialPort.getCommPort(portName);
        printToPort(port, message);
    }

    public static void printToPort(SerialPort port, String message) throws InterruptedException {
        System.out.println("> port " + port.getSystemPortName());
        port.setBaudRate(9600);
        System.out.println("baudRate: " + port.getBaudRate());
        port.setNumDataBits(8);
        System.out.println("dataBits: " + port.getNumDataBits());
        port.setParity(SerialPort.NO_PARITY);
        System.out.println("parity: NO_PARITY");
        port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        System.out.println("flowControl: FLOW_CONTROL_DISABLED");
        port.setNumStopBits(1);
        System.out.println("stopBits: " + port.getNumStopBits());
        port.clearRTS();
        System.out.println("clearRTS");
        port.clearDTR();
        System.out.println("clearDTR");

        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING | SerialPort.TIMEOUT_WRITE_BLOCKING, 1000, 1000);

        port.openPort(2000);
        System.out.println("> port " + port.getSystemPortName() + " opened");

        Thread.sleep(1000);

        OutputStream out = port.getOutputStream();
        System.out.println("- message: " + message);
        try {
            out.write(message.getBytes(StandardCharsets.UTF_8));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            Thread.sleep(1000);

            port.closePort();
            System.out.println("> port closed");
        }
    }
}
