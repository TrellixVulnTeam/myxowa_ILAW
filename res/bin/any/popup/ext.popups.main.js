xowa_implement("ext.popups.main@1gcuz", function($, jQuery, require, module) {
function fixedEncodeURIComponent(str) {
  return encodeURIComponent(str).replace(/[!'()*]/g, function(c) {
    return '%' + c.charCodeAt(0).toString(16).toUpperCase();
  });
}
    !function(e) {
        var t = {};
        function n(r) {
            if (t[r])
                return t[r].exports;
            var i = t[r] = {
                i: r,
                l: !1,
                exports: {}
            };
            return e[r].call(i.exports, i, i.exports, n),
            i.l = !0,
            i.exports
        }
        n.m = e,
        n.c = t,
        n.d = function(e, t, r) {
            n.o(e, t) || Object.defineProperty(e, t, {
                enumerable: !0,
                get: r
            })
        }
        ,
        n.r = function(e) {
            "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, {
                value: "Module"
            }),
            Object.defineProperty(e, "__esModule", {
                value: !0
            })
        }
        ,
        n.t = function(e, t) {
            if (1 & t && (e = n(e)),
            8 & t)
                return e;
            if (4 & t && "object" == typeof e && e && e.__esModule)
                return e;
            var r = Object.create(null);
            if (n.r(r),
            Object.defineProperty(r, "default", {
                enumerable: !0,
                value: e
            }),
            2 & t && "string" != typeof e)
                for (var i in e)
                    n.d(r, i, function(t) {
                        return e[t]
                    }
                    .bind(null, i));
            return r
        }
        ,
        n.n = function(e) {
            var t = e && e.__esModule ? function() {
                return e.default
            }
            : function() {
                return e
            }
            ;
            return n.d(t, "a", t),
            t
        }
        ,
        n.o = function(e, t) {
            return Object.prototype.hasOwnProperty.call(e, t)
        }
        ,
        n.p = "",
        n(n.s = "./src/index.js")
    }({
        "./node_modules/@babel/runtime/helpers/defineProperty.js": function(e, t) {
            e.exports = function(e, t, n) {
                return t in e ? Object.defineProperty(e, t, {
                    value: n,
                    enumerable: !0,
                    configurable: !0,
                    writable: !0
                }) : e[t] = n,
                e
            }
            ,
            e.exports.__esModule = !0,
            e.exports.default = e.exports
        },
        "./node_modules/redux-thunk/dist/redux-thunk.min.js": function(e, t, n) {
            e.exports = function(e) {
                function t(r) {
                    if (n[r])
                        return n[r].exports;
                    var i = n[r] = {
                        exports: {},
                        id: r,
                        loaded: !1
                    };
                    return e[r].call(i.exports, i, i.exports, t),
                    i.loaded = !0,
                    i.exports
                }
                var n = {};
                return t.m = e,
                t.c = n,
                t.p = "",
                t(0)
            }([function(e, t, n) {
                e.exports = n(1)
            }
            , function(e, t) {
                "use strict";
                function n(e) {
                    return function(t) {
                        var n = t.dispatch
                          , r = t.getState;
                        return function(t) {
                            return function(i) {
                                return "function" == typeof i ? i(n, r, e) : t(i)
                            }
                        }
                    }
                }
                t.__esModule = !0;
                var r = n();
                r.withExtraArgument = n,
                t.default = r
            }
            ])
        },
        "./node_modules/redux/dist/redux.min.js": function(e, t, n) {
            (function(e, n) {
                !function(t) {
                    "use strict";
                    var r = function(e) {
                        var t, n = e.Symbol;
                        return "function" == typeof n ? n.observable ? t = n.observable : (t = n("observable"),
                        n.observable = t) : t = "@@observable",
                        t
                    }("undefined" != typeof self ? self : "undefined" != typeof window ? window : void 0 !== e ? e : n)
                      , i = function() {
                        return Math.random().toString(36).substring(7).split("").join(".")
                    }
                      , o = {
                        INIT: "@@redux/INIT" + i(),
                        REPLACE: "@@redux/REPLACE" + i(),
                        PROBE_UNKNOWN_ACTION: function() {
                            return "@@redux/PROBE_UNKNOWN_ACTION" + i()
                        }
                    };
                    function a(e, t) {
                        var n = t && t.type;
                        return "Given " + (n && 'action "' + n + '"' || "an action") + ', reducer "' + e + '" returned undefined. To ignore an action, you must explicitly return the previous state. If you want this reducer to hold no value, you can return null instead of undefined.'
                    }
                    function s(e, t) {
                        return function() {
                            return t(e.apply(this, arguments))
                        }
                    }
                    function u(e, t, n) {
                        return t in e ? Object.defineProperty(e, t, {
                            value: n,
                            enumerable: !0,
                            configurable: !0,
                            writable: !0
                        }) : e[t] = n,
                        e
                    }
                    function c() {
                        for (var e = arguments.length, t = Array(e), n = 0; e > n; n++)
                            t[n] = arguments[n];
                        return 0 === t.length ? function(e) {
                            return e
                        }
                        : 1 === t.length ? t[0] : t.reduce((function(e, t) {
                            return function() {
                                return e(t.apply(void 0, arguments))
                            }
                        }
                        ))
                    }
                    t.createStore = function e(t, n, i) {
                        var a;
                        if ("function" == typeof n && "function" == typeof i || "function" == typeof i && "function" == typeof arguments[3])
                            throw Error("It looks like you are passing several store enhancers to createStore(). This is not supported. Instead, compose them together to a single function");
                        if ("function" == typeof n && void 0 === i && (i = n,
                        n = void 0),
                        void 0 !== i) {
                            if ("function" != typeof i)
                                throw Error("Expected the enhancer to be a function.");
                            return i(e)(t, n)
                        }
                        if ("function" != typeof t)
                            throw Error("Expected the reducer to be a function.");
                        var s = t
                          , u = n
                          , c = []
                          , p = c
                          , l = !1;
                        function d() {
                            p === c && (p = c.slice())
                        }
                        function f() {
                            if (l)
                                throw Error("You may not call store.getState() while the reducer is executing. The reducer has already received the state as an argument. Pass it down from the top reducer instead of reading it from the store.");
                            return u
                        }
                        function w(e) {
                            if ("function" != typeof e)
                                throw Error("Expected the listener to be a function.");
                            if (l)
                                throw Error("You may not call store.subscribe() while the reducer is executing. If you would like to be notified after the store has been updated, subscribe from a component and invoke store.getState() in the callback to access the latest state. See https://redux.js.org/api-reference/store#subscribe(listener) for more details.");
                            var t = !0;
                            return d(),
                            p.push(e),
                            function() {
                                if (t) {
                                    if (l)
                                        throw Error("You may not unsubscribe from a store listener while the reducer is executing. See https://redux.js.org/api-reference/store#subscribe(listener) for more details.");
                                    t = !1,
                                    d();
                                    var n = p.indexOf(e);
                                    p.splice(n, 1)
                                }
                            }
                        }
                        function h(e) {
                            if (!function(e) {
                                if ("object" != typeof e || null === e)
                                    return !1;
                                for (var t = e; null !== Object.getPrototypeOf(t); )
                                    t = Object.getPrototypeOf(t);
                                return Object.getPrototypeOf(e) === t
                            }(e))
                                throw Error("Actions must be plain objects. Use custom middleware for async actions.");
                            if (void 0 === e.type)
                                throw Error('Actions may not have an undefined "type" property. Have you misspelled a constant?');
                            if (l)
                                throw Error("Reducers may not dispatch actions.");
                            try {
                                l = !0,
                                u = s(u, e)
                            } finally {
                                l = !1
                            }
                            for (var t = c = p, n = 0; t.length > n; n++)
                                (0,
                                t[n])();
                            return e
                        }
                        return h({
                            type: o.INIT
                        }),
                        (a = {
                            dispatch: h,
                            subscribe: w,
                            getState: f,
                            replaceReducer: function(e) {
                                if ("function" != typeof e)
                                    throw Error("Expected the nextReducer to be a function.");
                                s = e,
                                h({
                                    type: o.REPLACE
                                })
                            }
                        })[r] = function() {
                            var e, t = w;
                            return (e = {
                                subscribe: function(e) {
                                    if ("object" != typeof e || null === e)
                                        throw new TypeError("Expected the observer to be an object.");
                                    function n() {
                                        e.next && e.next(f())
                                    }
                                    return n(),
                                    {
                                        unsubscribe: t(n)
                                    }
                                }
                            })[r] = function() {
                                return this
                            }
                            ,
                            e
                        }
                        ,
                        a
                    }
                    ,
                    t.combineReducers = function(e) {
                        for (var t = Object.keys(e), n = {}, r = 0; t.length > r; r++) {
                            var i = t[r];
                            "function" == typeof e[i] && (n[i] = e[i])
                        }
                        var s, u = Object.keys(n);
                        try {
                            !function(e) {
                                Object.keys(e).forEach((function(t) {
                                    var n = e[t];
                                    if (void 0 === n(void 0, {
                                        type: o.INIT
                                    }))
                                        throw Error('Reducer "' + t + "\" returned undefined during initialization. If the state passed to the reducer is undefined, you must explicitly return the initial state. The initial state may not be undefined. If you don't want to set a value for this reducer, you can use null instead of undefined.");
                                    if (void 0 === n(void 0, {
                                        type: o.PROBE_UNKNOWN_ACTION()
                                    }))
                                        throw Error('Reducer "' + t + "\" returned undefined when probed with a random type. Don't try to handle " + o.INIT + ' or other actions in "redux/*" namespace. They are considered private. Instead, you must return the current state for any unknown actions, unless it is undefined, in which case you must return the initial state, regardless of the action type. The initial state may not be undefined, but can be null.')
                                }
                                ))
                            }(n)
                        } catch (e) {
                            s = e
                        }
                        return function(e, t) {
                            if (void 0 === e && (e = {}),
                            s)
                                throw s;
                            for (var r = !1, i = {}, o = 0; u.length > o; o++) {
                                var c = u[o]
                                  , p = e[c]
                                  , l = (0,
                                n[c])(p, t);
                                if (void 0 === l) {
                                    var d = a(c, t);
                                    throw Error(d)
                                }
                                i[c] = l,
                                r = r || l !== p
                            }
                            return r ? i : e
                        }
                    }
                    ,
                    t.bindActionCreators = function(e, t) {
                        if ("function" == typeof e)
                            return s(e, t);
                        if ("object" != typeof e || null === e)
                            throw Error("bindActionCreators expected an object or a function, instead received " + (null === e ? "null" : typeof e) + '. Did you write "import ActionCreators from" instead of "import * as ActionCreators from"?');
                        for (var n = Object.keys(e), r = {}, i = 0; n.length > i; i++) {
                            var o = n[i]
                              , a = e[o];
                            "function" == typeof a && (r[o] = s(a, t))
                        }
                        return r
                    }
                    ,
                    t.applyMiddleware = function() {
                        for (var e = arguments.length, t = Array(e), n = 0; e > n; n++)
                            t[n] = arguments[n];
                        return function(e) {
                            return function() {
                                var n = e.apply(void 0, arguments)
                                  , r = function() {
                                    throw Error("Dispatching while constructing your middleware is not allowed. Other middleware would not be applied to this dispatch.")
                                }
                                  , i = {
                                    getState: n.getState,
                                    dispatch: function() {
                                        return r.apply(void 0, arguments)
                                    }
                                }
                                  , o = t.map((function(e) {
                                    return e(i)
                                }
                                ));
                                return function(e) {
                                    for (var t = 1; arguments.length > t; t++) {
                                        var n = null != arguments[t] ? arguments[t] : {}
                                          , r = Object.keys(n);
                                        "function" == typeof Object.getOwnPropertySymbols && (r = r.concat(Object.getOwnPropertySymbols(n).filter((function(e) {
                                            return Object.getOwnPropertyDescriptor(n, e).enumerable
                                        }
                                        )))),
                                        r.forEach((function(t) {
                                            u(e, t, n[t])
                                        }
                                        ))
                                    }
                                    return e
                                }({}, n, {
                                    dispatch: r = c.apply(void 0, o)(n.dispatch)
                                })
                            }
                        }
                    }
                    ,
                    t.compose = c,
                    t.__DO_NOT_USE__ActionTypes = o,
                    Object.defineProperty(t, "__esModule", {
                        value: !0
                    })
                }(t)
            }
            ).call(this, n("./node_modules/webpack/buildin/global.js"), n("./node_modules/webpack/buildin/module.js")(e))
        },
        "./node_modules/webpack/buildin/global.js": function(e, t) {
            var n;
            n = function() {
                return this
            }();
            try {
                n = n || new Function("return this")()
            } catch (e) {
                "object" == typeof window && (n = window)
            }
            e.exports = n
        },
        "./node_modules/webpack/buildin/module.js": function(e, t) {
            e.exports = function(e) {
                return e.webpackPolyfill || (e.deprecate = function() {}
                ,
                e.paths = [],
                e.children || (e.children = []),
                Object.defineProperty(e, "loaded", {
                    enumerable: !0,
                    get: function() {
                        return e.l
                    }
                }),
                Object.defineProperty(e, "id", {
                    enumerable: !0,
                    get: function() {
                        return e.i
                    }
                }),
                e.webpackPolyfill = 1),
                e
            }
        },
        "./src/index.js": function(e, t, n) {
            "use strict";
            n.r(t);
            var r = {};
            n.r(r),
            n.d(r, "boot", (function() {
                return ve
            }
            )),
            n.d(r, "fetch", (function() {
                return be
            }
            )),
            n.d(r, "linkDwell", (function() {
                return Ee
            }
            )),
            n.d(r, "abandon", (function() {
                return ye
            }
            )),
            n.d(r, "linkClick", (function() {
                return Te
            }
            )),
            n.d(r, "previewDwell", (function() {
                return Pe
            }
            )),
            n.d(r, "previewShow", (function() {
                return xe
            }
            )),
            n.d(r, "pageviewLogged", (function() {
                return ke
            }
            )),
            n.d(r, "showSettings", (function() {
                return Se
            }
            )),
            n.d(r, "hideSettings", (function() {
                return _e
            }
            )),
            n.d(r, "saveSettings", (function() {
                return Re
            }
            )),
            n.d(r, "statsvLogged", (function() {
                return Ae
            }
            ));
            var i = n("./node_modules/@babel/runtime/helpers/defineProperty.js")
              , o = n.n(i)
              , a = n("./node_modules/redux/dist/redux.min.js")
              , s = n("./node_modules/redux-thunk/dist/redux-thunk.min.js")
              , u = n.n(s)
              , c = function() {
                var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : window.devicePixelRatio;
                return e ? e > 1.5 ? 2 : e > 1 ? 1.5 : 1 : 1
            }()
              , p = {
                BRACKETED_DEVICE_PIXEL_RATIO: c,
                THUMBNAIL_SIZE: 320 * Math.max(c, 1.5),
                EXTRACT_LENGTH: 525
            }
              , l = {
                TYPE_GENERIC: "generic",
                TYPE_PAGE: "page",
                TYPE_DISAMBIGUATION: "disambiguation",
                TYPE_REFERENCE: "reference"
            };
            function d(e, t, n, r, i, o, a, s) {
                var u = function(e) {
                    if (null == e || 0 === e.length)
                        return;
                    return e
                }(i);
                return {
                    title: e,
                    url: t,
                    languageCode: n,
                    languageDirection: r,
                    extract: u,
                    type: function(e, t) {
                        if (void 0 === t)
                            return l.TYPE_GENERIC;
                        switch (e) {
                        case l.TYPE_GENERIC:
                        case l.TYPE_DISAMBIGUATION:
                        case l.TYPE_PAGE:
                            return e;
                        default:
                            return l.TYPE_PAGE
                        }
                    }(o, u),
                    thumbnail: a,
                    pageId: s
                }
            }
            function f(e, t) {
                return d(e, t, "", "", [], "")
            }
            function w(e, t, n) {
                return function(e, t) {
                    return e.getNamespaceId() === t.get("wgNamespaceNumber") && e.getMainText() === t.get("wgTitle")
                }(n, t) ? n.getFragment() && t.get("wgPopupsReferencePreviews") && $(e).parent().hasClass("reference") ? l.TYPE_REFERENCE : null : l.TYPE_PAGE
            }
            function h(e, t) {
                var n = e;
                return void 0 === e || 0 === n.length ? [] : n = function(e, t) {
                    var n = []
                      , r = "<bi-".concat(Math.random(), ">")
                      , i = "<snip-".concat(Math.random(), ">");
                    t = t.replace(/\s+/g, " ").trim();
                    var o = mw.util.escapeRegExp(t)
                      , a = new RegExp("(^|\\s)(".concat(o, ")(|$)"),"i");
                    return (e = (e = (e = e.replace(/\s+/, " ")).replace(a, "$1".concat(i).concat(r, "$2").concat(i, "$3"))).split(i)).forEach((function(e) {
                        0 === e.indexOf(r) ? n.push($("<b>").text(e.slice(r.length))) : n.push(document.createTextNode(e))
                    }
                    )),
                    n
                }(n, t)
            }
            function m(e) {
                if (e.query && e.query.pages && e.query.pages.length)
                    return e.query.pages[0];
                throw new Error("API response `query.pages` is empty.")
            }
            function g(e) {
                var t = $.extend({}, e);
                return t.extract = h(e.extract, e.title),
                t
            }
            function v(e) {
                return d(e.title, e.canonicalurl, e.pagelanguagehtmlcode, e.pagelanguagedir, e.extract, e.type, e.thumbnail, e.pageid)
            }
            function b(e, t, n) {
                function r(n) {
                    var r = t.endpoint;
                    return e({
                        url: r + fixedEncodeURIComponent(n)+'?action=firstpara',
                        headers: {
                            Accept: 'application/json; charset=utf-8; profile="'.concat("https://www.mediawiki.org/wiki/Specs/Summary/1.2.0", '"'),
                            "Accept-Language": t.acceptLanguage
                        }
                    })
                }
                return {
                    fetch: r,
                    convertPageToModel: y,
                    fetchPreviewForTitle: function(e) {
                        var i = e.getPrefixedDb()
                          , o = r(i);
                        return o.then((function(e) {
                            return (e = e || {}).title = e.title || i,
                            e.extract = e.extract || "",
                            y(e, t.THUMBNAIL_SIZE, n)
                        }
                        )).catch((function(e, t, n) {
                            return $.Deferred().reject("http", {
                                xhr: e,
                                textStatus: t,
                                exception: n
                            })
                        }
                        )).promise({
                            abort: function() {
                                o.abort()
                            }
                        })
                    }
                }
            }
            function E(e, t, n) {
                var r = e.source.split("/")
                  , i = r[r.length - 1]
                  , o = function(e) {
                    return new RegExp(/\.(jpg|jpeg|png|gif)$/i).test(e)
                }(t.source) || void 0
                  , a = i.indexOf("px-");
                if (-1 === a)
                    return o && t;
                var s, u, c = i.slice(a + 3);
                return e.width > e.height ? (s = n,
                u = Math.floor(n / e.width * e.height)) : (s = Math.floor(n / e.height * e.width),
                u = n),
                s >= t.width && -1 === c.indexOf(".svg") ? o && t : (r[r.length - 1] = "".concat(s, "px-").concat(c),
                {
                    source: r.join("/"),
                    width: s,
                    height: u
                })
            }
            function y(e, t, n) {
                return d(e.title, new mw.Title(e.title).getUrl(), e.lang, e.dir, n(e), e.type, e.thumbnail ? E(e.thumbnail, e.originalimage, t) : void 0, e.pageid)
            }
            function T(e) {
                var t = e.extract_html;
                return 0 === t.length ? [] : $.parseHTML(t)
            }
            function P(e) {
                return h(e.extract, e.title)
            }
            function x(e) {
                var t = $.extend({}, p, {
                    acceptLanguage: e.get("wgPageContentLanguage")
                })
                  , n = $.extend({}, t, {
                    endpoint: e.get("wgPopupsRestGatewayEndpoint")
                });
                switch (e.get("wgPopupsGateway")) {
                case "mwApiPlain":
                    return function(e, t) {
                        function n(n) {
                            return e.get({
                                action: "query",
                                prop: "info|extracts|pageimages|revisions|info",
                                formatversion: 2,
                                redirects: !0,
                                exintro: mw.config.get("wgPopupsTextExtractsIntroOnly", !0),
                                exchars: t.EXTRACT_LENGTH,
                                explaintext: !0,
                                exsectionformat: "plain",
                                piprop: "thumbnail",
                                pithumbsize: t.THUMBNAIL_SIZE,
                                pilicense: "any",
                                rvprop: "timestamp",
                                inprop: "url",
                                titles: n,
                                smaxage: 300,
                                maxage: 300,
                                uselang: "content"
                            }, {
                                headers: {
                                    "X-Analytics": "preview=1",
                                    "Accept-Language": t.acceptLanguage
                                }
                            })
                        }
                        return {
                            fetch: n,
                            extractPageFromResponse: m,
                            convertPageToModel: v,
                            fetchPreviewForTitle: function(e) {
                                var t = n(e.getPrefixedDb());
                                return t.then((function(e) {
                                    return v(g(m(e)))
                                }
                                )).promise({
                                    abort: function() {
                                        t.abort()
                                    }
                                })
                            },
                            formatPlainTextExtract: g
                        }
                    }(new mw.Api, t);
                case "restbasePlain":
                    return b($.ajax, n, P);
                case "restbaseHTML":
                    return b($.ajax, n, T);
                default:
                    throw new Error("Unknown gateway")
                }
            }
            function k() {
                function e(e) {
                    var t = ["book", "journal", "news", "note", "web"]
                      , n = null;
                    return e.find("cite[class]").each((function(e, r) {
                        for (var i = r.className.split(/\s+/), o = i.length; o--; )
                            if (-1 !== t.indexOf(i[o]))
                                return n = i[o],
                                !1
                    }
                    )),
                    n
                }
                return {
                    fetchPreviewForTitle: function(t, n) {
                        var r = t.getFragment().replace(/ /g, "_")
                          , i = function(e) {
                            var t = "#".concat($.escapeSelector(e));
                            return $("".concat(t, " .mw-reference-text, ").concat(t, " .reference-text"))
                        }(r);
                        if (!i.length || !i.text().trim() && !i.children().length)
                            return $.Deferred().reject("Footnote not found or empty", {
                                textStatus: "abort",
                                xhr: {
                                    readyState: 0
                                }
                            }).promise({
                                abort: function() {}
                            });
                        var o = {
                            url: "#".concat(r),
                            extract: i.html(),
                            type: l.TYPE_REFERENCE,
                            referenceType: e(i),
                            sourceElementId: n.parentNode.id
                        };
                        return $.Deferred().resolve(o).promise({
                            abort: function() {}
                        })
                    }
                }
            }
            function S(e) {
                return mw.html.escape(e)
            }
            var _ = {};
            function R(e) {
                if (!_[e]) {
                    var t = document.createElement("div");
                    t.innerHTML = e,
                    _[e] = t.firstElementChild
                }
                return _[e].cloneNode(!0)
            }
            function A(e) {
                var t = S(e.heading)
                  , n = S(e.saveLabel)
                  , r = S(e.closeLabel)
                  , i = S(e.helpText)
                  , o = S(e.okLabel)
                  , a = function() {
                    var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : [];
                    return e.map((function(e) {
                        var t = e.id
                          , n = e.name
                          , r = e.description
                          , i = e.isChecked;
                        return {
                            id: S(t),
                            name: S(n),
                            description: r ? S(r) : "",
                            isChecked: i
                        }
                    }
                    ))
                }(e.choices);
                return $($.parseHTML("\n\t\t<section id='mwe-popups-settings'>\n\t\t\t<header>\n\t\t\t\t<div>\n\t\t\t\t\t<div class='mw-ui-icon mw-ui-icon-element mw-ui-icon-popups-close close'>".concat(r, "</div>\n\t\t\t\t</div>\n\t\t\t\t<h1>").concat(t, "</h1>\n\t\t\t\t<div>\n\t\t\t\t\t<button class='save mw-ui-button mw-ui-progressive'>").concat(n, "</button>\n\t\t\t\t\t<button class='okay mw-ui-button mw-ui-progressive' style='display:none;'>").concat(o, "</button>\n\t\t\t\t</div>\n\t\t\t</header>\n\t\t\t<main id='mwe-popups-settings-form'>\n\t\t\t\t<form>\n\t\t\t\t\t").concat(a.map((function(e) {
                    var t = e.id
                      , n = e.name
                      , r = e.description
                      , i = e.isChecked;
                    return '\n\t\t\t\t\t<p class="mw-ui-checkbox">\n\t\t\t\t\t\t<input\n\t\t\t\t\t\t\t'.concat(i ? "checked" : "", "\n\t\t\t\t\t\t\tvalue='").concat(t, "'\n\t\t\t\t\t\t\ttype='checkbox'\n\t\t\t\t\t\t\tid='mwe-popups-settings-").concat(t, "'>\n\t\t\t\t\t\t<label for='mwe-popups-settings-").concat(t, "'>\n\t\t\t\t\t\t\t<span>").concat(n, "</span>\n\t\t\t\t\t\t\t").concat(r, "\n\t\t\t\t\t\t</label>\n\t\t\t\t\t</p>")
                }
                )).join(""), "\n\t\t\t\t</form>\n\t\t\t</main>\n\t\t\t<div class='mwe-popups-settings-help' style='display:none;'>\n\t\t\t\t<div class=\"mw-ui-icon mw-ui-icon-element mw-ui-icon-footer\"></div>\n\t\t\t\t<p>").concat(i, "</p>\n\t\t\t</div>\n\t\t</section>\n\t").trim()))
            }
            function C(e) {
                var t, n;
                return function(r) {
                    return t || (t = function(e) {
                        var t = [{
                            id: l.TYPE_PAGE,
                            name: mw.msg("popups-settings-option-page"),
                            description: mw.msg("popups-settings-option-page-description")
                        }, {
                            id: l.TYPE_REFERENCE,
                            name: mw.msg("popups-settings-option-reference"),
                            description: mw.msg("popups-settings-option-reference-description")
                        }];
                        return e || t.splice(1, 1),
                        A({
                            heading: mw.msg("popups-settings-title"),
                            closeLabel: mw.msg("popups-settings-cancel"),
                            saveLabel: mw.msg("popups-settings-save"),
                            helpText: mw.msg("popups-settings-help"),
                            okLabel: mw.msg("popups-settings-help-ok"),
                            choices: t
                        })
                    }(e),
                    n = $("<div>").addClass("mwe-popups-overlay"),
                    t.find(".save").on("click", (function() {
                        var e = {};
                        t.find("input").each((function(t, n) {
                            e[n.value] = $(n).is(":checked")
                        }
                        )),
                        r.saveSettings(e)
                    }
                    )),
                    t.find(".close, .okay").on("click", r.hideSettings)),
                    {
                        appendTo: function(e) {
                            n.appendTo(e),
                            t.appendTo(n)
                        },
                        show: function() {
                            n.show()
                        },
                        hide: function() {
                            n.hide()
                        },
                        toggleHelp: function(e) {
                            !function(e, t) {
                                var n = $("#mwe-popups-settings");
                                t ? (n.find("main, .save, .close").hide(),
                                n.find(".mwe-popups-settings-help, .okay").show()) : (n.find("main, .save, .close").show(),
                                n.find(".mwe-popups-settings-help, .okay").hide())
                            }(0, e)
                        },
                        setEnabled: function(e) {
                            Object.keys(e).forEach((function(n) {
                                t.find("#mwe-popups-settings-" + n).prop("checked", e[n])
                            }
                            ))
                        }
                    }
                }
            }
            function O(e, t) {
                var n;
                e.subscribe((function() {
                    var r = e.getState();
                    n !== r && (t(n, r),
                    n = r)
                }
                ))
            }
            function I(e, t) {
                if (function(e) {
                    return e.hash && e.host === location.host && e.pathname === location.pathname && e.search === location.search
                }(e))
                    try {
                        return mw.Title.newFromText(t.get("wgPageName") + decodeURIComponent(e.hash))
                    } catch (e) {
                        return null
                    }
                return function(e, t) {
                    if (!e)
                        return null;
                    var n = mw.Title.newFromText(e);
                    return n && t.indexOf(n.namespace) >= 0 ? n : null
                }(function(e, t) {
                    var n;
                    try {
                        n = new mw.Uri(e)
                    } catch (e) {
                        return
                    }
                    if (n.host === location.hostname) {
                        var r, i = Object.keys(n.query).length;
                        if (i)
                            1 === i && "title"in n.query && (r = n.query.title);
                        else {
                            var o = mw.util.escapeRegExp(t.get("wgArticlePath")).replace("\\$1", "([^?#]+)")
                              , a = new RegExp(o).exec(n.path);
                            try {
                                r = a && decodeURIComponent(a[1])
                            } catch (e) {}
                        }
                        return r ? "".concat(r).concat(n.fragment ? "#".concat(n.fragment) : "") : void 0
                    }
                }(e.href, t), t.get("wgContentNamespaces"))
            }
            function N(e) {
                var t = $.Deferred();
                return setTimeout((function() {
                    return t.resolve()
                }
                ), e),
                t.promise()
            }
            var j = n("./src/ui/pointer-mask.svg")
              , D = n.n(j)
              , L = {
                h: 250,
                w: 203
            }
              , Y = {
                h: 200,
                w: 320
            };
            function H(e, t) {
                var n = p.BRACKETED_DEVICE_PIXEL_RATIO;
                if (!e)
                    return null;
                var r = e.width / n
                  , i = e.height / n
                  , o = e.height > e.width || r < Y.w;
                if (o && i < L.h && e.height < L.h || e.source.indexOf("\\") > -1 || e.source.indexOf("'") > -1 || e.source.indexOf('"') > -1)
                    return null;
                var a, s, u, c, l = r / i, d = l > .7 && l < 1.3;
                o ? (a = r > L.w ? (r - L.w) / -2 : L.w - r,
                s = i > L.h ? (i - L.h) / -2 : 0,
                u = L.w,
                c = L.h,
                r < u && (a = 0,
                u = r)) : (a = 0,
                s = i > Y.h ? (i - Y.h) / -2 : 0,
                u = Y.w,
                c = i > Y.h ? Y.h : i);
                var f, w, h = o && r < L.w;
                return {
                    el: t ? (f = e.source,
                    (w = document.createElement("img")).className = "mwe-popups-thumbnail",
                    w.src = f,
                    w) : function(e, t, n, r, i, o, a, s) {
                        var u = "http://www.w3.org/2000/svg"
                          , c = document.createElementNS(u, "polyline")
                          , p = -1 === e.indexOf("not-tall") ? [0, 0, 0, s] : [0, s - 1, a, s - 1];
                        c.setAttribute("stroke", "rgba(0,0,0,0.1)"),
                        c.setAttribute("points", p.join(" ")),
                        c.setAttribute("stroke-width", 1);
                        var l = $(document.createElementNS(u, "image"));
                        l[0].setAttributeNS("http://www.w3.org/1999/xlink", "href", t),
                        l.addClass(e).attr({
                            x: n,
                            y: r,
                            width: i,
                            height: o
                        });
                        var d = $(document.createElementNS(u, "svg")).attr({
                            xmlns: u,
                            width: a,
                            height: s
                        }).append(l);
                        return d.append(c),
                        d
                    }(o ? "mwe-popups-is-tall" : "mwe-popups-is-not-tall", e.source, a, s, r, i, u, c),
                    isTall: o || d,
                    isNarrow: h,
                    offset: h ? L.w - r : 0,
                    width: r,
                    height: i
                }
            }
            function G(e, t) {
                var n = R('\n\t<div class="mwe-popups" aria-hidden></div>\n');
                return n.className = "mwe-popups mwe-popups-type-".concat(e),
                t.className = "mwe-popups-container",
                n.appendChild(t),
                $(n)
            }
            function F(e, t, n, r) {
                var i = G(e.type, R('\n\t<div class="mwe-popups-container">\n\t\t<div class="mw-ui-icon mw-ui-icon-element"></div>\n\t\t<strong class="mwe-popups-title"></strong>\n\t\t<a class="mwe-popups-extract">\n\t\t\t<span class="mwe-popups-message"></span>\n\t\t</a>\n\t\t<footer>\n\t\t\t<a class="mwe-popups-read-link"></a>\n\t\t</footer>\n\t</div>\n'));
                return i.find(".mw-ui-icon ").addClass("mw-ui-icon-preview-".concat(e.type)),
                i.find(".mwe-popups-extract").attr("href", e.url),
                i.find(".mwe-popups-message").html(S(n)),
                i.find(".mwe-popups-read-link").html(S(r)).attr("href", e.url),
                t ? i.find(".mwe-popups-title").html(S(e.title)) : i.find(".mwe-popups-title").remove(),
                i
            }
            var M = "event.ReferencePreviewsPopups"
              , U = !1;
            function X(e) {
                var t = e.referenceType || "generic"
                  , n = mw.message("popups-refpreview-".concat(t));
                n.exists() || (n = mw.message("popups-refpreview-reference"));
                var r = G(e.type, R('\n<div class="mwe-popups-container">\n    <div class="mwe-popups-extract">\n        <div class="mwe-popups-scroll">\n            <strong class="mwe-popups-title">\n                <span class="mw-ui-icon mw-ui-icon-element"></span>\n                <span class="mwe-popups-title-placeholder"></span>\n            </strong>\n            <div class="mw-parser-output"></div>\n        </div>\n        <div class="mwe-popups-fade"></div>\n    </div>\n\t<footer>\n\t\t<div class="mwe-popups-settings"></div>\n\t</footer>\n</div>'));
                return r.find(".mwe-popups-title-placeholder").replaceWith(S(n.text())),
                r.find(".mwe-popups-title .mw-ui-icon").addClass("mw-ui-icon-reference-".concat(t)),
                r.find(".mw-parser-output").html(e.extract),
                r.find('.mwe-popups-extract a[href][class~="external"]:not([target])').each((function(e, t) {
                    t.target = "_blank",
                    t.rel = "".concat(t.rel ? "".concat(t.rel, " ") : "", "noopener")
                }
                )),
                r.find(".mw-collapsible").replaceWith($("<div>").addClass("mwe-collapsible-placeholder").append($("<span>").addClass("mw-ui-icon mw-ui-icon-element mw-ui-icon-infoFilled"), $("<div>").addClass("mwe-collapsible-placeholder-label").text(mw.msg("popups-refpreview-collapsible-placeholder")))),
                r.find("table.sortable").removeClass("sortable jquery-tablesorter").find(".headerSort").removeClass("headerSort").attr({
                    tabindex: null,
                    title: null
                }),
                mw.config.get("wgPopupsReferencePreviewsBetaFeature") ? r.find(".mwe-popups-container").addClass("footer-empty") : r.find(".mwe-popups-settings").append($("<a>").addClass("mwe-popups-settings-icon").append($("<span>").addClass("mw-ui-icon mw-ui-icon-element mw-ui-icon-small mw-ui-icon-settings"))),
                U && r.find(".mw-parser-output").on("click", "a", (function() {
                    mw.track(M, {
                        action: "clickedReferencePreviewsContentLink"
                    })
                }
                )),
                r.find(".mwe-popups-scroll").on("scroll", (function(e) {
                    var t = e.target
                      , n = t.scrollTop >= t.scrollHeight - t.clientHeight - 1;
                    if (U && (t.isOpenRecorded || (mw.track(M, {
                        action: "poppedOpen",
                        scrollbarsPresent: t.scrollHeight > t.clientHeight
                    }),
                    t.isOpenRecorded = !0),
                    t.scrollTop > 0 && !t.isScrollRecorded && (mw.track(M, {
                        action: "scrolled"
                    }),
                    t.isScrollRecorded = !0)),
                    n || !t.isScrolling) {
                        var r = $(t).parent()
                          , i = t.scrollWidth > t.clientWidth
                          , o = t.offsetHeight - t.clientHeight
                          , a = t.scrollHeight > t.clientHeight
                          , s = t.offsetWidth - t.clientWidth;
                        r.find(".mwe-popups-fade").css({
                            bottom: i ? "".concat(o, "px") : 0,
                            right: a ? "".concat(s, "px") : 0
                        }),
                        t.isScrolling = !n,
                        r.toggleClass("mwe-popups-fade-out", t.isScrolling)
                    }
                }
                )),
                r
            }
            $((function() {
                mw.config.get("wgPopupsReferencePreviews") && navigator.sendBeacon && mw.config.get("wgIsArticle") && !U && (U = !0,
                mw.track(M, {
                    action: "pageview"
                }))
            }
            ));
            function W(e, t, n, r) {
                var i = G(e.type, R('\n<div>\n    <a class="mwe-popups-discreet"></a>\n    <a class="mwe-popups-extract"></a>\n    <footer>\n        <a class="mwe-popups-settings-icon">\n            <span class="mw-ui-icon mw-ui-icon-element mw-ui-icon-small mw-ui-icon-settings"></span>\n        </a>\n    </footer>\n</div>\n\t'));
                i.find(".mwe-popups-discreet, .mwe-popups-extract").attr("href", e.url),
                i.find(".mwe-popups-extract").attr("dir", e.languageDirection).attr("lang", e.languageCode),
                i.find(".mwe-popups-settings-icon").attr("title", r),
                t ? i.find(".mwe-popups-discreet").append(t.el) : i.find(".mwe-popups-discreet").remove();
                var o = i.find(".mwe-popups-extract");
                if (e.extract) {
                    o.append(e.extract);
                    var a = function(e) {
                        return e && e.isNarrow ? "".concat(215 + e.offset, "px") : ""
                    }(t);
                    n || (o.css("width", a),
                    i.find("footer").css("width", a))
                }
                return i
            }
            function B() {
                var e;
                q() || (e = document.body,
                $("<div>").attr("id", "mwe-popups-svg").html(D.a).appendTo(e))
            }
            function V(e) {
                var t = function(e) {
                    switch (e.type) {
                    case l.TYPE_PAGE:
                        return function(e) {
                            var t = H(e.thumbnail, q())
                              , n = null !== t
                              , r = q()
                              , i = mw.msg("popups-settings-icon-gear-title");
                            return {
                                el: W(e, t, r, i),
                                hasThumbnail: n,
                                thumbnail: t,
                                isTall: n && t.isTall
                            }
                        }(e);
                    case l.TYPE_DISAMBIGUATION:
                        return function(e) {
                            var t = mw.msg("popups-preview-disambiguation")
                              , n = mw.msg("popups-preview-disambiguation-link");
                            return {
                                el: F(e, !0, t, n),
                                hasThumbnail: !1,
                                isTall: !1
                            }
                        }(e);
                    case l.TYPE_REFERENCE:
                        return function(e) {
                            return {
                                el: X(e),
                                hasThumbnail: !1,
                                isTall: !1
                            }
                        }(e);
                    default:
                        return function(e) {
                            var t = mw.msg("popups-preview-no-preview")
                              , n = mw.msg("popups-preview-footer-read");
                            return {
                                el: F(e, !1, t, n),
                                hasThumbnail: !1,
                                isTall: !1
                            }
                        }(e)
                    }
                }(e);
                return {
                    show: function(e, n, r) {
                        return function(e, t, n, r, i, o, a) {
                            var s = function(e, t, n, r) {
                                var i, o = !1, a = !1, s = t.pageY ? K(t.pageY - t.scrollTop, t.clientRects, !1) + t.scrollTop + n : t.offset.top + t.height + 8, u = t.clientY ? t.clientY : s;
                                i = t.pageX ? t.width > 28 ? t.pageX : t.offset.left + t.width / 2 : t.offset.left;
                                i > t.windowWidth / 2 && (i += t.pageX ? 0 : t.width,
                                i -= e ? 450 : 320,
                                o = !0);
                                t.pageX && (i += o ? 18 : -18);
                                u > t.windowHeight / 2 && (a = !0,
                                s = t.offset.top,
                                t.pageY && (s = K(t.pageY - t.scrollTop, t.clientRects, !0) + t.scrollTop),
                                s -= n);
                                return {
                                    offset: {
                                        top: s,
                                        left: i
                                    },
                                    flippedX: "rtl" === r ? !o : o,
                                    flippedY: a,
                                    dir: r
                                }
                            }(e.isTall, t, 8, a);
                            e.el.appendTo(o),
                            function(e, t, n, r, i, o) {
                                var a = e.el
                                  , s = e.isTall
                                  , u = e.hasThumbnail
                                  , c = e.thumbnail
                                  , p = t.flippedY;
                                !p && !s && u && c.height < r && !q() && a.find(".mwe-popups-extract").css("margin-top", c.height - i);
                                a.addClass(n),
                                a.css({
                                    left: "".concat(t.offset.left, "px"),
                                    top: p ? "auto" : t.offset.top,
                                    bottom: p ? "".concat(o - t.offset.top, "px") : "auto"
                                }),
                                u && !q() && function(e, t) {
                                    var n = e.el
                                      , r = e.isTall
                                      , i = e.thumbnail
                                      , o = t.flippedY
                                      , a = t.flippedX
                                      , s = t.dir
                                      , u = function(e, t, n) {
                                        if (!e && !t)
                                            return n ? "mwe-popups-mask-flip" : "mwe-popups-mask";
                                        if (e && n)
                                            return t ? "mwe-popups-landscape-mask-flip" : "mwe-popups-landscape-mask";
                                        return
                                    }(r, o, a);
                                    if (u) {
                                        var c = {
                                            scaleX: 1,
                                            translateX: r ? Math.min(i.width - L.w, 0) : 0
                                        };
                                        "rtl" === s && (c.scaleX = -1,
                                        c.translateX = r ? L.w : Y.w),
                                        document.getElementById(u).setAttribute("transform", "matrix(".concat(c.scaleX, " 0 0 1 ").concat(c.translateX, " 0)")),
                                        n.find("image")[0].setAttribute("clip-path", "url(#".concat(u, ")"))
                                    }
                                }(e, t)
                            }(e, s, function(e, t) {
                                var n = [];
                                t.flippedY ? n.push("mwe-popups-fade-in-down") : n.push("mwe-popups-fade-in-up");
                                t.flippedY && t.flippedX ? n.push("flipped-x-y") : t.flippedY ? n.push("flipped-y") : t.flippedX && n.push("flipped-x");
                                n.push(function(e, t) {
                                    if ((!e.hasThumbnail || e.isTall && !t.flippedX) && !t.flippedY)
                                        return !1;
                                    if (e.hasThumbnail && (!e.isTall && !t.flippedY || e.isTall && t.flippedX))
                                        return !0;
                                    return !1
                                }(e, t) ? "mwe-popups-image-pointer" : "mwe-popups-no-image-pointer"),
                                e.isTall ? n.push("mwe-popups-is-tall") : n.push("mwe-popups-is-not-tall");
                                return n
                            }(e, s), Y.h, 8, t.windowHeight),
                            e.el.show(),
                            e.el.hasClass("mwe-popups-type-reference") && e.el.find(".mwe-popups-scroll").first().trigger("scroll");
                            return N(200).then((function() {
                                !function(e, t) {
                                    e.el.on("mouseenter", t.previewDwell).on("mouseleave", t.previewAbandon),
                                    e.el.click(t.click),
                                    e.el.find(".mwe-popups-settings-icon").attr("href", t.settingsUrl).click((function(e) {
                                        e.stopPropagation(),
                                        t.showSettings(e)
                                    }
                                    ))
                                }(e, r),
                                r.previewShow(i)
                            }
                            ))
                        }(t, e, $(e.target), n, r, document.body, document.documentElement.getAttribute("dir"))
                    },
                    hide: function() {
                        return function(e) {
                            var t = e.el.hasClass("mwe-popups-fade-in-up") ? "mwe-popups-fade-in-up" : "mwe-popups-fade-in-down"
                              , n = "mwe-popups-fade-in-up" === t ? "mwe-popups-fade-out-down" : "mwe-popups-fade-out-up";
                            return e.el.removeClass(t).addClass(n),
                            N(150).then((function() {
                                e.el.remove()
                            }
                            ))
                        }(t)
                    }
                }
            }
            function q() {
                return window.CSS && "function" == typeof CSS.supports && CSS.supports("clip-path", "polygon(1px 1px)")
            }
            function K(e, t, n) {
                var r, i = null;
                return Array.prototype.slice.call(t).forEach((function(t) {
                    var o = Math.abs(e - t.top + e - t.bottom);
                    (null === i || i > o) && (i = o,
                    r = n ? Math.floor(t.top) : Math.ceil(t.bottom))
                }
                )),
                r
            }
            function z(e, t) {
                return t.split(".").reduce((function(e, t) {
                    return e && e[t]
                }
                ), e)
            }
            function Z(e, t, n, r) {
                var i = z(t, n);
                e && z(e, n) !== i && r(i)
            }
            var J = {
                footerLink: function(e) {
                    var t;
                    return function(n, r) {
                        void 0 === t && (t = function() {
                            var e = $("<li>").append($("<a>").attr("href", "#").text(mw.message("popups-settings-enable").text()));
                            e.hide();
                            var t = $("#footer-places, #f-list");
                            return 0 === t.length && (t = $("#footer li").parent()),
                            t.append(e),
                            e
                        }()).on("click", (function(t) {
                            t.preventDefault(),
                            e.showSettings()
                        }
                        )),
                        r.settings.shouldShowFooterLink ? t.show() : t.hide()
                    }
                },
                linkTitle: function() {
                    var e;
                    return function(t, n) {
                        var r, i = t && t.preview.activeLink;
                        i !== n.preview.activeLink && ((r = i) && e && ($(r).attr("title", e),
                        e = void 0),
                        n.preview.enabled[n.preview.previewType] && function(t) {
                            if (t && !e) {
                                var n = $(t);
                                e = n.attr("title"),
                                n.attr("title", "")
                            }
                        }(n.preview.activeLink))
                    }
                },
                pageviews: function(e, t) {
                    return function(n, r) {
                        var i, o;
                        r.pageviews && r.pageviews.pageview && r.pageviews.page && (i = r.pageviews.page,
                        o = r.pageviews.pageview,
                        t("event.VirtualPageView", {
                            source_page_id: i.id,
                            source_namespace: i.namespaceId,
                            source_title: mw.Title.newFromText(i.title).getPrefixedDb(),
                            source_url: i.url,
                            page_id: o.page_id,
                            page_namespace: o.page_namespace,
                            page_title: mw.Title.newFromText(o.page_title).getPrefixedDb()
                        }),
                        e.pageviewLogged())
                    }
                },
                render: function(e) {
                    var t;
                    return function(n, r) {
                        r.preview.shouldShow && !t ? (t = V(r.preview.fetchResponse)).show(r.preview.measures, e, r.preview.activeToken) : !r.preview.shouldShow && t && (t.hide(),
                        t = void 0)
                    }
                },
                settings: function(e, t) {
                    var n;
                    return function(r, i) {
                        r && (!1 === r.settings.shouldShow && i.settings.shouldShow ? (n || (n = t(e)).appendTo(document.body),
                        n.setEnabled(i.preview.enabled),
                        n.show()) : r.settings.shouldShow && !1 === i.settings.shouldShow && n.hide(),
                        r.settings.showHelp !== i.settings.showHelp && n.toggleHelp(i.settings.showHelp))
                    }
                },
                statsv: function(e, t) {
                    return function(n, r) {
                        var i = r.statsv;
                        i.action && (t(i.action, i.data),
                        e.statsvLogged())
                    }
                },
                syncUserSettings: function(e) {
                    return function(t, n) {
                        Z(t, n, "eventLogging.previewCount", e.storePreviewCount),
                        Z(t, n, "preview.enabled." + l.TYPE_PAGE, e.storePagePreviewsEnabled),
                        Z(t, n, "preview.enabled." + l.TYPE_REFERENCE, e.storeReferencePreviewsEnabled)
                    }
                }
            }
              , Q = "BOOT"
              , ee = "LINK_DWELL"
              , te = "ABANDON_START"
              , ne = "ABANDON_END"
              , re = "LINK_CLICK"
              , ie = "FETCH_START"
              , oe = "FETCH_END"
              , ae = "FETCH_COMPLETE"
              , se = "FETCH_FAILED"
              , ue = "FETCH_ABORTED"
              , ce = "PAGEVIEW_LOGGED"
              , pe = "PREVIEW_DWELL"
              , le = "PREVIEW_SHOW"
              , de = "PREVIEW_SEEN"
              , fe = "SETTINGS_SHOW"
              , we = "SETTINGS_HIDE"
              , he = "SETTINGS_CHANGE"
              , me = "STATSV_LOGGED";
            function ge(e) {
                return e.timestamp = mw.now(),
                e
            }
            function ve(e, t, n, r, i) {
                var o = r.get("wgUserEditCount");
                return {
                    type: Q,
                    initiallyEnabled: e,
                    isNavPopupsEnabled: r.get("wgPopupsConflictsWithNavPopupGadget"),
                    sessionToken: t.sessionId(),
                    pageToken: t.getPageviewToken(),
                    page: {
                        url: i,
                        title: r.get("wgTitle"),
                        namespaceId: r.get("wgNamespaceNumber"),
                        id: r.get("wgArticleId")
                    },
                    user: {
                        isAnon: t.isAnon(),
                        editCount: o
                    }
                }
            }
            function be(e, t, n, r, i) {
                var o = t.getPrefixedDb()
                  , a = t.namespace;
                return function(s) {
                    var u = e.fetchPreviewForTitle(t, n);
                    s(ge({
                        type: ie,
                        el: n,
                        title: o,
                        namespaceId: a,
                        promise: u
                    }));
                    var c = u.then((function(e) {
                        return s(ge({
                            type: oe,
                            el: n
                        })),
                        e
                    }
                    )).catch((function(e, t) {
                        var i = new Error(e)
                          , o = t && t.textStatus && "abort" === t.textStatus ? ue : se;
                        throw i.data = t,
                        s({
                            type: o,
                            el: n,
                            token: r
                        }),
                        i
                    }
                    ));
                    return $.when(c, N(function(e) {
                        switch (e) {
                        case l.TYPE_PAGE:
                            return 350;
                        case l.TYPE_REFERENCE:
                            return 150;
                        default:
                            return 0
                        }
                    }(i))).then((function(e) {
                        s({
                            type: ae,
                            el: n,
                            result: e,
                            token: r
                        })
                    }
                    )).catch((function(e) {
                        var i = e.data
                          , a = !0;
                        i && i.xhr && 0 === i.xhr.readyState && (a = !("error" === i.textStatus && "" === i.exception || "abort" === i.textStatus));
                        a && s({
                            type: ae,
                            el: n,
                            result: f(o, t.getUrl()),
                            token: r
                        })
                    }
                    ))
                }
            }
            function Ee(e, t, n, r, i, o) {
                var a = i()
                  , s = e.getPrefixedDb()
                  , u = e.namespace;
                return function(i, c) {
                    var p = N(150)
                      , l = ge({
                        type: ee,
                        el: t,
                        previewType: o,
                        measures: n,
                        token: a,
                        title: s,
                        namespaceId: u,
                        promise: p
                    });
                    function d() {
                        return c().preview.activeToken === a
                    }
                    return i(l),
                    d() ? p.then((function() {
                        if (c().preview.enabled[o] && d())
                            return i(be(r, e, t, a, o))
                    }
                    )) : $.Deferred().resolve().promise()
                }
            }
            function ye() {
                return function(e, t) {
                    var n = t().preview
                      , r = n.activeToken
                      , i = n.promise;
                    return r ? (e(ge({
                        type: te,
                        token: r
                    })),
                    "abort"in i && i.abort(),
                    N(300).then((function() {
                        e({
                            type: ne,
                            token: r
                        })
                    }
                    ))) : $.Deferred().resolve().promise()
                }
            }
            function Te(e) {
                return ge({
                    type: re,
                    el: e
                })
            }
            function Pe() {
                return {
                    type: pe
                }
            }
            function xe(e) {
                return function(t, n) {
                    return t(ge({
                        type: le,
                        token: e
                    })),
                    N(1e3).then((function() {
                        var r = n().preview
                          , i = r && r.fetchResponse
                          , o = r && r.activeToken
                          , a = i && [l.TYPE_PAGE, l.TYPE_DISAMBIGUATION].indexOf(i.type) > -1;
                        o && o === e && i && a && t({
                            type: de,
                            title: i.title,
                            pageId: i.pageId,
                            namespace: 0
                        })
                    }
                    ))
                }
            }
            function ke() {
                return {
                    type: ce
                }
            }
            function Se() {
                return {
                    type: fe
                }
            }
            function _e() {
                return {
                    type: we
                }
            }
            function Re(e) {
                return function(t, n) {
                    t({
                        type: he,
                        oldValue: n().preview.enabled,
                        newValue: e
                    })
                }
            }
            function Ae() {
                return {
                    type: me
                }
            }
            function Ce(e, t) {
                var n, r = Object.prototype.hasOwnProperty, i = {};
                for (var o in e)
                    r.call(e, o) && !r.call(t, o) && (i[o] = e[o]);
                for (var a in t)
                    if (r.call(t, a))
                        if ((n = t[a]) && n.constructor === Object) {
                            var s = e[a] ? Ce({}, e[a]) : {};
                            i[a] = Ce(s, t[a])
                        } else
                            i[a] = t[a];
                return i
            }
            var Oe = {
                pageviews: function(e, t) {
                    switch (void 0 === e && (e = {
                        pageview: void 0
                    }),
                    t.type) {
                    case Q:
                        return Ce(e, {
                            page: t.page
                        });
                    case ce:
                        return Ce(e, {
                            pageview: void 0
                        });
                    case de:
                        return Ce(e, {
                            pageview: {
                                page_title: t.title,
                                page_id: t.pageId,
                                page_namespace: t.namespace
                            }
                        });
                    default:
                        return e
                    }
                },
                preview: function(e, t) {
                    switch (void 0 === e && (e = {
                        enabled: {},
                        activeLink: void 0,
                        previewType: void 0,
                        measures: void 0,
                        activeToken: "",
                        shouldShow: !1,
                        isUserDwelling: !1,
                        wasClicked: !1
                    }),
                    t.type) {
                    case Q:
                        return Ce(e, {
                            enabled: t.initiallyEnabled
                        });
                    case he:
                        return Ce(e, {
                            enabled: t.newValue
                        });
                    case ee:
                        return t.el !== e.activeLink ? Ce(e, {
                            activeLink: t.el,
                            previewType: t.previewType,
                            measures: t.measures,
                            activeToken: t.token,
                            shouldShow: !1,
                            isUserDwelling: !0,
                            promise: t.promise
                        }) : Ce(e, {
                            isUserDwelling: !0
                        });
                    case ue:
                    case ne:
                        return t.token !== e.activeToken || e.isUserDwelling ? e : Ce(e, {
                            activeLink: void 0,
                            previewType: void 0,
                            activeToken: void 0,
                            measures: void 0,
                            fetchResponse: void 0,
                            shouldShow: !1
                        });
                    case pe:
                        return Ce(e, {
                            isUserDwelling: !0
                        });
                    case te:
                        return Ce(e, {
                            isUserDwelling: !1,
                            wasClicked: !1
                        });
                    case ie:
                        return Ce(e, {
                            fetchResponse: void 0,
                            promise: t.promise
                        });
                    case ae:
                        if (t.token === e.activeToken)
                            return Ce(e, {
                                fetchResponse: t.result,
                                shouldShow: e.isUserDwelling
                            });
                    default:
                        return e
                    }
                },
                settings: function(e, t) {
                    switch (void 0 === e && (e = {
                        shouldShow: !1,
                        showHelp: !1,
                        shouldShowFooterLink: !1
                    }),
                    t.type) {
                    case fe:
                        return Ce(e, {
                            shouldShow: !0,
                            showHelp: !1
                        });
                    case we:
                        return Ce(e, {
                            shouldShow: !1,
                            showHelp: !1
                        });
                    case he:
                        var n = Object.keys(t.newValue)
                          , r = n.every((function(e) {
                            return t.oldValue[e] === t.newValue[e]
                        }
                        ))
                          , i = n.some((function(e) {
                            return t.oldValue[e] && !t.newValue[e]
                        }
                        ))
                          , o = n.some((function(e) {
                            return !1 === t.newValue[e]
                        }
                        ));
                        return Ce(e, r ? {
                            shouldShow: !1
                        } : {
                            shouldShow: i,
                            showHelp: i,
                            shouldShowFooterLink: o
                        });
                    case Q:
                        var a = Object.keys(t.initiallyEnabled).some((function(e) {
                            return !1 === t.initiallyEnabled[e]
                        }
                        ));
                        return Ce(e, {
                            shouldShowFooterLink: t.user.isAnon && a
                        });
                    default:
                        return e
                    }
                },
                statsv: function(e, t) {
                    switch (e = e || {},
                    t.type) {
                    case ie:
                        return Ce(e, {
                            fetchStartedAt: t.timestamp
                        });
                    case oe:
                        return Ce(e, {
                            action: "timing.PagePreviewsApiResponse",
                            data: t.timestamp - e.fetchStartedAt
                        });
                    case se:
                        return Ce(e, {
                            action: "counter.PagePreviewsApiFailure",
                            data: 1
                        });
                    case ee:
                        return Ce(e, {
                            linkDwellStartedAt: t.timestamp
                        });
                    case le:
                        return Ce(e, {
                            action: "timing.PagePreviewsPreviewShow",
                            data: t.timestamp - e.linkDwellStartedAt
                        });
                    case me:
                        return Ce(e, {
                            action: null,
                            data: null
                        });
                    default:
                        return e
                    }
                }
            };
            var Ie = [".extiw", ".image", ".new", ".internal", ".external", ".mw-cite-backlink a", ".oo-ui-buttonedElement-button", ".ve-ce-surface a", ".cancelLink a"];
            !function() {
                var e, t, n;
                t = mw.config,
                n = parseInt(t.get("wgPopupsFlags"), 10);
                //,
//                t.set("wgPopupsConflictsWithNavPopupGadget", !!(1 & n)),
//                t.set("wgPopupsConflictsWithRefTooltipsGadget", !!(2 & n)),
//                t.set("wgPopupsReferencePreviews", !!(4 & n)),
//                t.set("wgPopupsReferencePreviewsBetaFeature", !!(8 & n));
                var i, s, c = a.compose, p = mw.user.generateRandomSessionId, d = x(mw.config), f = k(), h = (s = mw.storage,
                {
                    isPagePreviewsEnabled: function() {
                        return "0" !== s.get("mwe-popups-enabled")
                    },
                    storePagePreviewsEnabled: function(e) {
                        e ? s.remove("mwe-popups-enabled") : s.set("mwe-popups-enabled", "0")
                    },
                    isReferencePreviewsEnabled: function() {
                        return "0" !== s.get("mwe-popups-referencePreviews-enabled")
                    },
                    storeReferencePreviewsEnabled: function(e) {
                        e ? s.remove("mwe-popups-referencePreviews-enabled") : s.set("mwe-popups-referencePreviews-enabled", "0"),
                        mw.track("event.ReferencePreviewsPopups", {
                            action: e ? "anonymousEnabled" : "anonymousDisabled"
                        })
                    }
                }), m = function(e, t, n) {
                    return n.get("wgPopupsReferencePreviews") ? n.get("wgPopupsConflictsWithRefTooltipsGadget") || "minerva" === n.get("skin") ? null : e.isAnon() ? t.isReferencePreviewsEnabled() : !!n.get("wgPopupsReferencePreviews") || ("1" === mw.user.options.get("popups-reference-previews") || null) : null
                }(mw.user, h, mw.config), g = C(null !== m), v = (i = mw.experiments,
                {
                    weightedBoolean: function(e, t, n) {
                        return "true" === i.getBucket({
                            enabled: !0,
                            name: e,
                            buckets: {
                                true: t,
                                false: 1 - t
                            }
                        }, n)
                    }
                }), b = function(e, t, n) {
                    return function(e, t, n) {
                        var r = t.get("wgPopupsStatsvSamplingRate", 0);
                        return n.weightedBoolean("ext.Popups.statsv", r, e.sessionId())
                    }(e, t, n) ? mw.track : function() {}
                }(mw.user, mw.config, v), E = function(e) {
                    return e.get("wgPopupsVirtualPageViews") ? mw.track : function() {}
                }(mw.config), y = (e = {},
                o()(e, l.TYPE_PAGE, function(e, t, n) {
                    return n.get("wgPopupsConflictsWithNavPopupGadget") ? null : e.isAnon() ? t.isPagePreviewsEnabled() : "1" === mw.user.options.get("popups") || null
                }(mw.user, h, mw.config)),
                o()(e, l.TYPE_REFERENCE, m),
                e);
                mw.config.get("debug") && (c = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || c);
                var T = a.createStore(a.combineReducers(Oe), c(a.applyMiddleware(u.a)))
                  , P = a.bindActionCreators(r, T.dispatch)
                  , S = function(e, t) {
                    var n, r = function() {};
                    if (e.isAnon())
                        r = function(e) {
                            e.preventDefault(),
                            t.showSettings()
                        }
                        ;
                    else {
                        n = mw.Title.newFromText("Special:Preferences#mw-prefsection-rendering").getUrl()
                    }
                    return {
                        settingsUrl: n,
                        showSettings: r,
                        previewDwell: t.previewDwell,
                        previewAbandon: t.abandon,
                        previewShow: t.previewShow,
                        click: t.linkClick
                    }
                }(mw.user, P);
                !function(e, t, n, r, i, o, a) {
                    O(e, J.footerLink(t)),
                    O(e, J.linkTitle()),
                    O(e, J.render(i)),
                    O(e, J.statsv(t, o)),
                    O(e, J.syncUserSettings(n)),
                    O(e, J.settings(t, r)),
                    O(e, J.pageviews(t, a))
                }(T, P, h, g, S, b, E),
                P.boot(y, mw.user, h, mw.config, window.location.href),
                mw.popups = function(e) {
                    return {
                        isEnabled: function() {
                            return e.getState().preview.enabled[l.TYPE_PAGE]
                        }
                    }
                }(T);
                var _ = [];
                if (null !== y[l.TYPE_PAGE]) {
                    var R = Ie.join(", ");
                    _.push("#mw-content-text a[href][title]:not(".concat(R, ")"))
                }
                if (null !== y[l.TYPE_REFERENCE] && _.push('#mw-content-text .reference a[ href*="#" ]'),
                _.length) {
                    var A = _.join(", ");
                    B(),
                    $(document).on("mouseover keyup", A, (function(e) {
                        var t = I(this, mw.config);
                        if (t) {
                            var n, r = w(this, mw.config, t);
                            switch (r) {
                            case l.TYPE_PAGE:
                                n = d;
                                break;
                            case l.TYPE_REFERENCE:
                                n = f;
                                break;
                            default:
                                return
                            }
                            var i = $(this)
                              , o = $(window)
                              , a = {
                                pageX: e.pageX,
                                pageY: e.pageY,
                                clientY: e.clientY,
                                width: i.width(),
                                height: i.height(),
                                offset: i.offset(),
                                clientRects: this.getClientRects(),
                                windowWidth: o.width(),
                                windowHeight: o.height(),
                                scrollTop: o.scrollTop()
                            };
                            P.linkDwell(t, this, a, n, p, r)
                        }
                    }
                    )).on("mouseout blur", A, (function() {
                        I(this, mw.config) && P.abandon()
                    }
                    )).on("click", A, (function() {
                        var e = I(this, mw.config);
                        e && l.TYPE_PAGE === w(this, mw.config, e) && P.linkClick(this)
                    }
                    ))
                } else
                    mw.log.warn("ext.popups was loaded but everything is disabled")
            }(),
            window.Redux = a,
            window.ReduxThunk = s
        },
        "./src/ui/pointer-mask.svg": function(e, t) {
            e.exports = '<svg xmlns="http://www.w3.org/2000/svg" width="0" height="0"><defs><clipPath id="mwe-popups-mask"><path d="M0 8h10l8-8 8 8h974v992H0z"></path></clipPath><clipPath id="mwe-popups-mask-flip"><path d="M0 8h294l8-8 8 8h690v992H0z"></path></clipPath><clipPath id="mwe-popups-landscape-mask"><path d="M0 8h174l8-8 8 8h810v992H0z"></path></clipPath><clipPath id="mwe-popups-landscape-mask-flip"><path d="M0 0h1000v242H190l-8 8-8-8H0z"></path></clipPath></defs></svg>'
        }
    });
}, {
    "css": ["@-webkit-keyframes mwe-popups-fade-in-up{0%{opacity:0;-webkit-transform:translate(0,20px);-ms-transform:translate(0,20px);transform:translate(0,20px)}100%{opacity:1;-webkit-transform:translate(0,0);-ms-transform:translate(0,0);transform:translate(0,0)}}@keyframes mwe-popups-fade-in-up{0%{opacity:0;-webkit-transform:translate(0,20px);-ms-transform:translate(0,20px);transform:translate(0,20px)}100%{opacity:1;-webkit-transform:translate(0,0);-ms-transform:translate(0,0);transform:translate(0,0)}}@-webkit-keyframes mwe-popups-fade-in-down{0%{opacity:0;-webkit-transform:translate(0,-20px);-ms-transform:translate(0,-20px);transform:translate(0,-20px)}100%{opacity:1;-webkit-transform:translate(0,0);-ms-transform:translate(0,0);transform:translate(0,0)}}@keyframes mwe-popups-fade-in-down{0%{opacity:0;-webkit-transform:translate(0,-20px);-ms-transform:translate(0,-20px);transform:translate(0,-20px)}100%{opacity:1;-webkit-transform:translate(0,0);-ms-transform:translate(0,0);transform:translate(0,0)}}@-webkit-keyframes mwe-popups-fade-out-down{0%{opacity:1;-webkit-transform:translate(0,0);-ms-transform:translate(0,0);transform:translate(0,0)}100%{opacity:0;-webkit-transform:translate(0,20px);-ms-transform:translate(0,20px);transform:translate(0,20px)}}@keyframes mwe-popups-fade-out-down{0%{opacity:1;-webkit-transform:translate(0,0);-ms-transform:translate(0,0);transform:translate(0,0)}100%{opacity:0;-webkit-transform:translate(0,20px);-ms-transform:translate(0,20px);transform:translate(0,20px)}}@-webkit-keyframes mwe-popups-fade-out-up{0%{opacity:1;-webkit-transform:translate(0,0);-ms-transform:translate(0,0);transform:translate(0,0)}100%{opacity:0;-webkit-transform:translate(0,-20px);-ms-transform:translate(0,-20px);transform:translate(0,-20px)}}@keyframes mwe-popups-fade-out-up{0%{opacity:1;-webkit-transform:translate(0,0);-ms-transform:translate(0,0);transform:translate(0,0)}100%{opacity:0;-webkit-transform:translate(0,-20px);-ms-transform:translate(0,-20px);transform:translate(0,-20px)}}.mwe-popups-fade-in-up{-webkit-animation:mwe-popups-fade-in-up 0.2s ease forwards;animation:mwe-popups-fade-in-up 0.2s ease forwards}.mwe-popups-fade-in-down{-webkit-animation:mwe-popups-fade-in-down 0.2s ease forwards;animation:mwe-popups-fade-in-down 0.2s ease forwards}.mwe-popups-fade-out-down{-webkit-animation:mwe-popups-fade-out-down 0.2s ease forwards;animation:mwe-popups-fade-out-down 0.2s ease forwards}.mwe-popups-fade-out-up{-webkit-animation:mwe-popups-fade-out-up 0.2s ease forwards;animation:mwe-popups-fade-out-up 0.2s ease forwards}  #mwe-popups-settings{z-index:1000;background:#fff;width:420px;border:1px solid #a2a9b1;box-shadow:0 2px 2px 0 rgba(0,0,0,0.25);border-radius:2px;font-size:14px}#mwe-popups-settings header{box-sizing:border-box;border-bottom:1px solid #c8ccd1;position:relative;display:table;width:100%;padding:5px 7px 5px 0}#mwe-popups-settings header > div{display:table-cell;width:3.5em;vertical-align:middle;cursor:pointer}#mwe-popups-settings header h1{margin-bottom:0.6em;padding-top:0.5em;border:0;width:100%;font-family:sans-serif;font-size:18px;font-weight:bold;text-align:center}#mwe-popups-settings .mwe-ui-icon-popups-close{opacity:0.87;transition:opacity 100ms}#mwe-popups-settings .mwe-ui-icon-popups-close:hover{opacity:0.73}#mwe-popups-settings .mwe-ui-icon-popups-close:active{opacity:1}#mwe-popups-settings main#mwe-popups-settings-form{display:block;width:350px;padding:32px 0 24px;margin:0 auto}#mwe-popups-settings main#mwe-popups-settings-form p{color:#54595d;font-size:14px;margin:16px 0 0}#mwe-popups-settings main#mwe-popups-settings-form p:first-child{margin-top:0}#mwe-popups-settings main#mwe-popups-settings-form form img{margin-right:60px}#mwe-popups-settings main#mwe-popups-settings-form form input{margin-right:10px}#mwe-popups-settings main#mwe-popups-settings-form form label{font-size:13px;line-height:16px;width:300px}#mwe-popups-settings main#mwe-popups-settings-form form label > span{color:#000;font-size:14px;font-weight:bold;display:block;margin-bottom:5px}#mwe-popups-settings main#mwe-popups-settings-form form label:before{top:0.78125em !important}.mwe-popups-settings-help{font-size:13px;font-weight:800;margin:40px;position:relative}.mwe-popups-settings-help .mw-ui-icon:before,.mwe-popups-settings-help .mw-ui-icon{background-size:contain;height:140px;width:180px;max-width:none;margin:0;padding:0}.mwe-popups-settings-help p{left:180px;bottom:20px;position:absolute}.mwe-popups{background:#fff;position:absolute;z-index:110;box-shadow:0 30px 90px -20px rgba(0,0,0,0.3),0 0 1px 1px rgba(0,0,0,0.05);padding:0;display:none;font-size:14px;line-height:20px;min-width:300px;border-radius:2px; }.mwe-popups .mw-ui-icon-preview-disambiguation,.mwe-popups .mw-ui-icon-preview-generic{opacity:0.25}.mwe-popups .mwe-popups-container{color:#202122;margin-top:-8px;padding-top:9px;text-decoration:none}.mwe-popups .mwe-popups-container footer{padding:0 16px 16px;margin:0;position:absolute;bottom:0;left:0;pointer-events:none}.mwe-popups .mwe-popups-container footer a{pointer-events:auto}.mwe-popups .mwe-popups-settings-icon{display:block;float:right;border-radius:2px;opacity:0.67;transition:background-color 100ms,opacity 100ms}.mwe-popups .mwe-popups-settings-icon:hover{background-color:#eaecf0}.mwe-popups .mwe-popups-settings-icon:active{background-color:#c8ccd1;opacity:1}.mwe-popups .mwe-popups-extract{margin:16px;display:block;color:#202122;text-decoration:none;position:relative;   }.mwe-popups .mwe-popups-extract:hover{text-decoration:none}.mwe-popups .mwe-popups-extract:after,.mwe-popups .mwe-popups-extract blockquote:after{content:' ';position:absolute;bottom:0;width:25%;height:20px;background-color:transparent;pointer-events:none}.mwe-popups .mwe-popups-extract[dir='ltr']:after{ right:0; background-image:-webkit-linear-gradient(to right,rgba(255,255,255,0),#ffffff 50%); background-image:linear-gradient(to right,rgba(255,255,255,0),#ffffff 50%)}.mwe-popups .mwe-popups-extract[dir='rtl']:after{ left:0; background-image:-webkit-linear-gradient(to left,rgba(255,255,255,0),#ffffff 50%); background-image:linear-gradient(to left,rgba(255,255,255,0),#ffffff 50%)}.mwe-popups .mwe-popups-extract blockquote:after{width:100%;height:25px; bottom:0; background-image:-webkit-linear-gradient(to bottom,rgba(255,255,255,0),#ffffff 75%); background-image:linear-gradient(to bottom,rgba(255,255,255,0),#ffffff 75%)}.mwe-popups .mwe-popups-extract p{margin:0}.mwe-popups .mwe-popups-extract ul,.mwe-popups .mwe-popups-extract ol,.mwe-popups .mwe-popups-extract li,.mwe-popups .mwe-popups-extract dl,.mwe-popups .mwe-popups-extract dd,.mwe-popups .mwe-popups-extract dt{margin-top:0;margin-bottom:0}.mwe-popups svg{overflow:hidden}.mwe-popups.mwe-popups-is-tall{width:450px}.mwe-popups.mwe-popups-is-tall > div > a > svg{vertical-align:middle}.mwe-popups.mwe-popups-is-tall .mwe-popups-extract{width:215px;height:176px;overflow:hidden;float:left}.mwe-popups.mwe-popups-is-tall .mwe-popups-extract + footer{left:0;right:203px}.rtl .mwe-popups.mwe-popups-is-tall .mwe-popups-extract + footer{ right:-12px;width:215px}.mwe-popups.mwe-popups-is-not-tall{width:320px}.mwe-popups.mwe-popups-is-not-tall .mwe-popups-extract{min-height:58px;max-height:136px;overflow:hidden;margin-bottom:58px;padding-bottom:0}.mwe-popups.mwe-popups-is-not-tall footer{width:288px}.mwe-popups .mwe-popups-container.footer-empty .mwe-popups-extract{margin-bottom:16px}.mwe-popups .mwe-popups-container.footer-empty .mwe-popups-extract .mwe-popups-scroll{max-height:379px}.mwe-popups.flipped-y .mwe-popups-container.footer-empty .mwe-popups-extract,.mwe-popups.flipped-x-y .mwe-popups-container.footer-empty .mwe-popups-extract{margin-bottom:24px}.mwe-popups.mwe-popups-type-generic .mwe-popups-extract,.mwe-popups.mwe-popups-type-disambiguation .mwe-popups-extract{min-height:auto;padding-top:4px;margin-top:0;margin-bottom:var(--margin-bottom);--margin-bottom:60px}.mwe-popups.mwe-popups-type-generic .mwe-popups-read-link,.mwe-popups.mwe-popups-type-disambiguation .mwe-popups-read-link{font-weight:bold;font-size:12px}.mwe-popups.mwe-popups-type-generic .mwe-popups-extract:hover + footer .mwe-popups-read-link,.mwe-popups.mwe-popups-type-disambiguation .mwe-popups-extract:hover + footer .mwe-popups-read-link{text-decoration:underline}.mwe-popups.mwe-popups-no-image-pointer:before{content:'';position:absolute;border:8px solid transparent;border-top:0;border-bottom:8px solid rgba(0,0,0,0.07000000000000001);top:-8px;left:10px}.mwe-popups.mwe-popups-no-image-pointer:after{content:'';position:absolute;border:11px solid transparent;border-top:0;border-bottom:11px solid #ffffff;top:-7px;left:7px}.mwe-popups.flipped-x.mwe-popups-no-image-pointer:before{left:auto;right:10px}.mwe-popups.flipped-x.mwe-popups-no-image-pointer:after{left:auto;right:7px}.mwe-popups.mwe-popups-image-pointer:before{content:'';position:absolute;border:9px solid transparent;border-top:0;border-bottom:9px solid #a2a9b1;top:-9px;left:9px;z-index:111}.mwe-popups.mwe-popups-image-pointer:after{content:'';position:absolute;border:12px solid transparent;border-top:0;border-bottom:12px solid #ffffff;top:-8px;left:6px;z-index:112}.mwe-popups.mwe-popups-image-pointer.flipped-x:before{content:'';position:absolute;border:9px solid transparent;border-top:0;border-bottom:9px solid #a2a9b1;top:-9px;left:293px}.mwe-popups.mwe-popups-image-pointer.flipped-x:after{content:'';position:absolute;border:12px solid transparent;border-top:0;border-bottom:12px solid #ffffff;top:-8px;left:290px}.mwe-popups.mwe-popups-image-pointer .mwe-popups-extract{padding-top:16px;margin-top:200px}.mwe-popups.mwe-popups-image-pointer > div > a > svg{margin-top:-8px;position:absolute;z-index:113;left:0}.mwe-popups.flipped-x.mwe-popups-is-tall{min-height:242px}.mwe-popups.flipped-x.mwe-popups-is-tall:before{content:'';position:absolute;border:9px solid transparent;border-top:0;border-bottom:9px solid #a2a9b1;top:-9px;left:420px;z-index:111}.mwe-popups.flipped-x.mwe-popups-is-tall > div > a > svg{margin:0;margin-top:-8px;margin-bottom:-7px;position:absolute;z-index:113;right:0}.mwe-popups.flipped-x.mwe-popups-is-tall .mwe-popups-extract{margin-top:8px}.mwe-popups.flipped-x-y:before{content:'';position:absolute;border:9px solid transparent;border-bottom:0;border-top:9px solid #a2a9b1;bottom:-9px;left:293px;z-index:111}.mwe-popups.flipped-x-y:after{content:'';position:absolute;border:12px solid transparent;border-bottom:0;border-top:12px solid #ffffff;bottom:-8px;left:290px;z-index:112}.mwe-popups.flipped-x-y.mwe-popups-is-tall{min-height:242px}.mwe-popups.flipped-x-y.mwe-popups-is-tall:before{content:'';position:absolute;border:9px solid transparent;border-bottom:0;border-top:9px solid #a2a9b1;bottom:-9px;left:420px}.mwe-popups.flipped-x-y.mwe-popups-is-tall:after{content:'';position:absolute;border:12px solid transparent;border-bottom:0;border-top:12px solid #ffffff;bottom:-8px;left:417px}.mwe-popups.flipped-x-y.mwe-popups-is-tall > div > a > svg{margin:0;margin-bottom:-9px;position:absolute;z-index:113;right:0}.mwe-popups.flipped-y:before{content:'';position:absolute;border:8px solid transparent;border-bottom:0;border-top:8px solid #a2a9b1;bottom:-8px;left:10px}.mwe-popups.flipped-y:after{content:'';position:absolute;border:11px solid transparent;border-bottom:0;border-top:11px solid #ffffff;bottom:-7px;left:7px}.mwe-popups-is-tall polyline{-webkit-transform:translate(0,0);-ms-transform:translate(0,0);transform:translate(0,0)}.mwe-popups-is-tall.flipped-x-y polyline{-webkit-transform:translate(0,-8px);-ms-transform:translate(0,-8px);transform:translate(0,-8px)}.mwe-popups-is-tall.flipped-x polyline{-webkit-transform:translate(0,8px);-ms-transform:translate(0,8px);transform:translate(0,8px)}.rtl .mwe-popups-is-tall polyline{-webkit-transform:translate(-100%,0);-ms-transform:translate(-100%,0);transform:translate(-100%,0)}.rtl .mwe-popups-is-tall.flipped-x-y polyline{-webkit-transform:translate(-100%,-8px);-ms-transform:translate(-100%,-8px);transform:translate(-100%,-8px)}.rtl .mwe-popups-is-tall.flipped-x polyline{-webkit-transform:translate(-100%,8px);-ms-transform:translate(-100%,8px);transform:translate(-100%,8px)}@supports (clip-path:polygon(1px 1px)){.mwe-popups .mwe-popups-thumbnail{display:block;object-fit:cover;outline:1px solid rgba(0,0,0,0.1)}.mwe-popups .mwe-popups-extract[dir='rtl'] + footer{ left:unset; right:0}.mwe-popups .mwe-popups-extract[dir='rtl'] + footer .mwe-popups-settings-icon{ float:left}.mwe-popups.flipped-y .mwe-popups-discreet,.mwe-popups.flipped-x-y .mwe-popups-discreet{margin-bottom:0}.mwe-popups.flipped-y .mwe-popups-container,.mwe-popups.flipped-x-y .mwe-popups-container{--y1:100%;--y2:calc(100% - var(--pointer-height));--y3:calc(100% - var(--pointer-height) - var(--pseudo-radius));--y4:var(--pseudo-radius);--y5:0;margin-bottom:-9px;margin-top:0}.mwe-popups .mwe-popups-container{--x1:0;--x2:var(--pseudo-radius);--x3:calc(var(--pointer-offset) - (var(--pointer-width) / 2));--x4:var(--pointer-offset);--x5:calc(var(--pointer-offset) + (var(--pointer-width) / 2));--x6:calc(100% - var(--pseudo-radius));--x7:100%;--y1:0;--y2:var(--pointer-height);--y3:calc(var(--pointer-height) + var(--pseudo-radius));--y4:calc(100% - var(--pseudo-radius));--y5:100%;padding-top:0;display:flex;background:#fff;--pseudo-radius:2px;--pointer-height:8px;--pointer-width:16px;--pointer-offset:26px;clip-path:polygon(var(--x2) var(--y2),var(--x3) var(--y2),var(--x4) var(--y1),var(--x5) var(--y2),var(--x6) var(--y2),var(--x7) var(--y3),var(--x7) var(--y4),var(--x6) var(--y5),var(--x2) var(--y5),var(--x1) var(--y4),var(--x1) var(--y3))}.mwe-popups.mwe-popups-is-tall{flex-direction:row}.mwe-popups.mwe-popups-is-tall .mwe-popups-discreet{order:1}.mwe-popups.mwe-popups-is-tall .mwe-popups-discreet .mwe-popups-thumbnail{width:203px;box-sizing:border-box;height:250px}.mwe-popups.mwe-popups-is-not-tall .mwe-popups-thumbnail{width:320px;height:192px}.mwe-popups.mwe-popups-is-not-tall .mwe-popups-container{flex-direction:column}.mwe-popups:before{display:none}.mwe-popups:after{display:none}.mwe-popups.mwe-popups-image-pointer .mwe-popups-discreet{margin-bottom:0}.mwe-popups.mwe-popups-image-pointer .mwe-popups-extract{margin-top:0}.mwe-popups:not(.flipped-y):not(.flipped-x-y):not(.mwe-popups-image-pointer):not(.mwe-popups-type-disambiguation) .mwe-popups-extract{padding-top:var(--pointer-height)}.mwe-popups.mwe-popups-type-generic:not(.flipped-y):not(.flipped-x-y) .mwe-popups-container,.mwe-popups.mwe-popups-type-disambiguation:not(.flipped-y):not(.flipped-x-y) .mwe-popups-container{padding-top:var(--pointer-height)}.mwe-popups.mwe-popups-type-generic:not(.flipped-y):not(.flipped-x-y) .mwe-popups-container .mwe-popups-extract,.mwe-popups.mwe-popups-type-disambiguation:not(.flipped-y):not(.flipped-x-y) .mwe-popups-container .mwe-popups-extract{margin-bottom:calc(var(--margin-bottom) - var(--pointer-height))}body.ltr .mwe-popups.flipped-x .mwe-popups-container,body.ltr .mwe-popups.flipped-x-y .mwe-popups-container,body.rtl .mwe-popups:not(.flipped-x):not(.flipped-x-y) .mwe-popups-container{--x3:calc(100% - var(--pointer-offset) - (var(--pointer-width) / 2));--x4:calc(100% - var(--pointer-offset));--x5:calc(100% - var(--pointer-offset) + (var(--pointer-width) / 2))}}.mwe-popups .mwe-popups-title{display:block;font-weight:bold;margin:0 16px}#mw-content-text .reference a[href*='#'] *{pointer-events:none}.mwe-popups.mwe-popups-type-reference .mwe-popups-title{margin:0 0 12px 0}.mwe-popups.mwe-popups-type-reference .mwe-popups-title .mw-ui-icon{padding:0 8px 0 0}.mwe-popups.mwe-popups-type-reference .mwe-popups-title .mw-ui-icon:hover{ background-color:transparent !important}.mwe-popups.mwe-popups-type-reference .mwe-popups-title .mw-ui-icon-reference-note{display:none}.mwe-popups.mwe-popups-type-reference .mwe-popups-extract{margin-right:0;max-height:inherit}.mwe-popups.mwe-popups-type-reference .mwe-popups-extract .mwe-popups-scroll{max-height:348px;overflow:auto;padding-right:16px}.mwe-popups.mwe-popups-type-reference .mwe-popups-extract .mw-parser-output{overflow-wrap:break-word}.mwe-popups.mwe-popups-type-reference .mwe-popups-extract:after{display:none}.mwe-popups.mwe-popups-type-reference .mwe-popups-extract .mwe-popups-fade{position:absolute;width:100%;height:20px;background-color:transparent;background-image:-webkit-linear-gradient(top,rgba(255,255,255,0),#ffffff);background-image:linear-gradient(rgba(255,255,255,0),#ffffff);opacity:0;pointer-events:none;transition:opacity 250ms ease}.mwe-popups.mwe-popups-type-reference .mwe-popups-extract.mwe-popups-fade-out .mwe-popups-fade{opacity:1}.mwe-popups.mwe-popups-type-reference .mwe-collapsible-placeholder{font-weight:bold;margin:1em 0;position:relative}.mwe-popups.mwe-popups-type-reference .mwe-collapsible-placeholder .mw-ui-icon{margin-left:-0.78em;position:absolute}.mwe-popups.mwe-popups-type-reference .mwe-collapsible-placeholder .mwe-collapsible-placeholder-label{margin-left:2.5em}.mwe-popups-overlay{background-color:rgba(255,255,255,0.9);z-index:999;position:fixed;height:100%;width:100%;top:0;bottom:0;left:0;right:0;display:flex;justify-content:center;align-items:center}#mwe-popups-svg{position:absolute;top:-1000px}"]
}, null);
;xowa_implement("mediawiki.ui.checkbox@1kkie", null, {
    "css": [".mw-ui-checkbox{display:table;position:relative;line-height:1.5625em;vertical-align:middle}.mw-ui-checkbox *{font-size:inherit;vertical-align:middle}.mw-ui-checkbox [type='checkbox']{display:table-cell;position:relative;width:1.5625em;height:1.5625em;max-width:none;opacity:0;z-index:1}.mw-ui-checkbox [type='checkbox'] + label{display:table-cell;padding-left:0.4em}.mw-ui-checkbox [type='checkbox'] + label:before{content:'';background-color:#fff;background-origin:border-box;background-position:center center;background-repeat:no-repeat;background-size:0 0;box-sizing:border-box;position:absolute;top:50%;left:0;width:1.5625em;height:1.5625em;margin-top:-0.78125em;border:1px solid #72777d;border-radius:2px}.mw-ui-checkbox [type='checkbox']:checked + label:before{background-image:url(/w/resources/src/mediawiki.ui.checkbox/images/checkbox-checked.svg?8153e);background-size:90% 90%}.mw-ui-checkbox [type='checkbox']:enabled{cursor:pointer}.mw-ui-checkbox [type='checkbox']:enabled + label{cursor:pointer}.mw-ui-checkbox [type='checkbox']:enabled + label:before{cursor:pointer;transition:background-color 100ms,color 100ms,border-color 100ms,box-shadow 100ms}.mw-ui-checkbox [type='checkbox']:enabled:focus + label:before{border-color:#3366cc;box-shadow:inset 0 0 0 1px #3366cc}.mw-ui-checkbox [type='checkbox']:enabled:hover + label:before{border-color:#447ff5}.mw-ui-checkbox [type='checkbox']:enabled:active + label:before{background-color:#2a4b8d;border-color:#2a4b8d;box-shadow:inset 0 0 0 1px #2a4b8d}.mw-ui-checkbox [type='checkbox']:enabled:checked + label:before{background-color:#3366cc;border-color:#3366cc}.mw-ui-checkbox [type='checkbox']:enabled:checked:focus + label:before{background-color:#3366cc;border-color:#3366cc;box-shadow:inset 0 0 0 1px #3366cc,inset 0 0 0 2px #ffffff}.mw-ui-checkbox [type='checkbox']:enabled:checked:hover + label:before{background-color:#447ff5;border-color:#447ff5}.mw-ui-checkbox [type='checkbox']:enabled:checked:active + label:before{background-color:#2a4b8d;border-color:#2a4b8d}.mw-ui-checkbox [type='checkbox']:disabled + label:before{background-color:#c8ccd1;border-color:#c8ccd1}"]
}, null);
