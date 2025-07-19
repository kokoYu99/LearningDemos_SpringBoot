<template>
    <div class="main-panel">
        <div class="menus">
            当前用户：
            <el-button type="primary">
                {{name}}
            </el-button>
            
            <div v-for="(menu, index) in menus" @click="toPage(menu)">
                {{ menu.name }}
            </div>
            
        </div>
        <div style="padding:20px;width:80%">
            <router-view></router-view>
        </div>
    </div>
    <div class="title">后台管理系统</div>
</template>

<script setup>
import { ref,  onMounted } from 'vue'
import { useRouter } from 'vue-router'
import cookie from 'js-cookie'

const router = useRouter()

// 遍历的菜单数组
const props = [
    { name: "文章管理", href: "/dashboard/article" },
    { name: "分类管理", href: "/dashboard/category" },
    { name: "用户中心", href: "/dashboard/user" },
    { name: "退出", href: "logout" },
]
const menus = ref(props)
//用户的名
const name = ref(null)

onMounted(() => {
    getName()
})

const getName = ()=>{
    //修改key等于你存储的key
    name.value = cookie.get('username')
    //null == false
    if(!name.value) {
        //为null,没有登录,跳转到登录页面
        router.push("/login")
    }
}

const toPage = (menu) => {
    if (menu.href == 'logout') {
        //跳转到登录页面
        router.push("/login")
    } else {
        let paths = menu.href;
        router.push(paths)
    }
}
</script>

<style lang="scss" scoped>
.main-panel {
    display: flex;
    color: #64676a;
    max-width: 100%;
}

.menus {
    line-height: 55px;
    text-align: center;
    width: 20%;
    height: 100%vh;
    border-right: 2px solid #dadada;
    cursor: pointer;
}

.title {
    font-size: 65px;
    font-weight: bold;
    text-align: right;
    position: fixed;
    color: rgba(0, 0, 0, 20%);
    right: calc((100vw - 1500px) / 2);
    bottom: 20px;
}
</style>