package com.capstone.momomeal.service;

import com.capstone.momomeal.domain.Category;
import com.capstone.momomeal.domain.Menu;
import com.capstone.momomeal.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {
    MenuRepository menuRepository;

    //메뉴 추가
    public Long AddMenu(Menu menu){
        boolean checkDupleMenu = CheckDuplicateMenu(menu);
        if(checkDupleMenu == true){
            menuRepository.save(menu);
        }else{
            return null;
        }
        return menu.getId();
    }

    private boolean CheckDuplicateMenu(Menu menu) {
        List<Menu> menuList = menuRepository.findByName(menu.getName());
        if(menuList.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<Menu> getMenus(){
        List<Menu> menuList = menuRepository.findAll();
        return menuList;
    }

    @Transactional(readOnly = true)
    public List<Menu> getMenuByCategory(Category category){
        List<Menu> menuList = menuRepository.findByCategory(category);
        return menuList;
    }
}
