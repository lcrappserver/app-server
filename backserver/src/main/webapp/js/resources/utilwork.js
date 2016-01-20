/**************** 表单验证框架 http://www.zhangxinxu.com/wordpress/?p=2857 *****************/
(function($, undefined) {
    DBC2SBC = function(str) {
        var result = "",
            i, code;
        for (i = 0; i < str.length; i++) {
            code = str.charCodeAt(i);
            if (code >= 65281 && code <= 65373) {
                result += String.fromCharCode(str.charCodeAt(i) - 65248)
            } else {
                if (code == 12288) {
                    result += String.fromCharCode(str.charCodeAt(i) - 12288 + 32)
                } else {
                    result += str.charAt(i)
                }
            }
        }
        return result
    };
    $.testRemind = (function() {
        var winWidth = $(window).width();
        var fnMouseDown = function(e) {
                if (!e || !e.target) {
                    return
                }
                if (e.target.id !== $.testRemind.id && $(e.target).parents("#" + $.testRemind.id).length === 0) {
                    $.testRemind.hide()
                }
            },
            fnKeyDown = function(e) {
                if (!e || !e.target) {
                    return
                }
                if (e.target.tagName.toLowerCase() !== "body") {
                    $.testRemind.hide()
                }
            },
            funResize = function() {
                if (!$.testRemind.display) {
                    return
                }
                var nowWinWidth = $(window).width();
                if (Math.abs(winWidth - nowWinWidth) > 20) {
                    $.testRemind.hide();
                    winWidth = nowWinWidth
                }
            };
        return {
            id: "validateRemind",
            display: false,
            css: {},
            hide: function() {
                $("#" + this.id).remove();
                this.display = false;
                $(document).unbind({
                    mousedown: fnMouseDown,
                    keydown: fnKeyDown
                });
                $(window).unbind("resize", funResize)
            },
            bind: function() {
                $(document).bind({
                    mousedown: fnMouseDown,
                    keydown: fnKeyDown
                });
                $(window).bind("resize", funResize)
            }
        }
    })();
    // OBJREG = {
    //     EMAIL: "(?!.*\\.co$)^[a-z0-9._%-]+@([a-z0-9-]+\\.)+[a-z]{2,4}$",
    //     NUMBER: "^\\-?\\d+(\\.\\d+)?$",
    //     URL: "^(http|https|ftp)\\:\\/\\/[a-z0-9\\-\\.]+\\.[a-z]{2,3}(:[a-z0-9]*)?\\/?([a-z0-9\\-\\._\\?\\,\\'\\/\\\\\\+&amp;%\\$#\\=~])*$",
    //     TEL: "^1\\d{10}$",
    //     ZIPCODE: "^\\d{6}$",
    //     "prompt": {
    //         radio: "请选择一个选项",
    //         // checkbox: "如果要继续，请选中此框",
    //         checkbox: "您尚未同意新致云服务条款",
    //         "select": "请选择列表中的一项",
    //         email: "请输入正确的电子邮件地址",
    //         url: "请输入网站地址",
    //         tel: "请输入手机号码",
    //         number: "请输入数值",
    //         date: "请输入日期",
    //         pattern: "内容格式不符合要求",
    //         empty: "请填写此字段",
    //         multiple: "多条数据使用逗号分隔",
    //     }
    // };
    $.html5Attr = function(ele, attr) {
        if (!ele || !attr) {
            return undefined
        }
        if (document.querySelector) {
            return $(ele).attr(attr)
        } else {
            var ret;
            ret = ele.getAttributeNode(attr);
            return ret && ret.nodeValue !== "" ? ret.nodeValue : undefined
        }
    };
    $.html5Validate = (function() {
        return {
            isSupport: (function() {
                return $('<input type="email">').attr("type") === "email"
            })(),
            // isEmpty: function(ele, value) {
            //     value = value || $.html5Attr(ele, "placeholder");
            //     var trimValue = ele.value;
            //     if (ele.type !== "password") {
            //         trimValue = $.trim(trimValue)
            //     }
            //     if (trimValue === "" || trimValue === value) {
            //         return true
            //     }
            //     return false
            // },
            // isRegex: function(ele, regex, params) {
            //     var inputValue = ele.value,
            //         dealValue = inputValue,
            //         type = ele.getAttribute("type") + "";
            //     type = type.replace(/\W+$/, "");
            //     if (type !== "password") {
            //         dealValue = DBC2SBC($.trim(inputValue));
            //         dealValue !== inputValue && $(ele).val(dealValue)
            //     }
            //     regex = regex || (function() {
            //         return $.html5Attr(ele, "pattern")
            //     })() || (function() {
            //         return type && $.map(type.split("|"), function(typeSplit) {
            //             var matchRegex = OBJREG[typeSplit.toUpperCase()];
            //             if (matchRegex) {
            //                 return matchRegex
            //             }
            //         }).join("|")
            //     })();
            //     if (dealValue === "" || !regex) {
            //         return true
            //     }
            //     var isMultiple = $(ele).hasProp("multiple"),
            //         newRegExp = new RegExp(regex, params || "gi");
            //     if (isMultiple && !/^number|range$/i.test(type)) {
            //         var isAllPass = true;
            //         $.each(dealValue.split(","), function(i, partValue) {
            //             partValue = $.trim(partValue);
            //             if (isAllPass && !newRegExp.test(partValue)) {
            //                 isAllPass = false
            //             }
            //         });
            //         return isAllPass
            //     } else {
            //         return newRegExp.test(dealValue)
            //     }
            //     return true
            // },
            // isOverflow: function(ele) {
            //     if (!ele) {
            //         return false
            //     }
            //     var attrMin = $(ele).attr("min"),
            //         attrMax = $(ele).attr("max"),
            //         attrStep, attrDataMin, attrDataMax, value = ele.value;
            //     if (!attrMin && !attrMax) {
            //         attrDataMin = $(ele).attr("data-min"), attrDataMax = $(ele).attr("data-max");
            //         if (attrDataMin && value.length < attrDataMin) {
            //             $(ele).testRemind("至少输入" + attrDataMin + "个字符");
            //             //ele.focus()
            //         } else {
            //             if (attrDataMax && value.length > attrDataMax) {
            //                 $(ele).testRemind("最多输入" + attrDataMax + "个字符");
            //                 try {
            //                     $(ele).selectRange(attrDataMax, value.length)
            //                 } catch (e) {}
            //             } else {
            //                 return false
            //             }
            //         }
            //     } else {
            //         value = Number(value);
            //         attrStep = Number($(ele).attr("step")) || 1;
            //         if (attrMin && value < attrMin) {
            //             $(ele).testRemind("值必须大于或等于" + attrMin)
            //         } else {
            //             if (attrMax && value > attrMax) {
            //                 $(ele).testRemind("值必须小于或等于" + attrMax)
            //             } else {
            //                 if (attrStep && !/^\d+$/.test(Math.abs((value - attrMin || 0)) / attrStep)) {
            //                     $(ele).testRemind("值无效")
            //                 } else {
            //                     return false
            //                 }
            //             }
            //         }
            //         //ele.focus();
            //         //ele.select()
            //     }
            //     return true
            // },
            isAllpass: function(elements, options) {
                if (!elements) {
                    return true
                }
                var defaults = {
                    labelDrive: true
                };
                params = $.extend({}, defaults, options || {});
                if (elements.size && elements.size() == 1 && elements.get(0).tagName.toLowerCase() == "form") {
                    elements = elements.find(":input")
                } else {
                    if (elements.tagName && elements.tagName.toLowerCase() == "form") {
                        elements = $(elements).find(":input")
                    }
                }
                var self = this;
                var allpass = true,
                    remind = function(control, type, tag) {
                        var key = $(control).attr("data-key"),
                            label = $("label[for='" + control.id + "']"),
                            text = "",
                            placeholder;
                        if (params.labelDrive) {
                            placeholder = $.html5Attr(control, "placeholder");
                            label.each(function() {
                                var txtLabel = $(this).text();
                                if (txtLabel !== placeholder) {
                                    text += txtLabel.replace(/\*|:|：/g, "")
                                }
                            })
                        }
                        if ($(control).isVisible()) {
                            if (type == "radio" || type == "checkbox") {
                                $(control).testRemind(OBJREG.prompt[type], {
                                    align: "left"
                                });
                                //control.focus()
                            } else {
                                if (tag == "select" || tag == "empty") {
                                    $(control).testRemind((tag == "empty" && text) ? text + "不能为空" : OBJREG.prompt[tag]);
                                    //control.focus()
                                } else {
                                    if (/^range|number$/i.test(type) && Number(control.value)) {
                                        $(control).testRemind("值无效");
                                        //control.focus();
                                        //control.select()
                                    } else {
                                        var finalText = OBJREG.prompt[type] || OBJREG.prompt["pattern"];
                                        if (!finalText && text) {
                                            finalText = "您输入的" + text + "格式不准确"
                                        }
                                        if (type != "number" && $(control).hasProp("multiple")) {
                                            finalText += "，" + OBJREG.prompt["multiple"]
                                        }
                                        $(control).testRemind(finalText);
                                        //control.focus();
                                        //control.select()
                                    }
                                }
                            }
                        } else {
                            var selector = $(control).attr("data-target");
                            var target = $("#" + selector);
                            if (target.size() == 0) {
                                target = $("." + selector)
                            }
                            var customTxt = "您尚未" + (key || (tag == "empty" ? "输入" : "选择")) + ((!/^radio|checkbox$/i.test(type) && text) || "该项内容");
                            if (target.size()) {
                                if (target.offset().top < $(window).scrollTop()) {
                                    $(window).scrollTop(target.offset().top - 50)
                                }
                                target.testRemind(customTxt)
                            } else {
                                alert(customTxt)
                            }
                        }
                        return false
                    };
                // elements.each(function() {
                //     var el = this,
                //         type = el.getAttribute("type"),
                //         tag = el.tagName.toLowerCase(),
                //         isRequired = $(this).hasProp("required");
                //     if (type) {
                //         var typeReplace = type.replace(/\W+$/, "");
                //         if (!params.hasTypeNormally && $.html5Validate.isSupport && type != typeReplace) {
                //             try {
                //                 el.type = typeReplace
                //             } catch (e) {}
                //         }
                //         type = typeReplace
                //     }
                //     if (allpass == false || el.disabled || type == "submit" || type == "reset" || type == "file" || type == "image") {
                //         return
                //     }
                //     if (type == "radio" && isRequired) {
                //         var eleRadios = el.name ? $("input[type='radio'][name='" + el.name + "']") : $(el),
                //             radiopass = false;
                //         eleRadios.each(function() {
                //             if (radiopass == false && $(this).is(":checked")) {
                //                 radiopass = true
                //             }
                //         });
                //         if (radiopass == false) {
                //             allpass = remind(eleRadios.get(0), type, tag)
                //         }
                //     } else {
                //         if (type == "checkbox" && isRequired && !$(el).is(":checked")) {
                //             allpass = remind(el, type, tag)
                //         } else {
                //             if (tag == "select" && isRequired && !el.value) {
                //                 allpass = remind(el, type, tag)
                //             } else {
                //                 if ((isRequired && self.isEmpty(el)) || !(allpass = self.isRegex(el))) {
                //                     allpass ? remind(el, type, "empty") : remind(el, type, tag);
                //                     allpass = false
                //                 } else {
                //                     if (self.isOverflow(el)) {
                //                         allpass = false
                //                     }
                //                 }
                //             }
                //         }
                //     }
                // });
                return allpass
            }
        }
    })();
    $.fn.extend({
        isVisible: function() {
            return $(this).attr("type") !== "hidden" && $(this).css("display") !== "none" && $(this).css("visibility") !== "hidden"
        },
        hasProp: function(prop) {
            if (typeof prop !== "string") {
                return undefined
            }
            var hasProp = false;
            if (document.querySelector) {
                var attrProp = $(this).attr(prop);
                if (attrProp !== undefined && attrProp !== false) {
                    hasProp = true
                }
            } else {
                var outer = $(this).get(0).outerHTML,
                    part = outer.slice(0, outer.search(/\/?['"]?>(?![^<]*<['"])/));
                hasProp = new RegExp("\\s" + prop + "\\b", "i").test(part)
            }
            return hasProp
        },
        selectRange: function(start, end) {
            var that = $(this).get(0);
            if (that.createTextRange) {
                var range = that.createTextRange();
                range.collapse(true);
                range.moveEnd("character", end);
                range.moveStart("character", start);
                range.select()
            } else {
                that.focus();
                that.setSelectionRange(start, end)
            }
            return this
        },
        testRemind: function(content, options) {
            var defaults = {
                size: 6,
                align: "center",
                css: {
                    maxWidth: 280,
                    backgroundColor: "#FFFFE0",
                    borderColor: "#F7CE39",
                    color: "#333",
                    fontSize: "12px",
                    padding: "5px 10px",
                    borderRadius: "3px",
                    boxShadow: "0 1px 3px rgba(0,0,0,.3)",
                    zIndex: 2000
                }
            };
            options = options || {};
            options.css = $.extend({}, defaults.css, options.css || $.testRemind.css);
            var params = $.extend({}, defaults, options || {});
            if (!content || !$(this).isVisible()) {
                return
            }
            var objAlign = {
                    "center": "50%",
                    "left": "15%",
                    "right": "85%"
                },
                align = objAlign[params.align] || "50%";
            params.css.position = "absolute";
            params.css.top = "-99px";
            params.css.border = "1px solid " + params.css.borderColor;
            if ($("#" + $.testRemind.id).size()) {
                $.testRemind.hide()
            }
            this.remind = $('<div id="' + $.testRemind.id + '">' + content + "</div>").css(params.css);
            $(document.body).append(this.remind);
            var maxWidth;
            if (!window.XMLHttpRequest && (maxWidth = parseInt(params.css.maxWidth)) && this.remind.width() > maxWidth) {
                this.remind.width(maxWidth)
            }
            var offset = $(this).offset(),
                direction = "top";
            if (!offset) {
                return $(this)
            }
            var remindTop = offset.top - this.remind.outerHeight() - params.size;
            if (remindTop < $(document).scrollTop()) {
                direction = "bottom";
                remindTop = offset.top + $(this).outerHeight() + params.size
            }
            var fnCreateCorner = function(beforeOrAfter) {
                var transparent = "transparent",
                    dashed = "dashed",
                    solid = "solid";
                var cssWithDirection = {},
                    cssWithoutDirection = {
                        width: 0,
                        height: 0,
                        overflow: "hidden",
                        borderWidth: params.size + "px",
                        position: "absolute"
                    },
                    cssFinalUsed = {};
                if (beforeOrAfter === "before") {
                    cssWithDirection = {
                        "top": {
                            borderColor: [params.css.borderColor, transparent, transparent, transparent].join(" "),
                            borderStyle: [solid, dashed, dashed, dashed].join(" "),
                            top: 0
                        },
                        "bottom": {
                            borderColor: [transparent, transparent, params.css.borderColor, ""].join(" "),
                            borderStyle: [dashed, dashed, solid, dashed].join(" "),
                            bottom: 0
                        }
                    }
                } else {
                    if (beforeOrAfter === "after") {
                        cssWithDirection = {
                            "top": {
                                borderColor: params.css.backgroundColor + ["", transparent, transparent, transparent].join(" "),
                                borderStyle: [solid, dashed, dashed, dashed].join(" "),
                                top: -1
                            },
                            "bottom": {
                                borderColor: [transparent, transparent, params.css.backgroundColor, ""].join(" "),
                                borderStyle: [dashed, dashed, solid, dashed].join(" "),
                                bottom: -1
                            }
                        }
                    } else {
                        cssWithDirection = null;
                        cssWithoutDirection = null;
                        cssFinalUsed = null;
                        return null
                    }
                }
                cssFinalUsed = $.extend({}, cssWithDirection[direction], cssWithoutDirection);
                return $("<" + beforeOrAfter + "></" + beforeOrAfter + ">").css(cssFinalUsed)
            };
            var cssOuterLimit = {
                width: 2 * params.size,
                left: align,
                marginLeft: (-1 * params.size) + "px",
                height: params.size,
                textIndent: 0,
                overflow: "hidden",
                position: "absolute"
            };
            if (direction == "top") {
                cssOuterLimit["bottom"] = -1 * params.size
            } else {
                cssOuterLimit["top"] = -1 * params.size
            }
            this.remind.css({
                left: offset.left,
                top: remindTop,
                marginLeft: $(this).outerWidth() * 0.5 - this.remind.outerWidth() * parseInt(align) / 100
            }).prepend($("<div></div>").css(cssOuterLimit).append(fnCreateCorner("before")).append(fnCreateCorner("after")));
            $.testRemind.display = true;
            $.testRemind.bind();
            return $(this)
        },
        html5Validate: function(callback, options) {
            var defaults = {
                novalidate: true,
                submitEnabled: true,
                validate: function() {
                    return true
                }
            };
            var params = $.extend({}, defaults, options || {});
            var elements = $(this).find(":input");
            if ($.html5Validate.isSupport) {
                if (params.novalidate) {
                    $(this).attr("novalidate", "novalidate")
                } else {
                    elements.each(function() {
                        var type = this.getAttribute("type") + "",
                            typeReplaced = type.replace(/\W+$/, "");
                        if (type != typeReplaced) {
                            try {
                                this.type = typeReplaced
                            } catch (e) {}
                        }
                    });
                    params.hasTypeNormally = true
                }
            }
            if (params.submitEnabled) {
                $(this).find(":disabled").each(function() {
                    if (/^image|submit$/.test(this.type)) {
                        $(this).removeAttr("disabled")
                    }
                })
            }
            $(this).bind("submit", function() {
                if ($.html5Validate.isAllpass(elements, params) && params.validate() && $.isFunction(callback)) {
                    callback.call(this)
                }
                return false
            });
            return $(this)
        }
    })
})(jQuery);

$.fn.immediValidate = function(passCallback) {
    return $(this).find(":input").each(function() {
        if (/^submit|image|reset|file|radio|checkbox|button$/.test(this.type)) return;
        var self = this;
        $(this).bind("blur", function() {
            setTimeout(function() {
                if ($.testRemind.display == false && $.html5Validate.isAllpass($(self))) {
                    $.isFunction(passCallback) && passCallback.call(self);
                }
            }, 100);
        });
    });
};

/**************** Cookie 操作相关类 https://github.com/carhartl/jquery-cookie *****************/
(function(factory) {
    if (typeof define1 === "function" && define1.amd) {
        define1(["jquery"], factory)
    } else {
        if (typeof exports === "object") {
            factory(require("jquery"))
        } else {
            factory(jQuery)
        }
    }
}(function($) {
    var pluses = /\+/g;

    function encode(s) {
        return config.raw ? s : encodeURIComponent(s)
    }

    function decode(s) {
        return config.raw ? s : decodeURIComponent(s)
    }

    function stringifyCookieValue(value) {
        return encode(config.json ? JSON.stringify(value) : String(value))
    }

    function parseCookieValue(s) {
        if (s.indexOf('"') === 0) {
            s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, "\\")
        }
        try {
            s = decodeURIComponent(s.replace(pluses, " "));
            return config.json ? JSON.parse(s) : s
        } catch (e) {}
    }

    function read(s, converter) {
        var value = config.raw ? s : parseCookieValue(s);
        return $.isFunction(converter) ? converter(value) : value
    }
    var config = $.cookie = function(key, value, options) {
        if (value !== undefined && !$.isFunction(value)) {
            options = $.extend({}, config.defaults, options);
            if (typeof options.expires === "number") {
                var days = options.expires,
                    t = options.expires = new Date();
                t.setTime(+t + days * 86400000)
            }
            return (document.cookie = [encode(key), "=", stringifyCookieValue(value), options.expires ? "; expires=" + options.expires.toUTCString() : "", options.path ? "; path=" + options.path : "", options.domain ? "; domain=" + options.domain : "", options.secure ? "; secure" : ""].join(""))
        }
        var result = key ? undefined : {};
        var cookies = document.cookie ? document.cookie.split("; ") : [];
        for (var i = 0, l = cookies.length; i < l; i++) {
            var parts = cookies[i].split("=");
            var name = decode(parts.shift());
            var cookie = parts.join("=");
            if (key && key === name) {
                result = read(cookie, value);
                break
            }
            if (!key && (cookie = read(cookie)) !== undefined) {
                result[name] = cookie
            }
        }
        return result
    };
    config.defaults = {};
    $.removeCookie = function(key, options) {
        if ($.cookie(key) === undefined) {
            return false
        }
        $.cookie(key, "", $.extend({}, options, {
            expires: -1
        }));
        return !$.cookie(key)
    }
}));

/**************** 面向对象编程 *****************/
(function() {
    var initializing = false,
        fnTest = /xyz/.test(function() {
            xyz
        }) ? /\b_super\b/ : /.*/;
    this.Class = function() {};
    Class.extend = function(prop) {
        var _super = this.prototype;
        initializing = true;
        var prototype = new this();
        initializing = false;
        for (var name in prop) {
            prototype[name] = typeof prop[name] == "function" && typeof _super[name] == "function" && fnTest.test(prop[name]) ? (function(name, fn) {
                return function() {
                    var tmp = this._super;
                    this._super = _super[name];
                    var ret = fn.apply(this, arguments);
                    this._super = tmp;
                    return ret
                }
            })(name, prop[name]) : prop[name]
        }

        function Class() {
            if (!initializing && this.init) {
                this.init.apply(this, arguments)
            }
        }
        Class.prototype = prototype;
        Class.prototype.constructor = Class;
        Class.extend = arguments.callee;
        return Class
    }
})();

// 全局参数定义
window.nsParam = (function() {

    var envs = {
        pre: { // 预发布
            when: ['workdev.sh1.newtouch.com'],
            loginUrl: '//stepdev.sh1.newtouch.com',
        },
        self: { // 本机环境
            when: ['127.0.0.1', 'localhost'],
            loginUrl: '//127.0.0.1:81',
        },
        dev: { // 开发环境
            when: ['192.168.7.15'],
            loginUrl: '//192.168.7.15:81',
        },
        test: { // IDC测试环境
            when: ['wtest.newtouch.com'],
            loginUrl: '//stest.newtouch.com',
        },
        prod: { // 生产环境
            when: ['newtouch.com'],
            loginUrl: '//step.newtouch.com',
        }
    };

    for (var env in envs) {
        for (var w in envs[env].when) {
            if (window.location.href.indexOf(envs[env].when[w]) !== -1) {
                return envs[env];
            }
        }
    }

    return envs['prod'];
})();

/**************** 公共工具类 *****************/
// JavaScript Document
var Util = Class.extend({
    cookie: {
        user: 'client_login_user'
    },
    // ============================= init 构造方法 ===============================
    init: function() {
        if ($.cookie) $.cookie.json = true;

        $.ajaxSetup({
            cache: false
        });

        jQuery.cachedScript = function(url, options) {

            // Allow user to set any option except for dataType, cache, and url
            options = $.extend(options || {}, {
                dataType: "script",
                cache: true,
                url: url
            });

            // Use $.ajax() since it is more flexible than $.getScript
            // Return the jqXHR object so we can chain callbacks
            return jQuery.ajax(options);
        };
    },
    // 准备方法
    ready: function() {

        // OBJREG.USER = "(?!.*\\.git$)^[a-zA-Z]([a-zA-Z0-9._-]){7,29}$";
        // OBJREG["prompt"].user = "用户名格式不正确";
        // // 密码框验证
        // OBJREG.PASSWORD = "^[\.%-]*([0-9]*[a-zA-Z]+[0-9]+[a-zA-Z]*)|([a-zA-Z]*[0-9]+[a-zA-Z]+[0-9]*)[\.%-]*$";
        // OBJREG["prompt"].password = "密码需包含字母和数字,必须大于8个字符";
        // // 电话框验证    
        // //  匹配格式：
        // // 11位手机号码
        // OBJREG.PHONE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
        // OBJREG["prompt"].phone = "请输入有效的手机号码";
        // // 3-4位区号，7-8位直播号码，1－4位分机号  
        // // OBJREG.Tel = "(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$";
        // OBJREG.TELE = "(^1\\d{10}$)|(^\\d{7,8}$)|(^(\\d{4}|\\d{3})-(\\d{7,8})$)|(^(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{1,4})$)|(^(\\d{7,8})-(\\d{1,4})$)";
        // OBJREG["prompt"].tele = "11位手机号码,3-4位区号,7-8位直播号码,1－4位分机号    ";

        // //验证电子邮件

        // // OBJREG["prompt"].email = "输入正确的邮件地址";
        // // 必填项类型
        // OBJREG["required"] = {
        //     tele: true
        // };

        // // 设置验证规则
        // utils.setValidate(); // 向OBJREG控件中加 required 属性
    },
    // ============================= 表单操作相关方法  ===============================
    // 设置验证规则
    // 设置表单元素验证规则
    setValidate: function(select) {
        if (!select) select = 'body';
        $(select).find('input:checked,input:selected,[name][type!=checkbox][type!=radio]').each(function() {
            var an = $(this).attr('name');
            if (!an) return;

            var type = $(this).attr('type');
            if (OBJREG.required[type]) $(this).attr('required', true);
            var a = {};
            if (type === 'email') {
                a['data-max'] = '30';
            } else if (type === 'password') {
                a['data-min'] = 8;
                a['data-max'] = 20;
            } else if (type === 'version') {
                a['data-max'] = 30;
            }
            if (utils.isNotEmpty(a)) {
                $(this).attr(a);
            }
        });
    },
    // 是否数据验证通过
    lastForm: null,
    // 获得表单数据
    getForm: function(select) {
        if (!select) return;
        if (!$.html5Validate.isAllpass($(select).find('input,textarea,select'))) return;
        utils.lastForm = select;
        var obj = {};
        $(select).find('input:checked,input:selected,[name][type!=checkbox][type!=radio]').each(function() {
            var an = $(this).attr('name');
            var tag = $(this)[0].tagName;
            if (!an || tag.toLowerCase() === 'param') return;
            var av = $(this).val() || utils.getOther($(this));

            var old = obj[an] || '';
            av = old ? old + ',' + av : av;

            obj['' + an] = av;
        });
        return obj;
    },
    getOther: function(select) {
        if (!select) return '';
        var t = $(select).attr('type');

        // 获取上传文件值
        if (t === 'upload') {
            return utils.getUpload(select);
            // 获取 包含 selected 的值
        } else if (t) {
            if (t) {
                return $(select).attr(t);
            } else {
                var r = '';
                $(select).find('.selected').each(function() {
                    r += ',' + $(this).attr(t);
                });
                return r && r.substring(1);
            }
        } else {
            return utils.getUnit(select);
        }
    },
    // 将表单还原到初始化状态
    clear: function() {
        var select = utils.lastForm;
        if (!select || select == "#my-setup #form_info") return; //修改用户信息时不需要清空表单 bug1239
        var cs = $(select).find('input:checked,input:selected,[name][type!=checkbox][type!=radio]');
        if (cs.length < 1) {
            cs = $(select);
        }
        cs.each(function() {
            if ($(this).hasClass('input_trans')) return;
            if ($(this).hasClass('no_clear')) return;
            var tag = $(this)[0].tagName.toLowerCase();
            if (tag === 'param') return;
            var readonly = $(this).attr('readonly');
            if (readonly) return;
            if (tag === 'input') {
                $(this).val('');
            }
            if (tag === 'textarea') $(this).val('');
        });
    },
    // ============================= Ajax 相关操作 ==================================
    showLoading: function() {
        $('#ajax-loading').modal('show');
    },
    hideLoading: function() {
        $('#ajax-loading').modal('hide');
    },
    urlAjaxCache: {},
    ajax: function(url, param, method) {

        return utils.dfd(url, function(dfd) {
            $.ajax({
                type: method || "POST",
                url: url,
                data: param,
                dataType: "json",
                timeout: 8000000,
                beforeSend: function() {},
                success: function(msg) {
                    dfd.resolve(msg);
                    delete utils.urlAjaxCache[url];
                },
                error: function(XMLHttpRequest, textStatus, errorThrown) {
                    dfd.reject(XMLHttpRequest.status, XMLHttpRequest.responseJSON);
                    // utils.err(textStatus || errorThrown);
                },
                complete: function(XMLHttpRequest, textStatus) {}
            });
        }, 'url is empty', 8000000);
    },
    ajaxCache: {},
    // ajax 获得后台请求后，对数据进行处理
    ajaxExcute: function(operMap, param, data, url) {
        var oper = false;
        for (var k in operMap) {
            if (k === data.code || (k.indexOf(data.code) > -1)) {
                var f = operMap[k];
                if (typeof f === 'function') f(param, data);
                else if (typeof f === 'string') utils.remind(f, data);
                oper = true;
                break;
            } else if ('UNLOGIN' === data.code) {
                utils.ajaxClose = true;
                utils.ajaxOperCache = [];
                oper = true;
            } else if ('TOKEN_OUT' === data.code) {
                //window.location.href = "/page/login/login.html";
            }
        }
        if (!oper) {
            // $('#ajax-message-box').text(data.message);
            // $('#ajax-message').modal();
            // setTimeout(function() {
            //     $('#ajax-message').modal('hide');
            // }, 5000);
            // alert('请　求：　' + url + '\n返回码：　' + data.code + '\n消　息：　' + data.message);
        }
    },
    ajaxGoto: function(dfd, url, select, method, operMap) {
        // 参数格式验证
        if (!utils.isObject(operMap)) utils.err('operMap is object,example: {"SUCCESS": function(){ alert("SUCCESS"); }}');

        var param = select;
        if (select && !utils.isObject(select)) {
            // 提取表单数据
            param = utils.getForm(select);
            // 表单验证错误
            if (!param) {
                dfd.reject();
                return;
            }
        }

        var np = {};
        if (utils.isNotEmpty(param)) {
            for (var k in param) {
                var v = param[k];
                if (typeof v != 'undefined' && null != v) {
                    np[k] = v;
                }
            }
        }

        // if('get,put,delete'.indexOf(method.toLowerCase()) > -1){
        if ('put'.indexOf(method.toLowerCase()) > -1) {
            if (url.indexOf("?") < 0) url += "?";
            url += utils.parseParam(np);
            np = {};
        }

        utils.ajax(url, np, method).done(function(data) {
            try {
                utils.ajaxExcute(operMap, param, data, url);
            } catch (e) {
                utils.err(e);
            }
            dfd.resolve(data);
        }).fail(function(status, msg) {
            // $('#ajax-message-box').html('您的请求：<br/>' + url + '<br/>系统正忙，请稍后重试！');
            // $('#ajax-message').modal();
            // setTimeout(function() {
            //     $('#ajax-message').modal('hide');
            // }, 5000);
            dfd.reject(status, msg);
        }).always(function() {

        });
    },
    parseParam: function(param) {
        var paramStr = "";
        for (var key in param) {
            if (param[key]) {
                paramStr += "&" + key + "=" + encodeURIComponent(param[key]);
            }
        }
        return paramStr.substr(1);
    },
    // ajaxOper 缓存操作， 数组
    ajaxOperCache: [],
    // 收集页面需要，ajax操作
    ajaxOper: function(url, select, method, operMap) {
        if (method) {
            if (typeof method != "string" || 'post,get,put,delete'.indexOf(method.toLowerCase()) < 0) {
                operMap = method;
                method = 'POST';
            }
        }
        /*
        return utils.dfd(url, function(dfd) { 
            var t = select;
            if(utils.ajaxClose) return;
            if(utils.isArray(select) || utils.isObject(select)) t = utils.clone(select);
            var ajaxObj = {'url': url, 'select': t, 'method': method, 'operMap': operMap, 'dfd': dfd };
            utils.ajaxOperCache[utils.ajaxOperCache.length] = ajaxObj;
            utils.ajaxRecursion();
        }, 'utils.ajaxOper excute error');
        */
        return utils.dfd(url, function(dfd) {
            utils.btnLoading(url);
            utils.ajaxGoto(dfd, url, select, method, operMap);
        }, 'utils.ajaxOper excute error').always(function() {
            utils.btnRest(url);
        });
    },
    // 对ajaxOper 缓存数组的递归请求
    ajaxLock: false,
    ajaxClose: false,
    isButton: function(select) {
        var s = $(select);
        if (s.length < 1) return false;
        var tag = s[0].tagName.toLowerCase();
        if ('button' == tag) return true;
        else {
            var pb = s.parents('button');
            if (pb.length > 0) {
                utils.clickObj = pb[0];
                return true;
            }
        }
        return false;
    },
    btnLoading: function(url) {
        if (utils.clickObj && utils.isButton(utils.clickObj)) {
            var co = $(utils.clickObj);
            var au = co.attr('url');
            if (!au || au == url) {
                if (!co.is('[data-loading-text]')) {
                    var t = co.text();
                    if (co.hasClass('font_btn')) {
                        t = ' ';
                        co.attr({
                            'title': '处理中...'
                        });
                    } else {
                        if (!t) {
                            t = '处理中...';
                        } else {
                            t = t + '中...';
                        }
                    }
                    co.attr({
                        'data-loading-text': t,
                        'autocomplete': 'off'
                    });
                }
                co.attr({
                    'url': url
                });
                co.button('loading');
            }
        }
    },
    btnRest: function(url) {
        if (utils.clickObj && utils.isButton(utils.clickObj)) {
            var co = $(utils.clickObj);
            var au = co.attr('url');
            if (au == url) {
                if (co.hasClass('font_btn')) co.removeAttr('title');
                co.button('reset');
            }
        }
    },

    // 在选择器上显示错误信息
    remind: function(select, msg) {
        var r = $(select).testRemind(typeof msg !== 'string' ? msg.message : msg);
        // r && r.get(0) && r.get(0).focus();
    },
    message: function(select, msg) {
        $(select).testRemind(typeof msg !== 'string' ? msg.message : msg);
        setTimeout(function() {
            $("#validateRemind").fadeOut("slow");
        }, 3000);
    },
    getKey: function(arr, obj) {
        if (utils.isArray(arr) && utils.isObject(obj)) {
            var r = '';
            for (var i in arr) {
                var k = arr[i];
                var v = obj[k];
                if (!k || !v) {
                    continue;
                }
                r += '_' + v;
            }
            return r && r.substring(1);
        }
    },
    // ============================= 页面缓存 相关操作   ===============================
    // 浏览器端设置cookie
    setCookie: function(k, v, option) {
        var p = $.extend({}, {
            expires: 365,
            path: '/'
        }, option);

        $.cookie(k, v, p);
    },
    // 浏览器端获取cookie
    getCookie: function(k) {
        try {
            var v = $.cookie(k);
            if (v) return v;
            var allcookies = document.cookie;
            var cookie_pos = allcookies.indexOf(k);

            if (cookie_pos != -1) {
                cookie_pos += k.length + 1;
                var cookie_end = allcookies.indexOf(";", cookie_pos);

                if (cookie_end == -1) {
                    cookie_end = allcookies.length;
                }
                var value = unescape(allcookies.substring(cookie_pos, cookie_end));
                var m = value.match('"(.*)"');
                if (m) {
                    return m[1];
                }
                return value;
            }
        } catch (e) {}
    },
    // 删除现有cookie
    removeCookie: function(k) {
        $.removeCookie(k);
    },
    // 获得当前项目编号
    cookiePid: function() {
        return utils.getCookie('PROJECTID')
    },
    //设置cookie有效时间
    shortCookie: function(key, value, expiresDate) {
        $.cookie(key, value, {
            path: '/', //cookie的作用域
            expires: expiresDate
        });
    },
    // 设置当前展示块缓存
    setWidgetCache: function(key, value) {
        var id = utils.widgetId();
        $('#' + id).data(key, value);
    },
    // 获得当前展示块缓存
    getWidgetCache: function(key) {
        var id = utils.widgetId();
        return $('#' + id).data(key);
    },
    // 移除当前缓存块缓存
    removeWidgetCache: function(key) {
        var id = utils.widgetId();
        return $('#' + id).removeData(key);
    },
    // 页面缓存
    cache: {},
    // 设置页面缓存
    setCache: function(key, value) {
        if (!key) return;
        utils.cache[key] = value;
    },
    // 获得页面缓存
    getCache: function(key) {
        if (!key) return null;
        return utils.cache[key];
    },
    // 移除页面缓存
    removeCache: function(key) {
        if (!key) return null;
        var v = utils.getCache(key);
        delete utils.cache[key];
        return v;
    },
    // 执行缓存函数
    excuteCache: function(key, param) {
        return utils.dfd(key, function(dfd) {
            var f = utils.getCache(key);
            if (utils.isFunction(f)) {
                f(param, dfd);
            }
        }, 'utils.excuteCache error');
    },
    // 是否存在缓存
    hasCache: function(key) {
        return !!utils.getCache(key);
    },
    getUser: function() {
        return utils.getCookie(utils.cookie.user);
    },
    setName: function(select) {
        // 替换用户名
        var user = utils.getCookie(utils.cookie.user);
        if (user && user.realName) {
            if ($(select).length > 0) {
                var n = user.realName;
                if (utils.isString($(select).attr('value'))) {
                    $(select).val(n);
                } else if (utils.isString($(select).text())) {
                    $(select).text(n);
                }
            }
            $(select).attr("title", user.realName);
        }
    },
    setIcon: function(select) {
        // 头像
        var user = utils.getCookie(utils.cookie.user);
        if (user && user.icon) {
            if ($(select).length > 0) {
                var i = user.icon;
                if (utils.isString($(select).attr('src'))) {
                    $(select).attr('src', i);
                } else {
                    $(select).css({
                        'background': 'url(' + i + ') no-repeat  50% 50%',
                        'background-size': 'cover'
                    });
                }
            }
        }
    },

    // ============================= 承诺模式 相关方法 ===============================
    // 生产一个承诺对象，当cond为true，调用指定resolve 函数，参数为 dfd；或 返回resolve对象
    //      当cond 为false，调用指定reject函数，参数为dfd；或返回reject对象
    dfd: function(cond, resolve, reject, timeout) {

        var dfd = $.Deferred();
        var rejected = false;
        var err = null;
        try {
            if (cond && !cond.stack) {
                if (resolve && typeof resolve === "function") {
                    resolve(dfd);
                } else {
                    dfd.resolve(resolve);
                }
            } else {
                rejected = true;
            }
        } catch (e) {
            rejected = true;
            err = utils.err(e);
        }
        if (rejected) {
            if (reject && typeof reject === "function") {
                try {
                    reject(dfd);
                } catch (e) {
                    dfd.reject(utils.err(e));
                }
            } else {
                if (!err) {
                    err = utils.err(reject);
                }
                dfd.reject(err);
            }
        }
        if (timeout) {
            setTimeout(function() {
                if (dfd.state() == 'pending') {
                    if (reject && typeof reject === "function") {
                        try {
                            reject(dfd);
                        } catch (e) {
                            dfd.reject(utils.err(e));
                        }
                    } else {
                        if (!err) {
                            err = utils.err(reject);
                        }
                        dfd.reject(err);
                    }
                    //dfd.reject(utils.err('dfd timeout'));
                }
            }, timeout);
        }
        return dfd.promise();
    },
    // 对承诺数组的操作，可以按照相同顺序传递参数数组
    dfds: function(pa, args, noReject) {
        var dfd = $.Deferred();
        this.nestDfds(dfd, pa, args, noReject);
        return dfd.promise();
    },
    // 对一组数据对象遍历执行承诺数组操作
    dfdFor: function(array, pa, args) {
        if (utils.isEmpty(array)) return utils.err('dfdFor array param is not Array or is Empty');
        if (!pa) return utils.err('dfdFor param pa is null');

        var dfd = $.Deferred();
        var result = new Array();
        var length = array.length;
        for (var i = 0; i < length; i++) {
            var obj = array[i];
            if (utils.isNotEmpty(pa)) {
                var tpa = pa.slice(0);
                var pal = tpa.length;
                var nargs;
                // 合并当前对象到参数中
                if (utils.isNotEmpty(args)) {
                    var ta = args.slice(0);
                    nargs = new Array();
                    for (var j = 0, l = args.length; j < l; j++) {
                        nargs[j] = {
                            arg: ta[j],
                            param: obj
                        };
                    }
                    // 新建当前参数
                } else {
                    nargs = new Array();
                    for (var j = 0; j < pal; j++) {
                        nargs[j] = obj;
                    }
                }
                utils.dfds(tpa, nargs).done(function(data) {
                    result[result.length] = data;
                }).fail(function(err) {
                    result[result.length] = err;
                }).always(function() {
                    if (result.length >= length) {
                        dfd.resolve(result);
                    }
                });
            } else if (pa) {
                pa(obj).done(function(data) {
                    result[result.length] = data;
                }).fail(function(err) {
                    result[result.length] = err;
                }).always(function() {
                    if (result.length >= length) {
                        dfd.resolve(result);
                    }
                });
            } else {
                dfd.reject(utils.err('dfdFor param pa is not dfd or dfds'));
                break;
            }
        }
        return dfd.promise();
    },
    // 如果一组承诺执行完毕，返回承诺状态
    dfdFinish: function(dfd, pa) {
        var count = 0;
        var result = new Array();
        var l = pa.length;
        for (var i = 0; i < l; i++) {
            pa[i].done(function(data) {
                result[result.length] = data;
            }).fail(function(err) {
                dfd.reject(err);
            }).always(function() {
                count++;
                if (count >= l) {
                    dfd.resolve(result);
                }
            });
        }
    },
    // 对承诺数组的操作，可以按照相同顺序传递参数数组
    nestDfds: function(dfd, promiseArray, args, noReject) {
        if (!dfd || !utils.isArray(promiseArray)) {
            if (dfd) dfd.reject(utils.err("dfds pa not Array"));
            return;
        }
        var wrap = promiseArray[0];
        var p;
        if (utils.isArray(args)) {
            if (promiseArray.length != args.length) {
                dfd.reject(utils.err("pa.length not equal args.length"));
                return;
            }
            p = wrap(args[0]);
            args.splice(0, 1);
        } else {
            p = args ? wrap(args) : wrap();
            args = null;
        }
        promiseArray.splice(0, 1);
        if (promiseArray.length === 0) {
            p.done(function(data) {
                dfd.resolve(data);
            }).fail(function(err) {
                dfd.reject(err);
            });
        } else {
            this.recursive(dfd, p, promiseArray, args, noReject);
        }
    },
    // 递归承诺数组，完成一次截取数组第一个元素
    recursive: function(dfd, p, pa, params, noReject) {
        var args = arguments;
        p.done(function(data) {
            var pn = pa[0];
            if (!pn) return;
            var param;
            if (params && params[0]) {
                param = params[0];
                params.splice(0, 1);
            }
            pa.splice(0, 1);
            if (pa.length <= 0) {
                pn(data, param).done(function(data) {
                    dfd.resolve(data);
                }).fail(function(err) {
                    dfd.reject(err);
                });
            } else {
                args.callee(dfd, pn(data), pa, params);
            }
        }).fail(function(err) {
            var pn = pa[0];
            if (!pn) return;
            var param;
            if (params && params[0]) {
                param = params[0];
                params.splice(0, 1);
            }
            pa.splice(0, 1);
            if (pa.length <= 0) {
                pn(err, param).done(function(data) {
                    dfd.resolve(data);
                }).fail(function(err) {
                    dfd.reject(err);
                });
            } else if (!noReject) {
                args.callee(dfd, pn(), pa, params);
            }
        });
    },
    // ============================= 数据 相关方法 ===============================
    // 对错误信息的操作
    err: function(msg, code) {
        var err = null;
        if (msg && msg.stack) {
            err = msg;
            msg = null;
        } else {
            try {
                throw Error('');
            } catch (e) {
                err = e;
            }
        }
        var obj = {
            "msg": msg || "UNKNOW ERROR",
            "code": code || "debug",
            "stack": err.stack
        };
        if (typeof msg !== 'string') msg = JSON.stringify(obj.msg);
        var r = "{msg: " + msg + "\n code: " + obj.code + "\n stack: " + obj.stack;
        try {
            console.error(r);
        } catch (e) {}
        return obj;
    },
    isString: function(obj) {
        return typeof obj === 'string';
    },
    isObject: function(obj) {
        return !utils.isArray(obj) && typeof obj === 'object';
    },
    isArray: function(obj) {
        return obj instanceof Array;
    },
    isFunction: function(obj) {
        return typeof obj == 'function';
    },
    // 判断一个对象是否为空
    isEmpty: function(obj) {
        if (utils.isArray(obj)) {
            return obj.length == 0;
        } else if (obj instanceof Object) {
            var l = 0;
            for (var k in obj) {
                l++;
            }
            return l == 0;
        }
        return !!!obj;
    },
    // 判断一个对象是非空的
    isNotEmpty: function(obj) {
        return !utils.isEmpty(obj);
    },
    // ============================= 页面事件 相关操作   ===============================
    // 对于回车的处理
    keyEnter: function(select, func) {
        var ips = $(select).find('input:checked,input:selected,[name][type!=checkbox][type!=radio]');
        var l = ips.length;
        if (l < 1) {
            ips = $(select);
            l = 1;
        }
        ips.each(function(i) {
            var end = false;
            if (i == ips.length - 1) end = true;
            $(this).attr({
                'enter_index': i,
                'enter_end': end
            }).keyup(function(e) {
                if (13 == e.keyCode) {
                    var e = $(this).attr('enter_end');
                    var ei = parseInt($(this).attr('enter_index'));
                    if (e == 'true') {
                        func();
                    } else {
                        $(select + ' [enter_index="' + (ei + 1) + '"]')[0].focus();
                    }
                }
            });
        });
    },
    eachEvents: function(data) {
        if (!data) return;
        for (var k in data) {
            var emap = data[k];
            for (var l in emap) {
                $('body').on(l, k, emap[l]);
            }
        }
    },
    eachValid: function(val, validFuncs) {
        if (!validFuncs) return '';
        for (var i in validFuncs) {
            var ret = validFuncs[i](val);
            if (ret) return ret;
        }
        return '';
    },
    eachResult: function(data) {
        if (!data) return;
        var r = true;
        for (var k in data) {
            var ret = $(k).data('result');
            if (ret) {
                r = false;
                utils.remind($(k), ret);
            }
        }
        return r;
    },
    getUrlParam: function(name) {
        var reg = new RegExp("(^|&|\\?)" + name + "=([^&]*)(&|$)");
        var h = window.location.search;
        if (!h) h = window.location.href;
        else h = h.substr(1);
        var r = h.match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

});

var util = new Util();
window.utils = util;
utils.ready();
