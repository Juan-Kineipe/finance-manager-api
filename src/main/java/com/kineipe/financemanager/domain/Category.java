package com.kineipe.financemanager.domain;

import com.kineipe.financemanager.domain.enums.CategoryTypeEnum;
import jakarta.persistence.*;


@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    @Enumerated(EnumType.STRING)
    private CategoryTypeEnum type;

    public Category() {}

    public Category(Long id, String name, CategoryTypeEnum type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryTypeEnum getType() {
        return type;
    }

    public void setType(CategoryTypeEnum type) {
        this.type = type;
    }
}
