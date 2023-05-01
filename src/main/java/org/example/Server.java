package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static Hospital hospital=new Hospital();
    public static final int MAKE = 6666;
    public static final int CANCEL = 6667;
    public static void main(String[] args) {


            //For Making Appointment
            new Thread(() -> {
                ServerSocket serverSocket=null;
                try {
                    serverSocket= new ServerSocket(MAKE);
                    while(true)
                    {
                        new Hospital_Jop(serverSocket.accept()).start();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                finally {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();

            //For Cancel Appointment
            new Thread(() -> {
                ServerSocket serverSocket=null;
                try {
                    serverSocket= new ServerSocket(CANCEL);
                    while(true)
                    {
                        new Hospital_Jop(serverSocket.accept()).start();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                finally {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }


    private static class Hospital_Jop extends Thread {
        String patient_name=null;
        Socket socket;
        Hospital_Jop(Socket socket)  {
            this.socket=socket;
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                patient_name=br.readLine();
            } catch (IOException e) {
                System.out.println("Error 1");
            }

        }
        @Override
        public synchronized void run() {
            try {
                BufferedReader br = null;
                PrintWriter out = null;
            while (true) {
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    String ID_Str = null;
                    //System.out.println(br);
                    if ((ID_Str = br.readLine()) == null || ID_Str.equals("."))
                        break;
                    //System.out.println("ID After Recieving" + ID_Str);
                    int ID = Integer.parseInt(ID_Str);

                    String timeslot_Str = null;
                    timeslot_Str = br.readLine();
                    //System.out.println("timeslot After Recieving" + timeslot_Str);
                    int timeslot = Integer.parseInt(timeslot_Str);

                    int DONE;
                    //System.out.println("Hellooooo");
                    String message=null;

                if (socket.getLocalPort() == MAKE) {
                        DONE = hospital.MakeAppointment(ID, timeslot, patient_name);
                        if     (DONE==0)message="The Appointment has been done successfully ";
                        else if(DONE==1)message="Failure ::there is no doctor with this id";
                        else if(DONE==2)message="Failure ::not valid time slot ";
                        else if(DONE==3)message="Failure ::already reserved";

                    }else {
                        DONE = hospital.CancelAppointment(ID, timeslot, patient_name);
                        if(DONE==0)message="The Appointment has been canceled successfully ";
                        else if(DONE==1)message="Failure ::there is no doctor with this id";
                        else if(DONE==2)message="Failure ::not valid time slot ";
                        else if(DONE==3)message="Failure ::Not found the appointment to cancel";
                        else if(DONE==4)message="Failure ::Patient name is not identical";
                        }
                    out.println(message);
                    hospital.Print();

                }
            out.close();
            br.close();
            socket.close();

            }
                catch (IOException e) {
                }

        }

    }
}
