package life.majiang.community.service;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
//        UserExample userExample = new UserExample();
//        userExample.createCriteria()
//                   .andAccountIdEqualTo(user.getAccountId());
//        List<User> users = userMapper.selectByExample(userExample);
        User dbUser = userMapper.findByAccountId(user.getAccountId());//从数据库中获取到用户Id
        //判断现在登录的Id是否在数据库中，
//        if (users.size() == 0)
        if (dbUser == null){
            //不在，插入
            user.setGmtCreate(System.currentTimeMillis());//获取当前时间戳
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //在，更新。获取新值
//            User dbUser = users.get(0);
//            User updateUser = new User();
//            updateUser.setGmtModified(System.currentTimeMillis());
//            updateUser.setAvatarUrl(user.getAvatarUrl());
//            updateUser.setName(user.getName());
//            updateUser.setToken(user.getToken());
//            UserExample example = new UserExample();
//            example.createCriteria()
//                   .andAccountIdEqualTo(dbUser.getId());
//            userMapper.updateByExampleSelective(updateUser,example);
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);//更新
        }
    }
}
