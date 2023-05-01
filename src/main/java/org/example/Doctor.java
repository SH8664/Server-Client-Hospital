package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Doctor{

    int id;

    String name;

    //indicating if the doc is available every timeslot or has appointment
    List<Boolean>timeslots=new ArrayList<>();

    //the patient’s name of each time slot if he had an appointment with the doctor in the corresponding timeslot.
    List<String> patients=new ArrayList<>();
    Doctor()
    {
        for(int i=0;i<24;i++){
        timeslots.add(false);
        patients.add(null);
        }
    }

}
