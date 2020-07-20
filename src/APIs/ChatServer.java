package APIs;


import Entity.Message;
import Entity.User;
import IService.IMessageService;
import Service.MessageService;
import Utility.DataStreamIO;
import Utility.ObjectStreamIO;
import Utility.ServerUtils;
import Utility.StreamIO;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {

    /* Setting up variables */
    private static final int PORT = 9001;
    private static final HashMap<User,StreamIO> usersSessions = new HashMap<>();
    private static final IMessageService messageService = new MessageService();

    public static void main(String[] args) throws Exception {
        ServerUtils.register("User", User.class);
        ServerUtils.register("Message", Message.class);
        ServerSocket listener = new ServerSocket(PORT);

        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            listener.close();
        }
    }


    private static class Handler extends Thread {
        private String name;
        private final Socket socket;
        private User user;
        private OutputStream os;
        private InputStream is;
        private StreamIO io;
        public Handler(Socket socket) throws IOException {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                is = socket.getInputStream();
                os = socket.getOutputStream();
                user = (User) ServerUtils.readObject(new DataInputStream(is));
                System.out.println(user);
                System.out.println(user.getSalt());
                if("D".equals(user.getSalt()))
                    io = new ObjectStreamIO(is, os);
                else
                    io = new DataStreamIO(is, os);
                usersSessions.put(user, io);
                while (socket.isConnected()) {
                    Message inputmsg = (Message) io.readObject();
                    if (inputmsg != null) {
                        messageService.insertMessage(inputmsg);
                        write(inputmsg);
                    }
                }
            } catch (SocketException ex) {
            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
            }  finally {
                closeConnections();
            }
        }
        
        private void write(Message message) throws IOException {
            StreamIO io = usersSessions.get(message.getReceiver());
            if(io != null)
            {
                System.out.println(message);
                io.writeObject(message);
                return;
            }
            NotificationApi.createMessageNotification(message);
        }       
        private synchronized void closeConnections() {
            
            if (io != null){
                usersSessions.remove(user);
                try {
                    io.close();
                } catch (IOException ex) {
                    Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (is != null){
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
            if (os != null){
                try {
                    os.close();
                } catch (IOException ex) {
                }
            }
        }
    }
}
