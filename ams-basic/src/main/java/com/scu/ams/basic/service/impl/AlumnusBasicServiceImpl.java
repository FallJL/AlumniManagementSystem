package com.scu.ams.basic.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.AlumnusBasicDao;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;


@Service("alumnusBasicService")
public class AlumnusBasicServiceImpl extends ServiceImpl<AlumnusBasicDao, AlumnusBasicEntity> implements AlumnusBasicService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AlumnusBasicDao alumnusBasicDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AlumnusBasicEntity> page = this.page(
                new Query<AlumnusBasicEntity>().getPage(params),
                new QueryWrapper<AlumnusBasicEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<AlumnusBasicEntity> alumniDataView(AlumnusBasicEntity alumnusBasicEntity) {
        QueryWrapper<AlumnusBasicEntity> queryWrapper = new QueryWrapper<>();
        if(alumnusBasicEntity.getGender() != null && alumnusBasicEntity.getGender() != ' '){
            queryWrapper.eq("gender", alumnusBasicEntity.getGender());
        }
        if (alumnusBasicEntity.getIdCard() != null && !alumnusBasicEntity.getIdCard().equals(" ")){
            queryWrapper.eq("id_card",alumnusBasicEntity.getIdCard());
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void sendBirthDayMail(List<AlumnusBasicEntity> alumnusBasicEntityList) {
        String fromEmail = "1796899275@qq.com";
        String subject = "生日快乐";
        String messageText = "祝您生日快乐！";

        List<String> toEmails = new ArrayList<>();
        for (AlumnusBasicEntity alumnusBasicEntity : alumnusBasicEntityList) {
            toEmails.add(alumnusBasicEntity.getEmail());
        }

        SimpleMailMessage[] messages = new SimpleMailMessage[toEmails.size()];
        for (int i = 0; i < toEmails.size(); i++) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromEmail);
            msg.setTo(toEmails.get(i));
            msg.setSubject(subject);
            msg.setText(messageText);
            messages[i] = msg;
        }

        // 批量发送邮件
        javaMailSender.send(messages);
    }

    @Override
    public void sendInformMail(List<AlumnusBasicEntity> alumnusBasicEntityList, String information) {
        String fromEmail = "1796899275@qq.com";
        String subject = "四川大学校友通知！";

        List<String> toEmails = new ArrayList<>();
        for (AlumnusBasicEntity alumnusBasicEntity : alumnusBasicEntityList) {
            toEmails.add(alumnusBasicEntity.getEmail());
        }

        SimpleMailMessage[] messages = new SimpleMailMessage[toEmails.size()];
        for (int i = 0; i < toEmails.size(); i++) {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromEmail);
            msg.setTo(toEmails.get(i));
            msg.setSubject(subject);
            msg.setText(information);
            messages[i] = msg;
        }

        // 批量发送邮件
        javaMailSender.send(messages);
    }

    @Override
    public PageUtils queryPageWrapper(AlumnusBasicEntity alumnusBasicEntity) {
        QueryWrapper<AlumnusBasicEntity> queryWrapper = new QueryWrapper<>();
        if(alumnusBasicEntity.getGender() != null && alumnusBasicEntity.getGender() != ' '){
            queryWrapper.eq("gender", alumnusBasicEntity.getGender());
        }
        if (alumnusBasicEntity.getIdCard() != null && !alumnusBasicEntity.getIdCard().equals(" ")){
            queryWrapper.eq("id_card",alumnusBasicEntity.getIdCard());
        }
        List<AlumnusBasicEntity> resultList = alumnusBasicDao.selectList(queryWrapper);
        // 创建Page对象，并设置查询结果和分页信息
        Page<AlumnusBasicEntity> page = new Page<>();
        page.setRecords(resultList);
        page.setTotal(resultList.size());

        // 返回PageUtils对象，将查询结果和分页信息进行封装
        return new PageUtils(page);
    }

}
