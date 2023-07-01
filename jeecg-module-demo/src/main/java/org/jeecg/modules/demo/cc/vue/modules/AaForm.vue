<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="EE" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="applicationNumber">
              <a-input v-model="model.applicationNumber" placeholder="请输入EE"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="EE" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="auditStatus">
              <a-input v-model="model.auditStatus" placeholder="请输入EE"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="EE" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="itemCategory">
              <a-input v-model="model.itemCategory" placeholder="请输入EE"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="EE" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="processType">
              <a-input v-model="model.processType" placeholder="请输入EE"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="QQ" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="contactPerson">
              <j-popup
                v-model="model.contactPerson"
                field="contactPerson"
                org-fields="first_name,last_name"
                dest-fields="contactPerson,processType"
                code="actorr"
                :multi="true"
                @input="popupCallback"
                />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="QQ" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="contactPersonCode">
              <a-input v-model="model.contactPersonCode" placeholder="请输入QQ"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="EE" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="counterSignDepartment">
              <a-input v-model="model.counterSignDepartment" placeholder="请输入EE"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="WW" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sealType">
              <a-input v-model="model.sealType" placeholder="请输入WW"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="CC" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fileCopies">
              <a-input v-model="model.fileCopies" placeholder="请输入CC"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="BB" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sealReason">
              <a-input v-model="model.sealReason" placeholder="请输入BB"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="AA" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="contactInformation">
              <a-input v-model="model.contactInformation" placeholder="请输入AA"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="AA" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sealFile">
              <a-input v-model="model.sealFile" placeholder="请输入AA"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="AA" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="attachmentDescription">
              <a-input v-model="model.attachmentDescription" placeholder="请输入AA"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="aaA" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="uploadTime">
              <a-input v-model="model.uploadTime" placeholder="请输入aaA"  ></a-input>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'AaForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
           auditStatus: [
              { required: true, message: '请输入EE!'},
           ],
           itemCategory: [
              { required: true, message: '请输入EE!'},
           ],
        },
        url: {
          add: "/cc/aa/add",
          edit: "/cc/aa/edit",
          queryById: "/cc/aa/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(value,row){
         this.model = Object.assign(this.model, row);
      },
    }
  }
</script>