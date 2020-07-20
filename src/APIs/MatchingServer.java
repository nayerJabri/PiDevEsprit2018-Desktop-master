package APIs;


import Entity.Message;
import Entity.User;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchingServer {

    /* Setting up variables */
    private static final int PORT = 9002;
    private static final HashMap<User,ObjectOutputStream> usersSessions = new HashMap<>();
    private static final Queue<User> userQueue = new ConcurrentLinkedQueue<User>();
    private static final HashMap<User,User> linkedUsers = new HashMap<>();
    public static void main(String[] args) throws Exception {
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
        private ObjectInputStream input;
        private OutputStream os;
        private ObjectOutputStream output;
        private InputStream is;
        
        public Handler(Socket socket) throws IOException {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                is = socket.getInputStream();
                input = new ObjectInputStream(is);
                os = socket.getOutputStream();
                output = new ObjectOutputStream(os);
                user = (User) input.readObject();
                usersSessions.put(user, output);

                while (socket.isConnected()) {
                    Message inputmsg = (Message) input.readObject();
                    if (inputmsg != null) {
                        if(inputmsg.getId().equals(-1))
                        {
                            breakUsers(inputmsg.getSender());
                            User found = searchUser(inputmsg.getSender());
                            if(found == null)
                                sendWait(inputmsg.getSender());
                            else
                            {
                                linkedUsers.put(inputmsg.getSender(), found);
                                linkedUsers.put(found, inputmsg.getSender());
                                sendConfirm(inputmsg.getSender(),found);
                                sendFound(found,inputmsg.getSender());
                            }
                        }
                        else if(inputmsg.getId().equals(-2))
                            breakUsers(inputmsg.getSender());
                        else
                            write(inputmsg);
                    }
                }
            } catch (SocketException ex) {
                
            } catch (IOException | ClassNotFoundException ex){
                
            } finally {
                closeConnections();
            }
        }
        
        private void breakUsers(User sender) throws IOException
        {
            User receiver = linkedUsers.get(sender);
            if(receiver == null)return;
            ObjectOutputStream writer = usersSessions.get(receiver);
            if(writer == null) return ;
            linkedUsers.remove(sender);
            linkedUsers.remove(receiver);
            Message message = new Message();
            message.setId(-5);
            writer.writeObject(message);
            writer.reset();
        }
        
        private void sendFound(User waiter,User requester) throws IOException
        {
            ObjectOutputStream writer = usersSessions.get(waiter);
            Message message = new Message();
            message.setSender(requester);
            message.setId(-4);
            writer.writeObject(message);
            writer.reset();
        }
        
        private void sendConfirm(User requester,User founded) throws IOException
        {
            ObjectOutputStream writer = usersSessions.get(requester);
            Message message = new Message();
            message.setReceiver(founded);
            message.setId(-3);
            writer.writeObject(message);
            writer.reset();
        }
        
        private void sendWait(User requester) throws IOException
        {
            ObjectOutputStream writer = usersSessions.get(requester);
            Message message = new Message();
            message.setId(-2);
            writer.writeObject(message);
            writer.reset();
        }
        
        private User searchUser(User requester)
        {
            User suser = userQueue.poll();
            if(suser == null)
            {
                userQueue.add(requester);
                return null;
            }
            if(user.getId().intValue() == suser.getId().intValue())
                return null;
            return suser;  
        }
        
        private void write(Message message) throws IOException {
            ObjectOutputStream writer = usersSessions.get(linkedUsers.get(message.getSender()));
            if(writer == null) return;
            writer.writeObject(message);
            writer.reset();
        }       
        private synchronized void closeConnections()  {
            try {
                breakUsers(user);
            } catch (IOException ex) {
                Logger.getLogger(MatchingServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (output != null){
                usersSessions.remove(user);
                try {
                    output.close();
                } catch (IOException ex) {
                    Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (is != null){
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (os != null){
                try {
                    os.close();
                } catch (IOException ex) {
                    Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (input != null){
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
