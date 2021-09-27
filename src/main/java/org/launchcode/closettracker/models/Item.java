package org.launchcode.closettracker.models;

import org.launchcode.closettracker.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Item extends AbstractEntity{


    @NotNull
    @Column(name = "item_name", nullable = false)
    private String itemName;

    @NotNull
    @Column(name = "item_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "item_color", nullable = false)
    private Color color;

    @NotNull
    @Column(name = "item_size", nullable = false)
    private String size;

    @NotNull
    @Column(name = "item_season", nullable = false)
    private Season season;

    private Integer user_Id;

    @Lob
    private byte[] itemImage;

    public Item(String itemName, String type, Color color, String size, Season season, byte[] itemImage, Integer userId ) {
        this.itemName = itemName;
        this.type = type;
        this.color = color;
        this.size = size;
        this.season = season;
        this.itemImage = itemImage;
        this.user_Id = userId;
    }

    public Item() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public Integer getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(Integer user_Id) {
        this.user_Id = user_Id;
    }


}