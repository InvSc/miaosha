package com.invsc.miaosha.service;

import com.invsc.miaosha.dao.MiaoshaUserDao;
import com.invsc.miaosha.domain.MiaoshaUser;
import com.invsc.miaosha.result.CodeMsg;
import com.invsc.miaosha.utils.MD5Util;
import com.invsc.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoshaUserService {
    @Autowired
    MiaoshaUserDao miaoshaUserDao;
    public MiaoshaUser getById(long id){
        return miaoshaUserDao.getById(id);
    }

    public CodeMsg login(LoginVo loginVo) {
        if(loginVo == null) {
            return CodeMsg.SERVER_ERROR;
        }
        String formMobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        // 判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(formMobile));
        if (user == null) {
            return CodeMsg.MOBILR_NOT_EXIST;
        }
        // 验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}
