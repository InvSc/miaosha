package com.invsc.miaosha.service;

import com.invsc.miaosha.dao.UserDao;
import com.invsc.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public User getById(int id) {
        return userDao.getById(id);
    }

//    @Transactional
    public boolean tx() {
        User u1 = new User(1, "tom");
        User u2 = new User(2, "marry");
        userDao.insert(u2);
        userDao.insert(u1);
        return true;
    }
}
