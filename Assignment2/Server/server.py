import socket
import sys
import threading
import time
from queue import Queue
import pickle
import os

from file_generator import F_Generator

lock = threading.Lock()


Number_of_threads = 2
job_number = [1,2]
queue = Queue()
connections = []
all_connections = []
all_address = []

host = ""
port = 9999
s = socket.socket()


def create_socket():
    try:
        global host
        global port
        global s
        host = ""
        port = 9999
        s = socket.socket()
    except socket.error as msg:
        print("Socket creation error: " + str(msg))

def bind_socket():
    try:
        global host
        global port
        global s
        print('Binding the Ports: ' + str(port))

        s.bind((host,port))
        s.listen(5)

    except socket.error as msg:
        print('Socket Binding error ' + str(msg) + '\n' + 'Retrying')


def accept_connections():
    
    for c in all_connections:
        c.close()
    del all_connections[:]
    del all_address[:]

    while True:
        try:
            conn, address = s.accept()
            s.setblocking(1)  # prevents timeout

            all_connections.append(conn)
            all_address.append(address)

            accepted = True
            
            print("Connection has been established : " + address[0])

            display_queue(conn)

        except:
            print("Error accepting connections")
            break


# Selecting the target
def get_target(target):
    try:
        conn = all_connections[target]
        print("You are now connected to: " + str(all_address[target][0]))
        return conn,target

    except:
        print("selection not valid")
        return None


# Send commands to client
def send_target_commands(conn):

    print("waiting for char") 

    char = str(conn[0].recv(1024))

    print("Chosen characters: " + char[1:])

    all_connections.remove(all_connections[conn[1]])

    lock.acquire()
    try:
        F_Generator(char[2:-1]).create_file()

        send_file(conn[0])
    finally:
            lock.release()
        
    #display_queue()
    #conn[0].send(bytes("hi","utf-8"))

    

    all_address.remove(all_address[conn[1]])

    conn[0].close()
    list_connections()


def display_queue(current):
    results = []

    for i, conn in enumerate(all_connections):

        if conn == current:
            output = "Your position"

        else:
            #output = str(i) + "   " + str(all_address[i][0])
            output = "Client ####" 
           
        results.append(output)

        
        msg = pickle.dumps(results)
        conn.send(msg)



def list_connections():
    print('listing connections')
    while True:
        results = []

        for i, conn in enumerate(all_connections):

            output = str(i) + "   " + str(all_address[i][0])
            results.append(output)

            print("----Client----" )
            print(results)
            
            
            target_connection = get_target(i)
            send_target_commands(target_connection)
            



def send_file(conn):

    print("Finding file")
    # receive 4096 bytes each time
    BUFFER_SIZE = 20480
    filename = "new_words.txt" # name of file to send
    filesize = os.path.getsize(filename) # get size of the file

    msg = pickle.dumps("Receiving file...")
    conn.send(msg)

    with open(filename, "rb") as f:
        while True:
            #time.sleep(1)
            #read the bytes from the file
            bytes_read = f.read(BUFFER_SIZE)
            if not bytes_read:
                #file transmitting is done
                break
            # Use sendall to assure transmission in busy networks
            conn.sendall(bytes_read)
            # update progress bar
    print("File sent")
    # close the client socket
    conn.close()

    
"""
def shutdown_server():
    time.sleep(10)
    if len(all_connections) == 0:
        print("Server shutting down!")
        #s.shutdown(1)
        
        s.close()
        sys.exit()


Thread(target=shutdown_server).start()
"""
def create_workers():
    for _ in range(Number_of_threads):
        t = threading.Thread(target=work)
        t.daemon = True
        t.start()


# Do next job that is in the queue (handle connections, send commands)
def work():

    while True:
        x = queue.get()
        if x == 1:
            create_socket()
            bind_socket()
            accept_connections()

        if x == 2:
            list_connections()

        queue.task_done()

def close_socket():
    s.close()

def create_jobs():
    for x in job_number:
        queue.put(x)

    queue.join()


create_workers()
create_jobs()


