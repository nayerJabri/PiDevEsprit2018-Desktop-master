package APIs;

import Controller.ChatController;
import Controller.MatchingController;
import Entity.Message;
import Entity.User;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MatchingListener implements Runnable{

    private static Socket socket;
    private static String hostname;
    private static int port;
    private static MatchingController matchingController;
    private static ObjectOutputStream oos;
    private static InputStream is;
    private static ObjectInputStream input;
    private static OutputStream outputStream;
    private static User user;
    
    public MatchingListener(String hostname, int port,User user) {
        MatchingListener.hostname = hostname;
        MatchingListener.port = port;
        MatchingListener.user = user;
    }

    public static void setController(MatchingController controller)
    {
        MatchingListener.matchingController = controller;
    }
    
    @Override
    public void run() {
        try {
            socket = new Socket(hostname, port);
            outputStream = socket.getOutputStream();
            oos = new ObjectOutputStream(outputStream);
            is = socket.getInputStream();
            input = new ObjectInputStream(is);
        } catch (IOException ex) {
            Logger.getLogger(ChatListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            oos.writeObject(MatchingListener.user);
            while (socket.isConnected()) {
                Message message;
                message = (Message) input.readObject();
                if(message != null)
                {
                    if(message.getId().equals(-2))
                    {
                        System.out.println("waiting");
                        matchingController.waiting();
                    }
                    else if(message.getId().equals(-3))
                    {
                        System.out.println("user confirmed");
                        System.out.println(message.getReceiver().getId());
                        matchingController.found(message.getReceiver());
                    }
                    else if(message.getId().equals(-4))
                    {
                        System.out.println("user found");
                        System.out.println(message.getSender().getId());
                        matchingController.found(message.getSender());
                    }
                    else if(message.getId().equals(-5))
                    {
                        System.out.println("user leaved");
                        matchingController.userLeaved();
                    }
                    else
                        matchingController.addToChat(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(ChatListener.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /* This method is used for sending a normal Message
     * @param msg - The message which the user generates
     */
    public static void send(Message message) throws IOException {
        oos.writeObject(message);
        oos.flush();
    }
    
    public static void sendRequest() throws IOException
    {
        Message message = new Message();
        message.setId(-1);
        message.setSender(user);
        send(message);
    }
    
    public static void sendStop() throws IOException
    {
        Message message = new Message();
        message.setId(-2);
        message.setSender(user);
        send(message);
    }
}
