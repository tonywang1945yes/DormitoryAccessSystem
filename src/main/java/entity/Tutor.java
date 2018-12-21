package entity;

import org.apache.poi.ss.usermodel.Row;
import org.omg.PortableInterceptor.INACTIVE;


import java.util.ArrayList;
import java.util.List;

public class Tutor extends People {
    private String institute;
    private String name;
    private String grade;
    private String emailAddress;


    /**
     * 通过传入某一行来构造一个Tutor对象
     * @param row
     */
    public Tutor(Row row){
        String grade = row.getCell(1).toString();
        if (grade.endsWith(".0")){
            grade = grade.substring(0, grade.length() - 2);
        }
        this.grade = grade;
         String institute= row.getCell(0).toString();
         switch(institute){
             case "文学院":institute="文学院，语言学系，文献学系，戏剧影视艺术系，大学语文部";break;
             case"历史学院":institute="历史学院，中国历史系，世界历史系，考古文物系";break;
             case"哲学系":institute="哲学院，哲学系";break;
             case"哲学院":institute="哲学院，哲学系";break;
             case"新闻传播学院":institute="新闻传播学院，新闻传播系，新闻与新媒体系,广播电影电视系";break;
             case"法学院":institute="法学院";break;
//             case"商学院":break;
             case"经济学院":institute="经济学院，经济学系，国际经济贸易系，金融与保险学系，产业经济学系，人口研究所";break;
             case"管理学院":institute="管理学院，工商管理系，会计学系，营销与电子商务系，人力资源管理学系";break;
             case"外国语学院":institute="外国语学院，英语系，俄语系，日语系，德语系，法语系，西班牙语系，朝鲜语系，国际商务系";break;
             case"政府管理学院":institute="政府管理学院，政治学系，行政管理学系，劳动人事与社会保障系";break;
//             case "信息管理学院":institute="信息管理学院";break;
             case"社会学院":institute="社会学院，社会学系，心理学系，社会工作与社会政策系，何仁社会慈善学院";break;
//             case"数学系":break;
             case"物理学院":institute="物理学院，现代物理系，光电科学系，声科学与工程系，基础物理教学中心";break;
//             case"天文与空间科学学院":break;
//             case"化学化工学院":break;
             case"计算机科学与技术系":institute="计算机科学与技术系，人工智能学院";
             if(Integer.parseInt(this.grade)>2017){
                 institute="计算机科学与技术系";//人工智能学院的独立
             }break;
             case"电子科学与工程学院":institute="电子科学与工程学院，电子工程系，微电子与光电子学系，信息电子学系，通信工程系，电子电工实验教学中心，微制造与集成工艺中心";break;
             case"现代工程与应用科学学院":institute="现代工程与应用科学学院，材料科学与工程系，量子电子学与光学工程系，生物医学工程系，能源科学与工程系";break;
             case"环境学院":institute="环境学院，环境科学系，环境工程系，环境规划与管理系";break;
             case"地球科学与工程学院":institute="地球科学与工程学院，地球科学系，水科学系，地质工程与信息技术系";break;
             case"地理与海洋科学学院":institute="地理与海洋科学学院，国土资源与旅游学系";break;
             case"大气科学学院":institute="大气科学学院，气象学系，大气物理学系";break;
             case"生命科学学院":institute="生命科学学院，生物科学与技术系，生物化学系";break;
//             case"医学院":break;
             case"工程管理学院":institute="工程管理学院，管理科学与工程系，控制与系统工程系，光通信工程研究中心，管理-控制-一体化实验教学中心";break;
//             case"匡亚明学院":break;
//             case"海外教育学院":break;
//             case"软件学院":break;
             case"建筑与城市规划学院":institute="建筑与城市规划学院，建筑系，撑死与区域规划系";
             case"马克思主义学院":break;
             case"艺术学院":break;
             default:break;
         }
        this.institute=institute;


        this.name = row.getCell(2).toString();
        if (checkValid(row.getCell(3).toString())){
            this.emailAddress = row.getCell(3).toString();
        }
        else {
            this.emailAddress = "";
        }
    }

    /**
     * 检查Email地址是否是合法的
     * @param address
     * @return
     */
    private boolean checkValid(String address){
        if (address == null || address.length() == 0 || address.length() > 30){
            return false;
        }
        if (!address.contains("@") || !address.contains(".")){
            return false;
        }
        if (address.lastIndexOf("@") > address.lastIndexOf(".")
                || address.charAt(address.length() - 1) == '.'){
            return false;
        }
        char capital = address.charAt(0);
        if (!(capital == '_'
                || (capital >= 'a' && capital <= 'z')
                || (capital >= 'A' && capital <= 'Z')
                || (capital >= '0' && capital <= '9'))){
            return false;
        }
        return true;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public String getInstitute() {
        return institute;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
