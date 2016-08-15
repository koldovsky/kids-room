!function (t) {
    "function" == typeof define && define.amd ? define(["jquery", "moment"], t) : "object" == typeof exports ? module.exports = t(require("jquery"), require("moment")) : t(jQuery, moment)
}(function (t, e) {
    function n(t) {
        return Z(t, Ut)
    }

    function i(e) {
        var n, i = {views: e.views || {}};
        return t.each(e, function (e, r) {
            "views" != e && (t.isPlainObject(r) && !/(time|duration|interval)$/i.test(e) && -1 == t.inArray(e, Ut) ? (n = null, t.each(r, function (t, r) {
                /^(month|week|day|default|basic(Week|Day)?|agenda(Week|Day)?)$/.test(t) ? (i.views[t] || (i.views[t] = {}), i.views[t][e] = r) : (n || (n = {}), n[t] = r)
            }), n && (i[e] = n)) : i[e] = r)
        }), i
    }

    function r(t, e) {
        e.left && t.css({"border-left-width": 1, "margin-left": e.left - 1}), e.right && t.css({
            "border-right-width": 1,
            "margin-right": e.right - 1
        })
    }

    function s(t) {
        t.css({"margin-left": "", "margin-right": "", "border-left-width": "", "border-right-width": ""})
    }

    function o() {
        t("body").addClass("fc-not-allowed")
    }

    function a() {
        t("body").removeClass("fc-not-allowed")
    }

    function l(e, n, i) {
        var r = Math.floor(n / e.length), s = Math.floor(n - r * (e.length - 1)), o = [], a = [], l = [], c = 0;
        u(e), e.each(function (n, i) {
            var u = n === e.length - 1 ? s : r, d = t(i).outerHeight(!0);
            u > d ? (o.push(i), a.push(d), l.push(t(i).height())) : c += d
        }), i && (n -= c, r = Math.floor(n / o.length), s = Math.floor(n - r * (o.length - 1))), t(o).each(function (e, n) {
            var i = e === o.length - 1 ? s : r, u = a[e], c = l[e], d = i - (u - c);
            i > u && t(n).height(d)
        })
    }

    function u(t) {
        t.height("")
    }

    function c(e) {
        var n = 0;
        return e.find("> span").each(function (e, i) {
            var r = t(i).outerWidth();
            r > n && (n = r)
        }), n++, e.width(n), n
    }

    function d(t, e) {
        var n, i = t.add(e);
        return i.css({position: "relative", left: -1}), n = t.outerHeight() - e.outerHeight(), i.css({
            position: "",
            left: ""
        }), n
    }

    function h(e) {
        var n = e.css("position"), i = e.parents().filter(function () {
            var e = t(this);
            return /(auto|scroll)/.test(e.css("overflow") + e.css("overflow-y") + e.css("overflow-x"))
        }).eq(0);
        return "fixed" !== n && i.length ? i : t(e[0].ownerDocument || document)
    }

    function f(t, e) {
        var n = t.offset(), i = n.left - (e ? e.left : 0), r = n.top - (e ? e.top : 0);
        return {left: i, right: i + t.outerWidth(), top: r, bottom: r + t.outerHeight()}
    }

    function g(t, e) {
        var n = t.offset(), i = v(t), r = n.left + w(t, "border-left-width") + i.left - (e ? e.left : 0), s = n.top + w(t, "border-top-width") + i.top - (e ? e.top : 0);
        return {left: r, right: r + t[0].clientWidth, top: s, bottom: s + t[0].clientHeight}
    }

    function p(t, e) {
        var n = t.offset(), i = n.left + w(t, "border-left-width") + w(t, "padding-left") - (e ? e.left : 0), r = n.top + w(t, "border-top-width") + w(t, "padding-top") - (e ? e.top : 0);
        return {left: i, right: i + t.width(), top: r, bottom: r + t.height()}
    }

    function v(t) {
        var e = t.innerWidth() - t[0].clientWidth, n = {
            left: 0,
            right: 0,
            top: 0,
            bottom: t.innerHeight() - t[0].clientHeight
        };
        return m() && "rtl" == t.css("direction") ? n.left = e : n.right = e, n
    }

    function m() {
        return null === qt && (qt = y()), qt
    }

    function y() {
        var e = t("<div><div/></div>").css({
            position: "absolute",
            top: -1e3,
            left: 0,
            border: 0,
            padding: 0,
            overflow: "scroll",
            direction: "rtl"
        }).appendTo("body"), n = e.children(), i = n.offset().left > e.offset().left;
        return e.remove(), i
    }

    function w(t, e) {
        return parseFloat(t.css(e)) || 0
    }

    function S(t) {
        return 1 == t.which && !t.ctrlKey
    }

    function E(t) {
        if (void 0 !== t.pageX)return t.pageX;
        var e = t.originalEvent.touches;
        return e ? e[0].pageX : void 0
    }

    function D(t) {
        if (void 0 !== t.pageY)return t.pageY;
        var e = t.originalEvent.touches;
        return e ? e[0].pageY : void 0
    }

    function b(t) {
        return /^touch/.test(t.type)
    }

    function T(t) {
        t.addClass("fc-unselectable").on("selectstart", C)
    }

    function C(t) {
        t.preventDefault()
    }

    function H(t, e) {
        var n = {
            left: Math.max(t.left, e.left),
            right: Math.min(t.right, e.right),
            top: Math.max(t.top, e.top),
            bottom: Math.min(t.bottom, e.bottom)
        };
        return n.left < n.right && n.top < n.bottom ? n : !1
    }

    function R(t, e) {
        return {left: Math.min(Math.max(t.left, e.left), e.right), top: Math.min(Math.max(t.top, e.top), e.bottom)}
    }

    function x(t) {
        return {left: (t.left + t.right) / 2, top: (t.top + t.bottom) / 2}
    }

    function I(t, e) {
        return {left: t.left - e.left, top: t.top - e.top}
    }

    function k(e) {
        var n, i, r = [], s = [];
        for ("string" == typeof e ? s = e.split(/\s*,\s*/) : "function" == typeof e ? s = [e] : t.isArray(e) && (s = e), n = 0; n < s.length; n++)i = s[n], "string" == typeof i ? r.push("-" == i.charAt(0) ? {
            field: i.substring(1),
            order: -1
        } : {field: i, order: 1}) : "function" == typeof i && r.push({func: i});
        return r
    }

    function L(t, e, n) {
        var i, r;
        for (i = 0; i < n.length; i++)if (r = z(t, e, n[i]))return r;
        return 0
    }

    function z(t, e, n) {
        return n.func ? n.func(t, e) : M(t[n.field], e[n.field]) * (n.order || 1)
    }

    function M(e, n) {
        return e || n ? null == n ? -1 : null == e ? 1 : "string" === t.type(e) || "string" === t.type(n) ? String(e).localeCompare(String(n)) : e - n : 0
    }

    function F(t, e) {
        var n, i, r, s, o = t.start, a = t.end, l = e.start, u = e.end;
        return a > l && u > o ? (o >= l ? (n = o.clone(), r = !0) : (n = l.clone(), r = !1), u >= a ? (i = a.clone(), s = !0) : (i = u.clone(), s = !1), {
            start: n,
            end: i,
            isStart: r,
            isEnd: s
        }) : void 0
    }

    function N(t, n) {
        return e.duration({days: t.clone().stripTime().diff(n.clone().stripTime(), "days"), ms: t.time() - n.time()})
    }

    function B(t, n) {
        return e.duration({days: t.clone().stripTime().diff(n.clone().stripTime(), "days")})
    }

    function G(t, n, i) {
        return e.duration(Math.round(t.diff(n, i, !0)), i)
    }

    function V(t, e) {
        var n, i, r;
        for (n = 0; n < Xt.length && (i = Xt[n], r = A(i, t, e), !(r >= 1 && st(r))); n++);
        return i
    }

    function A(t, n, i) {
        return null != i ? i.diff(n, t, !0) : e.isDuration(n) ? n.as(t) : n.end.diff(n.start, t, !0)
    }

    function O(t, e, n) {
        var i;
        return Y(n) ? (e - t) / n : (i = n.asMonths(), Math.abs(i) >= 1 && st(i) ? e.diff(t, "months", !0) / i : e.diff(t, "days", !0) / n.asDays())
    }

    function _(t, e) {
        var n, i;
        return Y(t) || Y(e) ? t / e : (n = t.asMonths(), i = e.asMonths(), Math.abs(n) >= 1 && st(n) && Math.abs(i) >= 1 && st(i) ? n / i : t.asDays() / e.asDays())
    }

    function P(t, n) {
        var i;
        return Y(t) ? e.duration(t * n) : (i = t.asMonths(), Math.abs(i) >= 1 && st(i) ? e.duration({months: i * n}) : e.duration({days: t.asDays() * n}))
    }

    function Y(t) {
        return Boolean(t.hours() || t.minutes() || t.seconds() || t.milliseconds())
    }

    function W(t) {
        return "[object Date]" === Object.prototype.toString.call(t) || t instanceof Date
    }

    function j(t) {
        return /^\d+\:\d+(?:\:\d+\.?(?:\d{3})?)?$/.test(t)
    }

    function Z(t, e) {
        var n, i, r, s, o, a, l = {};
        if (e)for (n = 0; n < e.length; n++) {
            for (i = e[n], r = [], s = t.length - 1; s >= 0; s--)if (o = t[s][i], "object" == typeof o)r.unshift(o); else if (void 0 !== o) {
                l[i] = o;
                break
            }
            r.length && (l[i] = Z(r))
        }
        for (n = t.length - 1; n >= 0; n--) {
            a = t[n];
            for (i in a)i in l || (l[i] = a[i])
        }
        return l
    }

    function U(t) {
        var e = function () {
        };
        return e.prototype = t, new e
    }

    function q(t, e) {
        for (var n in t)X(t, n) && (e[n] = t[n])
    }

    function $(t, e) {
        var n, i, r = ["constructor", "toString", "valueOf"];
        for (n = 0; n < r.length; n++)i = r[n], t[i] !== Object.prototype[i] && (e[i] = t[i])
    }

    function X(t, e) {
        return te.call(t, e)
    }

    function Q(e) {
        return /undefined|null|boolean|number|string/.test(t.type(e))
    }

    function K(e, n, i) {
        if (t.isFunction(e) && (e = [e]), e) {
            var r, s;
            for (r = 0; r < e.length; r++)s = e[r].apply(n, i) || s;
            return s
        }
    }

    function J() {
        for (var t = 0; t < arguments.length; t++)if (void 0 !== arguments[t])return arguments[t]
    }

    function tt(t) {
        return (t + "").replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/'/g, "&#039;").replace(/"/g, "&quot;").replace(/\n/g, "<br />")
    }

    function et(t) {
        return t.replace(/&.*?;/g, "")
    }

    function nt(e) {
        var n = [];
        return t.each(e, function (t, e) {
            null != e && n.push(t + ":" + e)
        }), n.join(";")
    }

    function it(t) {
        return t.charAt(0).toUpperCase() + t.slice(1)
    }

    function rt(t, e) {
        return t - e
    }

    function st(t) {
        return t % 1 === 0
    }

    function ot(t, e) {
        var n = t[e];
        return function () {
            return n.apply(t, arguments)
        }
    }

    function at(t, e, n) {
        var i, r, s, o, a, l = function () {
            var u = +new Date - o;
            e > u ? i = setTimeout(l, e - u) : (i = null, n || (a = t.apply(s, r), s = r = null))
        };
        return function () {
            s = this, r = arguments, o = +new Date;
            var u = n && !i;
            return i || (i = setTimeout(l, e)), u && (a = t.apply(s, r), s = r = null), a
        }
    }

    function lt(n, i, r) {
        var s, o, a, l, u = n[0], c = 1 == n.length && "string" == typeof u;
        return e.isMoment(u) ? (l = e.apply(null, n), ct(u, l)) : W(u) || void 0 === u ? l = e.apply(null, n) : (s = !1, o = !1, c ? ee.test(u) ? (u += "-01", n = [u], s = !0, o = !0) : (a = ne.exec(u)) && (s = !a[5], o = !0) : t.isArray(u) && (o = !0), l = i || s ? e.utc.apply(e, n) : e.apply(null, n), s ? (l._ambigTime = !0, l._ambigZone = !0) : r && (o ? l._ambigZone = !0 : c && (l.utcOffset ? l.utcOffset(u) : l.zone(u)))), l._fullCalendar = !0, l
    }

    function ut(t, n) {
        var i, r, s = !1, o = !1, a = t.length, l = [];
        for (i = 0; a > i; i++)r = t[i], e.isMoment(r) || (r = jt.moment.parseZone(r)), s = s || r._ambigTime, o = o || r._ambigZone, l.push(r);
        for (i = 0; a > i; i++)r = l[i], n || !s || r._ambigTime ? o && !r._ambigZone && (l[i] = r.clone().stripZone()) : l[i] = r.clone().stripTime();
        return l
    }

    function ct(t, e) {
        t._ambigTime ? e._ambigTime = !0 : e._ambigTime && (e._ambigTime = !1), t._ambigZone ? e._ambigZone = !0 : e._ambigZone && (e._ambigZone = !1)
    }

    function dt(t, e) {
        t.year(e[0] || 0).month(e[1] || 0).date(e[2] || 0).hours(e[3] || 0).minutes(e[4] || 0).seconds(e[5] || 0).milliseconds(e[6] || 0)
    }

    function ht(t, e) {
        return re.format.call(t, e)
    }

    function ft(t, e) {
        return gt(t, wt(e))
    }

    function gt(t, e) {
        var n, i = "";
        for (n = 0; n < e.length; n++)i += pt(t, e[n]);
        return i
    }

    function pt(t, e) {
        var n, i;
        return "string" == typeof e ? e : (n = e.token) ? se[n] ? se[n](t) : ht(t, n) : e.maybe && (i = gt(t, e.maybe), i.match(/[1-9]/)) ? i : ""
    }

    function vt(t, e, n, i, r) {
        var s;
        return t = jt.moment.parseZone(t), e = jt.moment.parseZone(e), s = (t.localeData || t.lang).call(t), n = s.longDateFormat(n) || n, i = i || " - ", mt(t, e, wt(n), i, r)
    }

    function mt(t, e, n, i, r) {
        var s, o, a, l, u = t.clone().stripZone(), c = e.clone().stripZone(), d = "", h = "", f = "", g = "", p = "";
        for (o = 0; o < n.length && (s = yt(t, e, u, c, n[o]), s !== !1); o++)d += s;
        for (a = n.length - 1; a > o && (s = yt(t, e, u, c, n[a]), s !== !1); a--)h = s + h;
        for (l = o; a >= l; l++)f += pt(t, n[l]), g += pt(e, n[l]);
        return (f || g) && (p = r ? g + i + f : f + i + g), d + p + h
    }

    function yt(t, e, n, i, r) {
        var s, o;
        return "string" == typeof r ? r : (s = r.token) && (o = oe[s.charAt(0)], o && n.isSame(i, o)) ? ht(t, s) : !1
    }

    function wt(t) {
        return t in ae ? ae[t] : ae[t] = St(t)
    }

    function St(t) {
        for (var e, n = [], i = /\[([^\]]*)\]|\(([^\)]*)\)|(LTS|LT|(\w)\4*o?)|([^\w\[\(]+)/g; e = i.exec(t);)e[1] ? n.push(e[1]) : e[2] ? n.push({maybe: St(e[2])}) : e[3] ? n.push({token: e[3]}) : e[5] && n.push(e[5]);
        return n
    }

    function Et() {
    }

    function Dt(t, e) {
        var n;
        return X(e, "constructor") && (n = e.constructor), "function" != typeof n && (n = e.constructor = function () {
            t.apply(this, arguments)
        }), n.prototype = U(t.prototype), q(e, n.prototype), $(e, n.prototype), q(t, n), n
    }

    function bt(t, e) {
        q(e, t.prototype)
    }

    function Tt(t, e) {
        return t || e ? t && e ? t.component === e.component && Ct(t, e) && Ct(e, t) : !1 : !0
    }

    function Ct(t, e) {
        for (var n in t)if (!/^(component|left|right|top|bottom)$/.test(n) && t[n] !== e[n])return !1;
        return !0
    }

    function Ht(t) {
        var e = xt(t);
        return "background" === e || "inverse-background" === e
    }

    function Rt(t) {
        return "inverse-background" === xt(t)
    }

    function xt(t) {
        return J((t.source || {}).rendering, t.rendering)
    }

    function It(t) {
        var e, n, i = {};
        for (e = 0; e < t.length; e++)n = t[e], (i[n._id] || (i[n._id] = [])).push(n);
        return i
    }

    function kt(t, e) {
        return t.start - e.start
    }

    function Lt(n) {
        var i, r, s, o, a = jt.dataAttrPrefix;
        return a && (a += "-"), i = n.data(a + "event") || null, i && (i = "object" == typeof i ? t.extend({}, i) : {}, r = i.start, null == r && (r = i.time), s = i.duration, o = i.stick, delete i.start, delete i.time, delete i.duration, delete i.stick), null == r && (r = n.data(a + "start")), null == r && (r = n.data(a + "time")), null == s && (s = n.data(a + "duration")), null == o && (o = n.data(a + "stick")), r = null != r ? e.duration(r) : null, s = null != s ? e.duration(s) : null, o = Boolean(o), {
            eventProps: i,
            startTime: r,
            duration: s,
            stick: o
        }
    }

    function zt(t, e) {
        var n, i;
        for (n = 0; n < e.length; n++)if (i = e[n], i.leftCol <= t.rightCol && i.rightCol >= t.leftCol)return !0;
        return !1
    }

    function Mt(t, e) {
        return t.leftCol - e.leftCol
    }

    function Ft(t) {
        var e, n, i, r = [];
        for (e = 0; e < t.length; e++) {
            for (n = t[e], i = 0; i < r.length && Gt(n, r[i]).length; i++);
            n.level = i, (r[i] || (r[i] = [])).push(n)
        }
        return r
    }

    function Nt(t) {
        var e, n, i, r, s;
        for (e = 0; e < t.length; e++)for (n = t[e], i = 0; i < n.length; i++)for (r = n[i], r.forwardSegs = [], s = e + 1; s < t.length; s++)Gt(r, t[s], r.forwardSegs)
    }

    function Bt(t) {
        var e, n, i = t.forwardSegs, r = 0;
        if (void 0 === t.forwardPressure) {
            for (e = 0; e < i.length; e++)n = i[e], Bt(n), r = Math.max(r, 1 + n.forwardPressure);
            t.forwardPressure = r
        }
    }

    function Gt(t, e, n) {
        n = n || [];
        for (var i = 0; i < e.length; i++)Vt(t, e[i]) && n.push(e[i]);
        return n
    }

    function Vt(t, e) {
        return t.bottom > e.top && t.top < e.bottom
    }

    function At(n, i) {
        function r() {
            j ? a() && (c(), l()) : s()
        }

        function s() {
            Z = O.theme ? "ui" : "fc", n.addClass("fc"), n.addClass(A.isTouch ? "fc-touch" : "fc-cursor"), O.isRTL ? n.addClass("fc-rtl") : n.addClass("fc-ltr"), O.theme ? n.addClass("ui-widget") : n.addClass("fc-unthemed"), j = t("<div class='fc-view-container'/>").prependTo(n), Y = A.header = new Pt(A, O), W = Y.render(), W && n.prepend(W), l(O.defaultView), O.handleWindowResize && (X = at(h, O.windowResizeDelay), t(window).resize(X))
        }

        function o() {
            q && q.removeElement(), Y.removeElement(), j.remove(), n.removeClass("fc fc-touch fc-cursor fc-ltr fc-rtl fc-unthemed ui-widget"), X && t(window).unbind("resize", X)
        }

        function a() {
            return n.is(":visible")
        }

        function l(e) {
            nt++, q && e && q.type !== e && (Y.deactivateButton(q.type), M(), q.removeElement(), q = A.view = null), !q && e && (q = A.view = et[e] || (et[e] = A.instantiateView(e)), q.setElement(t("<div class='fc-view fc-" + e + "-view' />").appendTo(j)), Y.activateButton(e)), q && (Q = q.massageCurrentDate(Q), q.displaying && Q.isWithin(q.intervalStart, q.intervalEnd) || a() && (q.display(Q), F(), S(), E(), v())), F(), nt--
        }

        function u(t) {
            return a() ? (t && d(), nt++, q.updateSize(!0), nt--, !0) : void 0
        }

        function c() {
            a() && d()
        }

        function d() {
            $ = "number" == typeof O.contentHeight ? O.contentHeight : "number" == typeof O.height ? O.height - (W ? W.outerHeight(!0) : 0) : Math.round(j.width() / Math.max(O.aspectRatio, .5))
        }

        function h(t) {
            !nt && t.target === window && q.start && u(!0) && q.trigger("windowResize", tt)
        }

        function f() {
            p(), m()
        }

        function g() {
            a() && (M(), q.displayEvents(it), F())
        }

        function p() {
            M(), q.clearEvents(), F()
        }

        function v() {
            !O.lazyFetching || K(q.start, q.end) ? m() : g()
        }

        function m() {
            J(q.start, q.end)
        }

        function y(t) {
            it = t, g()
        }

        function w() {
            g()
        }

        function S() {
            Y.updateTitle(q.title)
        }

        function E() {
            var t = A.getNow();
            t.isWithin(q.intervalStart, q.intervalEnd) ? Y.disableButton("today") : Y.enableButton("today")
        }

        function D(t, e) {
            q.select(A.buildSelectSpan.apply(A, arguments))
        }

        function b() {
            q && q.unselect()
        }

        function T() {
            Q = q.computePrevDate(Q), l()
        }

        function C() {
            Q = q.computeNextDate(Q), l()
        }

        function H() {
            Q.add(-1, "years"), l()
        }

        function R() {
            Q.add(1, "years"), l()
        }

        function x() {
            Q = A.getNow(), l()
        }

        function I(t) {
            Q = A.moment(t).stripZone(), l()
        }

        function k(t) {
            Q.add(e.duration(t)), l()
        }

        function L(t, e) {
            var n;
            e = e || "day", n = A.getViewSpec(e) || A.getUnitViewSpec(e), Q = t.clone(), l(n ? n.type : null)
        }

        function z() {
            return A.applyTimezone(Q)
        }

        function M() {
            j.css({width: "100%", height: j.height(), overflow: "hidden"})
        }

        function F() {
            j.css({width: "", height: "", overflow: ""})
        }

        function N() {
            return A
        }

        function B() {
            return q
        }

        function G(t, e) {
            return void 0 === e ? O[t] : void(("height" == t || "contentHeight" == t || "aspectRatio" == t) && (O[t] = e, u(!0)))
        }

        function V(t, e) {
            var n = Array.prototype.slice.call(arguments, 2);
            return e = e || tt, this.triggerWith(t, e, n), O[t] ? O[t].apply(e, n) : void 0
        }

        var A = this;
        A.initOptions(i || {});
        var O = this.options;
        A.render = r, A.destroy = o, A.refetchEvents = f, A.reportEvents = y, A.reportEventChange = w, A.rerenderEvents = g, A.changeView = l, A.select = D, A.unselect = b, A.prev = T, A.next = C, A.prevYear = H, A.nextYear = R, A.today = x, A.gotoDate = I, A.incrementDate = k, A.zoomTo = L, A.getDate = z, A.getCalendar = N, A.getView = B, A.option = G, A.trigger = V;
        var _ = U(_t(O.lang));
        if (O.monthNames && (_._months = O.monthNames), O.monthNamesShort && (_._monthsShort = O.monthNamesShort), O.dayNames && (_._weekdays = O.dayNames), O.dayNamesShort && (_._weekdaysShort = O.dayNamesShort), null != O.firstDay) {
            var P = U(_._week);
            P.dow = O.firstDay, _._week = P
        }
        _._fullCalendar_weekCalc = function (t) {
            return "function" == typeof t ? t : "local" === t ? t : "iso" === t || "ISO" === t ? "ISO" : void 0
        }(O.weekNumberCalculation), A.defaultAllDayEventDuration = e.duration(O.defaultAllDayEventDuration), A.defaultTimedEventDuration = e.duration(O.defaultTimedEventDuration), A.moment = function () {
            var t;
            return "local" === O.timezone ? (t = jt.moment.apply(null, arguments), t.hasTime() && t.local()) : t = "UTC" === O.timezone ? jt.moment.utc.apply(null, arguments) : jt.moment.parseZone.apply(null, arguments), "_locale" in t ? t._locale = _ : t._lang = _, t
        }, A.getIsAmbigTimezone = function () {
            return "local" !== O.timezone && "UTC" !== O.timezone
        }, A.applyTimezone = function (t) {
            if (!t.hasTime())return t.clone();
            var e, n = A.moment(t.toArray()), i = t.time() - n.time();
            return i && (e = n.clone().add(i), t.time() - e.time() === 0 && (n = e)), n
        }, A.getNow = function () {
            var t = O.now;
            return "function" == typeof t && (t = t()), A.moment(t).stripZone()
        }, A.getEventEnd = function (t) {
            return t.end ? t.end.clone() : A.getDefaultEventEnd(t.allDay, t.start)
        }, A.getDefaultEventEnd = function (t, e) {
            var n = e.clone();
            return t ? n.stripTime().add(A.defaultAllDayEventDuration) : n.add(A.defaultTimedEventDuration), A.getIsAmbigTimezone() && n.stripZone(), n
        }, A.humanizeDuration = function (t) {
            return (t.locale || t.lang).call(t, O.lang).humanize()
        }, Yt.call(A, O);
        var Y, W, j, Z, q, $, X, Q, K = A.isFetchNeeded, J = A.fetchEvents, tt = n[0], et = {}, nt = 0, it = [];
        Q = null != O.defaultDate ? A.moment(O.defaultDate).stripZone() : A.getNow(), A.getSuggestedViewHeight = function () {
            return void 0 === $ && c(), $
        }, A.isHeightAuto = function () {
            return "auto" === O.contentHeight || "auto" === O.height
        }, A.freezeContentHeight = M, A.unfreezeContentHeight = F, A.initialize()
    }

    function Ot(e) {
        t.each(Ce, function (t, n) {
            null == e[t] && (e[t] = n(e))
        })
    }

    function _t(t) {
        var n = e.localeData || e.langData;
        return n.call(e, t) || n.call(e, "en")
    }

    function Pt(e, n) {
        function i() {
            var e = n.header;
            return f = n.theme ? "ui" : "fc", e ? g = t("<div class='fc-toolbar'/>").append(s("left")).append(s("right")).append(s("center")).append('<div class="fc-clear"/>') : void 0
        }

        function r() {
            g.remove(), g = t()
        }

        function s(i) {
            var r = t('<div class="fc-' + i + '"/>'), s = n.header[i];
            return s && t.each(s.split(" "), function (i) {
                var s, o = t(), a = !0;
                t.each(this.split(","), function (i, r) {
                    var s, l, u, c, d, h, g, v, m, y;
                    "title" == r ? (o = o.add(t("<h2>&nbsp;</h2>")), a = !1) : ((s = (e.options.customButtons || {})[r]) ? (u = function (t) {
                        s.click && s.click.call(y[0], t)
                    }, c = "", d = s.text) : (l = e.getViewSpec(r)) ? (u = function () {
                        e.changeView(r)
                    }, p.push(r), c = l.buttonTextOverride, d = l.buttonTextDefault) : e[r] && (u = function () {
                        e[r]()
                    }, c = (e.overrides.buttonText || {})[r], d = n.buttonText[r]), u && (h = s ? s.themeIcon : n.themeButtonIcons[r], g = s ? s.icon : n.buttonIcons[r], v = c ? tt(c) : h && n.theme ? "<span class='ui-icon ui-icon-" + h + "'></span>" : g && !n.theme ? "<span class='fc-icon fc-icon-" + g + "'></span>" : tt(d), m = ["fc-" + r + "-button", f + "-button", f + "-state-default"], y = t('<button type="button" class="' + m.join(" ") + '">' + v + "</button>").click(function (t) {
                        y.hasClass(f + "-state-disabled") || (u(t), (y.hasClass(f + "-state-active") || y.hasClass(f + "-state-disabled")) && y.removeClass(f + "-state-hover"))
                    }).mousedown(function () {
                        y.not("." + f + "-state-active").not("." + f + "-state-disabled").addClass(f + "-state-down")
                    }).mouseup(function () {
                        y.removeClass(f + "-state-down")
                    }).hover(function () {
                        y.not("." + f + "-state-active").not("." + f + "-state-disabled").addClass(f + "-state-hover")
                    }, function () {
                        y.removeClass(f + "-state-hover").removeClass(f + "-state-down")
                    }), o = o.add(y)))
                }), a && o.first().addClass(f + "-corner-left").end().last().addClass(f + "-corner-right").end(), o.length > 1 ? (s = t("<div/>"), a && s.addClass("fc-button-group"), s.append(o), r.append(s)) : r.append(o)
            }), r
        }

        function o(t) {
            g.find("h2").text(t)
        }

        function a(t) {
            g.find(".fc-" + t + "-button").addClass(f + "-state-active")
        }

        function l(t) {
            g.find(".fc-" + t + "-button").removeClass(f + "-state-active")
        }

        function u(t) {
            g.find(".fc-" + t + "-button").attr("disabled", "disabled").addClass(f + "-state-disabled")
        }

        function c(t) {
            g.find(".fc-" + t + "-button").removeAttr("disabled").removeClass(f + "-state-disabled")
        }

        function d() {
            return p
        }

        var h = this;
        h.render = i, h.removeElement = r, h.updateTitle = o, h.activateButton = a, h.deactivateButton = l, h.disableButton = u, h.enableButton = c, h.getViewsWithButtons = d;
        var f, g = t(), p = []
    }

    function Yt(n) {
        function i(t, e) {
            return !F || F > t || e > V
        }

        function r(t, e) {
            F = t, V = e, W = [];
            var n = ++P, i = _.length;
            Y = i;
            for (var r = 0; i > r; r++)s(_[r], n)
        }

        function s(e, n) {
            o(e, function (i) {
                var r, s, o, a = t.isArray(e.events);
                if (n == P) {
                    if (i)for (r = 0; r < i.length; r++)s = i[r], o = a ? s : y(s, e), o && W.push.apply(W, D(o));
                    Y--, Y || A(W)
                }
            })
        }

        function o(e, i) {
            var r, s, a = jt.sourceFetchers;
            for (r = 0; r < a.length; r++) {
                if (s = a[r].call(M, e, F.clone(), V.clone(), n.timezone, i), s === !0)return;
                if ("object" == typeof s)return void o(s, i)
            }
            var l = e.events;
            if (l)t.isFunction(l) ? (M.pushLoading(), l.call(M, F.clone(), V.clone(), n.timezone, function (t) {
                i(t), M.popLoading()
            })) : t.isArray(l) ? i(l) : i(); else {
                var u = e.url;
                if (u) {
                    var c, d = e.success, h = e.error, f = e.complete;
                    c = t.isFunction(e.data) ? e.data() : e.data;
                    var g = t.extend({}, c || {}), p = J(e.startParam, n.startParam), v = J(e.endParam, n.endParam), m = J(e.timezoneParam, n.timezoneParam);
                    p && (g[p] = F.format()), v && (g[v] = V.format()), n.timezone && "local" != n.timezone && (g[m] = n.timezone), M.pushLoading(), t.ajax(t.extend({}, He, e, {
                        data: g,
                        success: function (e) {
                            e = e || [];
                            var n = K(d, this, arguments);
                            t.isArray(n) && (e = n), i(e)
                        },
                        error: function () {
                            K(h, this, arguments), i()
                        },
                        complete: function () {
                            K(f, this, arguments), M.popLoading()
                        }
                    }))
                } else i()
            }
        }

        function a(t) {
            var e = l(t);
            e && (_.push(e), Y++, s(e, P))
        }

        function l(e) {
            var n, i, r = jt.sourceNormalizers;
            if (t.isFunction(e) || t.isArray(e) ? n = {events: e} : "string" == typeof e ? n = {url: e} : "object" == typeof e && (n = t.extend({}, e)), n) {
                for (n.className ? "string" == typeof n.className && (n.className = n.className.split(/\s+/)) : n.className = [], t.isArray(n.events) && (n.origArray = n.events, n.events = t.map(n.events, function (t) {
                    return y(t, n)
                })), i = 0; i < r.length; i++)r[i].call(M, n);
                return n
            }
        }

        function u(e) {
            _ = t.grep(_, function (t) {
                return !c(t, e)
            }), W = t.grep(W, function (t) {
                return !c(t.source, e)
            }), A(W)
        }

        function c(t, e) {
            return t && e && d(t) == d(e)
        }

        function d(t) {
            return ("object" == typeof t ? t.origArray || t.googleCalendarId || t.url || t.events : null) || t
        }

        function h(t) {
            t.start = M.moment(t.start), t.end ? t.end = M.moment(t.end) : t.end = null, b(t, f(t)), A(W)
        }

        function f(e) {
            var n = {};
            return t.each(e, function (t, e) {
                g(t) && void 0 !== e && Q(e) && (n[t] = e)
            }), n
        }

        function g(t) {
            return !/^_|^(id|allDay|start|end)$/.test(t)
        }

        function p(t, e) {
            var n, i, r, s = y(t);
            if (s) {
                for (n = D(s), i = 0; i < n.length; i++)r = n[i], r.source || (e && (O.events.push(r), r.source = O), W.push(r));
                return A(W), n
            }
            return []
        }

        function v(e) {
            var n, i;
            for (null == e ? e = function () {
                return !0
            } : t.isFunction(e) || (n = e + "", e = function (t) {
                return t._id == n
            }), W = t.grep(W, e, !0), i = 0; i < _.length; i++)t.isArray(_[i].events) && (_[i].events = t.grep(_[i].events, e, !0));
            A(W)
        }

        function m(e) {
            return t.isFunction(e) ? t.grep(W, e) : null != e ? (e += "", t.grep(W, function (t) {
                return t._id == e
            })) : W
        }

        function y(i, r) {
            var s, o, a, l = {};
            if (n.eventDataTransform && (i = n.eventDataTransform(i)), r && r.eventDataTransform && (i = r.eventDataTransform(i)), t.extend(l, i), r && (l.source = r), l._id = i._id || (void 0 === i.id ? "_fc" + Re++ : i.id + ""), i.className ? "string" == typeof i.className ? l.className = i.className.split(/\s+/) : l.className = i.className : l.className = [], s = i.start || i.date, o = i.end, j(s) && (s = e.duration(s)), j(o) && (o = e.duration(o)), i.dow || e.isDuration(s) || e.isDuration(o))l.start = s ? e.duration(s) : null, l.end = o ? e.duration(o) : null, l._recurring = !0; else {
                if (s && (s = M.moment(s), !s.isValid()))return !1;
                o && (o = M.moment(o), o.isValid() || (o = null)), a = i.allDay, void 0 === a && (a = J(r ? r.allDayDefault : void 0, n.allDayDefault)), w(s, o, a, l)
            }
            return l
        }

        function w(t, e, n, i) {
            i.start = t, i.end = e, i.allDay = n, S(i), Wt(i)
        }

        function S(t) {
            E(t), t.end && !t.end.isAfter(t.start) && (t.end = null), t.end || (n.forceEventDuration ? t.end = M.getDefaultEventEnd(t.allDay, t.start) : t.end = null)
        }

        function E(t) {
            null == t.allDay && (t.allDay = !(t.start.hasTime() || t.end && t.end.hasTime())), t.allDay ? (t.start.stripTime(), t.end && t.end.stripTime()) : (t.start.hasTime() || (t.start = M.applyTimezone(t.start.time(0))), t.end && !t.end.hasTime() && (t.end = M.applyTimezone(t.end.time(0))))
        }

        function D(e, n, i) {
            var r, s, o, a, l, u, c, d, h, f = [];
            if (n = n || F, i = i || V, e)if (e._recurring) {
                if (s = e.dow)for (r = {}, o = 0; o < s.length; o++)r[s[o]] = !0;
                for (a = n.clone().stripTime(); a.isBefore(i);)(!r || r[a.day()]) && (l = e.start, u = e.end, c = a.clone(), d = null, l && (c = c.time(l)), u && (d = a.clone().time(u)), h = t.extend({}, e), w(c, d, !l && !u, h), f.push(h)), a.add(1, "days")
            } else f.push(e);
            return f
        }

        function b(e, n, i) {
            function r(t, e) {
                return i ? G(t, e, i) : n.allDay ? B(t, e) : N(t, e)
            }

            var s, o, a, l, u, c, d = {};
            return n = n || {}, n.start || (n.start = e.start.clone()), void 0 === n.end && (n.end = e.end ? e.end.clone() : null), null == n.allDay && (n.allDay = e.allDay), S(n), s = {
                start: e._start.clone(),
                end: e._end ? e._end.clone() : M.getDefaultEventEnd(e._allDay, e._start),
                allDay: n.allDay
            }, S(s), o = null !== e._end && null === n.end, a = r(n.start, s.start), n.end ? (l = r(n.end, s.end), u = l.subtract(a)) : u = null, t.each(n, function (t, e) {
                g(t) && void 0 !== e && (d[t] = e)
            }), c = T(m(e._id), o, n.allDay, a, u, d), {dateDelta: a, durationDelta: u, undo: c}
        }

        function T(e, n, i, r, s, o) {
            var a = M.getIsAmbigTimezone(), l = [];
            return r && !r.valueOf() && (r = null), s && !s.valueOf() && (s = null), t.each(e, function (e, u) {
                var c, d;
                c = {
                    start: u.start.clone(),
                    end: u.end ? u.end.clone() : null,
                    allDay: u.allDay
                }, t.each(o, function (t) {
                    c[t] = u[t]
                }), d = {
                    start: u._start,
                    end: u._end,
                    allDay: i
                }, S(d), n ? d.end = null : s && !d.end && (d.end = M.getDefaultEventEnd(d.allDay, d.start)), r && (d.start.add(r), d.end && d.end.add(r)), s && d.end.add(s), a && !d.allDay && (r || s) && (d.start.stripZone(), d.end && d.end.stripZone()), t.extend(u, o, d), Wt(u), l.push(function () {
                    t.extend(u, c), Wt(u)
                })
            }), function () {
                for (var t = 0; t < l.length; t++)l[t]()
            }
        }

        function C(e) {
            var i, r = n.businessHours, s = {
                className: "fc-nonbusiness",
                start: "09:00",
                end: "17:00",
                dow: [1, 2, 3, 4, 5],
                rendering: "inverse-background"
            }, o = M.getView();
            return r && (i = t.extend({}, s, "object" == typeof r ? r : {})), i ? (e && (i.start = null, i.end = null), D(y(i), o.start, o.end)) : []
        }

        function H(t, e) {
            var i = e.source || {}, r = J(e.constraint, i.constraint, n.eventConstraint), s = J(e.overlap, i.overlap, n.eventOverlap);
            return I(t, r, s, e)
        }

        function R(e, n, i) {
            var r, s;
            return i && (r = t.extend({}, i, n), s = D(y(r))[0]), s ? H(e, s) : x(e)
        }

        function x(t) {
            return I(t, n.selectConstraint, n.selectOverlap)
        }

        function I(t, e, n, i) {
            var r, s, o, a, l, u;
            if (null != e) {
                for (r = k(e), s = !1, a = 0; a < r.length; a++)if (L(r[a], t)) {
                    s = !0;
                    break
                }
                if (!s)return !1
            }
            for (o = M.getPeerEvents(t, i), a = 0; a < o.length; a++)if (l = o[a], z(l, t)) {
                if (n === !1)return !1;
                if ("function" == typeof n && !n(l, i))return !1;
                if (i) {
                    if (u = J(l.overlap, (l.source || {}).overlap), u === !1)return !1;
                    if ("function" == typeof u && !u(i, l))return !1
                }
            }
            return !0
        }

        function k(t) {
            return "businessHours" === t ? C() : "object" == typeof t ? D(y(t)) : m(t)
        }

        function L(t, e) {
            var n = t.start.clone().stripZone(), i = M.getEventEnd(t).stripZone();
            return e.start >= n && e.end <= i
        }

        function z(t, e) {
            var n = t.start.clone().stripZone(), i = M.getEventEnd(t).stripZone();
            return e.start < i && e.end > n
        }

        var M = this;
        M.isFetchNeeded = i, M.fetchEvents = r, M.addEventSource = a, M.removeEventSource = u, M.updateEvent = h, M.renderEvent = p, M.removeEvents = v, M.clientEvents = m, M.mutateEvent = b, M.normalizeEventDates = S, M.normalizeEventTimes = E;
        var F, V, A = M.reportEvents, O = {events: []}, _ = [O], P = 0, Y = 0, W = [];
        t.each((n.events ? [n.events] : []).concat(n.eventSources || []), function (t, e) {
            var n = l(e);
            n && _.push(n)
        }), M.getBusinessHoursEvents = C, M.isEventSpanAllowed = H, M.isExternalSpanAllowed = R, M.isSelectionSpanAllowed = x, M.getEventCache = function () {
            return W
        }
    }

    function Wt(t) {
        t._allDay = t.allDay, t._start = t.start.clone(), t._end = t.end ? t.end.clone() : null
    }

    var jt = t.fullCalendar = {version: "2.7.1", internalApiVersion: 3}, Zt = jt.views = {};
    jt.isTouch = "ontouchstart" in document, t.fn.fullCalendar = function (e) {
        var n = Array.prototype.slice.call(arguments, 1), i = this;
        return this.each(function (r, s) {
            var o, a = t(s), l = a.data("fullCalendar");
            "string" == typeof e ? l && t.isFunction(l[e]) && (o = l[e].apply(l, n), r || (i = o), "destroy" === e && a.removeData("fullCalendar")) : l || (l = new Ee(a, e), a.data("fullCalendar", l), l.render())
        }), i
    };
    var Ut = ["header", "buttonText", "buttonIcons", "themeButtonIcons"];
    jt.intersectRanges = F, jt.applyAll = K, jt.debounce = at, jt.isInt = st, jt.htmlEscape = tt, jt.cssToStr = nt, jt.proxy = ot, jt.capitaliseFirstLetter = it, jt.getOuterRect = f, jt.getClientRect = g, jt.getContentRect = p, jt.getScrollbarWidths = v;
    var qt = null;
    jt.preventDefault = C, jt.intersectRects = H, jt.parseFieldSpecs = k, jt.compareByFieldSpecs = L, jt.compareByFieldSpec = z, jt.flexibleCompare = M, jt.computeIntervalUnit = V, jt.divideRangeByDuration = O, jt.divideDurationByDuration = _, jt.multiplyDuration = P, jt.durationHasTime = Y;
    var $t = ["sun", "mon", "tue", "wed", "thu", "fri", "sat"], Xt = ["year", "month", "week", "day", "hour", "minute", "second", "millisecond"];
    jt.log = function () {
        var t = window.console;
        return t && t.log ? t.log.apply(t, arguments) : void 0
    }, jt.warn = function () {
        var t = window.console;
        return t && t.warn ? t.warn.apply(t, arguments) : jt.log.apply(jt, arguments)
    };
    var Qt, Kt, Jt, te = {}.hasOwnProperty, ee = /^\s*\d{4}-\d\d$/, ne = /^\s*\d{4}-(?:(\d\d-\d\d)|(W\d\d$)|(W\d\d-\d)|(\d\d\d))((T| )(\d\d(:\d\d(:\d\d(\.\d+)?)?)?)?)?$/, ie = e.fn, re = t.extend({}, ie);
    jt.moment = function () {
        return lt(arguments)
    }, jt.moment.utc = function () {
        var t = lt(arguments, !0);
        return t.hasTime() && t.utc(), t
    }, jt.moment.parseZone = function () {
        return lt(arguments, !0, !0)
    }, ie.clone = function () {
        var t = re.clone.apply(this, arguments);
        return ct(this, t), this._fullCalendar && (t._fullCalendar = !0), t
    }, ie.week = ie.weeks = function (t) {
        var e = (this._locale || this._lang)._fullCalendar_weekCalc;
        return null == t && "function" == typeof e ? e(this) : "ISO" === e ? re.isoWeek.apply(this, arguments) : re.week.apply(this, arguments)
    }, ie.time = function (t) {
        if (!this._fullCalendar)return re.time.apply(this, arguments);
        if (null == t)return e.duration({
            hours: this.hours(),
            minutes: this.minutes(),
            seconds: this.seconds(),
            milliseconds: this.milliseconds()
        });
        this._ambigTime = !1, e.isDuration(t) || e.isMoment(t) || (t = e.duration(t));
        var n = 0;
        return e.isDuration(t) && (n = 24 * Math.floor(t.asDays())), this.hours(n + t.hours()).minutes(t.minutes()).seconds(t.seconds()).milliseconds(t.milliseconds())
    }, ie.stripTime = function () {
        var t;
        return this._ambigTime || (t = this.toArray(), this.utc(), Kt(this, t.slice(0, 3)), this._ambigTime = !0, this._ambigZone = !0), this
    }, ie.hasTime = function () {
        return !this._ambigTime
    }, ie.stripZone = function () {
        var t, e;
        return this._ambigZone || (t = this.toArray(), e = this._ambigTime, this.utc(), Kt(this, t), this._ambigTime = e || !1, this._ambigZone = !0), this
    }, ie.hasZone = function () {
        return !this._ambigZone
    }, ie.local = function () {
        var t = this.toArray(), e = this._ambigZone;
        return re.local.apply(this, arguments), this._ambigTime = !1, this._ambigZone = !1, e && Jt(this, t), this
    }, ie.utc = function () {
        return re.utc.apply(this, arguments), this._ambigTime = !1, this._ambigZone = !1, this
    }, t.each(["zone", "utcOffset"], function (t, e) {
        re[e] && (ie[e] = function (t) {
            return null != t && (this._ambigTime = !1, this._ambigZone = !1), re[e].apply(this, arguments)
        })
    }), ie.format = function () {
        return this._fullCalendar && arguments[0] ? ft(this, arguments[0]) : this._ambigTime ? ht(this, "YYYY-MM-DD") : this._ambigZone ? ht(this, "YYYY-MM-DD[T]HH:mm:ss") : re.format.apply(this, arguments)
    }, ie.toISOString = function () {
        return this._ambigTime ? ht(this, "YYYY-MM-DD") : this._ambigZone ? ht(this, "YYYY-MM-DD[T]HH:mm:ss") : re.toISOString.apply(this, arguments)
    }, ie.isWithin = function (t, e) {
        var n = ut([this, t, e]);
        return n[0] >= n[1] && n[0] < n[2]
    }, ie.isSame = function (t, e) {
        var n;
        return this._fullCalendar ? e ? (n = ut([this, t], !0), re.isSame.call(n[0], n[1], e)) : (t = jt.moment.parseZone(t), re.isSame.call(this, t) && Boolean(this._ambigTime) === Boolean(t._ambigTime) && Boolean(this._ambigZone) === Boolean(t._ambigZone)) : re.isSame.apply(this, arguments)
    }, t.each(["isBefore", "isAfter"], function (t, e) {
        ie[e] = function (t, n) {
            var i;
            return this._fullCalendar ? (i = ut([this, t]), re[e].call(i[0], i[1], n)) : re[e].apply(this, arguments)
        }
    }), Qt = "_d" in e() && "updateOffset" in e, Kt = Qt ? function (t, n) {
        t._d.setTime(Date.UTC.apply(Date, n)), e.updateOffset(t, !1)
    } : dt, Jt = Qt ? function (t, n) {
        t._d.setTime(+new Date(n[0] || 0, n[1] || 0, n[2] || 0, n[3] || 0, n[4] || 0, n[5] || 0, n[6] || 0)), e.updateOffset(t, !1)
    } : dt;
    var se = {
        t: function (t) {
            return ht(t, "a").charAt(0)
        }, T: function (t) {
            return ht(t, "A").charAt(0)
        }
    };
    jt.formatRange = vt;
    var oe = {
        Y: "year",
        M: "month",
        D: "day",
        d: "day",
        A: "second",
        a: "second",
        T: "second",
        t: "second",
        H: "second",
        h: "second",
        m: "second",
        s: "second"
    }, ae = {};
    jt.Class = Et, Et.extend = function () {
        var t, e, n = arguments.length;
        for (t = 0; n > t; t++)e = arguments[t], n - 1 > t && bt(this, e);
        return Dt(this, e || {})
    }, Et.mixin = function (t) {
        bt(this, t)
    };
    var le = jt.EmitterMixin = {
        callbackHash: null, on: function (t, e) {
            return this.loopCallbacks(t, "add", [e]), this
        }, off: function (t, e) {
            return this.loopCallbacks(t, "remove", [e]), this
        }, trigger: function (t) {
            var e = Array.prototype.slice.call(arguments, 1);
            return this.triggerWith(t, this, e), this
        }, triggerWith: function (t, e, n) {
            return this.loopCallbacks(t, "fireWith", [e, n]), this
        }, loopCallbacks: function (t, e, n) {
            var i, r, s, o = t.split(".");
            for (i = 0; i < o.length; i++)r = o[i], r && (s = this.ensureCallbackObj((i ? "." : "") + r), s[e].apply(s, n))
        }, ensureCallbackObj: function (e) {
            return this.callbackHash || (this.callbackHash = {}), this.callbackHash[e] || (this.callbackHash[e] = t.Callbacks()), this.callbackHash[e]
        }
    }, ue = jt.ListenerMixin = function () {
        var e = 0, n = {
            listenerId: null, listenTo: function (e, n, i) {
                if ("object" == typeof n)for (var r in n)n.hasOwnProperty(r) && this.listenTo(e, r, n[r]); else"string" == typeof n && e.on(n + "." + this.getListenerNamespace(), t.proxy(i, this))
            }, stopListeningTo: function (t, e) {
                t.off((e || "") + "." + this.getListenerNamespace())
            }, getListenerNamespace: function () {
                return null == this.listenerId && (this.listenerId = e++), "_listener" + this.listenerId
            }
        };
        return n
    }(), ce = Et.extend(ue, {
        isHidden: !0, options: null, el: null, margin: 10, constructor: function (t) {
            this.options = t || {}
        }, show: function () {
            this.isHidden && (this.el || this.render(),
                this.el.show(), this.position(), this.isHidden = !1, this.trigger("show"))
        }, hide: function () {
            this.isHidden || (this.el.hide(), this.isHidden = !0, this.trigger("hide"))
        }, render: function () {
            var e = this, n = this.options;
            this.el = t('<div class="fc-popover"/>').addClass(n.className || "").css({
                top: 0,
                left: 0
            }).append(n.content).appendTo(n.parentEl), this.el.on("click", ".fc-close", function () {
                e.hide()
            }), n.autoHide && this.listenTo(t(document), "mousedown", this.documentMousedown)
        }, documentMousedown: function (e) {
            this.el && !t(e.target).closest(this.el).length && this.hide()
        }, removeElement: function () {
            this.hide(), this.el && (this.el.remove(), this.el = null), this.stopListeningTo(t(document), "mousedown")
        }, position: function () {
            var e, n, i, r, s, o = this.options, a = this.el.offsetParent().offset(), l = this.el.outerWidth(), u = this.el.outerHeight(), c = t(window), d = h(this.el);
            r = o.top || 0, s = void 0 !== o.left ? o.left : void 0 !== o.right ? o.right - l : 0, d.is(window) || d.is(document) ? (d = c, e = 0, n = 0) : (i = d.offset(), e = i.top, n = i.left), e += c.scrollTop(), n += c.scrollLeft(), o.viewportConstrain !== !1 && (r = Math.min(r, e + d.outerHeight() - u - this.margin), r = Math.max(r, e + this.margin), s = Math.min(s, n + d.outerWidth() - l - this.margin), s = Math.max(s, n + this.margin)), this.el.css({
                top: r - a.top,
                left: s - a.left
            })
        }, trigger: function (t) {
            this.options[t] && this.options[t].apply(this, Array.prototype.slice.call(arguments, 1))
        }
    }), de = jt.CoordCache = Et.extend({
        els: null,
        forcedOffsetParentEl: null,
        origin: null,
        boundingRect: null,
        isHorizontal: !1,
        isVertical: !1,
        lefts: null,
        rights: null,
        tops: null,
        bottoms: null,
        constructor: function (e) {
            this.els = t(e.els), this.isHorizontal = e.isHorizontal, this.isVertical = e.isVertical, this.forcedOffsetParentEl = e.offsetParent ? t(e.offsetParent) : null
        },
        build: function () {
            var t = this.forcedOffsetParentEl || this.els.eq(0).offsetParent();
            this.origin = t.offset(), this.boundingRect = this.queryBoundingRect(), this.isHorizontal && this.buildElHorizontals(), this.isVertical && this.buildElVerticals()
        },
        clear: function () {
            this.origin = null, this.boundingRect = null, this.lefts = null, this.rights = null, this.tops = null, this.bottoms = null
        },
        ensureBuilt: function () {
            this.origin || this.build()
        },
        queryBoundingRect: function () {
            var t = h(this.els.eq(0));
            return t.is(document) ? void 0 : g(t)
        },
        buildElHorizontals: function () {
            var e = [], n = [];
            this.els.each(function (i, r) {
                var s = t(r), o = s.offset().left, a = s.outerWidth();
                e.push(o), n.push(o + a)
            }), this.lefts = e, this.rights = n
        },
        buildElVerticals: function () {
            var e = [], n = [];
            this.els.each(function (i, r) {
                var s = t(r), o = s.offset().top, a = s.outerHeight();
                e.push(o), n.push(o + a)
            }), this.tops = e, this.bottoms = n
        },
        getHorizontalIndex: function (t) {
            this.ensureBuilt();
            var e, n = this.boundingRect, i = this.lefts, r = this.rights, s = i.length;
            if (!n || t >= n.left && t < n.right)for (e = 0; s > e; e++)if (t >= i[e] && t < r[e])return e
        },
        getVerticalIndex: function (t) {
            this.ensureBuilt();
            var e, n = this.boundingRect, i = this.tops, r = this.bottoms, s = i.length;
            if (!n || t >= n.top && t < n.bottom)for (e = 0; s > e; e++)if (t >= i[e] && t < r[e])return e
        },
        getLeftOffset: function (t) {
            return this.ensureBuilt(), this.lefts[t]
        },
        getLeftPosition: function (t) {
            return this.ensureBuilt(), this.lefts[t] - this.origin.left
        },
        getRightOffset: function (t) {
            return this.ensureBuilt(), this.rights[t]
        },
        getRightPosition: function (t) {
            return this.ensureBuilt(), this.rights[t] - this.origin.left
        },
        getWidth: function (t) {
            return this.ensureBuilt(), this.rights[t] - this.lefts[t]
        },
        getTopOffset: function (t) {
            return this.ensureBuilt(), this.tops[t]
        },
        getTopPosition: function (t) {
            return this.ensureBuilt(), this.tops[t] - this.origin.top
        },
        getBottomOffset: function (t) {
            return this.ensureBuilt(), this.bottoms[t]
        },
        getBottomPosition: function (t) {
            return this.ensureBuilt(), this.bottoms[t] - this.origin.top
        },
        getHeight: function (t) {
            return this.ensureBuilt(), this.bottoms[t] - this.tops[t]
        }
    }), he = jt.DragListener = Et.extend(ue, {
        options: null,
        subjectEl: null,
        subjectHref: null,
        originX: null,
        originY: null,
        scrollEl: null,
        isInteracting: !1,
        isDistanceSurpassed: !1,
        isDelayEnded: !1,
        isDragging: !1,
        isTouch: !1,
        delay: null,
        delayTimeoutId: null,
        minDistance: null,
        constructor: function (t) {
            this.options = t || {}
        },
        startInteraction: function (e, n) {
            var i = b(e);
            if ("mousedown" === e.type) {
                if (!S(e))return;
                e.preventDefault()
            }
            this.isInteracting || (n = n || {}, this.delay = J(n.delay, this.options.delay, 0), this.minDistance = J(n.distance, this.options.distance, 0), this.subjectEl = this.options.subjectEl, this.isInteracting = !0, this.isTouch = i, this.isDelayEnded = !1, this.isDistanceSurpassed = !1, this.originX = E(e), this.originY = D(e), this.scrollEl = h(t(e.target)), this.bindHandlers(), this.initAutoScroll(), this.handleInteractionStart(e), this.startDelay(e), this.minDistance || this.handleDistanceSurpassed(e))
        },
        handleInteractionStart: function (t) {
            this.trigger("interactionStart", t)
        },
        endInteraction: function (t) {
            this.isInteracting && (this.endDrag(t), this.delayTimeoutId && (clearTimeout(this.delayTimeoutId), this.delayTimeoutId = null), this.destroyAutoScroll(), this.unbindHandlers(), this.isInteracting = !1, this.handleInteractionEnd(t))
        },
        handleInteractionEnd: function (t) {
            this.trigger("interactionEnd", t)
        },
        bindHandlers: function () {
            var e = this, n = 1;
            this.isTouch ? (this.listenTo(t(document), {
                touchmove: this.handleTouchMove,
                touchend: this.endInteraction,
                touchcancel: this.endInteraction,
                touchstart: function (t) {
                    n ? n-- : e.endInteraction(t)
                }
            }), this.scrollEl && this.listenTo(this.scrollEl, "scroll", this.handleTouchScroll)) : this.listenTo(t(document), {
                mousemove: this.handleMouseMove,
                mouseup: this.endInteraction
            }), this.listenTo(t(document), {selectstart: C, contextmenu: C})
        },
        unbindHandlers: function () {
            this.stopListeningTo(t(document)), this.scrollEl && this.stopListeningTo(this.scrollEl)
        },
        startDrag: function (t, e) {
            this.startInteraction(t, e), this.isDragging || (this.isDragging = !0, this.handleDragStart(t))
        },
        handleDragStart: function (t) {
            this.trigger("dragStart", t), this.initHrefHack()
        },
        handleMove: function (t) {
            var e, n = E(t) - this.originX, i = D(t) - this.originY, r = this.minDistance;
            this.isDistanceSurpassed || (e = n * n + i * i, e >= r * r && this.handleDistanceSurpassed(t)), this.isDragging && this.handleDrag(n, i, t)
        },
        handleDrag: function (t, e, n) {
            this.trigger("drag", t, e, n), this.updateAutoScroll(n)
        },
        endDrag: function (t) {
            this.isDragging && (this.isDragging = !1, this.handleDragEnd(t))
        },
        handleDragEnd: function (t) {
            this.trigger("dragEnd", t), this.destroyHrefHack()
        },
        startDelay: function (t) {
            var e = this;
            this.delay ? this.delayTimeoutId = setTimeout(function () {
                e.handleDelayEnd(t)
            }, this.delay) : this.handleDelayEnd(t)
        },
        handleDelayEnd: function (t) {
            this.isDelayEnded = !0, this.isDistanceSurpassed && this.startDrag(t)
        },
        handleDistanceSurpassed: function (t) {
            this.isDistanceSurpassed = !0, this.isDelayEnded && this.startDrag(t)
        },
        handleTouchMove: function (t) {
            this.isDragging && t.preventDefault(), this.handleMove(t)
        },
        handleMouseMove: function (t) {
            this.handleMove(t)
        },
        handleTouchScroll: function (t) {
            this.isDragging || this.endInteraction(t)
        },
        initHrefHack: function () {
            var t = this.subjectEl;
            (this.subjectHref = t ? t.attr("href") : null) && t.removeAttr("href")
        },
        destroyHrefHack: function () {
            var t = this.subjectEl, e = this.subjectHref;
            setTimeout(function () {
                e && t.attr("href", e)
            }, 0)
        },
        trigger: function (t) {
            this.options[t] && this.options[t].apply(this, Array.prototype.slice.call(arguments, 1)), this["_" + t] && this["_" + t].apply(this, Array.prototype.slice.call(arguments, 1))
        }
    });
    he.mixin({
        isAutoScroll: !1,
        scrollBounds: null,
        scrollTopVel: null,
        scrollLeftVel: null,
        scrollIntervalId: null,
        scrollSensitivity: 30,
        scrollSpeed: 200,
        scrollIntervalMs: 50,
        initAutoScroll: function () {
            var t = this.scrollEl;
            this.isAutoScroll = this.options.scroll && t && !t.is(window) && !t.is(document), this.isAutoScroll && this.listenTo(t, "scroll", at(this.handleDebouncedScroll, 100))
        },
        destroyAutoScroll: function () {
            this.endAutoScroll(), this.isAutoScroll && this.stopListeningTo(this.scrollEl, "scroll")
        },
        computeScrollBounds: function () {
            this.isAutoScroll && (this.scrollBounds = f(this.scrollEl))
        },
        updateAutoScroll: function (t) {
            var e, n, i, r, s = this.scrollSensitivity, o = this.scrollBounds, a = 0, l = 0;
            o && (e = (s - (D(t) - o.top)) / s, n = (s - (o.bottom - D(t))) / s, i = (s - (E(t) - o.left)) / s, r = (s - (o.right - E(t))) / s, e >= 0 && 1 >= e ? a = e * this.scrollSpeed * -1 : n >= 0 && 1 >= n && (a = n * this.scrollSpeed), i >= 0 && 1 >= i ? l = i * this.scrollSpeed * -1 : r >= 0 && 1 >= r && (l = r * this.scrollSpeed)), this.setScrollVel(a, l)
        },
        setScrollVel: function (t, e) {
            this.scrollTopVel = t, this.scrollLeftVel = e, this.constrainScrollVel(), !this.scrollTopVel && !this.scrollLeftVel || this.scrollIntervalId || (this.scrollIntervalId = setInterval(ot(this, "scrollIntervalFunc"), this.scrollIntervalMs))
        },
        constrainScrollVel: function () {
            var t = this.scrollEl;
            this.scrollTopVel < 0 ? t.scrollTop() <= 0 && (this.scrollTopVel = 0) : this.scrollTopVel > 0 && t.scrollTop() + t[0].clientHeight >= t[0].scrollHeight && (this.scrollTopVel = 0), this.scrollLeftVel < 0 ? t.scrollLeft() <= 0 && (this.scrollLeftVel = 0) : this.scrollLeftVel > 0 && t.scrollLeft() + t[0].clientWidth >= t[0].scrollWidth && (this.scrollLeftVel = 0)
        },
        scrollIntervalFunc: function () {
            var t = this.scrollEl, e = this.scrollIntervalMs / 1e3;
            this.scrollTopVel && t.scrollTop(t.scrollTop() + this.scrollTopVel * e), this.scrollLeftVel && t.scrollLeft(t.scrollLeft() + this.scrollLeftVel * e), this.constrainScrollVel(), this.scrollTopVel || this.scrollLeftVel || this.endAutoScroll()
        },
        endAutoScroll: function () {
            this.scrollIntervalId && (clearInterval(this.scrollIntervalId), this.scrollIntervalId = null, this.handleScrollEnd())
        },
        handleDebouncedScroll: function () {
            this.scrollIntervalId || this.handleScrollEnd()
        },
        handleScrollEnd: function () {
        }
    });
    var fe = he.extend({
        component: null, origHit: null, hit: null, coordAdjust: null, constructor: function (t, e) {
            he.call(this, e), this.component = t
        }, handleInteractionStart: function (t) {
            var e, n, i, r = this.subjectEl;
            this.computeCoords(), t ? (n = {
                left: E(t),
                top: D(t)
            }, i = n, r && (e = f(r), i = R(i, e)), this.origHit = this.queryHit(i.left, i.top), r && this.options.subjectCenter && (this.origHit && (e = H(this.origHit, e) || e), i = x(e)), this.coordAdjust = I(i, n)) : (this.origHit = null, this.coordAdjust = null), he.prototype.handleInteractionStart.apply(this, arguments)
        }, computeCoords: function () {
            this.component.prepareHits(), this.computeScrollBounds()
        }, handleDragStart: function (t) {
            var e;
            he.prototype.handleDragStart.apply(this, arguments), e = this.queryHit(E(t), D(t)), e && this.handleHitOver(e)
        }, handleDrag: function (t, e, n) {
            var i;
            he.prototype.handleDrag.apply(this, arguments), i = this.queryHit(E(n), D(n)), Tt(i, this.hit) || (this.hit && this.handleHitOut(), i && this.handleHitOver(i))
        }, handleDragEnd: function () {
            this.handleHitDone(), he.prototype.handleDragEnd.apply(this, arguments)
        }, handleHitOver: function (t) {
            var e = Tt(t, this.origHit);
            this.hit = t, this.trigger("hitOver", this.hit, e, this.origHit)
        }, handleHitOut: function () {
            this.hit && (this.trigger("hitOut", this.hit), this.handleHitDone(), this.hit = null)
        }, handleHitDone: function () {
            this.hit && this.trigger("hitDone", this.hit)
        }, handleInteractionEnd: function () {
            he.prototype.handleInteractionEnd.apply(this, arguments), this.origHit = null, this.hit = null, this.component.releaseHits()
        }, handleScrollEnd: function () {
            he.prototype.handleScrollEnd.apply(this, arguments), this.computeCoords()
        }, queryHit: function (t, e) {
            return this.coordAdjust && (t += this.coordAdjust.left, e += this.coordAdjust.top), this.component.queryHit(t, e)
        }
    }), ge = Et.extend(ue, {
        options: null,
        sourceEl: null,
        el: null,
        parentEl: null,
        top0: null,
        left0: null,
        y0: null,
        x0: null,
        topDelta: null,
        leftDelta: null,
        isFollowing: !1,
        isHidden: !1,
        isAnimating: !1,
        constructor: function (e, n) {
            this.options = n = n || {}, this.sourceEl = e, this.parentEl = n.parentEl ? t(n.parentEl) : e.parent()
        },
        start: function (e) {
            this.isFollowing || (this.isFollowing = !0, this.y0 = D(e), this.x0 = E(e), this.topDelta = 0, this.leftDelta = 0, this.isHidden || this.updatePosition(), b(e) ? this.listenTo(t(document), "touchmove", this.handleMove) : this.listenTo(t(document), "mousemove", this.handleMove))
        },
        stop: function (e, n) {
            function i() {
                this.isAnimating = !1, r.removeElement(), this.top0 = this.left0 = null, n && n()
            }

            var r = this, s = this.options.revertDuration;
            this.isFollowing && !this.isAnimating && (this.isFollowing = !1, this.stopListeningTo(t(document)), e && s && !this.isHidden ? (this.isAnimating = !0, this.el.animate({
                top: this.top0,
                left: this.left0
            }, {duration: s, complete: i})) : i())
        },
        getEl: function () {
            var t = this.el;
            return t || (this.sourceEl.width(), t = this.el = this.sourceEl.clone().addClass(this.options.additionalClass || "").css({
                position: "absolute",
                visibility: "",
                display: this.isHidden ? "none" : "",
                margin: 0,
                right: "auto",
                bottom: "auto",
                width: this.sourceEl.width(),
                height: this.sourceEl.height(),
                opacity: this.options.opacity || "",
                zIndex: this.options.zIndex
            }), t.addClass("fc-unselectable"), t.appendTo(this.parentEl)), t
        },
        removeElement: function () {
            this.el && (this.el.remove(), this.el = null)
        },
        updatePosition: function () {
            var t, e;
            this.getEl(), null === this.top0 && (this.sourceEl.width(), t = this.sourceEl.offset(), e = this.el.offsetParent().offset(), this.top0 = t.top - e.top, this.left0 = t.left - e.left), this.el.css({
                top: this.top0 + this.topDelta,
                left: this.left0 + this.leftDelta
            })
        },
        handleMove: function (t) {
            this.topDelta = D(t) - this.y0, this.leftDelta = E(t) - this.x0, this.isHidden || this.updatePosition()
        },
        hide: function () {
            this.isHidden || (this.isHidden = !0, this.el && this.el.hide())
        },
        show: function () {
            this.isHidden && (this.isHidden = !1, this.updatePosition(), this.getEl().show())
        }
    }), pe = jt.Grid = Et.extend(ue, {
        view: null,
        isRTL: null,
        start: null,
        end: null,
        el: null,
        elsByFill: null,
        eventTimeFormat: null,
        displayEventTime: null,
        displayEventEnd: null,
        minResizeDuration: null,
        largeUnit: null,
        dayDragListener: null,
        segDragListener: null,
        segResizeListener: null,
        externalDragListener: null,
        constructor: function (t) {
            this.view = t, this.isRTL = t.opt("isRTL"), this.elsByFill = {}
        },
        computeEventTimeFormat: function () {
            return this.view.opt("smallTimeFormat")
        },
        computeDisplayEventTime: function () {
            return !0
        },
        computeDisplayEventEnd: function () {
            return !0
        },
        setRange: function (t) {
            this.start = t.start.clone(), this.end = t.end.clone(), this.rangeUpdated(), this.processRangeOptions()
        },
        rangeUpdated: function () {
        },
        processRangeOptions: function () {
            var t, e, n = this.view;
            this.eventTimeFormat = n.opt("eventTimeFormat") || n.opt("timeFormat") || this.computeEventTimeFormat(), t = n.opt("displayEventTime"), null == t && (t = this.computeDisplayEventTime()), e = n.opt("displayEventEnd"), null == e && (e = this.computeDisplayEventEnd()), this.displayEventTime = t, this.displayEventEnd = e
        },
        spanToSegs: function (t) {
        },
        diffDates: function (t, e) {
            return this.largeUnit ? G(t, e, this.largeUnit) : N(t, e)
        },
        prepareHits: function () {
        },
        releaseHits: function () {
        },
        queryHit: function (t, e) {
        },
        getHitSpan: function (t) {
        },
        getHitEl: function (t) {
        },
        setElement: function (t) {
            this.el = t, T(t), this.view.calendar.isTouch ? this.bindDayHandler("touchstart", this.dayTouchStart) : this.bindDayHandler("mousedown", this.dayMousedown), this.bindSegHandlers(), this.bindGlobalHandlers()
        },
        bindDayHandler: function (e, n) {
            var i = this;
            this.el.on(e, function (e) {
                return t(e.target).is(".fc-event-container *, .fc-more") || t(e.target).closest(".fc-popover").length ? void 0 : n.call(i, e)
            })
        },
        removeElement: function () {
            this.unbindGlobalHandlers(), this.clearDragListeners(), this.el.remove()
        },
        renderSkeleton: function () {
        },
        renderDates: function () {
        },
        unrenderDates: function () {
        },
        bindGlobalHandlers: function () {
            this.listenTo(t(document), {dragstart: this.externalDragStart, sortstart: this.externalDragStart})
        },
        unbindGlobalHandlers: function () {
            this.stopListeningTo(t(document))
        },
        dayMousedown: function (t) {
            this.clearDragListeners(), this.buildDayDragListener().startInteraction(t, {})
        },
        dayTouchStart: function (t) {
            this.clearDragListeners(), this.buildDayDragListener().startInteraction(t, {delay: this.view.opt("longPressDelay")})
        },
        buildDayDragListener: function () {
            var t, e, n = this, i = this.view, r = i.opt("selectable"), s = this.dayDragListener = new fe(this, {
                scroll: i.opt("dragScroll"),
                interactionStart: function () {
                    t = s.origHit
                },
                dragStart: function () {
                    i.unselect()
                },
                hitOver: function (i, s, a) {
                    a && (s || (t = null), r && (e = n.computeSelection(n.getHitSpan(a), n.getHitSpan(i)), e ? n.renderSelection(e) : e === !1 && o()))
                },
                hitOut: function () {
                    t = null, e = null, n.unrenderSelection(), a()
                },
                interactionEnd: function (r) {
                    t && i.triggerDayClick(n.getHitSpan(t), n.getHitEl(t), r), e && i.reportSelection(e, r), a(), n.dayDragListener = null
                }
            });
            return s
        },
        clearDragListeners: function () {
            this.dayDragListener && this.dayDragListener.endInteraction(), this.segDragListener && this.segDragListener.endInteraction(), this.segResizeListener && this.segResizeListener.endInteraction(), this.externalDragListener && this.externalDragListener.endInteraction()
        },
        renderEventLocationHelper: function (t, e) {
            var n = this.fabricateHelperEvent(t, e);
            return this.renderHelper(n, e)
        },
        fabricateHelperEvent: function (t, e) {
            var n = e ? U(e.event) : {};
            return n.start = t.start.clone(), n.end = t.end ? t.end.clone() : null, n.allDay = null, this.view.calendar.normalizeEventDates(n), n.className = (n.className || []).concat("fc-helper"), e || (n.editable = !1), n
        },
        renderHelper: function (t, e) {
        },
        unrenderHelper: function () {
        },
        renderSelection: function (t) {
            this.renderHighlight(t)
        },
        unrenderSelection: function () {
            this.unrenderHighlight()
        },
        computeSelection: function (t, e) {
            var n = this.computeSelectionSpan(t, e);
            return n && !this.view.calendar.isSelectionSpanAllowed(n) ? !1 : n
        },
        computeSelectionSpan: function (t, e) {
            var n = [t.start, t.end, e.start, e.end];
            return n.sort(rt), {start: n[0].clone(), end: n[3].clone()}
        },
        renderHighlight: function (t) {
            this.renderFill("highlight", this.spanToSegs(t))
        },
        unrenderHighlight: function () {
            this.unrenderFill("highlight")
        },
        highlightSegClasses: function () {
            return ["fc-highlight"]
        },
        renderBusinessHours: function () {
        },
        unrenderBusinessHours: function () {
        },
        getNowIndicatorUnit: function () {
        },
        renderNowIndicator: function (t) {
        },
        unrenderNowIndicator: function () {
        },
        renderFill: function (t, e) {
        },
        unrenderFill: function (t) {
            var e = this.elsByFill[t];
            e && (e.remove(), delete this.elsByFill[t])
        },
        renderFillSegEls: function (e, n) {
            var i, r = this, s = this[e + "SegEl"], o = "", a = [];
            if (n.length) {
                for (i = 0; i < n.length; i++)o += this.fillSegHtml(e, n[i]);
                t(o).each(function (e, i) {
                    var o = n[e], l = t(i);
                    s && (l = s.call(r, o, l)), l && (l = t(l), l.is(r.fillSegTag) && (o.el = l, a.push(o)))
                })
            }
            return a
        },
        fillSegTag: "div",
        fillSegHtml: function (t, e) {
            var n = this[t + "SegClasses"], i = this[t + "SegCss"], r = n ? n.call(this, e) : [], s = nt(i ? i.call(this, e) : {});
            return "<" + this.fillSegTag + (r.length ? ' class="' + r.join(" ") + '"' : "") + (s ? ' style="' + s + '"' : "") + " />"
        },
        getDayClasses: function (t) {
            var e = this.view, n = e.calendar.getNow(), i = ["fc-" + $t[t.day()]];
            return 1 == e.intervalDuration.as("months") && t.month() != e.intervalStart.month() && i.push("fc-other-month"), t.isSame(n, "day") ? i.push("fc-today", e.highlightStateClass) : n > t ? i.push("fc-past") : i.push("fc-future"), i
        }
    });
    pe.mixin({
        mousedOverSeg: null,
        isDraggingSeg: !1,
        isResizingSeg: !1,
        isDraggingExternal: !1,
        segs: null,
        renderEvents: function (t) {
            var e, n = [], i = [];
            for (e = 0; e < t.length; e++)(Ht(t[e]) ? n : i).push(t[e]);
            this.segs = [].concat(this.renderBgEvents(n), this.renderFgEvents(i))
        },
        renderBgEvents: function (t) {
            var e = this.eventsToSegs(t);
            return this.renderBgSegs(e) || e
        },
        renderFgEvents: function (t) {
            var e = this.eventsToSegs(t);
            return this.renderFgSegs(e) || e
        },
        unrenderEvents: function () {
            this.handleSegMouseout(), this.clearDragListeners(), this.unrenderFgSegs(), this.unrenderBgSegs(), this.segs = null
        },
        getEventSegs: function () {
            return this.segs || []
        },
        renderFgSegs: function (t) {
        },
        unrenderFgSegs: function () {
        },
        renderFgSegEls: function (e, n) {
            var i, r = this.view, s = "", o = [];
            if (e.length) {
                for (i = 0; i < e.length; i++)s += this.fgSegHtml(e[i], n);
                t(s).each(function (n, i) {
                    var s = e[n], a = r.resolveEventEl(s.event, t(i));
                    a && (a.data("fc-seg", s), s.el = a, o.push(s))
                })
            }
            return o
        },
        fgSegHtml: function (t, e) {
        },
        renderBgSegs: function (t) {
            return this.renderFill("bgEvent", t)
        },
        unrenderBgSegs: function () {
            this.unrenderFill("bgEvent")
        },
        bgEventSegEl: function (t, e) {
            return this.view.resolveEventEl(t.event, e)
        },
        bgEventSegClasses: function (t) {
            var e = t.event, n = e.source || {};
            return ["fc-bgevent"].concat(e.className, n.className || [])
        },
        bgEventSegCss: function (t) {
            return {"background-color": this.getSegSkinCss(t)["background-color"]}
        },
        businessHoursSegClasses: function (t) {
            return ["fc-nonbusiness", "fc-bgevent"]
        },
        bindSegHandlers: function () {
            this.view.calendar.isTouch ? this.bindSegHandler("touchstart", this.handleSegTouchStart) : (this.bindSegHandler("mouseenter", this.handleSegMouseover), this.bindSegHandler("mouseleave", this.handleSegMouseout), this.bindSegHandler("mousedown", this.handleSegMousedown)), this.bindSegHandler("click", this.handleSegClick)
        },
        bindSegHandler: function (e, n) {
            var i = this;
            this.el.on(e, ".fc-event-container > *", function (e) {
                var r = t(this).data("fc-seg");
                return !r || i.isDraggingSeg || i.isResizingSeg ? void 0 : n.call(i, r, e)
            })
        },
        handleSegClick: function (t, e) {
            return this.view.trigger("eventClick", t.el[0], t.event, e)
        },
        handleSegMouseover: function (t, e) {
            this.mousedOverSeg || (this.mousedOverSeg = t, this.view.trigger("eventMouseover", t.el[0], t.event, e))
        },
        handleSegMouseout: function (t, e) {
            e = e || {}, this.mousedOverSeg && (t = t || this.mousedOverSeg, this.mousedOverSeg = null, this.view.trigger("eventMouseout", t.el[0], t.event, e))
        },
        handleSegTouchStart: function (t, e) {
            var n, i = this.view, r = t.event, s = i.isEventSelected(r), o = i.isEventDraggable(r), a = i.isEventResizable(r), l = !1;
            s && a && (l = this.startSegResize(t, e)), l || !o && !a || (this.clearDragListeners(), n = o ? this.buildSegDragListener(t) : new he, n._dragStart = function () {
                s || i.selectEvent(r)
            }, n.startInteraction(e, {delay: s ? 0 : this.view.opt("longPressDelay")}))
        },
        handleSegMousedown: function (t, e) {
            var n = this.startSegResize(t, e, {distance: 5});
            !n && this.view.isEventDraggable(t.event) && (this.clearDragListeners(), this.buildSegDragListener(t).startInteraction(e, {distance: 5}))
        },
        startSegResize: function (e, n, i) {
            return t(n.target).is(".fc-resizer") ? (this.clearDragListeners(), this.buildSegResizeListener(e, t(n.target).is(".fc-start-resizer")).startInteraction(n, i), !0) : !1
        },
        buildSegDragListener: function (t) {
            var e, n, i, r = this, s = this.view, l = s.calendar, u = t.el, c = t.event, d = this.segDragListener = new fe(s, {
                scroll: s.opt("dragScroll"),
                subjectEl: u,
                subjectCenter: !0,
                interactionStart: function (i) {
                    e = !1, n = new ge(t.el, {
                        additionalClass: "fc-dragging",
                        parentEl: s.el,
                        opacity: d.isTouch ? null : s.opt("dragOpacity"),
                        revertDuration: s.opt("dragRevertDuration"),
                        zIndex: 2
                    }), n.hide(), n.start(i)
                },
                dragStart: function (n) {
                    e = !0, r.handleSegMouseout(t, n), r.segDragStart(t, n), s.hideEvent(c)
                },
                hitOver: function (e, a, u) {
                    var h;
                    t.hit && (u = t.hit), i = r.computeEventDrop(u.component.getHitSpan(u), e.component.getHitSpan(e), c), i && !l.isEventSpanAllowed(r.eventToSpan(i), c) && (o(), i = null), i && (h = s.renderDrag(i, t)) ? (h.addClass("fc-dragging"), d.isTouch || r.applyDragOpacity(h), n.hide()) : n.show(), a && (i = null)
                },
                hitOut: function () {
                    s.unrenderDrag(), n.show(), i = null
                },
                hitDone: function () {
                    a()
                },
                interactionEnd: function (o) {
                    n.stop(!i, function () {
                        e && (s.unrenderDrag(), s.showEvent(c), r.segDragStop(t, o)), i && s.reportEventDrop(c, i, this.largeUnit, u, o)
                    }), r.segDragListener = null
                }
            });
            return d
        },
        segDragStart: function (t, e) {
            this.isDraggingSeg = !0, this.view.trigger("eventDragStart", t.el[0], t.event, e, {})
        },
        segDragStop: function (t, e) {
            this.isDraggingSeg = !1, this.view.trigger("eventDragStop", t.el[0], t.event, e, {})
        },
        computeEventDrop: function (t, e, n) {
            var i, r, s = this.view.calendar, o = t.start, a = e.start;
            return o.hasTime() === a.hasTime() ? (i = this.diffDates(a, o), n.allDay && Y(i) ? (r = {
                start: n.start.clone(),
                end: s.getEventEnd(n),
                allDay: !1
            }, s.normalizeEventTimes(r)) : r = {
                start: n.start.clone(),
                end: n.end ? n.end.clone() : null,
                allDay: n.allDay
            }, r.start.add(i), r.end && r.end.add(i)) : r = {start: a.clone(), end: null, allDay: !a.hasTime()}, r
        },
        applyDragOpacity: function (t) {
            var e = this.view.opt("dragOpacity");
            null != e && t.each(function (t, n) {
                n.style.opacity = e
            })
        },
        externalDragStart: function (e, n) {
            var i, r, s = this.view;
            s.opt("droppable") && (i = t((n ? n.item : null) || e.target), r = s.opt("dropAccept"), (t.isFunction(r) ? r.call(i[0], i) : i.is(r)) && (this.isDraggingExternal || this.listenToExternalDrag(i, e, n)))
        },
        listenToExternalDrag: function (t, e, n) {
            var i, r = this, s = this.view.calendar, l = Lt(t), u = r.externalDragListener = new fe(this, {
                interactionStart: function () {
                    r.isDraggingExternal = !0
                }, hitOver: function (t) {
                    i = r.computeExternalDrop(t.component.getHitSpan(t), l), i && !s.isExternalSpanAllowed(r.eventToSpan(i), i, l.eventProps) && (o(), i = null), i && r.renderDrag(i)
                }, hitOut: function () {
                    i = null
                }, hitDone: function () {
                    a(), r.unrenderDrag()
                }, interactionEnd: function (e) {
                    i && r.view.reportExternalDrop(l, i, t, e, n), r.isDraggingExternal = !1, r.externalDragListener = null
                }
            });
            u.startDrag(e)
        },
        computeExternalDrop: function (t, e) {
            var n = this.view.calendar, i = {start: n.applyTimezone(t.start), end: null};
            return e.startTime && !i.start.hasTime() && i.start.time(e.startTime), e.duration && (i.end = i.start.clone().add(e.duration)), i
        },
        renderDrag: function (t, e) {
        },
        unrenderDrag: function () {
        },
        buildSegResizeListener: function (t, e) {
            var n, i, r = this, s = this.view, l = s.calendar, u = t.el, c = t.event, d = l.getEventEnd(c), h = this.segResizeListener = new fe(this, {
                scroll: s.opt("dragScroll"),
                subjectEl: u,
                interactionStart: function () {
                    n = !1
                },
                dragStart: function (e) {
                    n = !0, r.handleSegMouseout(t, e), r.segResizeStart(t, e)
                },
                hitOver: function (n, a, u) {
                    var h = r.getHitSpan(u), f = r.getHitSpan(n);
                    i = e ? r.computeEventStartResize(h, f, c) : r.computeEventEndResize(h, f, c), i && (l.isEventSpanAllowed(r.eventToSpan(i), c) ? i.start.isSame(c.start) && i.end.isSame(d) && (i = null) : (o(), i = null)), i && (s.hideEvent(c), r.renderEventResize(i, t))
                },
                hitOut: function () {
                    i = null
                },
                hitDone: function () {
                    r.unrenderEventResize(), s.showEvent(c), a()
                },
                interactionEnd: function (e) {
                    n && r.segResizeStop(t, e), i && s.reportEventResize(c, i, this.largeUnit, u, e), r.segResizeListener = null
                }
            });
            return h
        },
        segResizeStart: function (t, e) {
            this.isResizingSeg = !0, this.view.trigger("eventResizeStart", t.el[0], t.event, e, {})
        },
        segResizeStop: function (t, e) {
            this.isResizingSeg = !1, this.view.trigger("eventResizeStop", t.el[0], t.event, e, {})
        },
        computeEventStartResize: function (t, e, n) {
            return this.computeEventResize("start", t, e, n)
        },
        computeEventEndResize: function (t, e, n) {
            return this.computeEventResize("end", t, e, n)
        },
        computeEventResize: function (t, e, n, i) {
            var r, s, o = this.view.calendar, a = this.diffDates(n[t], e[t]);
            return r = {
                start: i.start.clone(),
                end: o.getEventEnd(i),
                allDay: i.allDay
            }, r.allDay && Y(a) && (r.allDay = !1, o.normalizeEventTimes(r)), r[t].add(a), r.start.isBefore(r.end) || (s = this.minResizeDuration || (i.allDay ? o.defaultAllDayEventDuration : o.defaultTimedEventDuration), "start" == t ? r.start = r.end.clone().subtract(s) : r.end = r.start.clone().add(s)), r
        },
        renderEventResize: function (t, e) {
        },
        unrenderEventResize: function () {
        },
        getEventTimeText: function (t, e, n) {
            return null == e && (e = this.eventTimeFormat), null == n && (n = this.displayEventEnd), this.displayEventTime && t.start.hasTime() ? n && t.end ? this.view.formatRange(t, e) : t.start.format(e) : ""
        },
        getSegClasses: function (t, e, n) {
            var i = this.view, r = t.event, s = ["fc-event", t.isStart ? "fc-start" : "fc-not-start", t.isEnd ? "fc-end" : "fc-not-end"].concat(r.className, r.source ? r.source.className : []);
            return e && s.push("fc-draggable"), n && s.push("fc-resizable"), i.isEventSelected(r) && s.push("fc-selected"), s
        },
        getSegSkinCss: function (t) {
            var e = t.event, n = this.view, i = e.source || {}, r = e.color, s = i.color, o = n.opt("eventColor");
            return {
                "background-color": e.backgroundColor || r || i.backgroundColor || s || n.opt("eventBackgroundColor") || o,
                "border-color": e.borderColor || r || i.borderColor || s || n.opt("eventBorderColor") || o,
                color: e.textColor || i.textColor || n.opt("eventTextColor")
            }
        },
        eventToSegs: function (t) {
            return this.eventsToSegs([t])
        },
        eventToSpan: function (t) {
            return this.eventToSpans(t)[0]
        },
        eventToSpans: function (t) {
            var e = this.eventToRange(t);
            return this.eventRangeToSpans(e, t)
        },
        eventsToSegs: function (e, n) {
            var i = this, r = It(e), s = [];
            return t.each(r, function (t, e) {
                var r, o = [];
                for (r = 0; r < e.length; r++)o.push(i.eventToRange(e[r]));
                if (Rt(e[0]))for (o = i.invertRanges(o), r = 0; r < o.length; r++)s.push.apply(s, i.eventRangeToSegs(o[r], e[0], n)); else for (r = 0; r < o.length; r++)s.push.apply(s, i.eventRangeToSegs(o[r], e[r], n))
            }), s
        },
        eventToRange: function (t) {
            return {
                start: t.start.clone().stripZone(),
                end: (t.end ? t.end.clone() : this.view.calendar.getDefaultEventEnd(null != t.allDay ? t.allDay : !t.start.hasTime(), t.start)).stripZone()
            }
        },
        eventRangeToSegs: function (t, e, n) {
            var i, r = this.eventRangeToSpans(t, e), s = [];
            for (i = 0; i < r.length; i++)s.push.apply(s, this.eventSpanToSegs(r[i], e, n));
            return s
        },
        eventRangeToSpans: function (e, n) {
            return [t.extend({}, e)]
        },
        eventSpanToSegs: function (t, e, n) {
            var i, r, s = n ? n(t) : this.spanToSegs(t);
            for (i = 0; i < s.length; i++)r = s[i], r.event = e, r.eventStartMS = +t.start, r.eventDurationMS = t.end - t.start;
            return s
        },
        invertRanges: function (t) {
            var e, n, i = this.view, r = i.start.clone(), s = i.end.clone(), o = [], a = r;
            for (t.sort(kt), e = 0; e < t.length; e++)n = t[e], n.start > a && o.push({
                start: a,
                end: n.start
            }), a = n.end;
            return s > a && o.push({start: a, end: s}), o
        },
        sortEventSegs: function (t) {
            t.sort(ot(this, "compareEventSegs"))
        },
        compareEventSegs: function (t, e) {
            return t.eventStartMS - e.eventStartMS || e.eventDurationMS - t.eventDurationMS || e.event.allDay - t.event.allDay || L(t.event, e.event, this.view.eventOrderSpecs)
        }
    }), jt.isBgEvent = Ht, jt.dataAttrPrefix = "";
    var ve = jt.DayTableMixin = {
        breakOnWeeks: !1,
        dayDates: null,
        dayIndices: null,
        daysPerRow: null,
        rowCnt: null,
        colCnt: null,
        colHeadFormat: null,
        updateDayTable: function () {
            for (var t, e, n, i = this.view, r = this.start.clone(), s = -1, o = [], a = []; r.isBefore(this.end);)i.isHiddenDay(r) ? o.push(s + .5) : (s++, o.push(s), a.push(r.clone())), r.add(1, "days");
            if (this.breakOnWeeks) {
                for (e = a[0].day(), t = 1; t < a.length && a[t].day() != e; t++);
                n = Math.ceil(a.length / t)
            } else n = 1, t = a.length;
            this.dayDates = a, this.dayIndices = o, this.daysPerRow = t, this.rowCnt = n, this.updateDayTableCols()
        },
        updateDayTableCols: function () {
            this.colCnt = this.computeColCnt(), this.colHeadFormat = this.view.opt("columnFormat") || this.computeColHeadFormat()
        },
        computeColCnt: function () {
            return this.daysPerRow
        },
        getCellDate: function (t, e) {
            return this.dayDates[this.getCellDayIndex(t, e)].clone()
        },
        getCellRange: function (t, e) {
            var n = this.getCellDate(t, e), i = n.clone().add(1, "days");
            return {start: n, end: i}
        },
        getCellDayIndex: function (t, e) {
            return t * this.daysPerRow + this.getColDayIndex(e)
        },
        getColDayIndex: function (t) {
            return this.isRTL ? this.colCnt - 1 - t : t
        },
        getDateDayIndex: function (t) {
            var e = this.dayIndices, n = t.diff(this.start, "days");
            return 0 > n ? e[0] - 1 : n >= e.length ? e[e.length - 1] + 1 : e[n]
        },
        computeColHeadFormat: function () {
            return this.rowCnt > 1 || this.colCnt > 10 ? "ddd" : this.colCnt > 1 ? this.view.opt("dayOfMonthFormat") : "dddd"
        },
        sliceRangeByRow: function (t) {
            var e, n, i, r, s, o = this.daysPerRow, a = this.view.computeDayRange(t), l = this.getDateDayIndex(a.start), u = this.getDateDayIndex(a.end.clone().subtract(1, "days")), c = [];
            for (e = 0; e < this.rowCnt; e++)n = e * o, i = n + o - 1, r = Math.max(l, n), s = Math.min(u, i), r = Math.ceil(r), s = Math.floor(s), s >= r && c.push({
                row: e,
                firstRowDayIndex: r - n,
                lastRowDayIndex: s - n,
                isStart: r === l,
                isEnd: s === u
            });
            return c
        },
        sliceRangeByDay: function (t) {
            var e, n, i, r, s, o, a = this.daysPerRow, l = this.view.computeDayRange(t), u = this.getDateDayIndex(l.start), c = this.getDateDayIndex(l.end.clone().subtract(1, "days")), d = [];
            for (e = 0; e < this.rowCnt; e++)for (n = e * a, i = n + a - 1, r = n; i >= r; r++)s = Math.max(u, r), o = Math.min(c, r), s = Math.ceil(s), o = Math.floor(o), o >= s && d.push({
                row: e,
                firstRowDayIndex: s - n,
                lastRowDayIndex: o - n,
                isStart: s === u,
                isEnd: o === c
            });
            return d
        },
        renderHeadHtml: function () {
            var t = this.view;
            return '<div class="fc-row ' + t.widgetHeaderClass + '"><table><thead>' + this.renderHeadTrHtml() + "</thead></table></div>"
        },
        renderHeadIntroHtml: function () {
            return this.renderIntroHtml()
        },
        renderHeadTrHtml: function () {
            return "<tr>" + (this.isRTL ? "" : this.renderHeadIntroHtml()) + this.renderHeadDateCellsHtml() + (this.isRTL ? this.renderHeadIntroHtml() : "") + "</tr>"
        },
        renderHeadDateCellsHtml: function () {
            var t, e, n = [];
            for (t = 0; t < this.colCnt; t++)e = this.getCellDate(0, t), n.push(this.renderHeadDateCellHtml(e));
            return n.join("")
        },
        renderHeadDateCellHtml: function (t, e, n) {
            var i = this.view;
            return '<th class="fc-day-header ' + i.widgetHeaderClass + " fc-" + $t[t.day()] + '"' + (1 == this.rowCnt ? ' data-date="' + t.format("YYYY-MM-DD") + '"' : "") + (e > 1 ? ' colspan="' + e + '"' : "") + (n ? " " + n : "") + ">" + tt(t.format(this.colHeadFormat)) + "</th>"
        },
        renderBgTrHtml: function (t) {
            return "<tr>" + (this.isRTL ? "" : this.renderBgIntroHtml(t)) + this.renderBgCellsHtml(t) + (this.isRTL ? this.renderBgIntroHtml(t) : "") + "</tr>"
        },
        renderBgIntroHtml: function (t) {
            return this.renderIntroHtml()
        },
        renderBgCellsHtml: function (t) {
            var e, n, i = [];
            for (e = 0; e < this.colCnt; e++)n = this.getCellDate(t, e), i.push(this.renderBgCellHtml(n));
            return i.join("")
        },
        renderBgCellHtml: function (t, e) {
            var n = this.view, i = this.getDayClasses(t);
            return i.unshift("fc-day", n.widgetContentClass), '<td class="' + i.join(" ") + '" data-date="' + t.format("YYYY-MM-DD") + '"' + (e ? " " + e : "") + "></td>"
        },
        renderIntroHtml: function () {
        },
        bookendCells: function (t) {
            var e = this.renderIntroHtml();
            e && (this.isRTL ? t.append(e) : t.prepend(e))
        }
    }, me = jt.DayGrid = pe.extend(ve, {
        numbersVisible: !1,
        bottomCoordPadding: 0,
        rowEls: null,
        cellEls: null,
        helperEls: null,
        rowCoordCache: null,
        colCoordCache: null,
        renderDates: function (t) {
            var e, n, i = this.view, r = this.rowCnt, s = this.colCnt, o = "";
            for (e = 0; r > e; e++)o += this.renderDayRowHtml(e, t);
            for (this.el.html(o), this.rowEls = this.el.find(".fc-row"), this.cellEls = this.el.find(".fc-day"), this.rowCoordCache = new de({
                els: this.rowEls,
                isVertical: !0
            }), this.colCoordCache = new de({
                els: this.cellEls.slice(0, this.colCnt), isHorizontal: !0
            }), e = 0; r > e; e++)for (n = 0; s > n; n++)i.trigger("dayRender", null, this.getCellDate(e, n), this.getCellEl(e, n))
        },
        unrenderDates: function () {
            this.removeSegPopover()
        },
        renderBusinessHours: function () {
            var t = this.view.calendar.getBusinessHoursEvents(!0), e = this.eventsToSegs(t);
            this.renderFill("businessHours", e, "bgevent")
        },
        renderDayRowHtml: function (t, e) {
            var n = this.view, i = ["fc-row", "fc-week", n.widgetContentClass];
            return e && i.push("fc-rigid"), '<div class="' + i.join(" ") + '"><div class="fc-bg"><table>' + this.renderBgTrHtml(t) + '</table></div><div class="fc-content-skeleton"><table>' + (this.numbersVisible ? "<thead>" + this.renderNumberTrHtml(t) + "</thead>" : "") + "</table></div></div>"
        },
        renderNumberTrHtml: function (t) {
            return "<tr>" + (this.isRTL ? "" : this.renderNumberIntroHtml(t)) + this.renderNumberCellsHtml(t) + (this.isRTL ? this.renderNumberIntroHtml(t) : "") + "</tr>"
        },
        renderNumberIntroHtml: function (t) {
            return this.renderIntroHtml()
        },
        renderNumberCellsHtml: function (t) {
            var e, n, i = [];
            for (e = 0; e < this.colCnt; e++)n = this.getCellDate(t, e), i.push(this.renderNumberCellHtml(n));
            return i.join("")
        },
        renderNumberCellHtml: function (t) {
            var e;
            return this.view.dayNumbersVisible ? (e = this.getDayClasses(t), e.unshift("fc-day-number"), '<td class="' + e.join(" ") + '" data-date="' + t.format() + '">' + t.date() + "</td>") : "<td/>"
        },
        computeEventTimeFormat: function () {
            return this.view.opt("extraSmallTimeFormat")
        },
        computeDisplayEventEnd: function () {
            return 1 == this.colCnt
        },
        rangeUpdated: function () {
            this.updateDayTable()
        },
        spanToSegs: function (t) {
            var e, n, i = this.sliceRangeByRow(t);
            for (e = 0; e < i.length; e++)n = i[e], this.isRTL ? (n.leftCol = this.daysPerRow - 1 - n.lastRowDayIndex, n.rightCol = this.daysPerRow - 1 - n.firstRowDayIndex) : (n.leftCol = n.firstRowDayIndex, n.rightCol = n.lastRowDayIndex);
            return i
        },
        prepareHits: function () {
            this.colCoordCache.build(), this.rowCoordCache.build(), this.rowCoordCache.bottoms[this.rowCnt - 1] += this.bottomCoordPadding
        },
        releaseHits: function () {
            this.colCoordCache.clear(), this.rowCoordCache.clear()
        },
        queryHit: function (t, e) {
            var n = this.colCoordCache.getHorizontalIndex(t), i = this.rowCoordCache.getVerticalIndex(e);
            return null != i && null != n ? this.getCellHit(i, n) : void 0
        },
        getHitSpan: function (t) {
            return this.getCellRange(t.row, t.col)
        },
        getHitEl: function (t) {
            return this.getCellEl(t.row, t.col)
        },
        getCellHit: function (t, e) {
            return {
                row: t,
                col: e,
                component: this,
                left: this.colCoordCache.getLeftOffset(e),
                right: this.colCoordCache.getRightOffset(e),
                top: this.rowCoordCache.getTopOffset(t),
                bottom: this.rowCoordCache.getBottomOffset(t)
            }
        },
        getCellEl: function (t, e) {
            return this.cellEls.eq(t * this.colCnt + e)
        },
        renderDrag: function (t, e) {
            return this.renderHighlight(this.eventToSpan(t)), e && !e.el.closest(this.el).length ? this.renderEventLocationHelper(t, e) : void 0
        },
        unrenderDrag: function () {
            this.unrenderHighlight(), this.unrenderHelper()
        },
        renderEventResize: function (t, e) {
            return this.renderHighlight(this.eventToSpan(t)), this.renderEventLocationHelper(t, e)
        },
        unrenderEventResize: function () {
            this.unrenderHighlight(), this.unrenderHelper()
        },
        renderHelper: function (e, n) {
            var i, r = [], s = this.eventToSegs(e);
            return s = this.renderFgSegEls(s), i = this.renderSegRows(s), this.rowEls.each(function (e, s) {
                var o, a = t(s), l = t('<div class="fc-helper-skeleton"><table/></div>');
                o = n && n.row === e ? n.el.position().top : a.find(".fc-content-skeleton tbody").position().top, l.css("top", o).find("table").append(i[e].tbodyEl), a.append(l), r.push(l[0])
            }), this.helperEls = t(r)
        },
        unrenderHelper: function () {
            this.helperEls && (this.helperEls.remove(), this.helperEls = null)
        },
        fillSegTag: "td",
        renderFill: function (e, n, i) {
            var r, s, o, a = [];
            for (n = this.renderFillSegEls(e, n), r = 0; r < n.length; r++)s = n[r], o = this.renderFillRow(e, s, i), this.rowEls.eq(s.row).append(o), a.push(o[0]);
            return this.elsByFill[e] = t(a), n
        },
        renderFillRow: function (e, n, i) {
            var r, s, o = this.colCnt, a = n.leftCol, l = n.rightCol + 1;
            return i = i || e.toLowerCase(), r = t('<div class="fc-' + i + '-skeleton"><table><tr/></table></div>'), s = r.find("tr"), a > 0 && s.append('<td colspan="' + a + '"/>'), s.append(n.el.attr("colspan", l - a)), o > l && s.append('<td colspan="' + (o - l) + '"/>'), this.bookendCells(s), r
        }
    });
    me.mixin({
        rowStructs: null, unrenderEvents: function () {
            this.removeSegPopover(), pe.prototype.unrenderEvents.apply(this, arguments)
        }, getEventSegs: function () {
            return pe.prototype.getEventSegs.call(this).concat(this.popoverSegs || [])
        }, renderBgSegs: function (e) {
            var n = t.grep(e, function (t) {
                return t.event.allDay
            });
            return pe.prototype.renderBgSegs.call(this, n)
        }, renderFgSegs: function (e) {
            var n;
            return e = this.renderFgSegEls(e), n = this.rowStructs = this.renderSegRows(e), this.rowEls.each(function (e, i) {
                t(i).find(".fc-content-skeleton > table").append(n[e].tbodyEl)
            }), e
        }, unrenderFgSegs: function () {
            for (var t, e = this.rowStructs || []; t = e.pop();)t.tbodyEl.remove();
            this.rowStructs = null
        }, renderSegRows: function (t) {
            var e, n, i = [];
            for (e = this.groupSegRows(t), n = 0; n < e.length; n++)i.push(this.renderSegRow(n, e[n]));
            return i
        }, fgSegHtml: function (t, e) {
            var n, i, r = this.view, s = t.event, o = r.isEventDraggable(s), a = !e && s.allDay && t.isStart && r.isEventResizableFromStart(s), l = !e && s.allDay && t.isEnd && r.isEventResizableFromEnd(s), u = this.getSegClasses(t, o, a || l), c = nt(this.getSegSkinCss(t)), d = "";
            return u.unshift("fc-day-grid-event", "fc-h-event"), t.isStart && (n = this.getEventTimeText(s), n && (d = '<span class="fc-time">' + tt(n) + "</span>")), i = '<span class="fc-title">' + (tt(s.title || "") || "&nbsp;") + "</span>", '<a class="' + u.join(" ") + '"' + (s.url ? ' href="' + tt(s.url) + '"' : "") + (c ? ' style="' + c + '"' : "") + '><div class="fc-content">' + (this.isRTL ? i + " " + d : d + " " + i) + "</div>" + (a ? '<div class="fc-resizer fc-start-resizer" />' : "") + (l ? '<div class="fc-resizer fc-end-resizer" />' : "") + "</a>"
        }, renderSegRow: function (e, n) {
            function i(e) {
                for (; e > o;)c = (m[r - 1] || [])[o], c ? c.attr("rowspan", parseInt(c.attr("rowspan") || 1, 10) + 1) : (c = t("<td/>"), a.append(c)), v[r][o] = c, m[r][o] = c, o++
            }

            var r, s, o, a, l, u, c, d = this.colCnt, h = this.buildSegLevels(n), f = Math.max(1, h.length), g = t("<tbody/>"), p = [], v = [], m = [];
            for (r = 0; f > r; r++) {
                if (s = h[r], o = 0, a = t("<tr/>"), p.push([]), v.push([]), m.push([]), s)for (l = 0; l < s.length; l++) {
                    for (u = s[l], i(u.leftCol), c = t('<td class="fc-event-container"/>').append(u.el), u.leftCol != u.rightCol ? c.attr("colspan", u.rightCol - u.leftCol + 1) : m[r][o] = c; o <= u.rightCol;)v[r][o] = c, p[r][o] = u, o++;
                    a.append(c)
                }
                i(d), this.bookendCells(a), g.append(a)
            }
            return {row: e, tbodyEl: g, cellMatrix: v, segMatrix: p, segLevels: h, segs: n}
        }, buildSegLevels: function (t) {
            var e, n, i, r = [];
            for (this.sortEventSegs(t), e = 0; e < t.length; e++) {
                for (n = t[e], i = 0; i < r.length && zt(n, r[i]); i++);
                n.level = i, (r[i] || (r[i] = [])).push(n)
            }
            for (i = 0; i < r.length; i++)r[i].sort(Mt);
            return r
        }, groupSegRows: function (t) {
            var e, n = [];
            for (e = 0; e < this.rowCnt; e++)n.push([]);
            for (e = 0; e < t.length; e++)n[t[e].row].push(t[e]);
            return n
        }
    }), me.mixin({
        segPopover: null, popoverSegs: null, removeSegPopover: function () {
            this.segPopover && this.segPopover.hide()
        }, limitRows: function (t) {
            var e, n, i = this.rowStructs || [];
            for (e = 0; e < i.length; e++)this.unlimitRow(e), n = t ? "number" == typeof t ? t : this.computeRowLevelLimit(e) : !1, n !== !1 && this.limitRow(e, n)
        }, computeRowLevelLimit: function (e) {
            function n(e, n) {
                s = Math.max(s, t(n).outerHeight())
            }

            var i, r, s, o = this.rowEls.eq(e), a = o.height(), l = this.rowStructs[e].tbodyEl.children();
            for (i = 0; i < l.length; i++)if (r = l.eq(i).removeClass("fc-limited"), s = 0, r.find("> td > :first-child").each(n), r.position().top + s > a)return i;
            return !1
        }, limitRow: function (e, n) {
            function i(i) {
                for (; i > D;)u = w.getCellSegs(e, D, n), u.length && (h = s[n - 1][D], y = w.renderMoreLink(e, D, u), m = t("<div/>").append(y), h.append(m), E.push(m[0])), D++
            }

            var r, s, o, a, l, u, c, d, h, f, g, p, v, m, y, w = this, S = this.rowStructs[e], E = [], D = 0;
            if (n && n < S.segLevels.length) {
                for (r = S.segLevels[n - 1], s = S.cellMatrix, o = S.tbodyEl.children().slice(n).addClass("fc-limited").get(), a = 0; a < r.length; a++) {
                    for (l = r[a], i(l.leftCol), d = [], c = 0; D <= l.rightCol;)u = this.getCellSegs(e, D, n), d.push(u), c += u.length, D++;
                    if (c) {
                        for (h = s[n - 1][l.leftCol], f = h.attr("rowspan") || 1, g = [], p = 0; p < d.length; p++)v = t('<td class="fc-more-cell"/>').attr("rowspan", f), u = d[p], y = this.renderMoreLink(e, l.leftCol + p, [l].concat(u)), m = t("<div/>").append(y), v.append(m), g.push(v[0]), E.push(v[0]);
                        h.addClass("fc-limited").after(t(g)), o.push(h[0])
                    }
                }
                i(this.colCnt), S.moreEls = t(E), S.limitedEls = t(o)
            }
        }, unlimitRow: function (t) {
            var e = this.rowStructs[t];
            e.moreEls && (e.moreEls.remove(), e.moreEls = null), e.limitedEls && (e.limitedEls.removeClass("fc-limited"), e.limitedEls = null)
        }, renderMoreLink: function (e, n, i) {
            var r = this, s = this.view;
            return t('<a class="fc-more"/>').text(this.getMoreLinkText(i.length)).on("click", function (o) {
                var a = s.opt("eventLimitClick"), l = r.getCellDate(e, n), u = t(this), c = r.getCellEl(e, n), d = r.getCellSegs(e, n), h = r.resliceDaySegs(d, l), f = r.resliceDaySegs(i, l);
                "function" == typeof a && (a = s.trigger("eventLimitClick", null, {
                    date: l,
                    dayEl: c,
                    moreEl: u,
                    segs: h,
                    hiddenSegs: f
                }, o)), "popover" === a ? r.showSegPopover(e, n, u, h) : "string" == typeof a && s.calendar.zoomTo(l, a)
            })
        }, showSegPopover: function (t, e, n, i) {
            var r, s, o = this, a = this.view, l = n.parent();
            r = 1 == this.rowCnt ? a.el : this.rowEls.eq(t), s = {
                className: "fc-more-popover",
                content: this.renderSegPopoverContent(t, e, i),
                parentEl: this.el,
                top: r.offset().top,
                autoHide: !0,
                viewportConstrain: a.opt("popoverViewportConstrain"),
                hide: function () {
                    o.segPopover.removeElement(), o.segPopover = null, o.popoverSegs = null
                }
            }, this.isRTL ? s.right = l.offset().left + l.outerWidth() + 1 : s.left = l.offset().left - 1, this.segPopover = new ce(s), this.segPopover.show()
        }, renderSegPopoverContent: function (e, n, i) {
            var r, s = this.view, o = s.opt("theme"), a = this.getCellDate(e, n).format(s.opt("dayPopoverFormat")), l = t('<div class="fc-header ' + s.widgetHeaderClass + '"><span class="fc-close ' + (o ? "ui-icon ui-icon-closethick" : "fc-icon fc-icon-x") + '"></span><span class="fc-title">' + tt(a) + '</span><div class="fc-clear"/></div><div class="fc-body ' + s.widgetContentClass + '"><div class="fc-event-container"></div></div>'), u = l.find(".fc-event-container");
            for (i = this.renderFgSegEls(i, !0), this.popoverSegs = i, r = 0; r < i.length; r++)this.prepareHits(), i[r].hit = this.getCellHit(e, n), this.releaseHits(), u.append(i[r].el);
            return l
        }, resliceDaySegs: function (e, n) {
            var i = t.map(e, function (t) {
                return t.event
            }), r = n.clone(), s = r.clone().add(1, "days"), o = {start: r, end: s};
            return e = this.eventsToSegs(i, function (t) {
                var e = F(t, o);
                return e ? [e] : []
            }), this.sortEventSegs(e), e
        }, getMoreLinkText: function (t) {
            var e = this.view.opt("eventLimitText");
            return "function" == typeof e ? e(t) : "+" + t + " " + e
        }, getCellSegs: function (t, e, n) {
            for (var i, r = this.rowStructs[t].segMatrix, s = n || 0, o = []; s < r.length;)i = r[s][e], i && o.push(i), s++;
            return o
        }
    });
    var ye = jt.TimeGrid = pe.extend(ve, {
        slotDuration: null,
        snapDuration: null,
        snapsPerSlot: null,
        minTime: null,
        maxTime: null,
        labelFormat: null,
        labelInterval: null,
        colEls: null,
        slatContainerEl: null,
        slatEls: null,
        nowIndicatorEls: null,
        colCoordCache: null,
        slatCoordCache: null,
        constructor: function () {
            pe.apply(this, arguments), this.processOptions()
        },
        renderDates: function () {
            this.el.html(this.renderHtml()), this.colEls = this.el.find(".fc-day"), this.slatContainerEl = this.el.find(".fc-slats"), this.slatEls = this.slatContainerEl.find("tr"), this.colCoordCache = new de({
                els: this.colEls,
                isHorizontal: !0
            }), this.slatCoordCache = new de({els: this.slatEls, isVertical: !0}), this.renderContentSkeleton()
        },
        renderHtml: function () {
            return '<div class="fc-bg"><table>' + this.renderBgTrHtml(0) + '</table></div><div class="fc-slats"><table>' + this.renderSlatRowHtml() + "</table></div>"
        },
        renderSlatRowHtml: function () {
            for (var t, n, i, r = this.view, s = this.isRTL, o = "", a = e.duration(+this.minTime); a < this.maxTime;)t = this.start.clone().time(a), n = st(_(a, this.labelInterval)), i = '<td class="fc-axis fc-time ' + r.widgetContentClass + '" ' + r.axisStyleAttr() + ">" + (n ? "<span>" + tt(t.format(this.labelFormat)) + "</span>" : "") + "</td>", o += '<tr data-time="' + t.format("HH:mm:ss") + '"' + (n ? "" : ' class="fc-minor"') + ">" + (s ? "" : i) + '<td class="' + r.widgetContentClass + '"/>' + (s ? i : "") + "</tr>", a.add(this.slotDuration);
            return o
        },
        processOptions: function () {
            var n, i = this.view, r = i.opt("slotDuration"), s = i.opt("snapDuration");
            r = e.duration(r), s = s ? e.duration(s) : r, this.slotDuration = r, this.snapDuration = s, this.snapsPerSlot = r / s, this.minResizeDuration = s, this.minTime = e.duration(i.opt("minTime")), this.maxTime = e.duration(i.opt("maxTime")), n = i.opt("slotLabelFormat"), t.isArray(n) && (n = n[n.length - 1]), this.labelFormat = n || i.opt("axisFormat") || i.opt("smallTimeFormat"), n = i.opt("slotLabelInterval"), this.labelInterval = n ? e.duration(n) : this.computeLabelInterval(r)
        },
        computeLabelInterval: function (t) {
            var n, i, r;
            for (n = Ne.length - 1; n >= 0; n--)if (i = e.duration(Ne[n]), r = _(i, t), st(r) && r > 1)return i;
            return e.duration(t)
        },
        computeEventTimeFormat: function () {
            return this.view.opt("noMeridiemTimeFormat")
        },
        computeDisplayEventEnd: function () {
            return !0
        },
        prepareHits: function () {
            this.colCoordCache.build(), this.slatCoordCache.build()
        },
        releaseHits: function () {
            this.colCoordCache.clear()
        },
        queryHit: function (t, e) {
            var n = this.snapsPerSlot, i = this.colCoordCache, r = this.slatCoordCache, s = i.getHorizontalIndex(t), o = r.getVerticalIndex(e);
            if (null != s && null != o) {
                var a = r.getTopOffset(o), l = r.getHeight(o), u = (e - a) / l, c = Math.floor(u * n), d = o * n + c, h = a + c / n * l, f = a + (c + 1) / n * l;
                return {
                    col: s,
                    snap: d,
                    component: this,
                    left: i.getLeftOffset(s),
                    right: i.getRightOffset(s),
                    top: h,
                    bottom: f
                }
            }
        },
        getHitSpan: function (t) {
            var e, n = this.getCellDate(0, t.col), i = this.computeSnapTime(t.snap);
            return n.time(i), e = n.clone().add(this.snapDuration), {start: n, end: e}
        },
        getHitEl: function (t) {
            return this.colEls.eq(t.col)
        },
        rangeUpdated: function () {
            this.updateDayTable()
        },
        computeSnapTime: function (t) {
            return e.duration(this.minTime + this.snapDuration * t)
        },
        spanToSegs: function (t) {
            var e, n = this.sliceRangeByTimes(t);
            for (e = 0; e < n.length; e++)this.isRTL ? n[e].col = this.daysPerRow - 1 - n[e].dayIndex : n[e].col = n[e].dayIndex;
            return n
        },
        sliceRangeByTimes: function (t) {
            var e, n, i, r, s = [];
            for (n = 0; n < this.daysPerRow; n++)i = this.dayDates[n].clone(), r = {
                start: i.clone().time(this.minTime),
                end: i.clone().time(this.maxTime)
            }, e = F(t, r), e && (e.dayIndex = n, s.push(e));
            return s
        },
        updateSize: function (t) {
            this.slatCoordCache.build(), t && this.updateSegVerticals([].concat(this.fgSegs || [], this.bgSegs || [], this.businessSegs || []))
        },
        getTotalSlatHeight: function () {
            return this.slatContainerEl.outerHeight()
        },
        computeDateTop: function (t, n) {
            return this.computeTimeTop(e.duration(t - n.clone().stripTime()))
        },
        computeTimeTop: function (t) {
            var e, n, i = this.slatEls.length, r = (t - this.minTime) / this.slotDuration;
            return r = Math.max(0, r), r = Math.min(i, r), e = Math.floor(r), e = Math.min(e, i - 1), n = r - e, this.slatCoordCache.getTopPosition(e) + this.slatCoordCache.getHeight(e) * n
        },
        renderDrag: function (t, e) {
            return e ? this.renderEventLocationHelper(t, e) : void this.renderHighlight(this.eventToSpan(t))
        },
        unrenderDrag: function () {
            this.unrenderHelper(), this.unrenderHighlight()
        },
        renderEventResize: function (t, e) {
            return this.renderEventLocationHelper(t, e)
        },
        unrenderEventResize: function () {
            this.unrenderHelper()
        },
        renderHelper: function (t, e) {
            return this.renderHelperSegs(this.eventToSegs(t), e)
        },
        unrenderHelper: function () {
            this.unrenderHelperSegs()
        },
        renderBusinessHours: function () {
            var t = this.view.calendar.getBusinessHoursEvents(), e = this.eventsToSegs(t);
            this.renderBusinessSegs(e)
        },
        unrenderBusinessHours: function () {
            this.unrenderBusinessSegs()
        },
        getNowIndicatorUnit: function () {
            return "minute"
        },
        renderNowIndicator: function (e) {
            var n, i = this.spanToSegs({start: e, end: e}), r = this.computeDateTop(e, e), s = [];
            for (n = 0; n < i.length; n++)s.push(t('<div class="fc-now-indicator fc-now-indicator-line"></div>').css("top", r).appendTo(this.colContainerEls.eq(i[n].col))[0]);
            i.length > 0 && s.push(t('<div class="fc-now-indicator fc-now-indicator-arrow"></div>').css("top", r).appendTo(this.el.find(".fc-content-skeleton"))[0]), this.nowIndicatorEls = t(s)
        },
        unrenderNowIndicator: function () {
            this.nowIndicatorEls && (this.nowIndicatorEls.remove(), this.nowIndicatorEls = null)
        },
        renderSelection: function (t) {
            this.view.opt("selectHelper") ? this.renderEventLocationHelper(t) : this.renderHighlight(t)
        },
        unrenderSelection: function () {
            this.unrenderHelper(), this.unrenderHighlight()
        },
        renderHighlight: function (t) {
            this.renderHighlightSegs(this.spanToSegs(t))
        },
        unrenderHighlight: function () {
            this.unrenderHighlightSegs()
        }
    });
    ye.mixin({
        colContainerEls: null,
        fgContainerEls: null,
        bgContainerEls: null,
        helperContainerEls: null,
        highlightContainerEls: null,
        businessContainerEls: null,
        fgSegs: null,
        bgSegs: null,
        helperSegs: null,
        highlightSegs: null,
        businessSegs: null,
        renderContentSkeleton: function () {
            var e, n, i = "";
            for (e = 0; e < this.colCnt; e++)i += '<td><div class="fc-content-col"><div class="fc-event-container fc-helper-container"></div><div class="fc-event-container"></div><div class="fc-highlight-container"></div><div class="fc-bgevent-container"></div><div class="fc-business-container"></div></div></td>';
            n = t('<div class="fc-content-skeleton"><table><tr>' + i + "</tr></table></div>"), this.colContainerEls = n.find(".fc-content-col"), this.helperContainerEls = n.find(".fc-helper-container"), this.fgContainerEls = n.find(".fc-event-container:not(.fc-helper-container)"), this.bgContainerEls = n.find(".fc-bgevent-container"), this.highlightContainerEls = n.find(".fc-highlight-container"), this.businessContainerEls = n.find(".fc-business-container"), this.bookendCells(n.find("tr")), this.el.append(n)
        },
        renderFgSegs: function (t) {
            return t = this.renderFgSegsIntoContainers(t, this.fgContainerEls), this.fgSegs = t, t
        },
        unrenderFgSegs: function () {
            this.unrenderNamedSegs("fgSegs")
        },
        renderHelperSegs: function (e, n) {
            var i, r, s, o = [];
            for (e = this.renderFgSegsIntoContainers(e, this.helperContainerEls), i = 0; i < e.length; i++)r = e[i], n && n.col === r.col && (s = n.el, r.el.css({
                left: s.css("left"),
                right: s.css("right"),
                "margin-left": s.css("margin-left"),
                "margin-right": s.css("margin-right")
            })), o.push(r.el[0]);
            return this.helperSegs = e, t(o)
        },
        unrenderHelperSegs: function () {
            this.unrenderNamedSegs("helperSegs")
        },
        renderBgSegs: function (t) {
            return t = this.renderFillSegEls("bgEvent", t), this.updateSegVerticals(t), this.attachSegsByCol(this.groupSegsByCol(t), this.bgContainerEls), this.bgSegs = t, t
        },
        unrenderBgSegs: function () {
            this.unrenderNamedSegs("bgSegs")
        },
        renderHighlightSegs: function (t) {
            t = this.renderFillSegEls("highlight", t), this.updateSegVerticals(t), this.attachSegsByCol(this.groupSegsByCol(t), this.highlightContainerEls), this.highlightSegs = t
        },
        unrenderHighlightSegs: function () {
            this.unrenderNamedSegs("highlightSegs")
        },
        renderBusinessSegs: function (t) {
            t = this.renderFillSegEls("businessHours", t), this.updateSegVerticals(t), this.attachSegsByCol(this.groupSegsByCol(t), this.businessContainerEls), this.businessSegs = t
        },
        unrenderBusinessSegs: function () {
            this.unrenderNamedSegs("businessSegs")
        },
        groupSegsByCol: function (t) {
            var e, n = [];
            for (e = 0; e < this.colCnt; e++)n.push([]);
            for (e = 0; e < t.length; e++)n[t[e].col].push(t[e]);
            return n
        },
        attachSegsByCol: function (t, e) {
            var n, i, r;
            for (n = 0; n < this.colCnt; n++)for (i = t[n], r = 0; r < i.length; r++)e.eq(n).append(i[r].el)
        },
        unrenderNamedSegs: function (t) {
            var e, n = this[t];
            if (n) {
                for (e = 0; e < n.length; e++)n[e].el.remove();
                this[t] = null
            }
        },
        renderFgSegsIntoContainers: function (t, e) {
            var n, i;
            for (t = this.renderFgSegEls(t), n = this.groupSegsByCol(t), i = 0; i < this.colCnt; i++)this.updateFgSegCoords(n[i]);
            return this.attachSegsByCol(n, e), t
        },
        fgSegHtml: function (t, e) {
            var n, i, r, s = this.view, o = t.event, a = s.isEventDraggable(o), l = !e && t.isStart && s.isEventResizableFromStart(o), u = !e && t.isEnd && s.isEventResizableFromEnd(o), c = this.getSegClasses(t, a, l || u), d = nt(this.getSegSkinCss(t));
            return c.unshift("fc-time-grid-event", "fc-v-event"), s.isMultiDayEvent(o) ? (t.isStart || t.isEnd) && (n = this.getEventTimeText(t), i = this.getEventTimeText(t, "LT"), r = this.getEventTimeText(t, null, !1)) : (n = this.getEventTimeText(o), i = this.getEventTimeText(o, "LT"), r = this.getEventTimeText(o, null, !1)), '<a class="' + c.join(" ") + '"' + (o.url ? ' href="' + tt(o.url) + '"' : "") + (d ? ' style="' + d + '"' : "") + '><div class="fc-content">' + (n ? '<div class="fc-time" data-start="' + tt(r) + '" data-full="' + tt(i) + '"><span>' + tt(n) + "</span></div>" : "") + (o.title ? '<div class="fc-title">' + tt(o.title) + "</div>" : "") + '</div><div class="fc-bg"/>' + (u ? '<div class="fc-resizer fc-end-resizer" />' : "") + "</a>"
        },
        updateSegVerticals: function (t) {
            this.computeSegVerticals(t), this.assignSegVerticals(t)
        },
        computeSegVerticals: function (t) {
            var e, n;
            for (e = 0; e < t.length; e++)n = t[e], n.top = this.computeDateTop(n.start, n.start), n.bottom = this.computeDateTop(n.end, n.start)
        },
        assignSegVerticals: function (t) {
            var e, n;
            for (e = 0; e < t.length; e++)n = t[e], n.el.css(this.generateSegVerticalCss(n))
        },
        generateSegVerticalCss: function (t) {
            return {top: t.top, bottom: -t.bottom}
        },
        updateFgSegCoords: function (t) {
            this.computeSegVerticals(t), this.computeFgSegHorizontals(t), this.assignSegVerticals(t), this.assignFgSegHorizontals(t)
        },
        computeFgSegHorizontals: function (t) {
            var e, n, i;
            if (this.sortEventSegs(t), e = Ft(t), Nt(e), n = e[0]) {
                for (i = 0; i < n.length; i++)Bt(n[i]);
                for (i = 0; i < n.length; i++)this.computeFgSegForwardBack(n[i], 0, 0)
            }
        },
        computeFgSegForwardBack: function (t, e, n) {
            var i, r = t.forwardSegs;
            if (void 0 === t.forwardCoord)for (r.length ? (this.sortForwardSegs(r), this.computeFgSegForwardBack(r[0], e + 1, n), t.forwardCoord = r[0].backwardCoord) : t.forwardCoord = 1, t.backwardCoord = t.forwardCoord - (t.forwardCoord - n) / (e + 1), i = 0; i < r.length; i++)this.computeFgSegForwardBack(r[i], 0, t.forwardCoord)
        },
        sortForwardSegs: function (t) {
            t.sort(ot(this, "compareForwardSegs"))
        },
        compareForwardSegs: function (t, e) {
            return e.forwardPressure - t.forwardPressure || (t.backwardCoord || 0) - (e.backwardCoord || 0) || this.compareEventSegs(t, e)
        },
        assignFgSegHorizontals: function (t) {
            var e, n;
            for (e = 0; e < t.length; e++)n = t[e], n.el.css(this.generateFgSegHorizontalCss(n)), n.bottom - n.top < 30 && n.el.addClass("fc-short")
        },
        generateFgSegHorizontalCss: function (t) {
            var e, n, i = this.view.opt("slotEventOverlap"), r = t.backwardCoord, s = t.forwardCoord, o = this.generateSegVerticalCss(t);
            return i && (s = Math.min(1, r + 2 * (s - r))), this.isRTL ? (e = 1 - s, n = r) : (e = r, n = 1 - s), o.zIndex = t.level + 1, o.left = 100 * e + "%", o.right = 100 * n + "%", i && t.forwardPressure && (o[this.isRTL ? "marginLeft" : "marginRight"] = 20), o
        }
    });
    var we = jt.View = Et.extend(le, ue, {
        type: null,
        name: null,
        title: null,
        calendar: null,
        options: null,
        el: null,
        displaying: null,
        isSkeletonRendered: !1,
        isEventsRendered: !1,
        start: null,
        end: null,
        intervalStart: null,
        intervalEnd: null,
        intervalDuration: null,
        intervalUnit: null,
        isRTL: !1,
        isSelected: !1,
        selectedEvent: null,
        eventOrderSpecs: null,
        widgetHeaderClass: null,
        widgetContentClass: null,
        highlightStateClass: null,
        nextDayThreshold: null,
        isHiddenDayHash: null,
        isNowIndicatorRendered: null,
        initialNowDate: null,
        initialNowQueriedMs: null,
        nowIndicatorTimeoutID: null,
        nowIndicatorIntervalID: null,
        constructor: function (t, n, i, r) {
            this.calendar = t, this.type = this.name = n, this.options = i, this.intervalDuration = r || e.duration(1, "day"), this.nextDayThreshold = e.duration(this.opt("nextDayThreshold")), this.initThemingProps(), this.initHiddenDays(), this.isRTL = this.opt("isRTL"), this.eventOrderSpecs = k(this.opt("eventOrder")), this.initialize()
        },
        initialize: function () {
        },
        opt: function (t) {
            return this.options[t]
        },
        trigger: function (t, e) {
            var n = this.calendar;
            return n.trigger.apply(n, [t, e || this].concat(Array.prototype.slice.call(arguments, 2), [this]))
        },
        setDate: function (t) {
            this.setRange(this.computeRange(t))
        },
        setRange: function (e) {
            t.extend(this, e), this.updateTitle()
        },
        computeRange: function (t) {
            var e, n, i = V(this.intervalDuration), r = t.clone().startOf(i), s = r.clone().add(this.intervalDuration);
            return /year|month|week|day/.test(i) ? (r.stripTime(), s.stripTime()) : (r.hasTime() || (r = this.calendar.time(0)), s.hasTime() || (s = this.calendar.time(0))), e = r.clone(), e = this.skipHiddenDays(e), n = s.clone(), n = this.skipHiddenDays(n, -1, !0), {
                intervalUnit: i,
                intervalStart: r,
                intervalEnd: s,
                start: e,
                end: n
            }
        },
        computePrevDate: function (t) {
            return this.massageCurrentDate(t.clone().startOf(this.intervalUnit).subtract(this.intervalDuration), -1)
        },
        computeNextDate: function (t) {
            return this.massageCurrentDate(t.clone().startOf(this.intervalUnit).add(this.intervalDuration))
        },
        massageCurrentDate: function (t, e) {
            return this.intervalDuration.as("days") <= 1 && this.isHiddenDay(t) && (t = this.skipHiddenDays(t, e), t.startOf("day")), t
        },
        updateTitle: function () {
            this.title = this.computeTitle()
        },
        computeTitle: function () {
            return this.formatRange({
                start: this.calendar.applyTimezone(this.intervalStart),
                end: this.calendar.applyTimezone(this.intervalEnd)
            }, this.opt("titleFormat") || this.computeTitleFormat(), this.opt("titleRangeSeparator"))
        },
        computeTitleFormat: function () {
            return "year" == this.intervalUnit ? "YYYY" : "month" == this.intervalUnit ? this.opt("monthYearFormat") : this.intervalDuration.as("days") > 1 ? "ll" : "LL"
        },
        formatRange: function (t, e, n) {
            var i = t.end;
            return i.hasTime() || (i = i.clone().subtract(1)), vt(t.start, i, e, n, this.opt("isRTL"))
        },
        setElement: function (t) {
            this.el = t, this.bindGlobalHandlers()
        },
        removeElement: function () {
            this.clear(), this.isSkeletonRendered && (this.unrenderSkeleton(), this.isSkeletonRendered = !1), this.unbindGlobalHandlers(), this.el.remove()
        },
        display: function (e) {
            var n = this, i = null;
            return this.displaying && (i = this.queryScroll()), this.calendar.freezeContentHeight(), this.clear().then(function () {
                return n.displaying = t.when(n.displayView(e)).then(function () {
                    n.forceScroll(n.computeInitialScroll(i)), n.calendar.unfreezeContentHeight(), n.triggerRender()
                })
            })
        },
        clear: function () {
            var e = this, n = this.displaying;
            return n ? n.then(function () {
                return e.displaying = null, e.clearEvents(), e.clearView()
            }) : t.when()
        },
        displayView: function (t) {
            this.isSkeletonRendered || (this.renderSkeleton(), this.isSkeletonRendered = !0), t && this.setDate(t), this.render && this.render(), this.renderDates(), this.updateSize(), this.renderBusinessHours(), this.startNowIndicator()
        },
        clearView: function () {
            this.unselect(), this.stopNowIndicator(), this.triggerUnrender(), this.unrenderBusinessHours(), this.unrenderDates(), this.destroy && this.destroy()
        },
        renderSkeleton: function () {
        },
        unrenderSkeleton: function () {
        },
        renderDates: function () {
        },
        unrenderDates: function () {
        },
        triggerRender: function () {
            this.trigger("viewRender", this, this, this.el)
        },
        triggerUnrender: function () {
            this.trigger("viewDestroy", this, this, this.el)
        },
        bindGlobalHandlers: function () {
            this.listenTo(t(document), "mousedown", this.handleDocumentMousedown), this.listenTo(t(document), "touchstart", this.handleDocumentTouchStart), this.listenTo(t(document), "touchend", this.handleDocumentTouchEnd)
        },
        unbindGlobalHandlers: function () {
            this.stopListeningTo(t(document))
        },
        initThemingProps: function () {
            var t = this.opt("theme") ? "ui" : "fc";
            this.widgetHeaderClass = t + "-widget-header", this.widgetContentClass = t + "-widget-content", this.highlightStateClass = t + "-state-highlight"
        },
        renderBusinessHours: function () {
        },
        unrenderBusinessHours: function () {
        },
        startNowIndicator: function () {
            var t, n, i, r = this;
            this.opt("nowIndicator") && (t = this.getNowIndicatorUnit(), t && (n = ot(this, "updateNowIndicator"), this.initialNowDate = this.calendar.getNow(), this.initialNowQueriedMs = +new Date, this.renderNowIndicator(this.initialNowDate), this.isNowIndicatorRendered = !0, i = this.initialNowDate.clone().startOf(t).add(1, t) - this.initialNowDate, this.nowIndicatorTimeoutID = setTimeout(function () {
                r.nowIndicatorTimeoutID = null, n(), i = +e.duration(1, t), i = Math.max(100, i), r.nowIndicatorIntervalID = setInterval(n, i)
            }, i)))
        },
        updateNowIndicator: function () {
            this.isNowIndicatorRendered && (this.unrenderNowIndicator(), this.renderNowIndicator(this.initialNowDate.clone().add(new Date - this.initialNowQueriedMs)))
        },
        stopNowIndicator: function () {
            this.isNowIndicatorRendered && (this.nowIndicatorTimeoutID && (clearTimeout(this.nowIndicatorTimeoutID), this.nowIndicatorTimeoutID = null), this.nowIndicatorIntervalID && (clearTimeout(this.nowIndicatorIntervalID), this.nowIndicatorIntervalID = null), this.unrenderNowIndicator(), this.isNowIndicatorRendered = !1)
        },
        getNowIndicatorUnit: function () {
        },
        renderNowIndicator: function (t) {
        },
        unrenderNowIndicator: function () {
        },
        updateSize: function (t) {
            var e;
            t && (e = this.queryScroll()), this.updateHeight(t), this.updateWidth(t), this.updateNowIndicator(), t && this.setScroll(e)
        },
        updateWidth: function (t) {
        },
        updateHeight: function (t) {
            var e = this.calendar;
            this.setHeight(e.getSuggestedViewHeight(), e.isHeightAuto())
        },
        setHeight: function (t, e) {
        },
        computeInitialScroll: function (t) {
            return 0
        },
        queryScroll: function () {
        },
        setScroll: function (t) {
        },
        forceScroll: function (t) {
            var e = this;
            this.setScroll(t), setTimeout(function () {
                e.setScroll(t)
            }, 0)
        },
        displayEvents: function (t) {
            var e = this.queryScroll();
            this.clearEvents(), this.renderEvents(t), this.isEventsRendered = !0, this.setScroll(e), this.triggerEventRender()
        },
        clearEvents: function () {
            var t;
            this.isEventsRendered && (t = this.queryScroll(), this.triggerEventUnrender(), this.destroyEvents && this.destroyEvents(), this.unrenderEvents(), this.setScroll(t), this.isEventsRendered = !1)
        },
        renderEvents: function (t) {
        },
        unrenderEvents: function () {
        },
        triggerEventRender: function () {
            this.renderedEventSegEach(function (t) {
                this.trigger("eventAfterRender", t.event, t.event, t.el)
            }), this.trigger("eventAfterAllRender")
        },
        triggerEventUnrender: function () {
            this.renderedEventSegEach(function (t) {
                this.trigger("eventDestroy", t.event, t.event, t.el)
            })
        },
        resolveEventEl: function (e, n) {
            var i = this.trigger("eventRender", e, e, n);
            return i === !1 ? n = null : i && i !== !0 && (n = t(i)), n
        },
        showEvent: function (t) {
            this.renderedEventSegEach(function (t) {
                t.el.css("visibility", "")
            }, t)
        },
        hideEvent: function (t) {
            this.renderedEventSegEach(function (t) {
                t.el.css("visibility", "hidden")
            }, t)
        },
        renderedEventSegEach: function (t, e) {
            var n, i = this.getEventSegs();
            for (n = 0; n < i.length; n++)e && i[n].event._id !== e._id || i[n].el && t.call(this, i[n])
        },
        getEventSegs: function () {
            return []
        },
        isEventDraggable: function (t) {
            var e = t.source || {};
            return J(t.startEditable, e.startEditable, this.opt("eventStartEditable"), t.editable, e.editable, this.opt("editable"))
        },
        reportEventDrop: function (t, e, n, i, r) {
            var s = this.calendar, o = s.mutateEvent(t, e, n), a = function () {
                o.undo(), s.reportEventChange()
            };
            this.triggerEventDrop(t, o.dateDelta, a, i, r), s.reportEventChange()
        },
        triggerEventDrop: function (t, e, n, i, r) {
            this.trigger("eventDrop", i[0], t, e, n, r, {})
        },
        reportExternalDrop: function (e, n, i, r, s) {
            var o, a, l = e.eventProps;
            l && (o = t.extend({}, l, n), a = this.calendar.renderEvent(o, e.stick)[0]), this.triggerExternalDrop(a, n, i, r, s)
        },
        triggerExternalDrop: function (t, e, n, i, r) {
            this.trigger("drop", n[0], e.start, i, r), t && this.trigger("eventReceive", null, t)
        },
        renderDrag: function (t, e) {
        },
        unrenderDrag: function () {
        },
        isEventResizableFromStart: function (t) {
            return this.opt("eventResizableFromStart") && this.isEventResizable(t)
        },
        isEventResizableFromEnd: function (t) {
            return this.isEventResizable(t)
        },
        isEventResizable: function (t) {
            var e = t.source || {};
            return J(t.durationEditable, e.durationEditable, this.opt("eventDurationEditable"), t.editable, e.editable, this.opt("editable"))
        },
        reportEventResize: function (t, e, n, i, r) {
            var s = this.calendar, o = s.mutateEvent(t, e, n), a = function () {
                o.undo(), s.reportEventChange()
            };
            this.triggerEventResize(t, o.durationDelta, a, i, r), s.reportEventChange()
        },
        triggerEventResize: function (t, e, n, i, r) {
            this.trigger("eventResize", i[0], t, e, n, r, {})
        },
        select: function (t, e) {
            this.unselect(e), this.renderSelection(t), this.reportSelection(t, e)
        },
        renderSelection: function (t) {
        },
        reportSelection: function (t, e) {
            this.isSelected = !0, this.triggerSelect(t, e)
        },
        triggerSelect: function (t, e) {
            this.trigger("select", null, this.calendar.applyTimezone(t.start), this.calendar.applyTimezone(t.end), e)
        },
        unselect: function (t) {
            this.isSelected && (this.isSelected = !1, this.destroySelection && this.destroySelection(), this.unrenderSelection(), this.trigger("unselect", null, t))
        },
        unrenderSelection: function () {
        },
        selectEvent: function (t) {
            this.selectedEvent && this.selectedEvent === t || (this.unselectEvent(), this.renderedEventSegEach(function (t) {
                t.el.addClass("fc-selected")
            }, t), this.selectedEvent = t)
        },
        unselectEvent: function () {
            this.selectedEvent && (this.renderedEventSegEach(function (t) {
                t.el.removeClass("fc-selected")
            }, this.selectedEvent), this.selectedEvent = null)
        },
        isEventSelected: function (t) {
            return this.selectedEvent && this.selectedEvent._id === t._id
        },
        handleDocumentMousedown: function (t) {
            !this.calendar.isTouch && S(t) && (this.processRangeUnselect(t), this.processEventUnselect(t))
        },
        handleDocumentTouchStart: function (t) {
            this.processRangeUnselect(t)
        },
        handleDocumentTouchEnd: function (t) {
            this.processEventUnselect(t)
        },
        processRangeUnselect: function (e) {
            var n;
            this.isSelected && this.opt("unselectAuto") && (n = this.opt("unselectCancel"), n && t(e.target).closest(n).length || this.unselect(e))
        },
        processEventUnselect: function (e) {
            this.selectedEvent && (t(e.target).closest(".fc-selected").length || this.unselectEvent())
        },
        triggerDayClick: function (t, e, n) {
            this.trigger("dayClick", e, this.calendar.applyTimezone(t.start), n)
        },
        initHiddenDays: function () {
            var e, n = this.opt("hiddenDays") || [], i = [], r = 0;
            for (this.opt("weekends") === !1 && n.push(0, 6), e = 0; 7 > e; e++)(i[e] = -1 !== t.inArray(e, n)) || r++;
            if (!r)throw"invalid hiddenDays";
            this.isHiddenDayHash = i
        },
        isHiddenDay: function (t) {
            return e.isMoment(t) && (t = t.day()), this.isHiddenDayHash[t];
        },
        skipHiddenDays: function (t, e, n) {
            var i = t.clone();
            for (e = e || 1; this.isHiddenDayHash[(i.day() + (n ? e : 0) + 7) % 7];)i.add(e, "days");
            return i
        },
        computeDayRange: function (t) {
            var e, n = t.start.clone().stripTime(), i = t.end, r = null;
            return i && (r = i.clone().stripTime(), e = +i.time(), e && e >= this.nextDayThreshold && r.add(1, "days")), (!i || n >= r) && (r = n.clone().add(1, "days")), {
                start: n,
                end: r
            }
        },
        isMultiDayEvent: function (t) {
            var e = this.computeDayRange(t);
            return e.end.diff(e.start, "days") > 1
        }
    }), Se = jt.Scroller = Et.extend({
        el: null,
        scrollEl: null,
        overflowX: null,
        overflowY: null,
        constructor: function (t) {
            t = t || {}, this.overflowX = t.overflowX || t.overflow || "auto", this.overflowY = t.overflowY || t.overflow || "auto"
        },
        render: function () {
            this.el = this.renderEl(), this.applyOverflow()
        },
        renderEl: function () {
            return this.scrollEl = t('<div class="fc-scroller"></div>')
        },
        clear: function () {
            this.setHeight("auto"), this.applyOverflow()
        },
        destroy: function () {
            this.el.remove()
        },
        applyOverflow: function () {
            this.scrollEl.css({"overflow-x": this.overflowX, "overflow-y": this.overflowY})
        },
        lockOverflow: function (t) {
            var e = this.overflowX, n = this.overflowY;
            t = t || this.getScrollbarWidths(), "auto" === e && (e = t.top || t.bottom || this.scrollEl[0].scrollWidth - 1 > this.scrollEl[0].clientWidth ? "scroll" : "hidden"), "auto" === n && (n = t.left || t.right || this.scrollEl[0].scrollHeight - 1 > this.scrollEl[0].clientHeight ? "scroll" : "hidden"), this.scrollEl.css({
                "overflow-x": e,
                "overflow-y": n
            })
        },
        setHeight: function (t) {
            this.scrollEl.height(t)
        },
        getScrollTop: function () {
            return this.scrollEl.scrollTop()
        },
        setScrollTop: function (t) {
            this.scrollEl.scrollTop(t)
        },
        getClientWidth: function () {
            return this.scrollEl[0].clientWidth
        },
        getClientHeight: function () {
            return this.scrollEl[0].clientHeight
        },
        getScrollbarWidths: function () {
            return v(this.scrollEl)
        }
    }), Ee = jt.Calendar = Et.extend({
        dirDefaults: null,
        langDefaults: null,
        overrides: null,
        options: null,
        viewSpecCache: null,
        view: null,
        header: null,
        loadingLevel: 0,
        isTouch: !1,
        constructor: At,
        initialize: function () {
        },
        initOptions: function (t) {
            var e, r, s, o;
            t = i(t), e = t.lang, r = De[e], r || (e = Ee.defaults.lang, r = De[e] || {}), s = J(t.isRTL, r.isRTL, Ee.defaults.isRTL), o = s ? Ee.rtlDefaults : {}, this.dirDefaults = o, this.langDefaults = r, this.overrides = t, this.options = n([Ee.defaults, o, r, t]), Ot(this.options), this.isTouch = null != this.options.isTouch ? this.options.isTouch : jt.isTouch, this.viewSpecCache = {}
        },
        getViewSpec: function (t) {
            var e = this.viewSpecCache;
            return e[t] || (e[t] = this.buildViewSpec(t))
        },
        getUnitViewSpec: function (e) {
            var n, i, r;
            if (-1 != t.inArray(e, Xt))for (n = this.header.getViewsWithButtons(), t.each(jt.views, function (t) {
                n.push(t)
            }), i = 0; i < n.length; i++)if (r = this.getViewSpec(n[i]), r && r.singleUnit == e)return r
        },
        buildViewSpec: function (t) {
            for (var i, r, s, o, a = this.overrides.views || {}, l = [], u = [], c = [], d = t; d;)i = Zt[d], r = a[d], d = null, "function" == typeof i && (i = {"class": i}), i && (l.unshift(i), u.unshift(i.defaults || {}), s = s || i.duration, d = d || i.type), r && (c.unshift(r), s = s || r.duration, d = d || r.type);
            return i = Z(l), i.type = t, i["class"] ? (s && (s = e.duration(s), s.valueOf() && (i.duration = s, o = V(s), 1 === s.as(o) && (i.singleUnit = o, c.unshift(a[o] || {})))), i.defaults = n(u), i.overrides = n(c), this.buildViewSpecOptions(i), this.buildViewSpecButtonText(i, t), i) : !1
        },
        buildViewSpecOptions: function (t) {
            t.options = n([Ee.defaults, t.defaults, this.dirDefaults, this.langDefaults, this.overrides, t.overrides]), Ot(t.options)
        },
        buildViewSpecButtonText: function (t, e) {
            function n(n) {
                var i = n.buttonText || {};
                return i[e] || (t.singleUnit ? i[t.singleUnit] : null)
            }

            t.buttonTextOverride = n(this.overrides) || t.overrides.buttonText, t.buttonTextDefault = n(this.langDefaults) || n(this.dirDefaults) || t.defaults.buttonText || n(Ee.defaults) || (t.duration ? this.humanizeDuration(t.duration) : null) || e
        },
        instantiateView: function (t) {
            var e = this.getViewSpec(t);
            return new e["class"](this, t, e.options, e.duration)
        },
        isValidViewType: function (t) {
            return Boolean(this.getViewSpec(t))
        },
        pushLoading: function () {
            this.loadingLevel++ || this.trigger("loading", null, !0, this.view)
        },
        popLoading: function () {
            --this.loadingLevel || this.trigger("loading", null, !1, this.view)
        },
        buildSelectSpan: function (t, e) {
            var n, i = this.moment(t).stripZone();
            return n = e ? this.moment(e).stripZone() : i.hasTime() ? i.clone().add(this.defaultTimedEventDuration) : i.clone().add(this.defaultAllDayEventDuration), {
                start: i,
                end: n
            }
        }
    });
    Ee.mixin(le), Ee.defaults = {
        titleRangeSeparator: " — ",
        monthYearFormat: "MMMM YYYY",
        defaultTimedEventDuration: "02:00:00",
        defaultAllDayEventDuration: {days: 1},
        forceEventDuration: !1,
        nextDayThreshold: "09:00:00",
        defaultView: "month",
        aspectRatio: 1.35,
        header: {left: "title", center: "", right: "today prev,next"},
        weekends: !0,
        weekNumbers: !1,
        weekNumberTitle: "W",
        weekNumberCalculation: "local",
        scrollTime: "06:00:00",
        lazyFetching: !0,
        startParam: "start",
        endParam: "end",
        timezoneParam: "timezone",
        timezone: !1,
        isRTL: !1,
        buttonText: {
            prev: "prev",
            next: "next",
            prevYear: "prev year",
            nextYear: "next year",
            year: "year",
            today: "today",
            month: "month",
            week: "week",
            day: "day"
        },
        buttonIcons: {
            prev: "left-single-arrow",
            next: "right-single-arrow",
            prevYear: "left-double-arrow",
            nextYear: "right-double-arrow"
        },
        theme: !1,
        themeButtonIcons: {
            prev: "circle-triangle-w",
            next: "circle-triangle-e",
            prevYear: "seek-prev",
            nextYear: "seek-next"
        },
        dragOpacity: .75,
        dragRevertDuration: 500,
        dragScroll: !0,
        unselectAuto: !0,
        dropAccept: "*",
        eventOrder: "title",
        eventLimit: !1,
        eventLimitText: "more",
        eventLimitClick: "popover",
        dayPopoverFormat: "LL",
        handleWindowResize: !0,
        windowResizeDelay: 200,
        longPressDelay: 1e3
    }, Ee.englishDefaults = {dayPopoverFormat: "dddd, MMMM D"}, Ee.rtlDefaults = {
        header: {
            left: "next,prev today",
            center: "",
            right: "title"
        },
        buttonIcons: {
            prev: "right-single-arrow",
            next: "left-single-arrow",
            prevYear: "right-double-arrow",
            nextYear: "left-double-arrow"
        },
        themeButtonIcons: {
            prev: "circle-triangle-e",
            next: "circle-triangle-w",
            nextYear: "seek-prev",
            prevYear: "seek-next"
        }
    };
    var De = jt.langs = {};
    jt.datepickerLang = function (e, n, i) {
        var r = De[e] || (De[e] = {});
        r.isRTL = i.isRTL, r.weekNumberTitle = i.weekHeader, t.each(be, function (t, e) {
            r[t] = e(i)
        }), t.datepicker && (t.datepicker.regional[n] = t.datepicker.regional[e] = i, t.datepicker.regional.en = t.datepicker.regional[""], t.datepicker.setDefaults(i))
    }, jt.lang = function (e, i) {
        var r, s;
        r = De[e] || (De[e] = {}), i && (r = De[e] = n([r, i])), s = _t(e), t.each(Te, function (t, e) {
            null == r[t] && (r[t] = e(s, r))
        }), Ee.defaults.lang = e
    };
    var be = {
        buttonText: function (t) {
            return {prev: et(t.prevText), next: et(t.nextText), today: et(t.currentText)}
        }, monthYearFormat: function (t) {
            return t.showMonthAfterYear ? "YYYY[" + t.yearSuffix + "] MMMM" : "MMMM YYYY[" + t.yearSuffix + "]"
        }
    }, Te = {
        dayOfMonthFormat: function (t, e) {
            var n = t.longDateFormat("l");
            return n = n.replace(/^Y+[^\w\s]*|[^\w\s]*Y+$/g, ""), e.isRTL ? n += " ddd" : n = "ddd " + n, n
        }, mediumTimeFormat: function (t) {
            return t.longDateFormat("LT").replace(/\s*a$/i, "a")
        }, smallTimeFormat: function (t) {
            return t.longDateFormat("LT").replace(":mm", "(:mm)").replace(/(\Wmm)$/, "($1)").replace(/\s*a$/i, "a")
        }, extraSmallTimeFormat: function (t) {
            return t.longDateFormat("LT").replace(":mm", "(:mm)").replace(/(\Wmm)$/, "($1)").replace(/\s*a$/i, "t")
        }, hourFormat: function (t) {
            return t.longDateFormat("LT").replace(":mm", "").replace(/(\Wmm)$/, "").replace(/\s*a$/i, "a")
        }, noMeridiemTimeFormat: function (t) {
            return t.longDateFormat("LT").replace(/\s*a$/i, "")
        }
    }, Ce = {
        smallDayDateFormat: function (t) {
            return t.isRTL ? "D dd" : "dd D"
        }, weekFormat: function (t) {
            return t.isRTL ? "w[ " + t.weekNumberTitle + "]" : "[" + t.weekNumberTitle + " ]w"
        }, smallWeekFormat: function (t) {
            return t.isRTL ? "w[" + t.weekNumberTitle + "]" : "[" + t.weekNumberTitle + "]w"
        }
    };
    jt.lang("en", Ee.englishDefaults), jt.sourceNormalizers = [], jt.sourceFetchers = [];
    var He = {dataType: "json", cache: !1}, Re = 1;
    Ee.prototype.getPeerEvents = function (t, e) {
        var n, i, r = this.getEventCache(), s = [];
        for (n = 0; n < r.length; n++)i = r[n], e && e._id === i._id || s.push(i);
        return s
    };
    var xe = jt.BasicView = we.extend({
        scroller: null,
        dayGridClass: me,
        dayGrid: null,
        dayNumbersVisible: !1,
        weekNumbersVisible: !1,
        weekNumberWidth: null,
        headContainerEl: null,
        headRowEl: null,
        initialize: function () {
            this.dayGrid = this.instantiateDayGrid(), this.scroller = new Se({overflowX: "hidden", overflowY: "auto"})
        },
        instantiateDayGrid: function () {
            var t = this.dayGridClass.extend(Ie);
            return new t(this)
        },
        setRange: function (t) {
            we.prototype.setRange.call(this, t), this.dayGrid.breakOnWeeks = /year|month|week/.test(this.intervalUnit), this.dayGrid.setRange(t)
        },
        computeRange: function (t) {
            var e = we.prototype.computeRange.call(this, t);
            return /year|month/.test(e.intervalUnit) && (e.start.startOf("week"), e.start = this.skipHiddenDays(e.start), e.end.weekday() && (e.end.add(1, "week").startOf("week"), e.end = this.skipHiddenDays(e.end, -1, !0))), e
        },
        renderDates: function () {
            this.dayNumbersVisible = this.dayGrid.rowCnt > 1, this.weekNumbersVisible = this.opt("weekNumbers"), this.dayGrid.numbersVisible = this.dayNumbersVisible || this.weekNumbersVisible, this.el.addClass("fc-basic-view").html(this.renderSkeletonHtml()), this.renderHead(), this.scroller.render();
            var e = this.scroller.el.addClass("fc-day-grid-container"), n = t('<div class="fc-day-grid" />').appendTo(e);
            this.el.find(".fc-body > tr > td").append(e), this.dayGrid.setElement(n), this.dayGrid.renderDates(this.hasRigidRows())
        },
        renderHead: function () {
            this.headContainerEl = this.el.find(".fc-head-container").html(this.dayGrid.renderHeadHtml()), this.headRowEl = this.headContainerEl.find(".fc-row")
        },
        unrenderDates: function () {
            this.dayGrid.unrenderDates(), this.dayGrid.removeElement(), this.scroller.destroy()
        },
        renderBusinessHours: function () {
            this.dayGrid.renderBusinessHours()
        },
        renderSkeletonHtml: function () {
            return '<table><thead class="fc-head"><tr><td class="fc-head-container ' + this.widgetHeaderClass + '"></td></tr></thead><tbody class="fc-body"><tr><td class="' + this.widgetContentClass + '"></td></tr></tbody></table>'
        },
        weekNumberStyleAttr: function () {
            return null !== this.weekNumberWidth ? 'style="width:' + this.weekNumberWidth + 'px"' : ""
        },
        hasRigidRows: function () {
            var t = this.opt("eventLimit");
            return t && "number" != typeof t
        },
        updateWidth: function () {
            this.weekNumbersVisible && (this.weekNumberWidth = c(this.el.find(".fc-week-number")))
        },
        setHeight: function (t, e) {
            var n, i, o = this.opt("eventLimit");
            this.scroller.clear(), s(this.headRowEl), this.dayGrid.removeSegPopover(), o && "number" == typeof o && this.dayGrid.limitRows(o), n = this.computeScrollerHeight(t), this.setGridHeight(n, e), o && "number" != typeof o && this.dayGrid.limitRows(o), e || (this.scroller.setHeight(n), i = this.scroller.getScrollbarWidths(), (i.left || i.right) && (r(this.headRowEl, i), n = this.computeScrollerHeight(t), this.scroller.setHeight(n)), this.scroller.lockOverflow(i))
        },
        computeScrollerHeight: function (t) {
            return t - d(this.el, this.scroller.el)
        },
        setGridHeight: function (t, e) {
            e ? u(this.dayGrid.rowEls) : l(this.dayGrid.rowEls, t, !0)
        },
        queryScroll: function () {
            return this.scroller.getScrollTop()
        },
        setScroll: function (t) {
            this.scroller.setScrollTop(t)
        },
        prepareHits: function () {
            this.dayGrid.prepareHits()
        },
        releaseHits: function () {
            this.dayGrid.releaseHits()
        },
        queryHit: function (t, e) {
            return this.dayGrid.queryHit(t, e)
        },
        getHitSpan: function (t) {
            return this.dayGrid.getHitSpan(t)
        },
        getHitEl: function (t) {
            return this.dayGrid.getHitEl(t)
        },
        renderEvents: function (t) {
            this.dayGrid.renderEvents(t), this.updateHeight()
        },
        getEventSegs: function () {
            return this.dayGrid.getEventSegs()
        },
        unrenderEvents: function () {
            this.dayGrid.unrenderEvents()
        },
        renderDrag: function (t, e) {
            return this.dayGrid.renderDrag(t, e)
        },
        unrenderDrag: function () {
            this.dayGrid.unrenderDrag()
        },
        renderSelection: function (t) {
            this.dayGrid.renderSelection(t)
        },
        unrenderSelection: function () {
            this.dayGrid.unrenderSelection()
        }
    }), Ie = {
        renderHeadIntroHtml: function () {
            var t = this.view;
            return t.weekNumbersVisible ? '<th class="fc-week-number ' + t.widgetHeaderClass + '" ' + t.weekNumberStyleAttr() + "><span>" + tt(t.opt("weekNumberTitle")) + "</span></th>" : ""
        }, renderNumberIntroHtml: function (t) {
            var e = this.view;
            return e.weekNumbersVisible ? '<td class="fc-week-number" ' + e.weekNumberStyleAttr() + "><span>" + this.getCellDate(t, 0).format("w") + "</span></td>" : ""
        }, renderBgIntroHtml: function () {
            var t = this.view;
            return t.weekNumbersVisible ? '<td class="fc-week-number ' + t.widgetContentClass + '" ' + t.weekNumberStyleAttr() + "></td>" : ""
        }, renderIntroHtml: function () {
            var t = this.view;
            return t.weekNumbersVisible ? '<td class="fc-week-number" ' + t.weekNumberStyleAttr() + "></td>" : ""
        }
    }, ke = jt.MonthView = xe.extend({
        computeRange: function (t) {
            var e, n = xe.prototype.computeRange.call(this, t);
            return this.isFixedWeeks() && (e = Math.ceil(n.end.diff(n.start, "weeks", !0)), n.end.add(6 - e, "weeks")), n
        }, setGridHeight: function (t, e) {
            e = e || "variable" === this.opt("weekMode"), e && (t *= this.rowCnt / 6), l(this.dayGrid.rowEls, t, !e)
        }, isFixedWeeks: function () {
            var t = this.opt("weekMode");
            return t ? "fixed" === t : this.opt("fixedWeekCount")
        }
    });
    Zt.basic = {"class": xe}, Zt.basicDay = {type: "basic", duration: {days: 1}}, Zt.basicWeek = {
        type: "basic",
        duration: {weeks: 1}
    }, Zt.month = {"class": ke, duration: {months: 1}, defaults: {fixedWeekCount: !0}};
    var Le = jt.AgendaView = we.extend({
        scroller: null,
        timeGridClass: ye,
        timeGrid: null,
        dayGridClass: me,
        dayGrid: null,
        axisWidth: null,
        headContainerEl: null,
        noScrollRowEls: null,
        bottomRuleEl: null,
        initialize: function () {
            this.timeGrid = this.instantiateTimeGrid(), this.opt("allDaySlot") && (this.dayGrid = this.instantiateDayGrid()), this.scroller = new Se({
                overflowX: "hidden",
                overflowY: "auto"
            })
        },
        instantiateTimeGrid: function () {
            var t = this.timeGridClass.extend(ze);
            return new t(this)
        },
        instantiateDayGrid: function () {
            var t = this.dayGridClass.extend(Me);
            return new t(this)
        },
        setRange: function (t) {
            we.prototype.setRange.call(this, t), this.timeGrid.setRange(t), this.dayGrid && this.dayGrid.setRange(t)
        },
        renderDates: function () {
            this.el.addClass("fc-agenda-view").html(this.renderSkeletonHtml()), this.renderHead(), this.scroller.render();
            var e = this.scroller.el.addClass("fc-time-grid-container"), n = t('<div class="fc-time-grid" />').appendTo(e);
            this.el.find(".fc-body > tr > td").append(e), this.timeGrid.setElement(n), this.timeGrid.renderDates(), this.bottomRuleEl = t('<hr class="fc-divider ' + this.widgetHeaderClass + '"/>').appendTo(this.timeGrid.el), this.dayGrid && (this.dayGrid.setElement(this.el.find(".fc-day-grid")), this.dayGrid.renderDates(), this.dayGrid.bottomCoordPadding = this.dayGrid.el.next("hr").outerHeight()), this.noScrollRowEls = this.el.find(".fc-row:not(.fc-scroller *)")
        },
        renderHead: function () {
            this.headContainerEl = this.el.find(".fc-head-container").html(this.timeGrid.renderHeadHtml())
        },
        unrenderDates: function () {
            this.timeGrid.unrenderDates(), this.timeGrid.removeElement(), this.dayGrid && (this.dayGrid.unrenderDates(), this.dayGrid.removeElement()), this.scroller.destroy()
        },
        renderSkeletonHtml: function () {
            return '<table><thead class="fc-head"><tr><td class="fc-head-container ' + this.widgetHeaderClass + '"></td></tr></thead><tbody class="fc-body"><tr><td class="' + this.widgetContentClass + '">' + (this.dayGrid ? '<div class="fc-day-grid"/><hr class="fc-divider ' + this.widgetHeaderClass + '"/>' : "") + "</td></tr></tbody></table>"
        },
        axisStyleAttr: function () {
            return null !== this.axisWidth ? 'style="width:' + this.axisWidth + 'px"' : ""
        },
        renderBusinessHours: function () {
            this.timeGrid.renderBusinessHours(), this.dayGrid && this.dayGrid.renderBusinessHours()
        },
        unrenderBusinessHours: function () {
            this.timeGrid.unrenderBusinessHours(), this.dayGrid && this.dayGrid.unrenderBusinessHours()
        },
        getNowIndicatorUnit: function () {
            return this.timeGrid.getNowIndicatorUnit()
        },
        renderNowIndicator: function (t) {
            this.timeGrid.renderNowIndicator(t)
        },
        unrenderNowIndicator: function () {
            this.timeGrid.unrenderNowIndicator()
        },
        updateSize: function (t) {
            this.timeGrid.updateSize(t), we.prototype.updateSize.call(this, t)
        },
        updateWidth: function () {
            this.axisWidth = c(this.el.find(".fc-axis"))
        },
        setHeight: function (t, e) {
            var n, i, o;
            this.bottomRuleEl.hide(), this.scroller.clear(), s(this.noScrollRowEls), this.dayGrid && (this.dayGrid.removeSegPopover(), n = this.opt("eventLimit"), n && "number" != typeof n && (n = Fe), n && this.dayGrid.limitRows(n)), e || (i = this.computeScrollerHeight(t), this.scroller.setHeight(i), o = this.scroller.getScrollbarWidths(), (o.left || o.right) && (r(this.noScrollRowEls, o), i = this.computeScrollerHeight(t), this.scroller.setHeight(i)), this.scroller.lockOverflow(o), this.timeGrid.getTotalSlatHeight() < i && this.bottomRuleEl.show())
        },
        computeScrollerHeight: function (t) {
            return t - d(this.el, this.scroller.el)
        },
        computeInitialScroll: function () {
            var t = e.duration(this.opt("scrollTime")), n = this.timeGrid.computeTimeTop(t);
            return n = Math.ceil(n), n && n++, n
        },
        queryScroll: function () {
            return this.scroller.getScrollTop()
        },
        setScroll: function (t) {
            this.scroller.setScrollTop(t)
        },
        prepareHits: function () {
            this.timeGrid.prepareHits(), this.dayGrid && this.dayGrid.prepareHits()
        },
        releaseHits: function () {
            this.timeGrid.releaseHits(), this.dayGrid && this.dayGrid.releaseHits()
        },
        queryHit: function (t, e) {
            var n = this.timeGrid.queryHit(t, e);
            return !n && this.dayGrid && (n = this.dayGrid.queryHit(t, e)), n
        },
        getHitSpan: function (t) {
            return t.component.getHitSpan(t)
        },
        getHitEl: function (t) {
            return t.component.getHitEl(t)
        },
        renderEvents: function (t) {
            var e, n, i = [], r = [], s = [];
            for (n = 0; n < t.length; n++)t[n].allDay ? i.push(t[n]) : r.push(t[n]);
            e = this.timeGrid.renderEvents(r), this.dayGrid && (s = this.dayGrid.renderEvents(i)), this.updateHeight()
        },
        getEventSegs: function () {
            return this.timeGrid.getEventSegs().concat(this.dayGrid ? this.dayGrid.getEventSegs() : [])
        },
        unrenderEvents: function () {
            this.timeGrid.unrenderEvents(), this.dayGrid && this.dayGrid.unrenderEvents()
        },
        renderDrag: function (t, e) {
            return t.start.hasTime() ? this.timeGrid.renderDrag(t, e) : this.dayGrid ? this.dayGrid.renderDrag(t, e) : void 0
        },
        unrenderDrag: function () {
            this.timeGrid.unrenderDrag(), this.dayGrid && this.dayGrid.unrenderDrag()
        },
        renderSelection: function (t) {
            t.start.hasTime() || t.end.hasTime() ? this.timeGrid.renderSelection(t) : this.dayGrid && this.dayGrid.renderSelection(t)
        },
        unrenderSelection: function () {
            this.timeGrid.unrenderSelection(), this.dayGrid && this.dayGrid.unrenderSelection()
        }
    }), ze = {
        renderHeadIntroHtml: function () {
            var t, e = this.view;
            return e.opt("weekNumbers") ? (t = this.start.format(e.opt("smallWeekFormat")), '<th class="fc-axis fc-week-number ' + e.widgetHeaderClass + '" ' + e.axisStyleAttr() + "><span>" + tt(t) + "</span></th>") : '<th class="fc-axis ' + e.widgetHeaderClass + '" ' + e.axisStyleAttr() + "></th>"
        }, renderBgIntroHtml: function () {
            var t = this.view;
            return '<td class="fc-axis ' + t.widgetContentClass + '" ' + t.axisStyleAttr() + "></td>"
        }, renderIntroHtml: function () {
            var t = this.view;
            return '<td class="fc-axis" ' + t.axisStyleAttr() + "></td>"
        }
    }, Me = {
        renderBgIntroHtml: function () {
            var t = this.view;
            return '<td class="fc-axis ' + t.widgetContentClass + '" ' + t.axisStyleAttr() + "><span>" + (t.opt("allDayHtml") || tt(t.opt("allDayText"))) + "</span></td>"
        }, renderIntroHtml: function () {
            var t = this.view;
            return '<td class="fc-axis" ' + t.axisStyleAttr() + "></td>"
        }
    }, Fe = 5, Ne = [{hours: 1}, {minutes: 30}, {minutes: 15}, {seconds: 30}, {seconds: 15}];
    return Zt.agenda = {
        "class": Le,
        defaults: {
            allDaySlot: !0,
            allDayText: "all-day",
            slotDuration: "00:30:00",
            minTime: "00:00:00",
            maxTime: "24:00:00",
            slotEventOverlap: !0
        }
    }, Zt.agendaDay = {type: "agenda", duration: {days: 1}}, Zt.agendaWeek = {type: "agenda", duration: {weeks: 1}}, jt
});