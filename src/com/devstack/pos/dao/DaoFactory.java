package com.devstack.pos.dao;

import com.devstack.pos.dao.custome.impl.CustomerDaoImpl;
import com.devstack.pos.dao.custome.impl.ProductDaoImpl;
import com.devstack.pos.dao.custome.impl.UserDaoImpl;
import com.devstack.pos.enums.DaoType;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return (daoFactory==null) ? daoFactory = new DaoFactory():daoFactory;
    }


    //type inference  ---- kiyl kiynne object eka auto convert wenn hadana upakkramayak
    public <T>T getDao(DaoType daoType){
        switch (daoType){
            case USER:
                return (T) new UserDaoImpl();
            case PRODUCT:
                return (T) new ProductDaoImpl();
            case CUSTOMER:
                return (T) new CustomerDaoImpl();
            default:
                return null;
        }
    }
}
