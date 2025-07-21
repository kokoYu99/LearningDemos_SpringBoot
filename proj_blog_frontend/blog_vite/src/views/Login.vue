<script setup>
/* TODO:
1. 导入依赖
    ref -> vue
    useRouter -> vue-router
    userLogin -> /api/user.js

2. 定义全局对象，接收数据
    user对象，接收用户名、密码

3. 定义周期函数，初始化加载数据
    无

4. 定义事件函数，自定义逻辑
    blur时，校验用户名、密码正则；
    点击登录，将用户名密码传给后端校验
    4.1 校验失败
        提示框
    4.2 校验通过
        后端返回uid，将其作为cookie保存
        跳转到后台管理页面

*/

import { ref } from "vue";
import { useRouter } from "vue-router";
import { userLogin } from '../api/user.js';
import cookie from 'js-cookie';
import { ElMessage } from 'element-plus';


/* router对象，用于跳转页面 */
const router = useRouter();

/* 接收表单输入的用户名、密码 */
const user = ref({
    username: '',
    password: ''
});

/* 接收表单对象，用于表单校验 */
const userForm = ref(null);

/* 表单校验规则 */
const pageParamsForm = {
    username: [
        { required: true, message: "请输入账号", trigger: "blur" },
        {
            min: 3,
            max: 12,
            message: "账号长度在 3 到 12 个字符",
            trigger: "blur",
        },
    ],
    password: [
        { required: true, message: "请输入密码", trigger: "blur" },
        {
            min: 6,
            max: 18,
            message: "密码长度在 6 到 18 个字符",
            trigger: "blur",
        },
    ],
};
const formRules = ref(pageParamsForm);

/* 登录方法
1. 表单规则校验
2. 调用后端接口userLogin
    2.1 校验通过，保存uid为cookie，跳转到后台管理页面
    2.2 不通过，提示框，账户密码置空
    */
const loginUser = async () => {
    //表单规则校验
    const result = await userForm.value.validate();

    //表单校验通过，调用后端接口
    if (result) {
        //进行登录，获取响应，取出code和data
        console.log(user.value);

        const { code, data } = await userLogin(user.value);
        if (code == 200) {
            cookie.set('uid', data.uid, { path: '/' });
            cookie.set('username', user.value.username, { path: '/' });
            router.push({ path: '/dashboard' })
        } else {
            ElMessage('用户名或密码错误');
            user.value.password = "";
        }

    }
};
</script>



<template>
    <div class="login">
        <el-form class="form" :model="user" :rules="formRules" ref="userForm">
            <h1 class="title">微博客后台管理系统</h1>
            <el-form-item prop="username">
                <el-input class="text" v-model="user.username" prefix-icon="User" clearable placeholder="用户名" />
            </el-form-item>
            <el-form-item prop="password">
                <el-input class="text" v-model="user.password" prefix-icon="Lock" show-password clearable
                    placeholder="密码" />
            </el-form-item>
            <el-form-item>
                <el-button :loading="loading" type="primary" class="btn" size="large" @click="loginUser">
                    登录
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<style lang="scss" scoped>
.login {
    transition: transform 1s;
    transform: scale(1);
    width: 100%;
    height: 600px;
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

            .el-input-group--append>.el-input__wrapper {
                border-top-right-radius: 0;
                border-bottom-right-radius: 0;
            }

            .el-input-group--prepend>.el-input__wrapper {
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
