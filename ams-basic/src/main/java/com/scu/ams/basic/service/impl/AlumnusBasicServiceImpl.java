package com.scu.ams.basic.service.impl;
import com.scu.ams.basic.dto.PageDTO;
import com.scu.ams.basic.vo.*;
import com.scu.ams.basic.utils.ExcelUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.http.HttpStatus;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.ParseException;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import java.util.stream.Collectors;

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

@RefreshScope // 动态获取nacos的配置
@Slf4j
@Service("alumnusBasicService")
public class AlumnusBasicServiceImpl extends ServiceImpl<AlumnusBasicDao, AlumnusBasicEntity> implements AlumnusBasicService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private AlumnusBasicService alumnusBasicService;
    @Value("${ams.basic.initpwd}")
    private String initpwd; // 校友用户的初始密码
    @Value("${ams.basic.initsalt}")
    private String initsalt; // 校友用户的初始密码对应的盐

    @Autowired
    private AlumnusBasicDao alumnusBasicDao;
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
        String fromEmail = "874085669@qq.com";
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
    @Async("taskExecutor") // 使用异步线程/线程池优化
    public void sendInformMail(Long[] ids, String information) {
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

    @Override
    public PageUtils queryPageWrapper(AlumusQueryVO alumnusBasicEntity)  {
        QueryWrapper<AlumnusBasicEntity> queryWrapper = new QueryWrapper<>();
        String nativePlace = alumnusBasicEntity.getNativePlace();
        String city = alumnusBasicEntity.getCity();
        String query = alumnusBasicEntity.getQuery();
        if (nativePlace != null) {
            if (nativePlace.contains("省")) {
                int index = nativePlace.indexOf("省");
                if(nativePlace.contains("市")) {
                    int index1 = nativePlace.indexOf("市");
                    String address = nativePlace.substring(index,index1);
                    alumnusBasicEntity.setNativePlace(address);
                }
                else{
                    String extractedString = nativePlace.substring(0, index);
                    alumnusBasicEntity.setNativePlace(extractedString);
                }
            }
        }
        if (city != null) {
            if (city.contains("省")) {
                int index = city.indexOf("省");
                if(city.contains("市")) {
                    int index1 = city.indexOf("市");
                    String address = city.substring(index+1,index1);
                    alumnusBasicEntity.setCity(address);
                }
                else{
                    String extractedString = city.substring(0, index);
                    alumnusBasicEntity.setCity(extractedString);
                }
            }
        }
        if (alumnusBasicEntity.getQuery() != null && !alumnusBasicEntity.getQuery().equals("")) {
            queryWrapper.and(wrapper -> wrapper
                    .like("alu_name", alumnusBasicEntity.getQuery())
                    .or()
                    .like("work_unit", alumnusBasicEntity.getQuery())
                    .or()
                    .like("alu_id", alumnusBasicEntity.getQuery())
                    .or()
                    .like("job_title", alumnusBasicEntity.getQuery())
            );
        }
        if(alumnusBasicEntity.getGender() != null && alumnusBasicEntity.getGender() != ' '){
            queryWrapper.eq("gender", alumnusBasicEntity.getGender());
        }
        if (alumnusBasicEntity.getIdCard() != null){
            queryWrapper.eq("id_card",alumnusBasicEntity.getIdCard());
        }
        if (alumnusBasicEntity.getNationality() != null && !alumnusBasicEntity.getNationality().equals("")){
            queryWrapper.eq("nationality",alumnusBasicEntity.getNationality());
        }
        if (alumnusBasicEntity.getPoliticalStatus() != null && !alumnusBasicEntity.getPoliticalStatus().equals("")){
            queryWrapper.eq("political_status",alumnusBasicEntity.getPoliticalStatus());
        }
        if (alumnusBasicEntity.getNativePlace() != null && !alumnusBasicEntity.getNativePlace().equals("")){
            queryWrapper.like("native_place",alumnusBasicEntity.getNativePlace());
        }
//        if (alumnusBasicEntity.getAluFormerName() != null && !alumnusBasicEntity.getAluFormerName().equals("")){
//            queryWrapper.like("alu_former_name",alumnusBasicEntity.getAluFormerName());
//        }
        if (alumnusBasicEntity.getClazz() != null && !alumnusBasicEntity.getClazz().equals("")){
            queryWrapper.eq("clazz",alumnusBasicEntity.getClazz());
        }
        if (!StringUtils.isEmpty(alumnusBasicEntity.getGraduationTime())){
            queryWrapper.like("graduation_time",alumnusBasicEntity.getGraduationTime());
        }
        if (alumnusBasicEntity.getMajor() != null && !alumnusBasicEntity.getMajor().equals("")){
            queryWrapper.eq("major",alumnusBasicEntity.getMajor());
        }
        if (alumnusBasicEntity.getDegreeStage() != null){
            queryWrapper.eq("degree_stage",alumnusBasicEntity.getDegreeStage());
        }
        if (city != null && !city.equals("")){
            queryWrapper.like("city", alumnusBasicEntity.getCity());
        }
        if (alumnusBasicEntity.getEnterpriseProperty() != null && !alumnusBasicEntity.getEnterpriseProperty().equals("")) {
            if (alumnusBasicEntity.getEnterpriseProperty().equals("其他")) {
                queryWrapper.notIn("enterprise_property", "国有企业", "民营企业","个体独资企业","三资企业","私营企业");
            } else {
                queryWrapper.eq("enterprise_property", alumnusBasicEntity.getEnterpriseProperty());
            }
        }
        // 不要把password查询出来
        queryWrapper.select(AlumnusBasicEntity.class, column -> !column.getColumn().equals("password"));

        Map<String, Object> map = new HashMap<>();
        map.put("page", alumnusBasicEntity.getPage());
        map.put("limit", alumnusBasicEntity.getLimit());
        IPage<AlumnusBasicEntity> page = this.page(
                new Query<AlumnusBasicEntity>().getPage(map),
                queryWrapper
        );
//        List<AlumnusBasicEntity> resultList = alumnusBasicDao.selectList(queryWrapper);
//        // 创建Page对象，并设置查询结果和分页信息
//        Page<AlumnusBasicEntity> page = new Page<>();
//        page.setRecords(resultList);
//        page.setTotal(resultList.size());

        // 返回PageUtils对象，将查询结果和分页信息进行封装
        return new PageUtils(page);
    }

    @Override
    public void inport(AlumnusBasicEntity alumnusBasicEntity) {
        String graduationTime = alumnusBasicEntity.getGraduationTime();
        String admissionTime = alumnusBasicEntity.getAdmissionTime();

        if (admissionTime != null && !Objects.equals(admissionTime, "")){
            alumnusBasicEntity.setAdmissionTime(admissionTime.substring(0,10));
        }
        if (graduationTime != null && !Objects.equals(graduationTime, "")){
            alumnusBasicEntity.setGraduationTime(graduationTime.substring(0,10));
        }

        String idCard = alumnusBasicEntity.getIdCard();
        String rawPassword = initpwd;
        if (idCard.length() >= 6) {
            // 用身份证后6位初始化密码
            rawPassword = idCard.substring(idCard.length() - 6);
        }

        // 使用shiro的md5加盐加密，迭代加密3次
        Md5Hash md5Hash = new Md5Hash(rawPassword, initsalt, 3);
        String password = md5Hash.toHex();
        alumnusBasicEntity.setPassword(password);

        alumnusBasicService.save(alumnusBasicEntity);
    }

    @Override
    public AlumnusBasicVo info(Long id) {
        AlumnusBasicEntity entity = this.baseMapper.selectById(id);

        AlumnusBasicVo vo = new AlumnusBasicVo(); // vo比entity少了password字段
        BeanUtils.copyProperties(entity, vo);

        return vo;
    }

    @Override
    public void cover(AlumnusBasicEntity alumnusBasic) {
        String graduationTime = alumnusBasic.getGraduationTime();
        String admissionTime = alumnusBasic.getAdmissionTime();

        if (admissionTime != null && !Objects.equals(admissionTime, "")){
            alumnusBasic.setAdmissionTime(admissionTime.substring(0,10));
        }
        if (graduationTime != null && !Objects.equals(graduationTime, "")){
            alumnusBasic.setGraduationTime(graduationTime.substring(0,10));
        }
        System.out.println("被调用了");
        baseMapper.updateByAluId(alumnusBasic.getAluId(),alumnusBasic);
    }

    @Override
    public Integer getStatusById(String aluId) {
        QueryWrapper<AlumnusBasicEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("alu_id", aluId).select("alu_status");
        AlumnusBasicEntity entity = this.baseMapper.selectOne(wrapper);
        if (entity == null) return null;
        return entity.getAluStatus();
    }

    @Override
    public int selectStatusById(Long id) {
        AlumnusBasicEntity entity = this.baseMapper.selectById(id);
        return entity.getAluStatus();
    }

    @Override
    public AlumnusBasicEntity login(AlumnusLoginVo vo) {
        // 得到vo的学号（或手机号）、密码
        String voLoginAccount = vo.getLoginAccount();
        String voPassword = vo.getPassword();

        // 在数据库中查询信息
        QueryWrapper<AlumnusBasicEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("alu_id", voLoginAccount).or().eq("phone_num", voLoginAccount); // 用学号或手机号都可以
        AlumnusBasicEntity entity = this.baseMapper.selectOne(wrapper);

        // 如果能查到，则检验密码是否正确
        if (entity != null) {
            String password = entity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean matches = passwordEncoder.matches(voPassword, password); // 密码匹配，数据库中存的是加密后的密码

            if (matches) {
                entity.setPassword(null); // 不能返回密码
                return entity;
            }
        }

        // 如果查不到或密码错误，则登录失败
        return null;
    }

    @Override
    public AlumnusBasicEntity getByAluId(String aluId) {
        QueryWrapper<AlumnusBasicEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("alu_id", aluId);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public R updatePassword(String aluId, UpdatePasswordVo updatePasswordVo) {
        String password = updatePasswordVo.getPassword();
        String newPassword = updatePasswordVo.getNewPassword();
//        String confirmPassword = updatePasswordVo.getConfirmPassword();
//        // 判断新密码和确认密码是否一致
//        if(!newPassword.equals(confirmPassword)){
//            return R.error("新密码和确认密码不一致");
//        }
        // 获取数据库的校友信息
        AlumnusBasicEntity entity = this.getByAluId(aluId);
        // 判断原密码与数据库的密码是否一致. Shiro采用md5加盐加密，迭代加密3次，盐暂固定为"scusalt"
        Md5Hash md5Hash = new Md5Hash(password, initsalt, 3);
        if(!md5Hash.toHex().equals(entity.getPassword())){
            return R.error("原密码不正确");
        }

        // 密码长度在8到20个字符之间
        if (newPassword.length() < 8 || newPassword.length() > 20) {
            return R.error("新密码长度需要在8到20个字符之间");
        }
        // 是否包含一位数字
        String regNumber = ".*\\d+.*";
        // 是否包含一位小写字母
        String regLowercase = ".*[a-z]+.*";
        // 是否包含一位大写字母
        String regUppercase = ".*[A-Z]+.*";
        // 是否包含一位特殊字符
        String regCharacter = ".*[^a-zA-Z0-9]+.*";
        if (!newPassword.matches(regNumber)) {
            return R.error("新密码需要包含数字");
        } else if (!newPassword.matches(regLowercase)) {
            return R.error("新密码需要包含小写字母");
        } else if (!newPassword.matches(regUppercase)) {
            return R.error("新密码需要包含大写字母");
        } else if (!newPassword.matches(regCharacter)) {
            return R.error("新密码需要包含特殊字符");
        }

        // 如果密码无误，则可以进行更新
        UpdateWrapper<AlumnusBasicEntity> updateWrapper = new UpdateWrapper<>();
        Md5Hash md5HashNew = new Md5Hash(newPassword, initsalt, 3);
        updateWrapper.set("password", md5HashNew.toHex()).eq("alu_id", aluId);
        this.baseMapper.update(null, updateWrapper);
        return R.ok("修改密码成功");
    }

    @Override
    public int selectById(AlumnusBasicEntity alumnusBasicEntity) {
        int exist = 1;
        String aluId = alumnusBasicEntity.getAluId();
        if(aluId == null || aluId.equals("")){
            exist = 0;
        }
        else {
            QueryWrapper<AlumnusBasicEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("alu_id", aluId);
            int count = this.baseMapper.selectCount(wrapper);
//            System.out.println(count);
            if(count == 0){
                exist = 0;
            }
        }
        return exist;
    }

    @Override
    public void resetAlumnusPassword(List<Long> ids) {
        List<AlumnusBasicEntity> users = this.baseMapper.selectBatchIds(ids);
        List<String> idCards = users.stream()
                .map(AlumnusBasicEntity::getIdCard)
                .collect(Collectors.toList());

        List<String> passwords = idCards.stream()
                .map(this::initPassword)
                .collect(Collectors.toList());

        for (int i = 0; i < ids.size(); i++) {
            UpdateWrapper<AlumnusBasicEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", ids.get(i)).set("password", passwords.get(i));
            this.baseMapper.update(null, updateWrapper);
        }
    }

    public String initPassword(String idCard) {
        String password = initpwd;
        if (idCard.length() >= 6) {
            // 用身份证后6位初始化密码
            password = idCard.substring(idCard.length() - 6);
        }

        // 使用shiro的md5加盐加密，迭代加密3次
        Md5Hash md5Hash = new Md5Hash(password, initsalt, 3);
        return md5Hash.toHex();
    }

    @Override
    public void sendBirthDayMails(Long[] ids) {
        String fromEmail = "874085669@qq.com";
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

        long otherCount = 0; // 额外变量用于记录其他的count值

        for (Map<String, Object> map : lists) {
            String key = (String) map.get("key");
            Long value = (Long) map.get("value");

            if (!key.equals("国有企业") && !key.equals("民营企业") && !key.equals("个体独资企业")
                    && !key.equals("三资企业") && !key.equals("私营企业")) {
                otherCount += value; // 累加其他的count值
            } else {
                EnterprisePropertyVO property = new EnterprisePropertyVO();
                property.setEnterpriseProperty(key);
                property.setCount(value);
                enterpriseProperties.add(property);
            }
        }

        // 添加其他情况的EnterprisePropertyVO
        if (otherCount > 0) {
            EnterprisePropertyVO enterprisePropertyVO = new EnterprisePropertyVO();
            enterprisePropertyVO.setEnterpriseProperty("其他");
            enterprisePropertyVO.setCount(otherCount);
            enterpriseProperties.add(enterprisePropertyVO);
        }

        System.out.println(enterpriseProperties);
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://202.115.43.57:3306/ams_basic?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai", "root", "123456");

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


            GraduationVO graduationVO = new GraduationVO();
            graduationVO.setGraduation(key);
            graduationVO.setCount(value);
            graduationVOS.add(graduationVO);
        }

        return graduationVOS;
    }
    @Override
    public List<NativePlaceVO> nativePlaceChart() {
        List<Map<String, Object>> lists = baseMapper.nativePlaceCount();
        List<NativePlaceVO> nativePlaceVOS = new ArrayList<>();

        for (Map<String, Object> map : lists) {
            String key = (String) map.get("key");
            Long value = (Long) map.get("value");


            NativePlaceVO nativePlaceVO = new NativePlaceVO();
            nativePlaceVO.setNativePlace(key);
            nativePlaceVO.setCount(value);
            nativePlaceVOS.add(nativePlaceVO);
        }
        return nativePlaceVOS;
    }

    @Override
    public List<CityVO> cityChart() {
        List<Map<String, Object>> lists = baseMapper.cityCount();
        List<CityVO> cityVOS = new ArrayList<>();

        for (Map<String, Object> map : lists) {
            String key = (String) map.get("key");
            Long value = (Long) map.get("value");


            CityVO cityVO = new CityVO();
            cityVO.setCity(key);
            cityVO.setCount(value);
            cityVOS.add(cityVO);
        }
        return cityVOS;
    }

    @Override
    public List<DegreeStageVO> degreeStageChart() {
        List<Map<Integer, Object>> lists = baseMapper.degreeStageCount();
        List<DegreeStageVO> degreeStageVOS = new ArrayList<>();

        for (Map<Integer, Object> map : lists) {
            Integer key = (Integer) map.get("key");
            Long value = (Long) map.get("value");


            DegreeStageVO degreeStageVO = new DegreeStageVO();
            degreeStageVO.setDegreeStage(key);
            degreeStageVO.setCount(value);
            degreeStageVOS.add(degreeStageVO);
        }
        return degreeStageVOS;
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

//    }

