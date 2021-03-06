/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import Hilos.RecibirMsjServidor;
import Modelo.ArrayComunicacionSockets;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tati
 */
public class Comunicacion {

    private DataOutputStream output;
    private DataInputStream input;
    private ObjectOutputStream outputObj;
    private ObjectInputStream inputObj;
    private Socket comunicacion;
    private RecibirMsjServidor hiloS;
    private String nombre;
    private int opcionChat = -1;
    private ArrayList<String> chatGrupal;
    private ArrayComunicacionSockets arrayC = ArrayComunicacionSockets.getInstance();
    private  ArrayList<Integer> arrayN;
    
    public Comunicacion(Socket comunicacion) {
        this.comunicacion = comunicacion;
    }

    public void runComunucacion() {
        try {
            getStreams();
            System.out.println("asdfhhsd");
            hiloS = new RecibirMsjServidor(input, inputObj, this);
            hiloS.start();
        } catch (IOException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InetAddress getAddress() {
        return comunicacion.getInetAddress();
    }

    private void getStreams() throws IOException {
        output = new DataOutputStream(comunicacion.getOutputStream());
        output.flush();
        input = new DataInputStream(comunicacion.getInputStream());
        inputObj = new ObjectInputStream(comunicacion.getInputStream());
        outputObj = new ObjectOutputStream(comunicacion.getOutputStream());
        outputObj.flush();
        System.out.println("getStream");
    }

    public void cerrarConexion() {
        try {
            output.close();
            input.close();
            outputObj.close();
            inputObj.close();
            comunicacion.close();
        } catch (IOException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarMensaje(String mensaje) throws IOException {
        output.writeUTF(mensaje);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOpcionChat() {
        return nombre;
    }

    public void setOpcionChat(int opcionChat) {
        this.opcionChat = opcionChat;
    }

    public void inicializarArrayChatGrupal() {
        chatGrupal = new ArrayList<>();
    }

    public void nullearChatGrupal() {
        chatGrupal = null;
    }
    
    public void enviarListaNombres() throws IOException {
        outputObj.writeObject(chatGrupal);
    }
    
    public void cargarListaNombres() {
        chatGrupal = arrayC.nombresDeClientes();
    }
    
    public ArrayList<String> getChatGrupal() {
        return chatGrupal;
    }
    
    public void setArrayN(ArrayList<Integer> arrayN) {
        this.arrayN = arrayN;
    }
}
