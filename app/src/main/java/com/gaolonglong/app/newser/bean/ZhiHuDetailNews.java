package com.gaolonglong.app.newser.bean;

import java.util.List;

/**
 * Created by gaohailong on 2016/12/7.
 */

public class ZhiHuDetailNews {

    /**
     * body : <div class="main-wrap content-wrap">
     <div class="headline">

     <div class="img-place-holder"></div>



     </div>

     <div class="content-inner">




     <div class="question">
     <h2 class="question-title">巨型的轮船建造的时候是由很多大型模块拼接起来的吗？如何保证接缝处的密闭性能？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic1.zhimg.com/da8e974dc_is.jpg">
     <span class="author">知乎用户，</span><span class="bio">我的志愿，是做一个校长</span>
     </div>

     <div class="content">
     <p>船舶建造流程需要甲方、乙方和第三方独立检验机构（船级社）共同监督完成。而在船舶成为海洋结构物正式服役直到寿终正寝拖到拆船厂拆掉之前，第三方独立检验机构会一直对船舶质量负责，保障船员的生命安全和防止环境污染。</p>
     <p><strong>按照既定的合同约定标准检验是第三方检验机构的主要工作内容。</strong></p>
     <p>这句话先说出来防止撕逼。</p>
     <p>船舶建造流程杨老师已经说得很全面了，那我针对题主的问题补充几点吧。</p>
     <p>各家船级社规范大同小异，那么我选取最具代表性的 IACS（国际船级社协会）的强制要求 <a href="http://www.iacs.org.uk/document/public/Publications/Unified_requirements/PDF/UR_S_pdf158.PDF">URS14</a> 来说一说吧。</p>
     <p>根据这个要求，船舶密性主要分成以下几个部分：</p>
     <p><strong>Hydrostatic test</strong>：水压试验，也叫舱强试验。这个试验是通过对有密性要求的密闭舱室进行灌水并达到一定压强之后来达到验证密性的目的，相关要求有两个表格，这里就不引用了。基本上就是超过舱顶两米四或者溢流管的高度（取大者）。同时，这个试验也是验证船体局部结构强度的一个重要手段。在我的工作生涯中，确实遇到过压完水整个舱壁变形的情况，看着挺吓人的。</p>
     <p>请注意这个试验应该使用淡水或者海水，油类物质是不行的。</p>
     <p><strong>Hydropneumatic test：</strong>液气混合压力试验。这个试验基本不会做，以现在的技术手段也没这个必要，属于脱裤子放屁的东西。这里就不多说了。</p>
     <p><strong>Hose test：</strong>冲水试验。这个试验一般用于需要开闭的设施密性验证，例如门窗。要求是 2 公斤的压力，水流出口管径不小于 12 毫米，距离不超过 1.5 米。如果有些位置比较特殊不能进行冲水试验的话，例如有些窗户很小人不能到反面去验证是否漏水，又或者有些重要的机器设备需要保护的情况下，则可以使用目视检验同时增加一些必要的探伤手段以及超声波验证来保障密性。</p>
     <p><img class="content-image" src="http://pic2.zhimg.com/70/6fef4888079bcb3a7d4ba6cb136f41d5_b.jpg" alt="" /></p>
     <p><strong>Tank air test：</strong>舱室密性试验。这个是最常见的了。相关舱室合拢结束后，对这个舱室加注空气增加压力，这个压力一般不超过 0.15 公斤（以前是 0.2 公斤）。通过压力表以及 U 型管（针对 U 型管还有一些乱七八糟的要求这里就不说了。）来检查压力是否符合要求。然后一般用肥皂水喷在焊缝表面，剩下的就是看会不会冒泡了。</p>
     <p>有些船级社会有额外的放宽条件，例如很多船级社就允许自动焊和半自动焊在仔细的目视检验的前提下可以不做这种密性试验。</p>
     <p><strong>Compressed air fillet weld test：</strong>角焊缝充气试验。这个试验就比较新鲜了，几年前中国只有那几家有数的国企大船厂和外资船厂做到，另外一些优秀的民营船厂近几年也可以做到了。这个试验的优点是方便快捷，不需要大范围的检验，对后续施工的影响（例如 PSPC 专用海水压载舱的涂层保护）很小。但是缺点就是对装配精度要求很高，所以经常导致试验失败。</p>
     <p>这里多说几句，所谓 Fillet Welding， 也就是角焊缝，是允许不作为全焊透（Full Penetration）焊缝处理的。或者你可以理解成这种焊缝允许&ldquo;内部缺陷&rdquo;（这种表达十分不准确但是你明白这个意思就行了。）所以在这种焊缝上，上述的密性试验手段就可以验证密性了。具体办法是在整条焊缝的一端充气，另外一端安装一个压力表以验证焊缝通畅。然后在焊缝表面喷上一层肥皂水就可以看是不是冒泡泡了。</p>
     <p>后来有些船厂为了保证每个焊缝试验都能成功，还会想出在焊缝里加根铜管的办法。当然被甲方或者我们发现以后各种虐，现在很少出现这种情况了。</p>
     <p><strong>Vacuum box test：</strong>真空试验。这是一种聚酯纤维的盒子，基本透明，用久了变成半透明。通过空气泵把内部空气抽走，人为制造出一定的压力来验证局部焊缝的密性。这试验看的时候很累，冬天出去经常冻成狗。还要小心盒子会炸，上次还把一哥们安全帽打了个窟窿。</p>
     <p><strong>Ultrasonic test：</strong>超声波试验。这个试验相当少见，一般用于验证舱口盖的水密性能的时候才会用到。具体原理就不说了（除非你问我）。特点是方便快捷，不耽误时间，准确率高。例如海洋 981 那么多门都是超声波做的水密性能测试。</p>
     <p><strong>Penetration test：</strong>渗透试验。这个试验上个世纪 8、90 年代在中国十分流行。他的原理是利用一些液体（例如煤油）的低表面张力渗透焊缝的特性，在反面涂上一层石灰粉，来验证焊缝是否水密。这个试验很容易误判，反应也慢，慢慢的就被淘汰了（当然对于船厂来说特别喜欢做，因为省事儿）。2010 年左右的时候很多船级社明确表示不接受这种试验。URS 这一版不知道为什么又加进去了。</p>
     <p><strong>Other test：</strong>其他试验。能不能做，怎么做，取决于船级社。</p>
     <p>以上试验各有不同，目的基本大同小异。都是验证船舶在各种工况下是否能保证水密性能的有效手段。随着技术手段和材料特性的不断进步，这部分的规范（也包括其他的部分）越来越宽泛，我个人认为终究会退出船舶检验标准。</p>
     <p>船舶建造流程也是不断发展的。以往的手段，是整体建造法。整条船由小到大，先铺龙骨，再做结构，最后贴外板。这种方法工艺复杂，流水线化程度不高，精度控制困难。现在只有浙江等地一些小船厂这么做了。</p>
     <p>现在的建造流程，是继承自韩国和日本，叫做模块化建造，也叫分段。</p>
     <p>海洋结构物和建筑物区别很大，唯一有共通之处的也就是钢结构部分了。然而建筑物的钢结构更多的是型材焊接，而船是板材类的薄壁结构。一般来说，船体结构中为数不多的铸锻造结构都在船尾附近，例如尾轴铸钢件，舵叶铸钢件等等，传动装置的轴系部分更多应用锻件。采用什么材料，要看具体的需求，设计各个方面。目前在造的郑和宝船，船尾部分采用的是钢木结合的材料&mdash;&mdash;也就是铁梨木船尾 + 钢制轴系。</p>
     <p>至于你说的&ldquo;船身肯定不是一次浇筑成形，也不可能是很多很小的块拼起来，所以我有把握相信是由很多大型模块拼接成的。&rdquo;这句话，说对了三分之二，分段的内部结构还真的是很多小块拼起来的。船舶作为海洋结构物，大部分是在运动状态的，同时本身所在的环境也要求船体钢结构对海洋环境这种不稳定力学结构有所倾斜，同时还要兼顾经济性，通过设计线型来降低油耗以及外观美学等等各方面的考虑。海洋环境十分复杂，对钢结构的应力传递以及金属疲劳性能都有一些特殊的要求。另外船舶很少是设计一个建造一个，一般都是一套图纸可以造很多系列船，然后具体每条船有一些个性化的需求设计。例如 2010 年左右的时候，上海船舶设计研究院一套 57000BC 的图纸卖了 400 多套，最终完成建造的也有 300 多条船。</p>
     <p>以上。</p>
     <p><a href="http://www.zhihu.com/people/harrypotterthesavior">Potter Harry</a> 的疑问我更新在这里。</p>
     <p><img class="content-image" src="http://pic2.zhimg.com/70/80443a414a3a4668e383bbef8a8c2441_b.jpg" alt="" /></p>
     <p>在船体结构上，常见的焊缝，一般分为对接缝（butt welding）和角焊缝（fillet welding）这两种。对接缝要求全焊透（full penetration）形式，角焊缝可以有普通的双面连续焊，深熔焊（deep penetration）和全焊透三种。</p>
     <p>船厂在施工之前，需要准备焊接工艺（WPS）&mdash;&mdash;船级社一般称为 WPQ，也就是 welding procedure qualification。简单来说就是焊接方法。</p>
     <p>这里面包括的内容有以下几个方面：母材金属（普通钢，高强钢，铸钢，厚度），焊材牌号（特性），焊接种类（二氧化碳气体保护焊，自动焊，手工焊等等），焊接方式（平焊，立焊，横焊，仰焊），焊接参数（电流电压热输入量等等），还有坡口形式以及其他一些要求。</p>
     <p>WPS 的工艺评定需要船级社的认可（我们会现场见证他们的试验）。整个过程中评定他们是否按照既定的要求去做，比如打底焊结束有没有清洁焊缝啊这些。</p>
     <p>然后，焊接结束。首先是目视检验，检查焊缝有没有表面缺陷，气孔夹渣表面裂纹等等；然后我们会给符合要求的试件敲上钢印，防止他们作假；再接着是无损探伤，包括超声波或者射线，在这个基础上可以补充一些表面探伤；后面的是机械性能试验，也就是破坏性试验，分别有拉伸，冲击，弯曲，折断；仅仅针对冲击试验，我们就会取样焊缝中心，熔合线，热影响区（熔合线 +2mm，+5mm，+10mm）来满足不同焊接方式的不同试验要求。</p>
     <p>以上这些都有相关的规范支持。同时有范围的接受其他船级社的焊接工艺评定。</p>
     <p>这个做完了，然后开始给焊工考试。焊工等级不同，可以操作的焊接方式也不同。我们会全程见证焊工考试的整个过程，然后重复上面的试验（具体要求会有细微差别）。</p>
     <p>还没完，对于焊接材料，我们同样对于焊材的生产厂家有要求。但是这一块相关的检验我没有做过，所以就没办法说的太多了。所有的认可厂家在系统内都有据可查。</p>
     <p>而在平时的建造过程中，我们会不定期的巡检，检查焊材牌号是否符合，检查焊工的焊工证，检查施工过程中是否有不符合规范要求的地方。</p>
     <p>比如你会看到这种：</p>
     <p><img class="content-image" src="http://pic2.zhimg.com/70/9e690cf7d81796f76dcbc4bc49e478b5_b.jpg" alt="" /></p>
     <p>这种：</p>
     <p><img class="content-image" src="http://pic2.zhimg.com/70/6525c7bfda3f55d95a1dfb38fb7bf6b1_b.jpg" alt="" /></p>
     <p>这些都会按照我们的要求返工并且增加相关的无损探伤。</p>
     <p>然后在分段和合拢（搭载）阶段，我们同样也会检查所有焊缝的质量（合拢焊前和焊后我们都会检验，有些船级社则只看焊后），还有按照图纸（退审过的）检查结构是否缺失。</p>
     <p>船厂还会提供一份无损探伤布置图，同样需要我们的审核。并且我们还会看情况增加 10% 的探伤位置，并且复核每一张射线探伤的 x 光片。</p>
     <p>所以你还担心焊缝质量问题吗？</p>
     <p>以上描述仅针对我公司入级船舶，其他的我不知道。</p>
     </div>
     </div>


     <div class="view-more"><a href="http://www.zhihu.com/question/27127883">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>


     </div>
     </div>
     * image_source : Lyn Gateley / CC BY
     * title : 「大轮船造好了，我们试试看会不会漏水吧」
     * image : http://pic3.zhimg.com/3b723612dfbf83dccedd250c8c603d0e.jpg
     * share_url : http://daily.zhihu.com/story/7329697
     * js : []
     * ga_prefix : 120711
     * images : ["http://pic2.zhimg.com/1443f49e428212a605c4bce58e56461d.jpg"]
     * type : 0
     * id : 7329697
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private int id;
    private List<?> js;
    private List<String> images;
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<?> getJs() {
        return js;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
