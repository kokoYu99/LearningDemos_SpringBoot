<script setup type="module">
/* TODO:
Java:
    1. 导入依赖
    2. 创建Java类
    3. 创建全局变量，接收数据
    4. 定义构造器，做初始化加载
    5. 定义其他方法，自定义逻辑
js：
    1. 导入依赖
    2. 定义页面展示的数据，包括响应式数据
        响应式数据：修改数据后，页面展示的数据随之更新
        Vue3的数据默认不具备响应性，必须主动设置
    3. 定义Vue的周期函数，加载初始化数据
    4. 定义事件函数，自定义逻辑

*/

/* 1. 导入依赖 */
import { categoryAll, } from '../api/category';
import { articleList, } from '../api/article';

import { ref, reactive, toRefs, onMounted, onUpdated } from "vue";
import { useRouter } from 'vue-router';


/*  2. 定义页面展示数据: 文章集合、类别集合、类别id  */
let categoryListRef = ref([]);
let articleListRef = ref([]);
let cidRef = ref({ cid: null });


/* 3. 定义周期函数(钩子)，初始化数据 */
onMounted(() => {
    getCategoryAll();
    getArticleList();
});


/* 4. 定义事件函数，自定义逻辑 */
/* 4.1 获取所有分类 */
const getCategoryAll = async () => {
    try {
        let { data } = await categoryAll();
        categoryListRef.value = data;
    } catch (error) {
        console.error('加载失败');
    }
};

/* 4.2 获取所有文章 */
const getArticleList = async (cid) => {
    try {
        let { data } = await articleList(cid);
        articleListRef.value = data;
    } catch (error) {
        console.error('加载失败');
    }
};

/* 4.3 跳转到文章详情页面 */
const router = useRouter();
const jumpToArticleDetails = (id) => {
    //传递路径参数
    router.push({ name: 'artDetail', params: { id: id } });
    
    //传递key-value
    // router.push({ path: '/detail', query: { id: id } });
};



</script>

<template>
    <div>
        <header id="header-menu" class="sticky top-0 z-10 flex h-14 bg-white py-3 menu-sticky"
            x-data="{ open : false }">
            <div class="container mx-auto flex h-full justify-between">
                <div class="flex h-full items-center gap-6">
                    <div class="mr-2 h-full">
                        <a href="http://localhost:5173/" class="inline-flex h-full items-center">
                            <img src="../assets/atguigu.jpg" alt="Logo" class="h-full w-auto" />
                        </a>
                    </div>

                    <ul class="hidden items-center gap-8 sm:flex">
                        <li class="relative text-sm" x-data="dropdown">
                            <a class="text-gray-600" href="http://localhost:5173/">首页</a>
                        </li>
                    </ul>
                </div>

                <div class="flex items-center">
                    <a href="http://localhost:5173/#/login" title="发布博文" class="inline-flex h-full items-center">
                        <img src="../assets/publish.jpg" alt="Logo" class="h-full w-auto" />
                        发布文章
                    </a>
                </div>
            </div>
        </header>

        <section>
            <div class="bg-cover bg-center bg-no-repeat h-96" style="
                    background-image: url(https://www.gulixueyuan.com/files/system/2023/04-04/150831f74f62589616.jpg);
                    height: 300px;
                ">
            </div>
        </section>

        <section class="container mx-auto mt-6 grid grid-cols-4 gap-6">
            <div class="col-span-4 sm:col-span-3">

                <!-- 分类栏，点击不同分类，显示对应分类的文章简介卡片 -->
                <ul id="filters" class="flex flex-wrap gap-2">
                    <li>
                        <!-- href="javascript:void(0)" ：点击后，触发事件函数，而不是跳转到href的url -->
                        <a href="javascript:void(0)" @click="getArticleList()">
                            <span class="truncate text-base"> 全部 </span>
                        </a>
                    </li>

                    <li v-for="(category, index) in categoryListRef" :key="index" x-data="dropdown"
                        class="relative cursor-pointer transition-all">
                        <a href="javascript:void(0)" @click="getArticleList(category.cid)">
                            <span class="truncate text-base">{{ category.cname }}</span>
                        </a>
                    </li>

                </ul>


                <!-- 所有文章简介卡片 -->
                <div id="post-list" class="mt-6 grid grid-cols-1 gap-6 md:grid-cols-2">

                    <!-- 一个文章简介卡片 -->
                    <div v-for="(article, index) in articleListRef" :key=index @click=jumpToArticleDetails(article.id)
                        class="overflow-hidden rounded-xl bg-white shadow-md hover:-translate-y-1 hover:ring-2">

                        <!-- 头图 -->
                        <div class="aspect-w-16 aspect-h-9">
                            <a href="javascript:void(0)" :title="article.title">
                                <img src="https://www.gulixueyuan.com/files/course/2021/08-02/151735f51c11714475.png"
                                    class="h-full w-full object-cover transition-all duration-500 group-hover:scale-105" />
                            </a>
                        </div>

                        <!-- 标题和内容 -->
                        <div class="relative flex flex-col gap-2 p-4">
                            <h1 class="text-2xl font-medium">
                                <a href="javascript:void(0)" :title="article.title">{{ article.title }}</a>
                            </h1>
                            <p class="font-sm font-light line-clamp-6">
                                {{ article.content }}
                            </p>
                            <div class="mt-4 flex flex-1 items-center">
                                <a href="javascript:void(0)">
                                    <img src="../assets/atguigu.jpg" class="h-8 w-8 dark:border-slate-700" />
                                </a>
                                <span class="text-sm text-gray-600">发布于 {{ article.createTime }}</span>
                            </div>
                        </div>

                    </div>

                </div>
            </div>

            <aside class="col-span-1 flex-col gap-6 sm:flex">
                <div class="bg-white p-3 shadow transition-all">
                    <div class="flex flex-col items-center">
                        <div class="relative h-24 w-24">
                            <img src="../assets/01.jpg" alt="Java程序员" class="h-full w-full rounded-full" />
                        </div>
                        <div>
                            <h1 class="text-2xl">Java程序员</h1>
                        </div>

                        <div class="grid grid-cols-4 gap-5">
                            <div class="inline-flex flex-col items-center">
                                <span class="text-xl">33</span>
                                <span class="text-xs">文章数</span>
                            </div>
                            <div class="inline-flex flex-col items-center">
                                <span class="text-xl">26</span>
                                <span class="text-xs">分类数</span>
                            </div>

                            <div class="inline-flex flex-col items-center">
                                <span class="text-xl">23403</span>
                                <span class="text-xs">访问量</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="bg-white p-3 shadow">
                    <h2>
                        <span class="text-lg"></span>
                        热门文章
                    </h2>
                    <div>
                        <ul role="list">
                            <li class="py-3">
                                <div class="flex items-center">
                                    <div class="flex flex-col gap-1">
                                        <h3>
                                            <a href="#" title="【MyBatis】MyBatis 和 Hibernate">【MyBatis】MyBatis 和
                                                Hibernate</a>
                                        </h3>
                                        <p>2023-06-26 发布</p>
                                    </div>
                                </div>
                            </li>
                            <li class="py-3">
                                <div class="flex items-center">
                                    <div class="flex flex-col gap-1">
                                        <h3>
                                            <a href="#" title="【对比】Native ui 和 Element Plus">【【对比】Native ui 和 Element
                                                Plus</a>
                                        </h3>
                                        <p>2023-06-13 发布</p>
                                    </div>
                                </div>
                            </li>
                            <li class="py-3">
                                <div class="flex items-center">
                                    <div class="flex flex-col gap-1">
                                        <h3>
                                            <a href="#" title="【毕业季】毕业设计指南">【毕业季】毕业设计指南</a>
                                        </h3>
                                        <p>2023-05-23 发布</p>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </aside>
        </section>
    </div>
</template>

<style lang="scss" scoped>
.login-panel {
    width: 500px;
    margin: 0 auto;
    margin-top: 130px;
}
</style>
