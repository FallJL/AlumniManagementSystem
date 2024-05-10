package com.scu.ams.basic.consumer;

import com.scu.ams.basic.service.AlumnusBasicService;
import com.scu.ams.basic.vo.EmailInfoVo;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.scu.ams.basic.config.MQConfig.*;

/**
 * 用RabbitMQ发送邮件，将@Component注释说明并未采用，实际采用的是线程池去发送邮件
 */
// @Component
public class EmailConsumer {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AlumnusBasicService alumnusBasicService;

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = EMAIL_ROUTE_EXCHANGE, type = DIRECT),
                    key = {EMAIL_NOTIFICATION})})
    public void sendEmail(EmailInfoVo emailInfoVo) {
        Long[] ids = emailInfoVo.getIds();
        String information = emailInfoVo.getInformation();

        String fromEmail = "874085669@qq.com";
        String subject = "四川大学化学与工程学院校友通知！";

        List<String> toEmails = new ArrayList<>();
        for (Long id : ids) {
            toEmails.add(alumnusBasicService.getById(id).getEmail());
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
}
