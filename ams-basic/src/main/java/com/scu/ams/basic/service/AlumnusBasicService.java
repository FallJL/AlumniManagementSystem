package com.scu.ams.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.ams.basic.vo.EnterprisePropertyVO;
import com.scu.ams.basic.vo.GraduationVO;
import com.scu.ams.basic.vo.MajorVO;
import com.scu.ams.basic.vo.NationalityVO;
import com.scu.common.utils.PageUtils;
import com.scu.ams.basic.entity.AlumnusBasicEntity;

import javax.servlet.http.HttpServletResponse;
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

    PageUtils queryPageWrapper(AlumnusBasicEntity alumnusBasicEntity);

    PageUtils listRandom(Map<String, Object> params);

    void sendBirthDayMails(Long[] ids);

    void export(AlumnusBasicEntity alumnusBasicEntity, HttpServletResponse response);

    List<EnterprisePropertyVO> enterpriseChart();

    PageUtils test();

    List<NationalityVO> nationalityChart();

    List<MajorVO> majorChart();

    List<GraduationVO> graduationChart();
}

