package org.launchcode.closettracker.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Size extends AbstractEntity {

    @NotBlank
    private String clothSize;

<<<<<<< HEAD
=======
    @OneToMany(mappedBy = "sizes")
    private final List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

>>>>>>> Image-upload
    public Size(String clothSize) {
        this.clothSize = clothSize;
    }

    public Size() {
    }

    public String getClothSize() {
        return clothSize;
    }

    public void setClothSize(String clothSize) {
        this.clothSize = clothSize;
    }
}
