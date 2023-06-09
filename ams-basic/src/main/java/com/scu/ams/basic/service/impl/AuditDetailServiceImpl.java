package com.scu.ams.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.scu.ams.basic.dao.AuditItemDao;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.entity.AuditItemEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import com.scu.ams.basic.service.AuditItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.AuditDetailDao;
import com.scu.ams.basic.entity.AuditDetailEntity;
import com.scu.ams.basic.service.AuditDetailService;
import org.springframework.transaction.annotation.Transactional;


@Service("auditDetailService")
public class AuditDetailServiceImpl extends ServiceImpl<AuditDetailDao, AuditDetailEntity> implements AuditDetailService {

    @Autowired
    AlumnusBasicService alumnusBasicService;

    @Autowired
    AuditItemService auditItemService;

    @Autowired
    AuditItemDao auditItemDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AuditDetailEntity> page = this.page(
                new Query<AuditDetailEntity>().getPage(params),
                new QueryWrapper<AuditDetailEntity>()
        );

        return new PageUtils(page);
    }
    @Transactional
    @Override
    public void apply(AuditDetailEntity auditDetail) {
        // 1.把auditDetail的id取出来（这实际上是校友表的id），根据这个id查校友表的信息
        Long alumnusId = auditDetail.getId();
        AlumnusBasicEntity alumnusBasic = alumnusBasicService.getById(alumnusId);

        // 2.new一个auditItem，把校友表查出的信息填充进去，然后插入数据库
        AuditItemEntity auditItem = new AuditItemEntity();
        auditItem.setAlumnusBasicId(alumnusId);
        auditItem.setAluId(alumnusBasic.getAluId());
        auditItem.setAluName(alumnusBasic.getAluName());
        auditItem.setStatus(0); // 0表示待审核
        auditItemService.save(auditItem);

        // 3.得到auditItem的id后，填充auditDetail，并将其id置为null，然后插入数据库
        Long auditItemId = auditItem.getId();
        auditDetail.setAuditId(auditItemId);
        auditDetail.setId(null);
        this.save(auditDetail);
    }

    @Override
    public AuditDetailEntity infoByAuditId(Long auditId) {
        QueryWrapper<AuditDetailEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("audit_id", auditId);

        AuditDetailEntity auditDetail = this.baseMapper.selectOne(wrapper);
        return auditDetail;
    }

    @Transactional
    @Override
    public void auditPass(AuditDetailEntity auditDetail) {
        // 1.将auditDetail的auditId对应的auditItem的状态更新为1（审核通过）
        UpdateWrapper<AuditItemEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", auditDetail.getAuditId()).set("status", 1);
        auditItemDao.update(null, updateWrapper);

        // 2.更新alumnusBasic
        AuditItemEntity auditItem = auditItemDao.selectById(auditDetail.getAuditId());
        Long alumnusBasicId = auditItem.getAlumnusBasicId();

        AlumnusBasicEntity alumnusBasic = new AlumnusBasicEntity();
        BeanUtils.copyProperties(auditDetail, alumnusBasic);
        alumnusBasic.setId(alumnusBasicId);

        alumnusBasicService.updateById(alumnusBasic);
    }

}