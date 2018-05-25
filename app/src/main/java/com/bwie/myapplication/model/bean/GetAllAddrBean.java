package com.bwie.myapplication.model.bean;

import java.io.Serializable;
import java.util.List;

public class GetAllAddrBean implements Serializable {

    /**
     * msg : 地址列表请求成功
     * code : 0
     * data : [{"addr":"asdasd","addrid":2,"mobile":1316551040,"name":"李培","status":0,"uid":71},{"addr":"北京市昌平区","addrid":3,"mobile":123,"name":"边走边爱","status":1,"uid":71},{"addr":"???","addrid":4,"mobile":18612991023,"name":"??","status":0,"uid":71},{"addr":"北京市","addrid":5,"mobile":18612991023,"name":"清晨","status":0,"uid":71},{"addr":"NININ","addrid":7,"mobile":14343434,"name":"NOGNEGO","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":15,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":16,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":17,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":18,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":19,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":20,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":21,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":22,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":23,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":24,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":26,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":27,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":28,"mobile":18612991023,"name":"kson0","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":29,"mobile":18612991023,"name":"kson0","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":30,"mobile":18612991023,"name":"kson147","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":31,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":39,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":42,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":43,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":50,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":51,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":52,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":53,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":59,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":61,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":64,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":73,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区1-1-1","addrid":74,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":78,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市哈哈哈啊哈哈","addrid":83,"mobile":2222333333,"name":"哈哈哈哈","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":95,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":98,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":99,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":100,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":101,"mobile":18612991023,"name":"kso","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":104,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":107,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":111,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":112,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":113,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":120,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":135,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":137,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":139,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":140,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":141,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":142,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":145,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":146,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":149,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":458,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":459,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市哈哈哈啊哈哈","addrid":460,"mobile":2222333333,"name":"哈哈哈哈","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":461,"mobile":18612991023,"name":"王帆kson=1234","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":463,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":465,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":488,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":489,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":503,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":504,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":509,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":510,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":511,"mobile":18612991023,"name":"liubb","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":512,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":518,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":523,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":526,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":527,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":528,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":529,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":530,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":537,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":543,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":546,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":548,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":556,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":567,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":568,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":569,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":593,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":618,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":627,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":628,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":638,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":649,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":658,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":659,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":660,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":661,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":662,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":663,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":664,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":666,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":669,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北 京 市 昌 平 区 金 域 国 际 1-1-1","addrid":670,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":673,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":674,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":675,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":676,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":677,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":678,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":681,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":700,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":711,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":712,"mobile":18612991023,"name":"1","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":713,"mobile":18612991023,"name":"2","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":714,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":715,"mobile":18612991023,"name":"4407","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":716,"mobile":18612991023,"name":"4402","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":717,"mobile":18612991023,"name":"18612991023","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":718,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":723,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":727,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":728,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":729,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":738,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":739,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":744,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":745,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":746,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":747,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":748,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金8888","addrid":749,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金8888","addrid":750,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":751,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":753,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":754,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":755,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":756,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":758,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":759,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":760,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":761,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":762,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":763,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":766,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":767,"mobile":17610935259,"name":"123456","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":775,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":783,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":786,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":805,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京海淀","addrid":807,"mobile":13691513530,"name":"张鹏飞","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":811,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":814,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":822,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":824,"mobile":18612991023,"name":"kson822","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":825,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":826,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":840,"mobile":18612991023,"name":"kson822","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":844,"mobile":18612991023,"name":"kson822","status":0,"uid":71},{"addr":"�����в�ƽ�������1-1-1","addrid":860,"mobile":18612991023,"name":"kson822","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":861,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":863,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":875,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":878,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":886,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":888,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":889,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":890,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":892,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":893,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":897,"mobile":18612991023,"name":"kson","status":0,"uid":71},{"addr":"北京市昌平区金域国际1-1-1","addrid":912,"mobile":18612991023,"name":"kson","status":0,"uid":71}]
     */

    private String msg;
    private String code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * addr : asdasd
         * addrid : 2
         * mobile : 1316551040
         * name : 李培
         * status : 0
         * uid : 71
         */

        private String addr;
        private int addrid;
        private long mobile;
        private String name;
        private int status;
        private int uid;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getAddrid() {
            return addrid;
        }

        public void setAddrid(int addrid) {
            this.addrid = addrid;
        }

        public long getMobile() {
            return mobile;
        }

        public void setMobile(long mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
