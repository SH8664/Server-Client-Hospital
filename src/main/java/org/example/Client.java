package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static String patient_name=null;
    public static final int MAKE = 6666;
    public static final int CANCEL = 6667;

    public static void main(String[] args) throws IOException {

        //TODO::Prompts the user to enter his name (the patient’s name)
        Scanner scanner = new Scanner(System.in);
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your name :");
        patient_name = consoleReader.readLine();
        //TODO::Open port to make appointment port 6666
        Socket Make_socket=new Socket("localhost",6666);
        //TODO::Open port to the cancel appointment port 6667
        Socket Cancel_socket=new Socket("localhost",6667);

        //TODO::Sends the patient’s name to both sockets
        PrintWriter out_make = new PrintWriter(Make_socket.getOutputStream(), true);
        PrintWriter out_cancel = new PrintWriter(Cancel_socket.getOutputStream(), true);
        out_make.println(patient_name);
        out_cancel.println(patient_name);

        // In a loop,
        while (true) {

            //TODO:: Prompts and asks the user if he wants to make or cancel an appointment

            System.out.println("Enter the port you want to connect to: (1) Make (2) Cancel appointment");
            BufferedReader socketReader =null;
            PrintWriter out=null;
            String message=null;
            if((message = consoleReader.readLine()) == null)
                break;
            if(Integer.parseInt(message) == 1) {
                //System.out.println("In make");
                socketReader = new BufferedReader(new InputStreamReader(Make_socket.getInputStream()));
                out=out_make;
            }
                else {
                //System.out.println("In Cancel");
                socketReader = new BufferedReader(new InputStreamReader(Cancel_socket.getInputStream()));
                out=out_cancel;
            }

            //System.out.println("Enter Doctor id:");
            //String id=consoleReader.readLine();
            //System.out.println("You Entered id : "+id);
            //System.out.println("Enter Doctor timeslot:");
            //String timeslot=consoleReader.readLine();
            //System.out.println("You Entered timeslot : "+timeslot);

            //TODO::Reads the doctor id and the timeslot index from the user
            String msg="Enter Doctor id:";
            String id =readNum(consoleReader,msg);
            out.println(id);

             msg="Enter Doctor timeslot:";
            String timeslot =readNum(consoleReader,msg);
            out.println(timeslot);

            //TODO::Prints the response message to the user on console
            try {
            String response= socketReader.readLine();
            System.out.println("the response from server is "+response);
            }
            catch (Exception e) {
             System.out.println("Eception in result");
            }
        }
        out_make.close();
        out_cancel.close();
        scanner.close();
        consoleReader.close();
    }
    private static String readNum(BufferedReader consoleReader,String msg){
        try {
            System.out.println(msg);
            String data=consoleReader.readLine();
            int trial= Integer.parseInt(data);
            return data;
        }
        catch (Exception e)
        {
            return readNum(consoleReader,msg);
        }
    }
}
