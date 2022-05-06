webpackJsonp([9],{"3uSt":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=[{dataIndex:"productLabel",title:"产品名称",ellipsis:!0,key:"productLabel"},{title:"产品ID",dataIndex:"id",ellipsis:!0,key:"id"},{title:"设备类型",ellipsis:!0,dataIndex:"deviceType",key:"deviceType"},{title:"协议类型",key:"protocolType",dataIndex:"protocolType",ellipsis:!0},{title:"操作",key:"action",scopedSlots:{customRender:"action"}}],i={name:"productList",components:{BTextarea:a("EscQ").a},data:function(){return{data:[],columns:o,searchObj:{productLabel:""},pagination:{showLessItems:!0,pageSizeOptions:["10","20","30"],showSizeChanger:!0,pageSize:10,current:1,hideOnSinglePage:!1,total:0},visible:!1,labelCol:{span:4},wrapperCol:{span:20},other:"",form:{productLabel:"",protocolType:void 0,dataFormat:"",id:"",deviceType:"",description:""},rules:{productLabel:[{required:!0,message:"请输入产品名称",trigger:"blur"}],dataFormat:[{required:!0,message:"请选择数据格式",trigger:"change"}],protocolType:[{required:!0,message:"请输入协议类型",trigger:"change"}],description:[{validator:this.$validator.checkRemarkLength,message:"描述最多128字符",trigger:["blur","change"]}]},confirmLoading:!1}},mounted:function(){this.getList()},methods:{tableChange:function(t){this.pagination.pageSize=t.pageSize,this.pagination.current=t.current,this.pagination.total=t.total,this.getList()},getList:function(){var t=this,e={productLabel:this.searchObj.productLabel,page:this.pagination.current,pageSize:this.pagination.pageSize};this.$api.getProductList(e).then(function(e){var a=e.data;t.pagination.total=a.total,t.pagination.pageSize=a.size,t.pagination.current=a.current;var o=a.records;t.data=o})},deleteCancel:function(t){var e=this;this.$api.deleteProduct(t).then(function(t){t.data&&e.getList()})},showModal:function(){this.visible=!0,this.form={}},handleOk:function(){var t=this;this.confirmLoading=!0,this.$refs.productForm.validate(function(e){e&&t.$api.postProductAdd(t.form).then(function(e){t.visible=!1,t.confirmLoading=!1,t.getList()})})},colseHandle:function(){this.visible=!1,this.$refs.productForm.resetFields()},onSearch:function(){this.getList()},goDetail:function(t){this.$router.push({path:"/productDetails",query:{id:t}})}}},r={render:function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",[t._m(0),t._v(" "),o("div",{staticClass:"product-info"},[o("div",{staticClass:"product-content"},[o("a-row",[o("a-col",{attrs:{span:"14"}},[o("h3",{staticStyle:{"font-weight":"bold"}},[t._v("功能介绍")]),t._v(" "),o("p",[t._v("在物联网平台中，某一类具有相同能力或特征的设备的合集被称为一款产品。")]),t._v(" "),o("p",[t._v("如果您希望使用平台查看设备上报的数据信息，并对设备进行管理控制，就需要开发产品模型(Model)。定义Model，使平台理解该款设备支持的属性、命令等信息。根据产品的接入协议、数据格式等可能还需要您定义其他相关的内容")])]),t._v(" "),o("a-col",{attrs:{span:"10"}},[o("img",{attrs:{src:a("tyoJ"),height:"100%",width:"100%"}})])],1)],1)]),t._v(" "),o("div",{staticClass:"table-content"},[o("div",{staticClass:"table-title"},[o("a-button",{attrs:{type:"primary"},on:{click:t.showModal}},[o("a-icon",{attrs:{type:"plus"}}),t._v(" 创建产品")],1),t._v(" "),o("a-input-search",{staticStyle:{width:"200px",margin:"0 20px"},attrs:{allowClear:"",placeholder:"请输入产品名称搜索"},on:{blur:t.onSearch,search:t.onSearch},model:{value:t.searchObj.productLabel,callback:function(e){t.$set(t.searchObj,"productLabel",e)},expression:"searchObj.productLabel"}})],1),t._v(" "),o("div",{staticClass:"table-box"},[o("a-table",{attrs:{columns:t.columns,rowKey:function(t,e){return e},pagination:t.pagination,"data-source":t.data},on:{change:t.tableChange},scopedSlots:t._u([{key:"name",fn:function(e){return o("a",{},[t._v(t._s(e))])}},{key:"action",fn:function(e,a){return o("span",{},[o("span",{staticClass:"table-btn",on:{click:function(e){return t.goDetail(a.id)}}},[t._v("查看")]),t._v(" "),o("a-popconfirm",{attrs:{title:"你确定删除此产品吗?","ok-text":"确定","cancel-text":"取消"},on:{confirm:function(e){return t.deleteCancel(a.id)}}},[o("span",{staticClass:"table-btn"},[t._v(" 删除 ")])])],1)}}])})],1)]),t._v(" "),o("a-modal",{attrs:{width:700,title:"创建产品"},on:{ok:t.handleOk,cancel:t.colseHandle},model:{value:t.visible,callback:function(e){t.visible=e},expression:"visible"}},[o("a-form-model",{ref:"productForm",attrs:{model:t.form,rules:t.rules,"confirm-loading":t.confirmLoading,"label-col":t.labelCol,"wrapper-col":t.wrapperCol}},[o("a-form-model-item",{ref:"name",attrs:{label:"产品名称",prop:"productLabel"}},[o("a-input",{attrs:{placeholder:"请输入产品名称"},model:{value:t.form.productLabel,callback:function(e){t.$set(t.form,"productLabel",e)},expression:"form.productLabel"}})],1),t._v(" "),o("a-form-model-item",{attrs:{label:"协议类型",prop:"protocolType"}},[o("a-select",{attrs:{placeholder:"请选择协议类型"},model:{value:t.form.protocolType,callback:function(e){t.$set(t.form,"protocolType",e)},expression:"form.protocolType"}},[o("a-select-option",{attrs:{value:"MQTT"}},[t._v("\n            MQTT\n          ")])],1)],1),t._v(" "),o("a-form-model-item",{attrs:{label:"数据格式",prop:"dataFormat"}},[o("a-select",{attrs:{placeholder:"请选择数据格式"},model:{value:t.form.dataFormat,callback:function(e){t.$set(t.form,"dataFormat",e)},expression:"form.dataFormat"}},[o("a-select-option",{attrs:{value:"JSON"}},[t._v("\n           JSON\n          ")]),t._v(" "),o("a-select-option",{attrs:{value:"BINARY"}},[t._v("\n           二进制码流\n          ")])],1)],1),t._v(" "),o("a-form-model-item",{attrs:{label:"设备类型"}},[o("a-input",{attrs:{placeholder:"请输入设备类型"},model:{value:t.form.deviceType,callback:function(e){t.$set(t.form,"deviceType",e)},expression:"form.deviceType"}})],1),t._v(" "),o("a-form-model-item",{attrs:{label:"产品描述"}},[o("BTextarea",{staticClass:"full-width",staticStyle:{height:"auto"},attrs:{placeholder:"请输入描述"},model:{value:t.form.description,callback:function(e){t.$set(t.form,"description",e)},expression:"form.description"}})],1)],1)],1)],1)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"home-title"},[e("span",[this._v("产品管理")])])}]};var n=a("C7Lr")(i,r,!1,function(t){a("Uvca")},"data-v-475e6d36",null);e.default=n.exports},Uvca:function(t,e){},tyoJ:function(t,e,a){t.exports=a.p+"static/img/product.1336662.png"}});
//# sourceMappingURL=9.624a739287885b27ffb8.js.map