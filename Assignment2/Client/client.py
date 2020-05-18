import socket
import os
import sys
import pickle
from threading import Thread
import time

buffer_size = 20484

host = '139.162.192.236' # IP address of server

port = 9999

s = socket.socket() # create client socket

print(f"Connecting to {host}:{port}")
s.connect((host,port)) # connect socket to address
print("Connected.")


char = None

def send_chunk():

    char = input("Please enter a range of characters: " )
    s.sendall(char.encode())


def check():
    time.sleep(2)
    if char != None:
        return
    close_socket = ""
    s.send(close_socket.encode())
    print("Connection closed due to inactivity")
    #s.close()

def display_queue():
    print("----Queue----")
    msg = s.recv(buffer_size)
    print(pickle.loads(msg))



def receive_file():
    with open('received_file.txt', "wb") as f:
        #print("Recieving data...")
        msg = s.recv(buffer_size)
        print(pickle.loads(msg))
        while True:

            bytes_read = s.recv(buffer_size)
            if not bytes_read:    
                # nothing is received
                # file transmitting is done
                break
            # write to the file the bytes we just received
            f.write(bytes_read)
    
    f.close()
    print('Successfully recieved the file')



def main():
    send_chunk()
    display_queue()
    receive_file()
    s.close()
    print("Connection closed")
    """
    cmd = input("Type quit to exit: ")
    if cmd == "quit":
        s.close()
        print('connection closed')
    else:
        main()
    """

main()

