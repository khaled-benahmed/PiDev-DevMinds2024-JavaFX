package Mytest;

import entities.Allergie;
import entities.Plat;
import services.AllergieServices;
import services.PlatServices;

public class MyTest2 {
    public static void main(String[] args) {
        Allergie a = new Allergie(10,"enaa","helloworld");
        AllergieServices ps =  new AllergieServices();
         ps.addEntity(a);
        // System.out.println("before editing");
        //  System.out.println(ps.getAllData());
        //a.setId(1);
        //  ps.DeleteEntity(p);
       // ps.updateEntity(p);
        //  System.out.println("after editing");
        System.out.println(ps.getAllData());
    }
    }

