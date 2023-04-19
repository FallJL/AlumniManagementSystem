<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="标题" prop="donationTitle">
      <el-input v-model="dataForm.donationTitle" placeholder="标题"></el-input>
    </el-form-item>
    <el-form-item label="新闻内容" prop="donationContent">
      <el-input v-model="dataForm.donationContent" placeholder="新闻内容"></el-input>
    </el-form-item>
    <el-form-item label="捐赠需求" prop="donationDemand">
      <el-input v-model="dataForm.donationDemand" placeholder="捐赠需求"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="donationStatus">
      <el-input v-model="dataForm.donationStatus" placeholder="状态"></el-input>
    </el-form-item>
    <el-form-item label="发布时间" prop="donationReleaseTime">
      <el-input v-model="dataForm.donationReleaseTime" placeholder="发布时间"></el-input>
    </el-form-item>
    <el-form-item label="更新时间" prop="donationUpdateTime">
      <el-input v-model="dataForm.donationUpdateTime" placeholder="更新时间"></el-input>
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
          donationTitle: '',
          donationContent: '',
          donationDemand: '',
          donationStatus: '',
          donationReleaseTime: '',
          donationUpdateTime: ''
        },
        dataRule: {
          donationTitle: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          donationContent: [
            { required: true, message: '新闻内容不能为空', trigger: 'blur' }
          ],
          donationDemand: [
            { required: true, message: '捐赠需求不能为空', trigger: 'blur' }
          ],
          donationStatus: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          donationReleaseTime: [
            { required: true, message: '发布时间不能为空', trigger: 'blur' }
          ],
          donationUpdateTime: [
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
              url: this.$http.adornUrl(`/basic/donation/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.donationTitle = data.donation.donationTitle
                this.dataForm.donationContent = data.donation.donationContent
                this.dataForm.donationDemand = data.donation.donationDemand
                this.dataForm.donationStatus = data.donation.donationStatus
                this.dataForm.donationReleaseTime = data.donation.donationReleaseTime
                this.dataForm.donationUpdateTime = data.donation.donationUpdateTime
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
              url: this.$http.adornUrl(`/basic/donation/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'donationTitle': this.dataForm.donationTitle,
                'donationContent': this.dataForm.donationContent,
                'donationDemand': this.dataForm.donationDemand,
                'donationStatus': this.dataForm.donationStatus,
                'donationReleaseTime': this.dataForm.donationReleaseTime,
                'donationUpdateTime': this.dataForm.donationUpdateTime
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
