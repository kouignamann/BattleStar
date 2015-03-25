/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.kouignamann.battlestar.core.controlers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guillaume BERTRAND
 */
public class Controlers {
    
    private static Controlers instance;
    
    private final List<Listener> listeners;
    
    private Controlers() {
        super();
        listeners = new ArrayList<>();
    }
    
    private static void checkInstance() {
        if (instance == null) {
            instance = new Controlers();
        }
    }
    
    public static void init() {
        Controlers.checkInstance();
        instance.listeners.clear();
        instance.listeners.add(new KeyboardListener());
        instance.listeners.add(new MouseListener());
    }
    
    public static void listen() {
        Controlers.checkInstance();
        instance.listeners.stream().forEach(Listener::listen);
    }
}
