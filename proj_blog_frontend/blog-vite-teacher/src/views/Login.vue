<template>
    <div class="login">
      <el-form class="form" :model="user" :rules="formRules" ref="userForm">
        <h1 class="title">微博客后台管理系统</h1>
        <el-form-item prop="username">
          <el-input
            class="text"
            v-model="user.username"
            prefix-icon="User"
            clearable
            placeholder="用户名"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            class="text"
            v-model="user.password"
            prefix-icon="Lock"
            show-password
            clearable
            placeholder="密码"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            :loading="loading"
            type="primary"
            class="btn"
            size="large"
            @click="loginUser"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
    
  <script setup>

   /**
    * 
    * 1.导包 ref -> user输入标签   useRouter  userLogin
    * 2.声明全局数据  用户对象,收集输入的用户数据
    * 3.初始化方法 [不需要]
    * 4.业务逻辑方法 -> 点击登录   失败: 提示框  成功: 数据保存/cookie 跳转到后台管理页面
    * 
    */

    import { ref  } from 'vue';
    import { useRouter } from 'vue-router'
    import cookie from 'js-cookie'
    import { userLogin } from '../api/user'
    import { ElMessage } from 'element-plus'

    const router = useRouter()

  
    //接收表单输入的账号和密码
    const user = ref({
        username: "",
        password: ""
    }) 
    
    //接收表单对象,用于进行表单的校验
    const userForm = ref(null)
    
  
    const pageParamsForm = {
        username: [
            { required: true, message: "请输入账号", trigger: "blur" },
            { min: 3, max: 12, message: "账号长度在 3 到 12 个字符", trigger: "blur" },
        ],
        password: [
            { required: true, message: "请输入密码", trigger: "blur" },
            { min: 6, max: 18, message: "密码长度在 6 到 18 个字符", trigger: "blur" },
        ],
    }
    //表单规则
    const formRules = ref(pageParamsForm) 
  

    //登录方法
    /**
     * 1. 查看输入的数据是否符合校验规则
     * 2. 调用后台的接口 
     * 3. 成功 -> 存储cookie -> 跳转
     *    失败 -> 失败提示 | 密码置空
     */
    const loginUser =async () => {
    
        const valid = await userForm.value.validate()

        if(valid){
          //ElMessage('账号或者密码不符合规则!!!')
          const {code,data} = await userLogin(user.value);
    
          if (code == 200) {
            
            //存储数据
            cookie.set('uid', data.uid, { path: '/' })
            cookie.set('username', user.value.username, { path: '/' })
            //跳转页面
            router.push({path:"/dashboard"})
          }else{
            ElMessage("账号或者密码错误!!")
            user.value.password=""
            //前端的空 null 曾经拥有 | undefined 母胎单身
            // var name; //undefined 
            // name = '小井老师'
            // name = null
          }
        }

    }
  </script>
    
  <style lang="scss" scoped>
    .login {
      transition: transform 1s;
      transform: scale(1);
      width: 100%;
      height:600px;
        margin: 0 auto;
      overflow: hidden;
      background: #2d3a4b;
    .form {
      width: 520px;
      max-width: 100%;
      padding: 0 24px;
      box-sizing: border-box;
      margin: 160px auto 0;
      :deep {
        .el-input__wrapper {
          box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.1) inset;
          background: rgba(0, 0, 0, 0.1);
        }
        .el-input-group--append > .el-input__wrapper {
          border-top-right-radius: 0;
          border-bottom-right-radius: 0;
        }
        .el-input-group--prepend > .el-input__wrapper {
          border-top-left-radius: 0;
          border-bottom-left-radius: 0;
        }
      }
      .title {
        color: #fff;
        text-align: center;
        font-size: 24px;
        margin: 0 0 24px;
      }
      .text {
        font-size: 16px;
        :deep(.el-input__inner) {
          color: #fff;
          height: 48px;
          line-height: 48px;
          &::placeholder {
            color: rgba(255, 255, 255, 0.2);
          }
        }
      }
      .btn {
        width: 100%;
      }
      }
    }
  </style>