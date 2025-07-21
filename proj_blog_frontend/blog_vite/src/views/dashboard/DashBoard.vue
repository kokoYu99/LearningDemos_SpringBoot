<script setup>
/* 1. 导入依赖 */
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import cookie from "js-cookie";

/* 2. 定义全局常量对象 */
//router对象，用于跳转页面
const router = useRouter();

//左侧的菜单数组
const menus = ref([
    { name: "文章管理", href: "/dashboard/article" },
    { name: "分类管理", href: "/dashboard/category" },
    { name: "用户中心", href: "/dashboard/user" },
    { name: "退出", href: "logout" },
]);

//左上角的用户名，页面挂载后立即加载
const name = ref(null);

/* 3. 定义周期函数 */
//挂载后立即获取用户名
onMounted(() => {
    getName();
});

/* 4. 定义方法，自定义逻辑 */
const getName = () => {
    //从存储的cookie中取出username
    name.value = cookie.get("username");

    //如果name为null，跳转到登录页
    //逻辑：name==null -> null==false -> !false取反为true
    if (!name.value) {
        router.push("/login")
    }
};

const toPage = (menu) => {
    if (menu.href == "logout") {
        //点击退出，跳转回登录页
        router.push("/login");
    } else {
        let paths = menu.href;
        router.push(paths);
    }
};
</script>


<template>
    <div class="main-panel">
        <div class="menus">
            当前用户：
            <el-button type="primary">
                {{ name }}
            </el-button>

            <div v-for="(menu, index) in menus" :key="index" @click="toPage(menu)">
                {{ menu.name }}
            </div>

        </div>
        <div style="padding:20px;width:80%">
            <router-view></router-view>
        </div>
    </div>
    <div class="title">后台管理系统</div>
</template>


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