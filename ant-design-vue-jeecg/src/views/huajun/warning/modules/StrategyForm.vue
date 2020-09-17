<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="策略名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name']" placeholder="请输入策略名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="区域" :labelCol="labelCol" :wrapperCol="wrapperCol">
             <j-area-linkage type="cascader" v-decorator="['region']" placeholder="请输入省市区"/>
            </a-form-item>
          </a-col>
          <a-col :span="24" >
            <
            <a-form-item label="是否公开" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group buttonStyle="solid" v-decorator="[ 'status', {'initialValue':0}]">
                <a-radio :value="1">公开</a-radio>
                <a-radio :value="2">不公开</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="模板选择" style="width: 300px">
              <j-search-select-tag
                placeholder="模板选择"
                v-model="selectValue"
                :dictOptions="templateList">
              </j-search-select-tag>
              {{ selectValue }}
            </a-form-item>
            <a-form-item label="模板"  :labelCol="labelCol" :wrapperCol="wrapperCol">
              <select>
                <option v-for="temp in templateList" v-bind:value="temp.id" >
                  {{ temp.tname }}
                </option> </select>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JMultiSelectTag from "@/components/dict/JMultiSelectTag"
  import JAreaLinkage from '@comp/jeecg/JAreaLinkage'
  import SelectUserListModal from '../../../system/modules/SelectUserListModal'
  import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
  import { axios } from '../../../../utils/request'
  import JCheckbox from '../../../../components/jeecg/JCheckbox'
  import JVxeRadioCell from '../../../../components/JVxeCells/JVxeRadioCell'

  export default {
    name: 'StrategyForm',
    components: {
      JVxeRadioCell,
      JCheckbox,
      SelectUserListModal,
      JFormContainer,
      JDictSelectTag,
      JMultiSelectTag,
      JAreaLinkage,
      JSearchSelectTag,

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
        templateList:[],
        templatesTest:[],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        selectValue:"",
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/warning/strategy/add",
          edit: "/warning/strategy/edit",
          queryById: "/warning/strategy/queryById",
          queryTemplates:"/warning/strategy/queryTemplates"
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
      //加载模板信息
      this.showTemplates();
      this.showTemplatesTest();
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
          this.form.setFieldsValue(pick(this.model,'name','region','isrelease','tid'))
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
      showTemplates(){
        var that = this;
        var templates = [];
        axios.get(this.url.queryTemplates).then((resp)=> {
          console.log(resp.result);

         that.templateList= resp.result;
        }).catch((error)=>{
          console.log('加载失败！'+error);
        });
      },
      showTemplatesTest(){
        var that = this;
        axios.get("/tableToDictList/selectList").then((resp)=> {
          console.log(resp.result);

          that.templatesTest= resp.result;
        }).catch((error)=>{
          console.log('加载失败！'+error);
        });
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'name','region','isrelease','tid'))
      },
    }
  }
</script>