import ado.HBaseDao;
import constants.Constants;
import utils.HBaseUtil;

import java.io.IOException;

public class TestWeiBo {
    public static void init(){
        try {
            //  创建命名空间
            HBaseUtil.createNameSpace(Constants.NAMESPACE);
            //  创建用户关系表
            HBaseUtil.createTable(Constants.CONTENT_TABLE, Constants.CONTENT_TABLE_VERSIONS, Constants.CONTENT_TABLE_CF);
            //  创建用户关系表
            HBaseUtil.createTable(Constants.RELATION_TABLE, Constants.CONTENT_TABLE_VERSIONS,
                                    Constants.RELATION_TABLE_CF1, Constants.RELATION_TABLE_CF2);
            //  创建收件箱表
            HBaseUtil.createTable(Constants.INBOX_TABLE, Constants.INBOX_TABLE_VERSIONS, Constants.INBOX_TABLE_CF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        //  初始化
        init();
        //  1001发布微博
        HBaseDao.publishWeibo("1001", "1001的第1条微博");
        //  1002关注1001和1003
        HBaseDao.addAttends("1002", "1001","1003");
        //  获取1002初始化页面
        HBaseDao.getInit("1002");

        System.out.println("==========================111==========================");

        //  1003发布3条微博，同时1001发布2条微博
        HBaseDao.publishWeibo("1003", "1003的第1条微博");
        Thread.sleep(50);
        HBaseDao.publishWeibo("1001", "1001的第2条微博");
        Thread.sleep(50);
        HBaseDao.publishWeibo("1003", "1003的第2条微博");
        Thread.sleep(50);
        HBaseDao.publishWeibo("1001", "1001的第3条微博");
        Thread.sleep(50);
        HBaseDao.publishWeibo("1003", "1003的第3条微博");
        Thread.sleep(50);
        //  获取1002初始化页面
        HBaseDao.getInit("1002");

        System.out.println("==========================222==========================");


        //  1002取关1003
        HBaseDao.deleteAttends("1002", "1003");

        //  获取1002初始化页面
        HBaseDao.getInit("1002");

        System.out.println("===========================333===========================");

        //  1002再次关注1003
        HBaseDao.addAttends("1002", "1003");

        //  获取1002初始化页面
        HBaseDao.getInit("1002");
        System.out.println("==========================444============================");


        //  获取1001微博详情
        HBaseDao.getWeiBo("1001");


    }
}
