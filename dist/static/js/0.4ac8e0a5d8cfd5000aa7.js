webpackJsonp([0],{EscQ:function(e,t,a){"use strict";var n={model:{prop:"oldValue",event:"change"},watch:{oldValue:{handler:function(e){this.newValue=e},immediate:!0},newValue:{handler:function(e){this.$emit("change",e)}}},data:function(){return{newValue:""}},props:{disabled:{type:Boolean,default:!1},autoSize:{type:Object,default:function(){return{minRows:3,maxRows:5}}},placeholder:{type:String,default:"请输入备注"},maxLength:{type:[Number,String],default:128},oldValue:{type:String,default:""}}},l={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"b-textarea-box"},[a("a-textarea",{attrs:{disabled:e.disabled,placeholder:e.placeholder,"auto-size":e.autoSize},model:{value:e.newValue,callback:function(t){e.newValue=t},expression:"newValue"}}),e._v(" "),a("div",{staticClass:"tip",style:{color:e.newValue.length>e.maxLength?"red":""}},[e._v("\n    "+e._s(e.newValue.length+" / "+e.maxLength)+"\n  ")])],1)},staticRenderFns:[]};var u=a("C7Lr")(n,l,!1,function(e){a("NSdR")},"data-v-6fd5184f",null);t.a=u.exports},NSdR:function(e,t){}});
//# sourceMappingURL=0.4ac8e0a5d8cfd5000aa7.js.map