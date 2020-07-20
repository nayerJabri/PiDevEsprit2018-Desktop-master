package Entity;


import java.sql.Date;
import java.util.Objects;


public class Category {
    private int id_category;
    private String date_ajout;
    private String libelle;
    private String description;
    private String image;

    public Category() {
    }

    public Category(String date_ajout, String libelle, String description, String image) {
        this.date_ajout = date_ajout;
        this.libelle = libelle;
        this.description = description;
        this.image = image;
    }

    
    
    public Category(int id_category) {
        this.id_category = id_category;
    }

    public Category(int id_category,String libelle, String description,String date_ajout) {
        this.id_category = id_category;
        this.date_ajout = date_ajout;
        this.libelle = libelle;
        this.description = description;
    }
    
    

    public Category(int id_category, String date_ajout, String libelle, String description, String image) {
        this.id_category = id_category;
        this.date_ajout = date_ajout;
        this.libelle = libelle;
        this.description = description;
        this.image = image;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(String date_ajout) {
        this.date_ajout = date_ajout;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Category{" + "id_category=" + id_category + ", date_ajout=" + date_ajout + ", libelle=" + libelle + ", description=" + description + ", image=" + image + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id_category;
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
        final Category other = (Category) obj;
        if (this.id_category != other.id_category) {
            return false;
        }
        if (!Objects.equals(this.libelle, other.libelle)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.date_ajout, other.date_ajout)) {
            return false;
        }
        return true;
    }
    
    

  

    
}

