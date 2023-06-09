package com.scu.ams.basic.service.impl;
import com.scu.ams.basic.dto.PageDTO;
import com.scu.ams.basic.vo.EnterprisePropertyVO;
import com.scu.ams.basic.utils.ExcelUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.ams.basic.vo.GraduationVO;
import com.scu.ams.basic.vo.MajorVO;
import com.scu.ams.basic.vo.NationalityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.scu.ams.basic.vo.AlumnusBasicVo;
import com.scu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.AlumnusBasicDao;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@Slf4j
@Service("alumnusBasicService")
public class AlumnusBasicServiceImpl extends ServiceImpl<AlumnusBasicDao, AlumnusBasicEntity> implements AlumnusBasicService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AlumnusBasicDao alumnusBasicDao;
    @Autowired
    private AlumnusBasicService alumnusBasicService;
//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<AlumnusBasicEntity> page = this.page(
//                new Query<AlumnusBasicEntity>().getPage(params),
//                new QueryWrapper<AlumnusBasicEntity>()
//        );
//
//        return new PageUtils(page);
//    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 如果key不为空，则需要进行模糊匹配，用wrapper即可
        String key = (String) params.get("key");
        LambdaQueryWrapper<AlumnusBasicEntity> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            wrapper.like(AlumnusBasicEntity::getAluId, key).or().like(AlumnusBasicEntity::getAluName, key);
        }

        // 查询时排除password字段
        wrapper.select(AlumnusBasicEntity.class, info -> !info.getColumn().equals("password"));

        IPage<AlumnusBasicEntity> page = this.page(
                new Query<AlumnusBasicEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }


    @Override
    public void sendBirthDayMail(Long id) {
        String fromEmail = "1796899275@qq.com";
        String subject = "生日快乐";
        String messageText = "祝您生日快乐！";
        String email = alumnusBasicService.getById(id).getEmail();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(messageText);
        javaMailSender.send(msg);
    }

    @Override
    public void sendInformMail(Long[] ids, String information) {
        String fromEmail = "1796899275@qq.com";
        String subject = "四川大学校友通知！";

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

    @Override
    public PageUtils queryPageWrapper(AlumnusBasicEntity alumnusBasicEntity) {
        QueryWrapper<AlumnusBasicEntity> queryWrapper = new QueryWrapper<>();
        if(alumnusBasicEntity.getGender() != null && alumnusBasicEntity.getGender() != ' '){
            queryWrapper.eq("gender", alumnusBasicEntity.getGender());
        }
        if (alumnusBasicEntity.getIdCard() != null && !alumnusBasicEntity.getIdCard().equals("")){
            queryWrapper.eq("id_card",alumnusBasicEntity.getIdCard());
        }
        if (alumnusBasicEntity.getCity() != null && !alumnusBasicEntity.getCity().equals("")){
            queryWrapper.eq("city",alumnusBasicEntity.getCity());
        }
        if (alumnusBasicEntity.getAluId() != null && !alumnusBasicEntity.getAluId().equals("")){
            queryWrapper.eq("alu_id",alumnusBasicEntity.getCity());
        }
        if (alumnusBasicEntity.getAluName() != null && !alumnusBasicEntity.getAluName().equals("")){
            queryWrapper.like("alu_name",alumnusBasicEntity.getAluName());
        }
        if (alumnusBasicEntity.getAluFormerName() != null && !alumnusBasicEntity.getAluFormerName().equals("")){
            queryWrapper.like("alu_former_name",alumnusBasicEntity.getAluFormerName());
        }
        if (alumnusBasicEntity.getClazz() != null && !alumnusBasicEntity.getClazz().equals("")){
            queryWrapper.eq("clazz",alumnusBasicEntity.getClazz());
        }
        List<AlumnusBasicEntity> resultList = alumnusBasicDao.selectList(queryWrapper);
        // 创建Page对象，并设置查询结果和分页信息
        Page<AlumnusBasicEntity> page = new Page<>();
        page.setRecords(resultList);
        page.setTotal(resultList.size());

        // 返回PageUtils对象，将查询结果和分页信息进行封装
        return new PageUtils(page);
    }

    @Override
    public AlumnusBasicVo info(Long id) {
        AlumnusBasicEntity entity = this.baseMapper.selectById(id);

        AlumnusBasicVo vo = new AlumnusBasicVo(); // vo比entity少了password字段
        BeanUtils.copyProperties(entity, vo);

        return vo;
    }

    @Override
    public void sendBirthDayMails(Long[] ids) {
        String fromEmail = "1796899275@qq.com";
        String subject = "生日快乐";
        String messageText = "祝您生日快乐！";
        List<String> toEmails = new ArrayList<>();
        for (Long id:ids) {
            toEmails.add(alumnusBasicService.getById(id).getEmail());
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
    public void export(AlumnusBasicEntity alumnusBasicEntity, HttpServletResponse response) {
        Page<AlumnusBasicEntity> applicationVOPage= this.pageList(new PageDTO(1, -1), alumnusBasicEntity);

        ExcelUtil.exportAlumnus(applicationVOPage.getRecords(),response);
    }

    @Override
    public List<EnterprisePropertyVO> enterpriseChart() {
        List<Map<String, Object>> lists = baseMapper.enterpriseCount();
        List<EnterprisePropertyVO> enterpriseProperties = new ArrayList<>();

        for (Map<String, Object> map : lists) {
            String key = (String) map.get("key");
            Long value = (Long) map.get("value");

            // 使用key和value进行后续操作
            System.out.println("Key: " + key + ", Value: " + value);

            EnterprisePropertyVO property = new EnterprisePropertyVO();
            property.setEnterpriseProperty(key);
            property.setCount(value);
            enterpriseProperties.add(property);
        }

        return enterpriseProperties;
    }




    private Page<AlumnusBasicEntity> pageList(PageDTO pageDTO, AlumnusBasicEntity alumnusBasicEntity) {
        Page<AlumnusBasicEntity> alumnusBasicEntityPage=new Page<>(pageDTO.getPageNo(),pageDTO.getPageSize());
//        alumnusBasicEntityPage=baseMapper.myPage(alumnusBasicEntityPage,alumnusBasicEntity);

        return alumnusBasicEntityPage;
    }

    @Override
    public PageUtils test() {
        List<Map<String, Integer>> resultList = new ArrayList<>();

        try {
            // 建立数据库连接
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.141:13306/ams_basic?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai", "root", "123456");

            // 执行查询语句
            String sql = "SELECT enterprise_property, COUNT(*) AS count FROM alumnus_basic GROUP BY enterprise_property";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // 提取每一行的数据
                String enterpriseProperty = resultSet.getString("enterprise_property");
                int count = resultSet.getInt("count");

                // 构建键值对对象
                Map<String, Integer> resultMap = new HashMap<>();
                resultMap.put(enterpriseProperty, count);

                // 添加到结果列表
                resultList.add(resultMap);
            }

            // 关闭连接和资源
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int totalCount = resultList.size();
        int pageSize = 10;
        int currentPage = 1;

        // 创建 PageUtils 对象，将结果列表和分页信息放入其中

        return new PageUtils(resultList, totalCount, pageSize, currentPage);

    }

    @Override
    public List<NationalityVO> nationalityChart() {
        List<Map<String, Object>> lists = baseMapper.nationalityCount();
        List<NationalityVO> nationalityVOList = new ArrayList<>();

        for (Map<String, Object> map : lists) {
            String key = (String) map.get("key");
            Long value = (Long) map.get("value");

            // 使用key和value进行后续操作
            System.out.println("Key: " + key + ", Value: " + value);

            NationalityVO nationalityVO = new NationalityVO();
            nationalityVO.setNationality(key);
            nationalityVO.setCount(value);
            nationalityVOList.add(nationalityVO);
        }

        return nationalityVOList;
    }

    @Override
    public List<MajorVO> majorChart() {
        List<Map<String, Object>> lists = baseMapper.majorCount();
        List<MajorVO> majorVOS = new ArrayList<>();

        for (Map<String, Object> map : lists) {
            String key = (String) map.get("key");
            Long value = (Long) map.get("value");

            // 使用key和value进行后续操作
            System.out.println("Key: " + key + ", Value: " + value);

            MajorVO majorVO = new MajorVO();
            majorVO.setMajor(key);
            majorVO.setCount(value);
            majorVOS.add(majorVO);
        }

        return majorVOS;
    }
    @Override
    public List<GraduationVO> graduationChart() {
        List<Map<Integer, Object>> lists = baseMapper.graduationCount();
        List<GraduationVO> graduationVOS = new ArrayList<>();

        for (Map<Integer, Object> map : lists) {
            Integer key = (Integer) map.get("key");
            Long value = (Long) map.get("value");

            // 使用key和value进行后续操作
            System.out.println("Key: " + key + ", Value: " + value);

            GraduationVO graduationVO = new GraduationVO();
            graduationVO.setGraduation(key);
            graduationVO.setCount(value);
            graduationVOS.add(graduationVO);
        }

        return graduationVOS;
    }
}
//    @Override
//    public PageUtils listRandom(Map<String, Object> params) {
//        QueryWrapper<AlumnusBasicEntity> wrapper = new QueryWrapper<>();
//
//        String key = (String) params.get("key");
//        if(!StringUtils.isEmpty(key)){
//            wrapper.like("alu_name", key);
//        }
//        wrapper.orderByAsc("RAND()");
//
//        IPage<AlumnusBasicEntity> page = this.page(
//                new Query<AlumnusBasicEntity>().getPage(params),
//                wrapper
//        );
//        return new PageUtils(page);
//    }

//    @Value("${file-upload-path}")
//    private String fileUploadPath;
//    /**
//     * 把头像图片保存在本地文件夹中，返回url
//     */
//    @Override
//    public String uploadPortrait(MultipartFile file) throws IOException {
//        String url = storeFile(file, Paths.get(fileUploadPath, "image").toString());
//        return url;
//    }
//
//    @Override
//    public R deletePortrait(AlumnusBasicEntity alumnusBasic) {
//        // .\ams-basic\src\main\java\com\scu\ams\basic\file/image/202306/-0dc9d329785e43df84b0d753dee97efe.jpg
//        String fileUrl = Paths.get(fileUploadPath) + alumnusBasic.getPortraitUrl();
//
//        File file = new File(fileUrl);
//        if (file.delete()) { // 1.删除文件
//            // 2.将该校友的portrait_url属性置为null
//            UpdateWrapper<AlumnusBasicEntity> wrapper = new UpdateWrapper<>();
//            wrapper.set("portrait_url", null).eq("id", alumnusBasic.getId());
//            this.baseMapper.update(null, wrapper);
//            return R.ok();
//        } else {
//            return R.error("图片删除失败！请刷新后重试");
//        }
//    }
//
//    @Override
//    public FileInputStream portraitImg(AlumnusBasicEntity alumnusBasic) throws FileNotFoundException {
//        String fileUrl = Paths.get(fileUploadPath) + alumnusBasic.getPortraitUrl();
//        FileInputStream fis = new FileInputStream(fileUrl);
//        return fis;
//    }
//
//    // 在storeFile函数中使用
//    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMM");
//
//    /**
//     * 后缀名或empty："a.png" => ".png"
//     */
//    private static String suffix(String fileName) {
//        int i = fileName.lastIndexOf('.');
//        return i == -1 ? "" : fileName.substring(i);
//    }
//
//    /**
//     * 存储图片文件，将URL返回
//     */
//    private static String storeFile(MultipartFile file, String fileUploadPath) throws IOException {
//
//        String yearMonth = SDF.format(new Date());//当前年月
//        String random = UUID.randomUUID().toString().replaceAll("-","");//xxxx-xxxx-xxxx
//        String fileName = file.getOriginalFilename();//文件全名
//        String suffix = suffix(fileName);//文件后缀
//        //    /image/202111/-789.png
//        String relPath = "/" + yearMonth + "/" + "-" + random + suffix;
//        String toPath = fileUploadPath + relPath;
//        FileOutputStream out = null;
//
//        File toFile = new File(toPath).getParentFile();
//        if (!toFile.exists()) {
//            toFile.mkdirs(); //自动创建目录
//        }
//        try {
//            out = new FileOutputStream(toPath);
//            out.write(file.getBytes());
//            out.flush();
//
//            log.info(relPath);
//            return "/image" + relPath;
//        } catch (FileNotFoundException fnfe) {
//            throw fnfe;
//        } catch (IOException ioe) {
//            throw ioe;
//        } finally {
//            if (out != null) {
//                out.close();
//            }
//        }
//    }

}
