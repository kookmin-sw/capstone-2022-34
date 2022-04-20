package com.capstone.momomeal.api;

import com.capstone.momomeal.domain.Category;
import com.capstone.momomeal.domain.Menu;
import com.capstone.momomeal.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuApiController {
    private final MenuService menuService;

    @ResponseBody
    @RequestMapping(value = "addMenu.do",method = RequestMethod.POST)
    public HashMap<String, Object> addMenu(@RequestBody HashMap<String, Object> map){
        HashMap<String, Object> returnMap = new HashMap<>();

        Menu menu = new Menu();
        menu.setName((String)map.get("name"));
        menu.setCategory((Category)map.get("category"));
        menu.setX_value((Double)map.get("x"));
        menu.setY_value((Double)map.get("y"));
        Long check = menuService.AddMenu(menu);

        if(check == null){
            returnMap.put("check",0);
        }else{
            returnMap.put("check",1);
        }
        return returnMap;
    }

    @ResponseBody
    @RequestMapping(value = "getMenu.do",method = RequestMethod.POST)
    public HashMap<String, Object> getMenuList(){
        HashMap<String, Object> returnMap = new HashMap<>();
        List<Menu> menuList = menuService.getMenus();

        returnMap.put("menuList",menuList);
        return returnMap;
    }
}
