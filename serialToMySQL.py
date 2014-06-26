#!/usr/bin/python3

import serial

def main(sport, baud):
	s = serial.Serial(sport, baud)
	while s.isOpen():
		inp = s.read()
		print("{}\t{}\n".format(inp, ord(inp)))
	

if __name__ == "__main__" :
	main("/dev/ttyUSB0", 57600)
