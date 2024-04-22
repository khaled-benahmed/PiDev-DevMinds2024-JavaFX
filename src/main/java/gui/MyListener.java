/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Activite;
import entities.Planning;


public interface MyListener {
    public void onClickListener(Activite act);
    public void onClickListener(Planning p);

}
