#!/usr/bin/python3

import serial, os, time

def main(sport, baud):
	s = serial.Serial(sport, baud)
	currStr = b""
	isRecording = False
	while s.isOpen():
		inp = s.read()
		if ord(inp) == 6:
			isRecording = True
			currStr = b""
		elif ord(inp) == 0:
			if(isRecording):
				if(len(currStr) > 3): # Handle a new line here!
					currStr = currStr.decode("utf-8").split(",")
					if len(currStr) == 4:
						# This is bad:
						INSERT_STRING="INSERT INTO demo VALUES ({}, {}, {}, {}, {})".format(time.time(), currStr[3], currStr[0], currStr[1], currStr[2])
						# I am ashamed that I can even write such code.
						os.system("mysql -u user sensor_readings -e \"{}\"".format(INSERT_STRING))
						# At least I'm not putting my name to it.
					else:
						print("Unexpected Input: {}".format(str(currStr)))
			isRecording = False
		else:
			currStr += inp
	

if __name__ == "__main__" :
	main("/dev/ttyUSB0", 57600)
