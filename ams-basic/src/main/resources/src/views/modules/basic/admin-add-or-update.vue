<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="管理员名" prop="adminName">
      <el-input v-model="dataForm.adminName" placeholder="管理员名"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="adminPassword">
      <el-input v-model="dataForm.adminPassword" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item label="权限" prop="adminAuthority">
      <el-input v-model="dataForm.adminAuthority" placeholder="权限"></el-input>
    </el-form-item>
    <el-form-item label="注册时间" prop="adminCreateTime">
      <el-input v-model="dataForm.adminCreateTime" placeholder="注册时间"></el-input>
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
          adminName: '',
          adminPassword: '',
          adminAuthority: '',
          adminCreateTime: ''
        },
        dataRule: {
          adminName: [
            { required: true, message: '管理员名不能为空', trigger: 'blur' }
          ],
          adminPassword: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          adminAuthority: [
            { required: true, message: '权限不能为空', trigger: 'blur' }
          ],
          adminCreateTime: [
            { required: true, message: '注册时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/basic/admin/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.adminName = data.admin.adminName
                this.dataForm.adminPassword = data.admin.adminPassword
                this.dataForm.adminAuthority = data.admin.adminAuthority
                this.dataForm.adminCreateTime = data.admin.adminCreateTime
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
              url: this.$http.adornUrl(`/basic/admin/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'adminName': this.dataForm.adminName,
                'adminPassword': this.dataForm.adminPassword,
                'adminAuthority': this.dataForm.adminAuthority,
                'adminCreateTime': this.dataForm.adminCreateTime
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
