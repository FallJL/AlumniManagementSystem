package com.scu.ams.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.ams.basic.vo.AlumnusBasicVo;
import com.scu.common.utils.PageUtils;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    AlumnusBasicVo info(Long id);

//    PageUtils listRandom(Map<String, Object> params);

//    String uploadPortrait(MultipartFile file) throws IOException;
//
//    R deletePortrait(AlumnusBasicEntity alumnusBasic);
//
//    FileInputStream portraitImg(AlumnusBasicEntity alumnusBasic) throws FileNotFoundException;

}

