package com.scu.ams.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.common.utils.PageUtils;
import com.scu.common.utils.Query;

import com.scu.ams.basic.dao.AuditDetailDao;
import com.scu.ams.basic.entity.AuditDetailEntity;
import com.scu.ams.basic.service.AuditDetailService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("auditDetailService")
public class AuditDetailServiceImpl extends ServiceImpl<AuditDetailDao, AuditDetailEntity> implements AuditDetailService {

    @Autowired
    AlumnusBasicService alumnusBasicService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 如果key不为空，则需要进行模糊匹配，用wrapper即可
        String key = (String) params.get("key");
        LambdaQueryWrapper<AuditDetailEntity> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            wrapper.and(i -> i.like(AuditDetailEntity::getAluId, key).or().like(AuditDetailEntity::getAluName, key));
        }

        // 如果params中有审核状态信息，则还要查审核状态
        String status = (String) params.get("status");
        if(!StringUtils.isEmpty(status)){
            wrapper.eq(AuditDetailEntity::getStatus, Integer.parseInt(status));
        }

        IPage<AuditDetailEntity> page = this.page(
                new Query<AuditDetailEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void apply(AuditDetailEntity auditDetail) {
        // 审核状态设为“待审核”(0)，然后插入数据库
        auditDetail.setStatus(0);
        this.save(auditDetail);
    }

    @Override
    public AuditDetailEntity infoByAuditId(Long auditId) {
        QueryWrapper<AuditDetailEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("audit_id", auditId);

        AuditDetailEntity auditDetail = this.baseMapper.selectOne(wrapper);
        return auditDetail;
    }

    /**
     * 审核通过
     * @param ids
     */
    @Transactional
    @Override
    public void auditPass(List<Long> ids) {
        // 1. 将auditDetail表的status字段更新为1（审核通过）
        UpdateWrapper<AuditDetailEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        updateWrapper.eq("status", 0);
        updateWrapper.set("status", 1);
        this.baseMapper.update(null, updateWrapper);

        // 2. 查找这些ids在auditDetail中的所有信息（为什么不前端直接传数据？因为前端数据不一定可信）
        List<AuditDetailEntity> details = this.baseMapper.selectBatchIds(ids);

        // 3. 更新alumnusBasic
        List<AlumnusBasicEntity> list = details.stream().map((detail) -> {
            AlumnusBasicEntity alumnusBasic = new AlumnusBasicEntity();
            BeanUtils.copyProperties(detail, alumnusBasic);
            alumnusBasic.setCreateTime(null);
            alumnusBasic.setUpdateTime(null);
            alumnusBasic.setId(detail.getAlumnusBasicId());

            return alumnusBasic;
        }).collect(Collectors.toList());
        alumnusBasicService.updateBatchById(list);
    }

    @Override
    public Map<String, Object> infoAndBasic(Long id) {
        AuditDetailEntity auditDetail = this.getById(id);
        AlumnusBasicEntity alumnusBasic = alumnusBasicService.getById(auditDetail.getAlumnusBasicId());
        alumnusBasic.setPassword(null); // 不能传密码

        Map<String, Object> map = new HashMap<>();
        map.put("auditDetail", auditDetail);
        map.put("alumnusBasic", alumnusBasic);
        return map;
    }

    /**
     * 审核不通过
     * @param ids
     */
    @Override
    public void auditNotPass(List<Long> ids) {
        // 将auditDetail表的status字段更新为2（审核不通过）
        UpdateWrapper<AuditDetailEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        updateWrapper.eq("status", 0);
        updateWrapper.set("status", 2);
        this.baseMapper.update(null, updateWrapper);
    }

    @Override
    public List<AuditDetailEntity> getListByAluId(String aluId) {
        QueryWrapper<AuditDetailEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("alu_id", aluId);
        List<AuditDetailEntity> list = this.baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public void auditRepeal(List<Long> ids) {
        // 将auditDetail表的status字段更新为3（撤销审核）
        UpdateWrapper<AuditDetailEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", ids);
        updateWrapper.eq("status", 0);
        updateWrapper.set("status", 3);
        this.baseMapper.update(null, updateWrapper);
    }

}