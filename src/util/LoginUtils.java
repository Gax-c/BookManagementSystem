package util;

import java.util.ArrayList;

import mysqloperation.SearchPassword;

public class LoginUtils {
    public boolean contains; // 用户名是否存在
    public String password; // 用户密码

    public void loginVer(String userName) {
        SearchPassword search = new SearchPassword(); 
        search.database(); 
        ArrayList<String> userid = search.userid;
        ArrayList<String> userpw = search.userpw;

        // 在用名中查找是否有与之匹配的用户
        contains = userid.contains(userName);
        if (contains == true){
            // 获取该用户名对应的下标
            int indexOf = userid.indexOf(userName);

            // 获取密码
            password = userpw.get(indexOf);
        }
    }
}
