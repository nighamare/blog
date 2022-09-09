<template>
<div>
    <div class="con-wrapper">
        <div class="con-div">
            <h1 style="background: rgba(255, 246, 181, 0.5);">关于本站</h1>
            <div>
                <p>本网站由一个麻瓜站主开发，只会CV弟中弟！</p>
                <p>建立博客的初衷是把我所学所懂记录下来以及收集网络上优秀的文章，俗话说："好记性不如烂笔头"。</p>
                <p>希望此博客能为博主积累技术，也能结识更多的各方面爱好者</p>
                <p>个人博客千千万，属于自己的只有一个！在开发过程中借鉴了很多博客的布局，在此对先驱者表示感谢。</p>
                <p>当您看到此处时，博主很开心又多了一位博主成长历程的见证者</p>
            </div>
            <h1 style="background: rgba(255, 246, 181, 0.5);">关于博主</h1>
            <div>
                <p>枫雪</p>
                <p>99年的咸鱼~</p>
                <p>喜欢在Warframe、LOL、CF以及各种单机游戏中虚度光阴，也是为数不多的乐趣之一</p>
                <p>一事无成，经常emo，对未来很沮丧，也很担忧。</p>
                <p>希望闲暇之余，有时间持续优化此博客</p>
            </div>
            <h1 style="background: rgba(255, 246, 181, 0.5);">网站结构</h1>
            <div>
                <p><span style="font-weight: 700;">域名:</span>木得</p>
                <p><span style="font-weight: 700;">前台页面：</span>Vue2 + Element-ui</p>
                <p><span style="font-weight: 700;">服务器端：</span>Spring boot + Mysql</p>
                <p><span style="font-weight: 700;">网站简介：</span>本站主要的栏目有，标签，分类，在线留言，友情链接以及关于博客。</p>
                <p>本站前后端均为个人书写，个人水平有限，大佬路过还请多多批评指正。</p>
            </div>
            <h1 style="background: rgba(255, 246, 181, 0.5);">特别说明</h1>
            <div>
                <p>本站所有内容仅代表个人观点，和任何组织或个人无关。</p>
                <p>本站内容仅供学习交流，请勿用于任何形式商业行为。</p>
                <p>本站如无意中侵犯了某些组织或个人的知识产权，请联系站主，及时删除。</p>
            </div>
        </div>

    </div>

</div>
</template>

<script>
export default {
    name: 'Category',

}
</script>

<style lang="less">
.con-wrapper {
    padding: 0px;
    background-color: rgba(255, 255, 255, 0.4);
    border: 1px solid #eee;
    width: 1000px;
    margin: auto;

    .con-div {

        h1 {
            color: #737373;
            border-radius: 5px;
            position: relative;
            padding: 8px 35px;
            text-align: center;
            border-bottom: 1px dashed #ddd;
            box-shadow: 0 2px 3px rgba(0, 0, 0, .3);
            font-size: 2em;
            margin: 0.67em .3em;

            h1:before {
                content: "「";
                margin-right: 5px;
                color: #FF6D6D;
            }

            h1:after {
                content: "」";
                margin-left: 5px;
                color: #FF6D6D;
            }

        }

    }
}

.con-div p {
    margin: auto;
    color: #797979;
    padding: 10px 0;
    font-weight: 400;
    font-size: 16px;
    text-indent: 2em;
    letter-spacing: 0.1em;
    line-height: 2.2rem;
    margin: 0 .3em;
}
</style>
