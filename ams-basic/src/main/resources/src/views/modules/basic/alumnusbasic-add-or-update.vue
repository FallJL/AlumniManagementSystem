<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="姓名" prop="aluName">
      <el-input v-model="dataForm.aluName" placeholder="姓名"></el-input>
    </el-form-item>
    <el-form-item label="学号" prop="aluId">
      <el-input v-model="dataForm.aluId" placeholder="学号"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="gender">
      <el-input v-model="dataForm.gender" placeholder="性别"></el-input>
    </el-form-item>
    <el-form-item label="曾用名" prop="aluFormerName">
      <el-input v-model="dataForm.aluFormerName" placeholder="曾用名"></el-input>
    </el-form-item>
    <el-form-item label="身份证号" prop="idCard">
      <el-input v-model="dataForm.idCard" placeholder="身份证号"></el-input>
    </el-form-item>
    <el-form-item label="民族" prop="nationality">
      <el-input v-model="dataForm.nationality" placeholder="民族"></el-input>
    </el-form-item>
    <el-form-item label="政治面貌" prop="politicalStatus">
      <el-input v-model="dataForm.politicalStatus" placeholder="政治面貌"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item label="籍贯" prop="nativePlace">
      <el-input v-model="dataForm.nativePlace" placeholder="籍贯"></el-input>
    </el-form-item>
    <el-form-item label="班级" prop="clazz">
      <el-input v-model="dataForm.clazz" placeholder="班级"></el-input>
    </el-form-item>
    <el-form-item label="入学时间" prop="admissionTime">
      <el-input v-model="dataForm.admissionTime" placeholder="入学时间"></el-input>
    </el-form-item>
    <el-form-item label="毕业时间" prop="graduationTime">
      <el-input v-model="dataForm.graduationTime" placeholder="毕业时间"></el-input>
    </el-form-item>
    <el-form-item label="院系" prop="department">
      <el-input v-model="dataForm.department" placeholder="院系"></el-input>
    </el-form-item>
    <el-form-item label="专业" prop="major">
      <el-input v-model="dataForm.major" placeholder="专业"></el-input>
    </el-form-item>
    <el-form-item label="阶段" prop="degreeStage">
      <el-input v-model="dataForm.degreeStage" placeholder="阶段"></el-input>
    </el-form-item>
    <el-form-item label="手机" prop="phoneNum">
      <el-input v-model="dataForm.phoneNum" placeholder="手机"></el-input>
    </el-form-item>
    <el-form-item label="通信地址" prop="mailingAddress">
      <el-input v-model="dataForm.mailingAddress" placeholder="通信地址"></el-input>
    </el-form-item>
    <el-form-item label="所在城市" prop="city">
      <el-input v-model="dataForm.city" placeholder="所在城市"></el-input>
    </el-form-item>
    <el-form-item label="工作单位" prop="workUnit">
      <el-input v-model="dataForm.workUnit" placeholder="工作单位"></el-input>
    </el-form-item>
    <el-form-item label="担任职务" prop="jobTitle">
      <el-input v-model="dataForm.jobTitle" placeholder="担任职务"></el-input>
    </el-form-item>
    <el-form-item label="行业类别" prop="industryCategory">
      <el-input v-model="dataForm.industryCategory" placeholder="行业类别"></el-input>
    </el-form-item>
    <el-form-item label="企业性质" prop="enterpriseProperty">
      <el-input v-model="dataForm.enterpriseProperty" placeholder="企业性质"></el-input>
    </el-form-item>
    <el-form-item label="出生日期" prop="birthDate">
      <el-input v-model="dataForm.birthDate" placeholder="出生日期"></el-input>
    </el-form-item>
    <el-form-item label="工作电话" prop="workPhone">
      <el-input v-model="dataForm.workPhone" placeholder="工作电话"></el-input>
    </el-form-item>
    <el-form-item label="工作地址" prop="workAddress">
      <el-input v-model="dataForm.workAddress" placeholder="工作地址"></el-input>
    </el-form-item>
    <el-form-item label="QQ" prop="qq">
      <el-input v-model="dataForm.qq" placeholder="QQ"></el-input>
    </el-form-item>
    <el-form-item label="微博" prop="weibo">
      <el-input v-model="dataForm.weibo" placeholder="微博"></el-input>
    </el-form-item>
    <el-form-item label="个人网站" prop="personalWebsite">
      <el-input v-model="dataForm.personalWebsite" placeholder="个人网站"></el-input>
    </el-form-item>
    <el-form-item label="微信" prop="wechat">
      <el-input v-model="dataForm.wechat" placeholder="微信"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="note">
      <el-input v-model="dataForm.note" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="vip" prop="vip">
      <el-input v-model="dataForm.vip" placeholder="vip"></el-input>
    </el-form-item>
    <el-form-item label="在校期间荣誉" prop="honorsDuringSchool">
      <el-input v-model="dataForm.honorsDuringSchool" placeholder="在校期间荣誉"></el-input>
    </el-form-item>
    <el-form-item label="头像地址" prop="portraitUrl">
      <el-input v-model="dataForm.portraitUrl" placeholder="头像地址"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="aluStatus">
      <el-input v-model="dataForm.aluStatus" placeholder="状态"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="dataForm.password" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item label="注册时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="注册时间"></el-input>
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
          aluName: '',
          aluId: '',
          gender: '',
          aluFormerName: '',
          idCard: '',
          nationality: '',
          politicalStatus: '',
          email: '',
          nativePlace: '',
          clazz: '',
          admissionTime: '',
          graduationTime: '',
          department: '',
          major: '',
          degreeStage: '',
          phoneNum: '',
          mailingAddress: '',
          city: '',
          workUnit: '',
          jobTitle: '',
          industryCategory: '',
          enterpriseProperty: '',
          birthDate: '',
          workPhone: '',
          workAddress: '',
          qq: '',
          weibo: '',
          personalWebsite: '',
          wechat: '',
          note: '',
          vip: '',
          honorsDuringSchool: '',
          portraitUrl: '',
          aluStatus: '',
          password: '',
          createTime: ''
        },
        dataRule: {
          aluName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          aluId: [
            { required: true, message: '学号不能为空', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: '性别不能为空', trigger: 'blur' }
          ],
          aluFormerName: [
            { required: true, message: '曾用名不能为空', trigger: 'blur' }
          ],
          idCard: [
            { required: true, message: '身份证号不能为空', trigger: 'blur' }
          ],
          nationality: [
            { required: true, message: '民族不能为空', trigger: 'blur' }
          ],
          politicalStatus: [
            { required: true, message: '政治面貌不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '邮箱不能为空', trigger: 'blur' }
          ],
          nativePlace: [
            { required: true, message: '籍贯不能为空', trigger: 'blur' }
          ],
          clazz: [
            { required: true, message: '班级不能为空', trigger: 'blur' }
          ],
          admissionTime: [
            { required: true, message: '入学时间不能为空', trigger: 'blur' }
          ],
          graduationTime: [
            { required: true, message: '毕业时间不能为空', trigger: 'blur' }
          ],
          department: [
            { required: true, message: '院系不能为空', trigger: 'blur' }
          ],
          major: [
            { required: true, message: '专业不能为空', trigger: 'blur' }
          ],
          degreeStage: [
            { required: true, message: '阶段不能为空', trigger: 'blur' }
          ],
          phoneNum: [
            { required: true, message: '手机不能为空', trigger: 'blur' }
          ],
          mailingAddress: [
            { required: true, message: '通信地址不能为空', trigger: 'blur' }
          ],
          city: [
            { required: true, message: '所在城市不能为空', trigger: 'blur' }
          ],
          workUnit: [
            { required: true, message: '工作单位不能为空', trigger: 'blur' }
          ],
          jobTitle: [
            { required: true, message: '担任职务不能为空', trigger: 'blur' }
          ],
          industryCategory: [
            { required: true, message: '行业类别不能为空', trigger: 'blur' }
          ],
          enterpriseProperty: [
            { required: true, message: '企业性质不能为空', trigger: 'blur' }
          ],
          birthDate: [
            { required: true, message: '出生日期不能为空', trigger: 'blur' }
          ],
          workPhone: [
            { required: true, message: '工作电话不能为空', trigger: 'blur' }
          ],
          workAddress: [
            { required: true, message: '工作地址不能为空', trigger: 'blur' }
          ],
          qq: [
            { required: true, message: 'QQ不能为空', trigger: 'blur' }
          ],
          weibo: [
            { required: true, message: '微博不能为空', trigger: 'blur' }
          ],
          personalWebsite: [
            { required: true, message: '个人网站不能为空', trigger: 'blur' }
          ],
          wechat: [
            { required: true, message: '微信不能为空', trigger: 'blur' }
          ],
          note: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          vip: [
            { required: true, message: 'vip不能为空', trigger: 'blur' }
          ],
          honorsDuringSchool: [
            { required: true, message: '在校期间荣誉不能为空', trigger: 'blur' }
          ],
          portraitUrl: [
            { required: true, message: '头像地址不能为空', trigger: 'blur' }
          ],
          aluStatus: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          createTime: [
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
              url: this.$http.adornUrl(`/basic/alumnusbasic/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.aluName = data.alumnusBasic.aluName
                this.dataForm.aluId = data.alumnusBasic.aluId
                this.dataForm.gender = data.alumnusBasic.gender
                this.dataForm.aluFormerName = data.alumnusBasic.aluFormerName
                this.dataForm.idCard = data.alumnusBasic.idCard
                this.dataForm.nationality = data.alumnusBasic.nationality
                this.dataForm.politicalStatus = data.alumnusBasic.politicalStatus
                this.dataForm.email = data.alumnusBasic.email
                this.dataForm.nativePlace = data.alumnusBasic.nativePlace
                this.dataForm.clazz = data.alumnusBasic.clazz
                this.dataForm.admissionTime = data.alumnusBasic.admissionTime
                this.dataForm.graduationTime = data.alumnusBasic.graduationTime
                this.dataForm.department = data.alumnusBasic.department
                this.dataForm.major = data.alumnusBasic.major
                this.dataForm.degreeStage = data.alumnusBasic.degreeStage
                this.dataForm.phoneNum = data.alumnusBasic.phoneNum
                this.dataForm.mailingAddress = data.alumnusBasic.mailingAddress
                this.dataForm.city = data.alumnusBasic.city
                this.dataForm.workUnit = data.alumnusBasic.workUnit
                this.dataForm.jobTitle = data.alumnusBasic.jobTitle
                this.dataForm.industryCategory = data.alumnusBasic.industryCategory
                this.dataForm.enterpriseProperty = data.alumnusBasic.enterpriseProperty
                this.dataForm.birthDate = data.alumnusBasic.birthDate
                this.dataForm.workPhone = data.alumnusBasic.workPhone
                this.dataForm.workAddress = data.alumnusBasic.workAddress
                this.dataForm.qq = data.alumnusBasic.qq
                this.dataForm.weibo = data.alumnusBasic.weibo
                this.dataForm.personalWebsite = data.alumnusBasic.personalWebsite
                this.dataForm.wechat = data.alumnusBasic.wechat
                this.dataForm.note = data.alumnusBasic.note
                this.dataForm.vip = data.alumnusBasic.vip
                this.dataForm.honorsDuringSchool = data.alumnusBasic.honorsDuringSchool
                this.dataForm.portraitUrl = data.alumnusBasic.portraitUrl
                this.dataForm.aluStatus = data.alumnusBasic.aluStatus
                this.dataForm.password = data.alumnusBasic.password
                this.dataForm.createTime = data.alumnusBasic.createTime
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
              url: this.$http.adornUrl(`/basic/alumnusbasic/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'aluName': this.dataForm.aluName,
                'aluId': this.dataForm.aluId,
                'gender': this.dataForm.gender,
                'aluFormerName': this.dataForm.aluFormerName,
                'idCard': this.dataForm.idCard,
                'nationality': this.dataForm.nationality,
                'politicalStatus': this.dataForm.politicalStatus,
                'email': this.dataForm.email,
                'nativePlace': this.dataForm.nativePlace,
                'clazz': this.dataForm.clazz,
                'admissionTime': this.dataForm.admissionTime,
                'graduationTime': this.dataForm.graduationTime,
                'department': this.dataForm.department,
                'major': this.dataForm.major,
                'degreeStage': this.dataForm.degreeStage,
                'phoneNum': this.dataForm.phoneNum,
                'mailingAddress': this.dataForm.mailingAddress,
                'city': this.dataForm.city,
                'workUnit': this.dataForm.workUnit,
                'jobTitle': this.dataForm.jobTitle,
                'industryCategory': this.dataForm.industryCategory,
                'enterpriseProperty': this.dataForm.enterpriseProperty,
                'birthDate': this.dataForm.birthDate,
                'workPhone': this.dataForm.workPhone,
                'workAddress': this.dataForm.workAddress,
                'qq': this.dataForm.qq,
                'weibo': this.dataForm.weibo,
                'personalWebsite': this.dataForm.personalWebsite,
                'wechat': this.dataForm.wechat,
                'note': this.dataForm.note,
                'vip': this.dataForm.vip,
                'honorsDuringSchool': this.dataForm.honorsDuringSchool,
                'portraitUrl': this.dataForm.portraitUrl,
                'aluStatus': this.dataForm.aluStatus,
                'password': this.dataForm.password,
                'createTime': this.dataForm.createTime
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
