/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.nsc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author user
 */
public class NSCDeviceDemo {

    private static String DEFAULT_TTY_PORT = "/dev/ttyUSB0";
    private static int DEFAULT_BAUD = 57600;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String portName;
        int baud = DEFAULT_BAUD;
        if (args.length == 0) {
            System.out.println("Defaulting to " + DEFAULT_TTY_PORT);
            portName = DEFAULT_TTY_PORT;
        } else {
            portName = args[0];
            if (args.length > 1) {
                baud = Integer.decode(args[1]);
            }
        }

        try {
            SerialPort serialPort;
            serialPort = new SerialPort(portName);
            serialPort.openPort();
            serialPort.setParams(baud, 8, 1, 0);
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            //Add an interface through which we will receive information about events
            serialPort.addEventListener(new SerialPortReader(serialPort));

            WSServer _server = new WSServer(18823);
            _server.start();
            BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String in = sysin.readLine();
                if (in.equals("quit")) {
                    _server.stop();
                    serialPort.closePort();
                    break;
                }
            }
        } catch (IOException | InterruptedException | SerialPortException ex) {
            ex.printStackTrace();
        }

    }


}
