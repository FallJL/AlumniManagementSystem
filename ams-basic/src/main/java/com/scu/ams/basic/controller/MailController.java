package com.scu.ams.basic.controller;

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

import java.util.List;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    @Autowired
    private AlumnusBasicService alumnusBasicService;

    @RequestMapping("/sendBirthDayMail")
    public R sendBirthDayMail(@RequestBody List<AlumnusBasicEntity> alumnusBasicEntityList){
        alumnusBasicService.sendBirthDayMail(alumnusBasicEntityList);
        return R.ok();
    }

    public static class InformMailRequest {
        private List<AlumnusBasicEntity> alumnusBasicEntity;
        private String information;

        // 构造函数、Getter和Setter方法

        public InformMailRequest(List<AlumnusBasicEntity> alumnusBasicEntity, String information) {
            this.alumnusBasicEntity = alumnusBasicEntity;
            this.information = information;
        }
        public InformMailRequest(){

        }

        public List<AlumnusBasicEntity> getAlumnusBasicEntity() {
            return alumnusBasicEntity;
        }

        public String getInformation() {
            return information;
        }

        public void setAlumnusBasicEntity(List<AlumnusBasicEntity> alumnusBasicEntity) {
            this.alumnusBasicEntity = alumnusBasicEntity;
        }

        public void setInformation(String information) {
            this.information = information;
        }
    }
    @RequestMapping("/sendInformMail")
    public R sendInformMail(@RequestBody InformMailRequest request){
        List<AlumnusBasicEntity> alumnusBasicEntityList = request.getAlumnusBasicEntity();
        String information = request.getInformation();
        alumnusBasicService.sendInformMail(alumnusBasicEntityList, information);
        return R.ok();
    }

}
