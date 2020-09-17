<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="预警等级" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['wlever']" :trigger-change="true" dictCode="" placeholder="请选择预警等级"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="预警类型 " :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['wtype']" :trigger-change="true" dictCode="" placeholder="请选择预警类型 "/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
             <j-area-linkage type="cascader" v-decorator="['region']" placeholder="请输入省市区"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="信息来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['infosource']" placeholder="请输入信息来源"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="信息标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['infotitle']" placeholder="请输入信息标题"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择开始时间" v-decorator="['timestart']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择结束时间" v-decorator="['timeend']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="上报" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sender']" placeholder="请输入上报"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="上报公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['sendercompany']" placeholder="请输入上报公司"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="策略ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['sid']" :trigger-change="true" dictCode="" placeholder="请选择策略ID"/>
            </a-form-item>
          </a-col>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JAreaLinkage from '@comp/jeecg/JAreaLinkage'

  export default {
    name: 'WarningissuedForm',
    components: {
      JFormContainer,
      JDate,
      JDictSelectTag,
      JAreaLinkage,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
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
        },
        url: {
          add: "/test/warningissued/add",
          edit: "/test/warningissued/edit",
          queryById: "/test/warningissued/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'wlever','wtype','region','infosource','infotitle','timestart','timeend','sender','sendercompany','sid','status'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
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
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
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
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'wlever','wtype','region','infosource','infotitle','timestart','timeend','sender','sendercompany','sid','status'))
      },
    }
  }
</script>