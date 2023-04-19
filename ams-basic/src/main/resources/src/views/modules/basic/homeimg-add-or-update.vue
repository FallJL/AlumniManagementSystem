<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="图片名" prop="homeImgName">
      <el-input v-model="dataForm.homeImgName" placeholder="图片名"></el-input>
    </el-form-item>
    <el-form-item label="图片地址" prop="homeImgUrl">
      <el-input v-model="dataForm.homeImgUrl" placeholder="图片地址"></el-input>
    </el-form-item>
    <el-form-item label="发布时间" prop="homeImgCreateTime">
      <el-input v-model="dataForm.homeImgCreateTime" placeholder="发布时间"></el-input>
    </el-form-item>
    <el-form-item label="是否默认图（0为不默认，1为默认）" prop="homeImgDefault">
      <el-input v-model="dataForm.homeImgDefault" placeholder="是否默认图（0为不默认，1为默认）"></el-input>
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
          homeImgName: '',
          homeImgUrl: '',
          homeImgCreateTime: '',
          homeImgDefault: ''
        },
        dataRule: {
          homeImgName: [
            { required: true, message: '图片名不能为空', trigger: 'blur' }
          ],
          homeImgUrl: [
            { required: true, message: '图片地址不能为空', trigger: 'blur' }
          ],
          homeImgCreateTime: [
            { required: true, message: '发布时间不能为空', trigger: 'blur' }
          ],
          homeImgDefault: [
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
              url: this.$http.adornUrl(`/basic/homeimg/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.homeImgName = data.homeImg.homeImgName
                this.dataForm.homeImgUrl = data.homeImg.homeImgUrl
                this.dataForm.homeImgCreateTime = data.homeImg.homeImgCreateTime
                this.dataForm.homeImgDefault = data.homeImg.homeImgDefault
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
              url: this.$http.adornUrl(`/basic/homeimg/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'homeImgName': this.dataForm.homeImgName,
                'homeImgUrl': this.dataForm.homeImgUrl,
                'homeImgCreateTime': this.dataForm.homeImgCreateTime,
                'homeImgDefault': this.dataForm.homeImgDefault
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
