/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package demo.nsc;

import java.nio.ByteBuffer;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author user
 */
class SerialPortReader implements SerialPortEventListener {
    ByteBuffer currBuffer = ByteBuffer.allocateDirect(100);
    private final SerialPort serialPort;

    private SerialPortReader(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR()) {
            try {
                byte[] buffer = serialPort.readBytes(event.getEventValue());
                System.out.println(buffer);
            } catch (SerialPortException ex) {
                System.out.println(ex);
            }
        }
    }
    
}
