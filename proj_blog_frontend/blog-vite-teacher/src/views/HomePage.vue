<template>
    <div>
      <header id="header-menu" class="sticky top-0 z-10 flex h-14 bg-white py-3 menu-sticky" x-data="{ open : false }">
        <div class="container mx-auto flex h-full justify-between">
          <div class="flex h-full items-center gap-6">
            <div class="mr-2 h-full">
              <a href="http://127.0.0.1:5173/" class="inline-flex h-full items-center">
                <img src="../assets/atguigu.jpg" alt="Logo" class="h-full w-auto">
              </a>
            </div>
          
            <ul class="hidden items-center gap-8 sm:flex">
              <li class="relative text-sm" x-data="dropdown">
                <a class="text-gray-600" href="http://127.0.0.1:5173/">首页</a>  
              </li>
            </ul>
          </div>
        
          <div class="flex items-center">
              <a href="http://127.0.0.1:5173/#/login" title="发布博文" class="inline-flex h-full items-center">
                <img src="../assets/publish.jpg" alt="Logo" class="h-full w-auto">
                发布文章
              </a>
          </div>
        </div>
      </header>
  
      <section>
        <div class="bg-cover bg-center bg-no-repeat h-96" style="background-image: url(https://www.gulixueyuan.com/files/system/2023/04-04/150831f74f62589616.jpg);height:300px;">
        </div>
      </section>
      
      <section class="container mx-auto mt-6 grid grid-cols-4 gap-6">
        <div class="col-span-4 sm:col-span-3">
        <ul id="filters" class="flex flex-wrap gap-2">
          <li>
            <!-- 触发 click -> 不触发 href地址-->
            <a href="javascript:void(0)" @click="getArticleList()">
              <span class="truncate text-base"> 全部 </span>
            </a>
          </li>
        
          <li v-for="category in categoryListRef " x-data="dropdown" class="relative cursor-pointer transition-all">
            <a href="javascript:void(0)" @click="getArticleListByCid(category.cid)">
            <span class="truncate text-base">{{ category.cname }}</span>
            </a>
          </li>
          
        </ul>
  
        <div id="post-list" class="mt-6 grid grid-cols-1 gap-6  md:grid-cols-2">
          
          <div v-for="article in articleListRef" @click="jumpDetail(article.id)" class="overflow-hidden rounded-xl bg-white shadow-md  hover:-translate-y-1 hover:ring-2 ">
            <div class="aspect-w-16 aspect-h-9">
            <a href="javascript:void(0)" :title="article.title">
              <img src="https://www.gulixueyuan.com/files/course/2021/08-02/151735f51c11714475.png" class="h-full w-full object-cover transition-all duration-500 group-hover:scale-105">
            </a>
            </div>
            <div class="relative flex flex-col gap-2 p-4">
            <h1 class="text-2xl font-medium">
              <a href="javascript:void(0)" :title="article.title">{{ article.title }}</a>
            </h1>
            <p class="font-sm font-light line-clamp-6">{{ article.content }}</p>
            <div class="mt-4 flex flex-1 items-center">
              <a href="javascript:void(0)">
              <img src="../assets/atguigu.jpg" class="h-8 w-8 dark:border-slate-700">
              </a>
              <span class="text-sm text-gray-600">发布于:{{ article.createTime }}</span>
            </div>
            </div>
          </div>
            
        </div>
      </div>
        
      <aside class="col-span-1 flex-col gap-6 sm:flex">
        <div class="bg-white p-3 shadow transition-all">
          <div class="flex flex-col items-center ">
          <div class="relative h-24 w-24">
            <img src="../assets/01.jpg" alt="Java程序员" class="h-full w-full rounded-full">
          </div>
          <div><h1 class="text-2xl">Java程序员</h1></div>
          
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
                  <h3 >
                    <a href="#" title="【MyBatis】MyBatis 和 Hibernate">【MyBatis】MyBatis 和 Hibernate</a>
                  </h3>
                  <p>2023-06-26 发布</p>
                </div>
              </div>
            </li>
            <li class="py-3">
              <div class="flex items-center">
                <div class="flex flex-col gap-1">
                  <h3>
                    <a href="#" title="【对比】Native ui 和 Element Plus">【【对比】Native ui 和 Element Plus</a>
                  </h3>
                  <p>2023-06-13 发布</p>
                </div>
              </div>
            </li>
            <li class="py-3">
              <div class="flex items-center">
                <div class="flex flex-col gap-1">
                  <h3 >
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

<script setup>

  /**
   * java 
   *     1.导入依赖[导包]
   *     2.定义一个java类
   *     3.定义全局变量 [接收数据]
   *     4.定义构造函数 [初始化加载的一些工作]
   *     5.定义其他函数 [自定义逻辑]
   * script 
   *     1.导包 
   *     2.定义页面展示的数据 [vue3需要将数据主动定义成响应式数据]
   *                        [vue2默认所有数据都是响应式,vue3需要你指定响应] 
   *                        [响应: 修改数据以后,页面会自动刷新数据的展示内容]
   *                        [let name = 'xxx' -> {{name}} -> xxx| name='jjj'  name不是响应式 ]
   *     3.定义vue初始化周期函数加载页面的初始化数据
   *     4.定义页面的时间函数和方法逻辑
   */
  
   //1.导包  ref | onMounted ->  vue   { articleList } -> api/article  {categoryList} -> api/category
   //       {useRouter [页面跳转],useRoute[目的地接收参数] } -> vue-router
  import { ref,onMounted } from "vue";
  import { articleList} from '../api/article'
  import { categoryList} from '../api/category'
  import { useRouter } from 'vue-router' 

  //2.定义页面展示数据  文章集合  类别集合  类别id
  const articleListRef = ref([]); //文章集合
  const categoryListRef = ref([]); //类别集合
  const cidRef = ref({cid:null}); //类别id

  //3.初始化数据 
  onMounted(()=>{
    //触发此周期,调用内部函数
    getArticleList();
    getCategoryList();
  })


  const getArticleList =async ()=>{
    //初始访问,不传
    let {data} = await articleList();
    articleListRef.value = data;
  }

  const getCategoryList =async()=>{
    let {data} = await categoryList();
    categoryListRef.value = data;
  }


  //4.页面的自定义逻辑 [事件 -> 方法]

  //类别点击触发
  const getArticleListByCid =async (cid)=>{
    //初始访问,不传
    let {data} = await articleList(cid);
    articleListRef.value = data;
  }

  const router = useRouter();

  const jumpDetail = (id)=>{
     //页面挑战
     router.push({ path: '/detail', query: { 'id': id } })
  }



</script>