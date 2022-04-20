//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.capstone.momomeal.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;
    private String name;

    private Category category;

    @Column(name = "x_value")
    private Double x_value;

    @Column(name = "y_value")
    private Double y_value;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Members member;

//    @OneToMany(mappedBy = "menu")
//    private List<RecommendMenu> recommendMenu = new ArrayList();

    public Menu() {}

}
