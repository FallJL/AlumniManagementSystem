package com.scu.ams.basic.controller;

import com.scu.ams.basic.dto.InformMailRequestDTO;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import com.scu.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/basic/mail")
public class MailController {
    @Autowired
    private AlumnusBasicService alumnusBasicService;

//    @RequestMapping("/sendBirthDayMail")
//    public R sendBirthDayMail(@RequestBody Long id){
//        alumnusBasicService.sendBirthDayMail(id);
//        return R.ok();
//    }
//    @RequestMapping("/sendBirthDayMails")
//    public R sendBirthDayMails(@RequestBody Long[] ids){
//        alumnusBasicService.sendBirthDayMails(ids);
//        return R.ok();
//    }

}
