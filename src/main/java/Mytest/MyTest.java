package Mytest;

import entities.Plat;
import services.PlatServices;

public class MyTest {
    public static void main(String[] args) {
        //Plat p = new Plat("salem","12","69","sarra");
        PlatServices ps =  new PlatServices();
       // ps.addEntity(p);
        // System.out.println("before editing");
      //  System.out.println(ps.getAllData());
      //   p.setId_p(8);
        //  ps.DeleteEntity(p);
       // ps.updateEntity(p);
        //  System.out.println("after editing");
         System.out.println(ps.getAllData());
    }
    }

