<template>
<div id="app">
    <router-view></router-view>
</div>
</template>

<script>
export default {
    name: 'App',
    data() {
        return {
            msg: 'Welcome to Your Vue.js App'
        }
    },
    methods: {
        note() {
            (function () {
                var a_idx = 0;
                window.onclick = function (event) {
                    var a = new Array("❤富强❤", "❤民主❤", "❤文明❤", "❤和谐❤", "❤自由❤", "❤平等❤", "❤公正❤", "❤法治❤", "❤爱国❤", "❤敬业❤", "❤诚信❤", "❤友善❤");

                    var heart = document.createElement("b"); //创建b元素
                    heart.onselectstart = new Function('event.returnValue=false'); //防止拖动

                    document.body.appendChild(heart).innerHTML = a[a_idx]; //将b元素添加到页面上
                    a_idx = (a_idx + 1) % a.length;
                    heart.style.cssText = "position: fixed;left:-100%;font-family: 楷体"; //给p元素设置样式

                    var f = 12, // 字体大小
                        x = event.clientX - f / 2, // 横坐标
                        y = event.clientY - f, // 纵坐标
                        c = randomColor(), // 随机颜色
                        a = 1, // 透明度
                        s = 1.2; // 放大缩小

                    var timer = setInterval(function () { //添加定时器
                        if (a <= 0) {
                            document.body.removeChild(heart);
                            clearInterval(timer);
                        } else {
                            heart.style.cssText = "font-size:12px;font-family: 楷体;cursor: default;position: fixed;color:" +
                                c + ";left:" + x + "px;top:" + y + "px;opacity:" + a + ";transform:scale(" +
                                s + ");";
                            y--;
                            a -= 0.016;
                            s += 0.002;
                        }
                    }, 15)

                }
                // 随机颜色
                function randomColor() {

                    return "rgb(" + (~~(Math.random() * 255)) + "," + (~~(Math.random() * 255)) + "," + (~~(Math
                        .random() * 255)) + ")";

                }
            }());
        },
        show() {
            ! function () {
                function n(n, e, t) {
                    return n.getAttribute(e) || t
                }

                function e(n) {
                    return document.getElementsByTagName(n)
                }

                function t() {
                    var t = e("script"),
                        o = t.length,
                        i = t[o - 1];
                    return {
                        l: o,
                        z: n(i, "zIndex", -1),
                        o: n(i, "opacity", .5),
                        c: n(i, "color", "0,0,0"),
                        n: n(i, "count", 99)
                    }
                }

                function o() {
                    a = m.width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth, c = m.height = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
                }

                function i() {
                    r.clearRect(0, 0, a, c);
                    var n, e, t, o, m, l;
                    s.forEach(function (i, x) {
                        for (i.x += i.xa, i.y += i.ya, i.xa *= i.x > a || i.x < 0 ? -1 : 1, i.ya *= i.y > c || i.y < 0 ? -1 : 1, r.fillRect(i.x - .5, i.y - .5, 1, 1), e = x + 1; e < u.length; e++) n = u[e], null !== n.x && null !== n.y && (o = i.x - n.x, m = i.y - n.y, l = o * o + m * m, l < n.max && (n === y && l >= n.max / 2 && (i.x -= .03 * o, i.y -= .03 * m), t = (n.max - l) / n.max, r.beginPath(), r.lineWidth = t / 2, r.strokeStyle = "rgba(" + d.c + "," + (t + .2) + ")", r.moveTo(i.x, i.y), r.lineTo(n.x, n.y), r.stroke()))
                    }), x(i)
                }

                var a, c, u, m = document.createElement("canvas"),
                    d = t(),
                    l = "c_n" + d.l,
                    r = m.getContext("2d"),
                    x = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function (n) {
                        window.setTimeout(n, 1e3 / 45)
                    },
                    w = Math.random,
                    y = {
                        x: null,
                        y: null,
                        max: 2e4
                    };
                m.id = l, m.style.cssText = "position:fixed;top:0;left:0;z-index:" + d.z + ";opacity:" + d.o, e("body")[0].appendChild(m), o(), window.onresize = o, window.onmousemove = function (n) {
                    n = n || window.event, y.x = n.clientX, y.y = n.clientY
                }, window.onmouseout = function () {
                    y.x = null, y.y = null
                };
                for (var s = [], f = 0; d.n > f; f++) {
                    var h = w() * a,
                        g = w() * c,
                        v = 2 * w() - 1,
                        p = 2 * w() - 1;
                    s.push({
                        x: h,
                        y: g,
                        xa: v,
                        ya: p,
                        max: 6e3
                    })
                }
                u = s.concat([y]), setTimeout(function () {
                    i()
                }, 100)
            }();
        }
    },
    mounted() {
        this.note()
        this.show()
    }

}
</script>

<style lang="less" scoped>
#app {
    margin: 0;
    padding: 0;
    background: linear-gradient(90deg, rgba(247, 149, 51, .1), rgba(243, 112, 85, .1) 15%, rgba(239, 78, 123, .1) 30%, rgba(161, 102, 171, .1) 44%, rgba(80, 115, 184, .1) 58%, rgba(16, 152, 173, .1) 72%, rgba(7, 179, 155, .1) 86%, rgba(109, 186, 130, .1));
    min-height: 700px;
        height: auto;
}
</style>
