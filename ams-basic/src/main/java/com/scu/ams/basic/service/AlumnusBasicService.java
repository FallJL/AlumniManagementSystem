package com.scu.ams.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.ams.basic.vo.*;
import com.scu.common.utils.PageUtils;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 校友的常用基本信息
 *
 * @author wanghuan
 * @email 1796899275@qq.com
 * @date 2023-04-18 14:01:11
 */
public interface AlumnusBasicService extends IService<AlumnusBasicEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void sendBirthDayMail(Long id);

    void sendInformMail(Long[] ids, String information);

    PageUtils queryPageWrapper(AlumusQueryVO alumusQueryVO);


//    PageUtils listRandom(Map<String, Object> params);

    void sendBirthDayMails(Long[] ids);

    void export(AlumnusBasicEntity alumnusBasicEntity, HttpServletResponse response);

    List<EnterprisePropertyVO> enterpriseChart();

    PageUtils test();

    List<NationalityVO> nationalityChart();

    List<MajorVO> majorChart();

    List<GraduationVO> graduationChart();
    AlumnusBasicVo info(Long id);


    void inport(AlumnusBasicEntity alumnusBasicEntity);

    List<NativePlaceVO> nativePlaceChart();

    List<DegreeStageVO> degreeStageChart();

    List<CityVO> cityChart();


    AlumnusBasicEntity login(AlumnusLoginVo vo);

    AlumnusBasicEntity getByAluId(String aluId);

    R updatePassword(String aluId, UpdatePasswordVo updatePasswordVo);

    void resetAlumnusPassword(List<Long> ids);
//    PageUtils listRandom(Map<String, Object> params);

//    String uploadPortrait(MultipartFile file) throws IOException;
//
//    R deletePortrait(AlumnusBasicEntity alumnusBasic);
//
//    FileInputStream portraitImg(AlumnusBasicEntity alumnusBasic) throws FileNotFoundException;

}

