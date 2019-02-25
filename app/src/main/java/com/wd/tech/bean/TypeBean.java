package com.wd.tech.bean;

import java.util.List;

/**
 * 作者：夏洪武
 * 时间：2019/2/22.
 * 邮箱：
 * 说明：
 */
public class TypeBean {

    /**
     * result : [{"id":1,"name":"电商消费","pic":"http://172.17.8.100/images/tech/plate/dsxf.png"},{"id":2,"name":"区块链","pic":"http://172.17.8.100/images/tech/plate/qkl.png"},{"id":3,"name":"AI世界","pic":"http://172.17.8.100/images/tech/plate/AIsj.png"},{"id":4,"name":"人工智能","pic":"http://172.17.8.100/images/tech/plate/rgzn.png"},{"id":5,"name":"车与出行","pic":"http://172.17.8.100/images/tech/plate/cycx.png"},{"id":6,"name":"智能终端","pic":"http://172.17.8.100/images/tech/plate/znzd.png"},{"id":7,"name":"金融地产","pic":"http://172.17.8.100/images/tech/plate/jrdc.png"},{"id":8,"name":"大数据","pic":"http://172.17.8.100/images/tech/plate/dsj.png"},{"id":9,"name":"社交通讯","pic":"http://172.17.8.100/images/tech/plate/sjtx.png"},{"id":10,"name":"全球热点","pic":"http://172.17.8.100/images/tech/plate/qqrd.png"}]
     * message : 查询成功
     * status : 0000
     */


        /**
         * id : 1
         * name : 电商消费
         * pic : http://172.17.8.100/images/tech/plate/dsxf.png
         */

        private int id;
        private String name;
        private String pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

}
