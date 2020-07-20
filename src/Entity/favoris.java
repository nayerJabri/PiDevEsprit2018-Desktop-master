/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Objects;

/**
 *
 * @author ASUS
 */
public class favoris {
    
    
    public int id;
    private Object User;
    private Object Post;

    @Override
    public String toString() {
        return "favoris{" + "id=" + id + ", User=" + User + ", Post=" + Post + '}';
    }

    public favoris(int id, Object User, Object Post) {
        this.id = id;
        this.User = User;
        this.Post = Post;
    }

    public favoris(Object User, Object Post) {
        this.User = User;
        this.Post = Post;
    }

    
 
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.User);
        hash = 79 * hash + Objects.hashCode(this.Post);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final favoris other = (favoris) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.User, other.User)) {
            return false;
        }
        if (!Objects.equals(this.Post, other.Post)) {
            return false;
        }
        return true;
    }

    
    
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getUser() {
        return User;
    }

    public void setUser(Object User) {
        this.User = User;
    }

    public Object getPost() {
        return Post;
    }

    public void setPost(Object Post) {
        this.Post = Post;
    }
    
    
    
    
    
}
