/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.nsc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author user
 */
public class NSCDeviceDemo {

    private static final String DEFAULT_TTY_PORT = "/dev/ttyUSB0";
    private static final String DEFAULT_ELF = "./nsc_gateway_64.elf";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String portName;
        String elfName;
        if (args.length == 0) {
            System.out.println("Defaulting to " + DEFAULT_TTY_PORT);
            portName = DEFAULT_TTY_PORT;
        } else {
            portName = args[0];
        }

        if (args.length < 2) {
            System.out.println("Default reader " + DEFAULT_ELF);
            elfName = DEFAULT_ELF;
        } else {
            elfName = args[1];
        }

        try {
            Process process = new ProcessBuilder(elfName, portName).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String in;

            WSServer _server = new WSServer(18823);
            _server.start();
            while (true) {
                in = br.readLine();
                
                if(in == null){
                    System.out.println("EOF");
                    _server.stop();
                    break;
                }
                
                if (!in.isEmpty() && !in.startsWith("#")) {
                    _server.sendAll(in);
                    System.out.println(in);
                }
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
