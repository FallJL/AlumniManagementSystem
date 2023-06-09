<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="校友编号id" prop="alumnusBasicId">
      <el-input v-model="dataForm.alumnusBasicId" placeholder="校友编号id"></el-input>
    </el-form-item>
    <el-form-item label="姓名" prop="aluName">
      <el-input v-model="dataForm.aluName" placeholder="姓名"></el-input>
    </el-form-item>
    <el-form-item label="学号" prop="aluId">
      <el-input v-model="dataForm.aluId" placeholder="学号"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="更新时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          alumnusBasicId: '',
          aluName: '',
          aluId: '',
          status: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          alumnusBasicId: [
            { required: true, message: '校友编号id不能为空', trigger: 'blur' }
          ],
          aluName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          aluId: [
            { required: true, message: '学号不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '更新时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/basic/audititem/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.alumnusBasicId = data.auditItem.alumnusBasicId
                this.dataForm.aluName = data.auditItem.aluName
                this.dataForm.aluId = data.auditItem.aluId
                this.dataForm.status = data.auditItem.status
                this.dataForm.createTime = data.auditItem.createTime
                this.dataForm.updateTime = data.auditItem.updateTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/basic/audititem/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'alumnusBasicId': this.dataForm.alumnusBasicId,
                'aluName': this.dataForm.aluName,
                'aluId': this.dataForm.aluId,
                'status': this.dataForm.status,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
