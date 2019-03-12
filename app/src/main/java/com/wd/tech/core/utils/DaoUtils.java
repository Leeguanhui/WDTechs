package com.wd.tech.core.utils;

import com.wd.tech.core.WDApplication;
import com.wd.tech.greendao.DaoMaster;
import com.wd.tech.greendao.DaoSession;
import com.wd.tech.greendao.FindConversationListDao;

public class DaoUtils {
    private FindConversationListDao conversationDao;

    private static DaoUtils instance;

    private DaoUtils(){
        DaoSession session = DaoMaster.newDevSession(WDApplication.getContext(), FindConversationListDao.TABLENAME);
        conversationDao = session.getFindConversationListDao();
    }

    public static DaoUtils getInstance() {
        if (instance==null){
            return new DaoUtils();
        }
        return instance;
    }

    public FindConversationListDao getConversationDao() {
        return conversationDao;
    }
}
