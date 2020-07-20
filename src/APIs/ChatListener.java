package APIs;

import Controller.ChatController;
import Entity.Message;
import Entity.User;
import Utility.ServerUtils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatListener implements Runnable{

    private static Socket socket;
    private static String hostname;
    private static int port;
    private static String username;
    private static HashMap<User,ChatController> chatListcontrollers = new HashMap<>();
    private static ObjectOutputStream oos;
    private static InputStream is;
    private static ObjectInputStream input;
    private static OutputStream outputStream;
    private static User user;
    public ChatListener(String hostname, int port,User user) {
        ServerUtils.register("User", User.class);
        ChatListener.hostname = hostname;
        ChatListener.port = port;
        ChatListener.user = user;
        this.user.setSalt("D");
    }

    public static HashMap<User, ChatController> getChatListcontrollers() {
        return chatListcontrollers;
    }

    
    
    public static void addController(ChatController controller)
    {
        chatListcontrollers.put(controller.getChatUser(), controller);
    }
    
    public static void removeController(ChatController controller)
    {
        chatListcontrollers.remove(controller.getChatUser());
    }
    
    @Override
    public void run() {
        try {
            socket = new Socket(hostname, port);
            outputStream = socket.getOutputStream();
            ServerUtils.writeObject(ChatListener.user, new DataOutputStream(outputStream));
            oos = new ObjectOutputStream(outputStream);
            is = socket.getInputStream();
            input = new ObjectInputStream(is);
        } catch (IOException ex) {
            Logger.getLogger(ChatListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            while (socket.isConnected()) {
                Message message;
                message = (Message) input.readObject();
                if(message != null)
                {
                    System.out.println(message.getSender());
                    ChatController controller = chatListcontrollers.get(message.getSender());
                    if(controller != null)
                        controller.addToChat(message);
                    else
                        NotificationApi.createMessageNotification(message);
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
}
