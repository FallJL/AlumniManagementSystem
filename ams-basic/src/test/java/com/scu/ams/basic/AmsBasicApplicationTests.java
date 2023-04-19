package com.scu.ams.basic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scu.ams.basic.entity.AlumnusBasicEntity;
import com.scu.ams.basic.service.AlumnusBasicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AmsBasicApplicationTests {

	@Autowired
	AlumnusBasicService alumnusBasicService;

	@Test
	public void contextLoads() {
		AlumnusBasicEntity alumnusBasicEntity = new AlumnusBasicEntity();
		//测试插入功能
//		alumnusBasicEntity.setAluId("1");
//		alumnusBasicEntity.setAluName("wanghuan");
//		alumnusBasicEntity.setCity("成都");
//		alumnusBasicService.save(alumnusBasicEntity);
//		System.out.println("保存成功");

//		测试修改功能
//		alumnusBasicEntity.setId(1L);
//		alumnusBasicEntity.setAluFormerName("李四");
//		alumnusBasicService.updateById(alumnusBasicEntity);

		//测试查询功能
		List<AlumnusBasicEntity> list = alumnusBasicService.list(new QueryWrapper<AlumnusBasicEntity>().eq("alu_id", "1"));
		list.forEach((item)->{
			System.out.println(item);
		});
	}

}
