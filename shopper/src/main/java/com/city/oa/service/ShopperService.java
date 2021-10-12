package com.city.oa.service;

import cn.hutool.json.JSONUtil;
import com.city.oa.dao.IShopperDao;
import com.city.oa.model.CommoditySale;
import com.city.oa.model.Question;
import com.city.oa.model.Shopper;
import com.city.oa.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class ShopperService {

    @Resource
    IShopperDao dao;


    @Resource
    RedisUtil redis;

    public void register(Shopper shopper) {
        shopper.setCredit(100);
        shopper.setPower(1);
        shopper.setPassword(shopper.getPassword()==null?"":shopper.getPassword());
        shopper.setPassword(getMD5(shopper.getPassword()));
        dao.addShopper(shopper);
    }

    public Shopper update(Shopper shopper,HttpServletRequest request) {
        Shopper old = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        if (shopper.getName()!=null&&!shopper.getName().equals("")) old.setName(shopper.getName());
        if (shopper.getNickName()!=null&&!shopper.getNickName().equals("")) old.setNickName(shopper.getNickName());
        if (shopper.getAge()!=null&&shopper.getAge()!=0) old.setAge(shopper.getAge());
        if (shopper.getPhone()!=null&&!shopper.getPhone().equals("")) old.setPhone(shopper.getPhone());
        if (shopper.getEmail()!=null&&!shopper.getEmail().equals("")) old.setEmail(shopper.getEmail());
        if (shopper.getAddress()!=null&&!shopper.getAddress().equals("")) old.setAddress(shopper.getAddress());
        if (shopper.getGender()!=null&&!shopper.getGender().equals("")) old.setGender(shopper.getGender());
        dao.updateShopper(old);
        redis.set(request.getHeader("token"), JSONUtil.toJsonStr(old), Duration.ofMinutes(30L));
        return old;
    }

    public String logon(Integer id, String password, HttpServletRequest request) {
        String old = request.getHeader("token");
        old = old==null?"":old;
        if (redis.ttl(old)>0) return "";
        password = getMD5(password);
        int i = dao.verifyPassword(id, password);
        if (i!=0){
            Shopper shopper = dao.selectById(id);
            String token = UUID.randomUUID().toString();
            redis.set(token, JSONUtil.toJsonStr(shopper), Duration.ofMinutes(30L));
            return token;
        }else
            return null;
    }

    public String logonByPhone(String phone, String password, HttpServletRequest request) {
        String old = request.getHeader("token");
        old = old==null?"":old;
        if (redis.ttl(old)>0) return "";
        password = getMD5(password);
        int i = dao.verifyPasswordByPhone(phone, password);
        if (i!=0){
            Shopper shopper = dao.selectByPhone(phone);
            String token = UUID.randomUUID().toString();
            redis.set(token, JSONUtil.toJsonStr(shopper), Duration.ofMinutes(30L));
            return token;
        }else
            return null;
    }

    public boolean step1(Integer id,String name, String email, String phone) {
        Shopper shopper = dao.selectById(id);
        System.out.println(shopper.toString());
        return name.equals(shopper.getName()) && email.equals(shopper.getEmail()) && phone.equals(shopper.getPhone());
    }
    public boolean haveQuestion(Integer id){
        return dao.haveQuestion(id)==1;
    }


    public boolean step2(Integer id,String answer) {
        return dao.verifyAnswer(id, answer) == 1;
    }

    public void step3(Integer id,String newPassword) {
        System.out.println(newPassword);
        newPassword = getMD5(newPassword);
        dao.updatePassword(id,newPassword);
    }

    public List<CommoditySale> getCollectList(HttpServletRequest request,Integer row,Integer page) {
        Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        return dao.selectCollectByShopperId(shopper.getId(),row*(page-1),row);
    }

    public void addCollect(HttpServletRequest request, Integer commodityId) {
        Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        dao.addToCollect(shopper.getId(),commodityId);
    }

    public void deleteCollect(HttpServletRequest request, Integer commodityId) {
        Shopper shopper = JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
        dao.deleteFromCollect(shopper.getId(),commodityId);
    }

    public String getMD5(String string) {
        StringBuilder str = new StringBuilder();
        char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F'};
        byte[] b = string.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        byte[] md5 = md.digest(b);
        for (byte m : md5) {
            str.append(chars[(m >> 4) & 0x0f]);
            str.append(chars[m & 0x0f]);
        }
        return str.substring(0,10);
    }

    public void resetQuestion(Integer id, Integer questionId, String answer) {
        dao.updateQuestion(id,questionId,answer);
    }

    public List<Question> questionList(Integer row, Integer page) {
        return dao.getListQuestion(row*(page-1),row);
    }

    public Question getQuestionById(Integer id) {
        return dao.getQuestionById(id);
    }

    public void logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        redis.del(token);
    }

    public Shopper getDetail(HttpServletRequest request) {
        return JSONUtil.parseObj(redis.get(request.getHeader("token"))).toBean(Shopper.class);
    }
}
