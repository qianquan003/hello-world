package com.xi.memcache;
import java.util.Date;
import java.util.List;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
public class MemcachedManager {
 
   // ����MemCachedClientȫ�ֶ���
    private static MemCachedClient mcc = new MemCachedClient();
    
    static {
       // �����������б���Ȩ��
       String[] servers = {"127.0.0.1:11211"};
       Integer[] weights = {3};
       
       // ����Socket���ӳض���
       SockIOPool pool = SockIOPool.getInstance();
       
       // ���÷�������Ϣ
       pool.setServers(servers);
       pool.setWeights(weights);
       pool.setFailover(true);
       
       // ���ó�ʼ����������С������������Լ������ʱ��
       pool.setInitConn(5);
       pool.setMinConn(5);
       pool.setMaxConn(250);
       pool.setMaxIdle(1000*60*60*6);
       
       // �������߳�˯��ʱ��
       pool.setMaintSleep(30);
       
       // ����TCP���������ӳ�ʱ��
       pool.setNagle(false);
       pool.setSocketTO(3000);
       pool.setSocketConnectTO(0);
       pool.setAliveCheck(true);
       
       // ��ʼ�����ӳ�
       pool.initialize();
       
       // ѹ�����ã�����ָ����С����λΪK�������ݶ��ᱻѹ��
//       mcc.setCompressEnable(true);
//       mcc.setCompressThreshold(64 * 1024);
    }
    
    /**
     * �޲ι���
     */
    protected MemcachedManager (){
       
    }
    
    // �ܱ����Ķ���
    protected static MemcachedManager instance = new MemcachedManager();
    
    /**
     * Ϊ�ܱ����Ķ����ṩһ�������ķ��ʷ���
     */
    public static MemcachedManager getInstance () {
       return instance;
    }
    
    /**
     * ��Ӷ��󵽻����У����ɷ�������
     * @param key
     * @param value
     * @return
     */
    public boolean add(String key,Object value) {
       return mcc.add(key, value);
    }
    public boolean add (String key,Object value,Date expiry) {
       return mcc.add(key, value,expiry);
    }
    
    public boolean replace (String key,Object value) {
       return mcc.replace(key, value);
    }
    
    public boolean replace (String key,Object value,Date expiry)
    {
       return mcc.replace(key, value, expiry);
    }
    
    /**
     * ����ָ���Ĺؼ��ֻ�ȡ����
     */
    public Object get(String key) {
       return mcc.get(key);
    }
    
    
    
    /**
     * ����MemCached���Խ�����������뻺�棬���ӻ�����ȡ��
     */
//  public static void main(String[] args) {
//      // �õ�MemcachedManagerʵ��
//     MemcachedManager cache = MemcachedManager.getInstance();
//     
//     // ����UserDao����
//     UserDao userDao = new UserDao();
//     // ��ѯ����User����
//     User user = userDao.getUserById(4);
//     
//     // ��User������ӵ�������
//     cache.add("user", user.getUname());
//     
//     // ��User����ӻ�����ȡ����
//     String uname = (String)cache.get("user");
//     
//     System.out.println("�ӻ�����ȡ���Ķ���Ϊ��" + uname);
//  }
    
    
    
    /**
     * ����MemCached���󽫼��ϴ��뻺�棬���ӻ�����ȡ��
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
       // �õ�MemcachedManagerʵ��
       MemcachedManager cache = MemcachedManager.getInstance();
       
       // ����UserDao����
//       UserDao userDao = new UserDao();
       // �õ����϶���
//       List userList = userDao.getUserList();
       
       // ����User����
//       User user = null;
//       for (int i=0; i<userList.size(); i++)
//       {
//           // ѭ���������϶���
//           user = (User)userList.get(i);
//           user.getUid();
//           user.getUname();
//           user.getUpass();
//
//           // �����϶�����뻺����
//           cache.add("userList" + i,user.getUname());
//
//           // �����ϴӻ�����ȡ��
//           String uname = (String)cache.get("userList"+i);
//
//           System.out.println("�ӻ�����ȡ�õļ���Ϊ��" + uname);
//       }
    }

}