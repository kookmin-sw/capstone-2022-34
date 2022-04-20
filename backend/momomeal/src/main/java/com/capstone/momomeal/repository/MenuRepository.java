package com.capstone.momomeal.repository;

import com.capstone.momomeal.domain.Category;
import com.capstone.momomeal.domain.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuRepository {

    private final EntityManager em;

    public Menu findById(Long id){
        return em.find(Menu.class,id);
    }

    public void save(Menu menu){
        em.persist(menu);
    }

    public List<Menu> findAll(){
        List<Menu> menuList = em.createQuery("select m from Menu m", Menu.class)
                .getResultList();
        return menuList;
    }

    public List<Menu> findByName(String name){
        List<Menu> menuList = em.createQuery("select m from Menu m where m.name = :name",Menu.class)
                .setParameter("name",name)
                .getResultList();
        return menuList;
    }

    public List<Menu> findByCategory(Category category){
        List<Menu> menuList = em.createQuery(
                "select m from Menu m where m.category = : category",Menu.class)
                .setParameter("category",category)
                .getResultList();
        return menuList;
    }
}
