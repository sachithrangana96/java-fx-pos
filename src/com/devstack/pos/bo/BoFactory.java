package com.devstack.pos.bo;

import com.devstack.pos.bo.custom.impl.CustomerBoImpl;
import com.devstack.pos.bo.custom.impl.ProductBoImpl;
import com.devstack.pos.bo.custom.impl.UserBoImpl;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custome.impl.CustomerDaoImpl;
import com.devstack.pos.dao.custome.impl.ProductDaoImpl;
import com.devstack.pos.dao.custome.impl.UserDaoImpl;
import com.devstack.pos.enums.BoType;
import com.devstack.pos.enums.DaoType;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory(){}

    public static BoFactory getInstance(){
        return (boFactory==null) ? boFactory = new BoFactory():boFactory;
    }


    //type inference  ---- kiyl kiynne object eka auto convert wenn hadana upakkramayak
    public <T>T getBo(BoType boType){
        switch (boType){
            case USER:
                return (T) new UserBoImpl();
            case PRODUCT:
                return (T) new ProductBoImpl();
            case CUSTOMER:
                return (T) new CustomerBoImpl();
            default:
                return null;
        }
    }
}

