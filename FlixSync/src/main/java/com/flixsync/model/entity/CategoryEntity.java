package com.flixsync.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Category")
@Table(name = "Category")
public class CategoryEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "movieCategories")
    private Set<MovieEntity> movies;

    public CategoryEntity(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return '{' +
                "id: " + id +
                ", name: '" + name + '\'' +
                '}';
    }
}
