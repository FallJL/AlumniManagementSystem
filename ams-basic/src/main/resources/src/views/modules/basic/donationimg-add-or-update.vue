<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="外键-捐赠新闻表" prop="donationId">
      <el-input v-model="dataForm.donationId" placeholder="外键-捐赠新闻表"></el-input>
    </el-form-item>
    <el-form-item label="图片名" prop="donationImgName">
      <el-input v-model="dataForm.donationImgName" placeholder="图片名"></el-input>
    </el-form-item>
    <el-form-item label="图片地址" prop="donationImgUrl">
      <el-input v-model="dataForm.donationImgUrl" placeholder="图片地址"></el-input>
    </el-form-item>
    <el-form-item label="顺序" prop="donationImgSort">
      <el-input v-model="dataForm.donationImgSort" placeholder="顺序"></el-input>
    </el-form-item>
    <el-form-item label="是否默认图（0为不默认，1为默认）" prop="donationImgDefault">
      <el-input v-model="dataForm.donationImgDefault" placeholder="是否默认图（0为不默认，1为默认）"></el-input>
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
          donationId: '',
          donationImgName: '',
          donationImgUrl: '',
          donationImgSort: '',
          donationImgDefault: ''
        },
        dataRule: {
          donationId: [
            { required: true, message: '外键-捐赠新闻表不能为空', trigger: 'blur' }
          ],
          donationImgName: [
            { required: true, message: '图片名不能为空', trigger: 'blur' }
          ],
          donationImgUrl: [
            { required: true, message: '图片地址不能为空', trigger: 'blur' }
          ],
          donationImgSort: [
            { required: true, message: '顺序不能为空', trigger: 'blur' }
          ],
          donationImgDefault: [
            { required: true, message: '是否默认图（0为不默认，1为默认）不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/basic/donationimg/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.donationId = data.donationImg.donationId
                this.dataForm.donationImgName = data.donationImg.donationImgName
                this.dataForm.donationImgUrl = data.donationImg.donationImgUrl
                this.dataForm.donationImgSort = data.donationImg.donationImgSort
                this.dataForm.donationImgDefault = data.donationImg.donationImgDefault
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
              url: this.$http.adornUrl(`/basic/donationimg/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'donationId': this.dataForm.donationId,
                'donationImgName': this.dataForm.donationImgName,
                'donationImgUrl': this.dataForm.donationImgUrl,
                'donationImgSort': this.dataForm.donationImgSort,
                'donationImgDefault': this.dataForm.donationImgDefault
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
