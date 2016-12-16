package com.gaolonglong.app.newser.bean;

import java.util.List;

/**
 * Created by gaohailong on 2016/12/16.
 */

public class WeiXinNews {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-12-16","title":"如果美图倒闭，朋友圈的那些脸怎么办？","description":"脉脉互联网","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/0OMNHCiaJ1b8cVAf8uSb6ibc9Sngyqor9kwDCcFuT3fu5yeicqxZkicQvUnv0JMGRNh4UicZAaD3BVm5UCY7UUZPhNw/0?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/CN-T4f6FjS3Xj08Wt0kRYA"},{"ctime":"2016-12-16","title":"年底了，别忘了这6件事！有些后果你承担不起\u2026","description":"人民日报","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/xrFYciaHL08DiawJiauLuyCWpJ48RVWkvbxnIv2D6qhn5SWNgKXHtvlPoe7L9w13v5ODHg9AZn0KeXtEIfUicicmnNg/0?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/LZSbn_eDUrtKY_vukxAldQ"},{"ctime":"2016-12-16","title":"麦当劳哭了，新款咖啡杯被网友疯狂嘲讽\u2026\u2026","description":"淘金咖","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/8BPYqUd8P9oGDRWwrY2ic49r3XZSdKn5j6Zgia8uydXVzvDrhHvb3aiafOMyicO4wta8bLHsmARvW973t10wWUWgsQ/0?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/OFfBc0XeLVia2NMhaKvfAA"},{"ctime":"2016-12-16","title":"有个做新媒体的女朋友是怎样一种体验？","description":"新媒体课堂","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/xe4FY1hGDmre5UsnxF0wwViaxsXfYMkQibDBrhZIFFDu17hQwpicYK2Plrqtiayf8mH89bSCVibJHnVttKvHLpqht8w/0?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/mygWgJega5ddqXfq52-yfQ"},{"ctime":"2016-12-15","title":"创始人少女丹被投资人踢出局，有四个真相摆在你们面前","description":"触电报","picUrl":"http://mmbiz.qpic.cn/mmbiz_png/nic2wWnqFK5QYu0Nmzib5NnJNsS1nCrjD97u0efw84qoRnM3WXw9KQN4ehdxGljAjR4JBfsEknzTw3rF8NEzvFEA/0?wx_fmt=png","url":"http://mp.weixin.qq.com/s/QTkDUiQbb4vt-ta3mLNVjw"},{"ctime":"2016-12-15","title":"青春的正确打开方式","description":"腾讯","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/jhgibzXKDS2VFAwTicXTia8rXO7a1Uf6eJxcTzE2ibiaIYYYUP8DiaxSY9f3HQQACrOFewPWMNQkA8AgM0bhEnicMNr1Q/0?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/Hqp0BIlEJyB0ShxrJynduw"},{"ctime":"2016-12-15","title":"中国最孤独图书馆的建筑师，又出神作！","description":"一条","picUrl":"http://mmbiz.qpic.cn/mmbiz_png/VXm8w4GIZRqHibZTugRXerEM9dajO1oG4XkPBnhpNBaU822jpoM9KCicZXFicnAgibrncaiaYQNm4mvZGm9NxtuQDIQ/s640","url":"http://mp.weixin.qq.com/s/cRIzO25cEghYp6bAPDnGGQ"},{"ctime":"2016-12-15","title":"谢谢美国，告诉中国人民这个好消息","description":"人民日报","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/wZ8jfcVwf7IibiaqvXRiaw8sDQicvtrOlF0TbCia9PGjMsEAJqCy8iah3K94ibOSGSBr9tRthPPMPTKUticK3zbWxicXXLA/640?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/DPH-rckORas4rYMk_LiZAg"},{"ctime":"2016-12-15","title":"体验谷歌地球VR之后想哭！整个世界尽在眼中","description":"中国大数据","picUrl":"http://mmbiz.qpic.cn/mmbiz_gif/5TaxoSVpH1UQibLVA4H81SvxBoJNjO6yTmSib1kHYBdmsYOy2suib0qg21rnkjqXBlq2KskgaFEEyZTy9898KdGIw/s640","url":"http://mp.weixin.qq.com/s/YbAAcjcuKLPkGRDqzBF_Vw"},{"ctime":"2016-12-15","title":"一个开照相馆的美国人 记录下北京百年旧影 | 在线影展","description":"腾讯图片","picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/tWhD6YJteqm5EydDIOuaz1DTUFGEbibiaBalib2j77H7jnxNJEaU7I3IeGbDNk3cA7KZr3VO5RdS6Qib1ouJINicx9g/0?wx_fmt=jpeg","url":"http://mp.weixin.qq.com/s/e_DCSAFnNe1zeavTrKPADg"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * ctime : 2016-12-16
         * title : 如果美图倒闭，朋友圈的那些脸怎么办？
         * description : 脉脉互联网
         * picUrl : http://mmbiz.qpic.cn/mmbiz_jpg/0OMNHCiaJ1b8cVAf8uSb6ibc9Sngyqor9kwDCcFuT3fu5yeicqxZkicQvUnv0JMGRNh4UicZAaD3BVm5UCY7UUZPhNw/0?wx_fmt=jpeg
         * url : http://mp.weixin.qq.com/s/CN-T4f6FjS3Xj08Wt0kRYA
         */

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
