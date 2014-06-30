/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package demo.nsc;

import java.nio.ByteBuffer;
import java.util.Arrays;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author user
 */
class SerialPortReader implements SerialPortEventListener {
    ByteBuffer currBuffer = ByteBuffer.allocateDirect(DataPacket.PACKET_SIZE);
    private final SerialPort serialPort;

    SerialPortReader(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR()) {
            try {
                byte[] buf = serialPort.readBytes(Math.min(event.getEventValue(), currBuffer.remaining()));
                System.out.println(Arrays.asList(buf));
                currBuffer.put(buf);
                if(!currBuffer.hasRemaining()){
                    // Read the data packet
                    currBuffer.rewind();
                    DataPacket nDP = DataPacket.fromBytes(currBuffer);
                    
                    // Reset the buffer.
                    currBuffer.rewind();
                    
                    System.out.println(nDP.toString());
                }
                //System.out.println(buffer);
            } catch (SerialPortException ex) {
                System.out.println(ex);
            }
        }
    }
    
}
