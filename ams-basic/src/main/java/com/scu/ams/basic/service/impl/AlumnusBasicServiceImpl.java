package com.scu.ams.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

@Slf4j
@Service("alumnusBasicService")
public class AlumnusBasicServiceImpl extends ServiceImpl<AlumnusBasicDao, AlumnusBasicEntity> implements AlumnusBasicService {

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
    public AlumnusBasicVo info(Long id) {
        AlumnusBasicEntity entity = this.baseMapper.selectById(id);

        AlumnusBasicVo vo = new AlumnusBasicVo(); // vo比entity少了password字段
        BeanUtils.copyProperties(entity, vo);

        return vo;
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