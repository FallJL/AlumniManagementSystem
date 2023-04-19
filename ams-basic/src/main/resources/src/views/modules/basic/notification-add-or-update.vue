<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="标题" prop="notifTitle">
      <el-input v-model="dataForm.notifTitle" placeholder="标题"></el-input>
    </el-form-item>
    <el-form-item label="通知内容" prop="notifContent">
      <el-input v-model="dataForm.notifContent" placeholder="通知内容"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="notifStatus">
      <el-input v-model="dataForm.notifStatus" placeholder="状态"></el-input>
    </el-form-item>
    <el-form-item label="发布时间" prop="notifCreateTime">
      <el-input v-model="dataForm.notifCreateTime" placeholder="发布时间"></el-input>
    </el-form-item>
    <el-form-item label="更新时间" prop="notifUpdateTime">
      <el-input v-model="dataForm.notifUpdateTime" placeholder="更新时间"></el-input>
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
          notifTitle: '',
          notifContent: '',
          notifStatus: '',
          notifCreateTime: '',
          notifUpdateTime: ''
        },
        dataRule: {
          notifTitle: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          notifContent: [
            { required: true, message: '通知内容不能为空', trigger: 'blur' }
          ],
          notifStatus: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          notifCreateTime: [
            { required: true, message: '发布时间不能为空', trigger: 'blur' }
          ],
          notifUpdateTime: [
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
              url: this.$http.adornUrl(`/basic/notification/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.notifTitle = data.notification.notifTitle
                this.dataForm.notifContent = data.notification.notifContent
                this.dataForm.notifStatus = data.notification.notifStatus
                this.dataForm.notifCreateTime = data.notification.notifCreateTime
                this.dataForm.notifUpdateTime = data.notification.notifUpdateTime
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
              url: this.$http.adornUrl(`/basic/notification/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'notifTitle': this.dataForm.notifTitle,
                'notifContent': this.dataForm.notifContent,
                'notifStatus': this.dataForm.notifStatus,
                'notifCreateTime': this.dataForm.notifCreateTime,
                'notifUpdateTime': this.dataForm.notifUpdateTime
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
