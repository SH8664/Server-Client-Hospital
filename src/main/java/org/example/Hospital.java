package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hospital {
    /*
         contains an array of doctors as a data member and the following:
    */
    List<Doctor>doctors=new ArrayList<>();

    Hospital() {
        //TODO::Constructor reads doctor’s data from file and fills the array of doctors
        try {
            File file = new File("Doctors_inf.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                String[] values = line.split(" ");

                Doctor DoctorObj=new Doctor();
                DoctorObj.id=Integer.parseInt(values[0]);
                DoctorObj.name = values[1];

                for (int i = 2; i < values.length; i++) {

                    int patient_appointment = Integer.parseInt(values[i]);
                    String patient_Name = values[++i];
                    //System.out.println(patient_appointment+"   "+patient_Name);
                    DoctorObj.timeslots.set(patient_appointment,true);
                    DoctorObj.patients.set(patient_appointment,patient_Name);
                }
                doctors.add(DoctorObj);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    /*
    b. MakeAppointment: takes the doctor id, the index of his time slot to reserve, and
    the patient’s name, and makes the appointment if possible. The function updates
    the doctor data members appropriately.
    * */
    int MakeAppointment(int id,int time_slot,String Patient_name)
    {
        Doctor Doc=GetDoctor(id);
        if(Doc==null)return 1; //Failure ::there is no doctor with this id
        if(!is_valid_time(time_slot))return 2; ///Failure ::not valid time slot
        if(Doc.timeslots.get(time_slot).equals(true))return 3; ///Failure ::already reserved
        // so we can reserve an appointment
            GetDoctor(id).timeslots.set(time_slot,true);
            GetDoctor(id).patients.set(time_slot,Patient_name);

        return 0;  //The Appointment has been done successfully

    }
    Boolean is_valid_time(int time)
    {
        if(time>=0 && time<24)
            return true;
        return false;
    }
    Doctor GetDoctor(int ID)
    {
        for(Doctor Doc :doctors)
        {
            if(Doc.id==ID)
                return Doc;
        }
        return null;
    }
    /*
     c. CancelAppointment: takes the doctor id, the index of his time slot to reserve,
    and the patient’s name, and cancels the appointment if possible. The function
    updates the doctor data members appropriately.
    * */
    int CancelAppointment(int id,int time_slot,String Patient_name)
    {
        Doctor Doc=GetDoctor(id);
        if(Doc==null) return 1;//Failure ::there is no doctor with this id
        if(!is_valid_time(time_slot))return 2; ///Failure ::not valid time slot
        if(Doc.timeslots.get(time_slot).equals(false))return 3; ///Failure ::Not found the appointment to cancel
        //so check if this patient is our cancelled patient
        if(!Doc.patients.get(time_slot).equals(Patient_name))return 4 ;//Failure ::Patient name is not identical
        GetDoctor(id).patients.set(time_slot,null);
        GetDoctor(id).timeslots.set(time_slot,false);
        return 0;//The Appointment has been canceled successfully
    }
    /*
    d. Print: for each doctor in the hospital, it prints the id of the doctor and the name of
    his patient in each time slot index
    * */
    void Print()
    {
        for(Doctor Doc:doctors)
        {
            System.out.print("ID : "+Doc.id+" Doctor "+Doc.name);
            for(int i=0;i<24;i++)
            {
                if(Doc.timeslots.get(i)==true) {
                    System.out.print(" has in Time " + i + " Patient " + Doc.patients.get(i)+",");
                }
            }
            System.out.println("");
        }
        System.out.println("*************************************************************");
    }
}
