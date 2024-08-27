/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Usuario
 */
public class Observable {
    private final Collection<Observador> observadores;

    public Observable() {
        this.observadores = new ArrayList<>();
    }

    public void agregar(Observador o) {
        observadores.add(o);
    }

    public void remover(Observador o) {
        observadores.remove(o);
    }

    protected void avisar(Object evento) {
        for (Observador o : observadores) {
            o.actualizar(this, evento);
        }
    }
}
